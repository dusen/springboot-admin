
//excute when page loading completed.
$(document).ready(function() {	
	//get error message from common base form.
	PopUpUtil.constants.ERROR_OBJECT_IN_FORM =  
		ComponentUtil.util.parseToJsonObject($('#detailErrorObject').val());
	
	if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) {
		var src = $('#iPad_stylesheet_src').val();
		$('head').append('<link rel="stylesheet" href="'+ src +'">');
	}
	PopUpUtil.constants.CONFIRM_INTERRUPTING_MSG = $('#common_message_progressing').val();
});