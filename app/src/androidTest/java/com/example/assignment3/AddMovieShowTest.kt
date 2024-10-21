package com.example.assignment3

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.assignment3.MainActivity
import com.example.assignment3.R
import org.hamcrest.CoreMatchers.anything
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddMovieShowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addNewMovieShow_inputFields_shouldDisplayCorrectly() {
        // Navigate to Add Movie/Show screen
        onView(withId(R.id.fab_add_movie_show)).perform(click())

        // Check if all input fields are displayed
        onView(withId(R.id.editTextTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerType)).check(matches(isDisplayed()))
        onView(withId(R.id.editTextGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.editTextReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.spinnerStatus)).check(matches(isDisplayed()))
        onView(withId(R.id.ratingBar)).check(matches(isDisplayed()))
        onView(withId(R.id.editTextNotes)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonSave)).check(matches(isDisplayed()))
    }

    //    fun addNewMovieShow_withValidInput_shouldAddToList() {
//        // Navigate to Add Movie/Show screen
//        onView(withId(R.id.fab_add_movie_show)).perform(click())
//
//        // Input valid data
//        onView(withId(R.id.editTextTitle)).perform(typeText("Test Movie"), closeSoftKeyboard())
//        onView(withId(R.id.spinnerType)).perform(click())
//        onData(anything()).atPosition(0).perform(click()) // Select first item in spinner
//        onView(withId(R.id.editTextGenre)).perform(typeText("Action"), closeSoftKeyboard())
//        onView(withId(R.id.editTextReleaseDate)).perform(typeText("2023"), closeSoftKeyboard())
//        onView(withId(R.id.spinnerStatus)).perform(click())
//        onData(anything()).atPosition(0).perform(click()) // Select first item in spinner
//        onView(withId(R.id.ratingBar)).perform(click())
//        onView(withId(R.id.editTextNotes)).perform(typeText("Test note"), closeSoftKeyboard())
//
//        // Save the new movie/show
//        onView(withId(R.id.buttonSave)).perform(click())
//
//        // Check if we're back on the list screen and the new item is visible
//        onView(withId(R.id.movie_show_list)).check(matches(isDisplayed()))
//        onView(withText("Test Movie")).check(matches(isDisplayed()))
//    }
}