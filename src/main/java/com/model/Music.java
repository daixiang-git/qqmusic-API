package com.model;

/**
 * Created by 祥少 on 2017/7/3.
 */
public class Music {
    private String song_id;
    private String name;
    private String songer;
    private String download_linking;
    private String image_linking;
    private String album;
    private String album_id;
    private String playtime;

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSonger() {
        return songer;
    }

    public void setSonger(String songer) {
        this.songer = songer;
    }


    @Override
    public String toString() {
        return "Music{" +
                "song_id='" + song_id + '\'' +
                ", name='" + name + '\'' +
                ", songer='" + songer + '\'' +
                ", image_linking='" + image_linking + '\'' +
                ", album='" + album + '\'' +
                ", playtime='" + playtime + '\'' +
                '}';
    }


    public String getImage_linking() {
        return image_linking;
    }

    public void setImage_linking(String image_linking) {
        this.image_linking = image_linking;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getDownload_linking() {
        return download_linking;
    }

    public void setDownload_linking(String download_linking) {
        this.download_linking = download_linking;
    }
}
