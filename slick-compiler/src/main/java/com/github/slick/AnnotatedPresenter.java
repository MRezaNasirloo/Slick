package com.github.slick;

import com.squareup.javapoet.ClassName;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

class AnnotatedPresenter {
    private ClassName className;

    AnnotatedPresenter(String canonicalName) {
        if (canonicalName == null) return; // TODO: 2017-02-01 error

        final String[] split = canonicalName.split("\\.");
        StringBuilder builder = new StringBuilder(canonicalName.length());
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]).append(".");
        }
        //remove the extra dot
        builder.deleteCharAt(builder.length() - 1);
        className = ClassName.get(builder.toString(), split[split.length - 1]);
    }

    ClassName getClassName() {
        return className;
    }
}
