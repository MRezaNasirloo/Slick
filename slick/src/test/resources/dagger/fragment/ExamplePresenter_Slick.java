package test;

import android.app.Fragment;
import android.util.SparseArray;
import com.github.slick.InternalOnDestroyListener;
import com.github.slick.SlickDelegateFragment;

import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {

    private static ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateFragment<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends Fragment & ExampleView> SlickDelegateFragment<ExampleView, ExamplePresenter> bind(T daggerFragment) {
        final int id = SlickDelegateFragment.getId(daggerFragment);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateFragment<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerFragment) daggerFragment).provider.get();
            delegate = new SlickDelegateFragment<>(presenter, daggerFragment.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((DaggerFragment) daggerFragment).presenter = delegate.getPresenter();
        return delegate;
    }

    public static <T extends Fragment & ExampleView> void onStart(T daggerFragment) {
        hostInstance.delegates.get(SlickDelegateFragment.getId(daggerFragment)).onStart(daggerFragment);
    }
    public static <T extends Fragment & ExampleView> void onStop(T daggerFragment) {
        hostInstance.delegates.get(SlickDelegateFragment.getId(daggerFragment)).onStop(daggerFragment);
    }
    public static <T extends Fragment & ExampleView> void onDestroy(T daggerFragment) {
        hostInstance.delegates.get(SlickDelegateFragment.getId(daggerFragment)).onDestroy(daggerFragment);
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}