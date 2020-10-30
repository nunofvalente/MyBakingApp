package com.nunovalente.android.bakingapp;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.nunovalente.android.bakingapp.activity.RecipeListActivity;
import com.nunovalente.android.bakingapp.activity.RecipeStepsActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class OpenStepsActivityTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mIntentTest = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void openRecipeStepsTest() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(RecipeStepsActivity.class.getName()));
    }
}
