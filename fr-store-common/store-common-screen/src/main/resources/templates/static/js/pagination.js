//*****************************************************************************
// Pagination.constants
// ComponentUtil.browser

//*****************************************************************************

try{
	Pagination;
}catch(e){
	Pagination = {};
}

(function($pagination) {
	
	/** 
	 * ##################################################
	 * component constants define class
	 * ##################################################
	 */
	if($pagination.constants) { 
		return $pagination.constants; 
	}
	$pagination.constants = {
		TABLE_ID 				: '#dataTable',
		PAGE_LINK_INDEX 		: '#pageLinkIndex',
		PAGE_DATA_ID 			: '#pageData',
		ALL_PAGE_DATA_ID 		: '#allPageData',
		ADD_DATA_BUTTON_DIV_ID 	: '#show-more',
		MAX_PAGE_DATA_COUNT_ID 	: '#maxPageDataCount',
	}
	
	/** 
	 * ##################################################
	 * component [page] define class
	 * ##################################################
	 */
	if($pagination.page) { 
		return $pagination.page; 
	}
	$pagination.page = {
		//------------------------------------------------------------------------------
		// start show table data by pagination
		// url : action url(can not be null)
		// initSetting : do logic function before action(can be null)
		// afterCallBack : do logic function after action(can be null)
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'pageData', 
		//				'allPageData', 'show-more', 'maxPageDataCount']	
		//------------------------------------------------------------------------------
		showPage : function (url, initSetting, afterCallBack, idSetting) {
			Pagination.page._initialize(url, initSetting, afterCallBack, idSetting);
			Pagination.page.initializePage(idSetting);
			//do init setting
			if(initSetting!= null && typeof initSetting == "function") {
				initSetting();
			}
			
			if(afterCallBack!= null && typeof afterCallBack == "function") {
				Pagination.page._afterCallBack= afterCallBack;
			}
			
			PopUpUtil.ajaxHtml.post(null, url, Pagination.page._addTableData);
		},
		
		//------------------------------------------------------------------------------
		// do next show table data by pagination
		// url : action url(can not be null)
		// initSetting : do logic function before action(can be null)
		// afterCallBack : do logic function after action(can be null)
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'pageData', 
		//				'allPageData', 'show-more', 'maxPageDataCount']	
		//------------------------------------------------------------------------------
		doPagination : function (url, initSetting, afterCallBack, idSetting) {
			Pagination.page._initialize(url, initSetting, afterCallBack, idSetting);
			//do init setting
			if(initSetting!= null && typeof initSetting == "function") {
				initSetting();
			}
			
			if(afterCallBack!= null && typeof afterCallBack == "function") {
				Pagination.page._afterCallBack= afterCallBack;
			}
			
			PopUpUtil.ajaxHtml.post(null, url, Pagination.page._addTableData);
		},
		
		//------------------------------------------------------------------------------
		// do sort table data 
		// url : action url(can not be null)
		// initSetting : do logic function before action(can be null)
		// afterCallBack : do logic function after action(can be null)
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'pageData', 
		//				'allPageData', 'show-more', 'maxPageDataCount']	
		//------------------------------------------------------------------------------
		sortPage : function (url, initSetting, afterCallBack, idSetting) {
			Pagination.page._initialize(url, initSetting, afterCallBack, idSetting);
			//do init setting
			if(initSetting!= null && typeof initSetting == "function") {
				initSetting();
			}
			
			if(afterCallBack!= null && typeof afterCallBack == "function") {
				Pagination.page._afterCallBack= afterCallBack;
			}
			
			PopUpUtil.ajaxHtml.post(null, url, Pagination.page._sortTableData);
		},
		
		//------------------------------------------------------------------------------
		// initialize table data 
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'pageData', 
		//				'allPageData', 'addDataButtonDiv']	
		//------------------------------------------------------------------------------
		initializePage : function(idSetting) {
			Pagination.page._initializeId(idSetting);
			$(Pagination.page._idSetting[0]).find('tbody').find('tr').remove();
			$(Pagination.page._idSetting[1]).val(0);
			$(Pagination.page._idSetting[3]).val('');
			$(Pagination.page._idSetting[4]).hide();
			$(Pagination.page._idSetting[5]).val(0);
		},
		
		_initializeId : function(idSetting){
			if(idSetting != null && idSetting[0] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[0]);
				Pagination.page._idSetting[0] = jqueryId;
			}else{
				Pagination.page._idSetting[0] = Pagination.constants.TABLE_ID;
			}
			
			if(idSetting != null && idSetting[1] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[1]);
				Pagination.page._idSetting[1] = jqueryId;
			}else{
				Pagination.page._idSetting[1] = Pagination.constants.PAGE_LINK_INDEX;
			}
			
			if(idSetting != null && idSetting[2] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[2]);
				Pagination.page._idSetting[2] = jqueryId;
			}else{
				Pagination.page._idSetting[2] = Pagination.constants.PAGE_DATA_ID;
			}

			if(idSetting != null && idSetting[3] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[3]);
				Pagination.page._idSetting[3] = jqueryId;
			}else{
				Pagination.page._idSetting[3] = Pagination.constants.ALL_PAGE_DATA_ID;
			}
			
			if(idSetting != null && idSetting[4] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[4]);
				Pagination.page._idSetting[4] = jqueryId;
			}else{
				Pagination.page._idSetting[4] = Pagination.constants.ADD_DATA_BUTTON_DIV_ID;
			}
			
			if(idSetting != null && idSetting[5] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[5]);
				Pagination.page._idSetting[5] = jqueryId;
			}else{
				Pagination.page._idSetting[5] = Pagination.constants.MAX_PAGE_DATA_COUNT_ID;
			}
		},
		
		_initialize : function(url, initSetting, afterCallBack, idSetting){
			Pagination.page._initializeId(idSetting);

			Pagination.page._url = url;
			
			if(initSetting == null){
				Pagination.page._initSetting = function(){};
			}else{
				Pagination.page._initSetting = initSetting;
			}
			
			if(afterCallBack == null){
				Pagination.page._afterCallBack = function(){};
			}else{
				Pagination.page._afterCallBack = afterCallBack;
			}
		},
		
		_addTableData : function(data){
			var root = $(Pagination.page._idSetting[0]).find('tbody');
			var addData = $(data).find(Pagination.page._idSetting[0]).find('tbody').find('tr');
			if(addData.length == 1){
				addData.appendTo(root);
			}else if(addData.length > 1) {
				addData.each(function(i, elem) {
					$(elem).appendTo(root);
				});
			}
			
			$(Pagination.page._idSetting[1]).val(root.find('tr').length);
			Pagination.page._keepPageData($(data).find(Pagination.page._idSetting[2]).val());
			if(parseInt($(Pagination.page._idSetting[1]).val()) > 0 && 
					parseInt($(Pagination.page._idSetting[1]).val()) < 
					parseInt($(data).find(Pagination.page._idSetting[5]).val())){
				$(Pagination.page._idSetting[4]).show();
				$(Pagination.page._idSetting[4]).find('tr').find('td')
					.find('a').unbind().on('click', function(){
					Pagination.page.doPagination(
							$(this).attr('data-url'),
							Pagination.page._initSetting,
							Pagination.page._afterCallBack,
							Pagination.page._idSetting
					);
				});
			}else{
				$(Pagination.page._idSetting[4]).hide();
			}
			Pagination.page._afterCallBack(data);
			ComponentUtil.table.initialize();
		},
		
		_sortTableData : function (data) {
			Pagination.page.initializePage();
			Pagination.page._addTableData(data);
		},
		
		_keepPageData : function(data){
			var allPageData = [];
			var allPageDataString = $(Pagination.page._idSetting[3]).val();
			if(allPageDataString != null && allPageDataString != ''){
				allPageData = JSON.parse(allPageDataString);
			}
			
			if(data != null && data != ''){
				var jsonList = JSON.parse(data);
				for(i=0; i<jsonList.length; i++){
					allPageData.push(jsonList[i]);
				}
			}
			$(Pagination.page._idSetting[3]).val(JSON.stringify(allPageData));
		},
		
		_idSetting : [],
		
		_url : '',
		
		_initSetting : function () {},
		
		_afterCallBack : function () {},
		
	}
	
	/** 
	 * ##################################################
	 * component [justPaging] define class
	 * ##################################################
	 */
	if($pagination.justPaging) { 
		return $pagination.justPaging; 
	}
	$pagination.justPaging = {
		//------------------------------------------------------------------------------
		// start show table data by pagination
		// url : action url(can not be null)
		// initSetting : do logic function before action(can be null)
		// afterCallBack : do logic function after action(can be null)
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'show-more', 
		//             'maxPageDataCount']
		//------------------------------------------------------------------------------
		showPage : function (url, initSetting, afterCallBack, idSetting) {
			Pagination.justPaging._initialize(url, initSetting, afterCallBack, idSetting);
			Pagination.justPaging.initializePage(idSetting);
			//do initial setting
			if(initSetting!= null && typeof initSetting == "function") {
				initSetting();
			}
			
			if(afterCallBack!= null && typeof afterCallBack == "function") {
				Pagination.justPaging._afterCallBack= afterCallBack;
			}
			
			PopUpUtil.ajaxHtml.post(null, url, Pagination.justPaging._addTableData);
		},
		
		//------------------------------------------------------------------------------
		// do next show table data by pagination
		// url : action url(can not be null)
		// initSetting : do logic function before action(can be null)
		// afterCallBack : do logic function after action(can be null)
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'show-more', 
		//              'maxPageDataCount']	
		//------------------------------------------------------------------------------
		doPagination : function (url, initSetting, afterCallBack, idSetting) {
			Pagination.justPaging._initialize(url, initSetting, afterCallBack, idSetting);
			//do initial setting
			if(initSetting!= null && typeof initSetting == "function") {
				initSetting();
			}
			
			if(afterCallBack!= null && typeof afterCallBack == "function") {
				Pagination.justPaging._afterCallBack= afterCallBack;
			}
			
			PopUpUtil.ajaxHtml.post(null, url, Pagination.justPaging._addTableData);
		},
		
		//------------------------------------------------------------------------------
		// initialize table data 
		// idSetting : target component id list(can be null with default id)
		//             default id: ['dataTable', 'pageLinkIndex', 'addDataButtonDiv',
		//              'maxPageDataCount']	
		//------------------------------------------------------------------------------
		initializePage : function(idSetting) {
			Pagination.justPaging._initializeId(idSetting);
			$(Pagination.justPaging._idSetting[0]).find('tbody').find('tr').remove();
			$(Pagination.justPaging._idSetting[1]).val(0);
			$(Pagination.justPaging._idSetting[2]).hide();
			$(Pagination.justPaging._idSetting[3]).val(0);
		},
		
		_initializeId : function(idSetting){
			if(idSetting != null && idSetting[0] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[0]);
				Pagination.justPaging._idSetting[0] = jqueryId;
			}else{
				Pagination.justPaging._idSetting[0] = Pagination.constants.TABLE_ID;
			}
			
			if(idSetting != null && idSetting[1] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[1]);
				Pagination.justPaging._idSetting[1] = jqueryId;
			}else{
				Pagination.justPaging._idSetting[1] = Pagination.constants.PAGE_LINK_INDEX;
			}
			
			if(idSetting != null && idSetting[2] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[2]);
				Pagination.justPaging._idSetting[2] = jqueryId;
			}else{
				Pagination.justPaging._idSetting[2] = Pagination.constants.ADD_DATA_BUTTON_DIV_ID;
			}
			
			if(idSetting != null && idSetting[3] != null){
				var jqueryId = PopUpUtil.util.convertToJqueryId(idSetting[3]);
				Pagination.justPaging._idSetting[3] = jqueryId;
			}else{
				Pagination.justPaging._idSetting[3] = Pagination.constants.MAX_PAGE_DATA_COUNT_ID;
			}
		},
		
		_initialize : function(url, initSetting, afterCallBack, idSetting){
			Pagination.justPaging._initializeId(idSetting);

			Pagination.justPaging._url = url;
			
			if(initSetting == null){
				Pagination.justPaging._initSetting = function(){};
			}else{
				Pagination.justPaging._initSetting = initSetting;
			}
			
			if(afterCallBack == null){
				Pagination.justPaging._afterCallBack = function(){};
			}else{
				Pagination.justPaging._afterCallBack = afterCallBack;
			}
		},
		
		_addTableData : function(data){
			var root = $(Pagination.justPaging._idSetting[0]).find('tbody');
			var addData = $(data).find(Pagination.justPaging._idSetting[0]).find('tbody').find('tr');
			if(addData.length == 1){
				addData.appendTo(root);
			}else if(addData.length > 1) {
				addData.each(function(i, elem) {
					$(elem).appendTo(root);
				});
			}
			
			$(Pagination.justPaging._idSetting[1]).val(root.find('tr').length);
			if(parseInt($(Pagination.justPaging._idSetting[1]).val()) > 0 && 
					parseInt($(Pagination.justPaging._idSetting[1]).val()) < 
					parseInt($(data).find(Pagination.justPaging._idSetting[3]).val())){
				$(Pagination.justPaging._idSetting[2]).show();
				$(Pagination.justPaging._idSetting[2]).find('tr').find('td')
					.find('a').unbind().on('click', function(){
					Pagination.justPaging.doPagination(
							$(this).attr('data-url'),
							Pagination.justPaging._initSetting,
							Pagination.justPaging._afterCallBack,
							Pagination.justPaging._idSetting
					);
				});
			}else{
				$(Pagination.justPaging._idSetting[2]).hide();
			}
			Pagination.justPaging._afterCallBack(data);
			ComponentUtil.table.initialize();
		},
		
		_idSetting : [],
		
		_url : '',
		
		_initSetting : function () {},
		
		_afterCallBack : function () {},
		
	}
	
	/** 
	 * ##################################################
	 * component [pageLinked] define class
	 * ##################################################
	 */
	if($pagination.pageLinked) { 
		return $pagination.pageLinked; 
	}
	$pagination.pageLinked = {
		initialize : function (pageLinkIdPrefix, url, initSetting){
			var linkObject;
			linkObject = $('[id^='+pageLinkIdPrefix+']');
			linkObject.each(function(i, elem) {
				$(elem).on('click', function(event) {
					event.preventDefault();
					if(initSetting != null){
						initSetting();
					}
					$('#'+pageLinkIdPrefix+'Index').val(
							Pagination.pageLinked._setLinkedPageIndex($(elem).attr('id'), pageLinkIdPrefix));
					PopUpUtil.util.doTransition($(elem).attr('id'), url); 
				});
				
			});
		},
		_setLinkedPageIndex : function(linkedPageId, pageLinkIdPrefix){
			if(linkedPageId == (pageLinkIdPrefix+'Prev')){
				return parseInt($('#'+pageLinkIdPrefix+'Index').val()) - 1;
			}else if(linkedPageId == (pageLinkIdPrefix+'Next')){
				return parseInt($('#'+pageLinkIdPrefix+'Index').val()) + 1;
			}else{
				return parseInt(linkedPageId.replace( pageLinkIdPrefix, '' ))-1;
			}
		}
	}
})(Pagination);

