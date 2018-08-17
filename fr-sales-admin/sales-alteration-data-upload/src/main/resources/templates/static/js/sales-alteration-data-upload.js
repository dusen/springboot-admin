function selectFile(){
    document.getElementById("file").click();
}

function setfileName(){				
    var fileName = document.getElementById('file').value;
    document.getElementById('fileName').value=fileName;
}
function clickRegister() {		
    var fileFullPath = $("#fileName").val();
    if (fileFullPath == "") {
    	PopUpUtil.popup.showError($("#E_SLS_66000119").val());
    }
    var fileMaxSize= $("#filesize").val();
    if($('#file')[0].files[0].size>fileMaxSize){
    	var message;
    	if($("input[name='alterationDataType']:checked").val() == 0){
    		var data = $("#salestransactiondata").val();
    		var size = $("#salestransactionsize").val();		
    	}else{
    		var data = $("#payoffdata").val();
    		var size = $("#payoffsize").val();
    	}
    	message = $("#E_SLS_66000120").val().replace("{0}",data).replace("{1}",size);
    	PopUpUtil.popup.showError(message);
    }else{
    	PopUpUtil.popup.showInformation(	
    			$("#I_SLS_06000111").val(), 
    				function(){	
    					PopUpUtil.ajaxJson.postWithUploadFile(
    							null,	
    							'/alteration-dataup/upload',
    							callAfterRegister);	
    				}	
    		    );
    }
 }
function callAfterRegister(data){
 	if(data.detailError.messageType == "I"){
		parent.PopUpUtil.popup.showInformation(data.detailError.errorMessage);
	} else {
		parent.PopUpUtil.popup.showError(data.detailError.errorMessage);
	}
}