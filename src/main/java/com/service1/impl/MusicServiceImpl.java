package com.service1.impl;

import com.dto.MusicVO;
import com.model.Music;
import com.service1.MusicService;
import com.util.MusicUrlSpider;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 祥少 on 2017/7/3.
 */
@Service
public class MusicServiceImpl implements MusicService {
    private static String SEAECH_URL = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n=12&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&catZhida=0&remoteplace=sizer.newclient.next_song&p=";
    private static String NEWMUSIC_URL = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_newsong.js";
    private static String HITMUSIC_URL = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_all.js";
    private static String MUSICLRC_URL = " http://music.qq.com/miniportal/static/lyric/";

    @Override
    public MusicVO searchMusic(String keyWord, int page) throws IOException {
        List<Music> list = new ArrayList<>();
        int allpage = 0;
        int allcount = 0;
        String url = SEAECH_URL + page + "&w=" + new String(java.net.URLEncoder.encode(keyWord,"utf-8").getBytes());
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection uc = u.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
        String str = null;
        while ((str = in.readLine()) != null) {
            JSONObject data = JSONObject.fromObject(str);
            for (Object str1 : data.keySet()) {
                if (str1.equals("data")) {
                    JSONObject databuff = (JSONObject) data.get(str1);
                    for (Object str2 : databuff.keySet()) {
                        if (str2.equals("song")) {
                            JSONObject databuff1 = (JSONObject) databuff.get(str2);
                            for (Object str3 : databuff1.keySet()) {
                                if (str3.equals("totalnum")) {
                                    allcount = (int) databuff1.get(str3);
                                    if (allcount == 0) {
                                        return new MusicVO(allcount, allpage, list);
                                    }
                                    allpage = (int) Math.ceil(allcount / 10.0);
                                }
                                if (str3.equals("list")) {
                                    JSONArray databuff2 = (JSONArray) databuff1.get(str3);
                                    for (int i = 0; i < databuff2.size(); i++) {
                                        String string = (String) (databuff2.getJSONObject(i).get("f"));
                                        String[] F = string.split("\\|");
                                        if(F.length<5){
                                            continue;
                                        }
                                        Music music = new Music();
                                        music.setSong_id(F[0]);
                                        music.setName(F[1]);
                                        music.setSonger(F[3]);
                                        music.setAlbum(F[5]);
                                        music.setAlbum_id(F[4]);
                                        music.setImage_linking("http://imgcache.qq.com/music/photo/album_300/" + Integer.valueOf(F[4]) % 100 + "/300_albumpic_" + F[4] + "_0.jpg");
//                                        music.setDownload_linking(MusicUrlSpider.getUrl(F[1],F[3]));
                                        list.add(music);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return new MusicVO(allcount, allpage, list);
    }

    @Override
    public MusicVO getNewMusic(int type) throws IOException {
        List<Music> list = new ArrayList<>();
        int allpage = 0;
        int allcount = 0;
        URL u = null;
        try {
            if (type == 0) {
                u = new URL(NEWMUSIC_URL);
            } else {
                u = new URL(HITMUSIC_URL);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection uc = u.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "gbk"));
        String str = null;
        while ((str = in.readLine()) != null) {
            String json = str.substring(str.indexOf("(") + 1, str.lastIndexOf(")"));
            JSONObject data = JSONObject.fromObject(json);
            JSONArray jsonlist = (JSONArray) data.get("songlist");
            for (int i = 0; i < jsonlist.size(); i++) {
                JSONObject string = jsonlist.getJSONObject(i);
                Music music = new Music();
                music.setSong_id((String) string.get("id"));
                music.setName((String) string.get("songName"));
                music.setSonger((String) string.get("singerName"));
                music.setAlbum((String) string.get("albumName"));
                music.setPlaytime((String) string.get("playtime"));
                music.setAlbum_id((String) string.get("albumId"));
                music.setImage_linking("http://imgcache.qq.com/music/photo/album_300/" + Integer.valueOf((String) string.get("albumId")) % 100 + "/300_albumpic_" + Integer.valueOf((String) string.get("albumId")) + "_0.jpg");
//                music.setDownload_linking(MusicUrlSpider.getUrl(music.getName(),music.getSonger()));
                list.add(music);
                if (list.size() >= 12) {
                    break;
                }
            }
            break;
        }
        return new MusicVO(list.size(), allpage, list);
    }

    @Override
    public String getLrc(String song_id) {
        String url = MUSICLRC_URL + Integer.valueOf(song_id.trim()) % 100 + "/" + song_id + ".xml";
        URL u = null;
        StringBuilder lrc=new StringBuilder();
        try {
            u = new URL(url);
            URLConnection uc = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "gbk"));
            String str = null;
            while ((str = in.readLine()) != null) {
                if(str.contains("xml")){
                    continue;
                }
                lrc.append(str);
                lrc.append("\n");
            }
        } catch (Exception e) {
            return "";
        }
        return String.valueOf(lrc);
    }


}
