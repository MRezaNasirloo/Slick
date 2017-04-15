package com.github.slick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;

import java.util.List;

import javax.inject.Provider;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 *         <p>
 *         Model for Fields which annotated with {@link Presenter}
 */

public class AnnotatedPresenter {

    public static final String HOST_INSTANCE_VAR_NAME = "hostInstance";
    public static final String PRESENTER_VAR_NAME = "presenter";
    public static final String DELEGATE_VAR_NAME = "delegate";
    public static final String DELEGATES_FIELD_NAME = "delegates";

    private final String presenterProvider;
    private List<Arg> args;
    private String fieldName;
    private ClassName view;
    private SlickProcessor.ViewType viewType;
    private ClassName viewInterface;
    private ClassName presenter;

    AnnotatedPresenter(String viewCanonicalName, List<Arg> args, String fieldName, ClassName view,
                       SlickProcessor.ViewType viewType,
                       ClassName presenter, String presenterProvider) {
        this.presenterProvider = presenterProvider;
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

    /**
     * @return the view interface
     * <p>
     * e.g: MainView
     */
    public ClassName getViewInterface() {
        return viewInterface;
    }

    /**
     * @return a list of presenter's constructor parameters
     */
    public List<Arg> getArgs() {
        return args;
    }

    /**
     * @return the presenter parameters in a comma separated string to use as in input
     * <p>
     * e.g: "obj1, obj2, var1, var2"
     */
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

    /**
     * @return presenter host name;
     * <p>
     * e.g: MainActivity_Slick
     */
    public ClassName getPresenterHost() {
        return ClassName.get(presenter.packageName(), view.simpleName() + "_Slick");
    }

    /**
     * @return presenter class name object
     */
    public ClassName getPresenter() {
        return presenter;
    }

    /**
     * @return the view type
     */
    public SlickProcessor.ViewType getViewType() {
        return viewType;
    }

    /**
     * @return the view
     * <p>
     * e.g: MainActivity
     */
    public ClassName getView() {
        return view;
    }

    /**
     * @return presenter field name
     * <p>
     * e.g: presenter
     */
    public String getPresenterFieldName() {
        return fieldName;
    }

    /**
     * @return view name with deCapitalized initial
     * <p>
     * e.g: MainActivity -> mainActivity
     */
    public String getViewVarName() {
        return Utils.deCapitalize(view.simpleName());
    }

    /**
     * @return delegate class which the host uses
     * <p>
     * e.g: SlickDelegate, SlickFragmentDelegate
     */
    public ClassName getDelegateType() {
        return viewType.delegateType();
    }

    /**
     * @return parametrized delegate type</pre>
     * <p>
     * e.g: SlickDelegate&lt;ExampleView, ExamplePresenter&gt;
     */
    public ParameterizedTypeName getParametrizedDelegateType() {
        return ParameterizedTypeName.get(viewType.delegateType(), viewInterface, presenter);
    }

    /**
     * @return provider field name
     * <p>
     * e.g: {@link Provider}&lt;MainPresenter&gt; provider ---> "provider"
     */
    public String getPresenterProvider() {
        return presenterProvider;
    }
}
