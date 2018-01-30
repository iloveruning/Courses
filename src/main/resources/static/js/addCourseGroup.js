function sub() {
//验证结果都符合要求,则发出数据到数据库
    if (validateid() === false || validatename() === false) {
    } else {
        console.log(validatename());
        console.log(validateid());
        $.ajax({
            url: "/addCourseGroup",
            data: JSON.stringify({
                "number": $("#groupid").val(),
                "name": $("#groupname").val(),
                "introduction": $("#groupintro").val()
            }),
            type: 'post',
            async: false,
            cache: false,
            dataType: 'json',
            timeout: 1000,
            contentType: 'application/json; charset=UTF-8',
            beforeSend: loadFunction,
            error: errorFunction,
            success: succeedFunction
        });

        function loadFunction() {

        }

        function errorFunction() {
            alert("失败了");
        }

        function succeedFunction(data) {
            alert(data.message);
            window.location.reload();

        }
    }

}

function validateid() {
    if ($("#groupid").val().length == 0 || $("#groupid").val().length > 20) {
        console.log($("#groupid").val());
        alert("编号不能为空或者大于20个字符!");
        // window.location.reload();
        $("#groupid").focus();
        return false;
    }

    else {
        return true;
    }

}

function validatename() {
    if ($("#groupname").val().length == 0 || $("#groupname").val().length > 40) {
        alert("名称不能为空或者不能大于40个字符!");
        // window.location.reload();
        $("#groupname").focus();
        return false;
    } else {
        return true;
    }
}


