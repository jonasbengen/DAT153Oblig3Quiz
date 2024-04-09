package com.example.oblig1quiz;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import com.example.oblig1quiz.Quiz.QuizActivity;
import com.example.oblig1quiz.Util.PhotoInfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class QuizTests {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);


    @Test
    public void TestScoreRightAnswer() throws InterruptedException {
        onView(withId(R.id.quizButton)).perform(click());

        // Get the right answer
        PhotoInfo rightAnswer = QuizActivity.getRightAnswer();

        // Click the button with the right answer
        onView(withText(rightAnswer.getName())).perform(click());

        // Sleep to wait so the question can be answered
        TimeUnit.SECONDS.sleep(4);

        // Check if the scoretext on the screen is as excpected
        onView(withId(R.id.score)).check(matches(withText("Score: 1 / 1")));

    }


    @Test
    public void TestScoreWrongAnswer() throws InterruptedException {
        onView(withId(R.id.quizButton)).perform(click());

        // Get the right answer
        PhotoInfo rightAnswer = QuizActivity.getRightAnswer();

        // Get all the answers on the buttons
        String[] allAnswers = QuizActivity.getAllButtonTexts();

        // Loop to find a button that has a wrong answer
        for (String answer : allAnswers) {
            if (!answer.equals(rightAnswer.getName())) {
                onView(withText(answer)).perform(click());
                break;
            }
        }

        // Sleep to wait so the question can be answered
        TimeUnit.SECONDS.sleep(4);

        // Check if the scoretext on the screen is as excpected
        onView(withId(R.id.score)).check(matches(withText("Score: 0 / 1")));

    }
}
