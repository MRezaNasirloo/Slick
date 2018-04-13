package test;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.util.SparseArray;
import android.view.View;
import com.mrezanasirloo.slick.InternalOnDestroyListener;
import com.mrezanasirloo.slick.OnDestroyListener;
import com.mrezanasirloo.slick.SlickDelegateView;
import java.lang.Override;
import java.lang.String;

public class ExamplePresenter_Slick implements InternalOnDestroyListener {
    private static ExamplePresenter_Slick hostInstance;

    private final SparseArray<SlickDelegateView<ExampleView, ExamplePresenter>> delegates = new SparseArray<>();

    public static <T extends ExampleCustomView & ExampleView & OnDestroyListener> void bind(T exampleCustomView, @IdRes int i, float f) {
        final int id = SlickDelegateView.getId(exampleCustomView);
        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();
        SlickDelegateView<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id);
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateView<>(presenter, exampleCustomView.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        ((ExampleCustomView) exampleCustomView).presenter = delegate.getPresenter();
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onAttach(T exampleCustomView) {
        hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)).onAttach(exampleCustomView);
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onDetach(T exampleCustomView) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)).onDetach(exampleCustomView);
    }

    public static <T extends View & ExampleView & OnDestroyListener> void onDestroy(T exampleCustomView) {
        if(hostInstance == null || hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)) == null) return;
        // Already has called by its delegate.
        hostInstance.delegates.get(SlickDelegateView.getId(exampleCustomView)).onDestroy(exampleCustomView);
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