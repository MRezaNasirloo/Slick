package com.github.slick.components;

import com.github.slick.AnnotatedPresenter;
import com.github.slick.SlickProcessor;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-03-06
 */

public class AddMethodGeneratorCallbackImpl implements AddMethodGenerator {

    private final String[] methodNames;

    public AddMethodGeneratorCallbackImpl(String... methodNames) {
        this.methodNames = methodNames;
    }

    @Override
    public Iterable<MethodSpec> generate(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        final MethodSignatureGeneratorDaggerImpl generatorDagger = new MethodSignatureGeneratorDaggerImpl();
        for (String name : methodNames) {
            MethodSpec.Builder builder = generatorDagger.generate(name, ap, TypeName.get(void.class));
            if (name.equals("onDetach") && (SlickProcessor.ViewType.VIEW.equals(ap.getViewType())
                    || SlickProcessor.ViewType.DAGGER_VIEW.equals(ap.getViewType()))) {
                builder.addStatement("if($L == null || $L.$L.get($T.getId($L)) == null) return", "hostInstance", "hostInstance",
                                     "delegates", ap.getDelegateType(), ap.getViewVarName())
                        .addComment("Already has called by its delegate.");
            }
            builder.addStatement("$L.$L.get($T.getId($L)).$L($L)", "hostInstance",
                                 "delegates", ap.getDelegateType(), ap.getViewVarName(), name, ap.getViewVarName()
            ).returns(void.class);

            list.add(builder.build());
        }
        return list;
    }
}
