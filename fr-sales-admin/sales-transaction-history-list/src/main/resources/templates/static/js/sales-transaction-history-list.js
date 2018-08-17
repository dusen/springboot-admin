function searchButtonClick(obj) {
	PopUpUtil.ajaxHtml.post($(obj).attr("id"),
			'/sales-transaction-history-list/list', '#divTableBody');
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

	PopUpUtil.ajaxHtml.post(sortKey, '/sales-transaction-history-list/sort',
			'#divTableBody');

}
function forDetail(obj) {
	$(".checkFlagInput").val(0);
	$(obj).parent().find(".checkFlagInput").val(1);
	PopUpUtil.util.doTransition(null,
			'/sales-transaction-correction-history-detail');
}
$(document).ready(function() {
	$("#clearButton").click(function() {
		$('input[type=text]').each(function() {
			$(this).val('');
		});
		$('#main li').removeClass("selected");
	});
});