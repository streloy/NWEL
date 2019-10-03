package saim.com.nwel.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import saim.com.nwel.R;
import saim.com.nwel.Util.SharedPrefDatabase;

public class Login extends AppCompatActivity {

    EditText inputUserID, inputUserPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppThemeFull);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        inputUserID = (EditText) findViewById(R.id.inputUserID);
        inputUserPassword = (EditText) findViewById(R.id.inputUserPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputUserID.getText().toString().isEmpty()) {
                    new AlertDialog.Builder(Login.this)
                            .setTitle("Warning")
                            .setMessage("User ID can not be empty!!!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();

                } else {
                    if (inputUserPassword.getText().toString().isEmpty()) {
                        new AlertDialog.Builder(Login.this)
                                .setTitle("Warning")
                                .setMessage("Password can not be empty!!!")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
                    } else {

                        new SharedPrefDatabase(getApplicationContext()).StoreLogin(1);

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                }
            }
        });
    }
}
