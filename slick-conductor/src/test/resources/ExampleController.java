package test;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class ExampleController extends Controller implements ExampleView {

    @Presenter
    ExamplePresenter presenter;

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        ExampleController_Slick.bind(this, 1, 2f);
        return null;
    }
}