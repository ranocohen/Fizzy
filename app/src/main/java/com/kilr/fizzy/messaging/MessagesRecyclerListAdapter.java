package com.kilr.fizzy.messaging;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kilr.fizzy.R;
import com.kilr.fizzy.models.Message;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessagesRecyclerListAdapter extends RecyclerView.Adapter<MessagesRecyclerListAdapter.MessageViewHolder>
        implements MessageItemTouchHelperAdapter {

    private ArrayList<Message> mMessages = new ArrayList();

    public HashMap<String, ParseUser> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, ParseUser> users) {
        this.users = users;
    }

    HashMap<String, ParseUser> users = new HashMap<>();

    private Context mCon;

    public ArrayList<Message> getmMessages() {
        return mMessages;
    }

    public void setmMessages(ArrayList<Message> mMessages) {
        this.mMessages = mMessages;
    }


    public MessagesRecyclerListAdapter(Context context, ArrayList<Message> messages) {
        this.mCon = context;
        mMessages = messages;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);
        MessageViewHolder messageViewHolder = new MessageViewHolder(view);
        return messageViewHolder;
    }


    @Override
    public void onBindViewHolder(MessageViewHolder messageViewHolder, int i) {

        ParseUser pu = users.get(mMessages.get(i).getFrom().getObjectId());

        if(pu!=null) {
            if (pu.getString("name") != null)
                messageViewHolder.mUserName.setText(pu.getString("name"));
            else
                messageViewHolder.mUserName.setText("anonymous");

            if (pu.getString("fbUserId") != null) {
                String imgUrl = "https://graph.facebook.com/" + pu.getString("fbUserId") + "/picture?type=small";
                Glide.with(mCon).load(imgUrl).into(messageViewHolder.mUserImage);
            }
        }

        if(mMessages.get(i).getCreatedAt()!=null) {
            Date date = mMessages.get(i).getCreatedAt();
            String time = String.valueOf(date.getHours())+ ":" + String.valueOf(date.getMinutes());
            messageViewHolder.mTime.setText(time);
        }else
            messageViewHolder.mTime.setText("");

        messageViewHolder.mMessageText.setText(mMessages.get(i).getBody());


    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mMessages.remove(position);
        notifyItemRemoved(position);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements
            MessageItemTouchHelperViewHolder {

        public final TextView mMessageText;
        public final CircleImageView mUserImage;
        public final TextView mUserName;
        public final TextView mTime;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mMessageText = (TextView) itemView.findViewById(R.id.message_item_message_text);
            mUserImage = (CircleImageView) itemView.findViewById(R.id.message_item_user_image);
            mUserName = (TextView) itemView.findViewById(R.id.message_item_user_name);
            mTime = (TextView) itemView.findViewById(R.id.message_item_time_text);

        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
