function setValueAndTransition(obj, url) {
	var $trObj = $(obj).parent().parent();
	ComponentUtil.util.setElementValue("codeL1", $trObj
			.attr("data-settingItem"));
	PopUpUtil.util.doTransition(null, url);
}

$(document).ready(function() {
	displayStatusColor();
	if (isNotEmpty($('#detailErrorMsg').val())) {
		var error = JSON.parse($('#detailErrorMsg').val());
		if (isNotEmpty(error)) {
			if (error.messageType == "I") {
				PopUpUtil.popup.showInformation(error.errorMessage,
						function() {PopUpUtil.util.doTransition(null, '/brand-country-setting-edit/after')});
			} else {
				PopUpUtil.popup.showError(error.errorMessage);
			}
		}
	}
});

function isNotEmpty(value) {
	return (value != null && value != "" && value != undefined);
}

function displayStatusColor() {
	$tr = $('#dataTable').find('tr');
	$tr.each(function() {
		var flg = $(this).attr('data-showStatusColor');
		if (flg == '1') {
			$(this).find("td").each(function() {
				$(this).css('background', '#EA6076');
			});
		}
	});
}