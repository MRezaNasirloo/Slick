package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

import static com.github.slick.AnnotatedPresenter.DELEGATES_FIELD_NAME;
import static com.github.slick.AnnotatedPresenter.DELEGATE_VAR_NAME;
import static com.github.slick.AnnotatedPresenter.HOST_INSTANCE_VAR_NAME;
import static com.github.slick.AnnotatedPresenter.PRESENTER_VAR_NAME;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class BindMethodBodyGeneratorImpl implements BindMethodBodyGenerator {

    private final GetViewIdGenerator getViewIdGenerator;
    private final PresenterInstantiationGenerator presenterInstantiationGenerator;
    private final ViewCallbackGenerator viewCallbackGenerator;

    public BindMethodBodyGeneratorImpl(GetViewIdGenerator getViewIdGenerator,
                                       PresenterInstantiationGenerator presenterInstantiationGenerator,
                                       ViewCallbackGenerator viewCallbackGenerator) {
        this.getViewIdGenerator = getViewIdGenerator;
        this.presenterInstantiationGenerator = presenterInstantiationGenerator;
        this.viewCallbackGenerator = viewCallbackGenerator;
    }

    @Override
    public MethodSpec.Builder generate(MethodSpec.Builder builder, AnnotatedPresenter ap) {

        final ClassName view = ap.getView();
        final ClassName viewInterface = ap.getViewInterface();
        final ClassName presenterType = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final String fieldName = ap.getFieldName();
        final ClassName delegateType = ap.getViewType().delegateType();
        final ParameterizedTypeName typeNameDelegate =
                ParameterizedTypeName.get(delegateType, viewInterface, presenterType);

        getViewIdGenerator
                .generate(builder, ap)
                .addStatement("if ($L == null) $L = new $T()",
                        HOST_INSTANCE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME,
                        presenterHost)
                .addStatement("$T $L = $L.$L.get(id)",
                        typeNameDelegate,
                        DELEGATE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME,
                        DELEGATES_FIELD_NAME)
                .beginControlFlow("if ($L == null)", DELEGATE_VAR_NAME);

        presenterInstantiationGenerator.generate(builder, ap)
                .addStatement("$L = new $T<>($L, $L.getClass(), id)",
                        DELEGATE_VAR_NAME,
                        ap.getDelegateType(),
                        PRESENTER_VAR_NAME,
                        ap.getViewVarName())
                .addStatement("$L.setListener($L)",
                        DELEGATE_VAR_NAME,
                        HOST_INSTANCE_VAR_NAME)
                .addStatement("$L.$L.put(id, $L)",
                        HOST_INSTANCE_VAR_NAME,
                        DELEGATES_FIELD_NAME,
                        DELEGATE_VAR_NAME);

        viewCallbackGenerator.generate(builder, ap)
                .addStatement("(($L) $L).$L = $L.getPresenter()",
                        view.simpleName(),
                        ap.getViewVarName(),
                        fieldName,
                        DELEGATE_VAR_NAME);
        return builder;
    }
}
