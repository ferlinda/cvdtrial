package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome_screen extends AppCompatActivity {

    ViewPager viewPager;
    // Add images from drawable to array
    int images[] = {R.drawable.introslide1,R.drawable.introslide2,R.drawable.introslide3};
    int currentPageCunter = 0;
    LinearLayout dotsLayout;
    int custom_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome_screen);

        // Find view by id
        dotsLayout = findViewById(R.id.dotscontainer);
        viewPager = findViewById(R.id.viewpager);
        // Add adapter
        prepareDots(custom_position++);
        viewPager.setAdapter(new SlideAdapter(images, Welcome_screen.this));

        // This is so that the dots move according to image position
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(custom_position>2)
                    custom_position = 0;
                prepareDots(custom_position++);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPageCunter == images.length){
                    currentPageCunter = 0;
                }

                viewPager.setCurrentItem(currentPageCunter++,true);
            }
        };

        Timer timer = new Timer();
        // Timer of intro images slider
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 3000,3000);
    }

    // This is for the dots
    private void prepareDots(int currentSlidePosition){
        if(dotsLayout.getChildCount()>0)
            dotsLayout.removeAllViews();

        ImageView dots[] = new ImageView[3];

        for(int i=0; i<3; i++){
            dots[i] = new ImageView(this);
            if(i==currentSlidePosition)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4,0,4,0);
            dotsLayout.addView(dots[i],layoutParams);
        }
    }
}
