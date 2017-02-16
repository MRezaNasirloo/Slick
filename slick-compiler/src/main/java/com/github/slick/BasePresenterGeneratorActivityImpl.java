package com.github.slick;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public abstract class BasePresenterGeneratorActivityImpl extends BasePresenterGenerator
        implements PresenterGenerator {

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

        final TypeVariableName activityGenericType = TypeVariableName.get("T", getClassNameViewType());

        final ParameterizedTypeName typeNameDelegate =
                ParameterizedTypeName.get(getClassNameDelegate(), viewInterface, presenter);

        final ParameterizedTypeName parametrizedMapTypeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ClASS_NAME_STRING, typeNameDelegate);

        final FieldSpec delegate = FieldSpec.builder(parametrizedMapTypeName, "delegates")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", ClASS_NAME_HASH_MAP)
                .build();

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
                fieldName,
                argNameView,
                activityGenericType,
                typeNameDelegate,
                argsCode);

        for (PresenterArgs arg : args) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            methodBuilder.addParameter(paramBuilder.build());
        }

        final MethodSpec bind = methodBuilder.build();


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

    /**
     * Builds the bind method
     *
     * @param view                the class which implements the view interface
     * @param presenter           presenter class
     * @param presenterHost       presenter host class
     * @param fieldName           presenter name in view class
     * @param argNameView     activity parameter name
     * @param activityGenericType activity type
     * @param typeNameDelegate    delegate type
     * @param argsCode            the presenter parameters in a comma separated string
     * @return bind method builder
     */
    protected abstract MethodSpec.Builder bindMethod(ClassName view, ClassName presenter, ClassName presenterHost,
                                                     String fieldName, String argNameView,
                                                     TypeVariableName activityGenericType,
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
}
