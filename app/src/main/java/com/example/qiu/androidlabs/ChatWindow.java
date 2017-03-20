package com.example.qiu.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {

    Button button4;
    EditText editText;
    ListView chatList;
    ArrayList<String> messages = new ArrayList<String>();
    protected static final String ACTIVITY_NAME = "ChatWindow";
    protected ChatDatabaseHelper dbHelper;
    boolean frameExisted = false;
    MessageFragment M = null;
    ChatAdapter messageAdapter = null;
    Cursor results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        FrameLayout f = (FrameLayout) findViewById(R.id.frame1);
        if (f != null)
        {
            frameExisted = true;
        }
        else
        {
            frameExisted = false;

        }



        dbHelper = new ChatDatabaseHelper(this);

        final SQLiteDatabase db = dbHelper.getWritableDatabase();
         results = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},null, null, null, null, null, null);
        int rows = results.getCount();
        results.moveToFirst();
        while(!results.isAfterLast()) {
           messages.add(results.getString(results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + results.getString( results.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            results.moveToNext();
        }

        button4 = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText4);
        chatList = (ListView) findViewById(R.id.ChatList);

        messageAdapter = new ChatAdapter(this);
        chatList.setAdapter(messageAdapter);

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bun = new Bundle();
                bun.putLong("ID", id );
                String msg = chatList.getItemAtPosition(position).toString();
                bun.putString("Msg", msg );

                if (frameExisted)
                {
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    MessageFragment m = new MessageFragment(ChatWindow.this);
                    M = m;
                    m.setArguments(bun);
                    if (M != null)
                        ft.replace(R.id.frame1, m);
                    else
                    {
                        ft.add(R.id.frame1, m);
                        M = m;
                    }
                    ft.commit();
                }
                else
                {
                    Intent intnt = new Intent(ChatWindow.this, MessageDetails.class);
intnt.putExtras(bun);
                   // intnt.putExtra("ID" , id);
                   //intnt.putExtra("Msg", msg);

                    startActivityForResult(intnt, 5);



                }

            }
        }
        );

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newMessage = editText.getText().toString();
                messages.add(newMessage);

                messageAdapter.notifyDataSetChanged();
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());
                db.insert(ChatDatabaseHelper.DATABASE_NAME, "", values);

                editText.setText("");

            }
        });

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + results.getColumnCount() );
       for (int i=0; i< results.getColumnCount(); i++){
           System.out.println(results.getColumnName(i));
        }
    }


    public class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        public long getItemId(int position)
        {
             results.moveToPosition(position);
            return results.getLong( results.getColumnIndex(ChatDatabaseHelper.KEY_ID) );
        }

        @Override
        public int getCount() {
            return messages.size();
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return messages.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;

            if (position%2 == 0)
            {

                result = inflater.inflate(R.layout.chat_row_incoming,null);

            }else {

                result = inflater.inflate(R.layout.chat_row_outgoing,null);
            }

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5)
            Log.i(ACTIVITY_NAME, "Returned to ChatWindow.onActivityResult");

        Long deleteId = data.getLongExtra("DeleteID", -1);
        if (resultCode == Activity.RESULT_OK)
        {
            deleteListMessage(deleteId);

            //int duration = Toast.LENGTH_LONG;
            //Toast toast = Toast.makeText(ChatWindow.this, "MessageDetails passed: " + deleteId.toString(), duration);
            //toast.show();
        }

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ChatDatabaseHelper.DATABASE_NAME, "id="+deleteId , null);
        messages.clear();
        results = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},null, null, null, null, null, null);
        int rows = results.getCount();
        results.moveToFirst();
        while(!results.isAfterLast()) {
            messages.add(results.getString(results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + results.getString( results.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            results.moveToNext();
        }


    }

    public void deleteListMessage(Long idx)
    {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(ChatDatabaseHelper.DATABASE_NAME, "id="+idx , null);
        messages.clear();
        results = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},null, null, null, null, null, null);
        int rows = results.getCount();
        results.moveToFirst();
        while(!results.isAfterLast()) {
            messages.add(results.getString(results.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + results.getString( results.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            results.moveToNext();
        }

        messageAdapter.notifyDataSetChanged();

    }

    public void removeFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(M);
        ft.commit();


    }

    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }


}
