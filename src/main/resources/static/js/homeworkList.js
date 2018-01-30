$(document).ready(function () {
    $.ajax({
        url: '/homework/1',
        type: 'get',
        dataType: 'json',
        timeout: 5000,
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
    function succeedFunction(data1) {
        console.log(data1.data);
        // console.log(data.data.homework[0].strName)
        $("#homeworkListDetails").dataTable({
            "data": data1.data.list,

            "columns": [
                {"data": "id"},
                {"data": "name"},
                {"data": "file"},
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
                    console.log(data)
                    return "<a type='button' class='btn btn-primary"

                        + "'   href='http://3y5z5q.natappfree.cc/file"+data.fileUrl+"' target='_blank'>详情</a>"
                }

            }, {
                targets: 4,
                render: function (data, type, row, meta) {

                    return "<a type='button' class='btn btn-danger"

                        + "' onclick='delHomework("+row.id+")'   href='#'>删除</a>"


                }

            }]
        })

    }

});
//
// function showHomeworkDetails(id) {
//
//     $.ajax({
//         url: 'http://3y5z5q.natappfree.cc/homework/1',
//         type: 'get',
//         data:JSON.stringify({
//             "id":id
//         }),
//         dataType: 'json',
//         timeout: 5000,
//         cache: false,
//         contentType: 'application/json; charset=UTF-8'
//     })
//         .done(function (data) {
//            $("#stname").val(data.data.name);
//             $("#homeworkname").val(data.data.officeId);
//         })
//         .fail(function () {
//             console.log("error");
//         })
//         .always(function () {
//             console.log("complete");
//         });
// }

function delHomework(id) {

    $.ajax({
        url: '/homework/'+id,
        type: 'post',
        dataType: 'json',
        data:JSON.stringify({
            "id":id
        }),
        timeout: 5000,
        cache: false,
        contentType: 'application/json; charset=UTF-8'
    })
        .done(function (data) {
            if (data.success === true) {
                alert(data.message);
                window.location.reload();
            } else {
                alert(data.message);
            }


            console.log("success");
        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
}
