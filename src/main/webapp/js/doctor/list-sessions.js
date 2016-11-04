$(document).ready(function(){
	buildSessionTable();
});

var sessionId = null;

$('#table_id tbody').on("click","tr",function(){
	var table = $('#table_id').DataTable();
	
	//remove current selected row
	var rowNewSelected = table
    .row( $('#table_id tbody .selected') )
    .node();
 
	$(rowNewSelected).removeClass( 'selected' );
	
	//add new row selected
	var rowNewSelected = table
	    .row( this )
	    .node();
	$(rowNewSelected).addClass( 'selected' );
	
	managePanelButton(rowNewSelected);
	
	sessionId = $(rowNewSelected).attr("id");
	listSymptoms(sessionId);
	

});

/****Clipboard****/
var clipboard = null;
function initializeClipboard(){
    $("#sessionToClipboard").hide();

    
	if(clipboard != null){
		clipboard.destroy();
	}
    clipboard = new Clipboard('#export');
	
	
    clipboard.on('success', function(e) {
        console.log(e);
        $("#sessionToClipboard").hide();
    });

    clipboard.on('error', function(e) {
        console.log(e);
        $("#sessionToClipboard").hide();
    });
}

function buildDataForClipboard(){
	$("#sessionToClipboard #sessionRow").remove();
	$("#sessionToClipboard .symptomsRow").remove();
	
	var tr = $('#table_id tbody .selected');
	var patientName = $($("td",tr)[2]).text();
	var dob = $($("td",tr)[3]).text();
	var dateSession = $($("td",tr)[1]).text();
	
	$("#sessionToClipboard").append("<div id='sessionRow'>"+patientName+", [DOB] "+dob+" , [Session Date] "+dateSession+"</div><br>");
	
	for(var i=0; i < $(".box-symptoms .box").length; i++){
		var description = $(".description",$(".box-symptoms .box")[i]).val();
		var ans1 = $(".answer1",$(".box-symptoms .box")[i]).val();
		var ans2 = $(".answer2",$(".box-symptoms .box")[i]).val();
		$("#sessionToClipboard").append("<div class='symptomsRow'>TextBox: "+(i+1)+") "+description+", [Duration] "+ans1+" , [Dysfunction] "+ans2+"</div> ");
	}
}

$("#table-panel #export").click(function(e){
	$("#sessionToClipboard").prepend("<H2>Prior to consultation, the patient used the GP-Mate app to report the following:<H2><br/>");
	$("#sessionToClipboard").show();
	
});
/****END Clipboard****/


function managePanelButton(row){
	initializeClipboard();
	
	if($(row).hasClass("availableToBeTaken")){
		$("#table-panel #allocate").prop("disabled",false);
		$("#table-panel #deallocate").prop("disabled",true);
	}else{
		$("#table-panel #allocate").prop("disabled",true);
		$("#table-panel #deallocate").prop("disabled",false);
	}
	
	if($(row).hasClass("ownSession")){
		$("#table-panel #export").prop("disabled",false);
		$("#table-panel #sendReport").prop("disabled",false);
		if($(row).hasClass("sessionOpen")){
			$("#table-panel #sendMessage").prop("disabled",false);
		}else{
			$("#table-panel #sendMessage").prop("disabled",true);
		}
	}else{
		$("#table-panel #export").prop("disabled",true);
		$("#table-panel #sendReport").prop("disabled",true);
		$("#table-panel #sendMessage").prop("disabled",true);
	}
		
}


var sessionIdForChat = null;

$("#table-panel #sendMessage").click(function(e){
	cleanChat();
	
	var table = $('#table_id').DataTable();
	var rowNewSelected = table
    .row( $('#table_id tbody .selected') )
    .node();
	
	sessionIdForChat = $(rowNewSelected).attr("id");
	
//	if(sessionIdChatAux == sessionIdChat){
//		throw "Chat has already been open";
//	}else{
//		sessionIdChat = sessionIdChatAux;
//	}
	
	$("#chat_window_1 .loading").show();
	$("#chat_window_1 .msg_container").hide();
	$("#chat_window_1").show();
	
	$.ajax({
		url: "../../chat-medicalSession/doctor/"+sessionIdForChat,
		method: "GET",
		success: function( data, textStatus, jqXHR) {
			chatSessionId = data.id;
			buildSessionChat(data);
		},
		error: function ( jqXHR, textStatus, errorThrown){
			//TODO : message error
		}
	});
	
});

var chatSessionId = null;

$(".chat-window .panel-footer #btn-chat").click(function(e){
	var message = $(".chat-window .panel-footer .chat_input").val();
	if(message != null && message != ""){
		var data = {'message':message,'chatSessionId':chatSessionId};
		$.ajax({
			url: "../../chat-medicalSession/doctor/create",
			method: "POST",
			data: data,
			success: function( data, textStatus, jqXHR) {
				buildChatMessage(message,new Date().toString('MM/d : HH:mm'));
				scrollTOBottomChat();
				$(".chat-window .panel-footer .chat_input").val("");
			},
			error: function ( jqXHR, textStatus, errorThrown){
				//TODO : message error
			}
		});
	}
		
});

function buildSessionChat(sessionChat){
	var patientName = sessionChat.session.patient.name + " " +sessionChat.session.patient.lastname;
	$(".chat-window .panel-title .chat-person").text(" "+patientName);
	for(var i=0; i < sessionChat.messages.length; i++){
		buildChatMessage(sessionChat.messages[i].message,new Date(sessionChat.messages[i].dateCreated).toString('MM/d : HH:mm'));
	}
	scrollTOBottomChat();
	$("#chat_window_1 .loading").hide();
	$("#chat_window_1 .msg_container").show();
}

function scrollTOBottomChat(){
	var wtf    = $('.msg_container_base');
	var height = wtf[0].scrollHeight;
	wtf.scrollTop(height);
}

function buildChatMessage(message,dateCreated){
	$(".chat-window .panel-body").append("<div class='row msg_container base_sent added_recent'>"+
            "<div class='col-md-10 col-xs-10 message-dialog'>"+
                "<div class='messages msg_sent'>"+
                    "<p>that mongodb thing looks good, huh?</p>"+
                    "<span class='time'>Timothy • 51 min</span>"+
                "</div>"+
            "</div>"+
        "</div>");
	$(".chat-window .panel-body .added_recent .messages.msg_sent p").text(message);
	$(".chat-window .panel-body .added_recent .messages.msg_sent .time").text(dateCreated);
	$(".chat-window .panel-body .added_recent").removeClass("added_recent");
}

$("#table-panel #allocate").click(function(e){
	if(sessionId != null){
		var data = {"sessionId":sessionId};
		$.ajax({
			url: "../json/allocate",
			method: "POST",
			data:data,
			success: function( data, textStatus, jqXHR) {
				//refresh table
				var table = $('#table_id').DataTable();
				table.ajax.reload( null, false );
				swal("Session allocated!", "The session has been allocated by you", "success");
			},
			error: function ( jqXHR, textStatus, errorThrown){
				//refresh table
				var table = $('#table_id').DataTable();
				table.ajax.reload( null, false );
				
				if(jqXHR.status == 409 || jqXHR.status == 401){
					swal("Error!", jqXHR.responseText, "error");
				}else{
					swal("Error!", "The session can not be allocated. Try again later please.", "error");
				}
			}
		});
	}
});

$("#table-panel #deallocate").click(function(e){
	if(sessionId != null){
		var data = {"sessionId":sessionId};
		$.ajax({
			url: "../json/deallocate",
			method: "POST",
			data:data,
			success: function( data, textStatus, jqXHR) {
				//refresh table
				var table = $('#table_id').DataTable();
				table.ajax.reload( null, false );
				swal("Session de-allocated!", "The session has been de-allocated by you", "success");
			},
			error: function ( jqXHR, textStatus, errorThrown){
				//refresh table
				var table = $('#table_id').DataTable();
				table.ajax.reload( null, false );
				
				if(jqXHR.status == 409 || jqXHR.status == 401){
					swal("Error!", jqXHR.responseText, "error");
				}else{
					swal("Error!", "The session can not be de-allocated. Try again later please.", "error");
				}
			}
		});
	}
});


function listSymptoms(sessionId){
	$(".box-symptoms-row .box-symptoms").hide();
	$(".overlay").show();
	$.ajax({
		url: "../json/doctor/"+sessionId+"/symptoms",
		method: "GET",
		success: function( data, textStatus, jqXHR) {
			disconnect();
			
			buildSymptomsBox(data);
		    buildDataForClipboard();
			
			$(".overlay").hide();
			$(".box-symptoms-row .box-symptoms").show();
			
			connect(sessionId);
		},
		error: function ( jqXHR, textStatus, errorThrown){
			swal("Error!", "The symptoms can not be obtained. Try again later please.", "error");
			$(".overlay").hide();
		}
	});
}

function buildSymptomsBox(symptomList,session){
	//build title
	var status,timeOpen,patientName,dob,doctor = "";
	if(session == null){
		status = $($("tr.selected td")[0]).text();
		timeOpen = $($("tr.selected td")[1]).text();
		patientName = $($("tr.selected td")[2]).text();
		dob = $($("tr.selected td")[3]).text();
		doctor = $($("tr.selected td")[4]).text();
	}else{
		status = (session.open)? "OPEN" : "CLOSED";
		timeOpen = new Date(session.startDate).toString('MMMM d, HH:mm');
		patientName = session.patient.name +" "+session.patient.lastname;
		dob = new Date(session.patient.dateOfBirth).toString('MMMM d, yyyy');
		doctor = (!session.availableToBeTaken)? session.doctor.name +" "+session.doctor.lastname : "";
	}
	$(".box-symptoms .title h4").text(status+" | "+timeOpen+" | "+patientName+" | "+dob+" | "+doctor);
	
	//build boxes
	symptomList.sort(function(a, b) {
	    return b.priority < a.priority;
	});
	$(".box-symptoms .content").html("");
	for(var i=0; i < symptomList.length; i++){
		var id = symptomList[i].id;
		var description = symptomList[i].description;
		var answer1 = symptomList[i].answer1;
		var answer2 = symptomList[i].answer2;
		$(".box-symptoms .content").append('<div class="box added col-md-12">'+
	    					'<div class="box-title col-md-12">'+
	    						'<h4>Box 1</h4>'+
	    					'</div>'+
	    					'<div class="col-md-12 col">'+
		    					'<textarea name="description" rows="2" class="form-control description" readonly></textarea>'+
	    					'</div>'+
	    					'<div class="col-md-6 col">'+
	    						'<label for="answer1">How long have you had this issue?</label>'+
	    						'<input name="answer1" class="form-control answer1" readonly>'+
	    					'</div>'+
	    					'<div class="col-md-6 col">'+
	    						'<label for="answer2">Does it affect your daily activities?</label>'+
	    						'<input name="answer2" class="form-control answer2" readonly>'+
	    					'</div>'+
	    				'</div>');
		$(".box-symptoms .content .added").attr("id",id);
		$(".box-symptoms .content .added .box-title h4").text("Box "+(i+1));
		$(".box-symptoms .content .added .description").val(description);
		$(".box-symptoms .content .added .answer1").val(answer1);
		var answer2Value = "";
		if(answer2 === "true"){
			answer2Value = "Yes";
		}else if(answer2 === "false"){
			answer2Value = "No";
		}
		$(".box-symptoms .content .added .answer2").val(answer2Value);
		$(".box-symptoms .content .added").removeClass("added");
	}
}


//SUBSCRIBE FOR UPDATES
var stompClient = null;

function connect(sessionId) {
    var socket = new SockJS(contextPath+"medical-session/broadcast/update");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        //console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/medicalSession/'+sessionId, function(sessionResponse){
        	var session = JSON.parse(sessionResponse.body);
        	var symptoms = session.symptoms;
        	console.log(symptoms);
        	buildSymptomsBox(symptoms,session);
        	buildDataForClipboard();
        	//updateSymptoms(symptoms);
        });
    });
}

//function updateSymptoms(symptoms){
//	for(var i=0;i < symptoms.length; i++){
//		var symp = symptoms[i];
//		$(".description",$(".box-symptoms .content #"+symp.id)).val(symp.description);
//		$(".answer1",$(".box-symptoms .content #"+symp.id)).val(symp.answer1);
//		$(".answer2",$(".box-symptoms .content #"+symp.id)).val(symp.answer2);
//	}
//}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    //console.log("Disconnected");
}

window.onbeforeunload = function (e) {
	disconnect();
	disconnectNewSessions();
};

//END SUBSCRIBE FOR UPDATES


//BUILD DATATABLE
function buildSessionTable(){
	$('#table_id').dataTable( {
  	  "ordering": true,
  	  /* Disable initial sort */
      "aaSorting": [],
  	  "bPaginate": true,
  	  "bLengthChange": false,
  	  "iDisplayLength": 10,
  	  "processing": true,
      "serverSide": true,
      "ajax": {
    	  url: "../json/doctor/list",
    	  error: function (xhr, error, thrown) {
    		  if(xhr.responseJSON.message != undefined){
    			  swal("Error!", xhr.responseJSON.message, "error"); 
    			  $("#table_id_processing").hide();
    		  }
    	  }
      },
      language: {
          searchPlaceholder: "Patient's name"
      },
      "oSearch": {"sSearch": patienNameToSearch},
      "createdRow" : function( row, data, index ) {
    	  row.id = data.id;
    	  if(data.ownSession){
    		  $(row).addClass('ownSession');
    	  }
    	  if(data.availableToBeTaken){
    		  $(row).addClass('availableToBeTaken'); 
    	  }
    	  if(data.open){
    		  $(row).addClass('sessionOpen');
    	  }
    	  if(data.patient.email != null && data.patient.email.indexOf('@') > -1){
    		  $(row).addClass('hasEmail');
    	  }
      },
        "aoColumns" : [
				{
					"mData": null,
				    "sClass": "text-center",
				    "width": "7%",
				    "mRender": function(data, type, full) {
				 	   if(data.open){
				 		   return '<span class="status open">OPEN</span>';
				 	   }else{
				 		  return '<span class="status closed">CLOSED</span>';
				 	   } 
				 	}
				},
				{
		           "mData": null,
		           "width": "15%",
		           "mRender": function(data, type, full) {
		        	   return new Date(data.startDate).toString('MMMM d, yyyy : HH:mm');
		           }
		        },
				{
		           "mData": null,
		           "mRender": function(data, type, full) {
		        	   return data.patient.name +" "+data.patient.lastname;
		           }
		        },
		        {
		           "mData": null,
		           "width": "15%",
		           "mRender": function(data, type, full) {
		        	   return new Date(data.patient.dateOfBirth).toString('MMMM d, yyyy');
		           }
		        },
		        {
			           "mData": null,
			           "mRender": function(data, type, full) {
			        	   return data.patient.email;
			           }
			        },
				{
		           "mData": null,
		           "mRender": function(data, type, full) {
		        	   if(!data.availableToBeTaken && data.doctor){
		        		   return data.doctor.name +" "+data.doctor.lastname;
		        	   }else{
		        		   return "";
		        	   }
		           }
			    },
			    {
			           "mData": null,
			           "mRender": function(data, type, full) {
			        	   return new Date(data.patient.appointmentDate).toString('MMMM d, yyyy : HH:mm');
			           }
			        }
			]
  });
}
