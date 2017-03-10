package test;

import android.view.View;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegateView;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class DaggerCustomView_Slick implements OnDestroyListener {
    private static DaggerCustomView_Slick hostInstance;

    private final HashMap<String, SlickDelegateView<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends View & ExampleView> void bind(T daggerCustomView) {
        final String id = SlickDelegateView.getId(daggerCustomView);
        if (hostInstance == null) hostInstance = new DaggerCustomView_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerCustomView) daggerCustomView).provider.get()
            delegate = new SlickDelegateView<>(presenter, daggerCustomView.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((DaggerCustomView) daggerCustomView).presenter = delegate.getPresenter();
    }

    public static <T extends View & ExampleView> void onAttach(T daggerCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onAttach(daggerCustomView);
    }

    public static <T extends View & ExampleView> void onDetach(T daggerCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onDetach(daggerCustomView);
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}