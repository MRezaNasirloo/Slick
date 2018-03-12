package test;

import android.support.annotation.IdRes;
import android.util.SparseArray;
import com.bluelinelabs.conductor.Controller;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.conductor.SlickDelegateConductor;
import java.lang.Override;

public class PresenterSimple_Slick implements InternalOnDestroyListener {
    private static PresenterSimple_Slick hostInstance;
    private final SparseArray<SlickDelegateConductor<ExampleView, PresenterSimple>> delegates = new SparseArray<>();

    public static <T extends Controller & ExampleView> void bind(T exampleController, @IdRes int i, float f) {
        final int id = exampleController.getInstanceId().hashCode();
        if (hostInstance == null) hostInstance = new PresenterSimple_Slick();
        SlickDelegateConductor<ExampleView, PresenterSimple> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final PresenterSimple presenter = new PresenterSimple(i, f);
            delegate = new SlickDelegateConductor<>(presenter, exampleController.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
            exampleController.addLifecycleListener(delegate);
        }
        ((ExampleController) exampleController).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}