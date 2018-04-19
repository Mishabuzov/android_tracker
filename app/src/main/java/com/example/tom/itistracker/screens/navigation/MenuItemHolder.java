package com.example.tom.itistracker.screens.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.screens.navigation.MenuAdapter.ClickCallback;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

class MenuItemHolder extends RecyclerView.ViewHolder {

    @BindColor(R.color.selected_menu_item) int mSelectedLayoutColor;

    @BindColor(R.color.unselected_menu_item) int mUnSelectedLayoutColor;

//    @BindColor(R.color.colorMenuItemUnselectedText) int mUnselectedTextColor;

//    @BindColor(R.color.colorMenuItemUnselectedLayout) int mUnselectedLayoutColor;

    @BindView(R.id.menu_item_name) TextView mItemNameTv;

    @BindView(R.id.menu_layout) RelativeLayout mMenuLayout;

    private MenuItemCallback mMenuItemCallback;

    private ClickCallback mClickCallback;


    MenuItemHolder(@NonNull View itemView,
                   @NonNull MenuItemCallback menuItemCallback,
                   @NonNull ClickCallback clickCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mMenuItemCallback = menuItemCallback;
        mClickCallback = clickCallback;
    }

    public void bind(@NonNull MenuItem item) {
        mItemNameTv.setText(item.getItemName());
        mMenuLayout.setOnClickListener(view -> {
            unselectMenuItems();
            selectMenuItem();
            mClickCallback.onMenuItemChosen(item);
        });
    }

    private void selectMenuItem() {
//        mItemNameTv.setTextColor(mSelectedTextColor);
        mMenuLayout.setBackgroundColor(mSelectedLayoutColor);
        saveSelectedValues();
    }

    private void unselectMenuItems() {
        mMenuItemCallback.getSelectedMenuLayout().setBackgroundColor(mUnSelectedLayoutColor);
//        mMenuItemCallback.getSelectedMenuTv().setTextColor(mUnselectedTextColor);
    }

    private void saveSelectedValues() {
     /*   RelativeLayout selectedLayout = mMenuLayout;
        TextView selectedTv = mItemNameTv;*/
        mMenuItemCallback.saveSelectedItem(mMenuLayout);
    }

    interface MenuItemCallback {

        void saveSelectedItem(RelativeLayout selectedLayout);

        RelativeLayout getSelectedMenuLayout();

    }

}
