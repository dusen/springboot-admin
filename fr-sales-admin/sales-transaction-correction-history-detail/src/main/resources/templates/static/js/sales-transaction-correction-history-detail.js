$(document)
		.ready(
				function() {
					initTable();
					$(".for-detail").on("click", function() {
						dkpop_option($(this).data("boxid"));
					});
					initHeaderTag();
					$("#afterCorrection")
							.on(
									"click",
									function() {
										PopUpUtil.ajaxHtml
												.post(
														null,
														'/sales-transaction-correction-history-detail/afterCorrection',
														function() {
															showHistory();
														});
									})
				});

function showHistory() {
	$(".title")[0].innerHTML = $("#afterCorrectionTitle").val();
	$(".historyAfter").show();
	$(".historyBefore").hide();
	$(".right-options").hide();
}
function executeAjaxPost(url, success, handException) {
	PopUpUtil.popup.showProgress();
	var postData = $(PopUpUtil.constants.ID_BASE_FORM).serializeArray();
	$.ajax({
		type : "POST",
		url : PopUpUtil.util.getFullUrl(url),
		data : postData,
		statusCode : {
			200 : function(data) {
				PopUpUtil.popup.hideProgress();
				if (success) {
					success(data);
				}
			},
			400 : function(data) {
				PopUpUtil.popup.hideProgress();
				if (handException) {
					handException(data);
				}
			},
			404 : function(data) {
				PopUpUtil.popup.hideProgress();
				PopUpUtil.popup.showError(JSON.stringify(data));
			},
			405 : function(data) {
				PopUpUtil.popup.hideProgress();
				PopUpUtil.popup.showError(JSON.stringify(data));
			},
			500 : function(data) {
				PopUpUtil.popup.hideProgress();
				PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
			}
		},
		timeout : 100000
	});
}
function dkpop_option(typeId, confirmCall) {
	PopUpUtil.popup.showOverlayMessage();
	$messageObj = $(typeId);
	// set width
	$messageObj.css({
		width : $(typeId).css("width")
	});
	// to center
	PopUpUtil.util.toAlignVerticalCenter($messageObj);
	// bond ok btn
	$btn_obj = $messageObj.find('.confirm-btn');
	$btn_obj.unbind();
	$btn_obj.on('click', function(event) {
		event.preventDefault();
		PopUpUtil.popup.hideOverlayMessage();
		$messageObj.hide();
		confirmCall();
	});
	// bond close btn
	$btn_obj = $messageObj.find('.modal-close');
	$btn_obj.unbind();
	$btn_obj.on('click', function(event) {
		event.preventDefault();
		PopUpUtil.popup.hideOverlayMessage();
		$messageObj.hide();
		// closeCall();
	});
	$messageObj.show();
}
function initHeaderTag() {
	$(".salesTransactionHeaderTab").first().siblings(
			".salesTransactionHeaderTab").hide();
	var pageString = "";
	$(".salesTransactionHeaderTab")
			.each(
					function() {
						if ($(this).index() == 1) {
							pageString += "<a href='javascript:void(0)' style='color:#5B68D2;font-size:1.3em;' onclick='pageLink(this)' class='page_link"
									+ $(this).index()
									+ "' data-eq='"
									+ $(this).index()
									+ "'>　"
									+ $(this).index()
									+ "　</a>";
						} else {
							pageString += "<a href='javascript:void(0)' onclick='pageLink(this)' class='page_link"
									+ $(this).index()
									+ "' data-eq='"
									+ $(this).index()
									+ "'>　"
									+ $(this).index()
									+ "　</a>";
						}
					});
	$(".pageLink").html(pageString);
}
function pageLink(obj) {
	var $this = $(obj);
	$(".salesTransactionHeaderTab").eq($this.html() - 1).show().siblings(
			".salesTransactionHeaderTab").hide();
	$(".salesTransactionHeaderTab").eq($this.html() - 1).trigger("click");
	$(".pageLink").each(function() {
		$(this).find(".page_link" + $this.html().trim()).css({
			"color" : "#5B68D2",
			"font-size" : "1.3em"
		}).siblings().css({
			"color" : "#000",
			"font-size" : "1em"
		});
	});
}
function initTable() {
	$("#dataTable tr td").unbind("click");
	$("#dataTable tr td:first-child")
			.unbind()
			.on(
					"click",
					function(event) {
						var $this = $(this);
						var $parents = $this.parents("table");
						if ($parents.hasClass("cell-color-change")) {
							var $elem = $this.parent("tr").find(
									"td input[type=checkbox]");
							$this.parents("tr").toggleClass("color-change");
							$elem.prop("checked", !$elem.prop("checked"));
							var checkboxGroup = $elem.attr('checkbox-group');
							if (checkboxGroup) {
								var target = $('a.active-with-checkbox[checkbox-group="'
										+ checkboxGroup + '"]');
								if ($(':checkbox[checkbox-group="'
										+ checkboxGroup + '"]:checked').length === 0) {
									target.attr('href', '#');
									target.addClass('disabled');
								} else {
									target.attr('href', target
											.attr('original-href'));
									target.removeClass('disabled');
								}
							}
							if ($elem.is(':checked')) {
								$this.parents("tr").addClass("color-change");
							} else {
								$this.parents("tr").removeClass("color-change");
							}
						}
					});
}
