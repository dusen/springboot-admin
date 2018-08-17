$(function() {
	$(".tab-index").on("keydown", function(evt) {
		var tabIndex = parseInt($(this).attr("tabindex"));
		switch (evt.which) {
		case 38:
			tabIndex--;
			break;
		case 40:
			tabIndex++;
			break;
		case 37:
			tabIndex--;
			break;
		case 39:
			tabIndex++;
			break;
		case 13:
			if (evt.shiftKey) {
				tabIndex--;
				break;
			}
			tabIndex++;
			break;
		default:
			return;
		}
		if (tabIndex > 0) {
			$(".tab-index[tabindex=" + tabIndex + "]").focus();
			return false;
		}
		return true;
	});
});