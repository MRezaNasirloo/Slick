package com.github.slick;

import com.squareup.javapoet.ClassName;

import java.util.List;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

class AnnotatedPresenter {
    private List<PresenterArgs> args;
    private ClassName view;
    private ClassName presenter;
    private ClassName PresenterHost;

    AnnotatedPresenter(String canonicalName, List<PresenterArgs> args, ClassName presenter, ClassName presenterHost) {
        if (canonicalName == null) {
            throw new IllegalArgumentException(new Throwable("canonicalName cannot be null")); // TODO: 2017-02-01 error
        }
        this.presenter = presenter;
        PresenterHost = presenterHost;
        this.args = args;

        final String[] split = canonicalName.split("\\.");
        StringBuilder builder = new StringBuilder(canonicalName.length());
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]).append(".");
        }
        //remove the extra dot
        if (split.length > 1) builder.deleteCharAt(builder.length() - 1);
        view = ClassName.get(builder.toString(), split[split.length - 1]);
    }

    ClassName getView() {
        return view;
    }

    public List<PresenterArgs> getArgs() {
        return args;
    }

    public ClassName getPresenterHost() {
        return PresenterHost;
    }

    public ClassName getPresenter() {
        return presenter;
    }
}
