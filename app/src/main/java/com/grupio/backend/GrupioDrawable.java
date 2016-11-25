package com.grupio.backend;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.Shape;

/**
 * Created by JSN on 25/11/16.
 */

public class GrupioDrawable<T extends Shape> extends ShapeDrawable {

    GrupioDrawable mShape;

    public GrupioDrawable(T shape) {
        mShape = new GrupioDrawable(shape);
    }


    public GrupioDrawable getLeftRoundRectangle(int radius) {
        if (mShape.getShape() instanceof RectShape) {
            mShape.setPadding(radius, radius, 0, 0);
        }
        return mShape;
    }

    public GrupioDrawable getLeftRectangleStroke(int radius, int strokeWidth) {
        if (mShape.getShape() instanceof RectShape) {
            mShape.setPadding(radius, radius, 0, 0);
            mShape.getPaint().setStrokeWidth(strokeWidth);
        }
        return mShape;
    }


    public GrupioDrawable getRightRoundRectangle(int radius) {
        if (mShape.getShape() instanceof RectShape) {
            mShape.setPadding(0, 0, radius, radius);
        }
        return mShape;
    }
}
