/**
 * Javascript routines supporting DRIC application.
 * @author: jcaple, 6/20/2015
 */

 /**
  * TODO:
  *	- Better error handling
  *	- 
  */

var dric = {

	url : "api/drug/recall",

	////////////////////////////////////////////////////////////////////////////////
	// Display the advanced search modal dialog.
	////////////////////////////////////////////////////////////////////////////////
	showAdvancedSearch : function() {
		dric.resetAdvancedSearchForm();
		$("#advancedSearchModal").modal('show');
	},

	////////////////////////////////////////////////////////////////////////////////
	// Display Search Results Modal Dialog 
	////////////////////////////////////////////////////////////////////////////////
	showSearchResultDetails : function() {
		$("#searchResultsModal").modal('show');
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
	genericAjax : function(queryParams, callback, err) {
		setTimeout(function() {
			$.ajax({
				dataType: "json",
				type: "GET",
				url: dric.url+"?"+queryParams, 
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
			dric.showLoadSpinner();
			var search = $("#advDrugSrchForm").val();
			var filterRecallTime = $("#recallTimeFilter").val();
			var filterRecallStatus = $("#recallStatusFilter").val();
			var filterClassification = $("#classificationFilter").val();
			$("#advancedSearchModal").modal('close');
			console.log("Advnaced Search Criteria: " + search + "," + filterRecallTime + "," +
				filterRecallStatus + "," + filterClassification);
			var queryParams = "name="+search+"&reportDate="+filterRecallTime+"&status="+filterRecallStatus+
				"&classification="+filterClassification;
			console.log("Query Param String: " + queryParams);
			dric.genericAjax(queryParams, dric.performAdvancedSearchCB, dric.performAdvancedSearchErr);	

		} catch (e) {
			console.log("Unexpected error in performAdvancedSearch: " + e.message);
		}
	},

	performAdvancedSearchCB : function(data) {

	},

	performAdvancedSearchErr : function(msg) {

	},

	////////////////////////////////////////////////////////////////////////////////
	// When user clicks the 'Search' button execute a quick drug recall search.  
	////////////////////////////////////////////////////////////////////////////////
	performQuickSearch : function() {
		try {
			dric.showLoadSpinner();
			var searchFor = $("#quickSearchFld").val();
			console.log("Search For: " + searchFor);
			if (searchFor === undefined || searchFor.trim() === '') 
				return;
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
			data.queryTerms = "Last Month";
			var drugs = _.templateFromUrl("templates/drugRecallList.html", data, {variable:"data"});
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
	          	$('#footer').css('margin-top', 2 + (docHeight - footerTop) + 'px');
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
