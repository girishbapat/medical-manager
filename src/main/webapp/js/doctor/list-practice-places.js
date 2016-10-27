$(document).ready(function(){
	buildPracticeTable();
});

function buildPracticeTable(){
	$('#table_id').dataTable( {
  	  "ordering": false,
  	  "bPaginate": true,
  	  "bLengthChange": false,
  	  "iDisplayLength": 10,
  	  "processing": true,
      "serverSide": true,
      "ajax": "../practice-place/json/list",
      language: {
          searchPlaceholder: "Practice ID"
      },
        "aoColumns" : [
				{"mData" : "practicePlace.practiceId"},
				{"mData" : "practicePlace.description"},
				{
		           "mData": null,
		           "bSortable": false,
		           "sClass": "text-center",
		           "width": "10%",
		           "mRender": function(data, type, full) {
		        	   if(data.status == 'NO_REQUEST'){
		        		   return '<a class="btn btn-primary btn-sm btn-request" data-practice-id=' + data.practicePlace.id + '>' + 'Request association' + '</a>';
		        	   }else if(data.status == 'ON_HOLD'){
		        		   return '<a class="btn btn-primary btn-sm btn-cancel-request btn-cancel" data-association-id=' + data.id + '>' + 'Cancel request' + '</a>';
		        	   }else if(data.status == 'DENIED'){
		        		   return '<span class="request-denied">Request denied</span>';
		        	   }else if(data.status == 'APPROVED'){
		        		   return '<a class="btn btn-login btn-sm btn-login-practice" data-association-id=' + data.id + '>' + 'Login' + '</a>';
		        	   }
		        	   
		           }
		        }
				
			]
  });
}

$("#table_id").on("click",".btn-cancel",function(){
	var button = this;
	swal({   
		title: "Are you sure?",   
		text: "You will delete the request you have made",   
		type: "warning",   
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55",   
		confirmButtonText: "Yes, delete it!",   
		closeOnConfirm: false,
		showLoaderOnConfirm: true
	}, 
	function(){  
		setTimeout(function(){     
			deleteRequest(button);
		}, 1000);
	});
});

$("#table_id").on("click",".btn-request",function(){
	var button = this;
	swal({   
			title: "Are you sure?",   
			text: "You will send a request to associate with a practice place",   
			type: "warning",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, send request!",   
			closeOnConfirm: false,
			showLoaderOnConfirm: true
		}, 
		function(){  
			setTimeout(function(){     
				buildRequest(button);
			}, 1000);
		});
});

function deleteRequest(button){
	var associatonId = $(button).data("association-id");
	var data = {'associatonId':associatonId};
	$.ajax({
		url: "../practice-place/json/cancelAssociaton",
		data: data,
		method: "POST",
		success: function( data, textStatus, jqXHR) {
			$(button).parent().html("<span class='request-deleted'>Request deleted</span>");
			swal("Request deleted!", "The request has been deleted!", "success");
		},
		error: function ( jqXHR, textStatus, errorThrown){
			swal("Error!", "The request can not be deleted. Try again later please.", "error");
		},
		async: false
	});
}

function buildRequest(button){
	var practiceId = $(button).data("practice-id");
	var data = {'practiceId':practiceId};
	$.ajax({
			url: "../practice-place/json/requestAssociaton",
			data: data,
			method: "POST",
			success: function( data, textStatus, jqXHR) {
				$(button).parent().html("<a class='btn btn-primary btn-sm btn-cancel-request btn-cancel' data-association-id='" + data.id + "'> Cancel request </a>");
				swal("Request sent!", "The request must be approved by the Practice Admin!", "success")
			},
			error: function ( jqXHR, textStatus, errorThrown){
				swal("Error!", "The request can not be sent. Try again later please.", "error")
			},
			async: false
	});
}

$("#table_id").on("click",".btn-login-practice",function(){
	if($("#loginForm #associationId").val()!=""){
		swal("Error!","You need to log out from the current Practice Place.", "error");
		return;
	}
	var button = this;
	swal({   
		title: "Are you sure?",   
		text: "You will login to this Practice Place",   
		type: "warning",   
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55",   
		confirmButtonText: "Yes, login to it!",   
		closeOnConfirm: false
	}, 
	function(){  
		login(button);
	});
});