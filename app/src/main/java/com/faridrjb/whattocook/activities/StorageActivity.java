package com.faridrjb.whattocook.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.faridrjb.whattocook.R;
import com.faridrjb.whattocook.ViewPagerAdapter;
import com.faridrjb.whattocook.fragments.storage.ChaashFragment;
import com.faridrjb.whattocook.fragments.storage.GhalFragment;
import com.faridrjb.whattocook.fragments.storage.GooshFragment;
import com.faridrjb.whattocook.fragments.storage.HobFragment;
import com.faridrjb.whattocook.fragments.storage.KRoghFragment;
import com.faridrjb.whattocook.fragments.storage.LabanFragment;
import com.faridrjb.whattocook.fragments.storage.MSabziFragment;
import com.faridrjb.whattocook.fragments.storage.OtherFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StorageActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;

    List<Fragment> fragments = new ArrayList<>(Arrays.asList(
            new HobFragment(), new GhalFragment(), new LabanFragment(), new MSabziFragment(),
            new ChaashFragment(), new KRoghFragment(), new GooshFragment(), new OtherFragment()));
    String[] titles =
            {"حبوبات", "غلات", "لبنیات", "میوه و سبزی", "چاشنی ها",
                    "کره و روغن", "گوشت و فرآورده ها", "غیره"};
    int[] icons = {R.drawable.ic_beans, R.drawable.ic_wheat, R.drawable.ic_cow,
            R.drawable.ic_apple, R.drawable.ic_salt, R.drawable.ic_olive_oil,
            R.drawable.ic_chicken, R.drawable.ic_shopping_cart};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        ImageButton back = findViewById(R.id.icon_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        setUpFragments();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        setUpIcons();
    }

    private void setUpFragments() {
        for (int i = 0; i < fragments.size(); i++) {
            adapter.addFragment(fragments.get(i), titles[i]);
        }
    }

    private void setUpIcons() {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
}