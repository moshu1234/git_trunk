package com.example.andrewliu.fatbaby;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrewliu.fatbaby.BodyCirleShow.BodyProgress;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab01;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab02;
import com.example.andrewliu.fatbaby.SlidMenu.MainTab03;
import com.example.andrewliu.fatbaby.SlidMenu.MenuLeftFragment;
import com.example.andrewliu.fatbaby.SlidMenu.MenuRightFragment;
import com.example.andrewliu.fatbaby.SlidMenu.StepCounterService;
import com.example.andrewliu.fatbaby.SlidMenu.StepDetector;
import com.example.andrewliu.fatbaby.progressbar.CircleProgressBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.LogRecord;

public class infoShow extends AppCompatActivity {

    private Thread thread;
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();
    private StepDetector detector;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ActionBar actionBar;
    private View tab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_show);
        actionBar = getSupportActionBar();
        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //show action bar icon
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setLogo(R.drawable.left);
//        actionBar.setDisplayUseLogoEnabled(true);


        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.left_setting);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.info_show);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                actionBar.setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                actionBar.setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        // 初始化SlideMenu
//        initRightMenu();
        // 初始化ViewPager
        initViewPager();  //
        Log.e("aaaaaa", "start step counter service");
//        detector = new StepDetector(this);
        Intent service = new Intent(this, StepCounterService.class);
        startService(service);
        if (thread == null) {

            thread = new Thread() {// ���߳����ڼ���ǰ����ı仯

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    int temp = 0;
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
//                        Log.e("aaaaaaaaaaa","flag="+StepCounterService.FLAG);
                        if (StepCounterService.FLAG) {
                            Message msg = new Message();
                            if (temp != StepDetector.CURRENT_SETP) {
                                temp = StepDetector.CURRENT_SETP;
                            }
                            handler.sendMessage(msg);
                        }
                    }
                }
            };
            thread.start();
        }
    }
    private void initViewPager()
    {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        MainTab01 tab01 = new MainTab01();
        MainTab02 tab02 = new MainTab02();
//        MainTab03 tab03 = new MainTab03();
        mFragments.add(tab01);
        mFragments.add(tab02);
//        mFragments.add(tab03);
        /**
         * 初始化Adapter
         */
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setAdapter(mAdapter);
//        mViewPager.setCurrentItem(1);
    }


    public void onDestroy(){
        super.onDestroy();
        Log.e("aaaaaa","stop step service");
        Intent service = new Intent(this, StepCounterService.class);
        stopService(service);
    }
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.e("aaaaaaaaaa","what="+msg.what);
            TextView textView = (TextView)findViewById(R.id.totalSteps);
            if(textView != null){
                textView.setText("今天走了" + StepDetector.CURRENT_SETP + "步");
            }
            super.handleMessage(msg);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.user_p:
                Toast.makeText(this, "用户", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.write_p:
                Toast.makeText(this, "发布", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.favo_p:
                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.user_p).setVisible(!drawerOpen);
        menu.findItem(R.id.favo_p).setVisible(!drawerOpen);
        menu.findItem(R.id.write_p).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        if(position > 1){
            mViewPager.setCurrentItem(1);
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        actionBar.setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
