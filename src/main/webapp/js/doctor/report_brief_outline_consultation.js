var sessionIdReport = null;
var patientNameReport = null;
var dateSessionReport = null;
$("#table-panel").on("click","#sendReport",function(e){
	$("#modal_report textarea").val("");
	$("#file").val("");
	
	var tr = $('#table_id tbody .selected');
	
	if(!$(tr).hasClass("hasEmail")){
		swal("No email available!", "The patient has not inserted an email for this session.", "info");
		return;
	}
	
	patientNameReport = $($("td",tr)[2]).text();
	dateSessionReport = $($("td",tr)[1]).text();
	
	$("#modal_report .modal-subtitle.date").text("Date / Time: "+dateSessionReport);
	$("#modal_report .modal-subtitle.patienName").text("Patient name: "+patientNameReport);
	
	var table = $('#table_id').DataTable();
	var rowNewSelected = table
    .row( $('#table_id tbody .selected') )
    .node();
	sessionIdReport = $(rowNewSelected).attr("id");
	
	fillSymptomsReport();
	
	$('#modal_report').modal('show');
});



function fillSymptomsReport(){
	$("#modal_report .modal-body .box-simptom-report").html("");
	$("#modal_report .modal-body .box-action textarea").val("");
	$("#modal_report .modal-body .box-action input:checked").prop("checked",false);
	var symptomsDesciptionsDom = $(".box-symptoms-row .box-symptoms .box .description");
	for(var i=0; i < symptomsDesciptionsDom.length;i++){
		var description = $(symptomsDesciptionsDom[i]).val();
		$("#modal_report .modal-body .box-simptom-report").append("<div class='row symptom-row recent-added'>"+
														      			"<div class='col-md-5'>"+
															      			"<textarea rows='3' class='form-control simptom-detail' readonly='readonly'></textarea>"+
															      		"</div>"+
															      		"<div class='col-md-2'>"+
															      			"<input type='checkbox' class='discussed' value=''>"+
															      		"</div>"+
															      		"<div class='col-md-5'>"+
															      			"<textarea rows='3'  class='form-control additional-comment' ></textarea>"+	
															      		"</div>"+
														      		"</div>");
		$("#modal_report .modal-body .box-simptom-report .recent-added .simptom-detail").val(description);
		$("#modal_report .modal-body .box-simptom-report .recent-added").removeClass("recent-added");
	}
}

$("#modal_report .modal-footer .btn-primary").on("click",function(){
	swal({   
		title: "Are you sure?",   
		text: "You will send this report to the patient",   
		type: "warning",   
		showCancelButton: true,   
		confirmButtonColor: "#DD6B55",   
		confirmButtonText: "Yes, send it!",   
		closeOnConfirm: false,
		showLoaderOnConfirm: true
	}, 
	function(){  
		setTimeout(function(){     
			sendReport();
		}, 1000);
	});
});	

function sendReport(){
	var briefConsultReport = new BriefConsultReport();
	briefConsultReport.buildReportData();
	var files = $('#result').text();
	$('#selectedFiles').html(files);
	$('#files_attached').Text = files;
	var data = {'sessionId':sessionIdReport,'report':JSON.stringify(briefConsultReport),'files':files};
	$.ajax({
		url: contextPath+"report/brief-outline-consultation/build",
		method: "POST",
		data: data,
		success: function( data, textStatus, jqXHR) {
			$('#modal_report').modal('hide');
			swal("Report sent!", "The report has been sent!", "success");
		},
		error: function ( jqXHR, textStatus, errorThrown){
			swal("Error!", "The report can not be sent. Try again later please.", "error");
		}
	});
}


function BriefConsultIssue(issueBox,additionalComment,discussed){
	this.issueBox = issueBox;
	this.additionalComment = additionalComment;
	this.discussed = discussed;
}

function BriefConsultAction(action,additionalComment,discussed){
	this.action = action;
	this.additionalComment = additionalComment;
	this.discussed = discussed;
}

function BriefConsultReport(){
	this.dateSession;
	this.patientName;
	this.issues = new Array();
	this.actions = new Array();
	this.otherComment;
}

BriefConsultReport.prototype.buildReportData = function(){
	this.dateSession = dateSessionReport;
	this.patientName = patientNameReport;
	this.otherComment = $("#modal_report .modal-body #other-comment").val();
	var issuesDom = $("#modal_report .modal-body .symptom-row");
	for(var i=0;i < issuesDom.length;i++){
		var issueBox = $($(".simptom-detail",issuesDom[i])).val();
		var additionalComment = $($(".additional-comment",issuesDom[i])).val();
		var discussed = $($(".discussed",issuesDom[i])).is(":checked");
		var issue = new BriefConsultIssue(issueBox,additionalComment,discussed);
		this.issues.push(issue);
	}
	var actionsDom = $("#modal_report .modal-body .action-row");
	for(var i=0;i < actionsDom.length;i++){
		var actionName = $($(".action-label label",actionsDom[i])).html();
		var additionalComment = $($(".additional-comment",actionsDom[i])).val();
		var discussed = $($(".discussed",actionsDom[i])).is(":checked");
		var action = new BriefConsultAction(actionName,additionalComment,discussed);
		this.actions.push(action);
	}
}


$('.trigger-file-input').click(function () {
    $('#file').click();
});
$("input:file").change(function () {
    
    var files = document.getElementById('file').files;
    var formData = new FormData();
    for (var i = 0; i < files.length; i++) {
        formData.append("file" + i, files[i]);
    }
    $.ajax({
        url: contextPath+"report/upload-ajax",  //Server script to process data ../report/upload-ajax
        type: 'POST',
        xhr: function () {  // Custom XMLHttpRequest
            var myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
            }
            return myXhr;
        },
        //Ajax events
        beforeSend: beforeSendHandler,

        error: errorHandler,
        // Form data
        data: formData,
        //Options to tell jQuery not to process data or worry about content-type.
        cache: false,
        contentType: false,
        processData: false,
        success: function (e) {
            $("#result").html(e);
        }

    });
});

function beforeSendHandler() {
}

function errorHandler(e) {
    alert("An error occurred");
}

function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $('progress').attr({value: e.loaded, max: e.total});
    }
}
