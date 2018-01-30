/*login.js*/
$(document).ready(function () {
    $("#loginbutton").addClass("manage");
    rememberPass();
    // console.log($(".user-psw"))
    // 对密码进行加密
    /*$(".user-psw").blur(function () {
      let before = $(this).val();
      let beforeVal = $.md5(before);
      $(".user-psw").val(beforeVal)
      console.log($(".user-psw").val())
    })*/

    /*if ($.cookie("rmbUser") == "true") {
        $(".check").attr("checked", true);
        $(".user-name").val($.cookie("username"));
        $(".user-psw").val($.cookie("password"));
    }*/

    $.ajax({
        url: "/getAllCourses",
        type: 'GET',
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        // beforeSend: LoadFunction,
        error: geterrorFunction,
        success: gettsucceedFunction
    });

    function geterrorFunction() {
        alert("服务器连接失败") // body...
    }

    // 渲染课程组
    function gettsucceedFunction(data) {
        for (var i = data.data.length - 1; i >= 0; i--) {
            $("#usertype").append("<option value='" + data.data[i].id + "'>" + data.data[i].name + "</option>");
        }
        // body...
    };

    $(".forget a").click(function() {
        console.log(1)
        alert("请联系超级管理员，询问或重新设置密码")
    })

//点击选择管理员
    clickBtn("#manage");
    clickBtn("#super");
//todo 这里要选择某一个课程组进入其界面,现在是写死状态
    $("#loginbutton").click(
        function sub() {
            $.ajax({
                url: "/login",
                type: 'POST',
                dataType: 'json',
                timeout: 1000,
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify({
                    account: $("#user").val(),
                    password: $("#pswd").val(),
                    course_id:$("#usertype").val()
                }),
                success: function(data) {
                    console.log(data)
                    if(data.success){

                        alert(data.message);

                        if (data.data==1) {
                            window.location.href = "/superadmin";
                        }else{
                            window.location.href = "/admin";
                        }
                    } else {
                        alert("用户名或密码不正确！")
                    }
                },
                error: function (data) {
                    console.log(data);

                    alert("服务器响应时间过长，请稍后重试！")
                }
            })
        })


});

// 记住密码
/*function save() {
    if ($(".user-name").attr("checked")) {
        let str_username = $(".user-name").val();
        let str_password = $(".user-psw").val();
        $.cookie("rmbUser", "true", { expires: 7 }); //存储一个带7天期限的cookie
        $.cookie("username", str_username, { expires: 7 });
        $.cookie("password", str_password, { expires: 7 });
    } else {
        $.cookie("rmbUser", "false", { expire: -1 });
        $.cookie("username", "", { expires: -1 });
        $.cookie("password", "", { expires: -1 });
    }
}*/


function clickBtn(a) {
    $(a).click(function () {
        if (a === "#manage") {
             $("#usertype").css({"display": "block"});
            if ($("#loginbutton").hasClass("super") == true) {
                $("#loginbutton").removeClass("super");
                $("#loginbutton").addClass("manage");
            } else {
                $("#loginbutton").addClass("manage");
            }


        }
        else {
            $("#usertype").css("display", "none");
            if ($("#loginbutton").hasClass("manage") == true) {
                $("#loginbutton").removeClass("manage");
                $("#loginbutton").addClass("super");
            } else {
                $("#loginbutton").addClass("super");
            }
        }
    })
}

/**
 * @Author: yanni
 * @Description:记住密码
 * @Date: 15:20 2018/1/20
 * @Modified By:
 * @Params: * @param null
 */ function rememberPass(){
    var oForm = document.getElementById('loginForm');
    var oUser = document.getElementById('user');
    var oPswd = document.getElementById('pswd');
    var oRemember = document.getElementById('chek');
    //页面初始化时，如果帐号密码cookie存在则填充
    if(getCookie('user') && getCookie('pswd')){
        oUser.value = getCookie('user');
        oPswd.value = getCookie('pswd');
        oRemember.checked = true;
    }
    //复选框勾选状态发生改变时，如果未勾选则清除cookie
    oRemember.onchange = function(){
        if(!this.checked){
            delCookie('user');
            delCookie('pswd');
        }
    };
    //表单提交事件触发时，如果复选框是勾选状态则保存cookie
    oForm.onsubmit = function(){
        if(oRemember.checked){
            setCookie('user',oUser.value,7); //保存帐号到cookie，有效期7天
            setCookie('pswd',oPswd.value,7); //保存密码到cookie，有效期7天
        }
    };
};
//设置cookie
function setCookie(name,value,day){
    var date = new Date();
    date.setDate(date.getDate() + day);
    document.cookie = name + '=' + value + ';expires='+ date;
};
//获取cookie
function getCookie(name){
    var reg = RegExp(name+'=([^;]+)');
    var arr = document.cookie.match(reg);
    if(arr){
        return arr[1];
    }else{
        return '';
    }
};
//删除cookie
function delCookie(name){
    setCookie(name,null,-1);
};
