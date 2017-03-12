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
                  { "mData": "bookValuePerShare"}
			]
	 })
});