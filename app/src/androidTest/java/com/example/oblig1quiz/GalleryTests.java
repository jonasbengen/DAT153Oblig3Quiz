package com.example.oblig1quiz;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.contrib.RecyclerViewActions.*;

import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.oblig1quiz.Gallery.AddImageActivity;
import com.example.oblig1quiz.Gallery.GalleryActivity;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class GalleryTests {

    @Rule
    public IntentsTestRule<GalleryActivity> intentsTestRule = new IntentsTestRule<>(GalleryActivity.class);

    @Before
    public void setUp() {
        // Mock the intent for selecting an image
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent().setData(Uri.parse("android.resource://com.example.oblig1quiz/drawable/" + R.drawable.test))));
        //intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent().setData(Uri.parse("https://en.wikipedia.org/wiki/Google_logo#/media/File:Google_2015_logo.png"))));
    }

    @Test
    public void TestAddPhoto() throws InterruptedException {
        RecyclerView view = intentsTestRule.getActivity().findViewById(R.id.recyclerView);

        int beforeCount = view.getAdapter().getItemCount();

        addPhoto();

        TimeUnit.SECONDS.sleep(2);

        int afterCount = view.getAdapter().getItemCount();

        assertEquals(beforeCount + 1, afterCount);

    }
    @Test
    public void TestDeletePhoto() throws InterruptedException {
        RecyclerView view = intentsTestRule.getActivity().findViewById(R.id.recyclerView);

        int beforeCount = view.getAdapter().getItemCount();

        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.deleteButton)));

        onView(withText("Yes")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        int afterCount = view.getAdapter().getItemCount();

        assertEquals(beforeCount - 1, afterCount);
    }

    private void addPhoto() throws InterruptedException {
        onView(withId(R.id.addImageButton)).perform(click());

        onView(withId(R.id.textInputEditText)).perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.selectPhoto)).perform(click());
        onView(withId(R.id.saveButton)).perform(click());

    }


    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public Matcher<View> getConstraints() {
                return null;
            }
        };
    }
}
