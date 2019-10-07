package saim.com.nwel.Activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import saim.com.nwel.Model.ModelAttendance;
import saim.com.nwel.Model.ModelUsers;
import saim.com.nwel.R;
import saim.com.nwel.Util.MainUrl;

public class AttendanceHistory extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private TextView txtDatepicker;

    ArrayList<ModelAttendance> attendanceArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeFull);
        setContentView(R.layout.activity_attendance_history);

        init();
    }

    private void init() {
        txtDatepicker = (TextView) findViewById(R.id.txtDatepicker);
        txtDatepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDailog();
            }
        });
    }


    public void showDatePickerDailog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String year1 = year + "";
        String month1 = (month+1) + "";
        String day1 = dayOfMonth + "";

        if ((month+1) < 10) {
            month1 = "0" + (month+1);
        }

        if (dayOfMonth < 10) {
            day1 = "0" + dayOfMonth;
        }

        String main_date = year1 + "-" + month1 + "-" + day1;
        txtDatepicker.setText(main_date);

        submitAttendance(main_date);
    }



    public void submitAttendance(final String searchDate) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MainUrl.url_attendance_list,
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
                                for (int i=0; i<jsonArray1.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                                    String ID = jsonObject1.getString("ID");
                                    String USER_ID = jsonObject1.getString("USER_ID");
                                    String USER_NAME = jsonObject1.getString("USER_NAME");
                                    String LATITUDE = jsonObject1.getString("LATITUDE");
                                    String LONGITUDE = jsonObject1.getString("LONGITUDE");
                                    String ALTITUDE = jsonObject1.getString("ALTITUDE");
                                    String SERIAL = jsonObject1.getString("SERIAL");
                                    String IMEI = jsonObject1.getString("IMEI");
                                    String MODEL = jsonObject1.getString("MODEL");
                                    String BRAND = jsonObject1.getString("BRAND");
                                    String INSERT_TIME = jsonObject1.getString("INSERT_TIME");

                                    Log.d("SAIM_LOG", INSERT_TIME);

                                    ModelAttendance modelAttendance = new ModelAttendance(ID, USER_ID, USER_NAME, LATITUDE, LONGITUDE, ALTITUDE, SERIAL, IMEI, MODEL, BRAND, INSERT_TIME);
                                    attendanceArrayList.add(modelAttendance);

                                }


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
                params.put("INSERT_TIME", searchDate);


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

}















