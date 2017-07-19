package com.dto;

import com.model.Music;

import java.util.List;

/**
 * Created by 祥少 on 2017/7/3.
 */
public class MusicVO {
    private int allcount;
    private int allpage;
    private List<Music>list;

    public MusicVO(int allcount, int allpage, List<Music> list) {
        this.allcount = allcount;
        this.allpage = allpage;
        this.list = list;
    }

    @Override
    public String toString() {
        return "MusicVO{" +
                "allcount=" + allcount +
                ", allpage=" + allpage +
                ", list=" + list +
                '}';
    }

    public int getAllcount() {
        return allcount;
    }

    public void setAllcount(int allcount) {
        this.allcount = allcount;
    }

    public int getAllpage() {
        return allpage;
    }

    public void setAllpage(int allpage) {
        this.allpage = allpage;
    }

    public List<Music> getList() {
        return list;
    }

    public void setList(List<Music> list) {
        this.list = list;
    }
}
