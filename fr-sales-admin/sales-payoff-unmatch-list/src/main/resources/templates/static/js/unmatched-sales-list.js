function searchButtonClick(obj) {
    PopUpUtil.ajaxHtml.post($(obj).attr("id"),
			'/unmatched-sales-list/list', '#divTableBody');
}

function linkButtonClick(button) {
	$("#indexValue").val(button.text);
	PopUpUtil.util.doTransition('numList','/unmatched-sales-list/presslink')
}

function auditButtonClick(obj) {
	PopUpUtil.ajaxHtml.post($(obj).attr("id"),
			'/unmatched-sales-list/audit', '#divTableBody');
}

var tableBodyLastIndexEvent = function() {
	$(window).trigger('resize');
}

$(document).ready(function() {
	$("#clearButton").click(function() {
		$('input[type=text]').each(function() {
			$(this).val('');
		});
		$('input[type=hidden]').each(function() {
			$(this).val('');
		});
		$('#main li').removeClass("selected");
	});
	
    // flexible table.
    if ($(".th-fixed").length > 0) {
        $(".th-fixed").each(function() {
            var $wrap = $(this);
            var fixedHeight;

            $(window).on("resize", function(){
                if($wrap.parents().hasClass("modal")) {
                    fixedHeight = $(window).height()* 0.4;
                } else {
                    var optionsHeight = 0;
                    $(".option-block").each(function(){
                        optionsHeight = optionsHeight + $(this).height();
                    });
                    fixedHeight = $(".header").height() + $("#breadcrumbs").height() + optionsHeight +  $(".footer").height() + 116;
                }
                var h = $(window).height() - $(".header").height() -fixedHeight;

                $wrap.find(".v-hheight-list").height(h);
            });
            $(window).resize();

            $wrap.find(".scroll-block").on("scroll", function(){
                var scrollLeft = $(this).scrollLeft();
                $wrap.find(".th-fixed-head table").css({"margin-left": -scrollLeft});
            });
        });
    }
    if ($(".scroll-block").length > 0) {
        ComponentUtil.window.scrollBlockSetting();
        
        $(window).on("resize", function(){
            ComponentUtil.window.scrollBlockSetting();
        });
    }
});