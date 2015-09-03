package com.kilr.fizzy.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.kilr.fizzy.R;

/**
 * Created by idanakav on 9/3/15.
 */
public class IntroActivity extends AppCompatActivity {

    ViewPager mPager;
    PagerAdapter mPagerAdapter;
    LinearLayout mCircleIndicators;
    Button mSkipButton;
    Button mDoneButton;
    ImageButton mNext;

    private IndicatorController mController;


    boolean isOpaque = true; // TODO write proper comment regarding opacity balancing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_intro);


        mSkipButton = (Button) findViewById(R.id.skip);
        mSkipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 8/25/15 add end tutorial method
            }
        });

        mDoneButton = (Button) findViewById(R.id.done);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //// TODO: 8/25/15 add end tutorial method
            }
        });

        mNext = (ImageButton) findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
            }
        });

        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TourPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setPageTransformer(true, new CrossfadePageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //See note above for why this is needed
                if(position == IndicatorController.NUM_PAGES - 2 && positionOffset > 0){
                    if(isOpaque) {
                        mPager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                }else{
                    if(!isOpaque) {
                        mPager.setBackgroundColor(getResources().getColor(R.color.tutorial_background_opaque));
                        isOpaque = true;
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                mController.selectPosition(position);
                if(position == IndicatorController.NUM_PAGES - 2){
                    mSkipButton.setVisibility(View.GONE);
                    mNext.setVisibility(View.GONE);
                    mDoneButton.setVisibility(View.VISIBLE);
                }else if(position < IndicatorController.NUM_PAGES - 2){
                    mSkipButton.setVisibility(View.VISIBLE);
                    mNext.setVisibility(View.VISIBLE);
                    mDoneButton.setVisibility(View.GONE);
                }else if(position == IndicatorController.NUM_PAGES - 1){
                    //// TODO: 8/25/15 add end tutorial
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Unused
            }
        });

        initController();
    }

    private void initController() {
        if (mController == null)
            mController = new IndicatorController();

        FrameLayout indicatorContainer = (FrameLayout) findViewById(R.id.indicator_container);
        indicatorContainer.addView(mController.newInstance(this));

        mController.initialize();
    }



}
