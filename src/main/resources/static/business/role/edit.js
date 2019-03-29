// 选择权限
function selectPermission(){
    layer.open({
        title: '权限选择',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/role/permissionTree'
    });
}

function selectPermissionDetail(){
    layer.open({
        title: '查看权限',
        type: 2,
        area: ['250px', '270px'],
        fixed: false, //不固定
        content: '/role/permissionTreeDetail'
    });
}

$(function () {

    //保存操作
    $("#submits").click(function () {

//            if ($("#add").valid()) {
            $.ajax({
                url: "/role/save",
                type: "post",
                data: $("#edit").serialize(),
                success: function (data) {
                    if (data) {
                        layer.confirm('操作成功，是否跳转到列表页？', {
                            btn: ['确定','取消'] //按钮
                        }, function(){
                            location.href = "/role";
                        }, function(){
                            parent.layer.close();
                        });
                    } else {
                        layer.confirm('操作失败，是否跳转到列表页？', {
                            btn: ['确定','取消'] //按钮
                        }, function(){
                            location.href = "/role";
                        }, function(){
                            parent.layer.close();
                        });
                    }
                }
            });
//            }
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });
});
