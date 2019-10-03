package saim.com.nwel.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import saim.com.nwel.R;

public class Order extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setTitle("Order");
    }
}
