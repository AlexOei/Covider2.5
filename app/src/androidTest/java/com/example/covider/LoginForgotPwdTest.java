package com.example.covider;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class LoginForgotPwdTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule
            = new ActivityScenarioRule<>(Login.class);

    @Test
    // when forgot password is clicked, show reset window
    public void forgotPwd1() {
        onView(withId(R.id.forgotPassword)).perform(click());
        onView(withText("Reset Password?")).check(matches(isDisplayed()));
    }

    @Test
    // when user doesn't fill in reset email and click "Yes", does nothing and return to login screen
    public void forgotPwd2() {
        onView(withId(R.id.forgotPassword)).perform(click());
        onView(withText("Yes")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("Forgot Password?")).check(matches(isDisplayed()));
    }

    @Test
    // when user clicks "No", does nothing and return to login screen
    public void forgotPwd3() {
        onView(withId(R.id.forgotPassword)).perform(click());
        onView(withText("No")).inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());
        onView(withText("Forgot Password?")).check(matches(isDisplayed()));
    }
}