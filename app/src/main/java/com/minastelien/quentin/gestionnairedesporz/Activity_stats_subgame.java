package com.minastelien.quentin.gestionnairedesporz;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.minastelien.quentin.gestionnairedesporz.Fragments.Fragment_stats_subgame_characters;
import com.minastelien.quentin.gestionnairedesporz.Fragments.Fragment_stats_subgame_hist;

/**
 * This Activity shows all data about an inspected game.
 * Created by Quentin on 02/05/2016.
 */
public class Activity_stats_subgame extends AppCompatActivity {

    private final String KEY_CURRENT_FRAME = "current_frame";
    private final String KEY_TIMESTAMP = "TIMESTAMP_KEY";
    private final String KEY_GAME_KEY = "GAME_KEY";

    private long gameKey;
    private String timestamp;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    // Tab titles
    private String[] tabNames = {"Personnages", "Histoire"};


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        if (savedInstanceState != null) {
            gameKey = savedInstanceState.getLong(KEY_GAME_KEY);
            timestamp = savedInstanceState.getString(KEY_TIMESTAMP);
        } else {
            gameKey = getIntent().getExtras().getLong(KEY_GAME_KEY);
            timestamp = getIntent().getExtras().getString(KEY_TIMESTAMP);
        }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(timestamp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Initilization
        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.activity_stats_sliding_tabs);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final String KEY_GAME_KEY = "GAME_KEY";

            @Override
            public Fragment getItem(int index) {
                switch (index) {
                    case 0:
                        // Characters fragment activity
                        Fragment_stats_subgame_characters fragment_stats_subgame_characters =
                                new Fragment_stats_subgame_characters();
                        Bundle bundlechar = new Bundle();
                        bundlechar.putLong(KEY_GAME_KEY, gameKey);
                        fragment_stats_subgame_characters.setArguments(bundlechar);
                        return fragment_stats_subgame_characters;
                    case 1:
                        // Hist fragment activity
                        Fragment_stats_subgame_hist fragment_stats_subgame_hist =
                                new Fragment_stats_subgame_hist();
                        Bundle bundlehist = new Bundle();
                        bundlehist.putLong(KEY_GAME_KEY, gameKey);
                        fragment_stats_subgame_hist.setArguments(bundlehist);
                        return fragment_stats_subgame_hist;
                }
                return null;
            }

            @Override
            public int getCount() {
                // get item count - equal to number of tabs
                return 2;
            }
        });

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.addTab(mTabLayout.newTab().setText(tabNames[0]));
                mTabLayout.addTab(mTabLayout.newTab().setText(tabNames[1]));
                mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            }
        });

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                if (savedInstanceState != null) {
                    int current_frame_id_restored = savedInstanceState.getInt(KEY_CURRENT_FRAME);
                    mViewPager.setCurrentItem(current_frame_id_restored);
                    mTabLayout.getTabAt(current_frame_id_restored).select();
                }
            }
        });

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        mTabLayout.getTabAt(position).select();
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });

        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_GAME_KEY, gameKey);
        outState.putString(KEY_TIMESTAMP, timestamp);
        outState.putInt(KEY_CURRENT_FRAME, mViewPager.getCurrentItem());
    }
}
