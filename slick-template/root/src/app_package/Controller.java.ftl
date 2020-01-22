package ${packageName};


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<#if !includeLayout>import android.widget.TextView;</#if>

import com.bluelinelabs.conductor.Controller;
<#if applicationPackage??>
import ${applicationPackage}.R;
import ${applicationPackage}.databinding.Controller${className}Binding;
</#if>
import com.mrezanasirloo.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * A simple {@link Controller} subclass.
 */
public class Controller${className} extends Controller implements View${className} {

    @Inject
    Provider<Presenter${className}> provider;
    @Presenter
    Presenter${className} presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        // TODO: 2017-07-22 Inject dependencies 
        // App.componentMain().inject(this);
        Presenter${className}_Slick.bind(this);
<#if includeLayout>
        Controller${className}Binding binding = Controller${className}Binding.inflate(inflater, container, false);
        return binding.getRoot();
<#else>
        TextView textView = new TextView(getActivity());
        textView.setText("Hello from Controller${className}");
        return textView;
</#if>
    }       
}
