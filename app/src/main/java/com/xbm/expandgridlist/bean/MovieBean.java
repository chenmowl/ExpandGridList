package com.xbm.expandgridlist.bean;

/**
 * Created by xbm on 2018/3/7.
 */
public class MovieBean {
    String name;
    String turn;
    private boolean isToggle;

    public MovieBean(String name, String turn) {
        this.name = name;
        this.turn = turn;
    }

    public MovieBean(String name, String turn, boolean isToggle) {
        this.name = name;
        this.turn = turn;
        this.isToggle = isToggle;
    }

    public boolean isToggle() {
        return isToggle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
