package com.chatui;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context = null;
    ArrayList<Message> chatList = null;
    private final int SENT_MESSAGE = 0, RECEIVED_MESSAGE = 1,LOADER = 3;
    DateUtils Utils;
    private AdaperCallback mCallback;


    public ChatAdapter(Context context, ArrayList<Message> chatList, AdaperCallback mCallback) {
        this.context = context;
        this.chatList = chatList;
        this.mCallback =mCallback;

    }

    /***
     *
     * @param position
     * @return the view type of the item at the position for the
     * purpose of recycling view
     *
     * By default it returns zero showing a single view type for the adapter.
     */
    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getSendertype().equalsIgnoreCase("send")) {
            return SENT_MESSAGE;
        }
        else if (chatList.get(position).getSendertype().equalsIgnoreCase("reciever"))
        {
            return RECEIVED_MESSAGE;
        }
        else if (chatList.get(position).getSendertype().equalsIgnoreCase("loader"))
        {
            return LOADER;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        if (viewType == SENT_MESSAGE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sent_message, parent, false);
            return new ChatAdapter.SentViewHolder(view);
        } else if (viewType == RECEIVED_MESSAGE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recieved_message, parent, false);
            return new ChatAdapter.RecieverViewHolder(view);
        }
        else if (viewType == LOADER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loader, parent, false);
            return new ChatAdapter.LoadingViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message model = chatList.get(position);

        switch (holder.getItemViewType()) {
            case SENT_MESSAGE:
                ((ChatAdapter.SentViewHolder) holder).bind(model);

                break;
            case RECEIVED_MESSAGE:
                ((ChatAdapter.RecieverViewHolder) holder).bind(model);
                break;
            case LOADER:
                ((ChatAdapter.LoadingViewHolder)holder).bind();
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /*
     * Here we have kept ID's of all the child row elements same.
     * But we can also create to different viewHolder classes
     * for different child rows.
     */
    public class SentViewHolder extends RecyclerView.ViewHolder {
        TextView texttosend;
        TextView date;
        public SentViewHolder(View itemView) {
            super(itemView);
            texttosend = (TextView) itemView.findViewById(R.id.message);
            date = (TextView) itemView.findViewById(R.id.date);
        }
            void bind(final Message message)
            {
                texttosend.setText(message.getMessage());

                date.setText(Utils.formatDateTime(System.currentTimeMillis()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onBotMessageItemClick(message);
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mCallback.onBotMessageItemLongClick(message);
                        return true;
                    }
                });

            }
        }

    public class RecieverViewHolder extends RecyclerView.ViewHolder {
        TextView texttosend;
        TextView date;
        public RecieverViewHolder(View itemView) {
            super(itemView);
            texttosend = (TextView) itemView.findViewById(R.id.message);
            date = (TextView) itemView.findViewById(R.id.date);
        }
            void bind(final Message message) {
                texttosend.setText(message.getMessage());

                date.setText(Utils.formatDateTime(System.currentTimeMillis()));
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mCallback.onBotMessageItemClick(message);
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mCallback.onBotMessageItemLongClick(message);
                        return true;
                    }
                });

            }
    }

     void addloader()
     {
         Message message = new Message();
         message.setSendertype("loader");
         chatList.add(message);
        notifyItemInserted(chatList.size() );

     }
    void removeLoader() {

        System.out.println("removeLoadingFooter ==================");
        Message message = new Message();
        message.setSendertype("loader");

        final int position = chatList.size() ;

        //chatList.remove(position);
        Handler handler = new Handler();

        final Runnable run = new Runnable() {
            public void run() {

                notifyItemRemoved(position);
            }
        };

        handler.post(run);
    }
    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        public LoadingViewHolder(View itemView) {
            super(itemView);

        }
        void bind()
        {

        }
    }
}