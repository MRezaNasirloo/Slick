package test;

import android.app.Fragment;
import android.support.annotation.IdRes;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickFragmentDelegate;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleFragment_Slick implements OnDestroyListener {

    private static ExampleFragment_Slick hostInstance;
    private final HashMap<String, SlickFragmentDelegate<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends Fragment & ExampleView> SlickFragmentDelegate<ExampleView, ExamplePresenter> bind(T exampleFragment, @IdRes
            int i, float f) {
        final String id = SlickFragmentDelegate.getId(exampleFragment);
        if (hostInstance == null) hostInstance = new ExampleFragment_Slick();
        SlickFragmentDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickFragmentDelegate<>(presenter, exampleFragment.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((ExampleFragment) exampleFragment).presenter = delegate.getPresenter();
        return delegate;
    }

    public static <T extends Fragment & ExampleView> void onStart(T exampleFragment) {
        hostInstance.delegates.get(SlickFragmentDelegate.getId(exampleFragment)).onStart(exampleFragment);
    }
    public static <T extends Fragment & ExampleView> void onStop(T exampleFragment) {
        hostInstance.delegates.get(SlickFragmentDelegate.getId(exampleFragment)).onStop(exampleFragment);
    }
    public static <T extends Fragment & ExampleView> void onDestroy(T exampleFragment) {
        hostInstance.delegates.get(SlickFragmentDelegate.getId(exampleFragment)).onDestroy(exampleFragment);
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}