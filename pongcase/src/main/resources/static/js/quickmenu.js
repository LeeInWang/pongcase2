// 기본 위치(top)값
var floatPosition = parseInt($(".quickmenu").css('top'));

// scroll 인식
$(window).scroll(function() {

    // 현재 스크롤 위치
    var currentTop = $(window).scrollTop();
    var bannerTop = currentTop + floatPosition + "px";

    //이동 애니메이션
    $(".quickmenu").stop().animate({
        "top" : bannerTop
    }, 80);

});

