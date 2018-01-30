var modalEditor;

var course_id = "";
var cooks = document.cookie.split(";");
for (var i = 0; i < cooks.length; i++) {
    var index = cooks[i].indexOf("course_id");
    if (index > -1) {
        course_id = cooks[i].split("=")[1];
    }
}

$(document).ready(function () {

    showChapterList();

    var E = window.wangEditor;

    modalEditor = new E('#showEditor');
    modalEditor.create();

});

function showChapterList() {

    /*console.log("Dfgrtwbehyrnj");*/

    $("#chapterList").dataTable({

        "sPaginationType": "full_numbers",
        "bPaginite": true,
        "bInfo": true,
        "bSort": true,
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "/getChaptersByPage",//这个是请求的地址
        "fnServerData": retrieveChapters,
        "searching": false,
        /*"lengthChange": false,
        "pageLength": 5,*/

        "columns": [
            {"data": "number"},
            {"data": "name"},

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
            targets: 2,
            render: function (data, type, row, meta) {

                return "<a type='button' class='btn btn-primary"
                    +
                    "' data-toggle='modal' data-target='#chapterModal' onclick=queryChapterById(\'"
                    + data.id +
                    "\') href='#'>详情</a>"
            }

        }, {
            targets: 3,
            render: function (data, type, row, meta) {
                console.log("2." + data);
                return "<a type='button' class='btn btn-danger"

                    +
                    "' onclick='deleteChapterById(\"" +
                    row.id +

                    "\")' href='#'>删除</a>"


            }

        }]
    });
}

//利用ajax获取章列表的数据
function retrieveChapters(url, aoData, fnCallback) {

    console.log("章获取完毕")

    $.ajax({
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: url,//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "course_id": course_id,
            "iDisplayStart": aoData[3].value,
            "iDisplayLength": aoData[4].value
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

            fnCallback(data.data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert("您的请求已经gg");

        }
    });
}

//获取某章详情
function queryChapterById(id) {

    $.ajax({
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: "/queryChapterById",
        data: JSON.stringify({
            "id": id
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

            $("#chapterIdModal").val(data.data.id);
            $("#chapterNumberModal").val(data.data.number);
            $("#chapterNameModal").val(data.data.name);

            showUnitList();

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert("您的请求已经gg");

        }
    });

}
//更新模态框的章节序号
//todo 更新模态框的章节序号
function updatequeryChapterById() {

    $.ajax({
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: "/queryChapterById",
        data: JSON.stringify({
            "id":   $("#chapterIdModal").val(),
            "number": $("#chapterNumberModal").val(),
            "name":    $("#chapterNameModal").val()
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

         alert("更新成功 !")

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert("您的请求已经gg");

        }
    });
}

//显示某章的单元列表
function showUnitList() {

    $("#unitListTableModal").dataTable().fnClearTable();    //清空数据
    $("#unitListTableModal").dataTable().fnDestroy();         //销毁datatable

    $("#unitListTableModal").dataTable({

        "sPaginationType": "full_numbers",
        "bPaginite": true,
        "bInfo": true,
        "bSort": true,
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "/getUnitsByChapter",//这个是请求的地址
        "fnServerData": retrieveUnits,
        "searching": false,
        "lengthChange": false,
        "pageLength": 5,

        "columns": [
            {"data": "number"},
            {"data": "name"},
            {"data": "content"},
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

                return "<a type='button' class='btn btn-primary"
                    +
                    "' data-toggle='modal' data-target='#unitModal' onclick=queryUnitById(\'"
                    + data.id +
                    "\') href='#'>详情</a>"
            }

        }, {
            targets: 4,
            render: function (data, type, row, meta) {
                console.log("2." + data);
                return "<a type='button' class='btn btn-danger"

                    +
                    "' onclick='deleteUnitById(\""
                    +
                    data.id
                    +
                    "\")' href='#'>删除</a>"


            }

        }]
    });

}

//利用ajax获取教学单元列表的数据
function retrieveUnits(url, aoData, fnCallback) {

    $.ajax({

        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: url,//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "chapter_id": $("#chapterIdModal").val(),
            "iDisplayStart": aoData[3].value,
            "iDisplayLength": aoData[4].value
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

            fnCallback(data.data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert("您的请求已经gg");

        }
    });
}


//获取某一教学单元的详细信息
function queryUnitById(id) {
    $.ajax({
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: "/queryUnitById",
        data: JSON.stringify({
            "id": id
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

            console.log(data);
            //展示教学单元详情的操作
            $("#unumber").val(data.data.unit.number)//教学单元编号
            $("#uname").val(data.data.unit.name);//教学单元名称
            $("#uid").val(data.data.unit.id);//教学单元id
            modalEditor.txt.html(data.data.unit.content);//教学单元详情

            //显示教学资料列表（模态框）
            refreshModalDataTable();

            console.log($("#unitIdInput").val());
            console.log($("#uid").val());
            console.log(id);

            //显示已有知识点
            /*if ($("#unitIdInput").val()!=$("#uid").val()){
                $("#modalKPList").empty();
            }*/
            $("#modalKPList").empty();
            var kps = data.data.knowledgePoints;
            for (var i = kps.length - 1; i >= 0; i--) {
                $("#modalKPList").append('<span href="#" id="' + kps[i].id + '" class="btn btn-default">' + kps[i].content + '<a href="#" class="btn btn-link" onclick=releaseModalRelation("' + kps[i].id + '")>删除</a></span>');
            }


        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            /*alert("您的请求已经gg");*/

        }
    });
}

//模态框中教学单元详情中的资料获取
var refreshModalDataTable = function () {

    $('#modalFileList').dataTable().fnClearTable();    //清空数据
    $('#modalFileList').dataTable().fnDestroy();         //销毁datatable

    $('#modalFileList').DataTable({

        "sPaginationType": "full_numbers",
        "bPaginite": true,
        "bInfo": true,
        "bSort": true,
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "/getOfficesByUnit",//这个是请求的地址
        "fnServerData": retrieveModalData,
        "searching": false,
        "lengthChange": false,
        "pageLength": 5,
        "fnDrawCallback": function () {
            var api = this.api();
            var startIndex = api.context[0]._iDisplayStart;//获取到本页开始的条数
            api.column(0).nodes().each(function (cell, i) {
                cell.innerHTML = startIndex + i + 1;
            });
        },

        "columns": [
            {"data": null},//序号
            {"data": "description"},//中文描述
            {"data": "name"},//名称
            {"data": null},//演示
            {"data": null},//更新
            {"data": null}//删除
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
                return "<a target='_blank' href='" + data.viewUrl + "'>演示</a>"
            }
        }, {
            targets: 4,
            render: function (data, type, row, meta) {

                //渲染删除课程按钮

                return "<a type='button' class='btn btn-danger"
                    + "' onclick='updateModalDescription(\""
                    + data.id
                    + "\")' href='#'>更新</a>"
            }
        }, {
            targets: 5,
            render: function (data, type, row, meta) {

                //渲染删除课程按钮
                return "<a type='button' class='btn btn-danger"
                    + "' onclick='deleteModalOfficeById(\""
                    + data.id
                    + "\")' href='#'>删除</a>"
            }
        }]


    });
}

//模态框中利用ajax获取教学单元资料列表的数据
function retrieveModalData(url, aoData, fnCallback) {

    /*console.log($("#unitIdInput").val());*/
    $.ajax({

        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: url,//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "unit_id": $("#uid").val(),
            "iDisplayStart": aoData[3].value,
            "iDisplayLength": aoData[4].value
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {
            fnCallback(data.data);//把返回的数据传给这个方法就可以了,datatable会自动绑定数据的
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            console.log("你的请求已经gg");

        }
    });
}

//显示模态框中的文件名称
function showModalFileName() {
    var inputname = $("#modalFilePreview").val();
    $("#modalFileName").val(inputname);
}

//模态框上传资料
function uploadModalFile() {
    console.log("文件正在上传");

    var desc = $("#modalFileDescription").val();
    var formData = new FormData();
    formData.append("desc", desc);
    formData.append("file", $("#modalFilePreview")[0].files[0]);

    $.ajax({
        url: "/file/office",
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
            /*console.log(data);*/
            //将该文件显示在窗口右侧带提交列表
            $("#uploadModalFiles").append("<div><a id='" + data.data.id + "'>" + data.data.name + "--" + data.data.description + "</a><button onclick=deleteModalUploadFile(\'" + data.data.id + "\')>删除</button></div>");

            //将刚才的文件框中的内容清空
            /*$("#textName").val("");*/

        },
        error: function () {
            console.log("error");
        }
    });
}

//模态框中删除上传文件（此时还未与教学单元绑定）
function deleteModalUploadFile(id) {
    $.ajax({
        url: '/deleteOfficeById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "office_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            /*$("#uploadModalFiles").find("a").each(function () {
                if (this.getAttribute("id")==id){
                    console.log(this);
                    this.parent().hide();
                    alert(data.message);
                    return 0;
                }
            });*/
            $("#" + id).parent().remove();
            alert(data.message);

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}

//模态框中绑定教学资料与教学单元
function addModalFilesToCourse() {

    var office_ids = new Array();
    var count = 0;

    $("#uploadModalFiles").find("a").each(function () {
        office_ids[count] = this.getAttribute("id");
        count++;
    });

    $.ajax({
        url: '/bindUnit_offices',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#uid").val(),
            "office_ids": office_ids
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //清空模态框待提交列表
            $("#uploadModalFiles").empty();

            //刷新模态框文件列表
            refreshModalDataTable();


        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//模态框中更新资料的描述方法
function updateModalDescription() {

    console.log("正在更新资料描述");
    console.log(id);

}

//模态框中删除教学资料列表中的内容
function deleteModalOfficeById(id) {
    $.ajax({
        url: '/deleteOfficeById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#uid").val(),
            "office_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            //刷新模态框文件列表
            refreshModalDataTable();

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}

//更新模态框中教学单元基本信息
function updateModalUnit() {

    $.ajax({
        url: '/updateUnit',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "number": $("#unumber").val(),
            "name": $("#uname").val(),
            "id": $("#uid").val(),
            "content": modalEditor.txt.html()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            //更新教学单元列表
        })
        .fail(function () {

            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//模态框关联知识点和教学单元v
function addModalKP() {

    $.ajax({
        url: 'http://localhost:8080/addUnit_knowledgePointRelation',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            id: $("#modalKPSelect").val(),
            unit_id: $("#uid").val()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            $("#modalKPSelect").val("");
            $("#modalKPList").append('<span href="#" id="' + data.data.id + '" class="btn btn-default">' + data.data.content + '<a href="#" class="btn btn-link" onclick=releaseModalRelation("' + data.data.id + '")>删除</a></span>');

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//释放知识点和教学单元的关联
function releaseModalRelation(id) {

    $.ajax({
        url: 'http://localhost:8080/releaseUnit_knowledgePointRelation',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            id: id,
            unit_id: $("#uid").val()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //移除本单元该知识点
            $("#modalKPList").find("span").each(function () {

                if (this.getAttribute("id") == id) {
                    this.remove();
                    return 0;
                }

            });

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

function deleteChapterById(id) {
    $.ajax({
        url: '/deleteChapter',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({

            "id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert("删除成功!");
            window.location.reload();
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}
