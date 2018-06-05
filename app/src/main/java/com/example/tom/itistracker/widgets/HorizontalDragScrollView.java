package com.example.tom.itistracker.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

public class HorizontalDragScrollView extends HorizontalScrollView {

    private OnScrollViewListener mListener;

    public HorizontalDragScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScrollChanged();
        }
    }

    public void setOnScrollViewListener(OnScrollViewListener listener) {
        mListener = listener;
    }

    public interface OnScrollViewListener {
        void onScrollChanged();
    }

}
