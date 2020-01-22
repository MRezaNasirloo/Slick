package test;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.mrezanasirloo.slick.Presenter;

public class ExampleFragment extends Fragment implements ExampleView {

    @Presenter
    ExamplePresenter presenter;

    @Override
    public void onAttach(Context context) {
        ExamplePresenter_Slick.bind(this, 1, 2f);
        super.onAttach(context);
    }
}