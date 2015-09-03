package com.kilr.fizzy.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kilr.fizzy.R;
import com.kilr.fizzy.messaging.MessageItemTouchHelperCallback;
import com.kilr.fizzy.messaging.MessagesRecyclerListAdapter;

/**
 * Created by rancohen on 8/18/15.
 */
public class PublicMessagesRecyclerListFragment extends Fragment {

    private ItemTouchHelper mItemTouchHelper;

    public PublicMessagesRecyclerListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_public_messages,container,false);

        MessagesRecyclerListAdapter adapter = new MessagesRecyclerListAdapter(getActivity());

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ItemTouchHelper.Callback callback = new MessageItemTouchHelperCallback(adapter);
        //mItemTouchHelper = new ItemTouchHelper(callback);
        //mItemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.notifyDataSetChanged();
        return v;
    }


}
