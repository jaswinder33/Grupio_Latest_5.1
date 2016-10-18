package com.grupio.Utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.grupio.R;

/**
 * Created by JSN on 20/9/16.
 */
public class CustomTextView extends TextView{
    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public void init(AttributeSet attrs){

        if(attrs != null){
            TypedArray a =  getContext().obtainStyledAttributes(attrs, R.styleable.FontedText);
            String fontName =    a.getString(R.styleable.FontedText_font);
            if(fontName != null){
                Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/"+fontName);
                setTypeface(typeface);
            }

            a.recycle();

        }
    }


}
