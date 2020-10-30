package com.nunovalente.android.bakingapp;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.nunovalente.android.bakingapp.activity.RecipeListActivity;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeClickTest {

    private IdlingResource mIdlingResource;

    @Rule
    public final ActivityScenarioRule<RecipeListActivity> mActivityScenario = new ActivityScenarioRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResource() {
        mActivityScenario.getScenario().onActivity(activity -> mIdlingResource = RecipeListActivity.getIdlingResource());

        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void clickRecipeRecycler() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.recycler_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
