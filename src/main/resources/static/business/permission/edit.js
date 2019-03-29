function selectParentPermission(){
    var parentId = $("#parentId").val();
    layer.open({
        title: '父级权限选择',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/permission/tree'
    });
}


$(function () {
    //保存操作
    $("#submits").click(function () {

        var permissionName = $("#permissionName").val();
        if(permissionName == ""){
            layer.alert('请输入权限名称！', {icon: 0});
            return;
        }
        var permissionUrl = $("#permissionUrl").val();
        if(permissionUrl == ""){
            layer.alert('请输入权限地址！', {icon: 0});
            return;
        }

        $.ajax({
            url: "/permission/Action_edit",
            type: "post",
            data: $("#edit").serialize(),
            success: function (data) {
                if (data) {
                    layer.confirm('操作成功，是否跳转到列表页？', {
                      btn: ['确定','取消'] //按钮
                    }, function(){
                        goBack();
                    }, function(){
                        parent.layer.close();
                    });
                } else {
                    layer.confirm('操作失败，是否跳转到列表页？', {
                      btn: ['确定','取消'] //按钮
                    }, function(){
                        goBack();
                    }, function(){
                        parent.layer.close();
                    });
                }
            }
        });
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
});

function goBack(){
    location.href="/permission";
}