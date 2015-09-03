package com.kilr.fizzy.intro;

/**
 * Created by idanakav on 9/3/15.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kilr.fizzy.MainActivity;
import com.kilr.fizzy.R;

/**
 * Created by rancohen on 8/25/15.
 */
public class TourPagerAdapter  extends FragmentStatePagerAdapter {

    public TourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Fragment f = null;
        switch(position){
            case 0:
                f = IntroFragment.newInstance(R.layout.tour_first_layout);
                break;
            case 1:
                f = IntroFragment.newInstance(R.layout.tour_mockup_layout);
                break;
            case 2:
                f = IntroFragment.newInstance(R.layout.tour_mockup_layout);
                break;
            case 3:
                f = IntroFragment.newInstance(R.layout.tour_mockup_layout);
                break;
            case 4:
                f = new LoginFragment();
                break;
        }

        return f;
    }

    @Override
    public int getCount() {
        return  IndicatorController.NUM_PAGES;
    }
}