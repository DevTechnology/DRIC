/**
 * Javascript routines supporting DRIC application.
 * @author: jcaple, 6/20/2015
 */

var dric = {

	url : "api/drug/recall",

	imgUrl : "api/drug/image",

	recallResponse : null,

	detailsResponse : null,

	////////////////////////////////////////////////////////////////////////////////
	// Display the advanced search modal dialog.
	////////////////////////////////////////////////////////////////////////////////
	showAdvancedSearch : function() {
		dric.resetAdvancedSearchForm();
		$("#recallTimeFilter").val("ONEMONTH")
		$("#recallStatusFilter").val("ALL");
		$("#classificationFilter").val("ALL");
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

			// Try to load an associated Drug Image if one can be found.
			if (data.hasOwnProperty("product_ndc")) {
				if (data.product_ndc.length > 0) {
					var ndcList = data.product_ndc.join();
					var queryParam = "ndcs="+ndcList;
					var callback = dric.ndcImageCallback;
					var err = dric.ndcImageErr;
					var url = dric.imgUrl;
					dric.genericAjax(queryParam, callback, err, url);
				} else {
					dric.showDrugDetailsModal();
				}
			} else {
				dric.showDrugDetailsModal();
			}

		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Show the drug details modal dialog with the data we've collected so far. 
	////////////////////////////////////////////////////////////////////////////////
	showDrugDetailsModal : function() {
		try {
			var detailsHtml = _.templateFromUrl("templates/drugRecallDetails.html", 
				dric.detailsResponse, {variable:"data"});
			$("#drugRecallDetailsBody").html(detailsHtml);
			$("#searchResultsModal").modal('show');
		} catch (e) {
			console.log(e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Handle NDC Image Loading.
	////////////////////////////////////////////////////////////////////////////////
	ndcImageCallback : function(data) {
		try {
			if (data.hasOwnProperty("url")) {
				if (data.url.length > 0) {
					dric.detailsResponse.drugImage = data.url[0];
				}
			}
			dric.showDrugDetailsModal();
		} catch (e) {
			dric.showDrugDetailsModal();
			console.log("Unexpected error in ndcImageCallback: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Handle NDC Image errors.
	////////////////////////////////////////////////////////////////////////////////
	ndcImageErr : function(err) {
		dric.showDrugDetailsModal();
		console.log("ndcImageErr unexpected error: " + err.message);
	},

	////////////////////////////////////////////////////////////////////////////////
	// Set the value of the selected filter criteria in the specified filter field.
	////////////////////////////////////////////////////////////////////////////////
	setAdvancedFilterString : function(filterFld, value) {
		filterFld.val(value);
	},

	////////////////////////////////////////////////////////////////////////////////
	// Reset the advanced search form. 
	////////////////////////////////////////////////////////////////////////////////
	resetAdvancedSearchForm : function() {
		$("#advDrugSrchForm").val("");
		$("#recallTimeFilter").val("");
		$("#recallStatusFilter").val("");
		$("#classificationFilter").val("");
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
	performAdvancedSearch : function() {
		try {
			$("#advancedSearchModal").modal('toggle');
			dric.showLoadSpinner();
			var search = $("#advDrugSrchForm").val();
			$("#quickSearchFld").val(search);
			var reportDateFilter = "";
			var filterRecallTime = $("#recallTimeFilter").val();
			if (filterRecallTime !== '') {
				reportDateFilter = '&reportDate='+filterRecallTime;
			}
			var statusFilter = "";
			var filterRecallStatus = $("#recallStatusFilter").val();
			if (filterRecallStatus !== '') {
				statusFilter = '&status='+filterRecallStatus;
			}
			var classificationFilter = "";
			var filterClassification = $("#classificationFilter").val();
			if (filterClassification !== '') {
				classificationFilter = '&classification='+filterClassification;
			}
			var queryParams = "name="+search+reportDateFilter+statusFilter+classificationFilter;
			console.log("Query Param String: " + queryParams);
			dric.genericAjax(queryParams, dric.performAdvancedSearchCB, dric.performAdvancedSearchErr);	
		} catch (e) {
			console.log("Unexpected error in performAdvancedSearch: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Callback function for the advanced search function.  
	////////////////////////////////////////////////////////////////////////////////
	performAdvancedSearchCB : function(data) {
		try {
			dric.recallResponse = data;
			data.queryTerms = "Advanced Search";
			var dateTime = new Date().getTime();
			var drugs = _.templateFromUrl("templates/drugRecallList.html?time="+dateTime, data, {variable:"data"});
			$("#mainContent").html(drugs);
			dric.reloadFooter();
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Error callback function for the advanced search function.  
	////////////////////////////////////////////////////////////////////////////////
	performAdvancedSearchErr : function(msg) {
		console.log("Unexpected error occurred in advanced search: " + msg.message);
	},

	////////////////////////////////////////////////////////////////////////////////
	// When user clicks the 'Search' button execute a quick drug recall search.  
	////////////////////////////////////////////////////////////////////////////////
	performQuickSearch : function() {
		try {
			var searchFor = $("#quickSearchFld").val();
			console.log("Search For: " + searchFor);
			if (searchFor === undefined || searchFor.trim() === '') 
				return;
			dric.showLoadSpinner();
			var queryParams = "name="+searchFor;
			var cbHandler = dric.performQuickSearchCB;
			var cbError = dric.performQuickSearchErr;
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
			dric.recallResponse = data;
			data.queryTerms = "Last Month";
			var dateTime = new Date().getTime();
			var drugs = _.templateFromUrl("templates/drugRecallList.html?time="+dateTime, data, {variable:"data"});
			$("#mainContent").html(drugs);
			dric.reloadFooter();
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
		//dric.loadRecentDrugReports();
		$("#quickSearchFld").val("");		
		$("#mainContent").html("");
		dric.reloadFooter();
	},

	////////////////////////////////////////////////////////////////////////////////
	// Load Drug Recall Reports for the last month.  
	////////////////////////////////////////////////////////////////////////////////
	loadRecentDrugReports : function() {
		try {
			$("#quickSearchFld").val("");		
			dric.showLoadSpinner();
			var queryParams = "reportDate=ONEMONTH";
			var cbHandler = dric.loadRecentDrugReportsCB;
			var cbError = dric.loadRecentDrugReportsErr;
			dric.genericAjax(queryParams, cbHandler, cbError);
		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Callback Handler for loadRecentDrugReports function. 
	////////////////////////////////////////////////////////////////////////////////
	loadRecentDrugReportsCB : function(data) {
		try {
			data.queryTerms = "Last Month";
			var drugs = _.templateFromUrl("templates/drugRecallList.html", data, {variable:"data"});
			$("#mainContent").html(drugs);

		} catch (e) {
			console.log("Unexpected error: " + e.message);
		}
	},

	////////////////////////////////////////////////////////////////////////////////
	// Error Handler for loadRecentDrugReports function. 
	////////////////////////////////////////////////////////////////////////////////
	loadRecentDrugReportsErr : function(msg) {
		console.log("Unexpected error: " + msg.message);
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

	////////////////////////////////////////////////////////////////////////////////
	// Make sure the footer stays at correct location at bottom of the screen. 
	////////////////////////////////////////////////////////////////////////////////
	reloadFooter : function() {
     		var docHeight = $(window).height();
        	var footerHeight = $('#footer').height();
		var mainHeight = $('#mainContent').height();
	   	var footerTop = $('#footer').position().top + footerHeight;

	      	if (footerTop < docHeight) {
	          	$('#footer').css('margin-top', -2 + (docHeight - footerTop) + 'px');
		}
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
