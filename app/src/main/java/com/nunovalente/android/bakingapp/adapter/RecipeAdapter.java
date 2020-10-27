package com.nunovalente.android.bakingapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyRecipeViewHolder> {

    private List<Recipe> mRecipeList;
    final private RecyclerClickListener listener;
    final private Context context;

    public RecipeAdapter(Context context, List<Recipe> mRecipeList, RecyclerClickListener listener) {
        this.context = context;
        this.mRecipeList = mRecipeList;
        this.listener = listener;
    }

    public void setValue(List<Recipe> list) {
        this.mRecipeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_adapter_list, parent, false);

        return new MyRecipeViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.mRecipeName.setText(recipe.getName());
        holder.mRecipeServings.setText(String.format("%s%s", context.getString(R.string.servings), recipe.getServings()));

        String image = recipe.getImage();

        if(!image.equals("")) {
            Uri uri = Uri.parse(image);
            Glide.with(context).load(uri).into(holder.mRecipeImage);
        } else {
            holder.mRecipeImage.setImageResource(R.drawable.default_recipe_img);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public class MyRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView mRecipeImage;
        private final TextView mRecipeName, mRecipeServings;

        public MyRecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            mRecipeImage = itemView.findViewById(R.id.image_recipe);
            mRecipeServings = itemView.findViewById(R.id.tv_number_servings);
            mRecipeName = itemView.findViewById(R.id.tv_recipe_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onClick(position);
        }
    }
}
