package com.example.hautran.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hautran.myapplication.presentation.AbstractActivity;
import com.example.hautran.myapplication.presentation.BaseActivity;


public abstract class AbstractAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_LOAD_MORE = -1;

    protected View customLoadMoreView = null;
    protected LayoutInflater mInflater;
    protected AbstractActivity mActivity;
    private boolean isHasNext = true;

    protected ItemRecyclerListListener mListener;

    public AbstractAdapter(AbstractActivity mActivity) {
        this.mActivity = mActivity;
        mInflater = LayoutInflater.from(mActivity);
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public void setIsHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
//        enableLoadMore(isHasNext);
    }

    public void setListener(ItemRecyclerListListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_LOAD_MORE) {
            return new LoadMoreVH(customLoadMoreView);
        }else {
            return OnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(!(holder instanceof LoadMoreVH))
            OnBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int footer = 0;
        if (customLoadMoreView != null) footer++;
        return setItemCount() > 0?setItemCount() + footer : setItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() -1 && customLoadMoreView != null)
            return TYPE_LOAD_MORE;
        else
            return setItemViewType(position);
    }


    /**
     * declare all abstract method.
     */
    protected abstract RecyclerView.ViewHolder OnCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void OnBindViewHolder(RecyclerView.ViewHolder holder, int position);

    protected abstract int setItemCount();

    protected abstract int setItemViewType(int position);

    /**
     * Using a custom LoadMoreView
     *
     * @param loadMoreView
     */
    public void setCustomLoadMoreView(View loadMoreView) {
        customLoadMoreView = loadMoreView;
    }

    static class LoadMoreVH extends RecyclerView.ViewHolder{

        public LoadMoreVH(View itemView) {
            super(itemView);
        }
    }

    public interface ItemRecyclerListListener{
        public void onItemClick(Object object, int position);
    }
}
