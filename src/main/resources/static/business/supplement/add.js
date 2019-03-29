function returnPage() {
    location.href = "/supplementInfo?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
}

function postData() {
    boolResult = false;
    $.ajax({
        url: "/supplementInfo/Action_add",
        type: "post",
        data: $("#edit").serialize(),
        success: function (data) {
            if (data) {
                boolResult = true;
                layer.confirm('操作成功，是否跳转到列表页？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    location.href = "/supplementInfo?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                }, function(){
                    location.reload(true);
                });
            } else {
                layer.confirm('操作失败，是否跳转到列表页？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    location.href = "/supplementInfo?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                }, function(){
                    location.reload(true);
                });
            }
        }
    });
};

$("#returnPage").click(function(){
    location.href = "/supplementInfo?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
});