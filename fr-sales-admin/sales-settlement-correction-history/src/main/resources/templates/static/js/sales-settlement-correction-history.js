function searchButtonClick(obj) {
	PopUpUtil.ajaxHtml.post($(obj).attr("id"),
			"/sales-settlement-correction-history/list", function(data) {
				changeColor(data);
				});
}

function sortThis(sortKey) {
	var sortItem = $("#sortItem").val();
	var orderByClause = $("#orderByClause").val();
	if (sortItem != sortKey) {
		$("#sortItem").val(sortKey);
	}
	if (orderByClause == 0) {
		$("#orderByClause").val(1);
	} else {
		$("#orderByClause").val(0);
	}

	PopUpUtil.ajaxHtml.post(sortKey,
			"/sales-settlement-correction-history/sort", function(data) {
		changeColor(data);
	});	
}

function changeColor(data) {
	$(PopUpUtil.util.convertToJqueryId('#divTableBody')).html(data);
	$tr = $('#dataTable').find('tr');
	$tr.each(function() {
		var flgAmount = $(this).attr('data-showAmountColor');
		if (flgAmount == '1') {
			$(this).find($(".amount")).each(function() {
				$(this).css('background', '#FF99FF');
			});
		}
		var flgQuantity = $(this).attr('data-showQuantityColor');
		if (flgQuantity == '1') {
			$(this).find($(".quantity")).each(function() {
				$(this).css('background', '#FF99FF');
			});
		}
	});
}

$(document).ready(function() {
	$("#clearButton").click(function() {
		$('input[type=text]').each(function() {
			$(this).val('');
		});
	});
});