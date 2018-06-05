package com.example.tom.itistracker.screens.navigation;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.local.MenuItem;
import com.example.tom.itistracker.screens.navigation.MenuAdapter.ClickCallback;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tom.itistracker.tools.Constants.FIRST_SCREEN_ITEM;

class MenuItemHolder extends RecyclerView.ViewHolder {

    @BindColor(R.color.selected_menu_item) int mSelectedLayoutColor;

    @BindColor(R.color.unselected_menu_item) int mUnSelectedLayoutColor;

//    @BindColor(R.color.colorMenuItemUnselectedText) int mUnselectedTextColor;

//    @BindColor(R.color.colorMenuItemUnselectedLayout) int mUnselectedLayoutColor;

    @BindView(R.id.menu_item_name) TextView mItemNameTv;

    @BindView(R.id.menu_layout) LinearLayout mMenuLayout;

    @BindView(R.id.menu_icon) ImageView mMenuIcon;

    @NonNull
    private final MenuItemCallback mMenuItemCallback;

    @NonNull
    private final ClickCallback mClickCallback;

    MenuItemHolder(@NonNull final View itemView,
                   @NonNull final MenuItemCallback menuItemCallback,
                   @NonNull final ClickCallback clickCallback) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mMenuItemCallback = menuItemCallback;
        mClickCallback = clickCallback;
    }

    public void bind(@NonNull final MenuItem item) {
        saveFirstScreenItem(item);
        mItemNameTv.setText(item.getItemName());
        mMenuIcon.setImageDrawable(ContextCompat.getDrawable(mMenuIcon.getContext(), item.getIcon()));
        mMenuLayout.setOnClickListener(view -> tryToChangeSelectedItem(item));
    }

    private void saveFirstScreenItem(@NonNull final MenuItem item) {
        if (item == FIRST_SCREEN_ITEM) {
            selectMenuItem();
        }
    }

    private void tryToChangeSelectedItem(@NonNull final MenuItem item) {
        if (item.isSelectable()) {
            unselectMenuItems();
            selectMenuItem();
        }
        mClickCallback.onMenuItemChosen(item);
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

        void saveSelectedItem(ViewGroup selectedLayout);

        ViewGroup getSelectedMenuLayout();

    }

}
