package test;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.mrezanasirloo.slick.Presenter;

import javax.inject.Inject;
import javax.inject.Provider;

public class DaggerFragment extends Fragment implements ExampleView {

    @Inject
    Provider<ExamplePresenter> provider;
    @Presenter
    ExamplePresenter presenter;

    @Override
    public void onAttach(Context context) {
        ExamplePresenter_Slick.bind(this);
        super.onAttach(context);
    }
}