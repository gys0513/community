$(function(){
    $("#uploadForm").submit(upload);  //重写form的提交事件
});

function upload() {
    $.ajax({
        url: "https://upload-z2.qiniup.com",
        method: "post",
        processData: false,    // 拒绝把表单的内容转化为字符串
        contentType: false,    // 不让jquery设置上传类型(之前都是json或者html)
        data: new FormData($("#uploadForm")[0]),
        success: function(data) {
            if(data && data.code == 0) {
                // 更新头像访问路径
                $.post(
                    "/community" + "/user/header/url",
                    {"fileName":$("input[name='key']").val()},   // 元素选择器[属性选择器]
                    function(data) {
                        data = $.parseJSON(data);  //解析成json
                        if(data.code == 0) {
                            window.location.reload();
                        } else {
                            alert(data.msg);
                        }
                    }
                );
            } else {
                alert("上传失败!");
            }
        }
    });
    return false;
}