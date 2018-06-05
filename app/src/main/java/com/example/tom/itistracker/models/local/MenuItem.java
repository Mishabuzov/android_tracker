package com.example.tom.itistracker.models.local;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.example.tom.itistracker.R;

import java.util.ArrayList;
import java.util.List;

public enum MenuItem {

    HEADER,
    SPRINTS(R.string.menu_sprints, R.drawable.ic_sprint, true),
    PROJECT_CHOOSING(R.string.menu_title_project_changing, R.drawable.ic_project, false),
    LOG_OUT(R.string.menu_logout, R.drawable.ic_exit, false);

    private static final List<MenuItem> sMenuItems = setItems();

    private int mName;

    private int mIcon;

    private boolean mIsSelectable;

    MenuItem(@StringRes final int name,
             @DrawableRes final int icon,
             final boolean isSelectable) {
        mName = name;
        mIcon = icon;
        mIsSelectable = isSelectable;
    }

    MenuItem() {
    }

    public static List<MenuItem> setItems() {
        List<MenuItem> menuItemList = new ArrayList<>();
        menuItemList.add(HEADER);
        menuItemList.add(SPRINTS);
        menuItemList.add(PROJECT_CHOOSING);
        menuItemList.add(LOG_OUT);
        return menuItemList;
    }

    @NonNull
    public static List<MenuItem> getMenuItems() {
        return sMenuItems;
    }

    @StringRes
    public final int getItemName() {
        return mName;
    }

    @DrawableRes
    public final int getIcon() {
        return mIcon;
    }

    public boolean isSelectable() {
        return mIsSelectable;
    }
}
