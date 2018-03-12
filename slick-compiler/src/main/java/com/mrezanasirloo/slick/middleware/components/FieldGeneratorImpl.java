package com.mrezanasirloo.slick.middleware.components;

import com.mrezanasirloo.slick.Utils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-29
 */

public class FieldGeneratorImpl implements FieldGenerator {
    @Override
    public List<FieldSpec> generate(Set<ClassName> middleware) {
        List<FieldSpec> fieldSpecs = new ArrayList<>(middleware.size());
        for (ClassName className : middleware) {
            final FieldSpec.Builder builder =
                    FieldSpec.builder(className, Utils.deCapitalize(className.simpleName()), Modifier.FINAL,
                            Modifier.PRIVATE);
            fieldSpecs.add(builder.build());
        }
        return fieldSpecs;
    }
}
