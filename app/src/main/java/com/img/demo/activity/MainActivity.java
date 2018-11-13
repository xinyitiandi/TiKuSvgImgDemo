package com.img.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.img.demo.R;
import com.img.demo.adpater.ChooseAdapter;
import com.img.demo.bean.ItemListBean;
import com.img.demo.bean.QuestionBean;
import com.img.demo.util.TextViewHtml;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private ChooseAdapter chooseAdapter;
    private String exerciseContent= "<p><span style=\"font-family: 宋体, SimSun;\">若<img alt=\"a＞b＞1\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=a%EF%BC%9Eb%EF%BC%9E1\" style=\"vertical-align: middle;\"/>，<img alt=\"P=\\sqrt{lga\\cdot lgb},Q=\\frac{1}{2}(lga+lgb),R=lg(\\frac{a+b}{2}),\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=P%3D%5Csqrt%7Blga%5Ccdot%20lgb%7D%2CQ%3D%5Cfrac%7B1%7D%7B2%7D(lga%2Blgb)%2CR%3Dlg(%5Cfrac%7Ba%2Bb%7D%7B2%7D)%2C\" style=\"vertical-align: middle;\"/>则（ &nbsp;）</span><br/></p>";
    private String optionContentA="<p><span style=\"font-family: &quot;times new roman&quot;;\"><img alt=\"R＜P＜Q\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=R%EF%BC%9CP%EF%BC%9CQ\" style=\"vertical-align: middle;\"/></span></p>";
    private String optionContentB="<p><img alt=\"P＜Q＜R\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=P%EF%BC%9CQ%EF%BC%9CR\" style=\"vertical-align: middle;\"/></p>";
    private String optionContentC="<p><img alt=\"Q＜P＜R\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=Q%EF%BC%9CP%EF%BC%9CR\" style=\"vertical-align: middle;\"/></p>";
    private String optionContentD="<p><img alt=\"P＜R＜Q\" src=\"http://luobotest.xw100.com/jlatexservice/latexconvertor/tosvg?latex=P%EF%BC%9CR%EF%BC%9CQ\" style=\"vertical-align: middle;\"/></p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        chooseAdapter = new ChooseAdapter(this,false);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setAdapter(chooseAdapter);

        QuestionBean mQuestionBean=new QuestionBean();
        mQuestionBean.setExerciseContent(exerciseContent+"");

        List<ItemListBean> itemListBeanList=new ArrayList<>();
        ItemListBean mItemListBeanA=new ItemListBean();
        mItemListBeanA.setOptionIndex("A");
        mItemListBeanA.setOptionContent(optionContentA+"");

        ItemListBean mItemListBeanB=new ItemListBean();
        mItemListBeanB.setOptionIndex("B");
        mItemListBeanB.setOptionContent(optionContentB+"");

        ItemListBean mItemListBeanC=new ItemListBean();
        mItemListBeanC.setOptionIndex("C");
        mItemListBeanC.setOptionContent(optionContentC+"");

        ItemListBean mItemListBeanD=new ItemListBean();
        mItemListBeanD.setOptionIndex("D");
        mItemListBeanD.setOptionContent(optionContentD+"");

        itemListBeanList.add(mItemListBeanA);
        itemListBeanList.add(mItemListBeanB);
        itemListBeanList.add(mItemListBeanC);
        itemListBeanList.add(mItemListBeanD);

        mQuestionBean.setOptionlist(itemListBeanList);

        TextViewHtml.htmlToText(tv_title,this,mQuestionBean.getExerciseContent() + "",false);
        chooseAdapter.setNewData(mQuestionBean.getOptionlist());
    }
}
