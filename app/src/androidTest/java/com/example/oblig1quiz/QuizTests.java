package com.example.oblig1quiz;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.oblig1quiz.Quiz.QuizActivity;
import com.example.oblig1quiz.Util.PhotoInfo;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@RunWith(AndroidJUnit4.class)
// This is to make sure the tests run in the right order
// Test are renamed so they run in alphabetical order, starting with a_testActivityLaunch
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuizTests {

    @Rule
    public ActivityScenarioRule<QuizActivity> activityTestRule = new ActivityScenarioRule<>(QuizActivity.class);

    // Test for launching the activity and checking if it is launched,
    // so it is possible to test the other tests
    @Test
    public void a_testActivityLaunch() {
        // Start the activity scenario
        ActivityScenario<QuizActivity> scenario = activityTestRule.getScenario();

        // Check if the activity is launched
        scenario.onActivity(activity -> {
            assertNotNull(activity);
        });

    }

    @Test
    public void b_TestScoreRightAnswer() throws InterruptedException {
        //onView(withId(R.id.quizButton)).perform(click());

        AtomicReference<PhotoInfo> rightAnswer = new AtomicReference<>();
        activityTestRule.getScenario().onActivity(activity -> {
            // Get the right answer
            rightAnswer.set(activity.getRightAnswer());
        });


        // Click the button with the right answer
        onView(withText(rightAnswer.get().getName())).perform(click());

        // Sleep to wait so the question can be answered
        TimeUnit.SECONDS.sleep(4);

        // Check if the scoretext on the screen is as excpected
        onView(withId(R.id.score)).check(matches(withText("Score: 1 / 1")));

    }


    @Test
    public void c_TestScoreWrongAnswer() throws InterruptedException {
        //onView(withId(R.id.quizButton)).perform(click());

        AtomicReference<PhotoInfo> rightAnswer = new AtomicReference<>();
        AtomicReference<String[]> allAnswers = new AtomicReference<>();
        activityTestRule.getScenario().onActivity(activity -> {
            // Get the right answer
            rightAnswer.set(activity.getRightAnswer());

            // Get all the answers on the buttons
            allAnswers.set(activity.getAllButtonTexts());
        });



        // Loop to find a button that has a wrong answer
        for (String answer : allAnswers.get()) {
            if (!answer.equals(rightAnswer.get().getName())) {
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

