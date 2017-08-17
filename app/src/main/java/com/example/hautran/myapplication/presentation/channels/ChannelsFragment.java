package com.example.hautran.myapplication.presentation.channels;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.adapter.ChannelAdapter;
import com.example.hautran.myapplication.adapter.DividerItemDecoration;
import com.example.hautran.myapplication.models.Room;
import com.example.hautran.myapplication.presentation.AbstractFragment;
import com.example.hautran.myapplication.presentation.chat.GroupChatFragment;
import com.example.hautran.myapplication.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelsFragment extends AbstractFragment implements ChannelsView, ChannelAdapter.setChannelItemClick{

    @BindView(R.id.edtCreateChannel)
    AppCompatEditText edtCreateChannel;

    @BindView(R.id.tvCreate)
    AppCompatTextView tvCreate;

    @BindView(R.id.reChannels)
    RecyclerView reChannels;

    private ChannelsPresenter presenter;
    private final String TAG = ChannelsFragment.class.getSimpleName();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ArrayList<String> channelsData = new ArrayList<>();
    private ArrayList<String> idRooms = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ChannelAdapter adapter;
    String idGroup = "";
    private Room room;

    @Override
    protected void initTittleBar() {
        mActivity.setTitle("Power Chat");
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
    }



    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_channels;
    }

    @Override
    protected void initViewFragment() {
        room=new Room();
//        for (String id : listIDChoose) {
//            room.member.add(id);
//        }

        idGroup = (Constants.UID + System.currentTimeMillis()).hashCode() + "";
        presenter = new ChannelsPresenter(this);
        adapter = new ChannelAdapter(mActivity);
        adapter.setChannelItemClickListener(this);
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
                    Room post = postSnapshot.getValue(Room.class);
                    channelsData.add(post.groupInfo.get("name"));
                    rooms.add(post);
                    idRooms.add(postSnapshot.getKey().toString());
                    Log.e(TAG, post.groupInfo.get("name"));
                    Log.e(TAG, postSnapshot.getKey().toString());
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

    @Override
    public void setChannelItemClickListener(int position) {
        Log.d(TAG, "pos:"+position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("nameOfRoom", (Parcelable) rooms.get(position));
        bundle.putString("idRooms",idRooms.get(position));
        addToBackStack(new GroupChatFragment(), bundle);
    }
}
