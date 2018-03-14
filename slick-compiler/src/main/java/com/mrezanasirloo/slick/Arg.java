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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2017-02-01
 *
 *         Model class for presenter parameters
 */

public class Arg {
    private String name;
    private TypeMirror type;
    private List<? extends AnnotationMirror> annotations;

    Arg(String name, TypeMirror type, List<? extends AnnotationMirror> annotations) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
    }

    /**
     * @return argument's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return argument's type
     */
    public TypeMirror getType() {
        return type;
    }

    /**
     * @return a list of argument's annotations
     */
    public List<? extends AnnotationMirror> getAnnotations() {
        return annotations;
    }
}
