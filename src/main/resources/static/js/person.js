// person.js
$(document).ready(function() {
    // var _url = 'http://localhost:8080'
    // 判断是否已经登录，没有登录拒绝访问
    if ($.cookie('userMsgId') == 'null' || !$.cookie('userMsgId')) {
        window.location.href = "../../index.html"
        // alert("淘气了哟")
    }
    // var image = ''
    // 点击切换页面
    function clickBtn(e) {
        let id = $(e).attr('id')
        console.log(id)
        $('.body-left').find('li').removeClass('checked')
        $(e).addClass('checked')
        $('.body-right').removeClass('live')
        $('.div-body').find('.' + id).addClass('live')
    }

    $('.body-left').find('li').on('click', function() {
        clickBtn(this)
    })

    // 是否可被编辑
    function edit(clas) {
        if ($('.personal .' + clas).val() == '') {
            $('.personal .' + clas).attr('readonly', false)
            $('.personal .span-' + clas).css('display', 'none')
        } else {
            $('.personal .' + clas).attr('readonly', true)
            $('.personal .span-' + clas).css('display', 'block')
        }
    }



    // 获取个人信息

    $(function() {
        $.ajax({
            // url: _url+'/stu/info',
            url: '/stu/info',
            dataType: 'json',
            type: 'GET',
            contentType: false,
            processData: false,
            cache: false,
            success: successFun,
            error: erryFun
        })

        function erryFun() {
            // alert('数据加载失败...')
        }

        function successFun(data) {
            console.log(data)
            if (data.success) {
                $('.userName .uName').val(data.data.name)
                $('userTel .tel').val(data.data.phone)
                $('userIntro .explore').val(data.data.introduction)
                $('.img img').attr('src', $.cookie('userMsgImg'))
                edit('uName')
                edit('tel')
                edit('explore')
                // $('input:radio').eq(1).attr('checked', 'true')
                // $("input[type=radio]").attr("checked",'1')
                if (data.data.sex == 1) {
                    $('input:radio').eq(0).attr('checked', 'true')
                } else if (data.data.sex == 2) {
                    $('input:radio').eq(1).attr('checked', 'true')
                } else {
                    $('input:radio').eq(2).attr('checked', 'true')
                }
            } else {
                alert(data.message)
            }
        }
    })

    // 点击编辑编辑资料
    $('.personal .change').on('click', function() {
        $(this).parent().find('input').attr('readonly', false)
    })

    // 货物头像路径
    $('#upfile').on('change', function() {
        var fd = new FormData();
        fd.append('file', $("#upfile").get(0).files[0])
        // fd.append( 1);
        // fd.append($("#upfile").get(0).files[0]);
        $.ajax({
            url: "/file/pic",
            type: "POST",
            async: false,
            cache: false,
            contentType: false,
            processData: false,

            data: fd,
            beforeSend: loadFunction,
            success: succyFunction
        });

        function loadFunction() {
            $('.uping p').html('上传中....')
        }

        function succyFunction(data) {
            // alert(1)
            // console.log(data.data.url)
            if (data.success) {
                $('#image').attr('src', data.data.url)
                $('.uping p').html('上传头像')
                $.cookie('userMsgImg', data.data.url, { path: '/resource/template' })
            } else {
                alert(data.message)
            }
        }
    });


    // 上传用户填写信息
    $('.save').on('click', function() {
        // alert(image)
        let name = $('.uName').val()
        let tel = $('.tel').val()
        let sex = $('input:radio:checked').val()
        let explore = $('.explore').val()

        if (/^1[3|4|5|8][0-9]\d{4,8}$/.test(tel)) {
            $.ajax({
                url: '/stu/info/update',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json;",
                data: JSON.stringify({
                    name: name,
                    phone: tel,
                    sex: sex,
                    introduction: explore,
                    img: 'image'
                }),
                // contentType: false,
                processData: false,
                cache: false,
                success: successFun,
                error: erryFun
            })

            function successFun(data) {
                // ...
            }

            function erryFun() {
                alert("天啦，未知错误...")
            }
        } else {
            alert('手机号输入有误！！')
        }





    })

    // 点击加载以学习课程 

    $('#learning').on('click', function() {
        $.ajax({
            url: '/sc/stu/1',
            type: 'GET',
            dataType: 'json',
            // cache: false,

            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },

            beforeSend: loadFun,
            success: successFun,
            error: erroyFun
        })

        function loadFun() {
            // ...
        }

        function erroyFun() {
            alert('数据加载失败，请稍后重试！')
            $('.learning p').removeClass('act')
            $('.t-two').addClass('act')
        }

        function successFun(data) {
            $('.learning').html('')
            if (data.success) {
                if (data.data.length == 0) {
                    $('.learning p').removeClass('act')
                    $('.t-one').addClass('act')
                } else {
                    $('.learning p').removeClass('act')
                }
                // console.log(data.data.list)

                for (let i in data.data.list) {
                    // console.log(data.data[i].img)
                    $('.learning').append(`<div class="course">
                        <div class="course-top">
                          <img class="per-img" src="${$.cookie('userMsgImg')}"/>
                          <span>${$.cookie('userMsgName')}</span>
                        </div>
                        <div class="course-bottom">
                          <img src="${data.data.list[i].img}" class="course-img" />
                          <div class="title">
                            <p class="c-name">${data.data.list[i].cname}</p>
                            <p>${data.data.list[i].gname}</p>
                            <p>${data.data.list[i].type}</p>
                            <a href="/course.html?${data.data.list[i].id}" title="" class="btn btn-info">进入学习</a>
                          </div>
                        </div>
                    </div>`)
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
        }
    })

    // 点击加载所提问题
    $('#asking').on('click', function() {


        $.ajax({
            url:  '/comment/stu/my/1',
            type: 'GET',
            dataType: 'json',
            cache: false,
            beforeSend: loadFun,
            success: successFun,
            error: erroyFun
        })

        function loadFun() {
            // ...
        }

        function erroyFun() {
            alert('数据加载失败，请稍后重试！')
            $('.asking p').removeClass('act')
            $('.asking .t-two').addClass('act')
        }

        function successFun(data) {
            // console.log(data)
            if (data.success) {
                $('.asking').html('')
                if (data.data.list.length == 0) {
                    $('.asking p').removeClass('act')
                    $('.asking .t-one').addClass('act')
                } else {
                    $('.asking p').removeClass('act')
                }

                for (let i of data.data.list) {
                    $('.asking').append(`<div class="comment-show">
                        <div class="comment-show-con clearfix">
                            <div class="comment-show-con-img pull-left"><img style="width:40px;height:40px;" src="https://img2.mukewang.com/59e96f420001726302400240.jpg" alt=""></div>
                            <div class="comment-show-con-list pull-left clearfix">
                                <div class="pl-text clearfix">
                                    <a href="#" class="comment-size-name">来自 : </a>
                                    <span class="my-pl-con">&nbsp;${i.courseName}第${i.chapterNum}章：${i.chapterName}</span>
                                </div>
                                <div class="pl-text clearfix">
                                    <a href="#" class="comment-size-name">评论内容: </a>
                                    <span class="my-pl-con">&nbsp;${i.content}</span>
                                </div>
                                <div class="date-dz">
                                    <span class="date-dz-left pull-left comment-time">${i.time}</span>
                                    <div class="date-dz-right pull-right comment-pl-block">
                                        <a href="javascript:;" style="display:none" class="id">${i.id}</a>
                                        <a href="javascript:;" class="pl-sum date-dz-pl hf-con-block pull-left pl-id-${i.id}">${i.reply}条回复</a>
                                        <span class="pull-left date-dz-line">|</span>
                                        <a href="javascript:;" class="date-dz-z pull-left"><i class="date-dz-z-click-red"></i>赞 (<i class="z-num">${i.agreeCount}</i>)</a>
                                    </div>
                                </div>
                                <div class="hf-list-con"></div>
                            </div>
                        </div>
    </div>`)

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
        }

    })

    // 删除评论
    // $('.asking').on('click', '.removeBlock', function() {
    //     let id = $(this).parent().find('.id').html()
    //     $.ajax({
    //         url: _url + '',
    //         type: 'GET',
    //         dataType: 'json',
    //         cache: false,
    //         data: {
    //             id: id
    //         },
    //         beforeSend: loadFun,
    //         success: successFun,
    //         error: erroyFun
    //     })

    //     function loadFun() {
    //         // ...
    //     }

    //     function erroyFun() {
    //         alert('删除失败，请稍后重试！')
    //     }

    //     function successFun(data) {
    //         if (data.success) {
    //             var oT = $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con');
    //             if (oT.siblings('.all-pl-con').length >= 1) {
    //                 oT.remove();
    //             } else {
    //                 $(this).parents('.date-dz-right').parents('.date-dz').parents('.all-pl-con').parents('.hf-list-con').css('display', 'none')
    //                 oT.remove();
    //             }
    //             $(this).parents('.date-dz-right').parents('.date-dz').parents('.comment-show-con-list').parents('.comment-show-con').remove();

    //         } else {
    //             alert(data.msg)
    //         }
    //     }


    // })

    // 点击加载回复
    $('.asking').on('click', '.pl-sum', function() {
        console.log("tyf is cool!!!")
        var oThis = $(this);
        var id = parseInt(oThis.parent().find(".id").html())

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


})