package test;

import android.app.Activity;
import android.support.annotation.IdRes;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegate;

import java.util.HashMap;

public class ExampleActivity_Slick implements OnDestroyListener {

    private static ExampleActivity_Slick hostInstance;
    private final HashMap<String, SlickDelegate<ExampleView, ExamplePresenter> delegates = new HashMap<>();

    public static <T extends Activity & ExampleView> void bind(T exampleActivity, @IdRes int ifloat f) {
        final String id = SlickDelegate.getActivityId(exampleActivity);
        if (hostInstance == null) hostInstance = new ExampleActivity_Slick();
        SlickDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegate<>(presenter, exampleActivity.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            exampleActivity.getApplication().registerActivityLifecycleCallbacks(delegate);
        }
        ((ExampleActivity) exampleActivity).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}