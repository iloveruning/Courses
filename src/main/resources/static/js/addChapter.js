var editor;
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
    'use strict';
    $('[data-toggle="popover"]').popover()

    var E = window.wangEditor;
    editor = new E('#mywangeditor');
    // 或者 var editor = new E( docum
    // ent.getElementById('editor') )
    editor.create();
    ;
    modalEditor = new E('#showEditor');
    modalEditor.create();

});

/**
 * 点击添加按钮产生的事件
 */

function addChapterTo() {

    if ($("#selectTitle").val() == "") {
        alert("请输入本章标题!");
    }
    /**
     * 发送数据numbr,name
     */
    else {
        $.ajax({
            url: '/addChapter',
            type: 'post',
            dataType: 'json',
            timeout: 5000,
            contentType: 'application/json; charset=UTF-8',
            cache: false,
            data: JSON.stringify({
                "number": $("#selectChapterNumber").val(),
                "name": $("#selectTitle").val(),
                "course_id": course_id
            })
        })
        /**
         * 点击按钮隐藏添加章节,显示章节详情
         */
            .done(function (data) {

                // todo 加上弹出的东西
                // alert("选择的章节是" + $("option selected").val() + "\n" + "标题是:  " + $("#selectTitle").val());
                $("#showchapter").show('slow', function () {

                });
                $("#submitchapter").hide('slow', function () {

                });
                // console.error($("option selected").val());
                // console.log("postsuccess");
                // console.log(data)
                // console.log(data.data.name);
                $("#selectShownChapterNumber").val(data.data.number);
                $("#totalId").val(data.data.id);
                $("#selectShownChapterTitle").val(data.data.name);


            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });
    }

}

function clearforms() {
    /**
     * 清空列表
     */
    $("input").val("");

}

//更新本章基本信息
function updateShownChapter() {
    $.ajax({
        url: 'http://localhost:8080/updateChapter',
        type: 'post',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        data: JSON.stringify({
            "number": $("#selectShownChapterNumber").val(),
            "name": $("#selectShownChapterTitle").val(),
            "id": $("#totalId").val() //$.session.get('key');
        })
    })
        .done(function (data) {
            //todo 炫酷更新成功效果
            alert(data.message)
            /*console.log("success");*/
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}


//todo 添加教学单元
function addSerialNumber() {

    clearUnit();//清空先前单元的全部数据
    if ($("#inputChapterName").val() == "") {
        alert("请输入本课名称!");
    } else {
        $("#hidecourses").show('slow', function () {
        });
        $.ajax({
            url: 'http://localhost:8080/addUnit',
            type: 'post',
            dataType: 'json',
            data: JSON.stringify({
                "number": $("#selectChapterSerialNumber").val(),
                "name": $("#inputChapterName").val(),
                "chapter_id": $("#totalId").val()
            }),
            timeout: 5000,
            contentType: 'application/json; charset=UTF-8',
            cache: false
        })
            .done(function (data) {

                //将刚才填写的信息显示在下方教学单元详情
                $("#selectupdateSerial").val(data.data.number);
                $("#inputUpdateSerial").val(data.data.name);
                $("#unitIdInput").val(data.data.id);
                /*console.log("success");*/

                //清空刚才所填的数值
                $("#selectChapterSerialNumber").val("");
                $("#inputChapterName").val("");

                //将添加的教学单元在上方列表显示
                refreshDataTable();


            })
            .fail(function () {
                console.log("error");
            })
            .always(function () {
                console.log("complete");
            });

    }
}


//教学单元列表的展示
var refreshDataTable = function () {

    $('#unitListTable').dataTable().fnClearTable();    //清空数据
    $('#unitListTable').dataTable().fnDestroy();         //销毁datatable

    $('#unitListTable').DataTable({

        "sPaginationType": "full_numbers",
        "bPaginite": true,
        "bInfo": true,
        "bSort": true,
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "/getUnitsByChapter",//这个是请求的地址
        "fnServerData": retrieveData,
        "searching": false,
        "lengthChange": false,
        "pageLength": 5,

        "columns": [
            {"data": "number"},//序号
            {"data": "name"},//名称
            {"data": "content"},//内容简介
            {"data": null},//详情
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


                return "<a type='button' class='btn btn-primary"
                    + "' onclick='queryUnitById(\""
                    + data.id
                    + "\")'  href='#' data-toggle=\"modal\" data-target=\"#unitModal\">详情</a>"

            }
        }, {
            targets: 4,
            render: function (data, type, row, meta) {

                //渲染删除课程按钮

                return "<a type='button' class='btn btn-danger"
                    + "' onclick='deleteUnit(\""
                    + data.id
                    + "\")' href='#'>删除</a>"
            }
        }]


    });
}

//利用ajax获取教学单元列表的数据
function retrieveData(url, aoData, fnCallback) {

    $.ajax({

        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: url,//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "chapter_id": $("#totalId").val(),
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


//删除教学单元
function deleteUnit(id) {
    $.ajax({
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: "/deleteUnit",
        data: JSON.stringify({
            "id": id,
            "chapter_id": $("#totalId").val()
        }),
        type: 'POST',
        dataType: 'json',
        async: false,
        success: function (data) {

            alert(data.message);
            refreshDataTable();

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {

            alert("您的请求已经gg");

        }
    });
}

//------------------------------------!!!!!模态框操作---------------------------------------------------------------------
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
            refreshModalFileDataTable();

            //显示教学视频列表（模态框）
            refreshModalVideoDataTable();

            //显示已有知识点
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
var refreshModalFileDataTable = function () {

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
                    + "' onclick='updateModalFileDescription(\""
                    + data.id
                    + "\")' href='#'>更新</a>"
            }
        }, {
            targets: 5,
            render: function (data, type, row, meta) {

                //渲染删除课程按钮
                return "<a type='button' class='btn btn-danger"
                    + "' onclick='deleteModalFileById(\""
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
            //刷新文件列表
            refreshOfficeDataTable();

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//模态框中更新资料的描述方法
function updateModalFileDescription() {

    console.log("正在更新资料描述");
    console.log(id);

}

//模态框中删除教学资料列表中的内容
function deleteModalFileById(id) {
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
            refreshModalFileDataTable();
            //刷新文件列表
            refreshOfficeDataTable();
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
            refreshDataTable();
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

            if ($("#unitIdInput").val() == $("#uid").val()) {
                $("#updatezhishidianList").append('<span href="#" id="' + data.data.id + '" class="btn btn-default">' + data.data.content + '<a href="#" class="btn btn-link" onclick=releaseModalRelation("' + data.data.id + '")>删除</a></span>');
            }

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

            //---------------------------------------------
            if ($("#unitIdInput").val() == $("#uid").val()) {
                $("#updatezhishidianList").find("span").each(function () {
                    if (this.getAttribute("id") == id) {
                        this.remove();
                        return 0;
                    }
                })
            }
            ;

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}


//模态框中教学单元详情中的资料获取
var refreshModalVideoDataTable = function () {

    $('#modalVideoList').dataTable().fnClearTable();    //清空数据
    $('#modalVideoList').dataTable().fnDestroy();         //销毁datatable

    $.ajax({
        url: "/getVideosByUnit",
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val()
        }),
        traditional: false,
        type: 'post',
        cache: false,
        async: false,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {

            $('#modalVideoList').DataTable({

                "data":data.data,

                "processing": false,
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
                        return "<a target='_blank' href='" + data.viewUrl + "'>播放</a>"
                    }
                }, {
                    targets: 4,
                    render: function (data, type, row, meta) {

                        //渲染删除课程按钮

                        return "<a type='button' class='btn btn-danger"
                            + "' onclick='updateModalVideoDescription(\""
                            + data.id
                            + "\")' href='#'>更新</a>"
                    }
                }, {
                    targets: 5,
                    render: function (data, type, row, meta) {

                        //渲染删除课程按钮
                        return "<a type='button' class='btn btn-danger"
                            + "' onclick='deleteModalVideoById(\""
                            + data.id
                            + "\")' href='#'>删除</a>"
                    }
                }]


            });

        },


        error: function () {
            alert("删除失败，请稍后重试！")
        }
    })
}

//显示模态框中的文件名称
function showModalVideoName() {
    var inputname = $("#modalVideoPreview").val();
    $("#modalVideoName").val(inputname);
}

//模态框上传资料
function uploadModalVideo() {
    console.log("文件正在上传");

    var desc = $("#modalVideoDescription").val();
    var formData = new FormData();
    formData.append("desc", desc);
    formData.append("file", $("#modalVideoPreview")[0].files[0]);

    $.ajax({
        url: "/file/video",
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
            $("#uploadModalVideos").append("<div><a id='" + data.data.id + "'>" + data.data.name + "--" + data.data.description + "</a><button onclick=deleteModalUploadVideo(\'" + data.data.id + "\')>删除</button></div>");

            //将刚才的文件框中的内容清空
            /*$("#textName").val("");*/

        },
        error: function () {
            console.log("error");
        }
    });
}

//模态框中删除上传文件（此时还未与教学单元绑定）
function deleteModalUploadVideo(id) {
    $.ajax({
        url: '/deleteVideoById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "video_id": id
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
function addModalVideosToUnit() {

    var video_ids = new Array();
    var count = 0;

    $("#uploadModalVideos").find("a").each(function () {
        video_ids[count] = this.getAttribute("id");
        count++;
    });

    $.ajax({
        url: '/bindUnit_videos',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#uid").val(),
            "video_ids": video_ids
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //清空模态框待提交列表
            $("#uploadModalVideos").empty();

            //刷新模态框文件列表
            refreshModalVideoDataTable();
            //刷新文件列表
            refreshDataVideoTable();

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//模态框中更新资料的描述方法
function updateModalVideoDescription() {

    console.log("正在更新资料描述");
    console.log(id);

}

//模态框中删除教学资料列表中的内容
function deleteModalVideoById(id) {
    $.ajax({
        url: '/deleteVideoById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#uid").val(),
            "video_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            //刷新模态框文件列表
            refreshModalVideoDataTable();
            //刷新文件列表
            refreshVideoDataTable();
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}



//------------------------------------!!!!!模态框操作结束---------------------------------------------------------------------

//更新教学单元
function updateSerialNumber() {

    /*console.log(editor.txt.html());*/

    $.ajax({
        url: '/updateUnit',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "number": $("#selectupdateSerial").val(),
            "name": $("#inputUpdateSerial").val(),
            "id": $("#unitIdInput").val(),
            "content": editor.txt.html()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            refreshDataTable();
        })
        .fail(function () {

            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//显示文件名称
function showFileName() {
    var inputname = $("#filepreview").val();
    $("#textName").val(inputname);
}

//上传教学资料
function uploadFile() {

    console.log("文件正在上传");

    var desc = $("#fileDescription").val();
    var formData = new FormData();
    formData.append("desc", desc);
    formData.append("file", $("#filepreview")[0].files[0]);

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
            $("#uploadFiles").append("<div><a id='" + data.data.id + "'>" + data.data.name + "--" + data.data.description + "</a><button onclick=deleteUploadFile(\'" + data.data.id + "\')>删除</button></div>");

            //将刚才的文件框中的内容清空
            /*$("#textName").val("");*/

        },
        error: function () {
            console.log("error");
        }
    });

}

//删除上传文件（此时还未与教学单元绑定）
function deleteUploadFile(id) {
    /*console.log("正在执行删除文件操作");
    console.log(id);*/
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

//绑定教学资料与教学单元
function addFilesToCourse() {

    var office_ids = new Array();
    var count = 0;

    $("#uploadFiles").find("a").each(function () {
        office_ids[count] = this.getAttribute("id");
        count++;
    });

    $.ajax({
        url: '/bindUnit_offices',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val(),
            "office_ids": office_ids
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //清空待提交列表
            $("#uploadFiles").empty();

            //刷新文件列表
            refreshOfficeDataTable();

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}


//教学资料列表的展示
var refreshOfficeDataTable = function () {

    $('#officeListTable').dataTable().fnClearTable();    //清空数据
    $('#officeListTable').dataTable().fnDestroy();         //销毁datatable

    $('#officeListTable').DataTable({

        "sPaginationType": "full_numbers",
        "bPaginite": true,
        "bInfo": true,
        "bSort": true,
        "processing": false,
        "serverSide": true,
        "sAjaxSource": "/getOfficesByUnit",//这个是请求的地址
        "fnServerData": retrieveOfficeData,
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
                    + "' onclick='updateOfficeDescription(\""
                    + data.id
                    + "\")' href='#'>更新</a>"
            }
        }, {
            targets: 5,
            render: function (data, type, row, meta) {

                //渲染删除课程按钮
                return "<a type='button' class='btn btn-danger"
                    + "' onclick='deleteOfficeById(\""
                    + data.id
                    + "\")' href='#'>删除</a>"
            }
        }]


    });
}

//利用ajax获取教学单元列表的数据
function retrieveOfficeData(url, aoData, fnCallback) {

    /*console.log($("#unitIdInput").val());*/
    $.ajax({

        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        url: url,//这个就是请求地址对应sAjaxSource
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val(),
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

//更新资料的描述方法
function updateOfficeDescription(id) {
    console.log("正在更新资料描述");
    console.log(id);
}

//删除教学资料列表中的内容
function deleteOfficeById(id) {

    $.ajax({
        url: '/deleteOfficeById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val(),
            "office_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            refreshOfficeDataTable();
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//增加知识点
function addZhishidian() {
    $.ajax({
        url: 'http://localhost:8080/addKnowledgePoint',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            "content": $("#addZhishidianInput").val()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            if (data.success == true) {

                alert(data.message);

                //清空知识点框
                $("#addZhishidianInput").val("");
                //渲染到本章已有知识点
                $("#zhishidianList").append('<span href="#" id="' + data.data.id + '" class="btn btn-default">' + data.data.content + '<a href="#" class="btn btn-link" onclick=deleteKnowledgePoint("' + data.data.id + '")>删除</a></span>');
                //渲染到本单元的知识点选择框
                $("#zhishidianSelect").append('<option value="' + data.data.id + '">' + data.data.content + '</option>');
                //渲染到模态框中的知识点选择框
                $("#modalKPSelect").append('<option value="' + data.data.id + '">' + data.data.content + '</option>');
            } else {
                alert(data.message);
            }


            /*console.log(data.data.content);
            console.log("success");*/
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//删除知识点
function deleteKnowledgePoint(id) {

    /*console.log("dsdfsfsdfsfs");
    console.log(id);*/
    $.ajax({
        url: 'http://localhost:8080/deleteKnowledgePoint',
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

            alert(data.message);

            //移除本章知识点部分的知识点
            $("#" + id + "").remove();

            //移除选择单元知识点的选择框的选项
            $("#zhishidianSelect option[value=" + id + "]").remove();

            //移除模态框中选择单元知识点的选择框的选项
            $("#modalKPSelect option[value=" + id + "]").remove();

            //移除当前单元可能已被选中的知识点的span标签
            $("#updatezhishidianList").find("span").each(function () {

                if (this.getAttribute("id") == id) {
                    this.remove();
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

//关联知识点和教学单元
function addzhishidianSelect() {
    $.ajax({
        url: 'http://localhost:8080/addUnit_knowledgePointRelation',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            id: $("#zhishidianSelect").val(),
            unit_id: $("#unitIdInput").val()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);


            $("#updatezhishidianInput").val("");
            $("#updatezhishidianList").append('<span href="#" id="' + data.data.id + '" class="btn btn-default">' + data.data.content + '<a href="#" class="btn btn-link" onclick=releaseRelation("' + data.data.id + '")>删除</a></span>');

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}

//释放知识点和教学单元的关联
function releaseRelation(id) {

    $.ajax({
        url: 'http://localhost:8080/releaseUnit_knowledgePointRelation',
        type: 'post',
        dataType: 'json',
        data: JSON.stringify({
            id: id,
            unit_id: $("#unitIdInput").val()
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //移除本单元该知识点
            $("#updatezhishidianList").find("span").each(function () {

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

//清空当前课时显示内容
function clearUnit() {

    $("#unitIdInput").val("");//当前教学单元的id
    $("#selectupdateSerial").val("");//当前教学单元的序号
    $("#inputUpdateSerial").val("");//当前教学单元的名称
    editor.txt.clear();//当前教学单元的内容

    //清空office列表
    $('#officeListTable').dataTable().fnClearTable();    //清空数据
    $('#officeListTable').dataTable().fnDestroy();         //销毁datatable
    $("#uploadFiles").empty();//清空待提交列表
    $("#filepreview").empty();//清空选择文件框


    $("#updatezhishidianList").empty();//清空已选择的当前单元知识点

    //清空video列表
    $('#videoListTable').dataTable().fnClearTable();    //清空数据
    $('#videoListTable').dataTable().fnDestroy();         //销毁datatable
    $("#uploadVideos").empty();//清空待提交列表
    $("#videoPreview").empty();//清空选择文件框

}

//显示视频名称
function showVideoName() {
    var inputname = $("#videoPreview").val();
    $("#videoName").val(inputname);
}

//上传教学视频
function uploadVideo() {

    console.log("视频正在上传");

    var desc = $("#videoDescription").val();
    var formData = new FormData();
    formData.append("desc", desc);
    formData.append("file", $("#videoPreview")[0].files[0]);

    $.ajax({
        url: "/file/video",
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
            $("#uploadVideos").append("<div><a id='" + data.data.id + "'>" + data.data.name + "--" + data.data.description + "</a><button onclick=deleteUploadVideo(\'" + data.data.id + "\')>删除</button></div>");

            //将刚才的文件框中的内容清空
            /*$("#textName").val("");*/

        },
        error: function () {
            console.log("error");
        }
    });

}

//删除上传文件（此时还未与教学单元绑定）
function deleteUploadVideo(id) {
    /*console.log("正在执行删除文件操作");
    console.log(id);*/
    $.ajax({
        url: '/deleteVideoById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "video_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
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

//绑定教学资料与教学单元
function addVideosToUnit() {

    var video_ids = new Array();
    var count = 0;

    $("#uploadVideos").find("a").each(function () {
        video_ids[count] = this.getAttribute("id");
        count++;
    });

    $.ajax({
        url: '/bindUnit_offices',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val(),
            "office_ids": office_ids
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {

            alert(data.message);

            //清空待提交列表
            $("#uploadVideos").empty();

            //刷新文件列表
            refreshVideoDataTable();

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

//教学资料列表的展示
var refreshVideoDataTable = function () {


    $('#videoListTable').dataTable().fnClearTable();    //清空数据
    $('#videoListTable').dataTable().fnDestroy();         //销毁datatable

    $.ajax({
        url: "/getVideosByUnit",
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val()
        }),
        traditional: false,
        type: 'post',
        cache: false,
        async: false,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function (data) {

            $('#videoListTable').DataTable({

                "data":data.data,

                "processing": false,
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
                            + "' onclick='updateVideoDescription(\""
                            + data.id
                            + "\")' href='#'>更新</a>"
                    }
                }, {
                    targets: 5,
                    render: function (data, type, row, meta) {

                        //渲染删除课程按钮
                        return "<a type='button' class='btn btn-danger"
                            + "' onclick='deleteVideoById(\""
                            + data.id
                            + "\")' href='#'>删除</a>"
                    }
                }]


            });

        },


        error: function () {
            alert("删除失败，请稍后重试！")
        }
    })

}

//更新资料的描述方法
function updateVideoDescription(id) {
    console.log("正在更新资料描述");
    console.log(id);
}

//删除教学资料列表中的内容
function deleteVideoById(id) {

    $.ajax({
        url: '/deleteVideoById',
        type: "post",
        dataType: 'json',
        data: JSON.stringify({
            "unit_id": $("#unitIdInput").val(),
            "video_id": id
        }),
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            alert(data.message);
            refreshVideoDataTable();
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}
