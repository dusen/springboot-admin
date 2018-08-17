$(document).ready(function() {
	$("#clearButton").click(buttonActions["clearAction"]);
	$("#searchButton").click(function() {
		buttonActions["searchAction"](this);
	});
	$("#deleteButton").click(function() {
		buttonActions["deleteAction"](this);
	});
	$("#uploadButton").click(function() {
		buttonActions["uploadAction"](this);
	});
	$("#downloadButton").click(function() {
		buttonActions["downloadAction"](this);
	});
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#searchButton").trigger("click");
		}
	});
	$("#divTableBody").on("click", ".noLink", function() {
		buttonActions["noLinkAction"](this);
	});
	$("#divTableBody").on("change", ".select_checkbox", function() {
		buttonActions["checkFunction"](this);
	});
	$("#checkbox_parent").on("change", function() {
		buttonActions["allCheckFunction"](this);
	})
});

var buttonActions = {
	checkFunction : function(obj) {
		var $this = $(obj);
		if ($this.is(":checked")) {
			$this.parents("tr").find(".checkFlagInput").val(1);
		} else {
			$this.parents("tr").find(".checkFlagInput").val(0);
		}
		if ($(".select_checkbox:checked").length == $(".select_checkbox").length) {
			$("#checkbox_parent").prop("checked", true);
		} else {
			$("#checkbox_parent").prop("checked", false);
		}
	},
	allCheckFunction : function(obj) {
		if ($(obj).is(":checked")) {
			$(".select_checkbox").prop("checked", true);
			$(".checkFlagInput").val(1);
		} else {
			$(".select_checkbox").prop("checked", false);
			$(".checkFlagInput").val(0);
		}
	},
	noLinkAction : function(obj) {
		$("#checkbox_parent").prop("checked", false);
		$(".checkFlagInput").val(0);
		$(obj).parent().find(".checkFlagInput").val(1);
		$(".select_checkbox").prop("checked", false);
		$(obj).parents("tr").find(".select_checkbox").prop("checked", true);
		buttonActions["executeAjaxPost"](
				'/sales-transaction-error-list/numberlink', function(data) {
					if(data == "AJAX_SUCCESS"){
						PopUpUtil.util.doTransition(obj.id,'/sales-transaction-correction');
					}
				})
	},
	clearAction : function() {
		$('input[type=text]').val('');
		$('#main li').removeClass("selected");
	},
	searchAction : function(obj) {
		$("#checkbox_parent").prop("checked",false);
		PopUpUtil.ajaxHtml.post(obj.id, '/sales-transaction-error-list/list','#divTableBody');
	},
	deleteAction : function() {
		// Check box list is checked.
		if ($(".select_checkbox:checked").length == 0) {
			PopUpUtil.popup.showError($("#E_SLS_66000104").val());
		} else {
			// Confirm popup.
			PopUpUtil.popup.showInformation($("#I_SLS_06000103").val(),function() {
				buttonActions.executeAjaxPost("/sales-transaction-error-list/delete",
					function(data) {
						if (data === "AJAX_SUCCESS") {
							PopUpUtil.popup.showInformation($("#I_SLS_06000104").val());
						}
					},
					function(data) {
						errorObject = data.responseJSON.detailError;
						switch (errorObject.errorCode) {
						case 'W_SIV_26000104':
						default:
							PopUpUtil.util.handleAjaxException(data);
							break;
						}
					}
				);
			});
		}
	},
	downloadAction : function(obj) {
		if ($(".select_checkbox:checked").length == 0) {
			PopUpUtil.popup.showError($("#E_SLS_66000102").val());
		} else {
			PopUpUtil.popup.showInformation($("#I_SLS_06000101").val(),function() {
				PopUpUtil.util.doTransition(obj.id,'/sales-transaction-error-list/download');
			}
			, function(data) {
				PopUpUtil.util.handleAjaxException(data);
			});
		}
	},
	uploadAction : function() {
		// Check box list is checked.
		if ($(".select_checkbox:checked").length == 0) {
			PopUpUtil.popup.showError($("#E_SLS_66000106").val());
		} else {
			// Confirm popup.
			PopUpUtil.popup.showInformation($("#I_SLS_06000105").val(),function() {buttonActions.executeAjaxPost(
				"/sales-transaction-error-list/upload",
				function(data) {
					if (data === "AJAX_SUCCESS") {
						PopUpUtil.popup.showInformation($("#I_SLS_06000106").val());
					}
				}, function(data) {
					PopUpUtil.util.handleAjaxException(data);
				});
			});
		}
	},
	executeAjaxPost : function(url, success, handException) {
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
};
