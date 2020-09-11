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

public class SkillRecyclerAdapter extends RecyclerView.Adapter<SkillRecyclerAdapter.Viewholder> {

    ArrayList<TopSkill> topSkills;
    public SkillRecyclerAdapter(ArrayList<TopSkill> skill) {
        this.topSkills = skill;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_skill_list, parent, false);
        return new Viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        TopSkill topLearner = topSkills.get(position);
        holder.bind(topLearner);
    }

    @Override
    public int getItemCount() {
        return topSkills.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvScore;
        TextView tvCountry;
        ImageView imageView;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById (R.id.tv_name);
            tvScore = (TextView) itemView.findViewById (R.id.tv_score);
            tvCountry = (TextView) itemView.findViewById (R.id.tv_country);
            imageView = (ImageView) itemView.findViewById(R.id.img_badge);
        }

        public void bind (TopSkill   topSkill) {
            final String WORDS = " skill IQ Score, ";
            String score_statement;
            tvName.setText(topSkill.learnerName);
            score_statement = topSkill.score + WORDS;
            tvScore.setText(score_statement);
            tvCountry.setText(topSkill.learnerCountry);
            Glide.with(itemView.getContext())
                    .load(topSkill.badgeImageUrl)
                    .placeholder(R.drawable.image)
                    .error(R.drawable.error)
                    .into(imageView);
        }
    }
}
