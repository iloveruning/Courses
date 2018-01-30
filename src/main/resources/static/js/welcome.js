$(document).ready(function () {

    console.log(document.cookie);

    var course_id = "";
    var cooks = document.cookie.split(";");
    for (var i = 0; i < cooks.length; i++) {
        var index = cooks[i].indexOf("course_id");
        if (index > -1) {
            course_id = cooks[i].split("=")[1];
        }
    }
    /*console.log(course_id);*/

    'use strict';
    $.ajax({
        // url: 'https://www.easy-mock.com/mock/5a50c209aaeb8a0aa380acba/courseadmin/queryCourseById',
        url: '/queryCourseById',
        type: 'post',
        data: JSON.stringify({
            id: course_id
        }),
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            /*console.log($("#courid").val());
            console.log("success");
            console.log(data);*/
            // console.log(data.data.CourseName);
            var temp = "";
            for (var i = data.data.teachers.length - 1; i >= 0; i--) {
                temp += data.data.teachers[i].name;
            }
            /*console.log(temp);
            console.log(data.data.teachers[0].name)*/
            $("#CourseTeacher").val(temp);
            $("#inCourseGroup").val(data.data.courseGroup.name);
            $("#CourseNumber").val(data.data.course.number);
            $("#CourseName").val(data.data.course.name);
            $("#CourseType").val(data.data.course.type);
            $("#CourseScore").val(data.data.course.credit);
            // $("#CourseTeacher").val(data.data.teacher);
            $("#CourseTime").val(data.data.course.hours);
            $("#CourseDetails").val(data.data.course.introduction)

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });


});

function updateWelcomeCourse() {

    $.ajax({
        url: '/queryCourseById',
        type: 'post',
        data: JSON.stringify({
            id: "17bfbbcd244b4f5b9a15df2b43ce87b9",
            name: $("#CourseName").val(),
            number: $("#CourseNumber").val(),
            type: $("#CourseType").val(),
            credit: $("#CourseScore").val(),
            hours: $("#CourseTime").val(),
            introduction: $("#CourseDetails").val()
        }),
        dataType: 'json',
        timeout: 5000,
        contentType: 'application/json; charset=UTF-8',
        cache: false
    })
        .done(function (data) {
            /*console.log($("#courid").val());*/
           $(".onupdate").show().delay(1000).hide(300);
           setTimeout(function () { $(".update-success").show('slow', function () {

            }); }, 1300);


            $("#disappare").show().delay(3000).hide(500);

            // console.log("success");
            // console.log(data);
            // // console.log(data.data.CourseName);
            // $("#inCourseGroup").val(data.data.courseGroup.name);
            // $("#CourseNumber").val(data.data.course.number);
            // $("#CourseName").val(data.data.course.name);
            // $("#CourseType").val(data.data.course.type);
            // $("#CourseScore").val(data.data.course.credit);
            // $("#CourseTeacher").val(data.data.teacher);
            // $("#CourseTime").val(data.data.course.hours);
            // $("#CourseDetails").val(data.data.course.introduction)

        })
        .fail(function () {
            console.log("error");
        })
        .always(function () {
            console.log("complete");
        });
    var $btn = $("#updateButton").button('loading');
    // business logic...
    $btn.button('reset')
    // $("#welcomeBody").append("<div class=\"alert alert-warning alert-dismissible\" role=\"alert\">\n" +
    //     "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>\n" +
    //     "  <strong>正在更新中</strong>\n" +
    //     "</div>");

}
