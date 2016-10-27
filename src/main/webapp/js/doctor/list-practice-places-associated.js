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
      "ajax": "../json/practice-places/list",
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
		        	   return '<a class="btn btn-primary btn-sm btn-login-practice" data-association-id=' + data.id + '>' + 'Login' + '</a>';
		           }
		        }
				
			]
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
