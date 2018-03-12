package test;

import android.util.SparseArray;
import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.conductor.SlickDelegateConductor;
import java.lang.Override;


public class PresenterDagger_Slick implements InternalOnDestroyListener {
    private static PresenterDagger_Slick hostInstance;
    private final SparseArray<SlickDelegateConductor<ExampleView, PresenterDagger>> delegates = new SparseArray<>();

    public static <T extends Controller & ExampleView> void bind(T daggerController) {
        final int id = daggerController.getInstanceId().hashCode();
        if (hostInstance == null) hostInstance = new PresenterDagger_Slick();
        SlickDelegateConductor<ExampleView, PresenterDagger> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final PresenterDagger presenter = ((DaggerController) daggerController).provider.get();
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