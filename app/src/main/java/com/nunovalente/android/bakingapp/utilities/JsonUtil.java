package com.nunovalente.android.bakingapp.utilities;

import com.nunovalente.android.bakingapp.model.Ingredient;
import com.nunovalente.android.bakingapp.model.Recipe;
import com.nunovalente.android.bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtil {

    public static ArrayList<Recipe> parseJSON(String recipes) throws JSONException {
        ArrayList<Recipe> mRecipeList = new ArrayList<>();
        JSONArray array = new JSONArray(recipes);

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            int servings = jsonObject.getInt("servings");
            String image = jsonObject.getString("image");
            ArrayList<Ingredient> ingredients = parseIngredients(jsonObject);
            ArrayList<Step> steps = parseSteps(jsonObject);
            mRecipeList.add(new Recipe(id, name, servings, image, ingredients, steps));
        }
        return mRecipeList;
    }

    public static ArrayList<Ingredient> parseIngredients(JSONObject object) throws JSONException {
        ArrayList<Ingredient> mIngredientsList = new ArrayList<>();
        JSONArray arrayIngredients = object.getJSONArray("ingredients");

        for (int i =0; i < arrayIngredients.length(); i++) {
            JSONObject currentIngredients = new JSONObject();
            int quantity = currentIngredients.getInt("quantity");
            String measure = currentIngredients.getString("measure");
            String ingredient = currentIngredients.getString("ingredient");
            mIngredientsList.add(new Ingredient(quantity, measure, ingredient));
        }

        return  mIngredientsList;
    }

    public static ArrayList<Step> parseSteps(JSONObject object) throws JSONException {
        ArrayList<Step> mStepList = new ArrayList<>();
        JSONArray arraySteps = object.getJSONArray("steps");

        for (int i =0; i < arraySteps.length(); i++) {
            JSONObject currentStep = new JSONObject();
            int id = currentStep.getInt("id");
            String shortDescription = currentStep.getString("shortDescription");
            String description = currentStep.getString("description");
            String videoURL = currentStep.getString("videoURL");
            String thumbnailURL = currentStep.getString("thumbnailURL");
            mStepList.add(new Step(id, shortDescription , description, videoURL, thumbnailURL ));
        }

        return  mStepList;
    }
}
