package com.controller;

import com.dto.MusicLrcVO;
import com.dto.MusicVO;
import com.model.Music;
import com.service1.MusicService;
import com.util.MusicUrlSpider;
import com.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

/**
 * Created by 祥少 on 2017/7/2.
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {
    @Autowired
    MusicService musicService;

    @GetMapping(value = "/")
    public String getIndex() {
        return "index";
    }

    @GetMapping(value = "/play")
    public String getPlay() {
        return "play";
    }

    @GetMapping(value = "/search")
    public ModelAndView searchMusic(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            MusicVO musics = musicService.searchMusic(keyword, page);
            modelAndView.addObject("result", musics);
            modelAndView.addObject("keyword", keyword);
            if (page != musics.getAllpage() - 1) {
                modelAndView.addObject("onpage", page + 1);
            }
            else{
                modelAndView.addObject("onpage", page);
            }
            if (page != 0) {
                modelAndView.addObject("lowpage", page - 1);
            }
            else{
                modelAndView.addObject("lowpage", page);
            }
            modelAndView.setViewName("result");
            return modelAndView;
        } catch (IOException e) {
            return modelAndView;
        }
    }

    @GetMapping(value = "/new")
    @ResponseBody
    public Result<Object> searchNewMusic() {
        try {
            MusicVO musics = musicService.getNewMusic(0);
            return new Result<Object>(true, musics, "");
        } catch (IOException e) {
            return new Result<Object>(false, e.getMessage());
        }
    }

    @GetMapping(value = "/hit")
    @ResponseBody
    public Result<Object> searchHitMusic() {
        try {
            MusicVO musics = musicService.getNewMusic(1);
            return new Result<Object>(true, musics, "");
        } catch (IOException e) {
            return new Result<Object>(false, e.getMessage());
        }
    }

    @PostMapping(value = "/lrc")
    @ResponseBody
    public Result<Object> searchMusicLrc(@RequestParam("song_id") String song_id,@RequestParam("name") String name,@RequestParam("songer") String songer) {
        String lrc = musicService.getLrc(song_id);
        String download_linking= MusicUrlSpider.getUrl(name,songer);
        return new Result<Object>(true, new MusicLrcVO(lrc, "http://ws.stream.qqmusic.qq.com/" + song_id + ".m4a?fromtag=46",download_linking), "");
    }
}
