package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class BindMethodBodyGeneratorImpl implements BindMethodBodyGenerator {

    private final String hostInstance = "hostInstance";
    private final String presenter = "presenter";
    private final String delegate = "delegate";
    private final String delegates = "delegates";
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
                        hostInstance,
                        hostInstance,
                        presenterHost)
                .addStatement("$T $L = $L.$L.get(id)",
                        typeNameDelegate,
                        delegate,
                        hostInstance,
                        delegates)
                .beginControlFlow("if ($L == null)", delegate);

        presenterInstantiationGenerator.generate(builder, ap)
                .addStatement("$L = new $T<>($L, $L.getClass(), id)",
                        delegate,
                        ap.getDelegateType(),
                        presenter,
                        ap.getViewVarName())
                .addStatement("$L.setListener($L)",
                        delegate,
                        hostInstance)
                .addStatement("$L.$L.put(id, $L)",
                        hostInstance,
                        delegates,
                        delegate);

        viewCallbackGenerator.generate(builder, ap)
                .addStatement("(($L) $L).$L = $L.getPresenter()",
                        view.simpleName(),
                        ap.getViewVarName(),
                        fieldName,
                        delegate);
        return builder;
    }
}
