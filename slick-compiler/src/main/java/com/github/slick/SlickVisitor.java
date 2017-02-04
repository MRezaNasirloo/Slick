package com.github.slick;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

class SlickVisitor extends SimpleAnnotationValueVisitor7 <TypeMirror, Void> {
    @Override
    public TypeMirror visitType(TypeMirror typeMirror, Void avoid) {
        return typeMirror;
    }
}
