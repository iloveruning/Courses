window.onload = function () {


    $.ajax({
        url: "/getCourseGroups",
        type: 'GET',
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        // beforeSend: LoadFunction,
        error: geterrorFunction,
        success: gettsucceedFunction
    });

    function geterrorFunction() {
        alert("获取失败") // body...
    }

    function gettsucceedFunction(data) {
        for (var i = data.data.length - 1; i >= 0; i--) {
            $("#tgroup").append("<option value='" + data.data[i].id + "'>" + data.data[i].name + "</option>");
        }
    }

};

function teachersub() {
    // if (validatetheacher() == false) {
    // }
    // else {
    $.ajax({
        url: "/addTeacher",
        data: JSON.stringify({
            "number": $("#tid").val(),
            "name": $("#tname").val(),
            "sex": $("#tsex").val(),
            "position": $("#tposition").val(),
            "personIntroduction": $("#tintro").val()
        }),
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        // beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedFunction
    });


    function errorFunction() {
        alert("失败了");
    }

    function succeedFunction(data) {
        alert(data.message);
        window.location.reload();
    }

}

// var thid = "";
// var thname = "";
// var thsex = "";
// var thposition = "";
// thid = validatetheacher("教师编号", tid, 20)
// thname = validatetheacher("教师姓名", tname, 20);
// thsex = validatetheacher("教师性别", tsex, 4);
// var thposition = validatetheacher("教师职务", tposition, 40);

function validatetheacher(name, id, limitLength) {

    if (document.getElementById(id).value.length > limitLength) {
        alert(name + '长度必须小于' + limitLength + '!');
        document.getElementById(id).value = '';
        this.focus();
        return false;
    } else if (document.getElementById(id).value.length === 0) {
        alert(name + '不能为空' + '!');
        this.focus();
        return false;
    } else {
        return true;
    }



}
