package com.example.oblig1quiz;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.example.oblig1quiz.Gallery.GalleryActivity;
import com.example.oblig1quiz.Quiz.QuizActivity;

import org.junit.Rule;
import org.junit.Test;

public class MainTests {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);


    // Test for clicking the quiz button and opening the quiz activity
    @Test
    public void TestQuizButton() {
        onView(withId(R.id.quizButton)).perform(click());
        intended(hasComponent(QuizActivity.class.getName()));
    }

    // Test for clicking the gallery button and opening the gallery activity
    @Test
    public void TestGalleryButton() {
        onView(withId(R.id.galleryButton)).perform(click());
        intended(hasComponent(GalleryActivity.class.getName()));
    }
}
