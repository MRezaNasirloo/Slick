package com.github.slick;

import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-01
 */

public class PresenterGeneratorsTest {

    @Test
    public void activity() {
        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.ExamplePresenter", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "public class ExamplePresenter extends SlickPresenter<SlickView> {\n"

                + "    public ExamplePresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleActivity", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.v7.app.AppCompatActivity;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "public class ExampleActivity extends AppCompatActivity implements SlickView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        ExamplePresenter_Slick.bind(this, 1, 2.0f);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExamplePresenter_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import com.github.slick.SlickView;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"
                + "import java.util.HashMap;\n"

                + "public class ExamplePresenter_Slick implements OnDestroyListener {\n"

                + "    private static ExamplePresenter_Slick hostInstance;\n"
                + "    private final HashMap<String, SlickDelegate<SlickView, ExamplePresenter>>"
                + "                   delegates = new HashMap<>();\n"

                + "    public static <T extends Activity & SlickView> void bind(T exampleActivity, @IdRes int i,"
                + "                                                        float f) {\n"
                + "        final String id = SlickDelegate.getActivityId(exampleActivity);\n"
                + "        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();\n"
                + "        SlickDelegate<SlickView, ExamplePresenter> delegate = hostInstance.delegates.get(id)\n"
                + "        if (delegate == null) {\n"
                + "             final ExamplePresenter presenter = new ExamplePresenter(i, f);\n"
                + "             delegate = new SlickDelegate<>(presenter, exampleActivity.getClass(), id);\n"
                + "             delegate.setListener(hostInstance);\n"
                + "             hostInstance.delegates.put(id, delegate);\n"
                + "             exampleActivity.getApplication().registerActivityLifecycleCallbacks(delegate);\n"
                + "        }\n"
                + "        ((ExampleActivity) exampleActivity).presenter = delegate.getPresenter();\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "        hostInstance.delegates.remove(id);\n"
                + "        if (hostInstance.delegates.size() == 0) {\n"
                + "            hostInstance = null;\n"
                + "        }\n"
                + "    }\n"
                + "}");

        final List<JavaFileObject> target = new ArrayList<>(2);
        target.add(sourcePresenter);
        target.add(sourceView);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void activityDagger() {
        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.DaggerPresenter", ""
                + "package test;\n"

                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerPresenter extends SlickPresenter<SlickView> {\n"

                + "    @Inject\n"
                + "    public DaggerPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.DaggerActivity", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.v7.app.AppCompatActivity;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerActivity extends AppCompatActivity implements SlickView {\n"

                + "    @Inject\n"
                + "    @Presenter\n"
                + "    DaggerPresenter presenter;\n"

                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        DaggerPresenter_Slick.bind(this, presenter);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.DaggerPresenter_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import com.github.slick.SlickView;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"


                + "public class DaggerPresenter_Slick implements OnDestroyListener {\n"

                + "    private static DaggerPresenter_Slick hostInstance;\n"
                + "    SlickDelegate<SlickView, DaggerPresenter> delegate = new SlickDelegate<>();\n"

                + "    public static <T extends Activity & SlickView> void bind(T daggerActivity,"
                + "                             DaggerPresenter daggerPresenter) {\n"
                + "        if (hostInstance == null) hostInstance = new DaggerPresenter_Slick();\n"
                + "        daggerActivity.getApplication()"
                + "                 .registerActivityLifecycleCallbacks(hostInstance.delegate);\n"
                + "        hostInstance.delegate.bind(daggerPresenter, daggerActivity.getClass());\n"
                + "        hostInstance.delegate.setListener(hostInstance);\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");
        final List<JavaFileObject> target = new ArrayList<>(2);
        target.add(sourcePresenter);
        target.add(sourceView);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
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

                + "public class FragmentPresenter extends SlickPresenter<SlickView> {\n"

                + "    public FragmentPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");
        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleFragment", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.v4.app.Fragment;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "public class ExampleFragment extends Fragment implements SlickView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        ExamplePresenter_Slick.bind(this, 1, 2.0f);\n"
                + "        super.onCreate(savedInstanceState);\n"
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

                + "    public static <T extends Fragment & SlickView & SlickDelegator> FragmentPresenter bind("
                + "T exampleFragment, @IdRes int i, float f) {\n"
                + "        if (hostInstance == null) hostInstance = new FragmentPresenter_Slick();\n"
                + "        if (presenterInstance == null) presenterInstance = new FragmentPresenter(i, f);\n"
                + "        return hostInstance.setListener(exampleFragment);\n"
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
