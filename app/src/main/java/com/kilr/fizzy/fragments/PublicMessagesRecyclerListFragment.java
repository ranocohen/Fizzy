package com.kilr.fizzy.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kilr.fizzy.MainActivity;
import com.kilr.fizzy.R;
import com.kilr.fizzy.messaging.MessageItemTouchHelperCallback;
import com.kilr.fizzy.messaging.MessagesRecyclerListAdapter;
import com.kilr.fizzy.models.Message;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by rancohen on 8/18/15.
 */
public class PublicMessagesRecyclerListFragment extends Fragment {

    private ItemTouchHelper mItemTouchHelper;

    MessagesRecyclerListAdapter adapter;
    RecyclerView recyclerView;

    public PublicMessagesRecyclerListFragment() {

    }

    public void setData(ArrayList<Message> messages) {
        adapter.setmMessages(messages);
        adapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_public_messages,container,false);


        adapter = new MessagesRecyclerListAdapter(getActivity(), ((MainActivity)getActivity()).getmMessages());

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Timber.i("recylcled");
                return false;
            }
        });
      /*  ItemTouchHelper.Callback callback = new MessageItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);*/
        adapter.notifyDataSetChanged();
        return v;
    }

    public MessagesRecyclerListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MessagesRecyclerListAdapter adapter) {
        this.adapter = adapter;
        this.adapter.notifyDataSetChanged();
    }



}
