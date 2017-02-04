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
import static com.github.slick.SlickProcessor.CLASS_NAME_SLICK_DELEGATOR;
import static com.github.slick.SlickProcessor.ClASS_NAME_ON_DESTROY_LISTENER;
import static com.github.slick.SlickProcessor.ClASS_NAME_SLICK_VIEW;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-05
 */
public class PresenterGeneratorFragmentImpl extends BasePresenterGenerator implements PresenterGenerator {
    @Override
    public TypeSpec generate(AnnotatedPresenter ap) {
        final ClassName viewInterface = ap.getViewInterface();
        final ClassName viewType = ap.getViewType();
        final ClassName presenter = ap.getPresenter();
        final ClassName presenterHost = ap.getPresenterHost();
        final List<PresenterArgs> args = ap.getArgs();
        final String presenterInstanceName = "presenterInstance";
        final String hostInstanceName = "hostInstance";
        final String argNameBind = deCapitalize(ap.getView().simpleName());
        final String argNameSetListener = deCapitalize(CLASS_NAME_SLICK_DELEGATOR.simpleName());

        final TypeVariableName type = TypeVariableName.get("T", ap.getView());
        final ParameterizedTypeName typeName =
                ParameterizedTypeName.get(CLASS_NAME_SLICK_DELEGATE, viewInterface, presenter);

        final FieldSpec delegate = FieldSpec.builder(typeName, "delegate")
                .initializer("new $T()", CLASS_NAME_SLICK_DELEGATE)
                .build();

        final FieldSpec presenterInstance = FieldSpec.builder(presenter, presenterInstanceName)
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
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
                .addTypeVariable(type.withBounds(ClASS_NAME_SLICK_VIEW).withBounds(CLASS_NAME_SLICK_DELEGATOR))
                .addParameter(type, argNameBind)
                .addStatement("if ($L == null) $L = new $T()", hostInstanceName, hostInstanceName, presenterHost)
                .addStatement("if ($L == null) $L = new $T($L)", presenterInstanceName, presenterInstanceName,
                        presenter, argsCode.toString())
                .addStatement("return $L.setListener($L)", hostInstanceName, argNameBind)
                .returns(presenter);

        for (PresenterArgs arg : args) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            methodBuilder.addParameter(paramBuilder.build());
        }

        final MethodSpec bind = methodBuilder.build();

        final MethodSpec setListener = MethodSpec.methodBuilder("setListener")
                .addModifiers(Modifier.PRIVATE)
                .addParameter(CLASS_NAME_SLICK_DELEGATOR, argNameSetListener)
                .returns(presenter)
                .addStatement("$L.getSlickDelegate().setListener(this)", argNameSetListener)
                .addStatement("return $L", presenterInstanceName)
                .build();

        final MethodSpec onDestroy = MethodSpec.methodBuilder("onDestroy")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("$L = null", presenterInstanceName)
                .addStatement("$L = null", hostInstanceName)
                .build();


        return TypeSpec.classBuilder(presenterHost)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ClASS_NAME_ON_DESTROY_LISTENER)
                .addField(delegate)
                .addField(presenterInstance)
                .addField(hostInstance)
                .addMethod(bind)
                .addMethod(setListener)
                .addMethod(onDestroy)
                .build();
    }
}
