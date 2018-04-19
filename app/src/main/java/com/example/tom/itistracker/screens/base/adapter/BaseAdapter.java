package com.example.tom.itistracker.screens.base.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.tools.Function;
import com.example.tom.itistracker.widgets.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<Item> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements NetworkErrorViewHolder.NetworkHolderCallback {

    protected static final int TYPE_ITEM_VIEW = 0;

    protected static final int TYPE_FOOTER_PAGINATION_PROGRESS = 1;

    protected static final int TYPE_NETWORK_ERROR_VIEW = 2;

    private final List<Item> mItems = new ArrayList<>();

    protected boolean mIsPaginationInProgress;

    protected boolean mIsNetworkError;

    private AdapterCallback mReloadCallback;

    @Nullable
    private OnItemClickListener<Item> mOnItemClickListener;

    private final View.OnClickListener mInternalListener = (view) -> {
        if (mOnItemClickListener != null) {
            int position = (int) view.getTag();
            Item item = mItems.get(position);
            mOnItemClickListener.onItemClick(item);
        }
    };

    @Nullable
    private EmptyRecyclerView mRecyclerView;

    public BaseAdapter(@NonNull List<Item> items) {
        mItems.addAll(items);
    }

    public BaseAdapter(@NonNull List<Item> items,
                       @NonNull AdapterCallback callback) {
        mItems.addAll(items);
        mReloadCallback = callback;
    }

    public void attachToRecyclerView(@NonNull EmptyRecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setAdapter(this);
        refreshRecycler();
    }

    public final void add(@NonNull Item value) {
        mItems.add(value);
        refreshRecycler();
    }

    public final void changeDataSet(@NonNull List<Item> values) {
        mItems.clear();
        mItems.addAll(values);
        refreshRecycler();
    }

    public final void clear() {
        mItems.clear();
        refreshRecycler();
    }

    public void refreshRecycler() {
        notifyDataSetChanged();
        if (mRecyclerView != null) {
            mRecyclerView.checkIfEmpty();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER_PAGINATION_PROGRESS:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.footer_progress_bar, parent, false);
                return new PaginationViewHolder(view);
            case TYPE_NETWORK_ERROR_VIEW:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_network_error, parent, false);
                return new NetworkErrorViewHolder(view, mReloadCallback.getReloadFunction(), this);
            default:
                return onCreateDefaultViewHolder(parent, viewType);
        }
    }

    protected abstract RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType);

    @CallSuper
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(mInternalListener);
        if (holder.getItemViewType() == TYPE_NETWORK_ERROR_VIEW) {
            NetworkErrorViewHolder h = ((NetworkErrorViewHolder) holder);
            h.bind();
        }
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener<Item> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    public Item getItem(int position) {
        return mItems.get(position);
    }

    public List<Item> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @MainThread
    public void showNetworkErrorView(boolean isEnable) {
        if (mIsNetworkError == isEnable) {
            return;
        }
        if (mIsPaginationInProgress) {
            enablePaginationView(false);
        }
        mIsNetworkError = isEnable;
        configExtraItem(isEnable);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && mIsPaginationInProgress) {
            return TYPE_FOOTER_PAGINATION_PROGRESS;
        } else if (position == getItemCount() - 1 && mIsNetworkError) {
            return TYPE_NETWORK_ERROR_VIEW;
        }
        return TYPE_ITEM_VIEW;
    }

    private void configExtraItem(boolean isEnable) {
        if (isEnable) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }

    public void enablePaginationView(boolean isEnable) {
        if (mIsPaginationInProgress == isEnable) {
            return;
        }
        if (mIsNetworkError) {
            showNetworkErrorView(false);
        }
        mIsPaginationInProgress = isEnable;
//        Logger.d("Layout manager items count: " + mRecyclerView.getLayoutManager().getItemCount());
//        Logger.d("Adapter items count: " + mRecyclerView.getLayoutManager().getItemCount());
        configExtraItem(isEnable);
    }

    public interface OnItemClickListener<T> {

        void onItemClick(@NonNull T item);

    }

    public interface AdapterCallback {
        Function getReloadFunction();
    }

    private class PaginationViewHolder extends RecyclerView.ViewHolder {
        private PaginationViewHolder(View itemView) {
            super(itemView);
        }
    }

}
