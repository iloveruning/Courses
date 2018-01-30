
var E = window.wangEditor;
var editor = new E('#infoContent');
$(document).ready(function () {

    // 或者 var editor = new E( document.getElementById('editor') )
    editor.create()
});

function addInformation() {
    $.ajax({
        url: '/notice',
        type: 'post',
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        data: JSON.stringify({
            "title": $("#infoTitle").val(),
            "content": editor.txt.html()
        })
    })
        .done(function () {
            alert("添加成功");
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
