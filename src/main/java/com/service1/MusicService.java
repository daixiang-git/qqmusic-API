package com.service1;

import com.dto.MusicVO;
import com.model.Music;

import java.io.IOException;

/**
 * Created by 祥少 on 2017/7/3.
 */
public interface MusicService {

    /**
     *查询音乐
     * @param keyWord
     * @param page
     * @return
     */
    public MusicVO searchMusic(String keyWord, int page) throws IOException;


    /**
     * int type
     * @return
     */
    public MusicVO getNewMusic(int type) throws IOException;


    /**
     * 获取歌词
     * @param song_id
     */
    public String getLrc(String song_id);
}
