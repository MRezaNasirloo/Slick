package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.github.slick.PresenterArgs;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeVariableName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-24
 */
public class MethodSignatureGeneratorImpl implements MethodSignatureGenerator {

    @Override
    public MethodSpec.Builder generate(String name, AnnotatedPresenter ap, TypeName returns) {
        final TypeVariableName typeVariableName = TypeVariableName.get("T",
                ap.getViewType().className()).withBounds(ap.getViewInterface());
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addTypeVariable(typeVariableName)
                .addParameter(typeVariableName, ap.getViewVarName())
                .addParameters(addExtraParameters(ap))
                .returns(returns);
    }

    protected Iterable<ParameterSpec> addExtraParameters(AnnotatedPresenter ap) {
        final List<ParameterSpec> list = new ArrayList<>(ap.getArgs().size());
        for (PresenterArgs arg : ap.getArgs()) {
            final ParameterSpec.Builder paramBuilder =
                    ParameterSpec.builder(TypeName.get(arg.getType()), arg.getName());
            for (AnnotationMirror annotationMirror : arg.getAnnotations()) {
                paramBuilder.addAnnotation(AnnotationSpec.get(annotationMirror));
            }
            list.add(paramBuilder.build());
        }
        return list;
    }
}
