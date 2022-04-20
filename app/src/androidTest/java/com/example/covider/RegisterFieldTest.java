package com.example.covider;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

public class RegisterFieldTest {
    @Rule
    public ActivityScenarioRule<Register> activityScenarioRule
            = new ActivityScenarioRule<>(Register.class);

    @Test
    // error message if user doesn't fill in first name
    public void validateFirstNameInput() {
        onView(withId(R.id.et_firstname)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.et_firstname)).check(matches(hasErrorText("First Name is required")));
    }

    @Test
    // error message if user doesn't fill in last name
    public void validateLastNameInput() {
        onView(withId(R.id.et_firstname)).perform(typeText("Xiao"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lastname)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email)).perform(typeText("xiaotan@usc.edu"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("000000"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.et_lastname)).check(matches(hasErrorText("Last Name is required")));
    }

    @Test
    // error message if user doesn't fill in email
    public void validateEmailInput() {
        onView(withId(R.id.et_firstname)).perform(typeText("Xiao"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lastname)).perform(typeText("Tan"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("000000"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.et_email)).check(matches(hasErrorText("USC email is required")));
    }

    @Test
    // error message if user doesn't fill in password
    public void validatePasswordInput1() {
        onView(withId(R.id.et_firstname)).perform(typeText("Xiao"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lastname)).perform(typeText("Tan"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email)).perform(typeText("xiaotan@usc.edu"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.et_password)).check(matches(hasErrorText("Password is required")));
    }

    @Test
    // error message if password is shorter than 6 digits
    public void validatePasswordInput2() {
        onView(withId(R.id.et_firstname)).perform(typeText("Xiao"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_lastname)).perform(typeText("Tan"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_email)).perform(typeText("xiaotan@usc.edu"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btn_register)).perform(click());
        onView(withId(R.id.et_password)).check(matches(hasErrorText("Password must be 6 or more characters")));
    }
}