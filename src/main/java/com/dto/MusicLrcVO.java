package com.dto;

/**
 * Created by 祥少 on 2017/7/3.
 */
public class MusicLrcVO {
    private String lrc;
    private String song_url;
    private String download_linking;
    public MusicLrcVO(String lrc, String song_url,String download_linking) {
        this.lrc = lrc;
        this.song_url = song_url;
        this.download_linking=download_linking;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getSong_url() {
        return song_url;
    }

    public void setSong_url(String song_url) {
        this.song_url = song_url;
    }

    public String getDownload_linking() {
        return download_linking;
    }

    public void setDownload_linking(String download_linking) {
        this.download_linking = download_linking;
    }
}
