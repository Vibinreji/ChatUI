package com.chatui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdaperCallback {

    ArrayList<Message> chatlist = null;
    ChatAdapter adapter = null;
    RecyclerView chat_list;
    EditText editText;
    ImageView mic;
    Button send,loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        send = (Button) findViewById(R.id.send);
        mic = (ImageView) findViewById(R.id.mic);
        editText = (EditText) findViewById(R.id.edittext);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        /*
         * When stackFromEnd is true the list fills its content
         * starting from the bottom of the view.
         */
        layoutManager.setStackFromEnd(false);
        chat_list.setLayoutManager(layoutManager);
        chatlist = new ArrayList<>();
        adapter = new ChatAdapter(this, chatlist, this);
        chat_list.setAdapter(adapter);
        send.setOnClickListener(this);
        mic.setOnClickListener(this);
    }

        @Override
        public void onClick (View v){
            int id = v.getId();
            String text = null;
            Message model, loader;

            if (editText != null) {
                text = editText.getText().toString().trim();
            }
            int count = adapter.getItemCount();

            if (!chatlist.isEmpty()) {
                if (chatlist.get(count - 1).getSendertype().equalsIgnoreCase("loader")) {
                    chatlist.remove(count - 1);
                    adapter.notifyItemRemoved(count);


                }

            }
//        count will give us the position(last) where we will insert item


            if (id == R.id.send) {
                if (text != null && text.length() != 0) {

                    send(text);
                }
            } else if (id == R.id.mic) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, 1);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(MainActivity.this, "Oops! Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                }



                /*
                 * void scrollToPosition(int position) tells layout manager to scroll recyclerView
                 * to given position
                 */

            }
        }

        @Override
        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case 1: {
                    if (resultCode == Activity.RESULT_OK && null != data) {
                        String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                        reciever(yourResult);
                    }
                    break;
                }
            }
        }

    public void send(String query) {

        Message model = new Message();
        model.setMessage(query);

        model.setSendertype("send");
        chatlist.add(model);
        adapter.notifyItemInserted(adapter.getItemCount());

        chat_list.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.addloader();
                chat_list.scrollToPosition(chat_list.getAdapter().getItemCount() - 1);

            }
        }, 1000);

        chat_list.scrollToPosition(chat_list.getAdapter().getItemCount() - 1);
        editText.setText("");
    }

    public void reciever(String query) {

        Message model = new Message();
        model.setMessage(query);

        model.setSendertype("reciever");
        chatlist.add(model);
        adapter.notifyItemInserted(adapter.getItemCount());

        chat_list.scrollToPosition(chat_list.getAdapter().getItemCount() - 1);
        editText.setText("");
    }
    @Override
    public void onUserMessageItemClick(Message message) {

    }

    @Override
    public void onBotMessageItemClick(Message message) {

    }

    @Override
    public void onUserMessageItemLongClick(Message message) {

    }

    @Override
    public void onBotMessageItemLongClick(Message message) {

    }
}
