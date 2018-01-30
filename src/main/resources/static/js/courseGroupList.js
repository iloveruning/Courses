$(document).ready(function () {


    //获取全部课程组信息
    $.ajax({
        url: '/getAllCourseGroups',
        type: 'get',
        dataType: 'json',
        timeout: 1000,
        cache: false,
        beforeSend: LoadFunction,
        error: errorFunction,
        success: succeedFunction
    });

    function LoadFunction() {

    }

    function errorFunction() {
        alert("错误")
    }

    //datatables的ajax方法
    function succeedFunction(data) {
        $("#coursegrouptable").dataTable({
            "data": data.data,

            "columns": [
                {"data": "courseGroup.number"},
                {"data": "courseGroup.name"},
                {"data": "courseGroup.introduction"},
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
                targets: 3,
                render: function (data, type, row, meta) {
                    /*console.log(data)*/
                    return "<a type='button' class='btn btn-primary"

                        + "' onclick='showdetails(\""
                        + data.courseGroup.id
                        + "\")' href='#'>详情</a>"
                }

            }, {
                targets: 4,
                render: function (data, type, row, meta) {

                    return "<a type='button' class='btn btn-danger"

                        + "' onclick='delcgroup(\""
                        + data.courseGroup.id
                        + "\")' href='#'>删除</a>"


                }

            }]
        })

    }
});

//删除课程组
function delcgroup(id) {

    alert("该课程组可能含有重要信息，一旦删除不可恢复，请谨慎操作！！！")

    // console.log(id)
    $.ajax({
        url: "/deleteCourseGroup",
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

//获取课程组详情
function showdetails(courseGroup_id) {
    // 显示课程组信息
    showgroup();
    //显示课程及教师
    $.ajax({
        url: "/getCourse_Teacher",
        type: 'POST',
        data: JSON.stringify({
                "id": courseGroup_id
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
        $("#coursegroupdiv").html("<table id=\"coursetable\" class=\"table table-hover table-bordered\">\n" +
            "                                    <thead>\n" +
            "                                    <tr>\n" +
            "                                        <td>课程编号</td>\n" +
            "                                        <td>课程名称</td>\n" +
            "                                        <td>课程类型</td>\n" +
            "                                        <td>详情</td>\n" +
            "                                        <td>删除</td>\n" +
            "\n" +
            "                                    </tr>\n" +
            "                                    </thead>\n" +
            "                                    <tbody id=\"coursegroupdev\"></tbody>\n" +
            "\n" +
            "                                </table><table id=\"teachertable\" class=\"table table-hover table-bordered\">\n" +
            "                                    <thead>\n" +
            "                                    <tr>\n" +
            "                                        <td>教师编号</td>\n" +
            "                                        <td>教师姓名</td>\n" +
            "                                        <td>教师职务</td>\n" +
            "                                        <td>详情</td>\n" +
            "                                        <td>删除</td>\n" +
            "\n" +
            "                                    </tr>\n" +
            "                                    </thead>\n" +
            "                                    <tbody id=\"coursegroupdev\"></tbody>\n" +
            "\n" +
            "                                </table>");
    }


    function errorFunction() {
        alert("粗错了")
    }

    function succeedFunction(data) {

        //拼接json数据
        var st1 = JSON.stringify(data.data);

        var words1 = st1.split("],");

        var t1 = words1[0].split(":[");

        var coursesT = '{\"success\": true, \"message\": \"请求成功\", \"data\": [ ' + t1[1] + ']}';

        var json1 = JSON.parse(coursesT);//课程json
        //-------------------------------------------------------------------------------------------

        var t2 = words1[1].split(":[");

        var teacherT = '{\"success\": true, \"message\": \"请求成功\", \"data\": [ ' + t2[1] + ']}';

        var json2 = JSON.parse(teacherT);//教师json
        //-------------------------------------------------------------------------------------------

        var t3 = words1[2].replace("}}", "");
        var t32 = t3.split("Group\":");
        var courseGroupT = '{\"success\": true, \"message\": \"请求成功\", \"data\":' + t32[1] + '}}';

        var json3 = JSON.parse(courseGroupT);//课程组json
        //-------------------------------------------------------------------------------------------

        //显示课程组信息
        $("#showgroupid").val(json3.data.number);
        $("#showgroupname").val(json3.data.name);
        $("#showgroupintro").val(json3.data.introduction);
        $("#updategroupid").val(json3.data.id);


        //课程列表
        $("#coursetable").dataTable({
            "data": json1.data,
            "columns": [
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
                targets: 3,
                render: function (data, type, row, meta) {
                    /*console.log("hhhhhh" + data.id);*/
                    return "<a type='button' class='btn btn-primary"

                        + "' onclick='getCourseById(\""
                        + data.id
                        + "\")' data-toggle='modal' data-target='#myModal' href='#'>详情</a>"


                }
            },
                {
                    targets: 4,
                    render: function (data, type, row, meta) {

                        // alert(data.courseGroup_id);
                        /*console.log(data.id)*/
                        return "<a type='button' class='btn btn-danger"

                            + "' onclick='delcourse(\""
                            + data.id
                            + "\")' href='#'>删除</a>"


                    }

                }]

        });


        //教师列表
        $("#teachertable").dataTable({
            "data": json2.data,
            "columns": [
                {"data": "number"},
                {"data": "name"},
                {"data": "position"},
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
                targets: 3,
                render: function (data, type, row, meta) {
                    /*console.log("hhhhhh" + data.id);*/
                    return "<a type='button' class='btn btn-primary"

                        + "' onclick='getTeacherById(\""
                        + data.id
                        + "\")' data-toggle='modal' data-target='#myModal2' href='#'>详情</a>"


                }
            },
                {
                    targets: 4,
                    render: function (data, type, row, meta) {

                        // alert(data.courseGroup_id);
                        /*console.log(data.id)*/
                        return "<a type='button' class='btn btn-danger"

                            + "' onclick='delteacher(\""
                            + data.id
                            + "\")' href='#'>删除</a>"


                    }

                }]

        })
        ;


    }

}

//查看课程详情
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
        timeout: 1000,
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

        //渲染教师下拉多选框

        $("#cteacher").empty();

        for (var i = data.data.teachers.length - 1; i >= 0; i--) {

            $("#cteacher").append("<option value='" + data.data.teachers[i].id + "'>" + data.data.teachers[i].name + "</option>");

            $("#cteacher").selectpicker("refresh");
        }

        //给定初始值
        var initTeachers=new Array();
        var initCount=0;

        for (var i = data.data.teachers.length - 1; i >= 0; i--){
            initTeachers[initCount]=data.data.teachers[i].id;
            initCount++;
        }

        $("#cteacher").selectpicker("val",initTeachers);
        $("#cteacher").selectpicker("refresh");

    }

}

//删除课程
function delcourse(id) {

    alert("该课程组可能含有重要信息，一旦删除不可恢复，请谨慎操作！！！")

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

//更新课程
function updateCourse() {
    // validateisnull();
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
    }
}

//查看教师详情
function getTeacherById(teacher_id) {

    $.ajax({
        url: "/queryTeacherById",
        type: 'POST',
        data: JSON.stringify({
                "id": teacher_id
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
        alert("请求失败！")
    }

    function succeedFunction(data) {
        //模态框的请求数据方法

            $("#tid").val(data.data.teacher.id),
            $("#tnumber").val(data.data.teacher.number),
            $("#tname").val(data.data.teacher.name),
            $("#tsex").val(data.data.teacher.sex),
            $("#tposition").val(data.data.teacher.position),
            $("#tintro").val(data.data.teacher.personIntroduction)
    }

}

//删除教师
function delteacher(id) {

    alert("该课程组可能含有重要信息，一旦删除不可恢复，请谨慎操作！！！")

    // console.log(id)
    $.ajax({
        url: "/deleteTeacher",
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
function updateTeacher(){

    $.ajax({
        url: "/updateTeacher",
        type: 'POST',
        data: JSON.stringify({
                "id": $("#tid").val(),
                "name": $("#tname").val(),
                "number": $("#tnumber").val(),
                "sex": $("#tsex").val(),
                "position": $("#tposition").val(),
                "personIntroduction": $("#tintro").val()
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

//更新课程组
function updatecoursegroup() {
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

}

//页面切换
function showgroup() {
    $("#showgroupdisplay").css("display", "");
}