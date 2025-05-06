package com.example.m7animedex

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInFragmentTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSignUpButtonVisible() {
        onView(withId(R.id.sign_in_button))
            .check(matches(isDisplayed()))
            .check(matches(isEnabled())) // Verifiquem que està habilitat
    }

    @Test
    fun testEmailInput() {
        onView(withId(R.id.email_input))
            .perform(typeText("test@example.com"), closeSoftKeyboard())
            .check(matches(withText("test@example.com")))
    }

    @Test
    fun testUsernameInput() {
        onView(withId(R.id.username_input))
            .perform(typeText("user123"), closeSoftKeyboard())
            .check(matches(withText("user123")))
    }

    @Test
    fun testPasswordInput() {
        onView(withId(R.id.password_input))
            .perform(typeText("abc12345"), closeSoftKeyboard())
            .check(matches(withText("abc12345")))
    }

    @Test
    fun testRePasswordInput() {
        onView(withId(R.id.rePassword_input))
            .perform(typeText("abc12345"), closeSoftKeyboard())
            .check(matches(withText("abc12345")))
    }

    @Test
    fun testSignUpButtonFunctionality() {
        onView(withId(R.id.email_input)).perform(typeText("test@example.com"), closeSoftKeyboard())
        onView(withId(R.id.username_input)).perform(typeText("user123"), closeSoftKeyboard())
        onView(withId(R.id.password_input)).perform(typeText("abc12345"), closeSoftKeyboard())
        onView(withId(R.id.rePassword_input)).perform(typeText("abc12345"), closeSoftKeyboard())

        onView(withId(R.id.sign_in_button)).perform(click())

        // Aquí afegim la lògica de comprovació del resultat del clic (exemple: si es fa la transició a una nova pantalla o es mostra un missatge d'èxit)
    }

    @Test
    fun testFormulariCompletInvalidPerCorreu() {
        onView(withId(R.id.email_input)).perform(typeText("usuari.gmail.com"), closeSoftKeyboard()) // correu malament
        onView(withId(R.id.username_input)).perform(typeText("user123"), closeSoftKeyboard())
        onView(withId(R.id.password_input)).perform(typeText("abc12345"), closeSoftKeyboard())
        onView(withId(R.id.rePassword_input)).perform(typeText("abc12345"), closeSoftKeyboard())

        onView(withId(R.id.sign_in_button)).perform(click())

        // Verifiquem que el formulari ha fallat i el missatge d'error és correcte
        onView(withId(R.id.email_input)).check(matches(hasErrorText("Format de correu invàlid")))
    }
}
