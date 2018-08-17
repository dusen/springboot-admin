$(document).ready(
		function() {
			initTable();
			$("#checkAll").change(function() {
				if (!$('input:checkbox').is('checked')) {
					$('input:checkbox').prop('checked', true);
				} else {
					$('input:checkbox').prop('checked', false);
				}
			});
			
			$("#itemDetailsSelectAll").change(function() {
				var status = this.checked;
				$('input[id^="itemDetailsSelect"]').each(function() {
					this.checked = status;
				});
			});
			$("#salesTaxSelectAll").change(function() {
				var status = this.checked;
				$('input[id^="salesTaxSelect"]').each(function() {
					this.checked = status;
				});
			});
			$("#salesPaymentSelectAll").change(function() {
				var status = this.checked;
				$('input[id^="salesPaymentSelect"]').each(function() {
					this.checked = status;
				});
			});
			$("#salesTotalSelectAll").change(function() {
				var status = this.checked;
				$('input[id^="salesTotalSelect"]').each(function() {
					this.checked = status;
				});
			});
			$(".parent_check").on("change",function(){
				removeChecked(this);
			});
			$(".for-detail").on(
					"click",
					function() {
						dkpop_option($(this).data("boxid"));
					});
			
			$("#upload").on("click",function(){
				upload(function(){
					if(!$("input[name='uploadOption']:checked").val()){
						PopUpUtil.popup.showError($("#E_SLS_66000140").val());
					}else{
						executeAjaxPost("/sales-transaction-correction/upload",function(){},function(data) {
							errorObject = data.responseJSON.detailError;
							switch (errorObject.errorCode) {
							case 'W_SIV_26000104':
							default:
								PopUpUtil.util.handleAjaxException(data);
								break;
							}
						});
					}
				});
			})
			$("#deleteButton").on("click",function(){
				deleteFooterAction(function(){
					if(!$("input[name='deleteOption']:checked").val()){
						PopUpUtil.popup.showError($("#E_SLS_66000140").val());
					}else{
						executeAjaxPost("/sales-transaction-correction/delete",function(){
							$("#back").trigger("click");
						},function(data) {
							errorObject = data.responseJSON.detailError;
							switch (errorObject.errorCode) {
							case 'W_SIV_26000104':
							default:
								PopUpUtil.util.handleAjaxException(data);
								break;
							}
						});
					}
				});
			});
			$("#back").on("click",function(){
				executeAjaxPost("/sales-transaction-correction/back",function(){
					window.history.go(-1);
				});
			});
			$(".audit").on("click",function(){
				executeAjaxPost("/sales-transaction-correction/audit",function(){
					alert("Success");
				},function(data) {
					errorObject = data.responseJSON.detailError;
					switch (errorObject.errorCode) {
					case 'W_SIV_26000104':
					default:
						PopUpUtil.util.handleAjaxException(data);
						break;
					}
				});
			})
			initHeaderTag();
		});

function upload(confirmCall){
	dkpop_option("#upload-option",confirmCall);
}

function deleteFooterAction(confirmCall){
	dkpop_option("#delete-option",confirmCall);
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
				if(handException){
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
function setValueAndTransition(id, obj, url) {
	var $trObj = $(obj).parent().parent().parent();
	ComponentUtil.util.setElementValue("transactionId", $trObj
			.attr("data-transactionId"));
	ComponentUtil.util.setElementValue("salesTransactionErrorId", $trObj
			.attr("data-salesTransactionErrorId"));
	ComponentUtil.util.setElementValue("orderSubNumber", $trObj
			.attr("data-orderSubNumber"));
	ComponentUtil.util.setElementValue("detailSubNumber", $trObj
			.attr("data-detailSubNumber"));
	var title = null;
	if (id === 'discount') {
		title = $('#discountPopupTitle').val();
	} else {
		title = $('#taxPopupTitle').val();
	}
	PopUpUtil.ajaxDialog.post(null, 870, title, url);
}
function dkpop_option(typeId,confirmCall){
	PopUpUtil.popup.showOverlayMessage();
	$messageObj = $(typeId);
	//set width
	$messageObj.css({width : $(typeId).css("width")});
	//to center
	PopUpUtil.util.toAlignVerticalCenter($messageObj);
	//bond ok btn
	$btn_obj = $messageObj.find('.confirm-btn');
	$btn_obj.unbind();
	$btn_obj.on('click', function(event) {
		event.preventDefault();
		PopUpUtil.popup.hideOverlayMessage();	
		$messageObj.hide();
		confirmCall();
	});
	//bond close btn
	$btn_obj = $messageObj.find('.modal-close');
	$btn_obj.unbind(); 
	$btn_obj.on('click', function(event) {
		event.preventDefault();
		PopUpUtil.popup.hideOverlayMessage();
		$messageObj.hide();
		//closeCall();
	});
	$messageObj.show();
}
function initHeaderTag(){
	$(".salesTransactionHeaderTab").first().siblings(".salesTransactionHeaderTab").hide();
	var pageString = "";
	$(".salesTransactionHeaderTab").each(function(){
		if($(this).index() == 1){
			pageString+="<a href='javascript:void(0)' style='color:#5B68D2;font-size:1.3em;' onclick='pageLink(this)' class='page_link"+$(this).index()+"' data-eq='"+$(this).index()+"'>　"+$(this).index()+"　</a>";	
		}else{
			pageString+="<a href='javascript:void(0)' onclick='pageLink(this)' class='page_link"+$(this).index()+"' data-eq='"+$(this).index()+"'>　"+$(this).index()+"　</a>";
		}	
	});
	$(".pageLink").html(pageString);
}

function pageLink(obj){
	var $this = $(obj);
	$(".salesTransactionHeaderTab").eq($this.html()-1).show().siblings(".salesTransactionHeaderTab").hide();
	$(".salesTransactionHeaderTab").eq($this.html()-1).trigger("click");
	$(".pageLink").each(function(){
		$(this).find(".page_link"+$this.html().trim()).css({"color":"#5B68D2","font-size":"1.3em"}).siblings().css({"color":"#000","font-size":"1em"});
	});
}

function addDetailBySelect(obj){
	var addSelectFlag = $(".add_item_check").val();
	if(addSelectFlag == "ITEM"){
		addDetail(obj);
	}else{
		addNMDetail(obj);
	}
}

function deleteAction(obj){
	$this = $(obj);
	var _action_target = $this.data("action");
	$(_action_target).each(function(){
		if($(this).find(".detail_item_check:checked").length > 0){
			$(this).find(".detail_item_check:checked").on("change",function(){
				removeChecked(this);
			});
			$(this).find("input:text").prop("disabled",true);
			$(this).find("input.textbox").prop("disabled",true);
			$(this).find("td").addClass("background_gr");
			$(this).find(".deleteFlag").val("1").prop("disabled",false);
			$($(this).data("inputclass")).find("input").prop("disabled",true);
			if($(this).data("updatetype")){
				$(this).find(".select-elem").addClass("hidden_box")
			}
			if($(this).data("subclass")){
				if(!$($(this).data("subclass")).find(".detail_item_check").prop("checked")){
					$($(this).data("subclass")).find(".detail_item_check").next().trigger("click");	
					$($(this).data("subclass")).find(".detail_item_check").prop("checked",true);
				}
				
			}
		}
	});
}

function deleteDiscountAction(obj){
	$this = $(obj);
	var _action_target = $this.data("action");
	$(_action_target).each(function(){
		if($(this).find(".detail_item_check_inn:checked").length > 0){
			$(this).find(".detail_item_check_inn:checked").next().on("click",function(){
				removeDiscountChecked(this);
			});
			$(this).find("input:text").prop("disabled",true);
			$(this).find("input.textbox").prop("disabled",true);
			$(this).find("td").addClass("background_gr");
			$(this).find(".deleteFlag").val("1").prop("disabled",false);
			$($(this).data("inputclass")).find("input").prop("disabled",true);
			if($(this).data("updatetype")){
				$(this).find(".select-elem").addClass("hidden_box")
			}
		}
	});
}

function removeChecked(obj){
	//	$(obj).unbind("change");
	if(!$(obj).is("checked")){
		if($(obj).parents("tr").data("updatetype")){
			$(obj).parents("tr").find(".select-elem").removeClass("hidden_box");
		}
		$(obj).parents("tr").find(".deleteFlag").val(0);
		$(obj).parents("tr").find("td").removeClass("background_gr");
		$(obj).parents("tr").find("input:text").prop("disabled",false);
		$(obj).parents("tr").find("input:hidden").prop("disabled",false);
		$($(obj).parents("tr").data("inputclass")).find("input").prop("disabled",false);
	}
}

function removeDiscountChecked(obj){
	$(obj).unbind("click");
	if(!$(obj).prev().is("checked")){
		if($(obj).parents(".in_select_tr").data("updatetype")){
			$(obj).parents(".in_select_tr").find(".select-elem").removeClass("hidden_box");
		}
		$(obj).parents(".in_select_tr").find(".deleteFlag").val(0);
		$(obj).parents(".in_select_tr").find("td").removeClass("background_gr");
		$(obj).parents(".in_select_tr").find("input:text").prop("disabled",false);
		$(obj).parents(".in_select_tr").find("input:hidden").prop("disabled",false);
		$($(obj).parents(".in_select_tr").data("inputclass")).find("input").prop("disabled",false);
	}
}

String.prototype.replaceAll = function(str,target){
	return this.replace(new RegExp(str,"ig"),target);
};

function addDetail(obj){
	var detail_class = ".detail_index";
	var headindex = $(obj).data("headerindex");
	var detailindex = $(detail_class+"_"+headindex).length;
	var html = $(".detail_add_temp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",headindex);
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",detailindex);
	ComponentUtil.selectBox.initialize();
	$(".detail_table_"+headindex).append(html);
	ComponentUtil.selectBox.initialize();
	
	$(".datepicker-text-add_detail_"+headindex+"_"+detailindex).each(function(){
		$(this).datepicker({showOn: "both",
			buttonImageOnly: true,
			buttonImage: ComponentUtil.constants.DATEPICKER_BUTTON_IMAGE_SRC
		}); 
		var formatStr = $(this).attr('date-format');
		if (formatStr != "") {
			$(this).datepicker("option", "dateFormat", formatStr);
		}
		$(this).next().after("<img src='#' class='ui-datepicker-close ui-icon ui-icon-close'></div>");
		
		$(this).attr('readonly', 'readonly');
		$(this).addClass('textbox');
	});
	$('.ui-datepicker-close').on("click",function(){
		$(this).prev('img').prev(".datepicker-text").val("");
	});
}

function addNMDetail(obj){
	var detail_class = ".nmdetail_index";
	var html = $(".nmdetail_add_temp").find("tbody").html();
	var headindex = $(obj).data("headerindex");
	var detailindex = $(detail_class+"_"+headindex).length;
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",headindex);
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",detailindex);
	ComponentUtil.selectBox.initialize();
	$(".detail_table_"+headindex).append(html);
	ComponentUtil.selectBox.initialize();
	$(".datepicker-text-add_nmdetail_"+headindex+"_"+detailindex).each(function(){
		$(this).datepicker({showOn: "both",
			buttonImageOnly: true,
			buttonImage: ComponentUtil.constants.DATEPICKER_BUTTON_IMAGE_SRC
		}); 
		var formatStr = $(this).attr('date-format');
		if (formatStr != "") {
			$(this).datepicker("option", "dateFormat", formatStr);
		}
		$(this).next().after("<img src='#' class='ui-datepicker-close ui-icon ui-icon-close'></div>");
		
		$(this).attr('readonly', 'readonly');
		
		$(this).addClass('textbox');
	});
	$('.ui-datepicker-close').on("click",function(){
		$(this).prev('img').prev(".datepicker-text").val("");
	});
}

function addTax(obj){
	var tax_class = $(obj).data("action");
	var headindex = $(obj).data("headerindex");
	var taxindex = $(tax_class).length;
	var html = $(".tax_add_temp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",headindex);
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",taxindex);
	ComponentUtil.selectBox.initialize();
	$(".tax_table_"+headindex).append(html);
	ComponentUtil.selectBox.initialize();
}

function addTotal(obj){
	var total_class = $(obj).data("action");
	var headindex = $(obj).data("headerindex");
	var totalindex = $(total_class).length;
	var html = $(".total_add_temp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",headindex);
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",totalindex);
	ComponentUtil.selectBox.initialize();
	$(".total_table_"+headindex).append(html);
	ComponentUtil.selectBox.initialize();
	$(".currency_code_input_total").on("change",function(){
		$(this).parents("tr").find(".currencyCode_add").val($(this).next().val());
	});
}
function labelAction(obj){
	if($(obj).prev().prop("checked") == false){
		$(obj).prev().prop("checked",true);
	}else{
		$(obj).prev().prop("checked",false);
	}
	
}
function addNMItemDiscount(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_nmdetail_discount_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("nmitemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmDiscountStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	$(".currency_code_pull_down").on("change",function(){
		$(this).parents(".add_item_nmdetail_discount").find(".currency_code_hidden").val($(this).next().val());
	})
	ComponentUtil.selectBox.initialize();
}
function addItemNMItemDiscount(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_item_nmdetail_discount_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("itemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmItemStat.index\\}\\_\\_\\]",$(obj).data("nmitemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmDiscountStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	$(".currency_code_pull_down").on("change",function(){
		$(this).parents(".add_item_item_nmdetail_discount").find(".currency_code_hidden").val($(this).next().val());
	})
	ComponentUtil.selectBox.initialize();
}
function addItemDiscount(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_detail_discount_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("itemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{discountStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	$(".currency_code_pull_down").on("change",function(){
		$(this).parents(".add_item_detail_discount").find(".currency_code_hidden").val($(this).next().val());
	})
	ComponentUtil.selectBox.initialize();
}

function addItemTax(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_detail_tax_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("itemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{taxStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	$(".currency_code_pull_down").on("change",function(){
		$(this).parents(".add_item_detail_tax").find(".currency_code_hidden").val($(this).next().val());
	});
	ComponentUtil.selectBox.initialize();
}

function addNMItemTax(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_nmdetail_tax_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("nmitemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmTaxStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	ComponentUtil.selectBox.initialize();
}
function addItemNMItemTax(obj){
	var _table = $(obj).data("add_table_class");
	var html = $(".item_item_nmdetail_tax_tmp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",$(obj).data("headindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",$(obj).data("itemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmItemStat.index\\}\\_\\_\\]",$(obj).data("nmitemindex"));
	html= html.replaceAll("\\[\\_\\_\\$\\{nmTaxStat.index\\}\\_\\_\\]",$(_table).find("tr").length);
	ComponentUtil.selectBox.initialize();
	$(_table).append(html);
	ComponentUtil.selectBox.initialize();
}

function addTender(obj){
	var tender_class = $(obj).data("action");
	var headindex = $(obj).data("headerindex");
	var tenderindex = $(tender_class).length;
	var html = $(".tender_add_temp").find("tbody").html();
	html= html.replaceAll("\\[\\_\\_\\$\\{stat.index\\}\\_\\_\\]",headindex);
	html= html.replaceAll("\\[\\_\\_\\$\\{itemStat.index\\}\\_\\_\\]",tenderindex);
	ComponentUtil.selectBox.initialize();
	$(".tender_table_"+headindex).append(html);
	ComponentUtil.selectBox.initialize();
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

function referenceTender(){
	dkpop_option("#payment_refference");
}
