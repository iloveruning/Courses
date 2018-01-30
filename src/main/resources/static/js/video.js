/*video.js*/
$(document).ready(function () {

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
  

  $(".div-video").hover(function () {
    $(".hide-box").slideDown("slow")
  },function () {
    $(".hide-box").slideUp("slow")
  })

  // $("#my-video source").attr("src","http://www.w3school.com.cn/i/movie.ogg")
  $.ajax({
    url: "/getUnitDetail",
    type: 'POST',
    dataType: 'json',
    data: JSON.stringify({
            id: id
        }),
        contentType: "application/json; charset=utf-8",

    timeout: 10000,
    cache: false,
    beforeSend: LoadFunction,
    error: erryFunction,
    success: succFunction
  })

  function LoadFunction () {
    // console.log("requesting...please wait a moment!")
  }

  function erryFunction () {
    // alert("响应时间过长，请刷新重试！")
  }

  function succFunction (data) {
    let _data = data.data   
    $(".hide-box").html(_data.course.name+"&nbsp;&nbsp;"+_data.chapter.number+"&nbsp;&nbsp;"+_data.unit.name+"&nbsp;&nbsp;"+_data.unit.name)
    $("#my-video source").attr("src",_data.videos.url)

    $(".body-right .title").html(_data.chapter.number+"&nbsp;&nbsp;"+_data.unit.name+"&nbsp;&nbsp;"+_data.unit.name)
    // $(".body-right .course-about .c-need").html(_data.more.about)
      // 加载课程缩略图
      for(let i of _data.videos_chapter){
          $('.list ul').append(`<li>
                    <a href="/video?id=${i.unit.id}" title="">
                        <img src="${i.videos.picture.url}" />
                    <p>
                        ${i.unit.number}:${i.unit.name}
                    </p>
                    </a>
                </li>`)
      }

    // 加载本章知识点
    for(let i of _data.knowledgePoints){
      $('.div-body .body-right .r-r ul').append(`<li><a index="${i.id}" href="javascript:void(0);" />${i.content}</a></li>`)
    }
    getKnowledge('.div-body .body-right .r-r ul a')
    for(let i of _data.offices){
      $('.div-body .body-right .r-l .test ul').append(`<li>
                                    <img src="#" alt="" class="main" />
                                    <a class="" data-toggle="modal" index="${i.viewUrl}" data-target="#myModal" href="" title="">
                                        <p>${i.name}</p>
                                    </a>
                                </li>`)
    }

    getPdf('.body-right .r-l .test ul li a')
  }

    // 视频点击播放与暂停
    $("#my-video").on("click",function () {
    if(this.paused){
      this.play();
    }else{
      this.pause();
    }
  })
  $(".prev").click(function () {
    // console.log($(".list").offset().left)
  })

  $(".next").click(function () {
    // something...
  })

  // 获取数据详情(课程详细知识点)
  function getPdf (cless) {
    $(cless).on('click',function () {
      let id = $(this).attr('index')
      // $('#myModal modal-body div').css('display','none')
      $('#myModal iframe').attr('src',id)
    })
  }

  // 获取知识点详情
  function getKnowledge (cless) {
    $(cless).on('click',function () {
      let id = $(this).attr('index')
      $.ajax({
        url:'/getUnitsByKnowledgePoint',
        type:'POST',
        dataType:'json',
        data: JSON.stringify(id),
        success:functionSucc,
        error: function () {
          $('#myModal iframe').css('display','none')
          $('#myModal modal-body div').css('display','block')
          $('#myModal modal-body div').html('<center>获取失败</center>')
        }
      })
      function functionSucc (data) {
        if(data.success){
          $('#myModal iframe').css('display','none')
          $('#myModal modal-body div').css('display','block')
          for(let i of data.data){
            $('#myModal modal-body div').append(`<li style="list-style:none;margin-left:20px;margin-top:20px;font-size:16px;"><a href="video.html?id=${i.unit.id}">${i.course.name}&nbsp;&nbsp;${i.chapter.number}&nbsp;&nbsp;${i.unit.number}&nbsp;&nbsp;${i.unit.name}</a></li>`)
          }
        }else{
          $('#myModal iframe').css('display','none')
          $('#myModal modal-body div').css('display','block')
          $('#myModal modal-body div').html('<center>'+data.message+'</center>')
        }
      }
    })
  }

  // 点击播放视频
  $('.pause').on('click',function () {
     var src = $(this).data("src"),
       sourceDom = $("<source src=\""+ src +"\">");
       
   $("#video-box video").append(sourceDom);
   $("#video-box").show();
   
   // 自动播放
   $("#video-box video")[0].play()
  })

    // 点击next，prev切换模块
    let width = $('.list ul li').length*150
    $('.list ul').css('width',width)

    let left = $('.list ul').offset().left
    let _left = left
    // let right = $('.list ul').offset().right
    // console.log(right)
    $('.next').on('click',function () {
        // alert(8520)
        let minL = _left-width+750
        // console.log(minL)

        if(left<=minL){
            $('.list ul').offset({left:minL})
        }else{
            $('.list ul').offset({left:left -= 150})
        }
    })
    $('.prev').on('click',function () {
        let maxL = _left+width-900
        // console.log(maxL)
        // alert(8520)
        // console.log(_left)

        if(left>=_left){
            $('.list ul').offset({left:_left})
        }else{
            $('.list ul').offset({left:left += 150})
        }
    })



})