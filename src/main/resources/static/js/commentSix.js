/*commentSix.js*/
$(document).ready(function() {
    // 点击页码，上下页加载评论
    $('.fy').on('click', 'input', function() {
        // colorChange(this)
        // console.log($(this).html())
        // alert($(this).val())
        let id = $(this).val()
        $('.fy input').removeClass('active')
        $(this).addClass('active')
        link(id)

    })

    $('.fy .next').on('click', function() {
        // alert('aha')
        let id = parseInt($('.active').val()) + 1
        // alert(id)
        $('.fy input').removeClass('active')
        $('.fy .btn-id-' + id).addClass('active')
        link(id)

    })

    $('.fy .prev').on('click', function() {
        // alert('aha')
        let id = parseInt($('.active').val()) - 1
        // alert(id)
        $('.fy input').removeClass('active')
        $('.fy .btn-id-' + id).addClass('active')
        link(id)

    })

    // 加载第一页
    $('.fy .first-page').on('click', function() {
        // alert('aha')
        let id = 1
        // alert(id)
        $('.fy input').removeClass('active')
        $('.fy .btn-id-' + id).addClass('active')
        link(id)
    })


    // 点击加载最后一页
    $('.last-pages').on('click', function() {
        // alert('aha')
        let id = parseInt($('.active').val()) + 1
        // alert(id)
        $('.fy input').removeClass('active')
        $('.fy .btn-id-' + id).addClass('active')
        link(id)

    })
})