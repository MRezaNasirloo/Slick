package com.github.slick.middleware;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-15
 */

public class ContainerClass {
    private final ClassName className;
    private final TypeName superClass;
    private final List<? extends VariableElement> args;
    private final List<? extends TypeParameterElement> typeParameters;

    public ContainerClass(TypeName superClass, ClassName className, List<? extends VariableElement> args,
                          List<? extends TypeParameterElement> typeParameters) {
        this.superClass = superClass;
        this.className = className;
        this.args = args;
        this.typeParameters = typeParameters;
    }


    public ClassName getClassName() {
        return className;
    }

    public List<? extends VariableElement> getArgs() {
        return args;
    }

    public String getArgsVarNames() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.size(); i++) {
            builder.append(args.get(i).getSimpleName());
            if (i < args.size() - 1) builder.append(", ");
        }
        return builder.toString();
    }

    public List<? extends TypeParameterElement> getTypeParameters() {
        return typeParameters;
    }

    public TypeName getSuperClass() {
        return superClass;
    }

    /**
     * @return subclass name;
     * <p>
     * e.g: MainRouterSlick
     */
    public ClassName getSubclass() {
        return ClassName.get(className.packageName(), className.simpleName() + "Slick");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContainerClass that = (ContainerClass) o;

        if (!className.equals(that.className)) return false;
        if (!args.equals(that.args)) return false;
        return typeParameters.equals(that.typeParameters);

    }

    @Override
    public int hashCode() {
        int result = className.hashCode();
        result = 31 * result + args.hashCode();
        result = 31 * result + typeParameters.hashCode();
        return result;
    }
}