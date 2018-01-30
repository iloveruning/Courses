window.onload = function () {


    $.ajax({
        url: "/getCourseGroup_Teacher",
        type: 'GET',
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        // beforeSend: LoadFunction,
        error: geterrorFunction,
        success: gettsucceedFunction
    });

    function geterrorFunction() {
        alert("获取失败")
    }
//下拉框里的数据需要从数据库中获取,
    function gettsucceedFunction(data) {
        for (var i = data.data.courseGroups.length - 1; i >= 0; i--) {
            $("#ccgroup").append("<option value='" + data.data.courseGroups[i].id + "'>" + data.data.courseGroups[i].name + "</option>");
        }
        for (var i = data.data.teachers.length - 1; i >= 0; i--) {
            $("#cteacher").append("<option value='" + data.data.teachers[i].id + "'>" + data.data.teachers[i].name + "</option>");
        }
    }

};

function teachersub() {
    $.ajax({
        url: "/addCourse",
        data: JSON.stringify({
            "manager_account": $("#cadminname").val(),
            "manager_password": $("#cadminpass").val(),
            "number":$("#cnumber").val(),
            "name": $("#cname").val(),
            "type": $("#ctype").val(),
            "introduction": $("#cintro").val(),
            "credit": $("#ccredit").val(),
            "hours":$("#ctime").val()
        }),
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        // beforeSend: LoadFunction,
        error: errorFunction,
        success: postsucceedFunction
    });


    function errorFunction() {
        alert("失败了");
    }

    function postsucceedFunction(data) {
        alert(data.message);
        window.location.reload();
    }

}
//验证其是够为空,数据类型是否符合逻辑
function  validateisnull() {
    var cvarl=document.getElementById("ccgroup").value;
    var ccgroupbool=document.getElementById("ccgroup").value.is("1");
    console.log(ccgroupbool)

}