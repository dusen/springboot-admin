$(document).ready(function(){
  
	var div_id;
	if (!ComponentUtil.browser.isiPad()) $(".numeric-keypad").prop("readonly", false);
	/*
	 * フォームをクリックしたとき
	 */
	$(document).on('click','.numeric-keypad',function(){
		if (ComponentUtil.browser.isiPad()) {
			var w = $( this ).width() / 2;
			var offset = $( this ).offset();
			var top = 0;
			if ($(window).height() - Math.ceil(offset.top) > 236) {
				top  = Math.ceil(offset.top)+33;
			} else {
				top  = Math.ceil(offset.top)-238;
			}
			var left = Math.ceil(offset.left) + w;
			$(".numeric-keypad").removeClass("focus")
			$(".numeric-block").remove();
			$(this).addClass("focus").blur();

			//数値の配列
			arr_numeric = ['0','1','2','3','4','5','6','7','8','9'];

			var html = '';
			html +='  <div class="numeric-block">';
			html +='    <ul>';
			for(var i=0; i<arr_numeric.length; i++){
				html +='      <li id="numerickey'+arr_numeric[i]+'" class="numerickey" unselectable="on">'+arr_numeric[i]+'</li>';
			}
			html +='    <li unselectable="on" id="numkeyclear" class="numerickey_button">Clear</li>';
			html +='    <li unselectable="on" id="numkeyclose" class="numerickey_button">GO</li>';
			html +='    </ul>';
			html +='  </div>';

			$("body").append(html);
			$(".numeric-block").css({"top":top,"left":left});
			div_id= "#"+$(this).attr("id");
		}
	});

	//数字を押したとき
	$(document).on('click','.numerickey',function(){

		var id= $(this).attr("id");
		id = id.match(/numerickey(\d+)/)[1];
		var val = $(div_id).val();
		$(div_id).val(val+id);
		if ((val+id) != "") {
			$(div_id).parent(".numeric-keypad-wrap").addClass("input");
		} else {
			$(div_id).parent(".numeric-keypad-wrap").removeClass("input");
		}
		$(div_id).change();
	});

	//閉じる・クリアボタンを押したとき
	$(document).on('click','.numerickey_button',function(e){
		var id= $(this).attr("id");
		if(id === 'numkeyclear'){
			$(div_id).val('');
			$(div_id).parent(".numeric-keypad-wrap").removeClass("input");
			$(div_id).change();
		}else if(id === 'numkeyclose'){
			$(div_id).removeClass("focus");
			$(".numeric-block").remove();
		}
		e.preventDefault();
	});
  
	$(document).on('click','.numeric-keypad-wrap .input-clear',function(e){
		$(this).parent(".numeric-keypad-wrap").find("input").val('');
		$(this).parent(".numeric-keypad-wrap").removeClass("input");
		$(this).parent(".numeric-keypad-wrap").find("input").removeClass("focus").change();
		$(".numeric-block").remove();
		e.preventDefault();
	});

	$(document).on('click', 'input', function(){
		if (div_id) {
			var _div_id = div_id.replace("#", "");
			if($(this).attr("id") != _div_id) {
				$(div_id).removeClass("focus");
				$(".numeric-block").remove();
			}
		}
	});
});
