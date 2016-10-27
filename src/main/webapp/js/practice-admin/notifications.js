$(document).ready(function(){
	$.ajax({
		url: contextPath+"practice-place/infoSession",
		method: "GET",
		success: function( data, textStatus, jqXHR) {
			if(data != null && data != undefined && data != ""){
				connectNewSessions(data.id)
			}
		},
		error: function ( jqXHR, textStatus, errorThrown){
			//TODO : message error
		}
	});
});


//SUBSCRIBE FOR UPDATES
var stompClientNewSessions = null;

function connectNewSessions(practiceId) {
    var socket = new SockJS(contextPath+"practice-place/broadcast/new-sessions/update");
    stompClientNewSessions = Stomp.over(socket);
    stompClientNewSessions.connect({}, function(frame) {
        //console.log('Connected: ' + frame);
        stompClientNewSessions.subscribe('/topic/practice-place/sessions/'+practiceId, function(sessionResponse){
            var patient = JSON.parse(sessionResponse.body);
            buildNotificationNewSession(patient);
            //console.log(JSON.parse(sessionResponse.body).content);
        });
    });
}

function buildNotificationNewSession(patient){
	$(".notifications .dropdown-menu").append("<li class='recentAdded'><a href='#'>"+
						                    	"<div class='notifications label label-info'></div>"+
						                    	"<p class='username'>New patient: Jane Smith</p></a>"+
						                  		"</li>");
	$(".notifications .dropdown-menu .recentAdded .username").text("New patient: "+patient.name+" "+patient.lastname);
	$(".notifications .dropdown-menu .recentAdded a").attr('href','/consultpal-manager/medical-session/practicePlace/list?patienName='+patient.name+" "+patient.lastname);
	$(".notifications .dropdown-menu .recentAdded").removeClass("recentAdded");
	var counter =  parseInt($(".notifications .counter").text());
	$(".notifications .counter").text(counter+1);
}

function disconnectNewSessions() {
    if (stompClientNewSessions != null) {
    	stompClientNewSessions.disconnect();
    }
}
