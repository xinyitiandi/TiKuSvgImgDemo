package com.img.demo.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.widget.TextView;


/**
 * Created by Administrator on 2017/12/29.
 * TextView 显示 html 工具
 */
public class TextViewHtml {
    public static void htmlToText(TextView textView, Activity activity, String text, boolean isClickable){
        if(StringUtils.isTrimEmpty(text))return;
        String p = "<p>";
        if(text.startsWith(p) && text.endsWith("</p>")){
            int start = text.indexOf(p);
            int last = text.lastIndexOf("</p>");
            text = text.substring(start+p.length(),last);
        }
        Spanned spanned;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            spanned = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT, new HtmlImageGetter(textView, activity), null);
        } else {
            spanned = Html.fromHtml(text, new HtmlImageGetter(textView, activity), null);
        }
        if (spanned instanceof SpannableStringBuilder) {
            ImageSpan[] imageSpans = spanned.getSpans(0, spanned.length(), ImageSpan.class);
            for (ImageSpan imageSpan : imageSpans) {
                int start = spanned.getSpanStart(imageSpan);
                int end = spanned.getSpanEnd(imageSpan);
                Drawable d = imageSpan.getDrawable();
                StickerSpan newImageSpan = new StickerSpan(d, ImageSpan.ALIGN_BASELINE, imageSpan.getSource());
                ((SpannableStringBuilder) spanned).setSpan(newImageSpan, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                ((SpannableStringBuilder) spanned).removeSpan(imageSpan);
            }
        }
        textView.setText(spanned);
        if (isClickable){
            textView.setMovementMethod(ClickableLinkMovementMethod.getInstance());
        }
    }

}
