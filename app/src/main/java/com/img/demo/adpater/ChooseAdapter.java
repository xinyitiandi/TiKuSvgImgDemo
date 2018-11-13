package com.img.demo.adpater;

import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.img.demo.R;
import com.img.demo.bean.ItemListBean;
import com.img.demo.util.TextViewHtml;


public class ChooseAdapter extends BaseQuickAdapter<ItemListBean, BaseViewHolder> {

    private boolean isClickable;
    private Activity mActivity;
    public ChooseAdapter(Activity mActivity, boolean isClickable) {
        super(R.layout.item_choose);
        this.mActivity=mActivity;
        this.isClickable=isClickable;
    }


    @Override
    protected void convert(BaseViewHolder helper, ItemListBean item) {
        ImageView iv_first_single=helper.getView(R.id.iv_first_single);
        TextView tv_choose_content=helper.getView(R.id.tv_choose_content);
        TextView tv_choose_letter=helper.getView(R.id.tv_choose_letter);
        Log.e("---------","-----"+item.getOptionContent());
        TextViewHtml.htmlToText(tv_choose_content,mActivity,item.getOptionContent()+"",isClickable);
        helper.setText(R.id.tv_choose_letter,item.getOptionIndex()+"");
        iv_first_single.setImageResource(R.drawable.circle_gray);
        tv_choose_letter.setTextColor(mActivity.getResources().getColor(R.color.color_9));
    }
}
