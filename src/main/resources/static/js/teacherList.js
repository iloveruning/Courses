window.onload = function () {

    //获取所有教师
    $.ajax({

        url: "/getAllTeachers",
        type: 'get',
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedFunction

    });


    function LoadFunction() {

    }

    function errorFunction() {
        alert("失败了");
    }

    function succeedFunction(data) {
        $('#teachertable').dataTable({
            "data": data.data,
            "columns": [

                {"data": "teacher.number"},
                {"data": "teacher.name"},
                {"data": "teacher.sex"},
                {"data": "teacher.position"},
                {"data": "teacher.personIntroduction"},
                {"data": null},
                {"data": null}

            ],
            language: {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            columnDefs: [{
                targets: 5,
                render: function (data, type, row, meta) {
                    // alert(course.name)
                    return "<a type='button' class='btn btn-primary"

                        + "' onclick='showteacherdetails(\""
                        + data.teacher.id
                        + "\")' href='#'>详情</a>"
                }

            }, {
                targets: 6,
                render: function (data, type, row, meta) {

                    // alert(data.courseGroup_id);
                    // console.log(data.course.id)
                    return "<a type='button' class='btn btn-danger"

                        + "' onclick='deloneteacher(\""
                        + data.teacher.id
                        + "\")' href='#'>删除</a>"

                }

            }],

        })
    }

};

function deloneteacher(teachid) {
    alert("该课程组可能含有重要信息，一旦删除不可恢复，请谨慎操作！！！")

    // console.log(id)
    $.ajax({
        url: "/deleteTeacher",
        data: JSON.stringify({
            "id": teachid
        }),
        traditional: false,
        type: 'post',
        cache: false,
        async: false,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {
            if (data.success === true) {
                alert(data.message);
                window.location.reload();
            } else {
                alert(data.message);
            }
        },


        error: function () {
            alert("删除失败，请稍后重试！")
        }
    })
}


/*function updatecoursegroup() {
    $.ajax({
        url: "/updateCourseGroup",
        data: JSON.stringify({
            "id": $("#updategroupid").val(),
            "number": $("#showgroupid").val(),
            "name": $("#showgroupname").val(),
            "introduction": $("#showgroupintro").val()
        }),
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        timeout: 1000,
        contentType: 'application/json; charset=UTF-8',
        error: errorFunction,
        success: postsucceedFunction
    });


    function errorFunction() {
        alert("失败了");
    }

    function postsucceedFunction(data) {
        alert(data.message);

    }
}*/

function showgroup() {
    $("#showgroupdisplay").css("display", "");

}

function showteacherdetails(teachid) {
    showgroup();

    //获取教师课程信息
    $.ajax({

        url: "/getTeacher_Courses",
        data: JSON.stringify({
            "id": teachid
        }),
        type: 'post',
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedgetcFunction

    });


    function LoadFunction() {

        $("#teachertablepanel").hide();

        $("#teacherdetailpanel").html("<div class=\"panel panel-default\">\n" +
            "\n" +
            "\n" +
            "                                        <div class=\"panel-heading\">\n" +
            "                                            <p class=\"panel-title\">负责课程</p>\n" +
            "                                        </div>\n" +
            "\n" +
            "                                        <div class=\"panel-body\">\n" +
            "                                            <div class=\"col-md-10\">\n" +
            "                                                <table id=\"coursesdatailtable\" class=\"table table-hover table-bordered\">\n" +
            "                                                    <thead>\n" +
            "                                                    <tr>\n" +
            "                                                        <td>课程组</td>\n"+
            "                                                        <td>课程编号</td>\n" +
            "                                                        <td>课程名称</td>\n" +
            "                                                        <td>课程类型</td>\n" +
            "                                                        <td>详情</td>\n" +
            "                                                        <td>删除</td>\n" +
            "\n" +
            "                                                    </tr>\n" +
            "                                                    </thead>\n" +
            "\n" +
            "                                    <tbody ></tbody>\n" +
            "\n" +
            "\n" +
            "                                                </table>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                                    </div>\n");


    }

    function errorFunction() {
        alert("失败了");
    }

    //显示老师的所属课程组
    function succeedgetcFunction(data2) {

        /*for (var i = data.data.courseGroups.length - 1; i >= 0; i--) {

            if (data.data.courseGroups[i].id == data.data.teacher.courseGroup_id) {


                $("#showteachergroup").append("<option selected value='" + data.data.courseGroups[i].id + "'>" + data.data.courseGroups[i].name + "</option>");
            }
            else {
                $("#showteachergroup").append("<option value='" + data.data.courseGroups[i].id + "'>" + data.data.courseGroups[i].name + "</option>");
            }
        }*/

        //显示教师基本信息
        $("#showteacherid").val(data2.data.teacher.number);
        $("#oneteacherid").val(data2.data.teacher.id);
        $("#showteachername").val(data2.data.teacher.name);
        $("#showteachersex").val(data2.data.teacher.sex);
        $("#showteacherposition").val(data2.data.teacher.position);
        $("#showteacherintro").val(data2.data.teacher.personIntroduction);


        var courseGroupSelectNameCount=0;

        //不要写teacherdetailtable,原因未知....
        $("#coursesdatailtable").dataTable({

            "data": data2.data.courses,
            "columns": [
                {"data": null},
                {"data": "number"},
                {"data": "name"},
                {"data": "type"},
                {"data": null},
                {"data": null}
            ],

            language: {
                "sProcessing": "处理中...",
                "sLengthMenu": "显示 _MENU_ 项结果",
                "sZeroRecords": "没有匹配结果",
                "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                "sInfoPostFix": "",
                "sSearch": "搜索:",
                "sUrl": "",
                "sEmptyTable": "表中数据为空",
                "sLoadingRecords": "载入中...",
                "sInfoThousands": ",",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "上页",
                    "sNext": "下页",
                    "sLast": "末页"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            columnDefs: [{
                targets: 0,
                render: function (data, type, row, meta) {

                    //渲染课程组下拉框

                    var allCourseGroupSelect = "<select class=\"form-control\" id=\"courseGroupSelect"+courseGroupSelectNameCount+"\">";

                    for (var i = data2.data.courseGroups.length - 1; i >= 0; i--) {

                        if (data2.data.courseGroups[i].id==row.courseGroup_id){
                            allCourseGroupSelect+="<option selected value='" + data2.data.courseGroups[i].id + "'>" + data2.data.courseGroups[i].name + "</option>";
                        }
                        else{
                            allCourseGroupSelect+="<option value='" + data2.data.courseGroups[i].id + "'>" + data2.data.courseGroups[i].name + "</option>";
                        }

                    }

                    allCourseGroupSelect+="</select>"
                    courseGroupSelectNameCount++;

                    return allCourseGroupSelect;

                }
            },{
                targets: 4,
                render: function (data, type, row, meta) {
                    return "<a type='button' class='btn btn-primary"

                        + "' onclick='getCourseById(\""
                        + data.id
                        + "\")' data-toggle='modal' data-target='#myModal' href='#'>详情</a>"


                }
            },
                {
                    targets: 5,
                    render: function (data, type, row, meta) {

                       /*alert(data.id);*/
                        return "<a type='button' class='btn btn-danger"

                            + "' onclick='delcourse(\""
                            + data.id
                            + "\")' href='#'>删除</a>"


                    }

                }]

        });
    }



}

//查询课程详情
function getCourseById(course_id) {

    $.ajax({
        url: "/queryCourseById",
        type: 'POST',
        data: JSON.stringify({
                "id": course_id
            }
        ),
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        timeout: 5000,
        cache: false,
        beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedFunction
    });

    function LoadFunction() {
    }

    function errorFunction() {
        alert("请求失败！")
    }

    function succeedFunction(data) {
        //模态框的请求数据方法

            $("#cid").val(data.data.course.id),
            $("#cadminname").val(data.data.manager.account),
            $("#cadminpass").val(data.data.manager.password),
            $("#cnumber").val(data.data.course.number),
            $("#cname").val(data.data.course.name),
            $("#ctype").val(data.data.course.type),
            $("#cintro").val(data.data.course.introduction),
            $("#ccredit").val(data.data.course.credit),
            $("#ctime").val(data.data.course.hours)


    }

}

//删除课程
function delcourse(id) {

    alert("该课程可能含有重要信息，一旦删除不可恢复，请谨慎操作！！！")

    // console.log(id)
    $.ajax({
        url: "/deleteCourseById",
        data: JSON.stringify({
            "id": id
        }),
        traditional: false,
        type: 'post',
        cache: false,
        async: false,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {
            if (data.success === true) {
                alert(data.message);
                window.location.reload();
            } else {
                alert(data.message);
            }
        },


        error: function () {
            alert("删除失败，请稍后重试！")
        }
    })
}

//更新教师
function updateteacher(){

    $.ajax({
        url: "/updateTeacher",
        type: 'POST',
        data: JSON.stringify({
                "id": $("#oneteacherid").val(),
                "courseGroup_id": $("#showteachergroup").val(),
                "name": $("#showteachername").val(),
                "number": $("#showteacherid").val(),
                "sex": $("#showteachersex").val(),
                "position": $("#showteacherposition").val(),
                "personIntroduction": $("#showteacherintro").val()
            }
        ),
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        timeout: 1000,
        cache: false,
        beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedFunction
    });

    function LoadFunction() {
    }

    function errorFunction() {
        alert("请求失败！");
    }

    function succeedFunction(data) {
       alert(data.message);
    }

}

//更新课程
function updateCourse() {

    $.ajax({
        url: "/updateCourse",
        data: JSON.stringify({
            "id": $("#cid").val(),
            "manager_account": $("#cadminname").val(),
            "manager_password": $("#cadminpass").val(),
            "number": $("#cnumber").val(),
            "name": $("#cname").val(),
            "type": $("#ctype").val(),
            "introduction": $("#cintro").val(),
            "credit": $("#ccredit").val(),
            "hours": $("#ctime").val()
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
        /*        window.location.reload();*/
    }
}