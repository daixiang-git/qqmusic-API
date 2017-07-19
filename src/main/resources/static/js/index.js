new Vue({
    el: '#app',
    data: {
        musics: [],
        newmusic: [],
        hit: [],

    },
    //当vue实例创建完成后自动调用

    created: function () {
        var me = this;
        me.getNew();
        me.getHit();
        // me.getMovieOfType();
    },
    methods:{
        getNew: function(){
            var me = this;
            $.ajax({
                url: '/index/new',
                method: 'get',
                success:function(res){
                    me.newmusic = res.data.list;
                },
                error:function(e){
                    alert(e);
                }
            })
        },
        getHit:function () {
            var me = this;
            $.ajax({
                url: '/index/hit',
                method: 'get',
                success:function(res){
                    me.hit = res.data.list;
                },
                error:function(e){
                    alert(e);
                }
            })
        }
    }
})
