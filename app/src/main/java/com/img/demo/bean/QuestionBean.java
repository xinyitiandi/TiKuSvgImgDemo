package com.img.demo.bean;

import java.io.Serializable;
import java.util.List;

public class QuestionBean implements Serializable{

    private String exerciseContent;
    private List<ItemListBean> optionlist;


    public String getExerciseContent() {
        return exerciseContent;
    }

    public void setExerciseContent(String exerciseContent) {
        this.exerciseContent = exerciseContent;
    }

    public List<ItemListBean> getOptionlist() {
        return optionlist;
    }

    public void setOptionlist(List<ItemListBean> optionlist) {
        this.optionlist = optionlist;
    }
}
