package com.example.project_myvntour.ActivityMaintain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.Manifest;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.project_myvntour.Adapter.AdapterItemKhachSanMap;
import com.example.project_myvntour.Database.SelectAll;
import com.example.project_myvntour.Mode.KhachSan;
import com.example.project_myvntour.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.maps.android.ui.IconGenerator;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class NearbyActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterItemKhachSanMap.Listernaer  {
    public static final int code = 100;
    private GoogleMap mMap;
    private EditText etNhapDiaChi;
    LatLng currentUserLocation, searchPointLocation;
    Marker currentUser, searchPoint;
    private MaterialToolbar toolBar;
    private RecyclerView recyclerview;
    private List<KhachSan> listKhachSan;
    private List<KhachSan> listKhachSan2;
    private AdapterItemKhachSanMap adapter;
    private NumberFormat fm = new DecimalFormat("#,###");
    private MarkerOptions markerOptions;
    private CardView cvBoLoc;
    private View dongke;
    private CheckBox cbHouse;
    private CheckBox cbApartments;
    private CheckBox cbHotel;
    private CheckBox cbVilla;
    private CheckBox cbAll;
    private LinearLayout layoutContainer;
    private LinearLayout mGridLayout;
    private double a , b ,tong22 ,tong44;
    private SelectAll mSelectAll;
    private View vKhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        // muốn map hiện lên phải có cái này
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);


        mapFragment.getMapAsync(this);
        toolBar = (MaterialToolbar) findViewById(R.id.tool_bar2);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);




       // Expandable Card View







        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("Nearby");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        listKhachSan = new ArrayList<>();
        listKhachSan2 = new ArrayList<>();
        mSelectAll =new SelectAll(this);
        adapter = new AdapterItemKhachSanMap(this ,this);
        listKhachSan = mSelectAll.getListKhachSan();




        // sửa lí địa điểm gần nhất
        getListGanNhat(listKhachSan);

        // sửa lí địa điểm gần nhất
        adapter.setData(listKhachSan2);

        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL , false));
        recyclerview.setAdapter(adapter);
        // hàm lấy vị trí của item trong reclerview
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = getCurrentItem();// lấy vị trí
                    KhachSan khach = listKhachSan2.get(position);
                    liaCam(khach);


                }
            }
        });
// dữ nguyên item của recylerview ở giữa màn hình có 2 cách


//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerview);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerview);
//        cvBoLoc.setOnClickListener(v->{
//            int view = (mGridLayout.getVisibility() == View.GONE) ?View.VISIBLE :View.GONE;
//          //  int view2 = (vKhong.getVisibility() == View.GONE) ?View.VISIBLE :View.GONE;
//            TransitionManager.beginDelayedTransition(layoutContainer , new AutoTransition());
//            mGridLayout.setVisibility(view);
//           // vKhong.setVisibility(view2);
//        });


    }

    public void liaCam(KhachSan khach){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(khach.getKinhdo(), khach.getVido()))
                    .zoom(17)
                    .bearing(0)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        } else {
            Toast.makeText(getBaseContext(), "Không lấy được thông tin định vị, hãy bật GPS và bấm nút định vị trên bản đồ", Toast.LENGTH_LONG).show();
        }
    }
    public void getListGanNhat(List<KhachSan> list){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(location != null){
            for (KhachSan khach: list
            ) {
                a = location.getLatitude();
                b = location.getLongitude();
                tong22 = ((khach.getKinhdo() - a)*(khach.getKinhdo() - a)) + ((khach.getVido() - b)*(khach.getVido() - b));
                tong44 = Math.sqrt(tong22);
                if(tong44 <= 1000){
                    listKhachSan2.add(khach);
                }
            }
        }
    }
    private int getCurrentItem(){
        return ((LinearLayoutManager)recyclerview.getLayoutManager())
                .findFirstVisibleItemPosition();
    }

    public void getCurrentLocationItemMap(KhachSan khach) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

//         a = location.getLatitude();
//         b = location.getLongitude();
//         tong22 = ((khach.getKinhdo() - a)*(khach.getKinhdo() - a)) + ((khach.getVido() - b)*(khach.getVido() - b));
//         tong44 = Math.sqrt(tong22);
//       if(tong44 <= 1.0){
            if (location != null) {
                LatLng myLocation = new LatLng(khach.getKinhdo(), khach.getVido());
                currentUserLocation = myLocation;
                IconGenerator iconfactory = new IconGenerator(this);
                iconfactory.setBackground(getResources().getDrawable(R.drawable.marker_background));
                iconfactory.setTextAppearance(R.style.iconGenText);
                markerOptions = new MarkerOptions()
                        .position(myLocation)
                        .title(khach.getTenKhachSan())
                        .snippet(khach.getDiaDiem())
                        .icon(BitmapDescriptorFactory.fromBitmap(iconfactory.makeIcon(khach.getGiaThue())));
                currentUser = mMap.addMarker(markerOptions);
                currentUser.setTag(false);
            } else {
                Toast.makeText(getBaseContext(), "Không lấy được thông tin định vị, hãy bật GPS và bấm nút định vị trên bản đồ", Toast.LENGTH_LONG).show();
            }
//       }



    }


    @Override
    public void onMapReady(@NonNull  GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions( new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, code);
            }
            return;
        }
        mMap.setMyLocationEnabled(true);
        getCurrentLocation();// hàm lấy vị trí chỗ mình đang đứng
        for ( KhachSan x: listKhachSan2) {
            getCurrentLocationItemMap(x);// hineje bảng giá ở trên map
        }
        //tạo sự kiện click vào button
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            // Return true if the click event is consumed (and therefore no
            // info window is displayed) or false to produce default behavior.
            @SuppressLint("MissingPermission")
            public boolean onMarkerClick(final Marker marker) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
               // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                        .zoom(15)
                        .bearing(0)
                        .tilt(40)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                System.out.println("ddddddddddddddddddd" + marker.getPosition().latitude);
                System.out.println("ddddddddddddddddddd" + marker.getTitle());
                System.out.println("ddddddddddddddddddd" + marker.getTag());
                System.out.println("ddddddddddddddddddd" + Integer.parseInt(marker.getId().substring(1)));

                if(!String.valueOf(marker.getPosition().latitude).equals(String.valueOf(location.getLatitude())) && !String.valueOf(marker.getPosition().longitude).equals(String.valueOf(location.getLongitude()))){// đoạn này phải check xem có trùng vị trí mình đang đứng không nếu mà trùng thì sẽ lỗi vì vị trí đứng không lấy trong recylerview
                    recyclerview.smoothScrollToPosition(Integer.parseInt(marker.getId().substring(1)) -1);// di chuyển đến vị trí của recylerview
                }else{
                     return (marker != null && marker.getTag() != null && ((Boolean)marker.getTag()).booleanValue()); // thể hiện vị trí của người dùng không đc click vào nếu click vào sẽ bị văng lên là phải để là true
                }


                //return (marker != null && marker.getTag() != null && ((Boolean)marker.getTag()).booleanValue());
                return false;
            }
        });
    }
    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getBaseContext(), "Hãy cấp quyền truy cập GPS cho ứng dụng", Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(15)
                    .bearing(0)
                    .tilt(40)
                    .build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // vẽ maker
            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
            currentUserLocation = myLocation;
             markerOptions = new MarkerOptions()
                    .position(myLocation)
                    .title("Your position at here")
                    .snippet("Hello MyVnTour Company")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentUser = mMap.addMarker(markerOptions);
            currentUser.setTag(false);

        } else {
            Toast.makeText(getBaseContext(), "Không lấy được thông tin định vị, hãy bật GPS và bấm nút định vị trên bản đồ", Toast.LENGTH_LONG).show();
        }
    }
    //hàm này là hiện xin quyền map hiện dialog xin quền

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case code: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && (ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(NearbyActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
                    onMapReady(mMap);

                }else {
                    Toast.makeText(NearbyActivity.this.getBaseContext(), "Bạn không cấp quyền GPS không thể hoạt động", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClickKhachSanMap(View v, int position) {
        KhachSan khach = listKhachSan.get(position);
        Intent intent = new Intent(NearbyActivity.this , InFoKhachSanActivity.class);
        intent.putExtra("khachsan" , khach);
        startActivity(intent);
    }


}