package com.aliya.uimode.demo.bean;

/**
 * TODO (一句话描述)
 *
 * @author a_liYa
 * @date 2017/6/24 18:02.
 */
public class Bean {

    private long bgResid;
    private long tcResid;

    public Bean() {
    }

    public Bean(long bgResid, long tcResid) {
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
        return "bg " + bgResid + "; tc " + tcResid + " -- " + this;
    }

}
