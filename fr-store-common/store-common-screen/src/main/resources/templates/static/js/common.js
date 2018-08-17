//*****************************************************************************
// ComponentUtil.constants
// ComponentUtil.browser
// ComponentUtil.window
// ComponentUtil.util
// ComponentUtil.a
// ComponentUtil.navimenu
// ComponentUtil.table
// ComponentUtil.selectBox
// ComponentUtil.checkBox
// ComponentUtil.model
// ComponentUtil.trigger
// ComponentUtil.datepicker
// ComponentUtil.tooltip
// ComponentUtil.tab
// ComponentUtil.form
// --------------------------------
// jQuery(function($){});  initialize when base page loading
//*****************************************************************************

try{
	ComponentUtil;
}catch(e){
	ComponentUtil = {};
}

(function($componentUtil) {
	
	/** 
	 * ##################################################
	 * component constants define class
	 * ##################################################
	 */
	if($componentUtil.constants) { 
		return $componentUtil.constants; 
	}
	$componentUtil.constants = {
		//root path
		ROOT_PATH : '',
		
		DATEPICKER_BUTTON_IMAGE_SRC : '',
		
		//------------------------------------------------------------------------------
		// initialize [trigger] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			if($('#urlBasePath').val() != undefined){
				ComponentUtil.constants.ROOT_PATH = $('#urlBasePath').val();
			}
			
			if($('#datepicker_button_image_src').val() != undefined){
				ComponentUtil.constants.DATEPICKER_BUTTON_IMAGE_SRC = 
					$('#datepicker_button_image_src').val();
			}
		}
	}
	
	/** 
	 * ##################################################
	 * component [util] define class
	 * ##################################################
	 */
	if($componentUtil.util) { 
		return $componentUtil.util; 
	}
	$componentUtil.util = {
		/*
		 * Get locale date. 
		 */
		getLocaleDate : function () {
			var date = new Date();
			return date.toLocaleDateString();
		},

		/*
		 * Get locale time. 
		 */
		getLocaleTime : function () {
			var date = new Date();
			return date.toLocaleTimeString();
		},

		/*
		 * Get locale date time. 
		 */
		getLocaleDateTime : function () {
			var date = new Date();
			return date.toLocaleString();
		},

		/*
		 * Set locale. 
		 */
		setLocale : function (locale) {
			$("#specifyLocale").val(locale);
		},

		/*
		 * Set locale and submit. 
		 */
		setLocaleSubmit : function (locale) {
			$("#specifyLocale").val(locale);
			document.forms[0].submit();
		},
		
		setElementValue : function (obj, val){
			$('#'+obj).val(val);
		},
		
		parseToJsonObject : function (jsonString) {
			if(jsonString != null && jsonString != ''){
				return JSON.parse(jsonString);
			} else {
				return jsonString;
			}
		},
	}
	
	
	/** 
	 * ##################################################
	 * component [browser] define class
	 * ##################################################
	 */
	if($componentUtil.browser) { 
		return $componentUtil.browser; 
	}
	$componentUtil.browser = {
		//------------------------------------------------------------------------------
		// judge if browser is Chrome
		//------------------------------------------------------------------------------	
		isChrome : function () {
			var userAgent = navigator.userAgent.toLowerCase();
			return (userAgent.indexOf('chrome') > -1) && (userAgent.indexOf('edge') == -1);;
		},
		
		//------------------------------------------------------------------------------
		// judge if browser is Firefox
		//------------------------------------------------------------------------------	
		isFirefox : function () {
			var userAgent = navigator.userAgent.toLowerCase();
			return (userAgent.indexOf('firefox') > -1);
		},
		
		//------------------------------------------------------------------------------
		// judge if browser is Edge
		//------------------------------------------------------------------------------	
		isEdge : function () {
			var userAgent = navigator.userAgent.toLowerCase();
			return (userAgent.indexOf('edge') > -1);
		},
		
		//------------------------------------------------------------------------------
		// judge if browser is Safari
		//------------------------------------------------------------------------------	
		isSafari : function () {
			var userAgent = navigator.userAgent.toLowerCase();
			return (userAgent.indexOf("safari") > -1) && (userAgent.indexOf("chrome") == -1);
		},
		
		//------------------------------------------------------------------------------
		// judge if browser is IE
		//------------------------------------------------------------------------------	
		isIE : function () {
			var userAgent = navigator.userAgent.toLowerCase();
			return ((userAgent.indexOf("compatible") > -1) && (userAgent.indexOf("msie") > -1)) 
				|| (userAgent.indexOf("trident") > -1) ;
		},
		
		//------------------------------------------------------------------------------
		// judge if browser is iPad
		//------------------------------------------------------------------------------	
		isiPad : function () {
			var ua = navigator.userAgent.toLowerCase();
			return (ua.indexOf('ipad') > -1);
		},
		
		getBrowserType : function(){
			if (ComponentUtil.browser.isSafari()){
				return "Safari";
			} if (ComponentUtil.browser.isChrome()) {
				return "Chrome";
			} if (ComponentUtil.browser.isFirefox()) {
				return "Firefox";
			} if (ComponentUtil.browser.isEdge()) {
				return "Edge";
			} if (ComponentUtil.browser.isIE()) {
				return "IE";
			} else {
				return "Other Browser";
			}
		},
		
		getOSType : function () {
			if (/(iphone|ipad|ipod|ios)/i.test(navigator.userAgent.toLowerCase())) {
				return "IOS";
			 } else if (/(android)/i.test(navigator.userAgent.toLowerCase())) {
				return "Android";
			 } else {
				return "PC";
			 }
		},
	}
	
	/** 
	 * ##################################################
	 * component [window] define class
	 * ##################################################
	 */
	if($componentUtil.window) { 
		return $componentUtil.window; 
	}
	$componentUtil.window = {
		scrollInit : function (){
			$(window).on('scroll', function(){
				$(".header").css("left", -$(window).scrollLeft());
			});
		},
		scrollBlockSetting : function() {
			$(".scroll-block").each(function(){
				var childrenHeight = $(this).children().height();
				if ($("#show-more")[0]) {
					if($("#show-more").css('display') != 'none') {
						childrenHeight += $("#show-more").height();
					}
				}
				if ($(this).height() < childrenHeight) {
					$(this).parents(".th-fixed").addClass("scroll");
					$(this).addClass("scroll");
				} else {
					$(this).parents(".th-fixed").removeClass("scroll");
					$(this).removeClass("scroll");
				}
			});
		},
		
		numerickeyReset : function() {
			$("input").removeClass("focus");
			$(".numeric-block").remove();
		},
	}
	
	
	/** 
	 * ##################################################
	 * component [document] define class
	 * ##################################################
	 */
	if($componentUtil.document) { 
		return $componentUtil.document; 
	}
	$componentUtil.document = {
		
		//------------------------------------------------------------------------------
		// get documentElements of loading page
		//------------------------------------------------------------------------------	
		scrollingElement : function() {
			if (document.scrollingElement) {
				return document.scrollingElement;
			} else if (navigator.userAgent.indexOf('WebKit') != -1) {
				return 'body';
			} else {
				return document.documentElement;
			}
		},
	}
	
	/** 
	 * ##################################################
	 * component [a] define class
	 * ##################################################
	 */
	if($componentUtil.a) { 
		return $componentUtil.a; 
	}
	$componentUtil.a = {
		
		//------------------------------------------------------------------------------
		// initialize [a] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			$('a[href^="#"]').not('a[href="#"]').click(function() {
				var hash = this.hash;
				if(!hash || hash == "#")
						return false;
				$(ComponentUtil.document.scrollingElement()).animate(
						{scrollTop: $(hash).offset().top}, 500, "swing");
				return false;
			});
		},
		
		disable : function(buttonId) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if(!$(jqueryId).hasClass('disabled')) {
				$(jqueryId).addClass('disabled');
			}
		},
		
		enable : function(buttonId) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(buttonId);
			if($(jqueryId).hasClass('disabled')) {
				$(jqueryId).removeClass('disabled');
			}
		}
	}
	
	/** 
	 * ##################################################
	 * component [navimenu] define class
	 * ##################################################
	 */
	if($componentUtil.navimenu) { 
		return $componentUtil.navimenu; 
	}
	$componentUtil.navimenu = {
		
		//------------------------------------------------------------------------------
		// initialize [navi button] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			var scrollpos = $(window).scrollTop();
			
			$('.header #sp-nav-toggle-btn').unbind().on('click', function(event) {
				event.preventDefault();
				if (!$(this).hasClass("open")) {
					$('body').addClass('fixed').css({'top': -scrollpos});
					$('.header #global-navi').slideDown(300);
					$('.header #global-navi-bg').fadeIn(300);
					$(this).addClass("open");
					$('.header #global-navi').addClass("open");
					$('.header #global-navi-bg').addClass("visible");
				} else {
					$('body').removeClass('fixed').css({'top': 0});
					window.scrollTo( 0 , scrollpos );
					$('.header #global-navi').slideUp(300);
					$('.header #global-navi-bg').fadeOut(300, function(){
						$('.header #global-navi-bg').removeClass("visible");
					});
					$(this).removeClass("open");
					$('.header #global-navi').removeClass("open");
				}
				return false;
			});
			
			$('.header #global-navi .has-child .sp-open-btn').unbind().on('click', function(event) {
				event.preventDefault();
				if (!$(this).hasClass("open")) {
					$('.header #global-navi .has-child .sp-open-btn').removeClass("open");
					$('.sub-navi').slideUp(100);
					$(this).next('.sub-navi').slideDown(100);
					$(this).addClass("open");
				} else {
					$('body').removeClass('fixed').css({'top': 0});
					$(this).next('.sub-navi').slideUp(100);
					$(this).removeClass("open");
				}
				return false;
			});
		},
		
		closeMenu : function() {
			$('body').removeClass('fixed').css({'top': 0});
			window.scrollTo( 0 , scrollpos );
			$('.header #global-navi').slideUp(300);
			$('.header #global-navi-bg').fadeOut(300, function(){
				$('.header #global-navi-bg').removeClass("visible");
			});
			$('.header #sp-nav-toggle-btn').removeClass("open");
			$('.header #global-navi').removeClass("open");
		},
	}
	
	/** 
	 * ##################################################
	 * component [table] define class
	 * ##################################################
	 */
	if($componentUtil.table) { 
		return $componentUtil.table; 
	}
	$componentUtil.table = {
		
		//------------------------------------------------------------------------------
		// initialize [table] automatically
		//------------------------------------------------------------------------------	
		initialize : function() {
			ComponentUtil.table.initializeCell();
			ComponentUtil.table.initCheckBoxGroupInTd();
			ComponentUtil.table.initSortIcon();
		},
		//------------------------------------------------------------------------------
		// initialize [table cell] automatically
		//------------------------------------------------------------------------------	
		initializeCell : function() {
			//flexibleTable
			if ($(".js-flexible-table").length > 0) {
				$headFixed = $(".flexible-table-wrap.scroll-block-head .flexible-table-title");
				$headScrolled = $(".flexible-table-wrap.scroll-block-head .flexible-table-cont");
				$bodyFixed = $(".flexible-table-wrap.scroll-block-body .flexible-table-title");
				$bodyScrolled = $(".flexible-table-wrap.scroll-block-body .flexible-table-cont");
				
				$(".scroll-block").on("scroll", function(){
					$bodyFixed.find("table").css("top", $(this).find("table").position().top);
					$headScrolled.find("table").css("left", $(this).find("table").position().left);
				});
				
				var fixedHeight = $(".header").height() + $("#breadcrumbs").height() + $(".pagination").height() + $(".footer").height() + 54;
				$(window).on("resize", function(){
					var h = $(window).height() - $(".flexible-table-wrap.scroll-block-head").height() - fixedHeight;
					
					$(".v-hheight").height(h);
					
					$headFixed.find("table tr:eq(0) th").height($headScrolled.find("table tr:eq(0) th:eq(0)").height());
					$headFixed.find("table tr:eq(1) th:eq(0)").height($headScrolled.find("table tr:eq(1) th").height());
					
					ComponentUtil.table.cellHeightSet();
					ComponentUtil.table.cellWidthSet();
				});
				$(window).resize();
			}
			
			//flexibleTable
			if ($(".th-fixed").length > 0) {
				$(".th-fixed").each(function() {
					var $wrap = $(this);
					var fixedHeight;
					
					$(window).on("resize", function(){
						if($wrap.parents().hasClass("modal")) {
							fixedHeight = $(window).height()* 0.4;
						} else {
							var optionsHeight = 0;
							$(".option-block").each(function(){
								optionsHeight = optionsHeight + $(this).height();
							});
							fixedHeight = $(".header").height() + $("#breadcrumbs").height() + optionsHeight + $(".pagination").height() + $(".footer").height() + 80;
						}
						var h = $(window).height() - $wrap.find(".th-fixed-head").height() - fixedHeight;

						$wrap.find(".v-hheight").height(h);
					});
					//cellWidthSet($wrap.find(".th-fixed-head"), $wrap.find(".th-fixed-body"));
					$(window).resize();

					$wrap.find(".scroll-block").on("scroll", function(){
						var scrollLeft = $(this).scrollLeft();
						$wrap.find(".th-fixed-head table").css({"margin-left": -scrollLeft});
					});
				});
			}
			if ($(".scroll-block").length > 0) {
				ComponentUtil.window.scrollBlockSetting();
				
				$(window).on("resize", function(){
					ComponentUtil.window.scrollBlockSetting();
				});
			}
		},
			
		//------------------------------------------------------------------------------
		// set table's cell width automatically
		//------------------------------------------------------------------------------	
		adjustCellWidthOnClick : function() {
			$("[data-cell-elem]").unbind().on("click", function(event){
				event.preventDefault();
				var elem = $(this).data("cell-elem");
				$("table [data-cell-elem='" + elem + "']").toggle();
				ComponentUtil.table.cellWidthSet($(".th-fixed-head"), $(".th-fixed-body"));
			});
		},
		//------------------------------------------------------------------------------
		// set table's cell width automatically
		//------------------------------------------------------------------------------	
		cellWidthSet : function($headRow, $bodyRow) {
			$headRow.find("th").css("width", "");
			$headRow.find("td").css("width", "");
			$bodyRow.find("th").css("width", "");
			$bodyRow.find("td").css("width", "");
			
			var headRowArr = [];
			var bodyRowArr = [];
			$headRow.find("tr:eq(0) > *").each(function(){
				if ($(this).attr("colspan")) {
					var id = $(this).data("colspan");
					$headRow.find("tr:eq(1) [data-colspan-row=" + id + "]").each(function(){
						headRowArr.push($(this));
					});
				} else {
					headRowArr.push($(this));
				}
			});
			$bodyRow.find("tr:eq(0) > *").each(function(){
				bodyRowArr.push($(this));
			});
			var headLength = headRowArr.length;
			var bodyLength = bodyRowArr.length;
			
			if (headLength == bodyLength) {
				$.each(bodyRowArr, function(index){
					var $thisTd = this;
					var $scopeTh = headRowArr[index];
					var w;
					if (Math.ceil($thisTd.width()) <= Math.ceil($scopeTh.width())) {
						w = $scopeTh.width() + 24;

						if (ComponentUtil.browser.isFirefox() || 
								ComponentUtil.browser.isEdge()) w = Math.ceil(w);
						
						//if (isFirefox || isEdge) $scopeTh.css("max-width", w);
					} else {
						w = $thisTd.width() + 24;
						if (ComponentUtil.browser.isFirefox() || 
								ComponentUtil.browser.isEdge()) w = Math.ceil(w);
							
					}
					
					$thisTd.css("min-width", w);
					$scopeTh.css("min-width", w);
					$thisTd.css("max-width", w);
					$scopeTh.css("max-width", w);
				});
			}
		},
		
		//------------------------------------------------------------------------------
		// set table's cell height automatically
		//------------------------------------------------------------------------------	
		cellHeightSet : function() {
			var $bodyFixedTr = $bodyFixed.find("tr");
			$bodyFixed.find("th").css("height", "");
			$bodyFixed.find("td").css("height", "");
			$bodyScrolled.find("th").css("height", "");
			$bodyScrolled.find("td").css("height", "");
			
			$bodyFixedTr.each(function(index){
				var $this = $(this);
				var $thisTh = $this.find("th:eq(0)");
				if ($(".js-flexible-table").hasClass("flexible-pattern02")) $thisTh = $this.find("td:eq(0)");
				var $scopeTh = $bodyScrolled.find("table tr:eq(" + index + ") td:eq(0)");
				
				if (Math.ceil($thisTh.height()) <= Math.ceil($scopeTh.height())) {
					var h = $scopeTh.height();
					if (ComponentUtil.browser.isFirefox() || 
							ComponentUtil.browser.isEdge()) h = Math.ceil(h);
					$this.find("th").height(h);
				} else {
					var h = $thisTh.height();
					if (ComponentUtil.browser.isFirefox() || 
							ComponentUtil.browser.isEdge()) h = Math.ceil(h);
					$bodyScrolled.find("table tr:eq(" + index + ") td").height(h);
				}
			});
		},
		
		//------------------------------------------------------------------------------
		// initialize [checkbox in td] element
		//------------------------------------------------------------------------------	
		initCheckBoxGroupInTd : function() {
			$("table tr td").unbind().on("click", function(event){
				var $this = $(this);
				var $parents = $this.parents("table");
				if($parents.hasClass("cell-color-change")) {
					if (!$parents.hasClass("multiple")) $parents.find("tr").not($this.parents("tr")).removeClass("color-change");
					var $elem = $this.parent("tr").find("td input[type=checkbox]");
					$this.parents("tr").toggleClass("color-change");
					$elem.prop("checked", !$elem.prop("checked"));
					// Processing of checkbox interlocking activation control
					var checkboxGroup = $elem.attr('checkbox-group');
					if(checkboxGroup) {
						var target = $('a.active-with-checkbox[checkbox-group="' + checkboxGroup + '"]');
						if ($(':checkbox[checkbox-group="' + checkboxGroup + '"]:checked').length === 0) {
							// If none are checked inactive
							target.attr('href', '#');
							target.addClass('disabled');
						} else {
							// Activate if at least one is checked
							target.attr('href', target.attr('original-href'));
							target.removeClass('disabled');
						}
					}
				}
			});
		},
		
		//------------------------------------------------------------------------------
		// initialize [sort icon] element
		//------------------------------------------------------------------------------	
		initSortIcon : function() {
			$(".sort-icon").unbind().on("click", function(event){
				event.preventDefault();
				$(this).toggleClass("ascend");
				return false;
			});
		},
	}
	
	/** 
	 * ##################################################
	 * component [selectBox] define class
	 * ##################################################
	 */
	if($componentUtil.selectBox) { 
		return $componentUtil.selectBox; 
	}
	$componentUtil.selectBox = {
		//------------------------------------------------------------------------------
		// initialize [selectBox] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			if ($(".selectbox").length > 0) {
				$(".selectbox").each(function(e){
					var $elem = $(this);
					
					$elem.on("click", function(e){
						if ($elem.find(".select-elem").hasClass("active")) {
							$(".select-elem").removeClass("active");

						} else {
							var t = $elem.offset().top;
							var maxHeight = $(window).height() - t - 50;
							$elem.find(".select-elem").css("max-height", maxHeight);
							$(".select-elem").removeClass("active");
							$elem.find(".select-elem").addClass("active");
						}
						ComponentUtil.window.numerickeyReset();
						e.stopPropagation();
					});
					$("#root, .select-elem li").not(".select-elem").on("click", function(){
						$elem.find(".select-elem").removeClass("active");
					});
					$elem.find(".select-elem li").on("click", function(e){
						$elem.find(".select-elem li").removeClass("selected");
						$(this).addClass("selected", false);
						var selectedLabel;
						selectedLabel = $(this).attr("data-name");
						var selectedValue;
						selectedValue = $(this).attr("data-value");
						
						var oldValue = $elem.find("input[type=hidden]").val();
						
						if(selectedValue == '-1'){
							$elem.find("input[type=hidden]").val('');
							if (oldValue == '') {
								$elem.find("input[type=text]").val('');
							} else {
								$elem.find("input[type=text]").val('').change();
							}
						} else{
							$elem.find("input[type=hidden]").val(
									selectedValue);
							if (oldValue == selectedValue) {
								$elem.find("input[type=text]").val(
										selectedLabel);
							} else {
								$elem.find("input[type=text]").val(
										selectedLabel).change();
							}
						}
						$elem.find(".select-elem").removeClass("active");
						
						if ($elem.hasClass("modal-trigger"))
							ComponentUtil.model.modalOpen($elem.data("open-modal"));
						
						// Processing of checkbox interlocking activation control
						var checkboxGroup = $elem.attr('checkbox-group');
						if(checkboxGroup) {
							var target = $('a.active-with-checkbox[checkbox-group="' + checkboxGroup + '"]');
							// Activate when value is set
							target.attr('href', target.attr('original-href'));
							target.removeClass('disabled');
						}
						e.stopPropagation();
					});
					
					var intSelectedLabel = $elem.find("input[type=text]").val();
					if (intSelectedLabel != "") {
						$elem.find(".select-elem li").removeClass("selected");
						$elem.find(".select-elem li").each(function(){
							if ($(this).attr("data-name") == intSelectedLabel) {
								$(this).addClass("selected", false);
								$elem.find("input[type=hidden]").val($(this).attr("data-value"));
							}
						});
					} else {
						var intSelectedValue = $elem.find("input[type=hidden]").val();
						if (intSelectedValue != "") {
							$elem.find(".select-elem li").removeClass("selected");
							$elem.find(".select-elem li").each(function(){
								if ($(this).attr("data-value") == intSelectedValue) {
									$(this).addClass("selected", false);
									$elem.find("input[type=text]").val($(this).text());
								}
							});
						}
					}
				});
			}
		},
		
		initializeWithId : function(selectBoxId) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(selectBoxId);
			if ($(jqueryId+" .selectbox").length > 0) {
				$(jqueryId+" .selectbox").each(function(e){
					var $elem = $(this);
					
					$elem.on("click", function(e){
						if ($elem.find(".select-elem").hasClass("active")) {
							$(".select-elem").removeClass("active");

						} else {
							var t = $elem.offset().top;
							var maxHeight = $(window).height() - t - 50;
							$elem.find(".select-elem").css("max-height", maxHeight);
							$(".select-elem").removeClass("active");
							$elem.find(".select-elem").addClass("active");
						}
						ComponentUtil.window.numerickeyReset();
						e.stopPropagation();
					});
					$(jqueryId+" .select-elem li").not(".select-elem").on("click", function(){
						$elem.find(".select-elem").removeClass("active");
					});
					$elem.find(".select-elem li").on("click", function(e){
						$elem.find(".select-elem li").removeClass("selected");
						$(this).addClass("selected", false);
						var selectedLabel;
						selectedLabel = $(this).attr("data-name");
						var selectedValue;
						selectedValue = $(this).attr("data-value");
						
						if(selectedValue == '-1'){
							$elem.find("input[type=text]").val('');
							$elem.find("input[type=hidden]").val('');

						} else{					
							$elem.find("input[type=text]").val(
									selectedLabel);
							$elem.find("input[type=hidden]").val(
									selectedValue);
						}
						$elem.find(".select-elem").removeClass("active");
						
						if ($elem.hasClass("modal-trigger"))
							ComponentUtil.model.modalOpen($elem.data("open-modal"));
						
						// Processing of checkbox interlocking activation control
						var checkboxGroup = $elem.attr('checkbox-group');
						if(checkboxGroup) {
							var target = $('a.active-with-checkbox[checkbox-group="' + checkboxGroup + '"]');
							// Activate when value is set
							target.attr('href', target.attr('original-href'));
							target.removeClass('disabled');
						}
						e.stopPropagation();
					});
					
					var intSelectedLabel = $elem.find("input[type=text]").val();
					if (intSelectedLabel != "") {
						$elem.find(".select-elem li").removeClass("selected");
						$elem.find(".select-elem li").each(function(){
							if ($(this).attr("data-name") == intSelectedLabel) {
								$(this).addClass("selected", false);
								$elem.find("input[type=hidden]").val($(this).attr("data-value"));
							}
						});
					} else {
						var intSelectedValue = $elem.find("input[type=hidden]").val();
						if (intSelectedValue != "") {
							$elem.find(".select-elem li").removeClass("selected");
							$elem.find(".select-elem li").each(function(){
								if ($(this).attr("data-value") == intSelectedValue) {
									$(this).addClass("selected", false);
									$elem.find("input[type=text]").val($(this).text());
								}
							});
						}
					}
				});
			}
		},
	}
	
	/** 
	 * ##################################################
	 * component [checkBox] define class
	 * ##################################################
	 */
	if($componentUtil.checkBox) { 
		return $componentUtil.checkBox; 
	}
	$componentUtil.checkBox = {
		//------------------------------------------------------------------------------
		// initialize [checkBox] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			$(".cell-color-change input[type=checkbox]").on("change", function(){
				$(this).parents("tr").toggleClass("color-change");
			});
			

			$("input[data-select-group]").on("click", function(){
				$("input[data-select-group=" + $(this).attr("data-select-group") + "]").each(function(){
					$("div[data-select-id*=" + $(this).attr("data-select-for") + "]").hide();
				});
				$("div[data-select-id*=" + $(this).attr("data-select-for") + "]").css({"display":"flex"});
			});
			
			// Set button activation / inactivation processing linked to check box
			$('a.active-with-checkbox[checkbox-group]').each(function(i,e){
				var target = $(e);
				var checkboxGroup = target.attr('checkbox-group');

				target.attr('original-href', target.attr('href'));

				// Default deactivation if no checks are included
				if ($(':checkbox[checkbox-group="' + checkboxGroup + '"]:checked').length === 0) {
					target.attr('href', '#');
					target.addClass('disabled');
				}

				// Add event to checkbox with checkbox-group of same name
				$(document).on('change', ':checkbox[checkbox-group="' + checkboxGroup + '"]', function() {
					if ($(':checkbox[checkbox-group="' + checkboxGroup + '"]:checked').length === 0) {
						// If none are checked inactive
						target.attr('href', '#');
						target.addClass('disabled');
					} else {
						// Activate if at least one is checked
						target.attr('href', target.attr('original-href'));
						target.removeClass('disabled');
					}
				});
			});
		},
	}
	
	/** 
	 * ##################################################
	 * component [model] define class
	 * ##################################################
	 */
	if($componentUtil.model) { 
		return $componentUtil.model; 
	}
	$componentUtil.model = {
		//------------------------------------------------------------------------------
		// initialize [model] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			$(".modal-close").on("click", function(){
				ComponentUtil.model.modalClose();
				return false;
			});
		},
			
		modalOpen : function(id) {
			$(".modal").hide();
			$(".modal#" + id).show();
			$("#modal-wrapper").addClass("active");
		},
		
		modalClose : function() {
			ComponentUtil.window.numerickeyReset();
			$("#modal-wrapper").addClass("move");
			setTimeout(function(){
				$("#modal-wrapper").removeClass("move active");
			}, 200);
		},
	}
	
	/** 
	 * ##################################################
	 * component [trigger] define class
	 * ##################################################
	 */
	if($componentUtil.trigger) { 
		return $componentUtil.trigger; 
	}
	$componentUtil.trigger = {
		//------------------------------------------------------------------------------
		// initialize [trigger] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			$(".toggle-trigger").on("click", function(){
				var target = $(this).data("toggle-for");
				$("[data-toggle-id=" + target + "]").toggle();
				$(window).resize();
				return false;
			});
			
			$(".modal-trigger").not(".selectbox").on("click", function(){
				ComponentUtil.model.modalOpen($(this).data("open-modal"));
				return false;
			});
			
			$(".input-trigger").on("change keyup", function(){
				var target = $(this).data("toggle-for");
				if($(this).val() != "") {
					$("[data-toggle-id=" + target + "]").show();
				} else {
					$("[data-toggle-id=" + target + "]").hide();
				}
				$(window).resize();
			});	
		},
		
	}
	
	/** 
	 * ##################################################
	 * component [datepicker] define class
	 * ##################################################
	 */
	if($componentUtil.datepicker) { 
		return $componentUtil.datepicker; 
	}
	$componentUtil.datepicker = {
		//------------------------------------------------------------------------------
		// initialize [datepicker] element
		//------------------------------------------------------------------------------	
		initialize : function() {
			$('.datepicker-text').each(function(){
				// set datepicker display type.
				$(this).datepicker({showOn: "both",
					buttonImageOnly: true,
					buttonImage: ComponentUtil.constants.DATEPICKER_BUTTON_IMAGE_SRC,
					beforeShow: function() {
						setTimeout(function(){
							$('.ui-datepicker').css('z-index', 99999999999999);
							}, 0);
					}
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
	}
	
	/** 
	 * ##################################################
	 * component [tooltip] define class
	 * ##################################################
	 */
	if($componentUtil.tooltip) { 
		return $componentUtil.tooltip; 
	}
	$componentUtil.tooltip = {
		initialize : function() {
			$(".field-error").tooltip({
				items : '.field-error',
				disabled : true,
				content : function() {	
					var text1 = $(this).find('.error-message').text();
					if(text1 == ''){
						text1 = $(this).parents('.input-elem').find('.error-message').text();
					}
					return text1;
				}
			});
			$('.field-error').on({
				"focus" : function() {
					var t = $( this )[0];
					var viewWidth = $($( this )[0]).parents("html").width();
					var offsetLeft = $($( this )[0]).parents('.input-elem').offset().left;
					var offsetWidth = t.offsetWidth;
					if( viewWidth - offsetLeft < 2*offsetWidth && offsetWidth < 0.25*viewWidth) {
						$( this ).tooltip( "option", "position", {
							my: "right top+13" ,
							at : "right bottom",
							collision: "flip",
							using : function(position, feedback) {
								if(navigator.userAgent.search("iPad") < 0){ 
									ComponentUtil.tooltip.calculateHorizontalWhenRight(feedback,position);
								}
								$(this).css(position);
								$("<div>").addClass("arrow").addClass(
									feedback.vertical).addClass(
									'right').appendTo(this);
							}
						});
					}else {
						$( this ).tooltip( "option", "position", {
							my: "left top+13" ,
							at : "left bottom",
							collision: "flip",
							using : function(position, feedback) {
								if(navigator.userAgent.search("iPad") < 0){ 
									ComponentUtil.tooltip.calculateHorizontalWhenLeft(feedback,position);
								}
								$(this).css(position);
								$("<div>").addClass("arrow").addClass(
									feedback.vertical).addClass(
									'left').appendTo(this);
							}
						});
					}
					$(this).tooltip("open");
				},
				"blur" : function() {
					$(this).tooltip("close");
				}
			});
		},
		calculateHorizontalWhenLeft : function(feedback,position){
			var targetOffsetLeft = feedback.target.left;
			var viewWidth = $(feedback.target.element["0"]).parents("html").width();
			position.left=targetOffsetLeft*(1+0.01*1920/viewWidth);			
		},
		calculateHorizontalWhenRight : function(feedback,position){
			var targetOffsetLeft = feedback.target.left,
			targetWidth = feedback.target.width,
			elemWidth = feedback.element.width;
			position.left=(targetOffsetLeft - elemWidth+ targetWidth+19);
		},
		
		clearTooltip : function () {
			$(".field-error").tooltip({
				items : '.field-error',
				disabled : true,
				content : function() {	
					return '';
				}
			});
			$('.field-error').each(function(){
				$(this).removeClass('field-error');
			});
		},
	}
	
	/** 
	 * ##################################################
	 * component [tab] define class
	 * ##################################################
	 */
	if($componentUtil.tab) { 
		return $componentUtil.tab; 
	}
	$componentUtil.tab = {
		
		//------------------------------------------------------------------------------
		// initialize
		//------------------------------------------------------------------------------	
		initialize : function() {
			$(".tab").each(function(){
				$tab = $(this);
				$aList = $tab.find('a');
				$aList.each(function(){
					$a = $(this);
					var contentId = $a.attr('for');
					if($a.hasClass("active")){
						$('#'+contentId).show();
					}else{
						$('#'+contentId).hide();
					}
					
					$a.on('click', function(event) {
						$(this).parent().find('a').each(function(){
							$('#'+$(this).attr('for')).hide();
							$(this).removeClass('active');
						});
						$('#'+contentId).show();
						$(this).addClass('active');
					});
				});
			});
		},
		
		initializeWithId : function(tabId) {
			var jqueryId = PopUpUtil.util.convertToJqueryId(tabId);
			$(jqueryId).each(function(){
				$tab = $(this);
				$aList = $tab.find('a');
				$aList.each(function(){
					$a = $(this);
					var contentId = $a.attr('for');
					if($a.hasClass("active")){
						$('#'+contentId).show();
					}else{
						$('#'+contentId).hide();
					}
					
					$a.on('click', function(event) {
						$(this).parent().find('a').each(function(){
							$('#'+$(this).attr('for')).hide();
							$(this).removeClass('active');
						});
						$('#'+contentId).show();
						$(this).addClass('active');
					});
				});
			});
		}
	}
	
	/** 
	 * ##################################################
	 * component [form] define class
	 * ##################################################
	 */
	if($componentUtil.form) { 
		return $componentUtil.form; 
	}
	$componentUtil.form = {
		monitorChange : function(formId){
			var jqueryId;
			if(arguments.length === 1){
				jqueryId = PopUpUtil.util.convertToJqueryId(formId);
			}else{
				jqueryId = PopUpUtil.constants.ID_BASE_FORM;
			}
			var form = $(jqueryId);
			ComponentUtil.form._changeFlg[jqueryId] = false;
			form.change(function(){
				ComponentUtil.form._changeFlg[jqueryId] = true;
			});
		},
		hasChanged : function(formId) {
			var jqueryId;
			if(arguments.length === 1){
				jqueryId = PopUpUtil.util.convertToJqueryId(formId);
			}else{
				jqueryId = PopUpUtil.constants.ID_BASE_FORM;
			}
			
			return ComponentUtil.form._changeFlg[jqueryId];
		},
		_changeFlg : [],
	}
	
})(ComponentUtil);



//*****************************************************************************
//Excute initialize when page loading.

//*****************************************************************************

jQuery(function($){
	
	//*****************************************
	//step0: constants init when loading
	//*****************************************
	ComponentUtil.constants.initialize();
	
	//*****************************************
	//step1: css class loading
	//*****************************************
	//add iPad css class if opened by iPad
	if (ComponentUtil.browser.isiPad()) {
		$("body").addClass("i-pad");
	}
	//add edge css class if opened by Edge
	if (ComponentUtil.browser.isEdge()) {
		$("body").addClass("edge");
	}

	//*****************************************
	//step2: [a] element init when loading
	//*****************************************
	ComponentUtil.a.initialize();
	
	//*****************************************
	//step3: [navimenu] element init when loading
	//*****************************************
	ComponentUtil.navimenu.initialize();

	
	//*****************************************
	//step4: [table] element init when loading
	//*****************************************
	ComponentUtil.table.initialize();
	
	//*****************************************
	//step5: [select box] element init when loading
	//*****************************************
	ComponentUtil.selectBox.initialize();
	
	//*****************************************
	//step6: [check box] element init when loading
	//*****************************************
	ComponentUtil.checkBox.initialize();
	
	//*****************************************
	//step7: [window] element init when loading
	//*****************************************
	ComponentUtil.window.scrollInit();
	
	//*****************************************
	//step8: [model] element init when loading
	//*****************************************
	ComponentUtil.model.initialize();
	
	//*****************************************
	//step9: [trigger] element init when loading
	//*****************************************
	ComponentUtil.trigger.initialize();

	//*****************************************
	//step10: [datepicker] element init when loading
	//*****************************************
	ComponentUtil.datepicker.initialize();
	
	//*****************************************
	//step10: [tooltip] element init when loading
	//*****************************************
	ComponentUtil.tooltip.initialize();
	
	//*****************************************
	//step11: [tab] element init when loading
	//*****************************************
	ComponentUtil.tab.initialize();
});