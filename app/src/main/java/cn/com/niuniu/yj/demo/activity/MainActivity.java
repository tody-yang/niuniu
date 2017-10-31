package cn.com.niuniu.yj.demo.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.com.niuniu.yj.demo.R;
import cn.com.niuniu.yj.demo.activity.base.BaseActivity;
import cn.com.niuniu.yj.demo.bean.User;
import cn.com.niuniu.yj.demo.entity.KeyEntity;
import cn.com.niuniu.yj.demo.fragment.GreenDaoFragment;
import cn.com.niuniu.yj.demo.fragment.MainFragment;
import cn.com.niuniu.yj.demo.myInterface.MyInterface;

/**
 * 主Activity
 */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainFragment.OnFragmentInteractionListener ,
        GreenDaoFragment.OnListFragmentInteractionListener, MyInterface {

    /**
     *  主Fragment
     */
    private MainFragment mainFragment;

    /**
     *  GreenDao Fragment
     */
    private GreenDaoFragment mGreenDaoFragment = new GreenDaoFragment();




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            mainFragment = new MainFragment();
            mGreenDaoFragment = new GreenDaoFragment();
            setDefaultFragment(mainFragment);
        }

        //conten上的titlebar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //浮层图标
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        goneView(fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置显示OptionMenu
        setShowOptionMenu(false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("yj","刷新------------onRestart");
        onUpdateDB();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_greendao) {
            // select GreenDao
            setDefaultFragment(mGreenDaoFragment);
        } else if (id == R.id.nav_about) {
            //select about
            startMyActivity(AboutActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 设置默认的Fragment
     * @param defaultFragment  默认的Fragment
     */
    private void setDefaultFragment(Fragment defaultFragment){
        if (defaultFragment != null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_main, defaultFragment);
            ft.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void onListFragmentInteraction(User item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KeyEntity.KEY_GREENDAO_USER, item);
        bundle.putLong(KeyEntity.KEY_GREENDAO_ID,item.getId());
        startMyActivity(UpdateGreenDaoActivity.class,bundle);
    }


    @Override
    public void onUpdateDB() {
        Log.i("yj","刷新------------");
        if (mGreenDaoFragment != null){
            mGreenDaoFragment.onClickQueryAll();
        }
    }
}
