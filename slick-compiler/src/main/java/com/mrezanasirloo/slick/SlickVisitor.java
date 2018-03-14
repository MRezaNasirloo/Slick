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

package com.mrezanasirloo.slick;

import java.util.List;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-01
 */

public class SlickVisitor extends SimpleAnnotationValueVisitor7 <TypeMirror, List<TypeMirror>> {
    @Override
    public TypeMirror visitType(TypeMirror typeMirror, List<TypeMirror> list) {
        list.add(typeMirror);
        return typeMirror;
    }

    @Override
    public TypeMirror visitArray(List<? extends AnnotationValue> list, List<TypeMirror> resultList) {
        for (AnnotationValue value : list) {
            value.accept(this, resultList);
        }
        return super.visitArray(list, resultList);
    }
}
