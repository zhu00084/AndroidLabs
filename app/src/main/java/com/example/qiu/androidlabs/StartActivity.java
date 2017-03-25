package com.example.qiu.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.qiu.androidlabs.LoginActivity.ACTIVITY_NAME;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(ACTIVITY_NAME, "In onCreate()");

        Button fab = (Button) findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 5);
            }
        });

        Button btn3 = (Button) findViewById(R.id.button3);

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

        Button btnWeather = (Button) findViewById(R.id.btnWeather);
        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(ACTIVITY_NAME, "User clicked Weather Button");

                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivity(intent);
            }
        });

        Button btnTest = (Button) findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(ACTIVITY_NAME, "User clicked Test Toolbar");

                Intent intent = new Intent(StartActivity.this, TestToolbar.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 5)
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");

        if (resultCode == Activity.RESULT_OK)
        {
            String messagePassed = data.getStringExtra("Response");

            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(StartActivity.this, "ListItemsActivity passed: " + messagePassed, duration);
            toast.show();

        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

}
