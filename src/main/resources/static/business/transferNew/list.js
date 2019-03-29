
$(function () {
    //保存操作
    $("#batch").click(function () {
        if(invoiceIdSet.size == 0){
            layer.alert('请勾选需要转案的学生！');
            return;
        }
        layer.open({
            type: 2,
            area: ['520px', '450px'], //宽高
            content: '/transfer/batch/allot',
            btn: ['保存', '取消'],
            yes: function(index, layero){
                var body = layer.getChildFrame('body', index);
                var receiveNo = body.contents().find("#receive-1-no").val();
                var receiveName = body.contents().find("#receive-1-name").val();
                var transferReason = body.contents().find("#transferReason").val();
                var operatorType = body.contents().find("#operatorType").val();

                if(receiveNo == "" || receiveName == ""){
                    layer.alert('请选择接收顾问！', {icon: 5});
                    return;
                }

                //加载框
                var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                //提交
                $.ajax({
                    url: "/transfer/batch",
                    type: "post",
                    data: {
                        transferIdStr: getTransferIdStr(),
                        operatorType: operatorType,
                        receiveNo: receiveNo,
                        receiveName: receiveName,
                        transferReason: transferReason,
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        if (data) {
                            changeTable();
                            layer.confirm("操作成功！", {
                                icon: 1,
                                btn: ['确定']
                            },function(){
                                layer.closeAll();
                            });
                        } else {
                            var msg = '系统异常';
                            if (data.errorMsg != null){
                                msg = data.errorMsg;
                            }
                            layer.alert('操作失败! 失败原因：'+ msg);
                        }
                    },
                    error:function(data){
                        layer.close(loadIndex);
                    }
                });
            },
            btn2: function(index, layero){
                layer.close();
            },
        })

    })
});

function getTransferIdStr(){
    var transferIdStr = '';
    for(var i of invoiceIdSet){
        transferIdStr += i;
        transferIdStr += ',';
    }
    return transferIdStr;
}
