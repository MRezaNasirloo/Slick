/*
 * Copyright 2018. M. Reza Nasirloo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.Utils;
import com.mrezanasirloo.slick.middleware.ContainerClass;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-29
 */

public class ConstructorGeneratorImpl implements ConstructorGenerator {
    @Override
    public MethodSpec generate(ContainerClass container, Set<ClassName> middleware) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(Modifier.PUBLIC);
        for (AnnotationMirror annotationMirror : container.getAnnotations()) {
            builder.addAnnotation(AnnotationSpec.get(annotationMirror));
        }
        for (VariableElement variableElement : container.getArgs()) {
            final Set<Modifier> modifiers = variableElement.getModifiers();
            builder.addParameter(TypeName.get(variableElement.asType()),
                    variableElement.getSimpleName().toString(), modifiers
                            .toArray(new Modifier[modifiers.size()]));
        }
        builder.addStatement("super($L)", container.getArgsVarNames());

        for (ClassName className : middleware) {
            final String name = Utils.deCapitalize(className.simpleName());
            builder.addParameter(className, name);
            builder.addStatement("this.$L = $L", name, name);
        }
        return builder.build();
    }
}
