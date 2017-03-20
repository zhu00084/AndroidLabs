package com.example.qiu.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MessageFragment extends Fragment {

    ChatWindow cw = null;

    public MessageFragment() {
        // Required empty public constructor
    }

    public MessageFragment(ChatWindow c) {
        cw = c;
    }
    Long id;
    String msg;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        Bundle bun = getArguments();
        id = bun.getLong("ID");
        msg= bun.getString("Msg");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_mssage, null);
        TextView tv = (TextView)v.findViewById(R.id.txtDBId);
        tv.setText("");
        tv.setText(id.toString());

        TextView m = (TextView)v.findViewById(R.id.txtMessage);
        m.setText("");
        m.setText(msg);

        Button btnDelete = (Button)v.findViewById(R.id.btnDeleteMessage);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MessageFragment", "User clicked Delete Message button");
                if (cw == null) {               // called from a activity window
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("DeleteID", id);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
                else            //callled from a tablet emulator
                {
                    cw.deleteListMessage(id);
                    cw.removeFragment();
                }
            }
        });

        return v;
    }

}
