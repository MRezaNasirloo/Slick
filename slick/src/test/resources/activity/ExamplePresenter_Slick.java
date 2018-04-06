package test;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.SlickDelegateActivity;
import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {

    private static ExamplePresenter_Slick hostInstance;
    private final SparseArray<SlickDelegateActivity<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends ExampleActivity & ExampleView> void bind(T exampleActivity, @IdRes int i, float f) {
        final int id = SlickDelegateActivity.getId(exampleActivity);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateActivity<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateActivity<>(presenter, exampleActivity.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            exampleActivity.getApplication().registerActivityLifecycleCallbacks(delegate);
        }
        ((ExampleActivity) exampleActivity).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}