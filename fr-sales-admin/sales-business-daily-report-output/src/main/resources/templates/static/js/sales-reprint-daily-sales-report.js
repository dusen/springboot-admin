$(document).ready(function() {
	$("#clearButton").click(function() {
		$('input[type=text]').each(function() {
			$(this).val('');
		});
		$('#main li').removeClass("selected");
	});
});

function searchButtonClick(obj) {
	var storeCode = $('#loginStoreCode').val();
	var storeFlag = $('#storeFlag').val();
	if (storeFlag == "true") {
		var url = '/' + storeCode + '/sales-reprint-daily-sales-report/preview';
		PopUpUtil.ajaxHtml.post($(obj).attr("id"), url, '#divTableBody');
	} else {	
		var url = '/sales-reprint-daily-sales-report/preview';
		PopUpUtil.ajaxHtml.post($(obj).attr("id"), url, '#divTableBody');
	}
}

function printButtonClick(obj) {
	var storeCode = $('#loginStoreCode').val();
	var storeFlag = $('#storeFlag').val();
	if (storeFlag == "true") {
		var url = '/' + storeCode + '/sales-reprint-daily-sales-report/print';
		PopUpUtil.ajaxHtml.post($(obj).attr("id"), url, '#divTableBody');
	} else {
		var url = '/sales-reprint-daily-sales-report/print';
		PopUpUtil.ajaxHtml.post($(obj).attr("id"), url, '#divTableBody');
	}

}