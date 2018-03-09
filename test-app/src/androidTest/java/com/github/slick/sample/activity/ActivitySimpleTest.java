package com.github.slick.sample.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import com.github.slick.sample.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.pressBack;
import static junit.framework.Assert.assertEquals;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivitySimpleTest {

    @NonNull
    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testPresenter() {
        MainActivity activity = testRule.getActivity();
        activity.startActivity(new Intent(activity, ActivitySimple.class));
        ActivitySimple activitySimple = (ActivitySimple) getActivity();
        assert activitySimple != null;
        PresenterSimple presenter = activitySimple.presenter;
        assertEquals("onViewUp called on start", 1, presenter.onViewUpCount());
        assertEquals("onViewDown not called on start", 0, presenter.onViewDownCount());
        assertEquals("onDestroy not called on start", 0, presenter.onDestroyCount());

        int hashCode = presenter.hashCode();
        rotateScreen();
        getInstrumentation().waitForIdleSync();
        int hashCodeNew = activitySimple.presenter.hashCode();

        assertEquals("Presenters are the same after screen rotation.", hashCode, hashCodeNew);
        assertEquals("onViewDown called once before rotation", 1, presenter.onViewDownCount());
        assertEquals("onViewUp called after rotation", 2, presenter.onViewUpCount());
        assertEquals("onDestroy not called with screen rotation", 0, presenter.onDestroyCount());

        pressBack();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        assertEquals("onViewDown called after back press", 2, presenter.onViewDownCount());
        assertEquals("onDestroy called once after activity finished", 1, presenter.onDestroyCount());
/*
        ViewInteraction appCompatButton = onView(
                allOf(ViewMatchers.withId(R.id.activity), withText("Activity"),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());
*/

    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = getActivity();
        assert activity != null;
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Nullable
    private Activity getActivity() {
        getInstrumentation().waitForIdleSync();
        final Activity[] activity = new Activity[1];
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                java.util.Collection<Activity> activities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
                activity[0] = Iterables.getOnlyElement(activities);
            }
        });
        return activity[0];
    }

}
