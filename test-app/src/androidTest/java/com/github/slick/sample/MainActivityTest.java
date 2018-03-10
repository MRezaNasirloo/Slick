package com.github.slick.sample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

// @LargeTest
// @RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    // @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // @Test
    public void mainActivityTest2() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.activity_dagger), withText("activity with dagger"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.textView3), withText("delegate alternative"),
                        childAtPosition(
                                allOf(withId(R.id.activity_callback),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("delegate alternative")));

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.conductor), withText("Conductor"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.text_view), withText("Hello from conductor"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.con_controller_container),
                                        0),
                                0),
                        isDisplayed()));
        textView2.check(matches(withText("Hello from conductor")));

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.conductor_dagger), withText("Conductor with dagger"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.text_view), withText("Hello from conductor"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.con_controller_container),
                                        0),
                                0),
                        isDisplayed()));
        textView3.check(matches(withText("Hello from conductor")));

        pressBack();

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragment_delegate), withText("Fragment"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.button_delegate_fragment), withText("Delegate Android Fragment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        pressBack();

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.button_dagger_fragment), withText("Dagger Fragment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton6.perform(click());

        pressBack();

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.button_delegate_dagger_fragment), withText("Delegate Dagger Android Fragment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment),
                                        0),
                                2),
                        isDisplayed()));
        appCompatButton7.perform(click());

        pressBack();

        pressBack();

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.button_support_fragment), withText("Support Fragment"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.button_fragment_support), withText("Support Fragment"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment),
                                        0),
                                1),
                        isDisplayed()));
        appCompatButton9.perform(click());

        pressBack();

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.button_fragment_support_dagger), withText("Support Fragment Dagger"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        pressBack();

        pressBack();

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.button_custom_view), withText("Custom View"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.button_new_ac), withText("Button"),
                        childAtPosition(
                                allOf(withId(R.id.custom_view),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatButton12.perform(click());

        pressBack();

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.button_custom_view_dagger), withText("Dagger Custom View"),
                        childAtPosition(
                                allOf(withId(R.id.activity_main),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()));
        appCompatButton13.perform(click());

        pressBack();

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
