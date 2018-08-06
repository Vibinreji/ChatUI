package com.chatui;
public interface AdaperCallback {

    void onUserMessageItemClick(Message message);


    void onBotMessageItemClick(Message message);

    void onUserMessageItemLongClick(Message message);


    void onBotMessageItemLongClick(Message message);
}
