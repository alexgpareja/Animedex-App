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

    @Test
    fun testUsernameValidationEmpty() {
        onView(withId(R.id.username_input)).perform(clearText()) // Borrar el campo
        onView(withId(R.id.sign_in_button)).perform(click())

        // Verificamos que el mensaje de error es el correcto
        onView(withId(R.id.username_input)).check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

    @Test
    fun testUsernameValidationShort() {
        onView(withId(R.id.username_input)).perform(typeText("us"), closeSoftKeyboard()) // Nombre demasiado corto
        onView(withId(R.id.sign_in_button)).perform(click())

        // Verificamos que el mensaje de error es el correcto
        onView(withId(R.id.username_input)).check(matches(hasErrorText("Ha de tenir almenys 3 caràcters")))
    }

    @Test
    fun testUsernameValidationLong() {
        onView(withId(R.id.username_input)).perform(typeText("userwithaverylongusername123"), closeSoftKeyboard()) // Nombre demasiado largo
        onView(withId(R.id.sign_in_button)).perform(click())

        // Verificamos que el mensaje de error es el correcto
        onView(withId(R.id.username_input)).check(matches(hasErrorText("No pot tenir més de 20 caràcters")))
    }

    @Test
    fun testSignInFormulariCompletValid() {
        // Omple tots els camps amb dades vàlides
        onView(withId(R.id.username_input)).perform(typeText("usuariValid"), closeSoftKeyboard())
        onView(withId(R.id.email_input)).perform(typeText("valid@example.com"), closeSoftKeyboard())
        onView(withId(R.id.password_input)).perform(typeText("abc12345"), closeSoftKeyboard())
        onView(withId(R.id.rePassword_input)).perform(typeText("abc12345"), closeSoftKeyboard())

        // Prem el botó de sign in
        onView(withId(R.id.sign_in_button)).perform(click())

        // Comprova que el fragment Home s'ha carregat verificant la presència del títol "TOP AIRING"
        onView(withId(R.id.topAiringTitle)).check(matches(isDisplayed()))

    }


}
