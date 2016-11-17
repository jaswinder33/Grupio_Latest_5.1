package com.grupio.notes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class RuledView extends EditText {

    private Rect mRect;
    private Paint mPaint;

    public RuledView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xFF000000);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {

        int count = getLineCount();
        int height = this.getMeasuredHeight();
        int line_height = this.getLineHeight();
        int page_size = height / line_height + 1;
        int posY = 20;
        if (count < page_size) {
            count = page_size;
        }

        for (int i = 1; i < count; i++) {
            posY += line_height;

            canvas.drawLine(0, posY, getRight(), posY, mPaint);
            canvas.save();

        }

        super.onDraw(canvas);
        canvas.restore();
    }

}
