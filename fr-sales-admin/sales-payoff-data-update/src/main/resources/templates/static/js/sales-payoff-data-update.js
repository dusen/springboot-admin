function clickRegister() {
	var fileSizeOverMessge=/* [[#{I_SLS_06000112}]] */;
	PopUpUtil.popup.showInformation(
			fileSizeOverMessge
			,function(){	
		PopUpUtil.util.doPost("/sales/v1/uq/ca/screen/00/sales-settlement-unmatch-fix/update", $("#base_form"), false);	
	});			    	
}
			    
function returnBack() {
	var fileSizeOverMessge=/* [[#{I_SLS_06000113}]] */;
	PopUpUtil.popup.showInformation(
			fileSizeOverMessge
			,function(){	
		PopUpUtil.util.doPost("/sales/v1/uq/ca/screen/00/sales-settlement-unmatch-fix/returnback", $("#base_form"), false);	
	});			    	
}

$(document).ready(function() {
	PopUpUtil.util.showMessagePopup();			    	
});