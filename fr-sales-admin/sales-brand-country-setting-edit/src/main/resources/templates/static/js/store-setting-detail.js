$(document).ready(
		function() {
			if ($('#linkageTimingVariableType').val() == 1) {
				$('input[name^="linkageUnit"]').attr("disabled", true);
			} else {
				$('input[name^="linkageUnit"]').attr("disabled", false);
			}
			if ($('#businessDateUpdateTimeVariableType').val() == 1) {
				$('input[class^="businessDateUpdateTime"]').attr("disabled",
						true);
			} else {
				$('input[class^="businessDateUpdateTime"]').attr("disabled",
						false);
			}
			if ($('#beforeStoreOpenVariableType').val() == 1) {
				$('input[class^="beforeStoreOpen"]').attr("disabled", true);
			} else {
				$('input[class^="beforeStoreOpen"]').attr("disabled", false);
			}
			if ($('#afterStoreClosingVariableType').val() == 1) {
				$('input[class^="afterStoreClosing"]').attr("disabled", true);
			} else {
				$('input[class^="afterStoreClosing"]').attr("disabled", false);
			}
			if ($('#closedBusinessDateVariableType').val() == 1) {
				$('input[name^="closedBusinessDate"]').attr("disabled", true);
			} else {
				$('input[name^="closedBusinessDate"]').attr("disabled", false);
			}
			$('label[for=franctionTwo]').click(function () {
				$('#franctionTwo').prop("checked")
				var $digit = $('#digit');
				$digit.attr("readonly", true);
				$digit.val('0');
			})
			$('label[for=franctionOne]').click(function () {
				$('#franctionOne').prop("checked")
				var $digit = $('#digit');
				$digit.attr("readonly", false);
			})
			
			$tr = $('#dataTable').find('tr');
			$tr.each(function() {
				var flg = $(this).attr('data-showAddColor');
				if (flg == '1') {
					$(this).find("td").each(function() {
						$(this).css('background', '#FFE4E4');
					});
				}
			});
			var error = JSON.parse($('#detailErrorMsg').val());
			if (error != null && error != "" && error != undefined) {
				if (error.errorCode == "I_SLS_06000112") {
					PopUpUtil.util.doTransitionConfirm(null,
							'/brand-country-setting-edit/register',
							error.errorMessage);
				} else {
					PopUpUtil.popup.showError(error.errorMessage);
				}
			}
		});
function addDialog() {
	var title = $('#dialogTitle').val();
	PopUpUtil.ajaxDialog.post(this.id, 800, title,
			'/brand-country-setting-edit/add');
}
function doRegister() {
	PopUpUtil.util
			.doTransition(null, '/brand-country-setting-edit/doubleCheck');
}