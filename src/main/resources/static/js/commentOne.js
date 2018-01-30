/*comment.js*/
$(document).ready(function() {
    link(id)
})

// var _url = 'http://localhost:8080'
// 获取链接中的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    var id = GetQueryString('id');
    // alert(id)

function link(id) {
    $.ajax({
        url: '/comment/student/'+id+'/1',
        type: 'GET',
        dataType: 'json',
        contentType: "application/json; charset=utf-8",
        timeout: 5000,
        cache: false,
        beforeSend: LoadFunction,
        error: erryFunction,
        success: succyFunction,
    })

    function LoadFunction() {
        console.log("加载中请稍后....")
    }

    function erryFunction() {
        alert("哇，貌似服务器失联了，请等待稍后重试...")
    }

    function succyFunction(data) {
        if ($.cookie('uersMsgId') == 'null' || $.cookie('uersMsgId')) {
            $('.content').val('您还没有登录哟，请登陆后发表评论！')
            $('.content').attr('readonly', true)
            $('.plBtn').attr('disabled', true)
            console.log(8520)
        } else {
            $('.content').val('')
            $('.content').attr('readonly', false)
            $('.plBtn').attr('disabled', false)
        }
        // console.log(data)
        var _data = data.data.list
        $('.comment-show').html('')
        for (let i of _data) {
            // console.log(i.id)
            $('.comment-show').append('<div class="comment-show-con clearfix">' +
                '<div class="comment-show-con-img pull-left"><img class="img-id-' + i.id + '" src="" alt="" style="width:48px;height:48px;"></div>' +
                '<div class="comment-show-con-list pull-left clearfix">' +
                '<div class="pl-text clearfix">' +
                '<a href="#" class="comment-size-name pl-name">' + i.author + ' :' + '</a>' +
                '<span class="my-pl-con pl-talk">&nbsp;' + i.content + '</span>' +
                '</div>' +
                '<div class="date-dz">' +
                '<span class="date-dz-left pull-left comment-time pl-time">' + i.updateTime + '</span>' +
                '<div class="date-dz-right pull-right comment-pl-block">' +
                '<a href="javascript:;" class="date-dz-pl hf-con-block pull-left pl-id" style="display:none">' + i.id + '</a>' +
                '<a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>' +
                '<span class="pull-left date-dz-line">|</span>' +
                '<a href="javascript:;" class="pl-sum date-dz-pl hf-con-block pull-left pl-id-' + i.id + '">' + i.reply + '条回复</a>' +
                '<span class="pull-left date-dz-line">|</span>' +
                '<a href="javascript:;" index="' + i.id + '" class="date-dz-z pull-left pull-id-' + i.id + '"><i class="date-dz-z-click-red z-id-' + i.id + '"></i>赞 (<i class="z-num like">' + i.agreeCount + '</i>)</a>' +
                '</div>' +
                '</div>' +
                '<div class="hf-list-con"></div>' +
                '</div>' +
                '</div>')
            // $(".pl-sum").addClass('pl-id-'+ i.id)
            // console.log(i.id)
            //$(".pl-sum").attr('class','pl-sum date-dz-pl hf-con-block pull-left pl-id-'+ i.id)
            $(".img-id-" + i.id).attr('src', i.img)
            // console.log(i.isAgreed)
            // console.log($(".pull-left-"+ i.id))
            if (i.isAgreed) {
                // console.log(0)
                $('.pull-right').find(".pull-id-" + i.id).addClass('date-dz-z-click')
                // console.log(1)
                $('.date-dz-z').find('.z-id-' + i.id).addClass('red');
            }
        }

        // console.log(data.data.hasPreviousPage)
        // pages = data.data.pages
        // console.log(data)
        let html = ''
        for (let i = 1; i <= data.data.pages; i++) {
            html += '<input class="btn  btn-default btn-id-' + i + '" style="margin:0 5px;" type="button" value="' + i + '">'
            $(".fy span").html(html)
        }
        // if($('.fy .btn-id-1').find(active)){}





        // bug!!!!!!!!!!!!

        $('.fy .btn-id-1').addClass('active')
        // console.log(!data.data.hasNextPage)
        if (data.data.hasPreviousPage) {
            $('.fy .prev').attr('disabled', false)
            // alert(1111)
        } else {
            $('.fy .prev').attr('disabled', true)
        }

        if (data.data.hasNextPage) {
            $('.fy .next').attr('disabled', false)
            // alert(1111)
        } else {
            $('.fy .next').attr('disabled', true)
        }

        if (data.data.isLastPage) {
            $('.fy .last-pages').attr('disabled', true)
            // alert(1111)
        } else {
            $('.fy .last-pages').attr('disabled', false)
        }

        if (data.data.isFirstPage) {
            $('.fy .first-page').attr('disabled', true)
            // alert(1111)
        } else {
            $('.fy .first-page').attr('disabled', false)
        }
    }
}