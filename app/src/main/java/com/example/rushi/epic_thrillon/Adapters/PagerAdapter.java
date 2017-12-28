package com.example.rushi.epic_thrillon.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.rushi.epic_thrillon.Fragments.Completed;
import com.example.rushi.epic_thrillon.Fragments.Upcoming;

/**
 * Created by rushi on 12/27/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    @Override
    public int getCount() {
        return mNoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {

            case 0:
                Upcoming upcoming=new Upcoming();
                return upcoming;
            case 1:
                Completed completed=new Completed();
                return completed;
            default:
                return null;
        }
    }
}
