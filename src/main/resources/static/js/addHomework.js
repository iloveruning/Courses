$(document).ready(function () {
    'use strict';
    $.ajax({
        url: "json/homework.json",
        type: "post",
        data: JSON.stringify({}
        ),
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function () {

            console.log("success");
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

});

//显示文件名称
function showHomeworkName() {
    var inputname = $("#filepreview").val();
    $("#textName").val(inputname);

    console.log("文件正在上传");

    /*var name = $("#filepreview").val();*/
    var formData = new FormData();
    /*var name = $("input").val();
    formData.append("name", name);*/
    console.log(formData);
    formData.append("file", $("#filepreview")[0].files[0]);

    $.ajax({
        url: "http://3y5z5q.natappfree.cc/homework",
        type: 'POST',
        data: formData,
        // 告诉jQuery不要去处理发送的数据
        processData: false,
        // 告诉jQuery不要去设置Content-Type请求头
        contentType: false,
        cache: false,
        beforeSend: function () {
            console.log("正在进行，请稍候");
        },
        success: function (data) {
            console.log(data);
            /*console.log(data);*/

            $("#fileId").val(data.data.id);

        },
        error: function () {
            console.log("error");
        }
    });

}

function addHomework() {
    if ($("#textName").val() == "") {
        alert("请上传文件后再提交!")
    } else {
        $.ajax({
            url: "http://3y5z5q.natappfree.cc/file/office",
            type: "post",
            data: JSON.stringify({
                    "name": $("#homeworkstudent").val(),
                    "officeId": $("#fileId").val()
                }
            ),
            dataType: 'json',
            timeout: 5000,
            contentType: 'application/json; charset=UTF-8',
            cache: false
        })
            .done(function () {
                alert("添加成功")
                console.log("success");
            })
            .fail(function () {
                alert("添加失败")
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });
    }


}
