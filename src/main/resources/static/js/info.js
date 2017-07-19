;

var music__info_ops = {
    init:function(){
        this.player = null;
        this.counter = 0;
        this.st = 0;
        this.interval_id = null;
        this.song_id = window.location.href.split('=')[1].split('&')[0];
        this.name = decodeURI(window.location.href.split('=')[2].split('&')[0]);
        this.songer = decodeURI(window.location.href.split('=')[3].split('&')[0]);
        this.album_id = window.location.href.split('=')[4];
        this.initPlayer();
        this.eventBind();
    },
    eventBind:function(){
        var that = this;
        this.getLrc();
        $(".btn_pause").click(function(){
            that.player.pause();
            $("#music-pic").removeClass("active");
            that.setBtn(0);
        });

        $(".btn_play").click(function(){
            that.player.play();
            $("#music-pic").addClass("active");
            that.setBtn(1);
        });

        $(".btn_refresh").click(function(){
            window.location.href = window.location.href;
        });

    },
    initPlayer:function(){
        $('#songer').html(this.songer);
        $('#musicname').html(this.name);
        $('#song_id')[0].setAttribute('value', this.song_id);
        $("#music-pic")[0].style='background-image: url(http://imgcache.qq.com/music/photo/album_500/'+parseInt(this.album_id)%100+'/500_albumpic_'+this.album_id+'_0.jpg);'
        player = document.createElement('audio');
        $(player).attr({
            autoplay: 'autoplay',
            preload: 'preload'
        });
        this.player = player;
    },
    timer:function(){
        var that = music__info_ops;
        if (this.player.ended) {
            window.clearTimeout(this.interval_id);
            $("#music-pic").removeClass("active");
            that.setBtn(-1);
            return;
        } else if (this.player.paused) {
            $("#music-pic").removeClass("active");
            return;
        }
        var lrc_items = $("#lrc_box").children();
        if( lrc_items.size() < 1 ){
            return;
        }

        var st = that.st;
        var counter = that.counter;
        for (i = 0; i < lrc_items.length; i++) {
            item = $(lrc_items[i]);
            if (item.data('time') == Math.floor(this.player.currentTime)) {
                lrc_items.removeClass('active');
                item.addClass('active');
                dt = item[0].getBoundingClientRect().top - counter;
                st += dt;
                $("#lrc_box").css({
                    transform: 'translateY(' + (-st) + 'px)',
                    webkitTransform: 'translateY(' + (-st) + 'px)'
                });
            }
        }
        that.st = st;
    },
    getLrc:function(){
        var that = this;
        $.ajax({
            url:"/index/lrc",
            type:'POST',
            data:{ 'song_id':that.song_id, 'name':that.name , 'songer':that.songer  },
            dataType:'json',
            success:function(res){
                if( res.success == true ){
                    $('#download').html( '下载链接：'+res.data.download_linking);
                    // $('#download')[0].setAttribute('href', res.data.download_linking);
                    if( res.data.lrc.length > 0 ){
                        lrc_arr = res.data.lrc.split('\n');
                        lyc_str = "";
                        for (i = 0; i < lrc_arr.length; i++) {
                            lyc_str += that.lrc_template(lrc_arr[i]);
                        }
                        $("#lrc_box").html(lyc_str);
                    }else{
                        lyc_str = "没有找到相关歌词！";
                        $("#lrc_box").html(lyc_str);
                    }
                    that.counter = $("#lrc_box")[0].getBoundingClientRect().top;
                    that.player.src = res.data.song_url;
                    that.player.play();
                    $("#music-pic").addClass("active");
                    that.setBtn(1);
                    that.interval_id = setInterval(that.timer, 1000);
                }
            }
        });
    },
    lrc_template:function(s) {
        s = s.replace(/\[[^\]]+\]/g, function(e) {
            try {
                ms = /\[(\d+):(\d+)\.(\d+)\]/.exec(e);
                ms = parseInt(ms[1] * 60) + parseInt(ms[2]);
            } catch(e) {};
            return '';
        });
    return '\
            <div data-time="' + ms + '">' + s + '</div>\
        ';
    },
    setBtn:function( flag ){
        if( flag == 1 ){
            $(".btn_play").hide();
            $(".btn_refresh").hide();
            $(".btn_pause").css("display","block");
        }else if(flag == -1){
            $(".btn_play").hide();
            $(".btn_pause").hide();
            $(".btn_refresh").css("display","block");
        }else{
            $(".btn_pause").hide();
            $(".btn_refresh").hide();
            $(".btn_play").css("display","block");
        }
    }
};

$(document).ready(function(){
    music__info_ops.init();
});

