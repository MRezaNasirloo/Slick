package com.github.slick.middleware;

import com.github.slick.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-15
 */

public class AnnotatedMethod {
    private final ExecutableElement superMethod;
    private final MiddlewareProcessor.MethodType methodType;
    private final ContainerClass containerClass;
    private final String methodName;
    private final List<? extends VariableElement> args;
    private final List<TypeMirror> middleware;
    private final TypeName returnType;

    public AnnotatedMethod(ExecutableElement superMethod, MiddlewareProcessor.MethodType methodType,
                           ContainerClass containerClass,
                           String methodName,
                           List<? extends VariableElement> args, List<TypeMirror> middleware,
                           TypeName returnType) {
        this.superMethod = superMethod;
        this.methodType = methodType;
        this.containerClass = containerClass;
        this.methodName = methodName;
        this.args = args;
        this.middleware = middleware;
        this.returnType = returnType;
    }

    public MiddlewareProcessor.MethodType getMethodType() {
        return methodType;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<? extends VariableElement> getArgs() {
        return args;
    }

    public List<TypeMirror> getMiddleware() {
        return middleware;
    }

    public String getMiddlewareFieldNames() {
        StringBuilder builder = new StringBuilder();
        List<TypeMirror> middleware = getMiddleware();
        for (int i = 0; i < middleware.size(); i++) {
            TypeMirror typeMirror = middleware.get(i);
            final ClassName className = ClassName.get(
                    (TypeElement) MiddlewareProcessor.typeUtils.asElement(typeMirror));
            builder.append(Utils.deCapitalize(className.simpleName()));
            if (i < middleware.size() - 1) builder.append(", ");
        }
        return builder.toString();
    }

    public TypeName getReturnType() {
        return returnType;
    }

    public ContainerClass getContainerClass() {
        return containerClass;
    }

    public ExecutableElement getSuperMethod() {
        return superMethod;
    }
}