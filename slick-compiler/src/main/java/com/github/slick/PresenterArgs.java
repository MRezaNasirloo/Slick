package com.github.slick;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

class PresenterArgs {
    private String name;
    private TypeMirror type;
    private List<? extends AnnotationMirror> annotations;

    PresenterArgs(String name, TypeMirror type, List<? extends AnnotationMirror> annotations) {
        this.name = name;
        this.type = type;
        this.annotations = annotations;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public List<? extends AnnotationMirror> getAnnotations() {
        return annotations;
    }
}
