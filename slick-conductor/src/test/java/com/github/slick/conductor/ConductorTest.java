package com.github.slick.conductor;

import com.github.slick.SlickProcessor;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaFileObject;

import static com.google.common.truth.Truth.assertAbout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2017-02-16
 */

public class ConductorTest {
    @Test
    public void controller() throws Exception {
        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.ExamplePresenter", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "public class ExamplePresenter extends SlickPresenter<SlickView> {\n"

                + "    public ExamplePresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleController", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.annotation.Nullable;\n"
                + "import android.support.annotation.NonNull;\n"
                + "import com.bluelinelabs.conductor.Controller;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.view.LayoutInflater;\n"
                + "import android.view.View;\n"
                + "import android.view.ViewGroup;\n"

                + "public class ExampleController extends Controller implements SlickView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected View onCreateView(@NonNull LayoutInflater inflater, \n"
                + "             @NonNull ViewGroup container) {\n"
                + "        ExamplePresenter_Slick.bind(this, 1, 2.0f);\n"
                + "        return null;\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExamplePresenter_Slick", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"
                + "import com.bluelinelabs.conductor.Controller;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickView;\n"
                + "import com.github.slick.conductor.SlickConductorDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"
                + "import java.util.HashMap;\n"

                + "public class ExamplePresenter_Slick implements OnDestroyListener {\n"

                + "    private static ExamplePresenter_Slick hostInstance;\n"
                + "    private final HashMap<String, SlickConductorDelegate<SlickView, ExamplePresenter>>"
                + "                   delegates = new HashMap<>();\n"

                + "    public static <T extends Controller & SlickView> void bind(T exampleController, @IdRes int i,"
                + "                                                        float f) {\n"
                + "        final String id = exampleController.getInstanceId()\n"
                + "        if (hostInstance == null) hostInstance = new ExamplePresenter_Slick();\n"
                + "        SlickConductorDelegate<SlickView, ExamplePresenter> delegate = hostInstance.delegates.get(id)\n"
                + "        if (delegate == null) {\n"
                + "             final ExamplePresenter presenter = new ExamplePresenter(i, f);\n"
                + "             delegate = new SlickConductorDelegate<>(presenter, exampleController.getClass(), id);\n"
                + "             delegate.setListener(hostInstance);\n"
                + "             hostInstance.delegates.put(id, delegate);\n"
                + "             exampleController.addLifecycleListener(delegate);\n"
                + "        }\n"
                + "        ((ExampleController) exampleController).presenter = delegate.getPresenter();\n"
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
    public void dagger() throws Exception {
        JavaFileObject sourcePresenter = JavaFileObjects.forSourceString("test.ExamplePresenter", ""
                + "package test;\n"

                + "import android.support.annotation.IdRes;\n"

                + "import com.github.slick.SlickPresenter;\n"
                + "import com.github.slick.SlickView;\n"

                + "public class ExamplePresenter extends SlickPresenter<SlickView> {\n"

                + "    public ExamplePresenter(@IdRes int i, float f) {\n"
                + "    }\n"
                + "}");

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleController", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.annotation.Nullable;\n"
                + "import android.support.annotation.NonNull;\n"
                + "import com.bluelinelabs.conductor.Controller;\n"
                + "import com.github.slick.Presenter;\n"
                + "import com.github.slick.SlickView;\n"
                + "import android.view.LayoutInflater;\n"
                + "import android.view.View;\n"
                + "import android.view.ViewGroup;\n"
                + "import javax.inject.Inject;\n"

                + "public class ExampleController extends Controller implements SlickView {\n"

                + "    @Inject\n"
                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected View onCreateView(@NonNull LayoutInflater inflater, \n"
                + "             @NonNull ViewGroup container) {\n"
                + "        ExamplePresenter_Slick.bind(this);\n"
                + "        return null;\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExamplePresenter_Slick", ""
                + "package test;\n"

                + "import com.bluelinelabs.conductor.Controller;\n"
                + "import com.github.slick.OnDestroyListener;\n"
                + "import com.github.slick.SlickView;\n"
                + "import com.github.slick.conductor.SlickConductorDelegate;\n"
                + "import java.lang.Override;\n"
                + "import java.lang.String;\n"

                + "public class ExamplePresenter_Slick implements OnDestroyListener {\n"

                + "    private static ExamplePresenter_Slick hostInstance;\n"
                + "    SlickConductorDelegate<SlickView, ExamplePresenter> delegate;\n"

                + "    public static <T extends Controller & SlickView> void bind(T exampleController) {\n"
                + "        if (hostInstance == null) { \n"
                + "            hostInstance = new ExamplePresenter_Slick();\n"
                + "            ExamplePresenter presenter = ((ExampleController) exampleController).presenter;\n"
                + "            hostInstance.delegate = new SlickConductorDelegate<>(presenter, \n"
                + "                           exampleController.getClass())\n"
                + "            exampleController.addLifecycleListener(hostInstance.delegate);\n"
                + "            hostInstance.delegate.setListener(hostInstance);\n"
                + "        }\n"
                + "    }\n"

                + "    @Override\n"
                + "    public void onDestroy(String id) {\n"
                + "         hostInstance = null;\n"
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
}
