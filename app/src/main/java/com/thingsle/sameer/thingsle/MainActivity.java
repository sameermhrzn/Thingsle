package com.thingsle.sameer.thingsle;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends NavDrawer {

    private ViewFlipper mViewFlipper;

    static final LatLng Thamel = new LatLng(27.7152, 85.3102);
    private GoogleMap googleMap;

    TextView textView;
    SearchView searchView;




   /* int image_flipper[] = {
            R.drawable.icon_comment, R.drawable.icon_favorites, R.drawable.icon_login, R.drawable.icon_pin};
   ViewFlipper viewFlipper;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        searchView = (SearchView) findViewById(R.id.sv_search);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();

            }
        });

        //for view flipper
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mViewFlipper.showNext();
                return true;
            }
        });


        /* viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        for(int i=0;i<image_flipper.length;i++){

            setFlipperImage(image_flipper[i]);
        }
*/

        //For Map
        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            Marker TP = googleMap.addMarker(new MarkerOptions().position(Thamel).title("Thamel"));
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                    finish();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




 /*   public void setFlipperImage(int res){
        Log.i("Flipper",res+"");
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setBackgroundResource(res);
        viewFlipper.addView(imageView);

    }*/

   /* class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }*/
    }


