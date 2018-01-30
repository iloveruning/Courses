/*commentFive.js*/
$(document).ready(function() {
    // var _url = 'http://localhost:8080'
    // 点击加载回复
    $('.comment-show').on('click', '.pl-sum', function() {
        // console.log("tyf is cool!!!")
        var oThis = $(this);
        var id = parseInt(oThis.parent().find(".pl-id").html())

        // 通过id获取评论列表
        $.ajax({
            url: '/reply/' + id + '',
            type: 'GET',
            dataType: 'json',
            timeout: 5000,
            async: false,
            /*data: JSON.stringify({
                id: parseInt(oThis.parent().find(".pl-id").html())
            }),*/
            contentType: 'application/json; charset=UTF-8',
            beforeSend: LoadFunction,
            success: succyFunction,
            error: erryFunction

        })

        function LoadFunction() {
            console.log("发表中.....")
        }

        function succyFunction(data) {
            if (data.success) {
                var _data = data.data
                $.each(_data, function(index, items) {
                    oThis.parent().parent().parent().find(".hf-list-con").append('<div class="all-pl-con"><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">' + _data[index].from + ': </a><span class="my-pl-con"><a href="#" class="atName">@' + _data[index].to + '</a> : ' + _data[index].content + '</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">' + _data[index].updateTime + '</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a></div> </div></div>')
                });
            } else {
                alert(data.message)
            }
        }

        function erryFunction() {
            alert("获取评论列表失败！")
            data_msg = false;
        }
        // }

    });


    // 点赞
    $('.comment-show').on('click', '.date-dz-z', function() {
        let id = $(this).attr('index')
        // console.log(id)
        if($.cookie('userMsgId') == 'null' || !$.cookie('userMsgId')){
            alert("请先登录！")
        }else {
            if ($(this).is('.date-dz-z-click')) {
                // alert(111)
                changeLike(id, 'DELETE')
            } else {
                // alert(222)
                changeLike(id, 'POST')
            }
        }


        var zNum = $(this).find('.z-num').html();
        if ($(this).is('.date-dz-z-click')) {
            zNum--;
            $(this).removeClass('date-dz-z-click red');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').removeClass('red');
        } else {
            zNum++;
            $(this).addClass('date-dz-z-click');
            $(this).find('.z-num').html(zNum);
            $(this).find('.date-dz-z-click-red').addClass('red');
        }
    })

    function changeLike(id, type) {
        $.ajax({
            url: '/comment/student/agree/' + id + '',
            type: type,
            dataType: 'json',
            // data: {},
            timeout: 5000,
            beforeSend: function() {},
            error: function() {},
            success: function(data) {}
        })
    }
})