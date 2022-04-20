package com.example.covider;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.support.annotation.NonNull;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.Executor;

public class LoginFieldTest {
    @Rule
    public ActivityScenarioRule<Login> activityScenarioRule
            = new ActivityScenarioRule<>(Login.class);

    @Test
    //
    public void validateEmailLogIn() {
        onView(withId(R.id.et_lemail)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lpassword)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_llogin)).perform(click());
        onView(withId(R.id.et_lemail)).check(matches(hasErrorText("USC email is required")));
    }

    @Test
    public void validatePasswordLogIn1() {
        onView(withId(R.id.et_lemail)).perform(typeText("xiaotan@usc.edu"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lpassword)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_llogin)).perform(click());
        onView(withId(R.id.et_lpassword)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    public void validatePasswordLogIn2() {
        onView(withId(R.id.et_lemail)).perform(typeText("xiaotan"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lpassword)).perform(typeText("000000"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_llogin)).perform(click());
        onView(withId(R.id.et_lemail)).check(matches(hasErrorText("Provide a valid USC email!")));
    }
}