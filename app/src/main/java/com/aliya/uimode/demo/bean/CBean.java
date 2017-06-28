package com.aliya.uimode.demo.bean;

import android.util.Log;

/**
 * 具有Clone功能的Bean
 *
 * @author a_liYa
 * @date 2017/6/24 18:03.
 */
public class CBean implements Cloneable {

    private long bgResid;
    private long tcResid;

    public CBean() {
    }

    public CBean(long bgResid, long tcResid) {
        this.bgResid = bgResid;
        this.tcResid = tcResid;
    }

    public long getBgResid() {
        return bgResid;
    }

    public void setBgResid(long bgResid) {
        this.bgResid = bgResid;
    }

    public long getTcResid() {
        return tcResid;
    }

    public void setTcResid(long tcResid) {
        this.tcResid = tcResid;
    }

    @Override
    public String toString() {
        return "bg " + bgResid + "; tc " + tcResid + " -- " + hashCode();
    }

    @Override
    public CBean clone() {
        try {
            CBean clone = (CBean) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return new CBean();
    }
}
