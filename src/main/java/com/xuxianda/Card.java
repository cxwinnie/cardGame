package com.xuxianda;

import java.io.Serializable;

/**
 * Created by XiandaXu on 2020/4/3.
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 4574054047057249098L;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 编号
     */
    private Integer no;

    /**
     * 是否暗置，默认不暗置
     */
    private boolean hidden = false;

    public Card(Integer type, Integer no) {
        this.type = type;
        this.no = no;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Override
    public String toString() {
        if(hidden){
            return "*";
        }
        return "<"+type+","+no+">";
    }
}
