$(document).ready( function () {
	 var table = $('#stockTable').DataTable({
			"sAjaxSource": "/stocks",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "ticker"},
		          { "mData": "companyName" },
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

                /*  { "mData": "dividendGrowth5y"},
                  { "mData": "dividendGrowth10y"},
                  { "mData": "payoutRatio"},
                  { "mData": "morningstarStockEps"},
                  { "mData": "epsGrowth5y"},
                  { "mData": "epsGrowth10y"},
                  { "mData": "fcf"},
                  { "mData": "fcfGrowth5y"},
                  { "mData": "fcfGrowth10y"}*/
			]
	 })
});