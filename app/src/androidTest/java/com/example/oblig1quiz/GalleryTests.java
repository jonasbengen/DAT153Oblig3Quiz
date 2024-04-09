package com.example.oblig1quiz;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.intent.Intents.intended;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.oblig1quiz.Gallery.GalleryActivity;

import org.hamcrest.Matcher;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class GalleryTests {

    @Rule
    public IntentsTestRule<GalleryActivity> intentsTestRule = new IntentsTestRule<>(GalleryActivity.class);

    // Test for deleting a photo
    @Test
    public void TestDeletePhoto() throws InterruptedException {
        // Find the recyclerview
        RecyclerView view = intentsTestRule.getActivity().findViewById(R.id.recyclerView);

        // Get the count of items in the recyclerview before deleting
        int beforeCount = view.getAdapter().getItemCount();

        // Perform the action of clicking the delete button on the first item in the recyclerview
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.deleteButton)));

        // Click on "YES" in the alert dialog that pops up
        onView(withText("YES")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        // Get the count of items in the recyclerview after deleting
        int afterCount = view.getAdapter().getItemCount();

        // Check if the count of items in the recyclerview is as expected
        assertEquals(afterCount, beforeCount - 1);
    }

    // Test for adding a photo
    @Test
    public void TestAddPhoto() throws Exception {
        // Get the recyclerview
        RecyclerView view = intentsTestRule.getActivity().findViewById(R.id.recyclerView);

        // Get the count of items in the recyclerview before adding a photo
        int beforeCount = view.getAdapter().getItemCount();

        // Perform the action of adding a photo
        addPhoto();

        // Click the back button to go back to the gallery
        onView(withId(R.id.backButton)).perform(click());

        // Get the count of items in the recyclerview after adding a photo
        int afterCount = view.getAdapter().getItemCount();

        // Check if the count of items in the recyclerview is as expected
        assertEquals(afterCount, beforeCount + 1);

    }

    private void addPhoto() throws Exception {
        // Click the add image button
        onView(withId(R.id.addImageButton)).perform(click());

        // Type in text in the description field
        onView(withId(R.id.textInputEditText)).perform(typeText("Test"), closeSoftKeyboard());

        // Mock the photo
        mockPhoto();

        // Click the save button
        onView(withId(R.id.saveButton)).perform(click());
    }

    private void mockPhoto() throws Exception {
        // Create an Intent with the desired image URI
        Uri pictureUri = Uri.parse("android.resource://com.example.oblig1quiz/drawable/" + R.drawable.test);
        Intent resultIntent = new Intent();
        resultIntent.setData(pictureUri);

        // Respond to the intent as if the user has selected an image
        // This will mock when an ACTION_GET_CONTENT intent happens
        intending(hasAction(Intent.ACTION_GET_CONTENT)).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, resultIntent));

        // Perform the click action to select the photo
        onView(withId(R.id.selectPhoto)).perform(click());

        // Ensure that the intended intent was triggered
        intended(hasAction(Intent.ACTION_GET_CONTENT));

    }

    // Click on a child view with a specified id
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
