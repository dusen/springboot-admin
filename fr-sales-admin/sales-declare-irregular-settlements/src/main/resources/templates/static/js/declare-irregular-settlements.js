function searchButtonClick(obj) {
	
	doTransition($(obj).attr("id"), '/list');
}

function clearButtonClick(obj) {
	
	doTransition($(obj).attr("id"), '');
}

function confirmButtonClick(obj) {
	
	doTransition($(obj).attr("id"), '/confirm');
}

$(document).ready(function() {
	
		var messageDisplayFlag = $("#messageDisplayFlag").val();
		if (messageDisplayFlag == "true") {
			PopUpUtil.popup.showWarning(
					'Message', 
					$("#normalMessage").val());
			$("#messageDisplayFlag").val("false");
			$("#normalMessage").val("");
		}

	$("#changeFundInteger").on('change', function() {
		
		if ($('#cashPaymentTotal').val() != null && $('#cashPaymentTotal').val() != "") {
			doTransition($("#changeFundInteger").attr("id"), '/calculation');
		}
	});
	$("#changeFundDecimal").on('change', function() {
		
		if ($('#cashPaymentTotal').val() != null && $('#cashPaymentTotal').val() != "") {
			doTransition($("#changeFundDecimal").attr("id"), '/calculation');
		}
	});
	$("#closingBalanceInteger").on('change', function() {
		
		if ($('#cashPaymentTotal').val() != null && $('#cashPaymentTotal').val() != "") {
			doTransition($("#closingBalanceInteger").attr("id"), '/calculation');
		}
	});
	$("#closingBalanceDecimal").on('change', function() {
		
		if ($('#cashPaymentTotal').val() != null && $('#cashPaymentTotal').val() != "") {
			doTransition($("#closingBalanceDecimal").attr("id"), '/calculation');
		}
	});
});

function doTransition(id, url) {
	
	var storeFlag = $("#storeFlag").val();
	if (storeFlag == "true") {
		PopUpUtil.util.doTransition(id, "/" + $("#storeCode").val() + '/declare-irregular-settlements' + url);
	} else {
		PopUpUtil.util.doTransition(id, '/declare-irregular-settlements' + url);
	}
}
