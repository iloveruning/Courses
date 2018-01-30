/*course2.js*/
$(document).ready(function() {

    // 获取链接中的参数
    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
    var id = GetQueryString('id');
    // alert(id)
    // var _url = 'http://localhost:8080'
    $.ajax({
        url:'/getCourseDetail',
        type: 'POST',
        timeout: 5000,
        dataType: 'json',
        data: JSON.stringify({
            id: id
        }),
        contentType: "application/json; charset=utf-8",

        success: functionSuccy,
        error: function() {
            alert('获取课程数据失败！')
        }
    })

    function functionSuccy(data) {
        // let data = data.data
        console.log(data)
        if (data.success) {
            $('.source').html(`管理学院精品课程/${data.data.course.type}`)
            $('.name').html(data.data.course.name)

            // 课程概要
            $('.course-intro').before('a').append(`简介：${data.data.course.introduction}&nbsp;&nbsp;&nbsp;`)
            $('.course-intro a').attr('index', data.data.course.id)
            $('.begin div').append(`<span>学科门类：<br/>${data.data}</span>
                        <span class="line"></span>
                        <span>课程类型：<br/>${data.data}</span>
                        <span class="line"></span>
                        <span>课程学时：<br/>${data.data}</span>
                        <span class="line"></span>
                        <span>课程学分：<br/>${data.data}</span>`)
            $('.begin a').attr('href', `video.html?id=${data.data.chapters[0].units[0].id}`)
            // $('.teacher-intro').html(data.data.teacherDetail.teacher.personIntroduction)
            // $('.teacher-name').html(`讲师介绍:${data.data.teacherDetail.teacher.name}`)
            $('.teacher-img').attr('src', data.data.teacherDetail.picture)
            $('.teacher-name').html(`讲师介绍：${data.data.teacherDetail.teacher.name}`)
            $('.teacher-intro').html(`简介：${data.data.teacherDetail.teacher.personIntroduction}`)


            // 加载知识点
            for (let i of data.data.knowledgePoints) {
                $('.point ul').append(`<li>
                      <a data-toggle="modal" index="${i.id}" data-target="#myModal" href="javascript:void(0);" title="">
                        ${i.content}
                      </a>`)
            }
            getKnowledge('.point ul li a')
            // 加载优秀作品
            // for (let i of data.data.works) {
            //     $('.works ul').append(`<li>
            //           <a data-toggle="modal" index="${i.url}" data-target="#myModal" href="javascript:void(0);" index="${data}" title="">i.work</a>
            //         </li>`)
            // }
            // 加载章节
            for (i in data.data.chapters) {
                $('.subNavBox').append(`<div class="subNav">${data.data.chapters[i].chapter.number}:${data.data.chapters[i].chapter.name}</div><ul class="navContent ul-${i}"></ul> `)
                // let a = i
                for (x in data.data.chapters[i].units) {
                    // console.log(i)
                    $('.subNavBox').find(`.ul-${i}`).append(`<li><a href="video.html?id=${data.data.chapters[i].units[x].id}">${data.data.chapters[i].units[x].number}:${data.data.chapters[i].units[x].name}</a></li>`)
                }
            }
            // 加载课程资料
            $('.datas ul').append(`<li>
                      <div>
                        <img align="center" src="http://dynamic1.icourses.cn/userpic/2012/1112/ff8080813ac0dd8f013ac130ded800a3_180.jpg" />
                        <a href="" index="${data.data.courseinfo.date.viewUrl}" data-toggle="modal" data-target="#myModal" title=""><p>${data.data.courseinfo.date.description}</p></a>
                      </div>
                    </li>`)
            $('.datas ul').append(`<li>
                      <div>
                        <img align="center" src="http://dynamic1.icourses.cn/userpic/2012/1112/ff8080813ac0dd8f013ac130ded800a3_180.jpg" />
                        <a href="" index="${data.data.courseinfo.standard.viewUrl}" data-toggle="modal" data-target="#myModal" title=""><p>${data.data.courseinfo.standard.description}</p></a>
                      </div>
                    </li>`)
            $('.datas ul').append(`<li>
                      <div>
                        <img align="center" src="http://dynamic1.icourses.cn/userpic/2012/1112/ff8080813ac0dd8f013ac130ded800a3_180.jpg" />
                        <a href="" index="${data.data.courseinfo.outline.viewUrl}" data-toggle="modal" data-target="#myModal" title=""><p>${data.data.courseinfo.outline.description}</p></a>
                      </div>
                    </li>`)
            $('.datas ul').append(`<li>
                      <div>
                        <img align="center" src="http://dynamic1.icourses.cn/userpic/2012/1112/ff8080813ac0dd8f013ac130ded800a3_180.jpg" />
                        <a href="" index="${data.data.courseinfo.guide.viewUrl}" data-toggle="modal" data-target="#myModal" title=""><p>${data.data.courseinfo.guide.description}</p></a>
                      </div>
                    </li>`)







            mainMsg('.datas ul li a')



            // 点击加载详情
            function mainMsg(cless) {
                $(cless).on('click', function() {
                    let id = $(this).attr('index')
                    // alert(id)
                    $('#myModal .modal-body iframe').attr('src', id)
                })
            }
        } else {
            alert(data.message)
        }
    }

    // 章节点击

    $('.subNavBox').on('click', '.subNav', function() {
        // console.log(55)
        $(this).toggleClass("currentDd").siblings(".subNav").removeClass("currentDd")
        $(this).toggleClass("currentDt").siblings(".subNav").removeClass("currentDt")

        // 修改数字控制速度， slideUp(500)控制卷起速度
        $(this).next(".navContent").slideToggle(500).siblings(".navContent").slideUp(500);
    })

    // 获取课程简介：
        $('.main-left .course-intro a').on('click',function () {
            $.ajax({
                url:_url,
                timeout:5000,
                type:'GET',
                dataType:'json',
                contentType: "application/json; charset=utf-8",
                error:function () {
                    console.log('error!')
                },
                success:function(data){
                    if(data.success){
                        $('#myModal iframe').css('display', 'none')
                        $('#myModal modal-body div').css('display', 'block')
                        $('#myModal modal-body div').html()
                    }else{
                        ('#myModal iframe').css('display', 'none')
                    $('#myModal modal-body div').css('display', 'block')
                    $('#myModal modal-body div').html('<center>' + data.message + '</center>')
                    }
                }
            })
        })




    // 获取知识点详情
    function getKnowledge(cless) {
        $(cless).on('click', function() {
            let id = $(this).attr('index')
            // alert(id)
            $.ajax({
                url: '/getUnitsByKnowledgePoint',
                type: 'POST',
                dataType: 'json',
                data: JSON.stringify({
                    id:id
                }),
                contentType: "application/json; charset=utf-8",
                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },
                success: functionSucc,
                error: function() {
                    $('#myModal iframe').css('display', 'none')
                    $('#myModal modal-body div').css('display', 'block')
                    $('#myModal modal-body div').html('<center>获取失败</center>')
                }
            })

            function functionSucc(data) {
                if (data.success) {
                    $('#myModal iframe').css('display', 'none')
                    $('#myModal modal-body div').css('display', 'block')
                    for (let i of data.data) {
                        $('#myModal modal-body div').append(`<li style="list-style:none;margin-left:20px;margin-top:20px;font-size:16px;"><a href="video.html?id=${i.unit.id}">${i.course.name}&nbsp;&nbsp;${i.chapter.number}&nbsp;&nbsp;${i.unit.number}&nbsp;&nbsp;${i.unit.name}</a></li>`)
                    }
                } else {
                    $('#myModal iframe').css('display', 'none')
                    $('#myModal modal-body div').css('display', 'block')
                    $('#myModal modal-body div').html('<center>' + data.message + '</center>')
                }
            }
        })
    }
})