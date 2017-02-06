package com.example.qiu.androidlabs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx){
            super(ctx,0);
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
    };

    Button button4;
    EditText editText;
    ListView chatList;

    ArrayList<String> messages = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        button4 = (Button) findViewById(R.id.btnSend);
        editText = (EditText) findViewById(R.id.editText4);
        chatList = (ListView) findViewById(R.id.ChatList);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatList.setAdapter(messageAdapter);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newMessage = editText.getText().toString();
                messages.add(newMessage);

                messageAdapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }


}
