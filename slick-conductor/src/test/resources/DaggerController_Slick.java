package test;

import com.bluelinelabs.conductor.Controller;
import com.github.slick.OnDestroyListener;
import com.github.slick.conductor.SlickConductorDelegate;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class DaggerController_Slick implements OnDestroyListener {
    private static DaggerController_Slick hostInstance;
    private final HashMap<String, SlickConductorDelegate<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends Controller & ExampleView> void bind(T daggerController) {
        final String id = daggerController.getInstanceId()
        if (hostInstance == null) hostInstance = new DaggerController_Slick();
        SlickConductorDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerController) daggerController).provider.get();
            delegate = new SlickConductorDelegate<>(presenter, daggerController.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            daggerController.addLifecycleListener(delegate);
        }
        ((DaggerController) daggerController).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}