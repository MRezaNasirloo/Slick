package test;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class DaggerController extends Controller implements ExampleView {

    @Inject
    Provider<PresenterDagger> provider;

    @Presenter
    PresenterDagger presenter;

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        PresenterDagger_Slick.bind(this);
        return null;
    }
}