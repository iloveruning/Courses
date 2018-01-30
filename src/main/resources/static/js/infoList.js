var E = window.wangEditor;
var editor = new E('#infoListContent');
$(document).ready(function () {

    // 或者 var editor = new E( document.getElementById('editor') )

    $.ajax({
        url: '/notice/list/1',
        type: 'get',
        dataType: 'json',
        timeout: 5000,
        cache: false,
        contentType: 'application/json; charset=UTF-8'

    })
        .done(function (data) {

            console.log("success");
            console.log(data);
            $("#infoListDetails").dataTable({
                "data": data.data.list,
                "columns": [
                    {"data": "id"},
                    {"data": "title"},
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
                        /*console.log(data)*/
                        return "<a type='button' class='btn btn-primary"

                            +
                            "'  data-toggle='modal' data-target='#infomodal' onclick='showInfodetails(\"" +row.id+
                            "\")' href='#'>详情</a>"
                    }

                }, {
                    targets: 3,
                    render: function (data, type, row, meta) {

                        return "<a type='button' class='btn btn-danger"

                            +
                            "' onclick='delInfo(\""

                            + row.id +
                            "\")' href='#'>删除</a>"


                    }

                }]
            })

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {

            console.log("complete");
        });

});

function showInfodetails(infoid) {

    $.ajax({
        url: '/notice/'+infoid,
        type: 'get',
        dataType: 'json',
        timeout: 5000,

        cache: false,
        contentType: 'application/json; charset=UTF-8'
    })
        .done(function (data) {
            console.log(data);
            // console.log(data.data.infoTitle);
            $("#infomyid").val(infoid);
            $("#infoTitle").val(data.data.title);
            editor.create();
            editor.txt.html(data.data.content);
            console.log("success");
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

function changeInformation() {
    $.ajax({
        url: '/notice',
        type: 'put',
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false,
        data: JSON.stringify({
            "id":$("#infomyid").val(),
            "title": $("#infoTitle").val(),
            "content": editor.txt.html()
        })
    })
        .done(function (data) {
            console.log("success")
            alert(data.message);
            window.location.reload();
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });

}

function delInfo(id) {

    $.ajax({
        url: '/notice/'+id,
        type: 'DELETE',
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false

    })
        .done(function (data) {
            if (data.success === true) {
                alert(data.message);
                window.location.reload();
            } else {
                alert(data.message);
            }

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}
