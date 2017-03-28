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

    public String getMiddlewareVarNamesAsString() {
        StringBuilder builder = new StringBuilder();
        final String[] names = getMiddlewareVarNames();
        for (int i = 0; i < names.length; i++) {
            builder.append(names[i]);
            if (i < names.length - 1) builder.append(", ");
        }
        return builder.toString();
    }

    public String[] getMiddlewareVarNames() {
        final String[] names = getMiddlewareClassNamesAsStringArray();
        final String[] varNames = new String[names.length];
        for (int i = 0; i < names.length; i++) {
            varNames[i] = Utils.deCapitalize(names[i]);
        }
        return varNames;
    }

    public String[] getMiddlewareClassNamesAsStringArray() {
        final String[] names = new String[middleware.size()];
        for (int i = 0; i < middleware.size(); i++) {
            TypeMirror typeMirror = middleware.get(i);
            final ClassName className =
                    ClassName.get((TypeElement) MiddlewareProcessor.typeUtils.asElement(typeMirror));
            names[i] = className.simpleName();
        }
        return names;
    }

    public ClassName[] getMiddlewareClassNames() {
        final ClassName[] names = new ClassName[middleware.size()];
        for (int i = 0; i < middleware.size(); i++) {
            TypeMirror typeMirror = middleware.get(i);
            names[i] = ClassName.get((TypeElement) MiddlewareProcessor.typeUtils.asElement(typeMirror));
        }
        return names;
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