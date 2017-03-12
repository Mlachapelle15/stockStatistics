$(document).ready( function () {
	 var table = $('#stockTable').DataTable({
			"sAjaxSource": "/stocks",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			      { "mData": "ticker"},
		          { "mData": "companyName" }
			]
	 })
});