package test;

import android.support.v4.app.Fragment;
import android.util.SparseArray;
import com.github.slick.InternalOnDestroyListener;
import com.github.slick.supportfragment.SlickDelegateFragment;
import java.lang.Override;

public class DaggerFragment_Slick implements InternalOnDestroyListener {

    private static test.ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateFragment<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends Fragment & ExampleView> void bind(T daggerFragment) {
        final int id = SlickDelegateFragment.getId(daggerFragment);
        if (hostInstance == null) hostInstance = new test.ExamplePresenter_Slick();
        SlickDelegateFragment<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerFragment) daggerFragment).provider.get();
            delegate = new SlickDelegateFragment<>(presenter, daggerFragment.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        daggerFragment.getFragmentManager().registerFragmentLifecycleCallbacks(delegate, false);
        ((DaggerFragment) daggerFragment).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}