package test;

import android.util.SparseArray;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.supportfragment.SlickDelegateFragment;
import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {

    private static ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateFragment<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends DaggerFragment & ExampleView> void bind(T daggerFragment) {
        final int id = SlickDelegateFragment.getId(daggerFragment);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
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