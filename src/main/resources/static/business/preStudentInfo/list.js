
function toAllocation(studentNo){
    layer.open({
        title: '分配预科老师:',
        type: 2,
        area: ['370px', '180px'],
        fixed: false, //不固定
        content: '/preStudent/allotCourse?studentNo='+studentNo
    });
}

function toTransfer(studentNo){
    $("#transfer_content").show();
    layer.open({
        type: 1,
        title: "确定将此条记录进行转案吗？",
        area: ['500px', '350px'],
        fixed: false, //不固定
        btn: ['确定','取消'],
        content: $("#transfer_content"),
        btn1: function (index, layero) {
            var remark = $("#remark").val();
            $.ajax({
                url: "/preStudent/toTransfer",
                type: "post",
                data:
                    {
                        studentNo : studentNo,
                        remark: remark
                    },
                success: function (data) {
                    if(data.result){
                        layer.msg("转案成功!", {time: 1000},function(){
                            window.parent.location.reload();
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                    }else{
                        layer.msg("转案失败!", {time: 1000},function(){
                            window.parent.location.reload();
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        });
                    }
                }
            });
        }
    });
}

$("#transfer_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/preStudent/toTransfer?studentNo=" + $("#studentNo").val() +"&content="+$("#transfer_id").val(),
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            if(json.result){
                layer.msg("转案成功!", {time: 1000},function(){
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }else{
                layer.msg("转案失败!", {time: 1000},function(){
                    window.parent.location.reload();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                });
            }
        }
    });
})

$("#allotTeacher_callback").click(function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
})

$("#transfer_callback").click(function(){
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
})

$("#allotTeacher_submits").click(function(){
    if($("#allotTeacher").children("option:selected").val() == 0){
        layer.msg("请选择预科老师",{time: 1500});
        return;
    }
    $.ajax({
        type:'GET',
        url:  "/preStudent/addCourse?studentNo=" + $("#studentNo").val() +"&oaid="+$("#allotTeacher").val(),
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            layer.msg("分配成功!", {time: 1000},function(){
                window.parent.location.reload();
                parent.layer.closeAll();
            });
        }
    });
})