package com.example.tom.itistracker.screens.navigation;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.screens.base.adapter.BaseAdapter;

import java.util.List;

class MenuAdapter extends BaseAdapter<MenuItem> implements MenuItemHolder.MenuItemCallback {

    private static final int TYPE_ITEM_VIEW_HEADER = 3;

//    private TextView mSelectedMenuTv;

    private ViewGroup mSelectedMenuLayout;

    private final ClickCallback mClickCallback;

    private final User mUser;

    MenuAdapter(@NonNull final List<MenuItem> items,
                @NonNull final ClickCallback callback,
                @NonNull final User user) {
        super(items);
        mUser = user;
        mClickCallback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = super.getItemViewType(position);
        if (itemType == TYPE_ITEM_VIEW) {
            if (position == 0) {
                return TYPE_ITEM_VIEW_HEADER;
            } else {
                return TYPE_ITEM_VIEW;
            }
        }
        return itemType;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_VIEW_HEADER) {
            return new MenuHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_header, parent, false), mUser);
        } else {
            return new MenuItemHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false), this,
                    mClickCallback);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder.getItemViewType() == TYPE_ITEM_VIEW) {
            MenuItemHolder h = (MenuItemHolder) holder;
            h.bind(getItem(position));
        } else if (holder.getItemViewType() == TYPE_ITEM_VIEW_HEADER) {
            MenuHeaderHolder h = (MenuHeaderHolder) holder;
            h.bind();
        }
    }

    @Override
    public void saveSelectedItem(@NonNull ViewGroup selectedLayout) {
        mSelectedMenuLayout = selectedLayout;
//        mSelectedMenuTv = selectedTv;
    }

    /*@NonNull
    TextView getSelectedMenuTv() {
        return mSelectedMenuTv;
    }*/

    @NonNull
    @Override
    public ViewGroup getSelectedMenuLayout() {
        return mSelectedMenuLayout;
    }

    interface ClickCallback {
        void onMenuItemChosen(MenuItem item);
    }

}
