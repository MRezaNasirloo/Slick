package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.List;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

class AnnotatedPresenter {
    private List<PresenterArgs> args;
    private String fieldName;
    private ClassName view;
    private SlickProcessor.ViewType viewType;
    private ClassName viewInterface;
    private ClassName presenter;

    AnnotatedPresenter(String viewCanonicalName, List<PresenterArgs> args, String fieldName, ClassName view,
                       SlickProcessor.ViewType viewType,
                       ClassName presenter) {
        if (viewCanonicalName == null) {
            throw new IllegalArgumentException(
                    new Throwable("viewCanonicalName cannot be null")); // TODO: 2017-02-01 error
        }
        this.view = view;
        this.viewType = viewType;
        this.presenter = presenter;
        this.fieldName = fieldName;
        this.args = args;

        final String[] split = viewCanonicalName.split("\\.");
        StringBuilder builder = new StringBuilder(viewCanonicalName.length());
        for (int i = 0; i < split.length - 1; i++) {
            builder.append(split[i]).append(".");
        }
        //remove the extra dot
        if (split.length > 1) builder.deleteCharAt(builder.length() - 1);
        viewInterface = ClassName.get(builder.toString(), split[split.length - 1]);
    }

    ClassName getViewInterface() {
        return viewInterface;
    }

    public List<PresenterArgs> getArgs() {
        return args;
    }

    public String getArgsAsString() {
        StringBuilder argsCode = new StringBuilder(args.size() * 10);
        if (args.size() > 0) {

            for (int i = 0; i < args.size() - 1; i++) {
                argsCode.append(args.get(i).getName()).append(", ");

            }
            argsCode.append(args.get(args.size() - 1).getName());
        }
        return argsCode.toString();
    }

    public ClassName getPresenterHost() {
        return ClassName.get(presenter.packageName(), view.simpleName() + "_Slick");
    }

    public ClassName getPresenter() {
        return presenter;
    }

    public SlickProcessor.ViewType getViewType() {
        return viewType;
    }

    public ClassName getView() {
        return view;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getViewVarName() {
        return SlickProcessor.deCapitalize(view.simpleName());
    }

    public ClassName getDelegateType() {
        return viewType.delegateType();
    }

    public ParameterizedTypeName getDelegateParametrizedType() {
        return ParameterizedTypeName.get(viewType.delegateType(), viewInterface, presenter);
    }
}
