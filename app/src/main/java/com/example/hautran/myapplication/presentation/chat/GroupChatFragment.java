package com.example.hautran.myapplication.presentation.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hautran.myapplication.R;
import com.example.hautran.myapplication.models.Consersation;
import com.example.hautran.myapplication.models.Message;
import com.example.hautran.myapplication.models.Room;
import com.example.hautran.myapplication.presentation.AbstractFragment;
import com.example.hautran.myapplication.utils.Constants;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hautran on 17/08/17.
 */

public class GroupChatFragment extends AbstractFragment {

    @BindView(R.id.editWriteMessage)
    EditText editWriteMessage;

    @BindView(R.id.btnSend)
    AppCompatTextView btnSend;

    @BindView(R.id.recyclerChat)
    RecyclerView recyclerChat;

    @BindView(R.id.imgAttach)
    ImageButton imgAttach;


    private final String TAG = "GroupChatFragment";
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private ListMessageAdapter adapter;
    private String roomId;
    private ArrayList<CharSequence> idFriend;
    private Consersation consersation;
    private Room room;

    @Override
    protected void initTittleBar() {
        ActionBar actionBar = mActivity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mActivity.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getViewLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initViewFragment() {
        Bundle extras = getArguments();
        if (extras != null) {
            room = extras.getParcelable("nameOfRoom");
            roomId = extras.getString("idRooms");
            mActivity.setTitle(room.groupInfo.get("name").toString());
        }

        initView();
    }

    private void initView() {
        consersation = new Consersation();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        adapter = new ListMessageAdapter(mActivity, consersation);
        recyclerChat.setLayoutManager(layoutManager);
        FirebaseDatabase.getInstance().getReference().child("message/" + roomId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getValue() != null) {
                    HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                    Message newMessage = new Message();
                    newMessage.idSender = (String) mapMessage.get("idSender");
                    newMessage.idReceiver = (String) mapMessage.get("idReceiver");
                    newMessage.text = (String) mapMessage.get("text");
                    newMessage.timestamp = (long) mapMessage.get("timestamp");
                    consersation.getListMessageData().add(newMessage);
                    adapter.notifyDataSetChanged();
                    layoutManager.scrollToPosition(consersation.getListMessageData().size() - 1);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        recyclerChat.setAdapter(adapter);
    }

    @Override
    public void moveToNextScreen() {

    }

    class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private Consersation consersation;


        public ListMessageAdapter(Context context, Consersation consersation) {
            this.context = context;
            this.consersation = consersation;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_FRIEND_MESSAGE) {
                View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
                return new ItemMessageFriendHolder(view);
            } else if (viewType == VIEW_TYPE_USER_MESSAGE) {
                View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
                return new ItemMessageUserHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemMessageFriendHolder) {
                ((ItemMessageFriendHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);

            } else if (holder instanceof ItemMessageUserHolder) {
                ((ItemMessageUserHolder) holder).txtContent.setText(consersation.getListMessageData().get(position).text);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return consersation.getListMessageData().get(position).idSender.equals(Constants.UID) ? VIEW_TYPE_USER_MESSAGE : VIEW_TYPE_FRIEND_MESSAGE;
        }

        @Override
        public int getItemCount() {
            if (consersation != null) {
                return consersation.getListMessageData().size();
            } else {
                return 0;
            }
        }
    }

    class ItemMessageUserHolder extends RecyclerView.ViewHolder {
        public TextView txtContent;

        public ItemMessageUserHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.textContentUser);
        }
    }

    class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
        public TextView txtContent;

        public ItemMessageFriendHolder(View itemView) {
            super(itemView);
            txtContent = (TextView) itemView.findViewById(R.id.textContentFriend);
        }
    }

    @OnClick(R.id.btnSend)
    public void onSendMessage() {
        String content = editWriteMessage.getText().toString().trim();
        if (content.length() > 0) {
            editWriteMessage.setText("");
            Message newMessage = new Message();
            newMessage.text = content;
            newMessage.idSender = Constants.UID;
            newMessage.idReceiver = roomId;
            newMessage.timestamp = System.currentTimeMillis();
            FirebaseDatabase.getInstance().getReference().child("message/" + roomId).push().setValue(newMessage);
        }
    }

    @OnClick(R.id.imgAttach)
    public void onSendFile() {

    }
}