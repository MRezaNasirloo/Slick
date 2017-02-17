package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public abstract class BasePresenterGeneratorImpl implements PresenterGenerator {

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
        final List<PresenterArgs> args = ap.getArgs();
        final String fieldName = ap.getFieldName();
        final String argNameView = deCapitalize(ap.getView().simpleName());
        final String presenterArgName = deCapitalize(ap.getPresenter().simpleName());

        final TypeVariableName activityGenericType = TypeVariableName.get("T", getClassNameViewType());

        final ParameterizedTypeName typeNameDelegate =
                ParameterizedTypeName.get(getClassNameDelegate(), viewInterface, presenter);

        final FieldSpec delegate = getDelegateField(typeNameDelegate);

        final FieldSpec hostInstance = FieldSpec.builder(presenterHost, hostInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .build();

        StringBuilder argsCode = new StringBuilder(args.size() * 10);
        if (args.size() > 0) {

            for (int i = 0; i < args.size() - 1; i++) {
                argsCode.append(args.get(i).getName()).append(", ");

            }
            argsCode.append(args.get(args.size() - 1).getName());
        }

        final MethodSpec.Builder methodBuilder = bindMethod(view,
                presenter,
                presenterHost,
                getClassNameDelegate(),
                fieldName,
                argNameView,
                presenterArgName,
                activityGenericType,
                typeNameDelegate, argsCode);

        final MethodSpec bind = addConstructorParameter(args, methodBuilder).build();

        final MethodSpec onDestroy = onDestroyMethod();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(onDestroy)
                .build();
    }

    protected MethodSpec onDestroyMethod() {
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

    protected String deCapitalize(String string) {
        return Character.toLowerCase(string.charAt(0)) + string.substring(1);
    }

    /**
     * Builds the bind method
     *
     * @param view                the class which implements the view interface
     * @param presenter           presenter class
     * @param presenterHost       presenter host class
     * @param fieldName           presenter name in view class
     * @param argNameView         activity parameter name
     * @param presenterArgName
     *@param viewGenericType activity type
     * @param typeNameDelegate    delegate type
     * @param argsCode            the presenter parameters in a comma separated string    @return bind method builder
     */
    protected abstract MethodSpec.Builder bindMethod(ClassName view, ClassName presenter, ClassName presenterHost,
                                                     ClassName classNameDelegate,
                                                     String fieldName, String argNameView,
                                                     String presenterArgName, TypeVariableName viewGenericType,
                                                     ParameterizedTypeName typeNameDelegate,
                                                     StringBuilder argsCode);

    /**
     * @return ClassName for generic view
     */
    protected abstract ClassName getClassNameViewType();


    /**
     * @return ClassName for parametrized delegate
     */
    protected abstract ClassName getClassNameDelegate();

    /**
     * @param typeNameDelegate delegate parametrized type
     * @return delegate field
     */
    protected abstract FieldSpec getDelegateField(ParameterizedTypeName typeNameDelegate);

    protected MethodSpec.Builder addConstructorParameter(List<PresenterArgs> args,
                                                         MethodSpec.Builder methodBuilder) {
        return methodBuilder;
    }

}
