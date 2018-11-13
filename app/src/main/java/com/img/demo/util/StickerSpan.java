package com.img.demo.util;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import android.view.View;

import com.img.demo.activity.ImagePagerActivity;
import com.zzhoujay.richtext.spans.LongClickableSpan;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/29.
 */

public class StickerSpan extends ImageSpan implements LongClickableSpan {

//    private  List<String> imageUrls;
//    private  List<String> imageUrls;
    private  String imageUrl;

    public StickerSpan(Drawable b, int verticalAlignment) {
        super(b, verticalAlignment);

    }
    public StickerSpan(Drawable b, int verticalAlignment, String imageUrl) {
        super(b, verticalAlignment);
        this.imageUrl = imageUrl;
    }

    private WeakReference<Drawable> mDrawableRef;
    private float x;
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end,
                                 Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.descent - fmPaint.ascent;
            int drHeight = rect.bottom - rect.top;
            int centerY = fmPaint.ascent + fontHeight / 2;

            fontMetricsInt.ascent = centerY - drHeight / 2;
            fontMetricsInt.top = fontMetricsInt.ascent;
            fontMetricsInt.bottom = centerY + drHeight / 2;
            fontMetricsInt.descent = fontMetricsInt.bottom;
        }
        return rect.right;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y,
                     int bottom, Paint paint) {
        this.x = x;
        Drawable drawable = getCachedDrawable();
        canvas.save();
        Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
        int fontHeight = fmPaint.descent - fmPaint.ascent;
        int centerY = y + fmPaint.descent - fontHeight / 2;
        int transY = centerY - (drawable.getBounds().bottom - drawable.getBounds().top) / 2;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }

    private Drawable getCachedDrawable() {
        WeakReference<Drawable> wr = mDrawableRef;
        Drawable d = null;
        if (wr != null) {
            d = wr.get();
        }

        if (d == null) {
            d = getDrawable();
            mDrawableRef = new WeakReference<>(d);
        }

        return d;
    }

    public boolean clicked(int position) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Rect rect = drawable.getBounds();
            if (position <= rect.right + x && position >= rect.left + x) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View widget) {
//        if (onImageClickListener != null) {
//            onImageClickListener.imageClicked(imageUrls, 0);
//        }
//        LogUtils.i("StickerSpan",imageUrls.get(0));

        if(imageUrl.contains("http://")||imageUrl.contains("https://")){
            if (imageUrl.endsWith(".png")||imageUrl.endsWith(".jpg")){
                Intent intent = new Intent();
                intent.putExtra("url",imageUrl);
                intent.setClass(widget.getContext(),ImagePagerActivity.class);
                widget.getContext().startActivity(intent);
            }
        }

    }

    @Override
    public boolean onLongClick(View widget) {
        return false;
    }
}