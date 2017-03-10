package test;

import android.support.annotation.IdRes;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.OnDestroyListener;
import com.github.slick.conductor.SlickDelegateConductor;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleController_Slick implements OnDestroyListener {
    private static ExampleController_Slick hostInstance;
    private final HashMap<String, SlickDelegateConductor<ExampleView, ExamplePresenter>> delegates =
            new HashMap<>();

    public static <T extends Controller & ExampleView> void bind(T exampleController, @IdRes int i, float f) {
        final String id = exampleController.getInstanceId();
        if (hostInstance == null) hostInstance = new ExampleController_Slick();
        SlickDelegateConductor<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateConductor<>(presenter, exampleController.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            exampleController.addLifecycleListener(delegate);
        }
        ((ExampleController) exampleController).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}