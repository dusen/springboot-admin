var storeCode = $('#storeCode').val();
var urlChange = '/' + storeCode
		+ '/store-transaction-inquiry/view-non-merchandise-item-list';
var urlSearch = '/' + storeCode
+ '/store-transaction-inquiry/list';
var storeCodeChangeForTenderGroup = '/' + storeCode
		+ '/store-transaction-inquiry/view-payment-tender-group-list';
var storeCodeChangeForTenderId = '/' + storeCode
+ '/store-transaction-inquiry/view-payment-tender-id-list';
function searchButtonClick(obj) {
	var url = $(obj).attr("data-url");
	PopUpUtil.ajaxHtml.post($(obj).attr("id"), url, '#divTableBody');
	if ($('#showDetail').val() === 'true') {
		document.getElementById("previousButton").style.display = "block";
		document.getElementById("nextButton").style.display = "block";
		document.getElementById("countTransaction").style.display = "block";
		document.getElementById("detailArea").style.display = "block";
	}
}
// Bind click event to previous button.
function onClickPrevious() {
	var storeCode = $('#storeCode').val();
	var url = '/' + storeCode + '/store-transaction-inquiry/previous-list';
	$('#groupIndex').val(parseInt($('#groupIndex').val(), 10) - 1);
	PopUpUtil.util.doTransition('previousButton', url);

}
// Control display detail.
function controllDisplayDetail() {

	if ($('#showDetail').val() === 'false') {
		document.getElementById("previousButton").style.display = "none";
		document.getElementById("nextButton").style.display = "none";
		document.getElementById("countTransaction").style.display = "none";
		document.getElementById("detailArea").style.display = "none";
	} else {
		document.getElementById("previousButton").style.display = "block";
		document.getElementById("nextButton").style.display = "block";
		document.getElementById("countTransaction").style.display = "block";
		document.getElementById("detailArea").style.display = "block";
	}
}

// Bind click event to next button.
function onClickNext() {
	$('#groupIndex').val(parseInt($('#groupIndex').val(), 10) + 1);
	var url = '/' + storeCode + '/store-transaction-inquiry/next-list';
	PopUpUtil.util.doTransition('nextButton', url);
}

$(document).ready(
		function() {
			$("#clearButton").click(function() {
				$('input[type=text]').each(function() {
					$(this).val('');
				});
			});
			$('#systemBrandName').on(
					'change',
					function() {
						PopUpUtil.ajaxHtml.post(this.id, urlChange,
								'#nonMerchandiseItemSelect')
					});
			$('#systemCountryName').on(
					'change',
					function() {
						PopUpUtil.ajaxHtml.post(this.id, urlChange,
								'#nonMerchandiseItemSelect')
					});
			$('#storeCode').on(
					'change',
					function() {
						PopUpUtil.ajaxHtml.post(this.id, storeCodeChangeForTenderGroup,
								'#paymentTenderGroupSelect')
					});
			$('#storeCode').on(
					'change',
					function() {
						PopUpUtil.ajaxHtml.post(this.id, storeCodeChangeForTenderId,
						'#paymentTenderIdSelect')
					});
			controllDisplayDetail();
		});