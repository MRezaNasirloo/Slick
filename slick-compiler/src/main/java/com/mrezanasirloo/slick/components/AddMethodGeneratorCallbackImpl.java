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

package com.mrezanasirloo.slick.components;

import com.mrezanasirloo.slick.AnnotatedPresenter;
import com.mrezanasirloo.slick.SlickProcessor;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-03-06
 */

public class AddMethodGeneratorCallbackImpl implements AddMethodGenerator {

    private final String[] methodNames;
    private final AddMethodGeneratorNullCheck nullCheck = new AddMethodGeneratorNullCheckImpl();

    public AddMethodGeneratorCallbackImpl(String... methodNames) {
        this.methodNames = methodNames;
    }

    @Override
    public List<MethodSpec> generate(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(3);
        final MethodSignatureGeneratorDaggerImpl generatorDagger = new MethodSignatureGeneratorDaggerImpl();
        for (String name : methodNames) {
            MethodSpec.Builder builder = generatorDagger.generate(name, ap, TypeName.get(void.class));
            if (!name.equals("onAttach") && (SlickProcessor.ViewType.VIEW.equals(ap.getViewType())
                    || SlickProcessor.ViewType.DAGGER_VIEW.equals(ap.getViewType()))) {
                nullCheck.generate(ap, builder);
            }
            builder.addStatement("$L.$L.get($T.getId($L)).$L($L)", "hostInstance",
                    "delegates", ap.getDelegateType(), ap.getViewVarName(), name, ap.getViewVarName()
            ).returns(void.class);

            list.add(builder.build());
        }
        return list;
    }

}
