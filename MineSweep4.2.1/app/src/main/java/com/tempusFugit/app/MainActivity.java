package com.tempusFugit.app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    public static final int MAX = 10;
    UserClass u = new UserClass();
    public static String userName="Doug Funnie";
    public static String[] scoreBoardStr = new String[MAX];
    public static String[] rankingArString = new String[MAX];
    public static int[] rankingAr = new int[MAX];
    public static int location;
    public static int newRank;
    public static Vibrator v;
    public static String[] userBanner;
    public static String[] userRank;
    public static String score = "";
    public static int[] scrAr = new int[2];
    String msg = "ANDROID:";
    String j ="switch";
    Fragment fragment;
    int lvlChosen;
    final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 102;

/*
    public void print(String s) {
        Toast.makeText(this, s, 1).show();

    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        fragment = new WelcomeInputFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();

       // print(getScrString(this));
        if ((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }else{
            openSaysAhMe();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View parentLayout = findViewById(R.id.nav_view);
        Snackbar.make(parentLayout, "Welcome!", Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }
/*
    public static String getScrString(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        scrAr[0] = width;
        scrAr[1] = height;
        return "(" + width + "," + height + ")";
    }
*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                }
                if (!canUseExternalStorage) {
                    Toast.makeText(this,"High Scores Feature Will Not Work!", Toast.LENGTH_LONG).show();
                } else {
                    openSaysAhMe();
                }
            }

        }
    }

    private void openSaysAhMe(){
        File dir = new File(u.path);
        if (u.savedFile1.exists())
        {
            //Toast.makeText(getApplicationContext(), "SavedFile Exists!", Toast.LENGTH_SHORT).show();
            u.autoLoad();
        }else {
            Toast.makeText(getApplicationContext(), "[ello]:Files DO NOT exist, creating...", Toast.LENGTH_LONG).show();
            dir.mkdir();
            u.createScoreBoard();
            u.createRankAr();
            u.createRankArString();
            u.autoSave();
            u.autoLoad();
        }
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        //adapter.addFragment(new GameFragment(), "Game");
        //adapter.addFragment(new ListContentFragment(), "List");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
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

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        View parentLayout = findViewById(R.id.nav_view);
        Snackbar.make(parentLayout, "Back Button Press", Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment;
        fragment = null;
        //noinspection SimplifiableIfStatement
        if(id == R.id.action_playername){
            fragment = new NewUserFragment();
        }else if(id == R.id.action_settings) {
            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        }else if(id == R.id.action_scores){
            fragment = new HighScoreFragment();
        }else if(id == R.id.action_custom) {
            fragment = new CustomInputFragment();
        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(j);
            ft.replace(R.id.content, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Graphics2View.gameLevel(lvlChosen);
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        fragment = null;
        Fragment fragmentGame = new GameFragment();
        int id = item.getItemId();

        if (id == R.id.nav_beginner) {
            fragment = fragmentGame;
            lvlChosen = 0;
        } else if (id == R.id.nav_intermediate) {
            fragment = fragmentGame;
            lvlChosen = 1;
        } else if (id == R.id.nav_advanced) {
            lvlChosen = 2;
            fragment = fragmentGame;
        } else if (id == R.id.nav_Custom) {
            fragment = new CustomInputFragment();
        } else if (id == R.id.nav_info) {
            fragment = new InstructionsFragment();
        } else if (id == R.id.nav_scores) {
            fragment = new HighScoreFragment();
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
        } else if (id == R.id.nav_team) {
            fragment = new ListContentFragment();
        } else if (id == R.id.nav_newuser){
            fragment = new NewUserFragment();
        }

        if (fragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(j);
                ft.replace(R.id.content, fragment);
                ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        u.gameLevelChosenbyUser(lvlChosen);
        return true;
    }

    ////////////DO NOT DELETE!!!! USED FOR THE TIMER!!!!!!!!
    @Override
    protected void onResume() {
        super.onResume();
        Graphics2View.onResume();
        Log.d(msg, "The onResume() event");
    }

    /////////////DO NOT DELETE!!!! USED FOR THE TIMER!!!!!
    @Override
    protected void onPause() {
        super.onPause();
        Graphics2View.onPause();
        Log.d(msg, "The onPause() event");
    }

    /////////////////NOT CRITICAL TO THE SYSTEM

    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }


}
