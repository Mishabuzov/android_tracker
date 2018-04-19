package com.example.tom.itistracker.screens.auth.base_auth;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;

import com.example.tom.itistracker.R;

public class AuthHelper {

    @NonNull
    private final TextChangedListener mTextChangedListener;

    public AuthHelper(@NonNull final TextChangedListener textChangedListener) {
        mTextChangedListener = textChangedListener;
    }

    public final void showErrorInInputLayout(TextInputLayout textInputLayout, String errorMessage) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(errorMessage);
    }

    private void hideErrorFromInputLayout(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

    public final void showPassword(EditText passwordEdit, Button showPasswordButton) {
        passwordEdit.setTransformationMethod(null);
        showPasswordButton.setText(R.string.content_login_btn_text_hide);
    }

    public final void hidePassword(EditText passwordEdit, Button showPasswordButton) {
        passwordEdit.setTransformationMethod(new PasswordTransformationMethod());
        showPasswordButton.setText(R.string.content_login_btn_text_show);
    }

    public void setTextWatcher(@NonNull final EditText editText,
                               @NonNull final TextInputLayout editInputLayout) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                hideErrorFromInputLayout(editInputLayout);
                mTextChangedListener.onTextChanged();
            }
        });
    }

    public interface TextChangedListener {
        void onTextChanged();
    }

}
