$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");
	//获取标题和内容
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	//发送异步请求（POST）
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title": title, "content": content},
		function(data){
			data = $.parseJSON(data)
			//在提示框中显示返回消息
			$("#hintBody").text(data.msg);
			$("#hintModal").modal("show");
			//显示和隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if(data.code == 0){
					window.location.reload(); //重新加载当前页面
				}
			}, 2000);
		}
	)
}