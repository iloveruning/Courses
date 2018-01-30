window.onload = function () {

    //获取所有的课程信息
    $.ajax({
        url: "/getAllCourses",
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

        //获取所有的教师和课程组信息
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
            alert("获取失败") // body...
        }
        function gettsucceedFunction(data2) {

            var nameCount=0;
            var courseGroupSelectNameCount=0;

            $('#courselisttable').DataTable({
                "data": data.data,
                "columns": [
                    {"data": null},//所处课程组
                    {"data": null},//更新课程组按钮
                    {"data": "course.number"},//课程编号
                    {"data": "course.name"},//课程名称
                    {"data": null},//负责教师
                    {"data": null},//更新教师按钮
                    {"data": null},//课程详情按钮
                    {"data": null} //课程删除按钮
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
                columnDefs: [{targets:0,
                    render: function (data, type, row, meta) {

                        //渲染课程组下拉框

                        var allCourseGroupSelect = "<select class=\"form-control\" id=\"courseGroupSelect"+courseGroupSelectNameCount+"\">";

                        for (var i = data2.data.courseGroups.length - 1; i >= 0; i--) {

                            if (data2.data.courseGroups[i].id==row.courseGroup.id){
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
                    targets:1,
                    render: function (data, type, row, meta) {

                        //渲染更新课程组按钮

                        var courseGroupSelectId=(courseGroupSelectNameCount-1);
                        var teacherSelectId=nameCount;

                        return "<a type='button' class='btn btn-primary"
                            + "' onclick='updateCourseGroupById(\"courseGroupSelect"
                            +courseGroupSelectId
                            + "\",\""+row.course.id+"\",\"teacherSelect"+teacherSelectId+"\")'  href='#'>更新</a>"
                    }
                }, {
                    targets: 4,
                    render: function (data, type, row, meta) {

                        //渲染教师多选下拉框

                        var allTeachersSelect="<select class=\"selectpicker show-menu-arrow form-control\" " +
                            "multiple=\"multiple\" id=\"teacherSelect"+nameCount+"\">"

                        for (var i = data2.data.teachers.length - 1; i >= 0; i--) {

                            allTeachersSelect+="<option value='" + data2.data.teachers[i].id + "'>" + data2.data.teachers[i].name + "</option>"

                        }

                        allTeachersSelect+="</select>"

                        nameCount++;
                        return allTeachersSelect;
                    }
                }, {
                    targets: 5,
                    render: function (data, type, row, meta) {

                        //渲染更新教师按钮

                        var courseGroupSelectId=(courseGroupSelectNameCount-1);
                        var teacherSelectId=(nameCount-1);

                        return "<a type='button' class='btn btn-primary"
                            + "' onclick='updateTeachersById(\"courseGroupSelect"
                            +courseGroupSelectId
                            + "\",\""+row.course.id+"\",\"teacherSelect"+teacherSelectId+"\")'  href='#'>更新</a>"
                    }
                }, {
                    targets: 6,
                    render: function (data, type, row, meta) {

                        //渲染课程详情按钮

                        return "<a type='button' class='btn btn-primary"
                            + "' onclick='getCourseById(\""
                            + data.course.id
                            + "\")' href='#' data-toggle=\"modal\" data-target=\"#myModal\">详情</a>"
                    }
                },{
                    targets: 7,
                    render: function (data, type, row, meta) {

                        //渲染删除课程按钮

                        return "<a type='button' class='btn btn-danger"
                            + "' onclick='deleteCourseById(\""
                            + data.course.id
                            + "\")' href='#'>删除</a>"
                    }
                }]

            });

            //对每一个多选下拉框进行渲染并赋初始值
            var item=0;

            $(".selectpicker").each(function(){

                var selectedTeachers=new Array();
                var count=0;

                for (var i = data2.data.teachers.length - 1; i >= 0; i--) {

                    if (item==data.data.length){
                        break;
                    }

                    for (var j=data.data[item].teachers.length-1;j>=0;j--){

                        if (data.data[item].teachers[j].id==data2.data.teachers[i].id){
                            selectedTeachers[count]=data.data[item].teachers[j].id;
                            count++;
                        }

                    }
                }

                item++;

                $(this).selectpicker("val",selectedTeachers);
                $(this).selectpicker("refresh");

            });

        }
    }
};

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
/*        window.location.reload();*/
    }
}

//删除课程
function deleteCourseById(id) {
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
    }

}

//更新课程组
function updateCourseGroupById(courseGroupSelectId,courseId,teacherSelectId) {

    $.ajax({
        url: "/updateRelation_courseGroup_course",
        type: 'POST',
        data: JSON.stringify({
                "id": courseId,
                "courseGroup_id":$("#"+courseGroupSelectId).val(),
                "teacher_ids":$("#"+teacherSelectId).val()
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

        alert(data.message);

    }

    /*console.log("courseGroupSelectId: "+courseGroupSelectId);
    console.log("courseId: "+courseId);
    console.log("teacherSelectId: "+$("#"+teacherSelectId).val());*/
}

//更新负责教师
function updateTeachersById(courseGroupSelectId,courseId,teacherSelectId){

    /*console.log("courseGroupSelectId: "+courseGroupSelectId);
    console.log("courseId: "+courseId);
    console.log("teacherSelectId: "+$("#"+teacherSelectId).val());*/

    $.ajax({
        url: "/updateRelation_teacher_course",
        type: 'POST',
        data: JSON.stringify({
                "id": courseId,
                "courseGroup_id":$("#"+courseGroupSelectId).val(),
                "teacher_ids":$("#"+teacherSelectId).val()
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

        alert(data.message);

    }

}