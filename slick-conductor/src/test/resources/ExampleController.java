package test;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.Presenter;

public class ExampleController extends Controller implements ExampleView {

    @Presenter
    PresenterSimple presenter;

    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        PresenterSimple_Slick.bind(this, 1, 2f);
        return null;
    }
}