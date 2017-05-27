package me.lijpeng.one;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;

import me.lijpeng.one.fragments.*;
import me.lijpeng.one.viewpager.NoSlidingPageTransformer;
import me.lijpeng.one.viewpager.NoSlidingViewPaper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private ViewPager mViewPager;
    private View articleContainer;
    public static OkHttpClient client = new OkHttpClient();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            articleContainer = findViewById(R.id.article_container);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mViewPager.getCurrentItem() == 2)
                        articleContainer.setVisibility(View.INVISIBLE);
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    articleContainer.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    if (mViewPager.getCurrentItem() == 0)
                        articleContainer.setVisibility(View.INVISIBLE);
                    mViewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (NoSlidingViewPaper) findViewById(R.id.viewpager);
        final ArrayList<Fragment> fgLists = new ArrayList<>(3);
        fgLists.add(new FragmentOne());
        fgLists.add(new FragmentArticle());
        fgLists.add(new FragmentQuestion());
        NoSlidingPageTransformer pageTransformer = new NoSlidingPageTransformer();
        pageTransformer.setCurrentItem(this, 0, fgLists);
        mViewPager.setPageTransformer(true, pageTransformer);
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fgLists.get(position);
            }

            @Override
            public int getCount() {
                return fgLists.size();
            }
        };
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2); //预加载剩下两页
        articleContainer = findViewById(R.id.article_container);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}