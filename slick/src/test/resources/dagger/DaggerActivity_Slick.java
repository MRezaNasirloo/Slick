package test;

import android.app.Activity;
import android.util.SparseArray;
import com.github.slick.InternalOnDestroyListener;
import com.github.slick.SlickDelegateActivity;
import java.lang.Override;

public class DaggerActivity_Slick implements InternalOnDestroyListener {

    private static DaggerActivity_Slick hostInstance;
    private final SparseArray<SlickDelegateActivity<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends Activity & ExampleView> void bind(T daggerActivity) {
        final int id = SlickDelegateActivity.getId(daggerActivity);
        if (hostInstance == null) hostInstance = new DaggerActivity_Slick();
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