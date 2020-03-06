package com.benjamin.adachimitsurumanga.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class BoldTextView extends TextView {
    public BoldTextView(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public BoldTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public BoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @SuppressLint("NewApi")
    public BoldTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/QanelasSoftDEMO-ExtraBold.otf");
        setTypeface(custom_font);
    }
}