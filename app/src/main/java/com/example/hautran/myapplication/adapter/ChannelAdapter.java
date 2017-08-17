package com.example.hautran.myapplication.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hautran.myapplication.presentation.AbstractActivity;
import com.example.hautran.myapplication.presentation.BaseActivity;
import com.example.hautran.myapplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hautran on 16/08/17.
 */

public class ChannelAdapter extends AbstractAdapter {

    public ArrayList<String> channels;
    public AbstractActivity activity;

    public ChannelAdapter(AbstractActivity mActivity) {
        super(mActivity);
        channels = new ArrayList<>();
    }

    public void setData(ArrayList<String> data) {
        this.channels = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.channels.clear();
        notifyDataSetChanged();
    }

    public ArrayList<String> getData() {
        return channels;
    }

    @Override
    protected RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.channels_item, parent, false);
        return new ChannelsHolder(view);
    }

    @Override
    protected void OnBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChannelsHolder view = (ChannelsHolder) holder;
        String channelTitle = channels.get(position).toString();
        view.tvTitle.setText(channelTitle);
    }

    @Override
    protected int setItemCount() {
        return channels.size();
    }

    @Override
    protected int setItemViewType(int position) {
        return position;
    }

    static class ChannelsHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;


        public ChannelsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
