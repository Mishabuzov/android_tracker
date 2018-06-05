package com.example.tom.itistracker.tools.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.tom.itistracker.R;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.tools.functions.Function;
import com.example.tom.itistracker.tools.functions.ResultFunction;
import com.squareup.picasso.Picasso;

import java.util.List;

public final class UiUtils {

    private AndroidUtils mAndroidUtils;

    public UiUtils(@NonNull final AndroidUtils androidUtils) {
        mAndroidUtils = androidUtils;
    }

    public void hideSoftKeyboard(@NonNull final Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus() && null != activity.getCurrentFocus().getWindowToken())
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setExecutorAvatar(@Nullable final User executor, @NonNull final ImageView userImageView) {
        if (executor == null) {
            userImageView.setImageDrawable(
                    ContextCompat.getDrawable(userImageView.getContext(), R.drawable.unidentified));
        } else if (executor.getAvatarUrl() == null) {
            userImageView.setImageDrawable(
                    ContextCompat.getDrawable(userImageView.getContext(), R.drawable.user_pic));
        } else {
            Picasso.get().load(executor.getAvatarUrl()).into(userImageView);
        }
    }

    public void createConfirmDialog(@NonNull final Context context,
                                    @NonNull final Function nextFunction,
                                    @NonNull final String title) {
        new MaterialDialog.Builder(context)
                .title(title)
                .positiveText(R.string.standard_dialog_positive)
//                .positiveColor(ContextCompat.getColor(context, R.color.black))
                .negativeText(R.string.standard_dialog_negative)
//                .negativeColor(ContextCompat.getColor(context, R.color.black))
                .onPositive((dialog, which) -> {
                    dialog.dismiss();
                    nextFunction.action();
                })
                .onNegative((dialog, which) -> dialog.dismiss())
                .dismissListener(DialogInterface::dismiss)
                .show();
    }

    public void createListDialog(@NonNull final Context context,
                                 @StringRes final int title,
                                 @NonNull final List<String> items,
                                 @NonNull final ResultFunction<Integer> onChoosingFunction) {
        new MaterialDialog.Builder(context)
                .title(title)
                .items(items)
                .itemsCallback((dialog, view, which, text) -> {
                    dialog.dismiss();
                    onChoosingFunction.action(which);
                })
                .dismissListener(DialogInterface::dismiss)
                .show();
    }

    public void showToast(@NonNull final String message, @NonNull Context context) {
        mAndroidUtils.runOnUIThread(() ->
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

    public void showToast(@StringRes final int message, @NonNull Context context) {
        mAndroidUtils.runOnUIThread(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
    }

}
