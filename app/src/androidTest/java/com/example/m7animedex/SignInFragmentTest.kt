package com.example.m7animedex.res

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.m7animedex.R
import com.example.m7animedex.SignInFragment

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
Elements clau d'Espresso en els tests
onView(withId(...)) → Localitza un element de la UI.
perform(typeText(...)) → Escriu text en un EditText.
perform(click()) → Fa clic en un Button.
check(matches(...)) → Verifica que l'element compleix una condició.
hasErrorText("Missatge d'error") → Comprova si un EditText té un error assignat.
isDisplayed() → Comprova si un element és visible a la pantalla.
 */

@RunWith(AndroidJUnit4::class)
class SignInFragmentUITest {

    @get:Rule
    var activityRule = ActivityScenarioRule(SignInFragment::class.java)

    @Test
    fun testNomUsuariBuit() {
        onView(withId(R.id.username_input)).perform(clearText())
        onView(withId(R.id.sign_in_button)).perform(click())
        onView(withId(R.id.username_input))
            .check(matches(hasErrorText("El nom d'usuari és obligatori")))
    }

    @Test
    fun testEmailInvalid() {
        onView(withId(R.id.username_input)).perform(typeText("usuariValid"))
        onView(withId(R.id.email_input)).perform(typeText("correu.sensearrova"))
        onView(withId(R.id.sign_in_button)).perform(click())
        onView(withId(R.id.email_input))
            .check(matches(hasErrorText("Format de correu invàlid")))
    }

    @Test
    fun testContrasenyaCurta() {
        onView(withId(R.id.username_input)).perform(typeText("usuariValid"))
        onView(withId(R.id.email_input)).perform(typeText("usuari@gmail.com"))
        onView(withId(R.id.password_input)).perform(typeText("abc12"))
        onView(withId(R.id.rePassword_input)).perform(typeText("abc12"))
        onView(withId(R.id.sign_in_button)).perform(click())
        onView(withId(R.id.password_input))
            .check(matches(hasErrorText("Contrasenya massa curta (8-16 caràcters)")))
    }

    @Test
    fun testContrasenyesDiferents() {
        onView(withId(R.id.username_input)).perform(typeText("usuariValid"))
        onView(withId(R.id.email_input)).perform(typeText("usuari@gmail.com"))
        onView(withId(R.id.password_input)).perform(typeText("abc12345"))
        onView(withId(R.id.rePassword_input)).perform(typeText("abc54321"))
        onView(withId(R.id.sign_in_button)).perform(click())
        onView(withId(R.id.rePassword_input))
            .check(matches(hasErrorText("Les contrasenyes no coincideixen")))
    }
}
