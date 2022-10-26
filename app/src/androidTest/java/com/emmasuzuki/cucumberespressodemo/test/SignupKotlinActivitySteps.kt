package com.emmasuzuki.cucumberespressodemo.test

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.rule.ActivityTestRule
import com.emmasuzuki.cucumberespressodemo.R
import com.emmasuzuki.cucumberespressodemo.SignupKotlinActivity
import org.junit.Rule
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import junit.framework.TestCase

class SignupKotlinActivitySteps {
    @Rule
    private val activityTestRule = ActivityTestRule(
        SignupKotlinActivity::class.java
    )

    private var activity: SignupKotlinActivity? = null

    @Before("@signup-feature")
    fun setUp() {
        activityTestRule.launchActivity(Intent())
        activity = activityTestRule.activity
    }

    @After("@signup-feature")
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Given("^I am on sign up screen$")
    fun I_am_on_sign_up_screen() {
        TestCase.assertNotNull(activity)
    }

    @When("^I tap login button$")
    fun I_tap_login_button() {
//        Close the keyboard else the login button is not available for click on the screen
        Espresso.closeSoftKeyboard()
        Espresso.onView(withId(R.id.login)).perform(ViewActions.click())
    }

    @Then("^I should see login screen$")
    fun I_should_see_login_screen() {
        Espresso.onView(withId(R.id.page_title))
            .check(ViewAssertions.matches(withText(R.string.login)))
    }
}
