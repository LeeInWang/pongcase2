$(function() {
    $(window).scroll(function() {
        if ($(this).scrollTop() > 200) {
            $('#toTop').fadeIn();
            $('#toTop').css('left', $('#sidebar').offset().left);
        } else {
            $('#toTop').fadeOut();
        }
    });

    $("#toTop").click(function() {
        $('html, body').animate({
            scrollTop : 0
        }, 400);
        return false;
    });
});
