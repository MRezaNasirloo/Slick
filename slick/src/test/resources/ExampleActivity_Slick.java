package test;

import android.app.Activity;
import android.support.annotation.IdRes;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegateActivity;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleActivity_Slick implements OnDestroyListener {

    private static ExampleActivity_Slick hostInstance;
    private final HashMap<String, SlickDelegateActivity<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends Activity & ExampleView> void bind(T exampleActivity, @IdRes int i, float f) {
        final String id = SlickDelegateActivity.getId(exampleActivity);
        if (hostInstance == null) hostInstance = new ExampleActivity_Slick();
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
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}