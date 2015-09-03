package com.kilr.fizzy;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kilr.fizzy.messaging.MessageItemTouchHelperViewHolder;

/**
 * Created by rancohen on 9/3/15.
 */
public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder> {

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false);
        FriendViewHolder itemViewHolder = new FriendViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        FriendsList fl = FriendsList.getInstance();
        fl.getFriendsList().get(position);
    }

    @Override
    public int getItemCount() {
        FriendsList fl = FriendsList.getInstance();
        return fl.getFriendsList().size();
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder implements
            MessageItemTouchHelperViewHolder {

        public final TextView textView;
        public final ImageView handleView;

        public FriendViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.image);
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
