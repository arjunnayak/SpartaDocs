var left_side_width = 220; //Sidebar width in pixels

$(function() {
    "use strict";


    $("[data-toggle='offcanvas']").click(function(e) {
        e.preventDefault();


        if ($(window).width() <= 992) {
            $('.row-offcanvas').toggleClass('active');
            $('.left-side').removeClass("collapse-left");
            $(".right-side").removeClass("strech");
            $('.row-offcanvas').toggleClass("relative");
        } else {

            $('.left-side').toggleClass("collapse-left");
            $(".right-side").toggleClass("strech");
        }
    });


    $('.btn').bind('touchstart', function() {
        $(this).addClass('hover');
    }).bind('touchend', function() {
        $(this).removeClass('hover');
    });




    $("[data-widget='collapse']").click(function() {

        var box = $(this).parents(".box").first();

        var bf = box.find(".box-body, .box-footer");
        if (!box.hasClass("collapsed-box")) {
            box.addClass("collapsed-box");

            $(this).children(".fa-minus").removeClass("fa-minus").addClass("fa-plus");
            bf.slideUp();
        } else {
            box.removeClass("collapsed-box");

            $(this).children(".fa-plus").removeClass("fa-plus").addClass("fa-minus");
            bf.slideDown();
        }
    });





    $('.btn-group[data-toggle="btn-toggle"]').each(function() {
        var group = $(this);
        $(this).find(".btn").click(function(e) {
            group.find(".btn.active").removeClass("active");
            $(this).addClass("active");
            e.preventDefault();
        });

    });

    $("[data-widget='remove']").click(function() {
        //Find the box parent
        var box = $(this).parents(".box").first();
        box.slideUp();
    });


    function _fix() {

        var height = $(window).height() - $("body > .header").height() - ($("body > .footer").outerHeight() || 0);
        $(".wrapper").css("min-height", height + "px");
        var content = $(".wrapper").height();

        if (content > height)

            $(".left-side, html, body").css("min-height", content + "px");
        else {

            $(".left-side, html, body").css("min-height", height + "px");
        }
    }

    _fix();

    $(".wrapper").resize(function() {
        _fix();
        fix_sidebar();
    });

    fix_sidebar();


});
function fix_sidebar() {

    if (!$("body").hasClass("fixed")) {
        return;
    }



}
