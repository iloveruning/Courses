/*teachers.js*/
$(document).ready(function() {
    // var _url = 'http://localhost:8080'

    $.ajax({
        url: '/getTeacherTeam',
        type: 'GET',
        dataType: 'json',
        timeout: 5000,
        async: false,
        error: function() {
            console.log('请求失败！')
        },
        success: successFunction
    })

    function successFunction(data) {
        console.log(data.data[0].teachers)
        if (data.success) {
            for (let i in data.data) {
                $('.div-body .body-left ul').append(`<li id="group-${i}" index="${data.data[i].courseGroup.id}"><i class="glyphicon glyphicon-unchecked"></i>${data.data[i].courseGroup.name}</li>`)
                $('.div-body .body-left').after(`<div class="body-right group-${i}"></div>`)
                console.log(1)
                for(let x of data.data[i].teachers){
                    console.log(2)
                    $('.div-body').find(`.group-${i}`).append(`<div class="ther-per">
                    <div class="per-left">
                        <img src="${x.picture}" />
                    </div>
                    <div class="per-right">
                        <p>
                            成员
                        </p>
                        <p>
                            姓名：${x.teacher.name}
                        </p>
                        <p>
                            性别：${x.teacher.sex}
                        </p>
                        <p>
                            职称：${x.teacher.position}
                        </p>
                        <p>
                            <a href="javascript:void(0);" data-toggle="modal" data-target="#myModal" index="${x.teacher.id}" onclick="getId(this)"  title="">个人简介<i class="  glyphicon glyphicon-play"></i></a>
                        </p>
                    </div>
                </div>`)
                    // console.log(3)
                }
                
            }
            $('.div-body .body-left ul').children("li:first-child").attr('class', 'checked')
            $('.div-body .body-right:last').addClass('live')
            
        } else {
            alert(data.message)
        }
    }



    $('.body-left').find('li').on('click', function() {
        clickBtn(this)
        let id = $(this).attr('index')
        let cliss = $(this).attr('id')
        // getGroup(_url, id, cliss);
    })
})

// 点击切换页面
function clickBtn(e) {
    let id = $(e).attr('id')
    // console.log(id)
    $('.body-left').find('li').removeClass('checked')
    $(e).addClass('checked')
    $('.body-right').removeClass('live')
    $('.div-body').find('.' + id).addClass('live')
}

// 获取教师团队

// function getGroup(url, id, cliss) {
//     $.ajax({
//         url: url + '?id=' + id,
//         dataType: 'json',
//         type: 'GET',
//         timeout: 5000,
//         success: succyFun,
//         error: erryFun
//     })

//     function succyFun(data) {
//         if (data.success) {
//             $('.' + cliss).html('');
//             for (let i of data.data.teacher) {
//                 $('.' + cliss).append(`<div class="ther-per">
//                     <div class="per-left">
//                         <img src="${i.url}" />
//                     </div>
//                     <div class="per-right">
//                         <p>
//                             成员
//                         </p>
//                         <p>
//                             姓名：${i.name}
//                         </p>
//                         <p>
//                             性别：${i.sex}
//                         </p>
//                         <p>
//                             职称：${i.post}
//                         </p>
//                         <p>
//                             <a href="javascript:void(0);" data-toggle="modal" data-target="#myModal" index="${i.more}" onclick="getId(this)"  title="">个人简介<i class="  glyphicon glyphicon-play"></i></a>
//                         </p>
//                     </div>
//                 </div>`)
//             }

//         } else {
//             console.log(data.message)
//         }
//     }

//     function erryFun() {
//         alert('加载失败！')
//     }
// }

function getId(e) {
    // alert(11)
    let id = $(e).attr('index')
    // console.log(id)
    $.ajax({
        url: 'queryTeacherById',
        type:'POST',
        dataType:'json',
        timeout:5000,
        contentType: "application/json",
        data:{
            id:id
        },
        success:functionSucc,
        error: function () {
            console.log('error!')
        }


    })
    function functionSucc (data) {
        if(data.success){
            $('.modal-body').append(data.data.teacher.personIntroduction)
        }else{
            alert(data.message)
        }
    } 

    // $('#myModal').find('iframe').attr('src', id)
}