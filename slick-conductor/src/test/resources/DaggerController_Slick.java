package test;

import android.util.SparseArray;
import com.bluelinelabs.conductor.Controller;
import com.github.slick.InternalOnDestroyListener;
import com.github.slick.conductor.SlickDelegateConductor;
import java.lang.Override;


public class DaggerController_Slick implements InternalOnDestroyListener {
    private static DaggerController_Slick hostInstance;
    private final SparseArray<SlickDelegateConductor<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends Controller & ExampleView> void bind(T daggerController) {
        final int id = daggerController.getInstanceId().hashCode();
        if (hostInstance == null) hostInstance = new DaggerController_Slick();
        SlickDelegateConductor<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerController) daggerController).provider.get();
            delegate = new SlickDelegateConductor<>(presenter, daggerController.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            daggerController.addLifecycleListener(delegate);
        }
        ((DaggerController) daggerController).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}