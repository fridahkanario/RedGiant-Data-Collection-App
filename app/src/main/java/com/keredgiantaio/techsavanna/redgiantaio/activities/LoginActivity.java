package com.keredgiantaio.techsavanna.redgiantaio.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keredgiantaio.techsavanna.redgiantaio.R;
import com.keredgiantaio.techsavanna.redgiantaio.helpers.ApiLoginClient;
import com.keredgiantaio.techsavanna.redgiantaio.helpers.ApiLoginService;
import com.keredgiantaio.techsavanna.redgiantaio.methods.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
   public static EditText input_phone,input_password;
    Button loginbtn, register;
    private ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        input_phone=findViewById(R.id.input_phone);
        input_password=findViewById(R.id.input_password);



        loginbtn=findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register2);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();

            }
        });
    }

    private void checkLogin() {
        pDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_DayNight_DarkActionBar);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("login ...");
        pDialog.setCancelable(false);

        showpDialog();

        final String inputphone =  input_phone.getText().toString();
        final String inputpassword =  input_password.getText().toString();


        ApiLoginService service = ApiLoginClient.getClient().create(ApiLoginService.class);

        Call<LoginResponse> userCall = service.sendLogin(inputphone, inputpassword);

        System.out.println("login file" +inputphone+ "  "+inputpassword);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        prefs.edit().putString("telephone", inputphone).commit();

        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                hidepDialog();
                //onSignupSuccess();
                System.out.println("data out"+ call);
                Log.d("onResponse", "" + response.body().getMessage());

                String res=response.body().getMessage();



                if(response.body().getSuccess() == 1) {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();


                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    intent.putExtra("nameofperson",inputpassword);
                    intent.putExtra("telephone",inputphone);
                    startActivity(intent);



                }else {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
                Toast.makeText(LoginActivity.this, "Request Failed!!" , Toast.LENGTH_LONG).show();
            }
        });


    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
