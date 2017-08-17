package com.example.hautran.myapplication.presentation.channels;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.adapter.ChannelAdapter;
import com.example.hautran.myapplication.presentation.AbstractFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends AbstractFragment implements ChannelsView{

    @BindView(R.id.edtCreateChannel)
    AppCompatEditText edtCreateChannel;

    @BindView(R.id.tvCreate)
    AppCompatTextView tvCreate;

    @BindView(R.id.reChannels)
    RecyclerView reChannels;

    private ChannelsPresenter presenter;
    private final String TAG = "AllChannelsActivity";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> channelsData = new ArrayList<>();
    private ChannelAdapter adapter;

    @Override
    protected void initTittleBar() {
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_channels;
    }

    @Override
    protected void initViewFragment() {
        presenter = new ChannelsPresenter(this);
        adapter = new ChannelAdapter(mActivity);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        reChannels.setLayoutManager(layoutManager);
        reChannels.setAdapter(adapter);
        reChannels.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        getAllChannelsData();
    }

    @Override
    public void moveToNextScreen() {

    }

    @Override
    public String getKeyToCreateChannel() {
        return edtCreateChannel.getText().toString();
    }

    @Override
    public void onCreateChannelSuccess() {
        dialogHelper.alert(null, "Created a New channel");
    }

    @Override
    public void onCreateChannelFailed(String mess) {
        dialogHelper.alert(null, mess);
    }

    @Override
    public void onLoading() {
        dialogHelper.showProgress();
    }

    @Override
    public void onDismissLoading() {
        dialogHelper.dismissProgress();
    }
//
    @OnClick(R.id.tvCreate)
    public void createChannelClick() {
        presenter.createANewChannel();
    }

    private void getAllChannelsData() {
        onLoading();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Channel");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "" + dataSnapshot.getChildrenCount());
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                channelsData.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String post = postSnapshot.getValue(String.class);
                    channelsData.add(post);
                    Log.e("Get Data", post);
                }
                adapter.setData(channelsData);
                adapter.notifyDataSetChanged();
                onDismissLoading();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                onDismissLoading();
            }
        });
    }
}
