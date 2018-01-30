$(document).ready(function() {

    // var _url = 'http://v49fha.natappfree.cc'
    // 轮播图模块
    var i = 0;
    var timer, detail;
    // var _url = 'http://localhost:8080'


    $.ajax({
        url: '/getHomepageInfo',
        timeout: 5000,
        type: 'GET',
        dataType: 'json',
        contentType: "application/json",
        error: function() {
            console.log('error!')
        },
        success: funSucc
    })

    function funSucc(data) {
        if (data.success) {
            for (let i in data.data) {
                $('.classify .classify-ul').append(`<li index="c${data.data[i].courseGroup.id}" class="group">
                        <a href="#">
                            <p>${data.data[i].courseGroup.name}</p><i class="  glyphicon glyphicon-chevron-right"></i></a>
                    </li>`)
                // console.log(1)
                $('.classify .classify-ul').after(`<div class="detail" id="c${data.data[i].courseGroup.id}">
                <div class="core">
                    <p>核心课程</p>
                    <hr/>
                    <ul>
                    </ul>
                </div>
                <br/>
                <br/>
                <br/>
                <div class="aid">
                    <p>辅助课程</p>
                    <hr/>
                    <ul>
                    </ul>
                </div>
            </div>`)

                // 插入详细课程分组
                $('.div-body').append(`
                        <div class="classify-course h-${i} div-a">
                            <span>
                              <p>
                                ${data.data[i].courseGroup.name}
                              </p>
                            </span>
                            <span class="more point" index="h-${i}">
                                更多 &gt;&gt;
                            </span>
                            <div class="div-up-h-${i} div-h point" index="a">
                                收起 &gt;&gt;
                            </div>
                            <ul class="course-h-${i} ul-a">
                                
                            </ul>
                        </div>
                    `)


                // console.log(2)
                for (let x of data.data[i].keyCourses) {
                    $(`#c${data.data[i].courseGroup.id} .core ul`).append(` <li><a href="course2.html?id=${x.course.id}">${x.course.name}</a></li>`)
                    $('.div-body').find(`.course-h-${i}`).append(`
                        <li>
                            <a href="course2.html?id=${x.course.id}" title="">
                                <img src="${x.picture}" alt="${x.course.name}" />
                                <div class="shadow">
                                    ${x.course.name}
                                </div>
                            </a>
                        </li>  
                        `)
                }
                // console.log(3)
                for (let y of data.data[i].ordinaryCourse) {
                    $(`#c${data.data[i].courseGroup.id} .aid ul`).append(`<li><a href="course2.html?id=${y.course.id}">${y.course.name}</a></li>`)
                    $('.div-body').find(`.course-h-${i}`).append(`
                        <li>
                            <a href="course2.html?id=${y.course.id}" title="">
                                <img src="${y.picture}" alt="${y.course.name}" />
                                <div class="shadow">
                                    ${y.course.name}
                                </div>
                            </a>
                        </li>  
                        `)
                }
                // console.log(4)
            }

        } else {
            alert(data.message)
        }
    }


    // 悬浮显示课程

    $('#one .classify-ul').on('mouseenter','.group',function () {
        $('.detail').hide()
        $("#" + $(this).attr("index")).show()
    })
    $('#one .classify-ul').on('mouseleave','.group',function () {
        let _this = this
        detail = $("#" + $(_this).attr("index"))
        // console.log(detail)
        timer = setTimeout(function() {
            detail.hide()
        }, 3000)
    })

    $('#one .classify-ul').on('mouseenter','.detail',function () {
        console.log(22)
        clearTimeout(timer)
        $(this).css('display','block')
    })
    $('#one .classify-ul').on('mouseleave','.detail',function () {
        $(this).css('display','none')
    })



    $(function() {
        $(".img").eq(0).fadeIn(1000).siblings().fadeOut(1000);
        $(".item").eq(i).css({ 'background-color': 'red' });
        showTime();
        $(".item").hover(function() {
            clearInterval(timer);
            i = $(this).index();
            showT();
        }, function() {
            showTime();
        });
        $(".btn-left").on("click", function() {
            clearInterval(timer);
            if (i == 0) {
                i = 6;
            }
            i--;
            showT();
            showTime();
        });
        $(".btn-right").on("click", function() {
            clearInterval(timer);
            if (i == 5) {
                i = -1;
            }
            i++;
            showT();
            showTime();
        })

        // $('#usertype').selectpicker({
        //     'selectedText': 'cat'
        // });
    });

    function showT() {
        $(".img").eq(i).fadeIn(1000).siblings().fadeOut(1000);
        $(".item").eq(i).css({ 'background-color': 'red' }).siblings().css({ 'background-color': 'rgba(0,0,0,0.4' });
    };

    function showTime() {
        timer = setInterval(function() {
            i++;
            if (i == 6) {
                i = 0;
            }
            showT();
        }, 5000);
    };

    // console.log($("#one"))




    /*$("li").hover(function (){
        $(".detail").hide();
        $("#"+$(this).attr("index")).show();
        // $("#"+$(this).attr("index")).addClass("show")
    },function () {
        let _this=this
        
        setTimeout(function () {
            $("#"+$(_this).attr("index")).hide();

        },1500)
        
        
    })
    $(".show").mouseenter(function () {
        console.log(this)
        });
    $(".show").mouseleave(function () {
        
    })*/




    // 点击展开课程
    $('.div-body').on('click', '.more', function() {
        console.log('1')
        $(".course-" + $(this).attr("index")).height("auto")
        $("." + $(this).attr("index")).height("auto")
        $(".div-up-" + $(this).attr("index")).show();
        $(this).hide();
        console.log('2')
    })


    // 点击收起课程
    $('.div-body').on('click', '.div-h', function() {
        console.log(3)
        $(".ul-" + $(this).attr("index")).height("180");
        $(".div-" + $(this).attr("index")).height("210");
        $(this).hide();
        $(".more").show();
        console.log(4)
    })
})