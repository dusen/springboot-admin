//*****************************************************************************
// PopUpUtil.constants		common constants define.
// PopUpUtil.util			common utility method.
// PopUpUtil.popup 			dialog component define.
// PopUpUtil.ajaxMessage	execute ajax, show result in a message dialog.
// PopUpUtil.ajaxDialog		execute ajax, show result in a confirm dialog.
// PopUpUtil.ajaxJson		execute ajax, return a json object.
// 
//*****************************************************************************

try{
	PopUpUtil;
}catch(e){
	PopUpUtil = {};
}

(function($popUpUtil) {
	
	//*****************************************************************************
	// component id constants define class
	//*****************************************************************************
	if($popUpUtil.constants) { 
		return $popUpUtil.constants; 
	}
	$popUpUtil.constants = {
		//window-overlay id
		ID_WINDOW_OVERLAY	: '#window-overlay',
		//window-overlay-message id
		ID_WINDOW_OVERLAY_MESSAGE	: '#window-overlay-message',
		//progress-box id
		ID_PROGRESS 		: '#progress-box',
		//error-box id
		ID_ERROR_BOX		: '#error-box',
		//warning-box id
		ID_WARNING_BOX		: '#warning-box',
		//warning-confirm-box id
		ID_WARNING_CONFIRM_BOX	: '#warning-confirm-box',
		//information-box id
		ID_INFORMATION_BOX		: '#information-box',
		//information-confirm-box id
		ID_INFORMATION_CONFIRM_BOX	: '#information-confirm-box',
		//message-box id
		ID_MESSAGE_BOX		: '#message-box',
		//dialog-box id
		ID_DIALOG_BOX		: '#dialog-box',
		//box-title id
		ID_BOX_TITLE		: '#box-title',
		//box-body id
		ID_BOX_BODY			: '#box-body',
		//box width
		BOX_WIDTH			: 500,
		//box width
		ID_BASE_FORM		: '#base_form',
		//Progressing flag
		PROGRESSING_FLAG	: false,
		//alert title
		ALERT_TITLE			: 'Alert',
		//alert width
		PROGRESSING_WIDTH	: 120,
		//alert width
		ALERT_WIDTH			: 300,
		//confirm title
		CONFIRM_TITLE		: 'Confirm',
		//confirm width
		CONFIRM_WIDTH		: 400,
		
		//confirm width
		CONFIRM_INTERRUPTING_MSG	: '',
		
		//ERROR_404
		ERROR_404	: '404 URL Not found!',
		//ERROR_405
		ERROR_405	: '405 Method Not Allowed!',
		//ERROR_500
		ERROR_500	: '500 Internal Server Error!',
		//ajax excption class
		AJAX_EXCPTION_CLASS	: 'com.fastretailing.dcp.storecommon.screen.exception.AjaxScreenException',
		//error object in base common form
		ERROR_OBJECT_IN_FORM : null,
	}
	
	//*****************************************************************************
	// utility class
	//*****************************************************************************
	
	if($popUpUtil.util) { 
		return $popUpUtil.util; 
	}
	$popUpUtil.util = {
		//------------------------------------------------------------------------------
		// get the full url
		//------------------------------------------------------------------------------	
		getFullUrl : function (url) {
			if (url.match(ComponentUtil.constants.ROOT_PATH)) {
				return url;
			} else {
				return ComponentUtil.constants.ROOT_PATH+url;
			}
		},	
			
		//------------------------------------------------------------------------------
		// get the windown size
		//------------------------------------------------------------------------------	
		getWindowSize : function () {
			var w = window.innerWidth ? window.innerWidth: $(window).width();
			var h = window.innerHeight ? window.innerHeight: $(window).height();
			return {width : w, height : h};
		},
		//------------------------------------------------------------------------------
		// show component to vertical center and align center
		//------------------------------------------------------------------------------
		toAlignVerticalCenter : function (component) {
			var winSize = PopUpUtil.util.getWindowSize();
			var dialogTop =  winSize.height/2 - component.height()/2;
			var dialogLeft = (winSize.width/2) - component.width()/2;
			component.css('top', dialogTop);
			component.css('left', dialogLeft);
		},
		
		//------------------------------------------------------------------------------
		// judge if is progressing
		//------------------------------------------------------------------------------
		isProgressing : function () {
			return PopUpUtil.constants.PROGRESSING_FLAG;
		},
		
		//------------------------------------------------------------------------------
		// set progressing flag
		//------------------------------------------------------------------------------
		setProgressingFlag : function (flag) {
			PopUpUtil.constants.PROGRESSING_FLAG = flag;
		},
		
		//------------------------------------------------------------------------------
		// format date to a format string
		//------------------------------------------------------------------------------
		formatDate : function(date, format) {
			format = format.replace(/yyyy/g, date.getFullYear());
			format = format.replace(/MM/g, ('0' + (date.getMonth() + 1)).slice(-2));
			format = format.replace(/dd/g, ('0' + date.getDate()).slice(-2));
			format = format.replace(/HH/g, ('0' + date.getHours()).slice(-2));
			format = format.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
			format = format.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
			format = format.replace(/SSS/g, ('00' + date.getMilliseconds()).slice(-3));
			format = format.replace(/M/g, (date.getMonth() + 1));
			format = format.replace(/d/g, (date.getDate()));
			format = format.replace(/H/g, (date.getHours()));
			format = format.replace(/m/g, (date.getMinutes()));
			format = format.replace(/s/g, (date.getSeconds()));
			format = format.replace(/S/g, (date.getMilliseconds()));
			return format;
		},
		
		//------------------------------------------------------------------------------
		// format number to a format string
		//------------------------------------------------------------------------------
		formatCurrency : function(nStr, format) {
			var inD = format.substr(0, 1);
			if(inD == 'N'){
				inD = '';
			}
			var outD = format.substr(1, 1);
			if(outD == 'N'){
				outD = '';
			}
			var inSep = format.substr(2, 1);
			if(inSep == 'N'){
				inSep = '';
			}
			var outSep = format.substr(3, 1);
			if(outSep == 'N'){
				outSep = '';
			}
			nStr += '';
			var dpos = nStr.indexOf(inD);
			var nStrEnd = '';
			if (dpos != -1) {
				nStrEnd = outD + nStr.substring(dpos + 1, nStr.length);
				nStr = nStr.substring(0, dpos);
			}
			
			if(inSep != '') {			
				var rgx = new RegExp(inSep, 'g');
				nStr = nStr.replace(rgx, '');
			}
			
			if(outSep != '') {			
				var rgx = /(\d+)(\d{3})/;
				while (rgx.test(nStr)) {
					nStr = nStr.replace(rgx, '$1' + outSep + '$2');
				}
			}
			return nStr + nStrEnd;
			
		},
		
		convertToJqueryId : function(objectId) {
			if(objectId != null && objectId != '') {
				var firstChar = objectId.substr(0, 1);
				if(firstChar != '#') {
					return '#'+objectId;
				} 
				
				return objectId;
			}
			return objectId;
		},
		
		//------------------------------------------------------------------------------
		// do post restful
		//------------------------------------------------------------------------------
		doPost : function (url, formObj, openNewWindow) {
			if(PopUpUtil.util.isProgressing()) {
				PopUpUtil.popup.showInformation(
						PopUpUtil.constants.CONFIRM_INTERRUPTING_MSG,
						function(){
							PopUpUtil.util.setProgressingFlag(false);
							formObj.attr('action', url);
							if(openNewWindow!= null && openNewWindow){
								formObj.attr('target', '_blank');
							}
							formObj.submit();
						});
			} else {
				PopUpUtil.util.setProgressingFlag(false);
				formObj.attr('action', PopUpUtil.util.getFullUrl(url));
				if(openNewWindow!= null && openNewWindow){
					formObj.attr('target', '_blank');
				}
				formObj.submit();
			}
		},

		//------------------------------------------------------------------------------
		// do post restful with confirm
		//------------------------------------------------------------------------------
		doPostConfirm : function (url, confirmMessage, formObj, openNewWindow) {
			PopUpUtil.popup.showInformation(
					confirmMessage,
					function(){
						PopUpUtil.util.doPost(url, formObj, openNewWindow);
					});
		},
		
		//------------------------------------------------------------------------------
		// do transition restful
		//------------------------------------------------------------------------------
		doTransition : function (buttonId, url) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}

			PopUpUtil.util.doPost(url, $(PopUpUtil.constants.ID_BASE_FORM), false);
		},

		//------------------------------------------------------------------------------
		// do transition restful with confirm
		//------------------------------------------------------------------------------
		doTransitionConfirm : function (buttonId, url, confirmMessage) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}

			PopUpUtil.util.doPostConfirm(url, confirmMessage, $(PopUpUtil.constants.ID_BASE_FORM), false);
		},
		
		//------------------------------------------------------------------------------
		// do transition restful open with a new window
		//------------------------------------------------------------------------------
		doTransitionOpenNewWindow : function (buttonId, url) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}

			PopUpUtil.util.doPost(url, $(PopUpUtil.constants.ID_BASE_FORM), true);
		},

		//------------------------------------------------------------------------------
		// do transition restful with confirm open with a new window
		//------------------------------------------------------------------------------
		doTransitionOpenNewWindowConfirm : function (buttonId, url, confirmMessage) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}

			PopUpUtil.util.doPostConfirm(url, confirmMessage, $(PopUpUtil.constants.ID_BASE_FORM), true);
		},
		
		//------------------------------------------------------------------------------
		// handle exception when a ajax exception throws.
		//------------------------------------------------------------------------------
		handleAjaxException : function (data) {
			if(data.responseJSON.detailError){
				errorType = data.responseJSON.ajaxMessageType;
				errorObject = data.responseJSON.detailError;
	    		if(errorType == 'E') {
	    			if(errorObject.errorMessageTitle == ''){
	    				PopUpUtil.popup.showError(errorObject.errorMessage);
	    			} else {
	    				PopUpUtil.popup.showError(errorObject.errorMessageTitle, 
	    										errorObject.errorMessage);
	    			}
	    		} else if (errorType == 'W') {
	    			if(errorObject.errorMessageTitle == ''){
	    				PopUpUtil.popup.showWarning(errorObject.errorMessage);
	    			} else {
	    				PopUpUtil.popup.showWarning(errorObject.errorMessageTitle, 
													errorObject.errorMessage);
	    			}
	    		} else {
	    			if(errorObject.errorMessageTitle == ''){
	    				PopUpUtil.popup.showInformation(errorObject.errorMessage);
	    			} else {
	    				PopUpUtil.popup.showInformation(errorObject.errorMessageTitle, 
														errorObject.errorMessage);
	    			}
	    		}
	    	} else if(data.responseJSON.fieldErrors) {
	    		for(i=0; i<data.responseJSON.fieldErrors.length; i++) {
	    			var realId = data.responseJSON.fieldErrors[i].field
	    						.replace('[','').replace(']','').replace('.','_');
	    			$('#'+realId).addClass('field-error');
	    			var errorDiv = "<div class='error-message'>" 
	    							+ data.responseJSON.fieldErrors[i].defaultMessage
	    							+ "</div>";
	    			$('#'+realId).empty();
	    			$('#'+realId).append(errorDiv);
	    		}
	    		ComponentUtil.tooltip.initialize();
	    		
	    	}
			
			else{
	    		PopUpUtil.popup.showError(data.responseJSON.message);
	    	}
		},
		
		//------------------------------------------------------------------------------
		// handle error information in form with popup.
		//------------------------------------------------------------------------------
		showMessagePopup : function (confirmCall, closeCall) {
			errObject = PopUpUtil.constants.ERROR_OBJECT_IN_FORM;
			if(errObject){
				errorType = errObject.messageType;
				errorTitle = errObject.errorMessageTitle;
				errorMessage = errObject.errorMessage;
				if (errorTitle == '') {
					if (arguments.length === 1) {
						PopUpUtil.popup.showMessage(errorType, errorMessage, confirmCall);
					} else if (arguments.length === 2) {
						PopUpUtil.popup.showMessage(errorType, errorMessage, confirmCall, closeCall);
					} else {
						PopUpUtil.popup.showMessage(errorType, errorMessage);
					}
				} else {
					if (arguments.length === 1) {
						PopUpUtil.popup.showMessage(errorType, errorTitle, errorMessage, confirmCall);
					} else if (arguments.length === 2) {
						PopUpUtil.popup.showMessage(errorType, errorTitle, errorMessage, confirmCall, closeCall);
					} else {
						PopUpUtil.popup.showMessage(errorType, errorTitle, errorMessage);
					}
				}
			}
		}
	}
	
	//*****************************************************************************
	// pop up dialog class
	//*****************************************************************************
	
	if($popUpUtil.popup) { 
		return $popUpUtil.popup; 
	}
	$popUpUtil.popup = {
		//------------------------------------------------------------------------------
		// show window overlay 
		//------------------------------------------------------------------------------
		showOverlay : function () {
			var winSize = PopUpUtil.util.getWindowSize();
			winSize.top = 0;
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).css(winSize);
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).css('z-index', 11);
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).show();
		},
		
		showOverlayForProgress : function () {
			var winSize = PopUpUtil.util.getWindowSize();
			winSize.top = $(".header").height();
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).css('z-index', 9);
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).css(winSize).show();
		},
		
		hideOverlay : function () {
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY).hide();
		},
		
		//------------------------------------------------------------------------------
		// show window overlay for message
		//------------------------------------------------------------------------------
		showOverlayMessage : function () {
			var winSize = PopUpUtil.util.getWindowSize();
			winSize.top = 0;
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY_MESSAGE).css(winSize);
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY_MESSAGE).css('z-index', 4000);
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY_MESSAGE).show();
		},
		
		hideOverlayMessage : function () {
			$(PopUpUtil.constants.ID_WINDOW_OVERLAY_MESSAGE).hide();
		},
		
		
		//------------------------------------------------------------------------------
		// show progress dialog
		//------------------------------------------------------------------------------
		showProgress : function () {
			PopUpUtil.popup.showOverlayForProgress();
			$progress = $(PopUpUtil.constants.ID_PROGRESS);
			$progress.css({width : PopUpUtil.constants.PROGRESSING_WIDTH});
			PopUpUtil.util.toAlignVerticalCenter($progress);
			$progress.show();
			PopUpUtil.util.setProgressingFlag(true);
		},
		
		//------------------------------------------------------------------------------
		// hide progress dialog
		//------------------------------------------------------------------------------
		hideProgress : function () {
			if(PopUpUtil.popup.isOpeningDialog()){
				PopUpUtil.popup.showOverlay();
			}else{
				PopUpUtil.popup.hideOverlay();
			}
			$(PopUpUtil.constants.ID_PROGRESS).hide();
			PopUpUtil.util.setProgressingFlag(false);
		},
		
		//------------------------------------------------------------------------------
		// show pop up base
		//------------------------------------------------------------------------------
		showPop : function (typeId, title, context, confirmCall, closeCall) {
			PopUpUtil.popup.showOverlayMessage();
			$messageObj = $(typeId);
			//set title
			if(title != null){
				$messageObj.find(PopUpUtil.constants.ID_BOX_TITLE).html(title);
			} else {
				$messageObj.find(PopUpUtil.constants.ID_BOX_TITLE).html(
					$messageObj.find(PopUpUtil.constants.ID_BOX_TITLE).attr('default-title'));
			}
			//set width
			$messageObj.css({width : PopUpUtil.constants.BOX_WIDTH});
			//set body
			$messageObj.find(PopUpUtil.constants.ID_BOX_BODY).html(context);
			//to center
			PopUpUtil.util.toAlignVerticalCenter($messageObj);
			
			if(confirmCall != null && typeof confirmCall == "function") {
				//bond ok btn
				$btn_obj = $messageObj.find('.confirm-btn');
				$btn_obj.unbind(); 
				$btn_obj.on('click', function(event) {
					event.preventDefault();
					PopUpUtil.popup.hideOverlayMessage();
					$messageObj.find(PopUpUtil.constants.ID_BOX_BODY).html('');
					$messageObj.hide();
					confirmCall();
				});
			}
			
			
			if(closeCall != null && typeof closeCall == "function"){
				//bond close btn
				$btn_obj = $messageObj.find('.modal-close');
				$btn_obj.unbind(); 
				$btn_obj.on('click', function(event) {
					event.preventDefault();
					PopUpUtil.popup.hideOverlayMessage();
					//clear context
					$messageObj.find(PopUpUtil.constants.ID_BOX_BODY).html('');
					$messageObj.hide();
					closeCall();
				});
			} else {
				//bond close btn
				$btn_obj = $messageObj.find('.modal-close');
				$btn_obj.unbind(); 
				$btn_obj.on('click', function(event) {
					event.preventDefault();
					PopUpUtil.popup.hideOverlayMessage();
					//clear context
					$messageObj.find(PopUpUtil.constants.ID_BOX_BODY).html('');
					$messageObj.hide();
				});
			}
			
			$messageObj.show();
		},
		
		//------------------------------------------------------------------------------
		// show error dialog
		//------------------------------------------------------------------------------
		showError : function (title, context) {
			if(arguments.length === 2){
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_ERROR_BOX, arguments[0], arguments[1], null, null);
			} else {
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_ERROR_BOX, null, arguments[0], null, null);
			}
		},
		
		//------------------------------------------------------------------------------
		// show warning dialog
		//------------------------------------------------------------------------------
		showWarning : function (title, context, confirmCall, closeCall) {
			if(arguments.length === 1){
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_BOX, null, arguments[0], null, null);
			} else if(arguments.length === 2){
				if(typeof arguments[1] == "function") {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_CONFIRM_BOX, null, arguments[0], arguments[1], null);
				} else {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_BOX, arguments[0], arguments[1], null, null);
				}
			} else if(arguments.length === 3) {
				if(typeof arguments[1] == "function" && typeof arguments[2] == "function") {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_CONFIRM_BOX, null, arguments[0], arguments[1], arguments[2]);
				}else if (typeof arguments[1] != "function" && typeof arguments[2] == "function"){
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_CONFIRM_BOX, arguments[0], arguments[1], arguments[2], null);
				}
			} else {
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_WARNING_CONFIRM_BOX, arguments[0], arguments[1], arguments[2], arguments[3]);
			}
			
		},
		
		//------------------------------------------------------------------------------
		// show information dialog
		//------------------------------------------------------------------------------
		showInformation : function (title, context, confirmCall, closeCall) {
			if(arguments.length === 1){
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_BOX, null, arguments[0], null, null);
			} else if(arguments.length === 2){
				if(typeof arguments[1] == "function") {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_CONFIRM_BOX, null, arguments[0], arguments[1], null);
				} else {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_BOX, arguments[0], arguments[1], null, null);
				}
			} else if(arguments.length === 3) {
				if(typeof arguments[1] == "function" && typeof arguments[2] == "function") {
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_CONFIRM_BOX, null, arguments[0], arguments[1], arguments[2]);
				}else if (typeof arguments[1] != "function" && typeof arguments[2] == "function"){
					PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_CONFIRM_BOX, arguments[0], arguments[1], arguments[2], null);
				}
			} else{
				PopUpUtil.popup.showPop(PopUpUtil.constants.ID_INFORMATION_CONFIRM_BOX, arguments[0], arguments[1], arguments[2], arguments[3]);
			}
		},
		
		//------------------------------------------------------------------------------
		// show message up base
		//------------------------------------------------------------------------------
		showMessage : function (typeId, title, context, confirmCall, closeCall) {
			if(typeId == 'E'){
				if(arguments.length === 2){
					PopUpUtil.popup.showError(title);
				}else if(arguments.length === 3){
					PopUpUtil.popup.showError(title, context);
				}
			} else if (typeId == 'W') {
				if(arguments.length === 2){
					PopUpUtil.popup.showWarning(title);
				}else if(arguments.length === 3){
					PopUpUtil.popup.showWarning(title, context);
				}else if(arguments.length === 4){
					PopUpUtil.popup.showWarning(title, context, confirmCall);
				}else if(arguments.length === 5){
					PopUpUtil.popup.showWarning(title, context, confirmCall, closeCall);
				}
			} else {
				if(arguments.length === 2){
					PopUpUtil.popup.showInformation(title);
				}else if(arguments.length === 3){
					PopUpUtil.popup.showInformation(title, context);
				}else if(arguments.length === 4){
					PopUpUtil.popup.showInformation(title, context, confirmCall);
				}else if(arguments.length === 5){
					PopUpUtil.popup.showInformation(title, context, confirmCall, closeCall);
				}
			}
		},
		
		//------------------------------------------------------------------------------
		// show dialog with two button (OK | NG)
		//------------------------------------------------------------------------------
		showDialog : function (maxWidth, title, context) {
			PopUpUtil.popup.showOverlay();
			$confirmObj = $(PopUpUtil.constants.ID_DIALOG_BOX);
			//set width
			$confirmObj.css({width : maxWidth});
			//set title
			$confirmObj.find(PopUpUtil.constants.ID_BOX_TITLE).html(title);
			//set body
			$confirmObj.find(PopUpUtil.constants.ID_BOX_BODY).html(context);
			//to center
			PopUpUtil.util.toAlignVerticalCenter($confirmObj);
			//bond close btn
			$btn_obj = $confirmObj.find('.modal-close');
			$btn_obj.unbind(); 
			$btn_obj.on('click', function(event) {
				event.preventDefault();
				PopUpUtil.popup.hideOverlay();
				//clear context
				$confirmObj.find(PopUpUtil.constants.ID_BOX_BODY).html('');
				$confirmObj.hide();
			});
			$confirmObj.show();
		},
		
		//------------------------------------------------------------------------------
		// close dialog
		//------------------------------------------------------------------------------
		closeDialog : function() {
			$confirmObj = $(PopUpUtil.constants.ID_DIALOG_BOX);
			PopUpUtil.popup.hideOverlay();
			//clear context
			$confirmObj.find(PopUpUtil.constants.ID_BOX_BODY).html('');
			$confirmObj.hide();
		},
		
		//------------------------------------------------------------------------------
		// True when dialog is opening
		//------------------------------------------------------------------------------
		isOpeningDialog : function() {
			$confirmObj = $(PopUpUtil.constants.ID_DIALOG_BOX);
			if($confirmObj.css('display') != 'none'){
				return true;
			}
			
			return false;
		},
		
	}
	
	//*****************************************************************************
	// execute ajax, show result in a message dialog class
	//*****************************************************************************
	if($popUpUtil.ajaxMessage) { 
		return $popUpUtil.ajaxMessage; 
	}	
	
	$popUpUtil.ajaxMessage = {
			
		//------------------------------------------------------------------------------
		// execute post ajax, return a html context in message dialog
		//------------------------------------------------------------------------------
		post : function (buttonId, url) { 
			
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}
			
			PopUpUtil.popup.showProgress();
			
			ComponentUtil.tooltip.clearTooltip();
			
			var postData = $(PopUpUtil.constants.ID_BASE_FORM).serializeArray();
			
			$.ajax({
				   type: "POST",
				   url : PopUpUtil.util.getFullUrl(url),
				   data: postData,
				   statusCode: {
					    200: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showInformation(data);
					    },
					    400: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.util.handleAjaxException(data);
					    },
					    404: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_404);
					    },
					    405: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_405);
					    },
					    500: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
					    }
				   },
				   timeout: 100000
			});
		},
	}
	
	//*****************************************************************************
	// execute ajax, show result in a dialog class
	//*****************************************************************************
	if($popUpUtil.ajaxDialog) { 
		return $popUpUtil.ajaxDialog; 
	}	
	
	$popUpUtil.ajaxDialog = {
		//------------------------------------------------------------------------------
		// execute post ajax, return a html context in dialog
		//------------------------------------------------------------------------------
		post : function (buttonId, maxWidth, title, url) { 
			
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}
			
			PopUpUtil.popup.showProgress();
			
			ComponentUtil.tooltip.clearTooltip();
			
			var postData = $(PopUpUtil.constants.ID_BASE_FORM).serializeArray();
			
			
			$.ajax({
				   type: "POST",
				   url : PopUpUtil.util.getFullUrl(url),
				   data: postData,
				   statusCode: {
					    200: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showDialog(maxWidth, title, data);
					    },
					    400: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.util.handleAjaxException(data);
					    },
					    404: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_404);
					    },
					    405: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_405);
					    },
					    500: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
					    }
				   },
				   timeout: 100000
			});
		},
	}
	
	//*****************************************************************************
	// execute ajax, show result in a div component
	//*****************************************************************************
	if($popUpUtil.ajaxHtml) { 
		return $popUpUtil.ajaxHtml; 
	}	
	
	$popUpUtil.ajaxHtml = {
			
		//------------------------------------------------------------------------------
		// execute post ajax, return a html context in message dialog
		//------------------------------------------------------------------------------
		post : function (buttonId, url, showComponentId) { 
			
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}
			
			PopUpUtil.popup.showProgress();
			
			ComponentUtil.tooltip.clearTooltip();
			
			var postData = $(PopUpUtil.constants.ID_BASE_FORM).serializeArray();
			var doCallBackFlg = false;
			if(typeof arguments[2] == "function") {
				doCallBackFlg = true;
			}
			$.ajax({
				   type: "POST",
				   url : PopUpUtil.util.getFullUrl(url),
				   data: postData,
				   statusCode: {
					    200: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	if(doCallBackFlg){
					    		showComponentId(data);
					    	}else{
					    		$(PopUpUtil.util.convertToJqueryId(showComponentId)).html(data);
					    	}
					    },
					    400: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.util.handleAjaxException(data);
					    },
					    404: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_404);
					    },
					    405: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_405);
					    },
					    500: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
					    }
				   },
				   timeout: 100000
			});
		},
	}
	
	//*****************************************************************************
	// execute ajax, return a json object class
	//*****************************************************************************
	if($popUpUtil.ajaxJson) { 
		return $popUpUtil.ajaxJson; 
	}	
	
	$popUpUtil.ajaxJson = {
		
		//------------------------------------------------------------------------------
		// execute post ajax, return a json object
		//------------------------------------------------------------------------------
		post : function (buttonId, url, callBack) { 
			
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}
			
			PopUpUtil.popup.showProgress();
			
			ComponentUtil.tooltip.clearTooltip();
			
			var postData = $(PopUpUtil.constants.ID_BASE_FORM).serializeArray();
			
			$.ajax({
				   type: "POST",
				   url : PopUpUtil.util.getFullUrl(url),
				   data: postData,
				   statusCode: {
					    200: function(data){
					    	callBack(data);
					    	PopUpUtil.popup.hideProgress();
					    },
					    400: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.util.handleAjaxException(data);
					    },
					    404: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(JSON.stringify(data));
					    },
					    405: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(JSON.stringify(data));
					    },
					    500: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
					    }
				   },
				   timeout: 100000
			});
		},
		
		//------------------------------------------------------------------------------
		// execute post ajax with upload files, return a json object
		//------------------------------------------------------------------------------
		postWithUploadFile : function (buttonId, url, callBack, formId) { 
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId) && $(jqueryId).hasClass("disabled")) {
				return;
			}
			
			PopUpUtil.popup.showProgress();
			
			ComponentUtil.tooltip.clearTooltip();
			
			var formData;
			var form;
			if(arguments.length === 3){
				form = $(PopUpUtil.constants.ID_BASE_FORM);
			}else if(arguments.length === 4){
				var jqueryFormId = PopUpUtil.util.convertToJqueryId(formId);
				form = $(jqueryFormId);
			}
			formData = new FormData(form.get()[0]);
			
			var $files = form.find('input[type="file"]');
			// get file data by .prop() and set the file information to formData
		    $files.each(function(){
			    formData.append( 'file', $(this).prop("files")[0] );
		    });
		    
		    
			$.ajax({
				   type: "POST",
				   url : PopUpUtil.util.getFullUrl(url),
				   data: formData,
				   enctype: 'multipart/form-data',
				   processData: false,
				   contentType: false,
				   statusCode: {
					    200: function(data){
					    	callBack(data);
					    	PopUpUtil.popup.hideProgress();
					    },
					    400: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.util.handleAjaxException(data);
					    },
					    404: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(JSON.stringify(data));
					    },
					    405: function(data) {
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(JSON.stringify(data));
					    },
					    500: function(data){
					    	PopUpUtil.popup.hideProgress();
					    	PopUpUtil.popup.showError(PopUpUtil.constants.ERROR_500);
					    }
				   },
				   timeout: 100000
			});
		},
	}
})(PopUpUtil);