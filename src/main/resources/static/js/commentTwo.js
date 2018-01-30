/*commentTwo.js*/
// 点击评论创建评论条

$(document).ready(function() {
    // var _url = 'http://localhost:8080'
    var _data
    // 获取链接中的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    var id = GetQueryString('id');
    $('.commentAll').on('click', '.plBtn', function() {
        //获取输入内容
        var oSize = $(this).siblings('.flex-text-wrap').find('.comment-input').val();
        if (oSize == '') {
            alert('請輸入內容！')
        }else if($.cookie('uersMsgId') == 'null' || $.cookie('uersMsgId')){
            alert('请先登录')
        } else {
            $.ajax({
                url: '/comment/student',
                type: 'POST',
                dataType: 'json',
                timeout: 5000,
                async: false,
                data: JSON.stringify({
                    content: oSize,
                    chapterId: id
                }),
                contentType: 'application/json; charset=UTF-8',
                beforeSend: LoadFunction,
                success: succyFunction,
                error: erryFunction

            })

            function LoadFunction() {
                console.log("发表中.....")
            }

            function succyFunction(data) {
                // console.log(data.data.time)
                // console.log("aha")
                // data_msg = '1111';
                // if (data.msg) {
                alert("发表成功！");
                // console.log(oSize)
                // data_msg = true;
                _data = data.data
                // console.log(_data)

                // }
                // return false;
            }

            function erryFunction() {
                alert("发表失败，请稍后重试！")
                data_msg = false;
            }
        }


        // 发表成功前端渲染

        // console.log(oSize);
        //动态创建评论模块
        oHtml = '<div class="comment-show-con clearfix">' +
            '<div class="comment-show-con-img pull-left"><img src="' + _data.img + '" alt="" style="width:48px;height:48px;"></div>' +
            '<div class="comment-show-con-list pull-left clearfix">' +
            '<div class="pl-text clearfix">' +
            '<a href="#" class="comment-size-name pl-name">' + _data.author + ' :' + '</a>' +
            '<span class="my-pl-con pl-talk">&nbsp;' + oSize + '</span>' +
            '</div>' +
            '<div class="date-dz">' +
            '<span class="date-dz-left pull-left comment-time pl-time">' + _data.updateTime + '</span>' +
            '<div class="date-dz-right pull-right comment-pl-block">' +
            '<a href="javascript:;" class="date-dz-pl hf-con-block pull-left pl-id" style="display:none">' + _data.id + '</a>' +
            '<a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a>' +
            '<span class="pull-left date-dz-line">|</span>' +
            '<a href="javascript:;" class="pl-sum date-dz-pl hf-con-block pull-left pl-id-' + _data.id + '">' + _data.reply + '条回复</a>' +
            '<span class="pull-left date-dz-line">|</span>' +
            '<a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num like">' + _data.agreeCount + '</i>)</a>' +
            '</div>' +
            '</div>' +
            '<div class="hf-list-con"></div>' +
            '</div>' +
            '</div>';
        // console.log(oSize.replace(/(^\s*)|(\s*$)/g, "") != '')
        if (oSize.replace(/(^\s*)|(\s*$)/g, "") != '') {
            $(this).parents('.reviewArea ').siblings('.comment-show').prepend(oHtml);
            // console.log(this)
            $(this).siblings('.flex-text-wrap').find('.comment-input').prop('value', '').siblings('pre').find('span').text('');
        }

    });
})