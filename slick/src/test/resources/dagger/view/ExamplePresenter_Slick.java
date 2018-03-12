package test;

import android.util.SparseArray;
import android.view.View;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.OnDestroyListener;
import com.mrezanasirloo.slick.SlickDelegateView;
import java.lang.Override;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {
    private static ExamplePresenter_Slick hostInstance;

    private final SparseArray<SlickDelegateView<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends View & ExampleView & OnDestroyListener> void bind(T daggerCustomView) {
        final int id = SlickDelegateView.getId(daggerCustomView);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = ((DaggerCustomView) daggerCustomView).provider.get()
            delegate = new SlickDelegateView<>(presenter, daggerCustomView.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((DaggerCustomView) daggerCustomView).presenter = delegate.getPresenter();
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onAttach(T daggerCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onAttach(daggerCustomView);
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onDetach(T daggerCustomView) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onDetach(daggerCustomView);
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onDestroy(T daggerCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onDestroy(daggerCustomView);
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}