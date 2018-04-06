package test;

import android.util.SparseArray;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.SlickDelegateActivity;
import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {

    private static ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateActivity<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends DaggerActivity & ExampleView> void bind(T daggerActivity) {
        final int id = SlickDelegateActivity.getId(daggerActivity);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateActivity<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerActivity) daggerActivity).provider.get();
            delegate = new SlickDelegateActivity<>(presenter, daggerActivity.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            daggerActivity.getApplication().registerActivityLifecycleCallbacks(delegate);
        }
        ((DaggerActivity) daggerActivity).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}