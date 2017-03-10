package test;

import android.support.annotation.IdRes;
import android.view.View;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegateView;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleCustomView_Slick implements OnDestroyListener {
    private static ExampleCustomView_Slick hostInstance;

    private final HashMap<String, SlickDelegateView<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends View & ExampleView> void bind(T exampleCustomView, @IdRes int i, float f) {
        final String id = SlickDelegateView.getId(exampleCustomView);
        if (hostInstance == null) hostInstance = new ExampleCustomView_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateView<>(presenter, exampleCustomView.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((ExampleCustomView) exampleCustomView).presenter = delegate.getPresenter();
    }

    public static <T extends View & ExampleView> void onAttach(T exampleCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)).onAttach(exampleCustomView);
    }

    public static <T extends View & ExampleView> void onDetach(T exampleCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)).onDetach(exampleCustomView);
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}