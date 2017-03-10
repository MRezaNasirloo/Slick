package com.github.slick.conductor;

import com.github.slick.SlickProcessor;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

        JavaFileObject sourceView = JavaFileObjects.forSourceString("test.ExampleController", ""
                + "package test;\n"

                + "import android.os.Bundle;\n"
                + "import android.support.annotation.Nullable;\n"
                + "import android.support.annotation.NonNull;\n"
                + "import com.bluelinelabs.conductor.Controller;\n"
                + "import com.github.slick.Presenter;\n"
                + "import android.view.LayoutInflater;\n"
                + "import android.view.View;\n"
                + "import android.view.ViewGroup;\n"

                + "public class ExampleController extends Controller implements ExampleView {\n"

                + "    @Presenter\n"
                + "    ExamplePresenter presenter;\n"

                + "    @Override\n"
                + "    protected View onCreateView(@NonNull LayoutInflater inflater, \n"
                + "             @NonNull ViewGroup container) {\n"
                + "        ExampleController_Slick.bind(this, 1, 2.0f);\n"
                + "        return null;\n"
                + "    }\n"
                + "}");

        JavaFileObject genSource = JavaFileObjects.forSourceString("test.ExampleController_Slick", ""
                +
                "package test;\n"

                +
                "import android.support.annotation.IdRes;\n"
                +
                "import com.bluelinelabs.conductor.Controller;\n"
                +
                "import com.github.slick.OnDestroyListener;\n"
                +
                "import com.github.slick.conductor.SlickDelegateConductor;\n"
                +
                "import java.lang.Override;\n"
                +
                "import java.lang.String;\n"
                +
                "import java.util.HashMap;\n"

                +
                "public class ExampleController_Slick implements OnDestroyListener {\n"

                +
                "    private static ExampleController_Slick hostInstance;\n"
                +
                "    private final HashMap<String, SlickDelegateConductor<ExampleView, ExamplePresenter>>"
                +
                "                   delegates = new HashMap<>();\n"

                +
                "    public static <T extends Controller & ExampleView> void bind(T exampleController, @IdRes int i,"
                +
                "                                                        float f) {\n"
                +
                "        final String id = exampleController.getInstanceId()\n"
                +
                "        if (hostInstance == null) hostInstance = new ExampleController_Slick();\n"
                +
                "        SlickDelegateConductor<ExampleView, ExamplePresenter> delegate = hostInstance.delegates.get(id)\n"
                +
                "        if (delegate == null) {\n"
                +
                "             final ExamplePresenter presenter = new ExamplePresenter(i, f);\n"
                +
                "             delegate = new SlickDelegateConductor<>(presenter, exampleController.getClass(), id);\n"
                +
                "             delegate.setListener(hostInstance);\n"
                +
                "             hostInstance.delegates.put(id, delegate);\n"
                +
                "             exampleController.addLifecycleListener(delegate);\n"
                +
                "        }\n"
                +
                "        ((ExampleController) exampleController).presenter = delegate.getPresenter();\n"
                +
                "    }\n"

                +
                "    @Override\n"
                +
                "    public void onDestroy(String id) {\n"
                +
                "        hostInstance.delegates.remove(id);\n"
                +
                "        if (hostInstance.delegates.size() == 0) {\n"
                +
                "            hostInstance = null;\n"
                +
                "        }\n"
                +
                "    }\n"
                +
                "}");

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
    public void conductorDagger() {

        JavaFileObject sourceViewInterface = JavaFileObjects.forResource("resources/ExampleView.java");
        JavaFileObject sourcePresenter = JavaFileObjects.forResource("resources/ExamplePresenter.java");
        JavaFileObject sourceView = JavaFileObjects.forResource("resources/DaggerController.java");
        JavaFileObject genSource = JavaFileObjects.forResource("resources/DaggerController_Slick.java");

        assertAbout(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(sourceViewInterface, sourcePresenter, sourceView))
                .processedWith(new SlickProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(genSource);
    }
}
