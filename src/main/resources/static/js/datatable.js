$(document).ready( function () {
	 var table = $('#stockTable').DataTable({
			"sAjaxSource": "/stocks",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "ticker"},
		          { "mData": "companyName" },
		          { "mData": "quote" },
                  { "mData": "pe"},
                  { "mData": "peg"},
                  { "mData": "annualYieldPercent"},
                  { "mData": "eps"},
                  { "mData": "roe"},
                  { "mData": "marketCap"},
                  { "mData": "oneYearTargetPrice"},
                  { "mData": "ebitda"},
                  { "mData": "shortRatio"},
                  { "mData": "bookValuePerShare"},
                  { "mData": "dividendGrowth5y"},
                  { "mData": "dividendGrowth10y"},
                  { "mData": "payoutRatio"},
                  { "mData": "morningstarStockEps"},
                  { "mData": "epsGrowth5y"},
                  { "mData": "epsGrowth10y"},
                  { "mData": "fcf"},
                  { "mData": "fcfGrowth5y"},
                  { "mData": "fcfGrowth10y"},
                  { "mData": "roi1y"},
                  { "mData": "roi5y"},
                  { "mData": "roi10y"},
                  { "mData": "score"}
			]
	 })

	 var table = $('#stockScoreTable').DataTable({
     			"sAjaxSource": "/stockScores",
     			"sAjaxDataProp": "",
     			"order": [[ 0, "asc" ]],
     			"aoColumns": [
			      { "mData": "ticker"},
		          { "mData": "companyName" },
                  { "mData": "pe"},
                  //{ "mData": "peg"},
                  { "mData": "annualYieldPercent"},
                  //{ "mData": "eps"},
                  //{ "mData": "roe"},
                  //{ "mData": "marketCap"},
                  //{ "mData": "oneYearTargetPrice"},
                  //{ "mData": "ebitda"},
                  //{ "mData": "shortRatio"},
                  //{ "mData": "bookValuePerShare"},
                  { "mData": "dividendGrowth5y"},
                  { "mData": "dividendGrowth10y"},
                  { "mData": "payoutRatio"},
                  { "mData": "morningstarStockEps"},
                  { "mData": "epsGrowth5y"},
                  { "mData": "epsGrowth10y"},
                  { "mData": "fcf"},
                  { "mData": "fcfGrowth5y"},
                  { "mData": "fcfGrowth10y"},
                  { "mData": "roi1y"},
                  { "mData": "roi5y"},
                  { "mData": "roi10y"},
                  { "mData": "revenueGrowth5y"},
                  { "mData": "revenueGrowth10y"},
                  { "mData": "score"}
     			]
     	 })

	 var table = $('#stockGrowthScoreTable').DataTable({
     			"sAjaxSource": "/stockGrowthScores",
     			"sAjaxDataProp": "",
     			"order": [[ 0, "asc" ]],
     			"aoColumns": [
			      { "mData": "ticker"},
		          { "mData": "companyName" },
                  { "mData": "pe"},
                  //{ "mData": "peg"},
                  //{ "mData": "eps"},
                  //{ "mData": "roe"},
                  //{ "mData": "oneYearTargetPrice"},
                  //{ "mData": "ebitda"},
                  //{ "mData": "shortRatio"},
                  //{ "mData": "bookValuePerShare"},
                  { "mData": "epsGrowth5y"},
                  { "mData": "epsGrowth10y"},
                  { "mData": "fcfGrowth5y"},
                  { "mData": "fcfGrowth10y"},
                  { "mData": "roi5y"},
                  { "mData": "roi10y"},
                  { "mData": "score"}
     			]
     	 })
});