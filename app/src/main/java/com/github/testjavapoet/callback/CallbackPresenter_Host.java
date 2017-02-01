package com.github.testjavapoet.callback;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.slick.OnDestroyListener;
import com.github.slick.SlickDelegate;
import com.github.slick.SlickView;
import java.lang.Override;


/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2016-10-31
 *
 * Double Dummy
 */
public class CallbackPresenter_Host implements OnDestroyListener {

    private final SlickDelegate<CallBackView, CallBackPresenter> delegate = new SlickDelegate<>();


    private static final String TAG = CallbackPresenter_Host.class.getSimpleName();
    private static CallBackPresenter presenterInstance;
    private static CallbackPresenter_Host hostInstance;

    // TODO: 2016-11-01 Generate this based on presenter args
    public static <T extends Activity & SlickView>CallBackPresenter bind(T activity, @IdRes @NonNull Integer i, String s) {
        if (hostInstance == null) hostInstance = new CallbackPresenter_Host();
        if (presenterInstance == null) presenterInstance = new CallBackPresenter(i, s);
        return hostInstance.setListener(activity);
    }

    private CallBackPresenter setListener(Activity activity) {
        activity.getApplication().registerActivityLifecycleCallbacks(delegate);
        delegate.onCreate(presenterInstance);
        delegate.bind(presenterInstance, activity.getClass());
        delegate.setListener(this);
        return presenterInstance;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() called");
        presenterInstance = null;
        hostInstance = null;
    }
}
