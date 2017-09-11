package com.edu.progresso.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.edu.progresso.R;
import com.edu.progresso.fragment.AttendanceFragment;
import com.edu.progresso.fragment.BehaviourFragment;
import com.edu.progresso.fragment.TimeTableFragement;
import com.edu.progressoframework.app.activity.BaseActivity;
import com.edu.progressoframework.app.controller.BaseController;
import com.edu.progressoframework.model.BaseModel;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private int[] tabIcons = {
            R.mipmap.ic_tab_favourite,
            R.mipmap.ic_tab_call,
            R.mipmap.ic_tab_contacts
    };


    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.viewpager)
    ViewPager viewPager;

    @ViewById(R.id.tabs)
    TabLayout tabLayout;

    @Override
    public void initViewSpecific()
    {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);
//        setupTabIcons();
    }

    @Override
    public void update() {

    }

    @Override
    public void updateModel() {

    }

    @Override
    public BaseController getController() {
        return null;
    }

    @Override
    public BaseModel getModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void createModel() {

    }

    private void setupTabIcons()
    {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new TimeTableFragement(), getResources().getString(R.string.timetable));
        adapter.addFrag(new AttendanceFragment(), getResources().getString(R.string.attendance));
        adapter.addFrag(new BehaviourFragment(), getResources().getString(R.string.behaviour));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
