function searchButtonClick(obj) {
	PopUpUtil.ajaxHtml.post($(obj).attr("id"),
			'/brand-country-setting-edit/search', function(data) {
				displayStatusColor(data);
			});

}

function setValueAndTransition(obj, url) {
	var $trObj = $(obj).parent().parent();
	ComponentUtil.util.setElementValue("storeBrandCode", $trObj
			.attr("data-brand"));
	ComponentUtil.util.setElementValue("storeCountryCode", $trObj
			.attr("data-country"));
	ComponentUtil.util.setElementValue("businessCode", $trObj
			.attr("data-business"));
	ComponentUtil.util.setElementValue("stateCode", $trObj.attr("data-state"));
	ComponentUtil.util.setElementValue("storeCode", $trObj.attr("data-store"));
	PopUpUtil.util.doTransition(null, url);
}

$(document).ready(function() {
	$("#clearButton").click(function() {
		$('input[type=text]').each(function() {
			$(this).val('');
		});
	});
	dispalyColor();
});

function displayStatusColor(data) {
	$(PopUpUtil.util.convertToJqueryId('#divTableBody')).html(data);
	dispalyColor();
}
function dispalyColor() {
	$tr = $('#dataTable').find('tr');
	$tr.each(function() {
		var flg = $(this).attr('data-showStatusColor');
		if (flg == '1') {
			$(this).find("td").each(function() {
				$(this).css('background', '#EA6076');
			});
		} else if (flg == '2') {
			$(this).find("td").each(function() {
				$(this).css('background', '#FFC200');
			});
		}
	});
}