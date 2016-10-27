$(document).ready(function(){
	buildDoctorTable();
});

function buildDoctorTable(){
	$('#table_id').dataTable( {
  	  "ordering": false,
  	  "bPaginate": true,
  	  "searching": false,
  	  "bLengthChange": false,
  	  "iDisplayLength": 10,
  	  "processing": true,
      "serverSide": true,
      "ajax": "../doctor-admin/json/doctor/list",
        "aoColumns" : [
				{"mData" : "doctor.name"},
				{"mData" : "doctor.lastname"},
				{
			           "mData": null,
			           "bSortable": false,
			           "sClass": "text-center",
			           "width": "10%",
			           "mRender": function(data, type, full) {
			        	   if(data.status == 'ON_HOLD' || data.status == 'DENIED'){
			        		   return '<a class="btn btn-primary btn-sm btn-authorize" data-association-id=' + data.id + '>' + 'Authorize' + '</a>';
			        	   }else{
			        		   return '<a class="btn btn-primary btn-sm btn-remove-authorize" data-association-id=' + data.id + '>' + 'Invalidate' + '</a>';
			        	   }
			           }
			        }
				
			]
  });
	
	$("#table_id").on("click",".btn-authorize",function(){
		var button = this;
		swal({   
			title: "Are you sure?",   
			text: "You will authorize the request",   
			type: "warning",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, authorize it!",   
			closeOnConfirm: false,
			showLoaderOnConfirm: true
		}, 
		function(){  
			setTimeout(function(){     
				authorizeRequest(button);
			}, 1000);
		});
	});
	
	function authorizeRequest(button){
		var associatonId = $(button).data("association-id");
		var data = {'associationId':associatonId,'status':'APPROVED'};
		$.ajax({
			url: "../doctor-admin/json/request/updateStatus",
			data: data,
			method: "POST",
			success: function( data, textStatus, jqXHR) {
				$(button).parent().html("<a class='btn btn-primary btn-sm btn-remove-authorize' data-association-id='" + data.id + "'>Invalidate</a>");
				swal("Request authorized!", "The request has been authorized!", "success")
			},
			error: function ( jqXHR, textStatus, errorThrown){
				swal("Error!", "The request can not be authorized. Try again later please.", "error")
			},
			async: false
		});
	}
	
	$("#table_id").on("click",".btn-remove-authorize",function(){
		var button = this;
		swal({   
			title: "Are you sure?",   
			text: "You will disauthorize the request",   
			type: "warning",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, disauthorize it!",   
			closeOnConfirm: false,
			showLoaderOnConfirm: true
		}, 
		function(){  
			setTimeout(function(){     
				disauthorizeRequest(button);
			}, 1000);
		});
	});
	
	function disauthorizeRequest(button){
		var associatonId = $(button).data("association-id");
		var data = {'associationId':associatonId,'status':'DENIED'};
		$.ajax({
			url: "../doctor-admin/json/request/updateStatus",
			data: data,
			method: "POST",
			success: function( data, textStatus, jqXHR) {
				$(button).parent().html("<a class='btn btn-primary btn-sm btn-authorize' data-association-id='" + data.id + "'> Authorize </a>");
				swal("Request disauthorized!", "The request has been disauthorized!", "success")
			},
			error: function ( jqXHR, textStatus, errorThrown){
				swal("Error!", "The request can not be disauthorized. Try again later please.", "error")
			},
			async: false
		});
	}
}