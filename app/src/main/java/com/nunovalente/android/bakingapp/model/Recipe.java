package com.nunovalente.android.bakingapp.model;

import android.content.ContentValues;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.bumptech.glide.annotation.Excludes;

import java.util.List;

@Entity(tableName = "recipes")
public class Recipe {

    /**
     * id : 1
     * name : Nutella Pie
     * ingredients : [{"quantity":2,"measure":"CUP","ingredient":"Graham Cracker crumbs"},{"quantity":6,"measure":"TBLSP","ingredient":"unsalted butter, melted"},{"quantity":0.5,"measure":"CUP","ingredient":"granulated sugar"},{"quantity":1.5,"measure":"TSP","ingredient":"salt"},{"quantity":5,"measure":"TBLSP","ingredient":"vanilla"},{"quantity":1,"measure":"K","ingredient":"Nutella or other chocolate-hazelnut spread"},{"quantity":500,"measure":"G","ingredient":"Mascapone Cheese(room temperature)"},{"quantity":1,"measure":"CUP","ingredient":"heavy cream(cold)"},{"quantity":4,"measure":"OZ","ingredient":"cream cheese(softened)"}]
     * steps : [{"id":0,"shortDescription":"Recipe Introduction","description":"Recipe Introduction","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4","thumbnailURL":""},{"id":1,"shortDescription":"Starting prep","description":"1. Preheat the oven to 350°F. Butter a 9\" deep dish pie pan.","videoURL":"","thumbnailURL":""},{"id":2,"shortDescription":"Prep the cookie crust.","description":"2. Whisk the graham cracker crumbs, 50 grams (1/4 cup) of sugar, and 1/2 teaspoon of salt together in a medium bowl. Pour the melted butter and 1 teaspoon of vanilla into the dry ingredients and stir together until evenly mixed.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4","thumbnailURL":""},{"id":3,"shortDescription":"Press the crust into baking form.","description":"3. Press the cookie crumb mixture into the prepared pie pan and bake for 12 minutes. Let crust cool to room temperature.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9cb_4-press-crumbs-in-pie-plate-creampie/4-press-crumbs-in-pie-plate-creampie.mp4","thumbnailURL":""},{"id":4,"shortDescription":"Start filling prep","description":"4. Beat together the nutella, mascarpone, 1 teaspoon of salt, and 1 tablespoon of vanilla on medium speed in a stand mixer or high speed with a hand mixer until fluffy.","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd97a_1-mix-marscapone-nutella-creampie/1-mix-marscapone-nutella-creampie.mp4","thumbnailURL":""},{"id":5,"shortDescription":"Finish filling prep","description":"5. Beat the cream cheese and 50 grams (1/4 cup) of sugar on medium speed in a stand mixer or high speed with a hand mixer for 3 minutes. Decrease the speed to medium-low and gradually add in the cold cream. Add in 2 teaspoons of vanilla and beat until stiff peaks form.","videoURL":"","thumbnailURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda20_7-add-cream-mix-creampie/7-add-cream-mix-creampie.mp4"},{"id":6,"shortDescription":"Finishing Steps","description":"6. Pour the filling into the prepared crust and smooth the top. Spread the whipped cream over the filling. Refrigerate the pie for at least 2 hours. Then it's ready to serve!","videoURL":"https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffda45_9-add-mixed-nutella-to-crust-creampie/9-add-mixed-nutella-to-crust-creampie.mp4","thumbnailURL":""}]
     * servings : 8
     * image :
     */

    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_SERVINGS = "servings";
    public static final String RECIPE_IMAGE = "image";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String RECIPE_STEPS = "steps";

    @PrimaryKey
    @ColumnInfo(name = RECIPE_ID)
    private int id;

    @ColumnInfo(name = RECIPE_NAME)
    private String name;

    @ColumnInfo(name = RECIPE_SERVINGS)
    private int servings;

    @ColumnInfo(name = RECIPE_IMAGE)
    private String image;

    @ColumnInfo(name = RECIPE_INGREDIENTS)
    private List<Ingredient> ingredients;

    @ColumnInfo(name = RECIPE_STEPS)
    private List<Step> steps;


    public Recipe(int id, String name, int servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    @Ignore
    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }



}
