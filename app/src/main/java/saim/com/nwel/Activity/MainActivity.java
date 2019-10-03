package saim.com.nwel.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.PrintWriter;
import java.io.StringWriter;

import saim.com.nwel.R;
import saim.com.nwel.Splash;
import saim.com.nwel.Util.SharedPrefDatabase;

public class MainActivity extends AppCompatActivity {

    ImageView imgQuit;
    LinearLayout layoutAttendance, layoutCollection, layoutOrder, layoutHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeFull);
        setContentView(R.layout.activity_main);

        if (new SharedPrefDatabase(this).RetriveLogin() == 0) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        init();
    }

    private void init() {
        imgQuit = (ImageView) findViewById(R.id.imgQuit);

        layoutAttendance = (LinearLayout) findViewById(R.id.layoutAttendance);
        layoutCollection = (LinearLayout) findViewById(R.id.layoutCollection);
        layoutOrder = (LinearLayout) findViewById(R.id.layoutOrder);
        layoutHelp = (LinearLayout) findViewById(R.id.layoutHelp);


        actionEvent(layoutAttendance, new Intent(this, Attendance.class));
        actionEvent(layoutCollection, new Intent(this, Collection.class));
        actionEvent(layoutOrder, new Intent(this, Order.class));
        actionEvent(layoutHelp, new Intent(this, Help.class));

        haveStoragePermission();
    }

    private void actionEvent(LinearLayout linearLayout, final Intent intent) {

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        imgQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(MainActivity.this)
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setMessage("Are you want to logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new SharedPrefDatabase(getApplicationContext()).StoreLogin(0);
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            }
        });


        Log.i("TAG", "IMEI: " + getUniqueIMEIId(this));

    }


    public boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }


    public static String getUniqueIMEIId(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String imei;

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    imei = telephonyManager.getImei();
                } else {
                    imei = telephonyManager.getDeviceId();
                }

                if (imei != null && !imei.isEmpty()) {
                    return imei;
                } else {
                    return android.os.Build.SERIAL;
                }
            }

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            return errors.toString();
        }
        return "not_found";
    }

}
