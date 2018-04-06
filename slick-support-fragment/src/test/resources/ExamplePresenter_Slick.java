package test;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.supportfragment.SlickDelegateFragment;
import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {

    private static ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateFragment<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends ExampleFragment & ExampleView> void bind(T exampleFragment, @IdRes
            int i, float f) {
        final int id = SlickDelegateFragment.getId(exampleFragment);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateFragment<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateFragment<>(presenter, exampleFragment.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        exampleFragment.getFragmentManager().registerFragmentLifecycleCallbacks(delegate, false);
        ((ExampleFragment) exampleFragment).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}