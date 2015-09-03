package com.kilr.fizzy;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kilr.fizzy.messaging.MessagesRecyclerListAdapter;

/**
 * Created by rancohen on 9/3/15.
 */
public class FriendsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list_layout);

        FriendsListAdapter adapter = new FriendsListAdapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.notifyDataSetChanged();

    }
}
