package com.example.qiu.androidlabs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static android.R.attr.id;

public class TestToolbar extends AppCompatActivity {

    String snackBarMsg = "You selected item 1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ready!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id;
        id = mi.getItemId();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.tool_coorlayout);

        switch(id) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                Snackbar.make(coordinatorLayout, snackBarMsg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");

                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);

                builder.setTitle("Do you want to go back");
                // Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response", "My information to share");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                     // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();

                final View view = inflater.inflate(R.layout.dialog_custom, null);
                builder1.setView(view)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                TextView newText = (TextView)view.findViewById(R.id.newmsg);
                                snackBarMsg = newText.getText().toString();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                AlertDialog dialog2 = builder1.create();
                dialog2.show();

                break;
            case R.id.action_settings:
                Log.d("Toolbar", "Version 1.0, by Qiuju Zhu");
                break;
        }
        return true;
    }

}
