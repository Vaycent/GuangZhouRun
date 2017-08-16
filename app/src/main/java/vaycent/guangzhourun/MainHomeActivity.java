package vaycent.guangzhourun;

import android.os.Bundle;
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
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import vaycent.base.UmengUtils;
import vaycent.service.base.BaseActivity;
import vaycent.service.router.XRouter;
import vaycent.service.router.XRules;

public class MainHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        initToolBar();

        initLayout();
    }

    private void initToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_home_appbar_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_home_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.activity_main_home_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_home_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_rightmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_main_action_settings) {

        }else if(id == R.id.item_main_action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_home_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initLayout(){
        findViewById(R.id.include_main_home_btn_tomapgame).setOnClickListener(v ->{
            MobclickAgent.onEvent(this, "000001");
            XRouter.getRaw(XRules.IMapGameActivity.class,this).start();});


        findViewById(R.id.include_main_home_btn_umengdata).setOnClickListener(v ->{
            MobclickAgent.onEvent(this, "000002");
            String umengData = UmengUtils.getDeviceInfo(this);
            Toast.makeText(this,"umengData:"+umengData,Toast.LENGTH_LONG).show();
            Log.e("Vaycent","umengData:"+umengData);
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }

}