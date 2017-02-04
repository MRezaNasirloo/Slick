package com.github.slick;

import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

public class CallBackPresenterTest {

    @Test
    public void callback() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.CallBackPresenter", ""
                + "package test;\n"
                + "import android.support.annotation.IdRes;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.app.Activity;\n"

                + "@Presenter(Activity.class)\n"
                + "public class CallBackPresenter extends SlickPresenter<SlickView> {\n"

                + "    public CallBackPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");
        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.CallBackPresenter_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import com.github.slick.SlickView;\n"
                + "import java.lang.Override;\n"

                + "public class CallBackPresenter_Slick implements OnDestroyListener {\n"

                + "    private static CallBackPresenter presenterInstance;\n"
                + "    private static CallBackPresenter_Slick hostInstance;\n"
                + "    SlickDelegate<SlickView, CallBackPresenter> delegate = new SlickDelegate();\n"

                + "    public static <T extends Activity & SlickView> CallBackPresenter bind(T activity, @IdRes int i, float f) {\n"
                + "        if (hostInstance == null) hostInstance = new CallBackPresenter_Slick();\n"
                + "        if (presenterInstance == null) presenterInstance = new CallBackPresenter(i, f);\n"
                + "        return hostInstance.setListener(activity);\n"
                + "    }\n"

                + "    private CallBackPresenter setListener(Activity activity) {\n"
                + "        activity.getApplication().registerActivityLifecycleCallbacks(delegate);\n"
                + "        delegate.onCreate(presenterInstance);\n"
                + "        delegate.bind(presenterInstance, activity.getClass());\n"
                + "        delegate.setListener(this);\n"
                + "        return presenterInstance;\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy() {\n"
                + "        presenterInstance = null;\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");

        assertAbout(javaSource()).that(source)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(presenterHostSource);
    }

    @Test
    public void callbackDagger() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.DaggerPresenter", ""
                + "package test;\n"

                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import javax.inject.Inject;\n"

                + "@Presenter(Activity.class)\n"
                + "public class DaggerPresenter extends SlickPresenter<SlickView> {\n"

                + "    @Inject\n"
                + "    public DaggerPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");
        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.DaggerPresenter_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import com.github.slick.SlickView;\n"
                + "import java.lang.Override;\n"

                + "public class DaggerPresenter_Slick implements OnDestroyListener {\n"

                + "    private static DaggerPresenter_Slick hostInstance;\n"
                + "    SlickDelegate<SlickView, DaggerPresenter> delegate = new SlickDelegate();\n"

                + "    public static <T extends Activity & SlickView> void bind(T activity, DaggerPresenter daggerPresenter) {\n"
                + "        if (hostInstance == null) hostInstance = new DaggerPresenter_Slick();\n"
                + "        hostInstance.setListener(activity, daggerPresenter);\n"
                + "    }\n"

                + "    private void setListener(Activity activity, DaggerPresenter daggerPresenter) {\n"
                + "        activity.getApplication().registerActivityLifecycleCallbacks(delegate);\n"
                + "        delegate.bind(daggerPresenter, activity.getClass());\n"
                + "        delegate.setListener(this);\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy() {\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");

        assertAbout(javaSource()).that(source)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(presenterHostSource);
    }



    @Test
    public void fragment() {
        JavaFileObject source = JavaFileObjects.forSourceString("test.FragmentPresenter", ""
                + "package test;\n"
                + "import android.support.annotation.IdRes;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.app.Fragment;\n"

                + "@Presenter(Fragment.class)\n"
                + "public class FragmentPresenter extends SlickPresenter<SlickView> {\n"

                + "    public FragmentPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");
        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.FragmentPresenter_Slick", ""
                + "package test;\n"

                + "import android.app.Fragment;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import com.github.slick.SlickDelegator;\n"
                + "import com.github.slick.SlickView;\n"
                + "import java.lang.Override;\n"

                + "public class FragmentPresenter_Slick implements OnDestroyListener {\n"

                + "    private static FragmentPresenter presenterInstance;\n"
                + "    private static FragmentPresenter_Slick hostInstance;\n"
                + "    SlickDelegate<SlickView, FragmentPresenter> delegate = new SlickDelegate();\n"

                + "    public static <T extends Fragment & SlickView & SlickDelegator> FragmentPresenter bind(T fragment, @IdRes int i, float f) {\n"
                + "        if (hostInstance == null) hostInstance = new FragmentPresenter_Slick();\n"
                + "        if (presenterInstance == null) presenterInstance = new FragmentPresenter(i, f);\n"
                + "        return hostInstance.setListener(fragment);\n"
                + "    }\n"

                + "    private FragmentPresenter setListener(SlickDelegator slickDelegator) {\n"
                + "        slickDelegator.getSlickDelegate().setListener(this);\n"
                + "        return presenterInstance;\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy() {\n"
                + "        presenterInstance = null;\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");

        assertAbout(javaSource()).that(source)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(presenterHostSource);
    }
}
