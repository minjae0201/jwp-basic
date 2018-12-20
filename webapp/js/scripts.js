$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e){
	e.preventDefault();
	var queryString = $("form[name=answer]").serialize();
	
	$.ajax({
		type : 'post',
		url : '/api/qna/addAnswer',
		data : queryString,
		dataType : 'json',
		error : onError,
		success : onSuccess,
	});
}

function onError(xhr, status){
	alert("error!");
}

function onSuccess(json, status){
	var answerTemplate = $("#answerTemplate").html();
	var template = answerTemplate.format(json.writer, new Date(json.createdDate), json.contents, json.answerId, json.answerId, json.questionId);
	$(".qna-comment-slipp-articles").prepend(template);
	
	var countTemplate = $("#countTemplate").html();
	var template2 = countTemplate.format(json.countOfAnswer);
	$("#countAnswer").children().first().replaceWith(template2);
}

$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e){
	e.preventDefault();

	var deleteBtn = $(this);
	var queryString = deleteBtn.closest("form").serialize();
	
	$.ajax({
		type : 'post',
		url : '/api/qna/deleteAnswer',
		data : queryString,
		dataType : 'json',
		error : function(xhr, status){
					alter("error!");
				},
		success : function(json, status){
		        	if(json.status){
		        		deleteBtn.closest('article').remove();
		        	}
				}
	});
}

String.prototype.format = function(){
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number){
		return typeof args[number] != 'underfined'
						? args[number] : match;
	});
}