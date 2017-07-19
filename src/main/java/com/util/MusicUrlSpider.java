package com.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 祥少 on 2017/7/5.
 */
public class MusicUrlSpider {
    static String SEARCHURL = "http://musicmini.baidu.com/app/search/searchList.php?ie=utf-8&page=1&qword=";
    static String BAIDYURL = "http://music.baidu.com/data/music/links?songIds=";
    static String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36";
    static String acceptEncoding = "gzip, deflate";

    public static String getUrl(String musicName, String musicSonger) {
        try {
            String song_id = null;
            String song_name = null;
            String songer = null;
            Connection.Response res = Jsoup.connect(SEARCHURL + musicName).method(Connection.Method.GET).timeout(5000).userAgent(userAgent).header("Accept-Encoding", acceptEncoding).validateTLSCertificates(false).execute();
//            musicName=java.net.URLDecoder.decode(new String(musicName.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
//            musicSonger=java.net.URLDecoder.decode(new String(musicSonger.getBytes("ISO-8859-1"), "UTF-8"), "UTF-8");
            Document document = res.parse();
            Elements element = document.getElementsByClass("box");
            String playSong = element.first().getElementsByTag("a").first().attr("onclick");

            Pattern pattern = Pattern.compile("(?<=\\')(.*?)(?=\\')");
            Matcher matcher = pattern.matcher(playSong);
            for (int i = 0; i < 5; i++) {
                if (matcher.find()) {
                    if (i == 0) {
                        song_id = matcher.group();
                    }
                    if (i == 2) {
                        songer = matcher.group();
                    }
                    if (i == 4) {
                        song_name = matcher.group();
                    }
                }
            }
            if (musicName.equals(song_name) && musicSonger.equals(songer)) {
                return getMusicUrl(song_id);
            }
        } catch (IOException e) {
            return "";
        }
        return "";
    }

    public static String getMusicUrl(String song_id) throws IOException {
        String url = BAIDYURL + song_id;
        URL u = null;
        try {
            u = new URL(url);
            URLConnection uc = u.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "utf-8"));
            String str = null;
            while ((str = in.readLine()) != null) {
                JSONObject data = JSONObject.fromObject(str);
                for (Object str0 : data.keySet()) {
                    if (str0.equals("data")) {
                        JSONObject databuff0 = (JSONObject) data.get(str0);
                        for (Object str1 : databuff0.keySet()) {
                            if (str1.equals("songList")) {
                                JSONArray databuff = (JSONArray) databuff0.get(str1);
                                JSONObject darabuff1=databuff.getJSONObject(0);
                                String songLink = (String) (darabuff1.get("songLink"));
                                return songLink;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(new String(java.net.URLEncoder.encode("%E9%BE%99%E5%8D%B7%E9%A3%8E","utf-8").getBytes()));
        System.out.println(java.net.URLDecoder.decode(new String("%E9%BE%99%E5%8D%B7%E9%A3%8E".getBytes("ISO-8859-1"), "UTF-8"), "UTF-8"));
//        System.out.println(getUrl("想你的夜", "关喆"));
    }
}
