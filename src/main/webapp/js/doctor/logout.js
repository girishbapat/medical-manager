$(".logout").on('click',function(){
	var button = this;
	swal({   
		title: "Are you sure?",   
		text: "You will logout from this Practice Place",   
		type: "warning",   
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55",   
		confirmButtonText: "Yes, logout from it!",   
		closeOnConfirm: false
	}, 
	function(){  
		$("#logoutForm").submit();
	});
});

function login(button){
	var associatonId = $(button).data("association-id");
	$("#loginForm #associationId").val(associatonId);
	$("#loginForm").submit();
}