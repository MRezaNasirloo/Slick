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
        JavaFileObject sourceViewInterface = JavaFileObjects.forSourceString("test.ExampleView", ""
                + "package test;\n"

                + "public interface ExampleView {\n"

                + "}");

        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.ExamplePresenter", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.SlickPresenter;\n"

                + "public class ExamplePresenter extends SlickPresenter<ExampleView> {\n"

                + "    public ExamplePresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleActivity", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.v7.app.AppCompatActivity;\n"
                + "import com.github.slick.Presenter;\n"

                + "public class ExampleActivity extends AppCompatActivity implements ExampleView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        ExampleActivity_Slick.bind(this, 1, 2.0f);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExampleActivity_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"
                + "import java.util.HashMap;\n"

                + "public class ExampleActivity_Slick implements OnDestroyListener {\n"

                + "    private static ExampleActivity_Slick hostInstance;\n"
                + "    private final HashMap<String, SlickDelegate<ExampleView, ExamplePresenter>>"
                + "                   delegates = new HashMap<>();\n"

                + "    public static <T extends Activity & ExampleView> void bind(T exampleActivity, @IdRes int i,"
                + "                                                        float f) {\n"
                + "        final String id = SlickDelegate.getActivityId(exampleActivity);\n"
                + "        if (hostInstance == null) hostInstance = new ExampleActivity_Slick();\n"
                + "        SlickDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)\n"
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

        final List<JavaFileObject> target = new ArrayList<>(3);
        target.add(sourcePresenter);
        target.add(sourceView);
        target.add(sourceViewInterface);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void activityDagger() {

        JavaFileObject sourceViewInterface = JavaFileObjects.forSourceString("test.ExampleView", ""
                + "package test;\n"

                + "public interface ExampleView {\n"

                + "}");

        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.DaggerPresenter", ""
                + "package test;\n"

                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerPresenter extends SlickPresenter<ExampleView> {\n"

                + "    @Inject\n"
                + "    public DaggerPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.DaggerActivity", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.v7.app.AppCompatActivity;\n"
                + "import com.github.slick.Presenter;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerActivity extends AppCompatActivity implements ExampleView {\n"

                + "    @Inject\n"
                + "    @Presenter\n"
                + "    DaggerPresenter presenter;\n"

                + "    @Override\n"
                + "    protected void onCreate(Bundle savedInstanceState) {\n"
                + "        DaggerActivity_Slick.bind(this);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.DaggerActivity_Slick", ""
                + "package test;\n"

                + "import android.app.Activity;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"


                + "public class DaggerActivity_Slick implements OnDestroyListener {\n"

                + "    private static DaggerActivity_Slick hostInstance;\n"
                + "    SlickDelegate<ExampleView, DaggerPresenter> delegate;\n"

                + "    public static <T extends Activity & ExampleView> void bind(T daggerActivity) {\n"
                + "        if (hostInstance == null) { \n"
                + "            hostInstance = new DaggerActivity_Slick();\n"
                + "            DaggerPresenter presenter = ((DaggerActivity) daggerActivity).presenter ;\n"
                + "            hostInstance.delegate = new SlickDelegate<>(presenter, "
                + "                           daggerActivity.getClass())\n"
                + "            daggerActivity.getApplication()"
                + "                .registerActivityLifecycleCallbacks(hostInstance.delegate);\n"
                + "            hostInstance.delegate.setListener(hostInstance);\n"
                + "        }\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");
        final List<JavaFileObject> target = new ArrayList<>(3);
        target.add(sourcePresenter);
        target.add(sourceView);
        target.add(sourceViewInterface);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(presenterHostSource);
    }


    @Test
    public void fragment() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forSourceString("test.ExampleView", ""
                + "package test;\n"

                + "public interface ExampleView {\n"

                + "}");

        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.ExamplePresenter", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"
                + "import com.github.slick.SlickPresenter;\n"

                + "public class ExamplePresenter extends SlickPresenter<ExampleView> {\n"

                + "    public ExamplePresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");
        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleFragment", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                +"import android.support.annotation.Nullable;\n"
               + "import android.app.Fragment;"
                +"import com.github.slick.Presenter;"

                + "public class ExampleFragment extends Fragment implements ExampleView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    public void onCreate(@Nullable Bundle savedInstanceState) {\n"
                + "        ExampleFragment_Slick.bind(this, 1, 2.0f);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExampleFragment_Slick", ""
                + "package test;\n"

                + "import android.app.Fragment;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickFragmentDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"
                + "import java.util.HashMap;\n"

                + "public class ExampleFragment_Slick implements OnDestroyListener {\n"

                + "    private static ExampleFragment_Slick hostInstance;\n"
                + "    private final HashMap<String, SlickFragmentDelegate<ExampleView, ExamplePresenter>>"
                + "                   delegates = new HashMap<>();\n"

                + "    public static <T extends Fragment & ExampleView> SlickFragmentDelegate<ExampleView, ExamplePresenter> bind("
                + "                             T exampleFragment, @IdRes int i, float f) {\n"
                + "        final String id = SlickFragmentDelegate.getFragmentId(exampleFragment);\n"
                + "        if (hostInstance == null) hostInstance = new ExampleFragment_Slick();\n"
                + "        SlickFragmentDelegate<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)\n"
                + "        if (delegate == null) {\n"
                + "             final ExamplePresenter presenter = new ExamplePresenter(i, f);\n"
                + "             delegate = new SlickFragmentDelegate<>(presenter, exampleFragment.getClass(), id);\n"
                + "             delegate.setListener(hostInstance);\n"
                + "             hostInstance.delegates.put(id, delegate);\n"
                + "        }\n"
                + "        ((ExampleFragment) exampleFragment).presenter = delegate.getPresenter();\n"
                + "        return delegate;\n"
                + "    }\n"

                + "    public static <T extends Fragment & ExampleView> void onStart(T exampleFragment) {\n"
                + "        hostInstance.delegates.get(SlickFragmentDelegate.getFragmentId(exampleFragment)).onStart(exampleFragment);\n"
                + "    }\n"
                + "    public static <T extends Fragment & ExampleView> void onStop(T exampleFragment) {\n"
                + "        hostInstance.delegates.get(SlickFragmentDelegate.getFragmentId(exampleFragment)).onStop(exampleFragment);\n"
                + "    }\n"
                + "    public static <T extends Fragment & ExampleView> void onDestroy(T exampleFragment) {\n"
                + "        hostInstance.delegates.get(SlickFragmentDelegate.getFragmentId(exampleFragment)).onDestroy(exampleFragment);\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "        hostInstance.delegates.remove(id);\n"
                + "        if (hostInstance.delegates.size() == 0) {\n"
                + "            hostInstance = null;\n"
                + "}");

        final List<JavaFileObject> target = new ArrayList<>(3);
        target.add(sourcePresenter);
        target.add(sourceView);
        target.add(sourceViewInterface);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }

    @Test
    public void fragmentDagger() {
        JavaFileObject sourceViewInterface = JavaFileObjects.forSourceString("test.DaggerView", ""
                + "package test;\n"


                + "public interface DaggerView {\n"

                + "}");

        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.DaggerPresenter", ""
                + "package test;\n"

                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickPresenter;\n"
                + "import android.app.Activity;\n"
                + "import android.support.annotation.IdRes;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerPresenter extends SlickPresenter<DaggerView> {\n"

                + "    @Inject\n"
                + "    public DaggerPresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.DaggerFragment", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.app.Fragment;\n"
                + "import com.github.slick.Presenter;\n"

                + "import javax.inject.Inject;\n"

                + "public class DaggerFragment extends Fragment implements DaggerView {\n"

                + "    @Inject\n"
                + "    @Presenter\n"
                + "    DaggerPresenter presenter;\n"

                + "    @Override\n"
                + "    public void onCreate(Bundle savedInstanceState) {\n"
                + "        DaggerFragment_Slick.bind(this);\n"
                + "        super.onCreate(savedInstanceState);\n"
                + "    }\n"
                + "}");

        JavaFileObject presenterHostSource = JavaFileObjects.forSourceString("test.DaggerFragment_Slick", ""
                + "package test;\n"

                + "import android.app.Fragment;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickFragmentDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"


                + "public class DaggerFragment_Slick implements OnDestroyListener {\n"

                + "    private static DaggerFragment_Slick hostInstance;\n"
                + "    SlickFragmentDelegate<DaggerView, DaggerPresenter> delegate;\n"

                + "    public static <T extends Fragment & DaggerView> "
                + "                 SlickFragmentDelegate<DaggerView, DaggerPresenter>bind(T daggerFragment) {\n"
                + "        if (hostInstance == null) { \n"
                + "            hostInstance = new DaggerFragment_Slick();\n"
                + "            DaggerPresenter presenter = ((DaggerFragment) daggerFragment).presenter ;\n"
                + "            hostInstance.delegate = new SlickFragmentDelegate<>(presenter, "
                + "                           daggerFragment.getClass())\n"
                + "            hostInstance.delegate.setListener(hostInstance);\n"
                + "        }\n" +
                "           return hostInstance.delegate;"
                + "    }\n"

                + "    public static <T extends Fragment & DaggerView> void onStart(T daggerFragment) {\n"
                + "        hostInstance.delegate.onStart(daggerFragment);\n"
                + "    }\n"
                + "    public static <T extends Fragment & DaggerView> void onStop(T daggerFragment) {\n"
                + "        hostInstance.delegate.onStop(daggerFragment);\n"
                + "    }\n"
                + "    public static <T extends Fragment & DaggerView> void onDestroy(T daggerFragment) {\n"
                + "        hostInstance.delegate.onDestroy(daggerFragment);\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "        hostInstance = null;\n"
                + "    }\n"
                + "}");
        final List<JavaFileObject> target = new ArrayList<>(3);
        target.add(sourcePresenter);
        target.add(sourceView);
        target.add(sourceViewInterface);
        assertAbout(JavaSourcesSubjectFactory.javaSources()).that(target)
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(presenterHostSource);
    }
}
