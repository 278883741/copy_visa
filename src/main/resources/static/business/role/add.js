 // var name = $("#roleName").val();
$(function () {
    //保存操作
    $("#submits").click(function () {

        $.ajax({
            url: "/role/save",
            type: "post",
            data: $("#edit").serialize(),
            success: function (data) {
                // if (data) {
                //     $(".alert-success").text("操作成功").show();
                //     $('#alert').attr('class',
                //         'alert alert-success').show();
                //     oTable1.fnDraw();
                //     // location.href='/role/edit?id=' ;
                // } else {
                //     $('#alertText').text('操作失败');
                //     $('#alert').attr('class',
                //         'alert alert-danger').show();
                //     setTimeout("$('#alert').hide()", 2000);
                //     $("#myModal").modal("hide");
                // }
                if (data) {
                    layer.confirm('操作成功，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href = '/role';
                    }, function(){
                        location.reload(true);
                    });
                } else {
                    layer.confirm('操作失败，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href = '/role';
                    }, function(){
                        location.reload(true);
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