
$(function () {
    //审批信息
    $.ajax({
        url: "/auditResult/list",
        type: "post",
        data: {
            caseId: 10,
            bussinessId: $("#businessId").val()
        },
        dataType: "HTML",
        success: function (data) {
            $(".auditResult_content").html(data);
        }
    });

    //保存操作
    $("#submits").click(function () {
        if(boolResult == true){
            var studentNo = $("#studentNo").val();
            if($("#reason").val() == "") {
                layer.alert('请选择停缓办原因！', {icon: 0});
                return;
            }
            if($("#delay_time").val() == "") {
                layer.alert('填写下次联系日期！', {icon: 0});
                return;
            }
            if($("#delayType").val() == "") {
                layer.alert('填写停缓办类型！', {icon: 0});
                return;
            }
             boolResult = false;
            $.ajax({
                url: "/student/approveDelay",
                type: "post",
                data: $("#edit").serialize(),
                success: function (data) {
                    console.log(data);
                    data = JSON.parse(data);
                    // layer.close(index);
                    var code = data.code;
                    if(data.code == 0){
                        parent.layer.confirm("审批状态已变更，请刷新页面重试！",{
                            icon:7,
                            btn:['确定']
                        },function () {
                            location.reload(true);
                        });
                    }
                    if (data.code == 1) {
                        layer.confirm('操作成功，是否跳转到学生详情页？', {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            if(studentNo.indexOf('TY') == 0){
                                location.href = "/channelStudent/detail?studentNo=" + studentNo;
                            }
                            else{
                                location.href = "/student/detail?studentNo=" + studentNo;
                            }

                        }, function () {
                            location.reload(true);
                        });
                    }
                    if (data.code == 2) {
                        layer.confirm('操作失败，是否跳转到学生详情页？', {
                            btn: ['确定', '取消'] //按钮
                        }, function () {
                            if(studentNo.indexOf('TY') == 0){
                                location.href = "/channelStudent/detail?studentNo=" + studentNo;
                            }
                            else{
                                location.href = "/student/detail?studentNo=" + studentNo;
                            }
                        }, function () {
                            location.reload(true);
                        });
                    }
                    if(data.code == 3){
                        parent.layer.confirm("请确保之前的停办申请全部审批完毕，勿重复添加！",{
                            icon:7,
                            btn:['确定']
                        },function () {
                            location.reload(true);
                        });
                    }
                }
            })
        }
    });

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    $("#offer_upload").click(function(){
        $("#upload_office").attr("href",$("#offerAttachment").val());
    })
});

function returnStudentDetail(studentNo){
    // location.href="/student/detail?studentNo="+studentNo;
    if(studentNo.indexOf('TY') == 0){
        location.href = "/channelStudent/detail?studentNo=" + studentNo;
    }
    else{
        location.href = "/student/detail?studentNo=" + studentNo;
    }
}

//跳转学生列表页
function returnStudentList(studentNo) {
    location.href="/studentInfo";
}

function toEditURL(id,studentNo){
    location.href="/student/delay?id="+id+"&studentNo="+studentNo;
}

//添加申请
function addPage(){
    var studentNo=$("#studentNo").val();
    location.href='/student/delay?studentNo='+studentNo+"&type=1";
}

//审批
function approve(){
    var studentNo = $("#studentNo").val();
    var updateTime = $("#updateTime").val();
//    $.post("/student/delay/checkStatus",{"date":updateTime,"studentNo":studentNo},function (data,status) {
//        var data = JSON.parse(data);
//        if (data.code == 0) {
//            parent.layer.confirm("待审批内容已修改,请重新审批", {
//                icon: 7,
//                btn: ['确定']
//            }, function () {
//                window.location.reload();
//            });
//        }
//        else if (data.code == 1) {
            layer.prompt({
                formType: 2,
                title: '请添加备注',
                btn: ['通过', '拒绝'],
                yes: function (index, layero) {
                    var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                    var reason = layero.find('.layui-layer-input').val();
                    $.ajax({
                        url: "/student/delay/approve",
                        type: "post",
                        data: {
                            type: 2,
                            remark: reason,
                            updateTime: updateTime,
                            studentNo: studentNo
                        },
                        success: function (data) {
                            layer.close(loadIndex);
                            console.log(data);
                            data = JSON.parse(data);
                            layer.close(index);
                            var code = data.code;
                            if(data.code == 0){
                                parent.layer.confirm("待审批内容已修改,请重新审批",{
                                    icon:7,
                                    btn:['确定']
                                },function () {
                                    location.reload(true);
                                });
                            }
                            if (data.code == 1) {
                                layer.confirm('操作成功，是否跳转到学生详情页？', {
                                    btn: ['确定', '取消'] //按钮
                                }, function () {
                                    if(studentNo.indexOf('TY') == 0){
                                        location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                    }
                                    else{
                                        location.href = "/student/detail?studentNo=" + studentNo;
                                    }                                }, function () {
                                    location.reload(true);
                                });
                            }
                            if (data.code == 2) {
                                layer.confirm('操作失败，是否跳转到学生详情页？', {
                                    btn: ['确定', '取消'] //按钮
                                }, function () {
                                    if(studentNo.indexOf('TY') == 0){
                                        location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                    }
                                    else{
                                        location.href = "/student/detail?studentNo=" + studentNo;
                                    }                                }, function () {
                                    location.reload(true);
                                });
                            }
                        }
                    })
                },
                btn2: function (index, layero) {
                    var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                    var reason = layero.find('.layui-layer-input').val();
                    $.ajax({
                        url: "/student/delay/approve",
                        type: "post",
                        data: {
//					studentDelayId : id,
                            type: 1,
                            remark: reason,
                            updateTime: updateTime,
                            studentNo: studentNo,
                        },
                        success: function (data) {
                            layer.close(loadIndex);
                            data = JSON.parse(data);
                            layer.close(index);
                            var code = data.code;
                            if(data.code == 0){
                                console.log(0);
                                parent.layer.confirm("待审批内容已修改,请重新审批",{
                                    icon:7,
                                    btn:['确定']
                                },function () {
                                    location.reload(true);
                                });
                            }
                            if (data.code == 1) {
                                console.log(1);
                                layer.confirm('操作成功，是否跳转到学生详情页？', {
                                    btn: ['确定', '取消'] //按钮
                                }, function () {
                                    if(studentNo.indexOf('TY') == 0){
                                        location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                    }
                                    else{
                                        location.href = "/student/detail?studentNo=" + studentNo;
                                    }                                }, function () {
                                    location.reload(true);
                                });
                            }
                            if (data.code == 2) {
                                console.log(2);
                                layer.confirm('操作失败，是否跳转到学生详情页？', {
                                    btn: ['确定', '取消'] //按钮
                                }, function () {
                                    if(studentNo.indexOf('TY') == 0){
                                        location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                    }
                                    else{
                                        location.href = "/student/detail?studentNo=" + studentNo;
                                    }                                }, function () {
                                    location.reload(true);
                                });
                            }
                        }
                    })
                }
            });
//        }
//    })
}
//解除停办
function toCancelDelay(studentNo) {
    parent.layer.confirm('是否解除停办？', {
        icon: 2,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/student/Action_cancelDelay",
                type: "POST",
                data: {
                    studentNo: studentNo,
                },
                success: function (data) {
                    if (data) {
                        layer.confirm('操作成功！', {
                            icon: 1,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                parent.layer.closeAll();
                                if(studentNo.indexOf('TY') == 0){
                                    location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                }
                                else{
                                    location.href = "/student/detail?studentNo=" + studentNo;
                                }                            }
                        });
                    } else {
                        layer.confirm('操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                parent.layer.closeAll();
                                if(studentNo.indexOf('TY') == 0){
                                    location.href = "/channelStudent/detail?studentNo=" + studentNo;
                                }
                                else{
                                    location.href = "/student/detail?studentNo=" + studentNo;
                                }                            }
                        });
                    }
                }
            });
        }
    })

}