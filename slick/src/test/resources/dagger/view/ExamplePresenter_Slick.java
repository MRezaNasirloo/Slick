package test;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.SlickDelegateView;
import com.mrezanasirloo.slick.SlickLifecycleListener;

import java.lang.Override;
import java.lang.String;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {
    private static ExamplePresenter_Slick hostInstance;

    private final SparseArray<SlickDelegateView<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends DaggerCustomView & ExampleView & SlickLifecycleListener> void bind(T daggerCustomView) {
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

    public static <T extends View & ExampleView & SlickLifecycleListener> void onAttach(T daggerCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onAttach(daggerCustomView);
    }

    public static <T extends View & ExampleView & SlickLifecycleListener> void onDetach(T daggerCustomView) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onDetach(daggerCustomView);
    }

    public static <T extends View & ExampleView & SlickLifecycleListener> void onDestroy(T daggerCustomView) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(daggerCustomView)).onDestroy(daggerCustomView);
    }

    public static void onDestroy(String uniqueId, Activity activity) {
        if (hostInstance == null || hostInstance.delegates.get(uniqueId.hashCode()) == null) return;
        // Either has not bound or already has destroyed.
        hostInstance.delegates.get(uniqueId.hashCode()).onDestroy(activity);
    }

    public static void onDestroy(String uniqueId, View view) {
        onDestroy(uniqueId, SlickDelegateView.getActivity(view));
    }

    @Override
    public void onDestroy(int id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}