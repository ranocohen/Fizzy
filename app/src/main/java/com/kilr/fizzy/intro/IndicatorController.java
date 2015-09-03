package com.kilr.fizzy.intro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kilr.fizzy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idanakav on 9/3/15.
 */
public class IndicatorController {


    public static final int NUM_PAGES = 5;
    private Context mContext;
    private LinearLayout mDotLayout;
    private List<ImageView> mDots;
    private int mSlideCount;

    private static final int FIRST_PAGE_NUM = 0;

    public View newInstance(@NonNull Context context) {
        mContext = context;
        mDotLayout = (LinearLayout) View.inflate(context, R.layout.default_indicator, null);
        return mDotLayout;
    }

    public void initialize() {
        mDots = new ArrayList<>();
        mSlideCount = NUM_PAGES;

        for (int i = 0; i < NUM_PAGES; i++) {
            ImageView dot = new ImageView(mContext);

            dot.setImageResource(R.drawable.indicator_dot_grey);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            mDotLayout.addView(dot, params);
            mDots.add(dot);
        }

        selectPosition(FIRST_PAGE_NUM);
    }

    public void selectPosition(int index) {
        for (int i = 0; i < mSlideCount; i++) {
            int drawableId = (i == index) ? (R.drawable.indicator_dot_white) : (R.drawable.indicator_dot_grey);

            mDots.get(i).setImageResource(drawableId);
        }
    }
}


