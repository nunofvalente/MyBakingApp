package com.nunovalente.android.bakingapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nunovalente.android.bakingapp.R;
import com.nunovalente.android.bakingapp.model.Step;

import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.MyStepsViewHolder> {

    private final List<Step> mStepList;
    private final RecyclerClickListener listener;

    public RecipeStepsAdapter(List<Step> mStepList, RecyclerClickListener listener) {
        this.mStepList = mStepList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_adapter_list, parent, false);

        return new MyStepsViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStepsViewHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.mTextShortDescription.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public class MyStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

       final TextView mTextShortDescription;
        final TextView mTextViewDetails;

        public MyStepsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextShortDescription = itemView.findViewById(R.id.tv_step_short_description);
            mTextViewDetails = itemView.findViewById(R.id.tv_recipe_details);


            mTextViewDetails.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            listener.onClick(position);
        }
    }
}
