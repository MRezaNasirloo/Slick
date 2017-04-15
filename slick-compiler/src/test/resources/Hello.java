package test;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegate;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleActivity_Slick implements OnDestroyListener {
    private static ExampleActivity_Slick hostInstance;

    private final HashMap<String, SlickDelegate<ExampleActivityView, ExampleActivityPresenter>> delegates = new HashMap<>();

    public static <T extends Activity & ExampleActivityView> void bind(T exampleActivity, @IdRes @NonNull Integer integer, String s) {
        final String id = SlickDelegate.getActivityId(exampleActivity);
        if (hostInstance == null) hostInstance = new ExampleActivity_Slick();
        SlickDelegate<ExampleActivityView, ExampleActivityPresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExampleActivityPresenter presenter = new ExampleActivityPresenter(integer, s);
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
