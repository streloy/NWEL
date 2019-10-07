package saim.com.nwel.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import saim.com.nwel.R;
import saim.com.nwel.Splash;
import saim.com.nwel.Util.MainUrl;

public class Attendance extends AppCompatActivity {

    TextView txtlatLon, txtStatus;
    ImageView imgFinger, imgHistory;
    private FusedLocationProviderClient fusedLocationClient;

    public String latitude = "", longitude = "", altitude = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeFull);
        setContentView(R.layout.activity_attendance);

        init();
    }

    private void init() {
        txtlatLon = (TextView) findViewById(R.id.txtlatLon);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        imgFinger = (ImageView) findViewById(R.id.imgFinger);
        imgHistory = (ImageView) findViewById(R.id.imgHistory);



        getStatus();
        imgFinger.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Animation();
                return true;
            }
        });

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AttendanceHistory.class));
            }
        });

        getLocation();

    }


    public void Animation(){
        Glide.with(this).asGif().load(R.drawable.ic_finger_print).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setLoopCount(1);
                resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                    @Override
                    public void onAnimationEnd(Drawable drawable) {
                        if (!latitude.isEmpty() && !latitude.isEmpty() && !latitude.isEmpty() ) {
                            submitAttendance();
                        } else {
                            Toast.makeText(getApplicationContext(), "Lat not empty", Toast.LENGTH_LONG).show();
                        }


                    }
                });

                return false;
            }


        }).into(imgFinger);
    }


    public void getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    //txtlatLon.setText("Lat : " + location.getLatitude() + "\nLon : " + location.getLongitude() + "\nAlt : " + location.getAltitude());
                    latitude    = location.getLatitude() + "";
                    longitude   = location.getLongitude() + "";
                    altitude    = location.getAltitude() + "";

                    //Toast.makeText(getApplicationContext(), location.getAccuracy() + " : " + location.getAltitude(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void getStatus() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainUrl.url_attendance_status,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equals("success")) {
                                JSONArray jsonArray1 = jsonObject.getJSONArray("response");
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                                String IN_TIME = jsonObject1.getString("IN_TIME");
                                txtStatus.setText("LAST ATTENDANCE : " + IN_TIME);

                            } else {
                                showDialog(message);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("SERIAL", Build.SERIAL);

                return params;
            }
        };
        queue.add(stringRequest);
    }


    public void submitAttendance() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainUrl.url_insert_attendance,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String code = jsonObject.getString("code");
                            String message = jsonObject.getString("message");

                            if (code.equals("success")) {
                                showDialog(message);
                            } else {
                                showDialog(message);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("ID", MainActivity.modelUsers.getID());
                params.put("USER_NAME", MainActivity.modelUsers.getUSER_NAME());
                params.put("LAT", latitude);
                params.put("LON", longitude);
                params.put("ALT", altitude);
                params.put("SERIAL", Build.SERIAL);
                params.put("IMEI", MainActivity.modelUsers.getIMEI());
                params.put("MODEL", Build.MODEL);
                params.put("BRAND", Build.BRAND);

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void showDialog(String s) {

        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(s)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();

    }


    public void showDialogSubmit(String s) {

        new AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(s)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();

    }


}
