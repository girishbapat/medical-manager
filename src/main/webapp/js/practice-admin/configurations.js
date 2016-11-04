$(document).ready(function(){
	buildConfigurationTable();
});

function buildConfigurationTable(){
	oTable = $('#conf_table_id').dataTable( {
  	  "ordering": false,
  	  "bPaginate": false,
  	  "searching": false,
  	  "bLengthChange": false,
  	  "iDisplayLength": 25,
  	  "processing": true,
      "serverSide": true,
      "bInfo": false, 
      "ajax": "../doctor-admin/json/doctor/list1",
        "aoColumns" : [
				{"mData" : "key"},
				{
			           "mRender": function(data, type, full) {
			        	   return '<input type="text" value=' + full.value + ' id='+full.key+'>';
			           }
			        },
			        {
				           "mRender": function(data, type, full) {
				        	   var key = full.key;
				        	   var value = full.value;
				        	   return "<button class='btn btn-primary' type='button' onclick=updateConfiguration('"+key+"','"+value+"')>Update</button>";

				           }
				        }
			]
  });
}

function updateConfiguration(key,value){
		var id = "#"+key; var newValue = $(id).val();
		var data = {'key':key, 'value':newValue};

		$.ajax({
			url: "/doctor-admin/configurations1",
			method: "POST",
			data: data,
			success: function( data, textStatus, jqXHR) {
				swal("Configuration Update!","Configuration Update Successfuly.", "success");
			},
			error: function ( jqXHR, textStatus, errorThrown){
				swal("Error!",jqXHR.responseText, "error");
			}
		});

	}