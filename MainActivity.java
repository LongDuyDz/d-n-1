package com.example.project_myvntour.ActivityMaintain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project_myvntour.Fragment.HomeFragment;
import com.example.project_myvntour.R;
import com.google.android.material.appbar.MaterialToolbar;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private DuoDrawerLayout drawer;
    private LinearLayout itemHome;
    private LinearLayout itemProfile;
    private LinearLayout idNearby;
    private LinearLayout itemBookmark;
    private LinearLayout itemNotification;
    private LinearLayout itemMessage;
    private LinearLayout itemSettings;
    private LinearLayout itemHelp;
    private LinearLayout itemLogout;
    private MaterialToolbar toolBar;
    private FrameLayout frameLayout;
    private DuoDrawerLayout drawerLayout;
    private MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = (DuoDrawerLayout) findViewById(R.id.drawer);
        toolbar = (MaterialToolbar) findViewById(R.id.tool_bar);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
        View contentview = drawerLayout.getContentView();
        View menuView = drawerLayout.getMenuView();
        itemHome = (LinearLayout)menuView. findViewById(R.id.itemHome);
        itemProfile = (LinearLayout) menuView.findViewById(R.id.itemProfile);
        idNearby = (LinearLayout) menuView.findViewById(R.id.idNearby);
        itemBookmark = (LinearLayout)menuView. findViewById(R.id.itemBookmark);
        itemNotification = (LinearLayout) menuView.findViewById(R.id.itemNotification);
        itemMessage = (LinearLayout) menuView.findViewById(R.id.itemMessage);
        itemSettings = (LinearLayout) menuView.findViewById(R.id.itemSettings);
        itemHelp = (LinearLayout) menuView.findViewById(R.id.itemHelp);
        itemLogout = (LinearLayout) menuView.findViewById(R.id.itemLogout);

        itemHome.setOnClickListener(this);
        itemProfile.setOnClickListener(this);
        idNearby.setOnClickListener(this);
        itemBookmark.setOnClickListener(this);
        itemNotification.setOnClickListener(this);
        itemMessage.setOnClickListener(this);
        itemSettings.setOnClickListener(this);
        itemHelp.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.itemHome:

                break;
            case R.id.itemProfile:
                startActivity(new Intent(this,Profile_Activity.class));
                break;
            case R.id.idNearby:
                startActivity(new Intent(this, NearbyActivity.class));
                break;
            case R.id.itemBookmark:

                break;
            case R.id.itemNotification:

                break;
            case R.id.itemMessage:

                break;
            case R.id.itemSettings:
                startActivity(new Intent(this,SettingActivity.class));
                break;
            case R.id.itemHelp:
                startActivity(new Intent(this,RevenuaActivity.class));
                break;
            case R.id.itemLogout:
                comFirmExit();
                break;
        }
        drawerLayout.closeDrawer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_chuong , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.iconChuong:
                Toast.makeText(this, "Chọn chuông", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;



        }


        return true;
    }

    public void comFirmExit(){
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Thông Báo").setMessage("Bạn có chắc muốn thoát").setPositiveButton("Ok" , null).setNegativeButton("Cancel" , null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }
}