talend.updatereport = {};
talend.updatereport.UpdateReport = function(){
	
	function _getUrl(language, callBack){
		var frameUrl = "notImplemented.html";
		callBack(frameUrl);
	}
	
	return {
		getUrl : function(language, callBack){_getUrl(language, callBack);}
	}
}();