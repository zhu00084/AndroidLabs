package com.example.qiu.androidlabs;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MessageDetails extends AppCompatActivity {

    Long id;
    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        Bundle extras = getIntent().getExtras();

        Bundle bun = new Bundle();
        bun.putLong("ID", extras.getLong("ID") );
        String msg = extras.getString("Msg");
        bun.putString("Msg", msg );

        //int duration = Toast.LENGTH_LONG;
        //Toast toast = Toast.makeText(MessageDetails.this, msg , duration);
        //toast.show();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MessageFragment m = new MessageFragment(null);
        m.setArguments(bun);
        ft.replace(R.id.frame2, m);
        ft.commit();
    }
}
