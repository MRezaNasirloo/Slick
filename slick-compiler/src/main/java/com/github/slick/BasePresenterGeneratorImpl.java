package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;
import static com.github.slick.SlickProcessor.deCapitalize;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
abstract class BasePresenterGeneratorImpl implements PresenterGenerator {

    protected final MethodSignatureGenerator generator;

    public BasePresenterGeneratorImpl(MethodSignatureGenerator generator) {
        this.generator = generator;
    }

    protected String varNameDelegate = "delegate";
    protected String fieldNameDelegates = "delegates";
    protected String hostInstanceName = "hostInstance";
    protected String presenterName = "presenter";

    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final ClassName view = ap.getView();
        final ClassName viewInterface = ap.getViewInterface();
        final ClassName presenter = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final String fieldName = ap.getFieldName();
        final String argNameView = deCapitalize(ap.getView().simpleName());
        final String presenterArgName = deCapitalize(ap.getPresenter().simpleName());

        final TypeVariableName activityGenericType = TypeVariableName.get("T", ap.getViewType().className());

        final ClassName delegateType = ap.getViewType().delegateType();

        final ParameterizedTypeName typeNameDelegate =
                ParameterizedTypeName.get(delegateType, viewInterface, presenter);

        final FieldSpec delegate = getDelegateField(typeNameDelegate, ap);

        final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        String argsCode = ap.getArgsAsString();

        final MethodSpec.Builder bindMethodSignature = generator.generate("bind", ap, returnType(ap));

        final MethodSpec.Builder methodBuilder = bindMethodBody(bindMethodSignature,
                ap,
                view,
                presenter,
                presenterHost,
                delegateType,
                fieldName,
                argNameView,
                presenterArgName, activityGenericType, typeNameDelegate, argsCode);

        final MethodSpec onDestroy = onDestroyMethod(ap);


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(hostInstance)
                .addMethod(methodBuilder.build())
                .addMethods(addMethods(ap))
                .addMethod(onDestroy)
                .build();
    }

    protected TypeName returnType(AnnotatedPresenter ap) {
        return TypeName.get(void.class);
    }

    public MethodSpec onDestroyMethod(AnnotatedPresenter ap) {
        return MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L.$L.remove(id)", hostInstanceName, fieldNameDelegates)
                .beginControlFlow("if ($L.$L.size() == 0)", hostInstanceName, fieldNameDelegates)
                .addStatement("$L = null", hostInstanceName)
                .endControlFlow()
                .build();
    }

    /**
     * Builds the bind method
     *
     * @param builder
     * @param ap
     * @param view             the class which implements the view interface
     * @param presenter        presenter class
     * @param presenterHost    presenter host class
     * @param fieldName        presenter name in view class
     * @param argNameView      activity parameter name
     * @param presenterArgName it has already the method signature
     * @param viewGenericType  activity type
     * @param typeNameDelegate delegate type
     * @param argsCode         the presenter parameters in a comma separated string    @return bind method builder
     */
    protected abstract MethodSpec.Builder bindMethodBody(MethodSpec.Builder builder, AnnotatedPresenter ap,
                                                         ClassName view,
                                                         ClassName presenter,
                                                         ClassName presenterHost,
                                                         ClassName classNameDelegate,
                                                         String fieldName, String argNameView,
                                                         String presenterArgName, TypeVariableName viewGenericType,
                                                         ParameterizedTypeName typeNameDelegate,
                                                         String argsCode);


    /**
     * @param typeNameDelegate delegate parametrized type
     * @param ap
     * @return delegate field
     */
    protected abstract FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate, AnnotatedPresenter ap);

    protected Iterable<MethodSpec> addMethods(AnnotatedPresenter ap) {
        return new ArrayList<>(0);
    }

}
