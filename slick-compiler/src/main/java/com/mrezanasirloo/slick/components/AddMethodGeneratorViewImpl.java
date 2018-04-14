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

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 * Created on: 2017-03-06
 */

public class AddMethodGeneratorViewImpl implements AddMethodGenerator {

    public AddMethodGeneratorViewImpl() {
    }

    @Override
    public List<MethodSpec> generate(AnnotatedPresenter ap) {
        final ArrayList<MethodSpec> list = new ArrayList<>(2);

        String uniqueId = "uniqueId";
        String activity = "activity";
        String view = "view";
        String onDestroy = "onDestroy";
        MethodSpec.Builder builder = MethodSpec.methodBuilder(onDestroy)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, uniqueId)
                .addParameter(SlickProcessor.ClASS_NAME_ACTIVITY, activity);
        builder.addStatement("if($L == null || $L.$L.get($L.hashCode()) == null) return", "hostInstance", "hostInstance",
                "delegates", uniqueId)
                .addComment("Either has not bound or already has destroyed.");
        builder.addStatement("$L.$L.get($L.hashCode()).$L($L)", "hostInstance", "delegates", uniqueId, onDestroy, activity)
                .returns(void.class);

        list.add(builder.build());

        builder = MethodSpec.methodBuilder(onDestroy)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(String.class, uniqueId)
                .addParameter(SlickProcessor.ClASS_NAME_VIEW, view)
                .addStatement("$L($L, $L.getActivity($L))", onDestroy, uniqueId, ap.getDelegateType().simpleName(), view)
                .returns(void.class);

        list.add(builder.build());

        return list;
    }

}
