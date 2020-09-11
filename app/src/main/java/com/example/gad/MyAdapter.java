package com.example.gad;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context mContext;
    int totalTabs;

    public MyAdapter (Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        mContext = context;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
        case 0:
            return new LearningLeaders();
        case 1:
            return new SkillLeaders();
        default:
            return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
