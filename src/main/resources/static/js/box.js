/*box.js*/
    //textarea高度自适应
    $(function() {
        $('.content').flexText();
    });

    // textarea限制字数
    function keyUP(t) {
        var len = $(t).val().length;
        if (len > 139) {
            $(t).val($(t).val().substring(0, 140));
        }
    }