package com.example.viewpager;

import static com.example.viewpager.R.id.positionPlain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;
    Button list_btn,home_btn,settings_btn;
    LinearLayout positionPlain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        list_btn = findViewById(R.id.list_btn);
        home_btn = findViewById(R.id.home_btn);
        settings_btn = findViewById(R.id.settings_btn);
        positionPlain = findViewById(R.id.positionPlain);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), new Lifecycle() {
            @Override
            public void addObserver(@NonNull LifecycleObserver observer) {

            }

            @Override
            public void removeObserver(@NonNull LifecycleObserver observer) {

            }

            @NonNull
            @Override
            public State getCurrentState() {

                return null;
            }
        });
    }

    @Override

    protected void onResume() {
        super.onResume();
        viewPager.setAdapter(adapter);
        if (adapter.items.size() == 0){
            adapter.items.add(new ListFragment());
            adapter.items.add(new HomeFragment());
            adapter.items.add(new SettingsFragment());
        }
       viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               super.onPageScrolled(position, positionOffset, positionOffsetPixels);
               float offset = position + positionOffset;
               movingPositionPlain(offset);
           }
       });
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

    }

    private void movingPositionPlain(float offset){
        float standard_distance = home_btn.getX();
        positionPlain.setX(standard_distance*offset);
    }

    class ViewPagerAdapter extends FragmentStateAdapter{
        ArrayList<Fragment> items = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}