package com.kilr.fizzy.messaging;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kilr.fizzy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessagesRecyclerListAdapter extends RecyclerView.Adapter<MessagesRecyclerListAdapter.ItemViewHolder>
        implements MessageItemTouchHelperAdapter {

    private final List<String> mItems = new ArrayList<>();
    private Context mCon;
    public MessagesRecyclerListAdapter(Context context) {
        this.mCon = context;
        String[] names = new String[] {"Idan" ,"Lidan","Ken","Ran"};
        Random random = new Random();
        for(int i =0 ;i<50;i++) {
            int rand = random.nextInt(4);
            mItems.add(names[rand]);
        }
    }

    @Override
    public MessagesRecyclerListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }


    @Override
    public void onBindViewHolder(MessagesRecyclerListAdapter.ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.textView.setText(mCon.getString(R.string.is_hanzir,mItems.get(i)));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            MessageItemTouchHelperViewHolder {

        public final TextView textView;
        //public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            //handleView = (ImageView) itemView.findViewById(R.id.handle);
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
