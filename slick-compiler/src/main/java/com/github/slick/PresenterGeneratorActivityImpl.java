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

import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATE;
import static com.github.slick.SlickProcessor.ClASS_NAME_ACTIVITY;
import static com.github.slick.SlickProcessor.ClASS_NAME_HASH_MAP;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;
import static com.github.slick.SlickProcessor.ClASS_NAME_STRING;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorActivityImpl extends BasePresenterGenerator implements PresenterGenerator {
    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final ClassName view = ap.getView();
        final ClassName viewInterface = ap.getViewInterface();
        final ClassName viewType = ap.getViewType();
        final ClassName presenter = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final List<PresenterArgs> args = ap.getArgs();
        final String fieldName = ap.getFieldName();
        final String varNameDelegate = "delegate";
        final String fieldNameDelegates = "delegates";
        final String hostInstanceName = "hostInstance";
        final String presenterName = "presenter";
        final String argNameActivity = deCapitalize(ap.getView().simpleName());

        final TypeVariableName type = TypeVariableName.get("T", ClASS_NAME_ACTIVITY);
        final ParameterizedTypeName typeNameSlickDelegate =
                ParameterizedTypeName.get(CLASS_NAME_SLICK_DELEGATE, viewInterface, presenter);

        final ParameterizedTypeName typeName =
                ParameterizedTypeName.get(ClASS_NAME_HASH_MAP, ClASS_NAME_STRING, typeNameSlickDelegate);

        final FieldSpec delegate = FieldSpec.builder(typeName, "delegates")
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

        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("bind")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(type.withBounds(ClASS_NAME_SLICK_VIEW))
                .addParameter(type, argNameActivity)
                .addStatement("final String id = $T.getActivityId($L)", CLASS_NAME_SLICK_DELEGATE, argNameActivity)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("$T $L = $L.$L.get(id)", typeNameSlickDelegate, varNameDelegate, hostInstanceName, fieldNameDelegates)
                .beginControlFlow("if ($L == null)", varNameDelegate)
                .addStatement("final $T $L = new $T($L)", presenter, presenterName, presenter, argsCode.toString())
                .addStatement("$L = new $T<>($L, $L.getClass(), id)", varNameDelegate, CLASS_NAME_SLICK_DELEGATE, presenterName, argNameActivity)
                .addStatement("$L.setListener($L)", varNameDelegate, hostInstanceName)
                .addStatement("$L.$L.put(id, $L)", hostInstanceName, fieldNameDelegates, varNameDelegate)
                .addStatement("$L.getApplication().registerActivityLifecycleCallbacks(delegate)", argNameActivity)
                .endControlFlow()
                .addStatement("(($L) $L).$L = $L.getPresenter()", view.simpleName(), argNameActivity, fieldName, varNameDelegate)
                .returns(void.class);

        for (PresenterArgs arg : args) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            methodBuilder.addParameter(paramBuilder.build());
        }

        final MethodSpec bind = methodBuilder.build();


        final MethodSpec onDestroy = MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(ClASS_NAME_STRING, "id").build())
                .addStatement("$L.$L.remove(id)", hostInstanceName, fieldNameDelegates)
                .beginControlFlow("if ($L.$L.size() == 0)", hostInstanceName, fieldNameDelegates)
                .addStatement("$L = null", hostInstanceName)
                .endControlFlow()
                .build();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(onDestroy)
                .build();
    }
}
