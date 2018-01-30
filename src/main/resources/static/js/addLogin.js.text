/*addLogin.js*/
$(document).ready(function() {
    // var _url = 'http://xb3gaq.natappfree.cc'
    // var _data

    // console.log($('userMsg'))
    // console.log($(document).on('click'))   
    // let html = ``
    // 登录表
    let htmLogin = `<div class="modal fade" id="myModaLogin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
          登录
        </h4>
      </div>
      <div class="modal-body">
        <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
          <input type="text" class="form-control userName" placeholder="请输入邮箱..."/>
        </div>
        <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
          <input type="password" class="form-control userPsd" placeholder="请输入密码..." />
        </div>
        <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-magnet"></i></span>
          <input type="text" style="width:35%;" class="form-control userYzm" placeholder="请输入验证码.."/>
          <img id="yzm" src='' />
          <a href="javascript:;" class="change" style="font-size:14px;">看不清？换一张</a>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default calloff" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary dLogin">
          登录
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div>`

    //  注册表
    let htmSign = `<div class="modal fade" id="myModalSign" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
          注册
        </h4>
      </div>
      <div class="modal-body">
      <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="  glyphicon glyphicon-envelope"></i></span>
          <input type="text" class="form-control userTel" placeholder="请输入要验证的邮箱..." />
        </div>
        <p style="font-size:12px;color:red;display:none" class="emailWorn"> 输入密码不符合要求,请输入6-18位密码,且必须包含数字字母和特殊字符 </p>
      
        <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
          <input type="text" class="form-control signName"  placeholder="请输入昵称..."/>
        </div>
        <p style="font-size:12px;color:red;display:none" class="nameWorn"> 输入昵称不符合要求，请输入6-16位由汉字字母或数字组成的用户名 </p>
        
        <div class="input-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
          <input type="password" class="form-control signPsd" placeholder="请输入密码..."/>
        </div>
        <p style="font-size:12px;color:red;display:none" class="psdWorn"> 输入密码不符合要求,请输入6-18位密码,且必须包含数字字母和特殊字符 </p>
      

        <div class="input-group form-group" style="margin-top:25px;">
          <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
          <input type="text" class="form-control userVerify" style="width:65%" placeholder="请输入验证码..." />
          <button class="btn btn-success btnVerify">点击获取验证码</button>
        </div>

        </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default calloff" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary sureLogin">
          注册
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->
</div>`


    $('body').append(htmLogin, htmSign)


    // console.log(email)
    // alert(111)
    $('#myModaLogin,#myModalSign').modal({ backdrop: 'static', keyboard: false, show: false });
    // $('#myModal').find('input').val('8520')
    $('#myModaLogin,#myModalSign').on('show.bs.modal', function() {
        // alert(5)
        $('#myModaLogin,#myModalSign').find('input').val('')
    })

    // $.cookie('isZan','1', { expires: 1, path:'/'});

    // $.cookie('uersMsgId')

    // 初次加载时根据cookie作判断是否登录
    if ($.cookie('userMsgId') !== 'null' && $.cookie('userMsgId')) {
        // console.log("8520")
        var _id = $.cookie('userMsgId')
        var _name = $.cookie('userMsgName')
        var _img = $.cookie('userMsgImg')
        $.ajax({
            // url: _url + '/login/stu',
            url: '/login/status',
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify({
                id: _id,
                name: _name
            }),
            contentType: "application/json; charset=utf-8",

            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },

            timeout: 5000,
            success: function(data) {
                console.log(data)
                if (data.success) {
                    $('#myModaLogin').modal('hide')
                    $('.user').hide()
                    $('.person').show()
                    $('.person').find('img').attr("src", _img)
                    $('.person').find('.caret').html(_name + '&nbsp;&nbsp;')
                }
            }

        })

    } else {
        $('.person').hide()
        $('.user').show()
    }



    // 登录请求
    $(".dLogin").on('click', function() {
        // alert($('#myModal').find('userName').val())
        let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        let email = $('#myModaLogin').find('.userName').val()
        let psd = $.md5($('#myModaLogin').find('.userPsd').val())
        let yzm = $('#myModaLogin').find('.userYzm').val()
        if (email == '' && psd == '' && yzm == '') {
            alert('请输入邮箱、密码或验证码！')
        } else if (!reg.test(email)) {
            alert('请输入正确的邮箱格式！！！')
        } else {
            $.ajax({
                url: '/login/stu',
                type: 'POST',
                data: JSON.stringify({
                    email: email,
                    password: psd,
                    code: yzm
                }),
                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                timeout: 5000,

                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },

                beforeSend: loadFunction,
                error: erryFunction,
                success: succyFunction
            })
        }

        function loadFunction() {
            console.log('登录中...')
        }

        function erryFunction() {
            alert('登录异常，请稍后重试！！')
        }

        function succyFunction(data) {
            console.log(data.data)
            // data : img name id
            if (data.success) {
                let img = data.data.img
                let name = data.data.name
                let id = data.data.id
                $('#myModaLogin').modal('hide')
                $('.user').hide()
                $('.person').show()
                $('.person').find('img').attr("src", img)
                $('.person').find('.caret').html(name + '&nbsp;&nbsp;')
                // _data = {
                //     id: data.data.id,
                //     name: data.data.name,
                //     img: data.data.img
                // }
                // $.cookie('user_cookie','8520',{expires:1})
                $.cookie('userMsgId', data.data.id, { expires: 1, path: '/resource/template' })
                $.cookie('userMsgName', data.data.name, { expires: 1, path: '/resource/template' })
                $.cookie('userMsgImg', data.data.img, { expires: 1, path: '/resource/template' })
                // console.log($.cookie('userMsg',_data.id));
            } else {
                alert(data.message)
                $('#myModaLogin').find('input').val('')
            }
        }

    })

    // 点击下拉菜单
    $('.dropdown-toggle').dropdown();


    // 退出登录
    $('.logout').on('click', function() {
        console.log(1)
        $.ajax({
            url: '/login/logout',
            type: 'GET',
            dataType: 'json',
            timeout: 5000,

            crossDomain: true,
            xhrFields: {
                withCredentials: true
            },

            beforeSend: function() {
                // 
            },
            error: function() {
                // alert("")
            },
            success: function(data) {

                // alert('55');
                $('.person').hide()
                $('.user').show()

                $.cookie('userMsgId', null, { path: '/resource/template' })
                $.cookie('userMsgName', null, { path: '/resource/template' })
                $.cookie('userMsgImg', null, { path: '/resource/template' })

                // window.location.href = "../../index.html"
            }
        })
    })



    // console.log(chargeName())

    // 获取验证码
    $('.btnVerify').on('click', function() {

        let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        let email = $('#myModalSign').find('.userTel').val()
        if (!reg.test(email)) {

            alert('请输入正确的邮箱格式！！！')
        } else {
            $.ajax({
                url: '/login/email',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    email: email
                }),
                timeout: 5000,

                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },

                beforeSend: loadFun,
                error: erryFun,
                success: succyFun
            })
            timer()
        }




        function loadFun() {
            console.log('验证码发送中..')
        }

        function erryFun() {
            // alert('opps...')
        }

        function succyFun(data) {

        }
        // return email
    })

    // 点击注册

    $('.sureLogin').on('click', function() {
        // console.log(email)
        // alert($('.signPsd').val())
        let a = $('body').find('.signName').val()
        let b = $('body').find('.signPsd').val()
        // console.log(a)
        // console.log(b)
        if (a == '' && b == '') {
            alert('请输入用户名或密码！')
        } else if (!chargePsd() && !chargeName() && !chargeEmail()) {
            alert('用户名或密码格式有误')
        } else {
            $.ajax({
                url: '/login/register',
                type: 'POST',
                data: JSON.stringify({
                    name: $('.signName').val(),
                    password: $.md5($('.signPsd').val()),
                    email: $('#myModalSign').find('.userTel').val(),
                    code: $('.userVerify').val()
                }),

                crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },

                contentType: "application/json; charset=utf-8",
                dataType: 'json',
                timeout: 5000,
                beforeSend: VLoadFunction,
                error: VErryFunction,
                success: VSuccyFunction
            })
        }

        function VLoadFunction() {
            console.log('注册中...')
        }

        function VErryFunction() {
            alert('注册异常，请稍后重试！！')
        }

        function VSuccyFunction(data) {
            if (data.success) {
                alert('注册成功！')
                $('#myModalSign').modal('hide')
            } else {
                alert(data.message)
            }
        }
    })

    // 注册邮箱验证是否已经存在
    function chargeEmail() {
        $('body').find('.userTel').blur(yzEmail)
        return yzEmail()
    }
    chargeEmail()

    function yzEmail() {
        let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
        let email = $('#myModalSign').find('.userTel').val()
        if (email == '') {
            console.log(1)
        } else if (!reg.test(email)) {
            alert('请输入正确的邮箱格式！！！')
        } else {
            $.ajax({
                url: '/login/valid',
                type: 'POST',
                dataType: 'json',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    email: email
                }),
                timeout: 5000,
                beforeSend: eloadFunction,
                error: eErryFunction,
                success: esuccyFunction
            })

            function eloadFunction() {
                //...
            }

            function eErryFunction() {
                alert('数据请求异常')
            }

            function esuccyFunction(data) {
                if (data.success) {
                    $('.emailWorn').css('display', 'none')
                    return true
                } else {
                    $('.emailWorn').css('display', 'block')
                    return false
                }
            }

        }
    }

    // 用户名判断
    function chargeName() {
        // let  a
        $('body').find('.signName').blur(funName)
        // return a
        return funName()
    }

    chargeName()

    function funName() {
        // alert(222)
        let name = $('.signName').val()
        let strName = /^[\u4e00-\u9fff\w]{6,16}$/
        // strName.test(name)
        if (name == '') {
            $('.nameWorn').css('display', 'none')
            return false

        } else if (strName.test(name)) {
            $('.nameWorn').css('display', 'none')
            return true
        } else {
            // alert('用户名要求5-16位')
            $('.nameWorn').css('display', 'block')
            return false
        }
        return false
    }



    // 密码验证
    function chargePsd() {
        $('body').find('.signPsd').blur(funPsd)
        return funPsd()
    }

    chargePsd()

    function funPsd() {
        // alert(222)
        let psd = $('.signPsd').val()
        let strPsd = /^(?=.*((?=[\x21-\x7e]+)[^A-Za-z0-9]))(?=.*[a-zA-Z])(?=.*[0-9])[^\u4e00-\u9fa5]{6,16}$/
        // strName.test(name)
        if (psd == '') {
            $('.psdWorn').css('display', 'none')
            return false
        } else if (strPsd.test(psd)) {
            $('.psdWorn').css('display', 'none')
            return true
        } else {
            // alert('用户名要求5-16位')
            $('.psdWorn').css('display', 'block')
            return false
        }
    }


    // 60s后重新发送
    function timer() {
        let i = 60
        let timer = setInterval(function() {
            i--
            console.log(i)
            if (i > 0) {

                $('.btnVerify').attr('disabled', true) // (' + i + ')s后重新获取验证码
                $('.btnVerify').text(`( ${ i } )s后重新获取验证码`)

            } else {
                $('.btnVerify').attr('disabled', false)
                $('.btnVerify').text(`点击获取验证码`)
                clearInterval(timer)
            }
        }, 1000)

    }

    // 点击登录加载验证码  http://6rrsp7.natappfree.cc/login/getGifCode
    $('.user-dl').on('click', function() {
        // alert(52)
        $('#myModaLogin').find('#yzm').attr('src', '/login/getGifCode')
    })

    // 点击看不清，更换验证码
    $('.change').on('click', function() {
        let url = $('#yzm').attr('src')
        let urlChange = url + '?' + Math.random()
        $('#yzm').attr('src', urlChange)
    })

})