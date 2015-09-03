package com.kilr.fizzy.intro;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.kilr.fizzy.R;

/**
 * Created by rancohen on 8/25/15.
 */
public class CrossfadePageTransformer implements ViewPager.PageTransformer {

    float mX;
    float mY;
    View mText;

    @Override
    public void transformPage(View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        Log.i("TAG", String.valueOf(position));
        Log.i("TAG1", String.valueOf(pageWidth));
        Log.i("TAG2", String.valueOf(pageWidth * position));

        View backgroundView = page.findViewById(R.id.root);
        View text = page.findViewById(R.id.text);

        View phone = page.findViewById(R.id.tour_first_phone_overlay);
        View map = page.findViewById(R.id.tour_first_map);
        View phonemock = page.findViewById(R.id.phonemock);

        if (position <= 1) {
            page.setTranslationX(pageWidth * -position);
        }

//        //screen exit
//        if (position >= -1.0f && position < 0.0) {
//
//        }
//
//        //screen enters
//        if(position <= 0.0f && position > 1.0f) {
//
//        }

        if (position <= -1.0f || position >= 1.0f) {
        } else if (position == 0.0f) {
        } else {
            if (backgroundView != null) {
                backgroundView.setAlpha(1.0f - Math.abs(position));
            }

            if (text != null) {
                text.setTranslationX(pageWidth * position);
                text.setAlpha(1.0f - Math.abs(position));
            }

            //Map + phone - map simple translate, phone parallax effect
            if (map != null) {
                map.setTranslationX(pageWidth * position);
            }

            if (phone != null) {
                phone.setTranslationX((float) (pageWidth / 1.2 * position));
            }

            //fade in/out
            if (phonemock != null) {
                phonemock.setAlpha(1.0f - Math.abs(position));
            }

        }
    }
}
