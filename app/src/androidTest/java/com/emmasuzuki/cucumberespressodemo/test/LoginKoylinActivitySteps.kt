package com.emmasuzuki.cucumberespressodemo.test

import android.app.Activity
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.view.View
import android.widget.EditText
import com.emmasuzuki.cucumberespressodemo.LoginKotlinActivity
import com.emmasuzuki.cucumberespressodemo.R
import cucumber.api.PendingException
import cucumber.api.java.After
import cucumber.api.java.Before
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.internal.matchers.TypeSafeMatcher


class LoginKoylinActivitySteps {
    @Rule
    var activityTestRule = ActivityTestRule(
        LoginKotlinActivity::class.java
    )

    private var activity: Activity? = null

    @Before("@login-feature")
    fun setup() {
        activityTestRule.launchActivity(Intent())
        activity = activityTestRule.activity
    }

    @After("@login-feature")
    fun tearDown() {
        activityTestRule.finishActivity()
    }

    @Given("^I am on login screen")
    fun I_am_on_login_screen() {
        Assert.assertNotNull(activity)
    }

    @When("^I input email (\\S+)$")
    fun I_input_email(email: String?) {
        Espresso.onView(withId(R.id.email)).perform(ViewActions.typeText(email))
    }

    @When("^I input password \"(.*?)\"$")
    fun I_input_password(password: String?) {
        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
    }

    @When("^I press submit button$")
    fun I_press_submit_button() {
        Espresso.onView(withId(R.id.submit)).perform(ViewActions.click())
    }

    @When("^I tap sign up button$")
    fun I_tap_sign_up_button() {
        Espresso.onView(withId(R.id.signup)).perform(ViewActions.click())
    }

    @Then("^I should see error on the (\\S+)$")
    fun I_should_see_error_on_the_editTextView(viewName: String) {
        val viewId: Int = if (viewName == "email") R.id.email else R.id.password
        val messageId: Int =
            if (viewName == "email") R.string.msg_email_error else R.string.msg_password_error
        Espresso.onView(ViewMatchers.withId(viewId)).check(
            ViewAssertions.matches(
                hasErrorText(
                    activity!!.getString(messageId)
                )
            )
        )
    }

    @Then("^I should (true|false) auth error$")
    fun I_should_see_auth_error(shouldSeeError: Boolean) {
        if (shouldSeeError) {
            Espresso.onView(withId(R.id.error))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } else {
            Espresso.onView(withId(R.id.error))
                .check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
        }
    }

    @Then("^I should see sign up screen$")
    fun I_should_see_sign_up_screen() {
        Espresso.onView(withId(R.id.page_title))
            .check(ViewAssertions.matches(withText(R.string.signup)))
    }

    @Then("^I should see user blocked error")
    fun i_should_see_blocked_error() {
        Espresso.onView(withId(R.id.block))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun hasErrorText(expectedError: String): Matcher<in View?> {
        return ErrorTextMatcher(expectedError)
    }

    /**
     * Custom matcher to assert equal EditText.setError();
     */
    private class ErrorTextMatcher(private val mExpectedError: String) :
        TypeSafeMatcher<View?>() {
        override fun matchesSafely(view: View?): Boolean {
            if (view !is EditText) {
                return false
            }
            return mExpectedError == view.error.toString()
        }

        override fun describeTo(description: Description) {
            description.appendText("with error: $mExpectedError")
        }
    }
}
