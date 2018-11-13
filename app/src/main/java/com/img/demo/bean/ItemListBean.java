package com.img.demo.bean;

import java.io.Serializable;

public class ItemListBean implements Serializable {

    private String optionIndex;
    private String optionContent;

    public String getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(String optionIndex) {
        this.optionIndex = optionIndex;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }
}
