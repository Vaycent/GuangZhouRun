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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.umeng.analytics.MobclickAgent;

import vaycent.RouterPath;
import vaycent.base.BaseActivity;
import vaycent.base.UmengUtils;


@Route(path = RouterPath.MAIN_HOME)
public class MainHomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        initToolBar();

        initLayout();


//        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//        Toast.makeText(this, "11111hahhahahaha", Toast.LENGTH_LONG).show();
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
            ARouter.getInstance().build("/vaycent/mapgame/MapGameActivity").navigation();
        });


        findViewById(R.id.include_main_home_btn_mygoal).setOnClickListener(v ->{
            ARouter.getInstance().build(RouterPath.MY_GOAL).navigation();
        });


        findViewById(R.id.include_main_home_btn_umengdata).setOnClickListener(v ->{
            MobclickAgent.onEvent(this, "000002");
            String umengData = UmengUtils.getDeviceInfo(this);
            Toast.makeText(this,"umengData:"+umengData,Toast.LENGTH_LONG).show();
            Log.e("Vaycent","umengData:"+umengData);
        });


        findViewById(R.id.include_main_home_btn_test).setOnClickListener(v ->{
            try{
                Thread.sleep(11000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

    }

}
