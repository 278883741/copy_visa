
// 分配人员 id为选中人员存储的input的id
function selectStaff(id){

    layer.open({
        title: '分配人员',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/transfer/staff'
    });
}

$(function () {
    //加载事件
    var receive_count = $("#receive_count").val();
    var queryType = $("#queryType").val();


    //原因回写
    var reason = $("#reason").val();
    $(".reason option[value="+reason+"]").attr("selected", true);

    //保存操作
    $("#submits").click(function () {
        //校验
        if(queryType == "copy_visa"){
            if($("#receive-1-no").val() == ""){
                layer.alert('请选择文签经理！', {icon: 5});
                return;
            }
        }
        //加载框
        var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
        //提交
        $.ajax({
            url: "/transfer/save",
            type: "post",
            data: $("#edit").serialize(),
            success: function (data) {
                layer.close(loadIndex);
                if (data.result) {
                    layer.confirm('操作成功，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        goBack();
                    }, function(){
                        parent.layer.close();
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
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
});

function goBack(){
    location.href="/transfer/list";
}