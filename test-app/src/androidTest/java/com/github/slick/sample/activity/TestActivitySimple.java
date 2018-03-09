package com.github.slick.sample.activity;


import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TestActivitySimple {

    private ExampleActivityPresenter presenter;

    @NonNull
    @Rule
    public ActivityTestRule<ExampleActivity> mActivityTestRule = new ActivityTestRule<>(ExampleActivity.class);

    @Before
    public void setUp() throws Exception {
        presenter = Mockito.spy(mActivityTestRule.getActivity().presenter);
    }

    @Test
    public void testPresenter() {
        InstrumentationRegistry.getInstrumentation().callActivityOnDestroy(mActivityTestRule.getActivity());
        InstrumentationRegistry.getInstrumentation().callActivityOnCreate(mActivityTestRule.getActivity(), null);
        Mockito.verify(presenter, Mockito.only()).onViewUp(mActivityTestRule.getActivity());

/*
        ViewInteraction appCompatButton = onView(
                allOf(ViewMatchers.withId(R.id.activity), withText("Activity"),
                        withParent(allOf(withId(R.id.activity_main),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());
*/

    }

}
