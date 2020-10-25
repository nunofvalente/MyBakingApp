package com.nunovalente.android.bakingapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.adapter.RecipeStepsAdapter;
import com.nunovalente.android.bakingapp.adapter.RecyclerClickListener;
import com.nunovalente.android.bakingapp.model.Ingredient;
import com.nunovalente.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeStepsFragment extends Fragment implements RecyclerClickListener {

    private OnStepClickListener mCallback;
    private RecyclerView recyclerSteps;
    private Recipe mRecipe;
    private TextView mIngredientsListText;
    private List<Ingredient> ingredients;

    @Override
    public void onClick(int position) {
        mCallback.onStepSelected(position);
    }

    public interface OnStepClickListener {
        void onStepSelected(int id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepClickListener");
        }
    }

    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_steps, container, false);

        mIngredientsListText = view.findViewById(R.id.tv_ingredient_list);
        recyclerSteps = view.findViewById(R.id.recycler_steps);

        if (getArguments() != null) {
            mRecipe = (Recipe) getArguments().getSerializable(getResources().getString(R.string.RECIPE_STEP));
            assert mRecipe != null;
            ingredients = mRecipe.getIngredients();
        }

        loadIngredientsInfo();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        RecipeStepsAdapter adapter = new RecipeStepsAdapter(mRecipe.getSteps(), this);
        recyclerSteps.setLayoutManager(layoutManager);
        recyclerSteps.setAdapter(adapter);
        recyclerSteps.setHasFixedSize(true);


        return view;
    }

    private void loadIngredientsInfo() {
        int counter = 1;
        mIngredientsListText.setText("");
        for (Ingredient ingredient : ingredients) {
            if (counter == ingredients.size()) {
                mIngredientsListText.append(ingredient.toString());
            } else {
                mIngredientsListText.append(ingredient.toString() + "\n");
                counter++;
            }
        }
    }


}
