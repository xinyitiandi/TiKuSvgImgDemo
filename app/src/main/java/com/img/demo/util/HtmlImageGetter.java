package com.img.demo.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.caverock.androidsvg.PreserveAspectRatio;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.img.demo.base.BaseApplication;
import com.img.demo.svg.GlideApp;
import com.zzhoujay.richtext.ext.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2017/12/28.
 */

public class HtmlImageGetter implements Html.ImageGetter {

    private TextView mTextView;
    private Activity mActivity;
    private float firstRatio = 2.0f;
    private float nextRatio = 1.0f;
    private int mMax = 3;
    boolean isFirst = true;

    public HtmlImageGetter(TextView textView, Activity activity) {
        this.mTextView = textView;
        this.mActivity = activity;
        firstRatio = 2.0f;
    }

    public HtmlImageGetter(TextView textView, Activity activity, int size) {
        this.mTextView = textView;
        this.mActivity = activity;
        this.nextRatio = (float) size / (float) 16;
    }

    @Override
    public Drawable getDrawable(final String source) {
        if (source.contains("http://") || source.contains("https://")) {
            final URLDrawable urlDrawable = new URLDrawable();
            if (source.contains("tosvg")) {
                RequestBuilder requestBuilder = GlideApp.with(mActivity).asFile();
                Uri uri = Uri.parse(source);
                requestBuilder.load(uri).into(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(@NonNull File svgFile, @Nullable Transition<? super File> transition) {
                        SVG svg = null;
                        try {
                            svg = SVG.getFromInputStream(new FileInputStream(svgFile));
                            float documentWidth = svg.getDocumentWidth();
                            float documentHeight = svg.getDocumentHeight();

                            int screen_width=AppUtil.px2dp(mActivity,BaseApplication.screen_width);

                            if (documentWidth>screen_width*0.85) {
                                svg.setDocumentWidth(BaseApplication.screen_width*0.85f);
                                svg.setDocumentHeight(BaseApplication.screen_width*0.85f*AppUtil.dp2px(mActivity, documentHeight)/AppUtil.dp2px(mActivity, documentWidth));
                            }else{
                                svg.setDocumentWidth(AppUtil.dp2px(mActivity, documentWidth));
                                svg.setDocumentHeight(AppUtil.dp2px(mActivity, documentHeight));
                            }
                            svg.setDocumentViewBox(0, 0, documentWidth, documentHeight);
                            svg.setDocumentPreserveAspectRatio(PreserveAspectRatio.FULLSCREEN);

                            Bitmap bitmap = Bitmap.createBitmap((int) (svg.getDocumentWidth()), (int) (svg.getDocumentHeight()), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bitmap);
                            svg.renderToPicture().draw(canvas);


                            Drawable drawable = new BitmapDrawable(mActivity.getResources(), bitmap);
                            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            urlDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            urlDrawable.setDrawable(drawable);

                            mTextView.invalidate();
                            mTextView.setText(mTextView.getText()); // 解决图文重叠
                        } catch (SVGParseException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                GlideApp.with(mActivity).asBitmap()
                        .load(source).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(mActivity.getResources(), bitmap);
                        if (AppUtil.dp2px(mActivity, bitmap.getWidth()) < 200) {
                            drawable.setBounds(0, 0, AppUtil.dip2px(bitmap.getWidth()), AppUtil.dip2px(bitmap.getHeight()));
                            urlDrawable.setBounds(0, 0, AppUtil.dip2px(bitmap.getWidth()), AppUtil.dip2px(bitmap.getHeight()));
                        } else {
                            int screenW = BaseApplication.screen_width;
                            double w = screenW * 0.9;
                            if (AppUtil.dp2px(mActivity, bitmap.getWidth()) > w) {
                                drawable.setBounds(0, 0, (int)(screenW * 0.8f), (int)(bitmap.getHeight() * (screenW * 0.8f)/bitmap.getWidth()));
                                urlDrawable.setBounds(0, 0,  (int)(screenW * 0.8f), (int)(bitmap.getHeight() * (screenW * 0.8f)/bitmap.getWidth()));
                            } else {
                                if (AppUtil.dp2px(mActivity, bitmap.getWidth()) > (screenW * 0.8)) {
                                    drawable.setBounds(0, 0, AppUtil.dp2px(mActivity, bitmap.getWidth() * 0.8f), AppUtil.dp2px(mActivity, bitmap.getHeight() * 0.8f));
                                    urlDrawable.setBounds(0, 0, AppUtil.dp2px(mActivity, bitmap.getWidth() * 0.8f), AppUtil.dp2px(mActivity, bitmap.getHeight() * 0.8f));
                                } else if (AppUtil.dp2px(mActivity, bitmap.getWidth()) > (screenW * 0.7)) {
                                    drawable.setBounds(0, 0, AppUtil.dp2px(mActivity, bitmap.getWidth() * 0.9f), AppUtil.dp2px(mActivity, bitmap.getHeight() * 0.9f));
                                    urlDrawable.setBounds(0, 0, AppUtil.dp2px(mActivity, bitmap.getWidth() * 0.9f), AppUtil.dp2px(mActivity, bitmap.getHeight() * 0.9f));
                                } else {
                                    drawable.setBounds(0, 0, AppUtil.dip2px(bitmap.getWidth()), AppUtil.dip2px(bitmap.getHeight()));
                                    urlDrawable.setBounds(0, 0, AppUtil.dip2px(bitmap.getWidth()), AppUtil.dip2px(bitmap.getHeight()));
                                }
                            }

                        }

                        urlDrawable.setDrawable(drawable);
                        mTextView.invalidate();
                        mTextView.setText(mTextView.getText()); // 解决图文重叠
                    }
                });
            }

            return urlDrawable;
        } else {
            byte[] byteIcon = Base64.decode(source);
            if (byteIcon != null && byteIcon.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(byteIcon, 0, byteIcon.length);
                Drawable drawable = new BitmapDrawable(mActivity.getResources(), bitmap);
                drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 1.7f), (int) (drawable.getIntrinsicHeight() * 1.7f));

                return drawable;
            } else {
                return null;
            }
        }

    }

    private static final class URLDrawable extends BitmapDrawable {
        private Drawable drawable;

        @SuppressWarnings("deprecation")
        public URLDrawable() {
        }

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }
}
