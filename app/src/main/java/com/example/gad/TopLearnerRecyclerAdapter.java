package com.example.gad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class TopLearnerRecyclerAdapter extends RecyclerView.Adapter<TopLearnerRecyclerAdapter.Viewholder> {

    ArrayList<TopLearner> topLearners;
    public TopLearnerRecyclerAdapter(ArrayList<TopLearner> toplearners) {
        this.topLearners = toplearners;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_learner_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TopLearner topLearner = topLearners.get(position);
        holder.bind(topLearner);
    }

    @Override
    public int getItemCount() {
        return topLearners.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvLearningHours;
        TextView tvCountry;
        ImageView imageView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById (R.id.tv_name);
            tvLearningHours = (TextView) itemView.findViewById (R.id.tv_learning_hours);
            tvCountry = (TextView) itemView.findViewById (R.id.tv_country);
            imageView = (ImageView) itemView.findViewById(R.id.img_learners_badge);
        }

        public void bind (TopLearner topLearner) {
            final String WORDS = " learning hours, ";
            String learning_statement;
            tvName.setText(topLearner.learnerName);
            learning_statement = topLearner.learningHours + WORDS;
            tvLearningHours.setText(learning_statement);
            tvCountry.setText(topLearner.learnerCountry);
            Glide.with(itemView.getContext())
                    .load(topLearner.badgeImageUrl)
                    .placeholder(R.drawable.image)
                    .error(R.drawable.error)
                    .into(imageView);
        }
    }
}
