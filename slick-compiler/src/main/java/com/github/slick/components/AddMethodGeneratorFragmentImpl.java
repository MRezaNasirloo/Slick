package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class AddMethodGeneratorFragmentImpl implements AddMethodGenerator {

    @Override
    public Iterable<MethodSpec> generate(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        String[] methodNames = {"onStart", "onStop", "onDestroy"};
        final MethodSignatureGeneratorDaggerImpl generatorDagger = new MethodSignatureGeneratorDaggerImpl();
        for (String name : methodNames) {
            MethodSpec methodSpec = generatorDagger.generate(name, ap, TypeName.get(void.class))
                    .addStatement("$L.$L.get($T.getId($L)).$L($L)", "hostInstance",
                            "delegates", ap.getDelegateType(), ap.getViewVarName(), name, ap.getViewVarName())
                    .returns(void.class).build();
            list.add(methodSpec);
        }
        return list;
    }
}
