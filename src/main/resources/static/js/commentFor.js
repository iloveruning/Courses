/*commentFor.js*/
// 评论回复块创建
$(document).ready(function () {
    // var _url = 'http://localhost:8080'
    var hf_msg, _data
    $('.comment-show').on('click', '.hf-pl', function() {
        var oThis = $(this);
        //获取输入内容
        var oHfVal = $(this).siblings('.flex-text-wrap').find('.hf-input').val();
        // $.getJSON("https://easy-mock.com/mock/5a1bc24a9144e669fc6e7744/course/commit", function(data) {
        $.ajax({
            url: '/reply',
            type: 'POST',
            dataType: 'json',
            timeout: 5000,
            async: false,
            data: JSON.stringify({
                content: oHfVal,
                commentId: parseInt($('.pl-id').html())
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
            // console.log(data)
            // console.log("aha")
            _data = data.data
            // data_msg = '1111';
            // if (data.msg) {
            alert("回复成功成功！");
            // data_msg = true;
            // }
            // return false;
            $.each(data, function(n, v) {
                var oHtml = '<div class="all-pl-con"><div class="comment-show-con-img pull-left" style="display:none;border-random:5px;"><img class="img-id-' + _data.id + '" src="" alt="" style="width:30px;height:30px;"></div><div class="pl-text hfpl-text clearfix"><a href="#" class="comment-size-name">' + _data.from + ': </a><span class="my-pl-con">' + _data.content + '</span></div><div class="date-dz"> <span class="date-dz-left pull-left comment-time">' + _data.updateTime + '</span> <div class="date-dz-right pull-right comment-pl-block"> <a href="javascript:;" class="date-dz-pl pl-hf hf-con-block pull-left">回复</a></div> </div></div>';
                oThis.parents('.hf-con').parents('.comment-show-con-list').find('.hf-list-con').css('display', 'block').prepend(oHtml) && oThis.parents('.hf-con').siblings('.date-dz-right').find('.pl-hf').addClass('hf-con-block') && oThis.parents('.hf-con').remove();
                // $(".img-id-"+ i.id ).attr('src', i.img)
            })
        }

        function erryFunction() {
            alert("发表失败，请稍后重试！")
            data_msg = false;
        }

        // });
    });
})
