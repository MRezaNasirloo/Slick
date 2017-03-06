package test;

import android.app.Activity;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegate;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class MultiInstanceDaggerActivity_Slick implements OnDestroyListener {

    private static MultiInstanceDaggerActivity_Slick hostInstance;
    private final HashMap<String, SlickDelegate<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends Activity & ExampleView> void bind(T multiInstanceDaggerActivity) {
        final String id = SlickDelegate.getActivityId(multiInstanceDaggerActivity);
        if (hostInstance == null) hostInstance = new MultiInstanceDaggerActivity_Slick();
        SlickDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter =
                    ((MultiInstanceDaggerActivity) multiInstanceDaggerActivity).provider.get();
            delegate = new SlickDelegate<>(presenter, multiInstanceDaggerActivity.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            multiInstanceDaggerActivity.getApplication().registerActivityLifecycleCallbacks(delegate);
        }
        ((MultiInstanceDaggerActivity) multiInstanceDaggerActivity).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}