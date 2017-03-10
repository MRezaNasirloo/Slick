package test;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.github.slick.OnDestroyListener;
import com.github.slick.supportfragment.SlickDelegateFragment;

import java.lang.Override;
import java.lang.String;
import java.util.HashMap;

public class ExampleFragment_Slick implements OnDestroyListener {

    private static ExampleFragment_Slick hostInstance;
    private final HashMap<String, SlickDelegateFragment<ExampleView, ExamplePresenter>> delegates = new HashMap<>();

    public static <T extends Fragment & ExampleView> void bind(T exampleFragment, @IdRes
            int i, float f) {
        final String id = SlickDelegateFragment.getId(exampleFragment);
        if (hostInstance == null) hostInstance = new ExampleFragment_Slick();
        SlickDelegateFragment<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)
        if (delegate == null) {
            final ExamplePresenter presenter = new ExamplePresenter(i, f);
            delegate = new SlickDelegateFragment<>(presenter, exampleFragment.getClass(), id);
            delegate.setListener(hostInstance);
            hostInstance.delegates.put(id, delegate);
        }
        exampleFragment.getFragmentManager().registerFragmentLifecycleCallbacks(delegate, false);
        ((ExampleFragment) exampleFragment).presenter = delegate.getPresenter();
    }

    @Override
    public void onDestroy(String id) {
        hostInstance.delegates.remove(id);
        if (hostInstance.delegates.size() == 0) {
            hostInstance = null;
        }
    }
}