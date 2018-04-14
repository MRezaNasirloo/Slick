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

    public static <T extends MultiView1 & ExampleView & SlickLifecycleListener> void bind(T multiView1) {
        final int id = SlickDelegateView.getId(multiView1);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = ((MultiView1) multiView1).provider.get()
            delegate = new SlickDelegateView<>(presenter, multiView1.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((MultiView1) multiView1).presenter = delegate.getPresenter();
    }

    public static <T extends View & ExampleView & SlickLifecycleListener> void onAttach(T multiView1) {
        hostInstance.delegates.get(SlickDelegateView.getId(multiView1)).onAttach(multiView1);
    }

    public static <T extends View & ExampleView & SlickLifecycleListener> void onDetach(T multiView1) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(multiView1)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(multiView1)).onDetach(multiView1);
    }

    public static <T extends View & ExampleView & SlickLifecycleListener> void onDestroy(T multiView1) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(multiView1)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(multiView1)).onDestroy(multiView1);
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

    public static <T extends MultiView2 & ExampleView & SlickLifecycleListener> void bind(T multiView2) {
        final int id = SlickDelegateView.getId(multiView2);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = ((MultiView2) multiView2).provider.get()
            delegate = new SlickDelegateView<>(presenter, multiView2.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((MultiView2) multiView2).presenter = delegate.getPresenter();
    }
}