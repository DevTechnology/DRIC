/**
 * Javascript routines supporting DRIC application.
 * @author: jcaple, 6/20/2015
 */

var dric = {

	url : "api/drug/recall",

	imgUrl : "api/drug/image",

	recallResponse : null,

	detailsResponse : null,
	queryTerms : {
		name : '',
		reportDate : 'ALL',
		status : 'ALL',
		classification : 'ALL',
		skip : 0,
		limit : 100
	},

	////////////////////////////////////////////////////////////////////////////////
	// Display the advanced search modal dialog.
	////////////////////////////////////////////////////////////////////////////////
	showAdvancedSearch : function() {
		$("#advDrugSrchForm").val(dric.queryTerms.name);
		$("#recallTimeFilter").val(dric.queryTerms.reportDate)
		$("#recallStatusFilter").val(dric.queryTerms.status);
		var classification = dric.queryTerms.classification;
		if (classification === 'CLASS1') {
			classification = 'Class I';
		} else if (classification === 'CLASS2') {
			classification = 'Class II';
		} else if (classification === 'CLASS3') {
			classification = 'Class III';
		}
		$("#classificationFilter").val(classification);
		$("#advancedSearchModal").modal('show');
	},

	////////////////////////////////////////////////////////////////////////////////
	// Display Search Results Modal Dialog 
	////////////////////////////////////////////////////////////////////////////////
	showSearchResultDetails : function(index) {
		try {
			// Set data received so far
			dric.detailsResponse = dric.recallResponse.recalls[index];
			var data = dric.detailsResponse;
			dric.showDrugDetailsModal();
			dric.getNdcImage(dric.detailsResponse);
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Show the drug details modal dialog with the data we've collected so far. 
	////////////////////////////////////////////////////////////////////////////////
	showDrugDetailsModal : function() {
		try {
			var detailsHtml = _.templateFromUrl("templates/drugRecallDetails.html", dric.detailsResponse, {variable:"data"});
			$("#drugRecallDetailsBody").html(detailsHtml);
			$("#searchResultsModal").modal('show');
			$("#drugRecallDetailsBody").scrollTop(0);
		} catch (e) {
			console.log(e.message);
		}
	},
	
	getNdcImage : function(data) {
		var imageHtml = _.templateFromUrl("templates/drugRecallDetailsImage.html", {'ndcText':'Loading Image...' , 'drugImage':'img/ajax-loader-sm.gif'}, {variable:"data"});
		$("#images").html(imageHtml).show();
		if (data.hasOwnProperty("product_ndc") && data.product_ndc.length > 0) {
			var ndcList = data.product_ndc.join();
			var queryParam = "ndcs="+ndcList;
			var callback = dric.ndcImageCallback;
			var err = function(err) {
				console.log("ndcImageErr unexpected error: " + err.message);
			};
			var url = dric.imgUrl;
			dric.genericAjax(queryParam, callback, err, url);
		} else {
			dric.ndcImageCallback({});
		}
	},
	////////////////////////////////////////////////////////////////////////////////
	// Handle NDC Image Loading.
	////////////////////////////////////////////////////////////////////////////////
	ndcImageCallback : function(data) {
		try {
			var ndcText = 'No Image Available for NDC '+data.ndc;
			var imageUrl = 'img/No_image_available.svg';
			if (data.hasOwnProperty("url")) {
				if (data.url.length > 0) {
					imageUrl = data.url[0];
					'Drug Thumbnail for NDC '+data.ndc;
				}
			}
			var imageHtml = _.templateFromUrl("templates/drugRecallDetailsImage.html", {'ndcText':ndcText , 'drugImage':imageUrl}, {variable:"data"});
			$("#images").html(imageHtml).show();
		} catch (e) {
			console.log("Unexpected error in ndcImageCallback: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Set the value of the selected filter criteria in the specified filter field.
	////////////////////////////////////////////////////////////////////////////////
	setAdvancedFilterString : function(filterFld, value) {
		filterFld.val(value);
	},

	////////////////////////////////////////////////////////////////////////////////
	// Generic function to perform AJAX queries.
	// @queryParams - string of query params to pass to REST service
	// @callback - AJAX callback handler
	// @err - AJAX callback handler
	////////////////////////////////////////////////////////////////////////////////
	genericAjax : function(queryParams, callback, err, url) {
		setTimeout(function() {
			if (url === undefined) { url = dric.url; }
			var dateTime = new Date();
			var time = dateTime.getTime();
			queryParams += "&time="+time;  // try to prevent cache problems
			$.ajax({
				dataType: "json",
				type: "GET",
				url: url+"?"+queryParams, 
				//url: "data/recents.json", 
				success: callback,
				error: err 
			});
		}, 300);

	},

	////////////////////////////////////////////////////////////////////////////////
	// When user clicks the 'Search' button in advanced search mode, gather additional
	// filter criteria, if specified, and perform search.  
	////////////////////////////////////////////////////////////////////////////////
	executeAdvancedSearch : function() {
		dric.queryTerms.skip = 0;
		dric.queryTerms.name = $("#advDrugSrchForm").val();
		if ($("#recallTimeFilter").val() !== '') {
			dric.queryTerms.reportDate = $("#recallTimeFilter").val();
		} else {
			dric.queryTerms.reportDate = 'ALL';
		}
		if ($("#recallStatusFilter").val() !== '') {
			dric.queryTerms.status = $("#recallStatusFilter").val();
		} else {
			dric.queryTerms.status = 'ALL';
		}
		if ($("#classificationFilter").val() === 'CLASS I') {
			dric.queryTerms.classification = 'CLASS1';
		} else if ($("#classificationFilter").val() === 'CLASS II') {
			dric.queryTerms.classification = 'CLASS2';
		} else if ($("#classificationFilter").val() === 'CLASS III') {
			dric.queryTerms.classification = 'CLASS3';
		} else {
			dric.queryTerms.classification = 'ALL';
		}
		dric.performAdvancedSearch();
	},
	
	performAdvancedSearch : function() {
		try {
			$("#advancedSearchModal").modal('hide');
			dric.showLoadSpinner();
			var searchFilter = dric.queryTerms.name;
			$("#quickSearchFld").val(searchFilter);
			var reportDateFilter = dric.queryTerms.reportDate !== '' ? '&reportDate='+dric.queryTerms.reportDate : '';
			var statusFilter = dric.queryTerms.status !== '' ? '&status='+dric.queryTerms.status : '';
			var classificationFilter = dric.queryTerms.classification !== '' ? '&classification='+dric.queryTerms.classification : '';
			var skipFilter = "&skip="+dric.queryTerms.skip;
			var queryParams = "name="+searchFilter+reportDateFilter+statusFilter+classificationFilter+skipFilter;
			console.log("advanced params: " + queryParams);
			dric.genericAjax(queryParams, dric.performAdvancedSearchCB, function(msg) {
				console.log("Unexpected error occurred in advanced search: " + msg.message);
			});
		} catch (e) {
			console.log("Unexpected error in performAdvancedSearch: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Callback function for the advanced search function.  
	////////////////////////////////////////////////////////////////////////////////
	performAdvancedSearchCB : function(data) {
		try {
			dric.paginationSetup(data);
			dric.recallResponse = data;
			data.queryTerms = "Advanced Search";
			var dateTime = new Date().getTime();
			var drugs = _.templateFromUrl("templates/drugRecallList.html?time="+dateTime, data, {variable:"data"});
			$("#mainContent").html(drugs);
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// When user clicks the 'Search' button execute a quick drug recall search.  
	////////////////////////////////////////////////////////////////////////////////
	performQuickSearch : function() {
		try {
			var searchFor = $("#quickSearchFld").val();
			$("#advDrugSrchForm").val(searchFor);
			dric.queryTerms.name = searchFor;
			dric.queryTerms.reportDate = 'ALL';
			dric.queryTerms.status = 'ALL';
			dric.queryTerms.classification = 'ALL';
			dric.queryTerms.skip = 0;
			dric.showLoadSpinner();
			var queryParams = "name="+searchFor;
			var cbHandler = dric.performQuickSearchCB;
			var cbError = dric.performQuickSearchErr;
			console.log('quick params: '+queryParams);
			dric.genericAjax(queryParams, cbHandler, cbError);
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Callback Handler for performQuickSearch function. 
	////////////////////////////////////////////////////////////////////////////////
	performQuickSearchCB : function(data) {
		try {
			dric.paginationSetup(data);
			dric.recallResponse = data;
			data.queryTerms = "Last Month";
			var dateTime = new Date().getTime();
			var resultHtml = _.templateFromUrl("templates/drugRecallList.html?time="+dateTime, data, {variable:"data"});
			$("#mainContent").html(resultHtml);
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Callback Error Handler for performQuickSearch function. 
	////////////////////////////////////////////////////////////////////////////////
	performQuickSearchErr : function(msg) {
		console.log("Unexpected error occurred in quick search: " + msg.message);
	},

	////////////////////////////////////////////////////////////////////////////////
	// Run when the user presses the Clear button. 
	////////////////////////////////////////////////////////////////////////////////
	resetQuickSearch : function() {
		$("#quickSearchFld").val("");
		$("#mainContent").html("");
		dric.queryTerms.skip = 0;
	},

	////////////////////////////////////////////////////////////////////////////////
	//  
	////////////////////////////////////////////////////////////////////////////////
	showLoadSpinner : function() {
		$("#mainContent").html("<div class='spinner'><center><img src='img/ajax-loader.gif' title='Loading Data...'></center></div>");
	},

	////////////////////////////////////////////////////////////////////////////////
	//  
	////////////////////////////////////////////////////////////////////////////////
	hideLoadSpinner : function() {
		$("#mainContent").html("");
	},
	paginationSetup : function(data) {
		if (data && data.meta && data.meta.results) {
			dric.queryTerms.skip = data.meta.results.skip;
			var skip = data.meta.results.skip;
			var limit = data.meta.results.limit;
			var total = data.meta.results.total;
			var items = data.recalls.length;
			data.prevDisabled = skip === 0 ? "disabled" : "active";
			data.prevOnclick = skip === 0 ? "" : "dric.prevPage();";
			var isNext = (limit + skip) < total;
			data.nextDisabled = isNext === true ? "active" : "disabled";
			data.nextOnclick = isNext === true ? "dric.nextPage();" : "";
			var indexLo = skip+1;
			var indexHi = skip+items;
			data.resultStr = indexLo+'-'+indexHi+' of '+total
		}
	},
	
	prevPage : function() {
		dric.queryTerms.skip = dric.queryTerms.skip - dric.queryTerms.limit;
		dric.performAdvancedSearch();
		return false;
	},
	
	nextPage : function() {
		dric.queryTerms.skip = dric.queryTerms.skip + dric.queryTerms.limit;
		dric.performAdvancedSearch();
		return false;
	}
};


////////////////////////////////////////////////////////////////////////////////
// Mixin to load underscore templates from a file using ajax.  
////////////////////////////////////////////////////////////////////////////////
_.mixin({templateFromUrl: function (url, data, settings) {
	var templateHtml = "";
	this.cache = this.cache || {};
	if (this.cache[url]) {
		templateHtml = this.cache[url];
	} else {
		$.ajax({
			url: url,
			method: "GET",
			dataType: "html", 
			async: false,
			success: function(data) {
				templateHtml = data;
			},
			error: function(data) {
				alert(data);
			}
		});
		this.cache[url] = templateHtml;
	}
	var t = _.template(templateHtml, settings);
	return t(data);
}});
