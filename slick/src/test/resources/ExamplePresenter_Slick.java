package test;

import android.app.Activity;
import android.support.annotation.IdRes;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegate;
import com.github.slick.SlickView;

public class ExamplePresenter_Slick implements OnDestroyListener {

    private static ExamplePresenter presenterInstance;
    private static ExamplePresenter_Slick hostInstance;
    SlickDelegate<SlickView, ExamplePresenter> delegate = new SlickDelegate();

    public static <T extends Activity & SlickView> void bind(T activity, @IdRes int i, float f) {
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        if (presenterInstance == null) presenterInstance = new ExamplePresenter(i, f);
        hostInstance.setListener(activity);
    }

    private ExamplePresenter setListener(ExampleActivity activity) {
        activity.examplePresenter = presenterInstance;
        activity.getApplication().registerActivityLifecycleCallbacks(delegate);
        delegate.onCreate(presenterInstance);
        delegate.bind(presenterInstance, activity.getClass());
        delegate.setListener(this);
        return presenterInstance;
    }

    @Override
    public void onDestroy(String id) {
        presenterInstance = null;
        hostInstance = null;
    }
}