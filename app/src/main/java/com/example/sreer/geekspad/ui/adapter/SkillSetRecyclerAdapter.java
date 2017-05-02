package com.example.sreer.geekspad.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.model.Skill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kalirajkalimuthu on 4/10/17.
 */

public class SkillSetRecyclerAdapter extends RecyclerView.Adapter<SkillSetRecyclerAdapter.mySkillSetHolder> {

    public List<Skill> skillList;
    public Map<String, Integer> addedSkills = new HashMap<String, Integer>();
    public static class mySkillSetHolder extends RecyclerView.ViewHolder {
        TextView skill_name, level;

        public mySkillSetHolder(View view){
            super(view);
            skill_name = (TextView) view.findViewById(R.id.skill_name);
            level = (TextView) view.findViewById(R.id.skill_level);
        }
    }

    public Skill getSkill(int position) {
        return skillList.get(position);
    }

    public SkillSetRecyclerAdapter(List<Skill> users){
        this.skillList = users;
    }

    @Override
    public SkillSetRecyclerAdapter.mySkillSetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.skill_detail_row, parent, false);

        return new SkillSetRecyclerAdapter.mySkillSetHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SkillSetRecyclerAdapter.mySkillSetHolder holder, int position) {
        Skill skill = skillList.get(position);
        holder.skill_name.setText(skill.skillname);
        holder.level.setText(skill.proficency);
    }

    @Override
    public int getItemCount() {
        return skillList.size();
    }

    public void add(Skill skill) {

        if(addedSkills.get(skill.skillname)!=null) {
            Integer position= addedSkills.get(skill.skillname);
            updateSkill(skill, position);
            return;
        }
        skillList.add(skill);
        notifyItemInserted(skillList.size() - 1);
        addedSkills.put(skill.skillname,skillList.indexOf(skill));
    }

    public void updateSkill(Skill skill, int position){
        Skill updateSkill = skillList.get(position);
        updateSkill.proficency = skill.proficency;
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, skillList.size());
    }
    public void clear(){
        skillList.clear();
        notifyDataSetChanged();
    }

    public void remove(int position){
        skillList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, skillList.size());
    }

}
