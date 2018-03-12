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
