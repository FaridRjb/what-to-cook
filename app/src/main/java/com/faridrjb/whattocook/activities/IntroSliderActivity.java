package com.faridrjb.whattocook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.fragments.introslider.SlideFragment;
import com.faridrjb.whattocook.fragments.introslider.SlideLogoFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class IntroSliderActivity extends AppCompatActivity implements SlideLogoFragment.CallBacks {

    ViewPager viewPager;
    ViewPagerAdapter adapter;

    private int[] imgResIds = {R.drawable.ic_kitchen, R.drawable.ic_favorite_filled};
    private List<String> titles = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_slider);

        titles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.introTitles)));
        descriptions = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.introDescriptions)));

        viewPager = findViewById(R.id.introSliderVP);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SlideLogoFragment());
        for (int i = 0; i <titles.size(); i++) {
            if (i == titles.size() -1)
                adapter.addFragment(SlideFragment.newSlide(imgResIds[i], titles.get(i), descriptions.get(i), "شروع کن"));
            else adapter.addFragment(SlideFragment.newSlide(imgResIds[i], titles.get(i), descriptions.get(i), null));
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void nextClicked(int nextBtnId) {
        if (viewPager.getCurrentItem()+1 == adapter.getCount()) {
            SharedPreferences preferences = getSharedPreferences("First Time", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("First time", false);
            editor.apply();
            startActivity(new Intent(IntroSliderActivity.this, MainActivity.class));
            finish();
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment) {
            fragmentList.add(fragment);
        }
    }
}