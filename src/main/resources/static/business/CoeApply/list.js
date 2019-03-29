function toAddURL(applyId,studentNo) {
    if($("#applyStatus").val() == "10" || $("#applyStatus").val() == "20" ){
        layer.msg('申请状态为“未提交院校申请”或“未收到申请结果”，不允许录入coe');
        return;
    }
    location.href='/CoeApply/detail?applyId=' + applyId+'&studentNo='+studentNo;
}
function toDetail(id,applyId,studentNo) {
    location.href='/CoeApply/detail?id=' + id+'&applyId=' + applyId+'&studentNo='+studentNo;
}
function toEditURL(id,studentNo){
    location.href='/CoeApply/detail?id=' + id+'&applyId=' + applyId+'&studentNo='+studentNo;
}
function collegeList(studentNo){
    location.href="/apply?studentNo="+studentNo;
}
function toEdit(id,applyId,studentNo){
    location.href='/CoeApply/detail?id=' + id+'&applyId=' + applyId+'&studentNo='+studentNo;
}
//跳转学生列表页
function returnStudentList(studentNo) {
    location.href="/studentInfo";
}

//删除
function toDelete(id,ele){
    var studentNo = $("#studentNo").val();
    parent.layer.confirm('是否执行' + '删除'+ '功能？', {
        icon: 2,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/coeApplyInfo/delete",
                type: "POST",
                data: {
                    id: id,
                },
                success: function (data) {
                    data = JSON.parse(data);
                    if (data.code == 0) {
                        layer.confirm('操作成功！', {
                            icon: 1,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                oTable1.fnDraw();
                                parent.layer.closeAll();
                            }
                        });
                    }
                    if (data.code == 1) {
                        layer.confirm('操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                oTable1.fnDraw();
                                parent.layer.closeAll();
                            }
                        });
                    }
                    // if (data.code == 2) {
                    //     layer.confirm('当前登录人不是此条记录的操作人，无法删除！', {
                    //         icon: 2,
                    //         btn: ['确认']
                    //         , btn1: function (index, layero) {
                    //             oTable1.fnDraw();
                    //             parent.layer.closeAll();
                    //         }
                    //     });
                    // }
                }
            });
        }
    });
}

$(function () {
    if ($("#nationId").val() == 1) {
        fullOfferName = "COE";
        $("#name0").text("COE列表");
        $("#name1").text("COE信息列表");
        $("#name2").text("COE申请附件");
        $("#name3").text("COE结果附件");
        $("#name4").text("添加COE");
    }
    if ($("#nationId").val() == 4 || $("#nationId").val() == 40 || $("#nationId").val() == 41) {
        fullOfferName = "I-20";
        $("#name0").text("I-20列表");
        $("#name1").text("I-20信息列表");
        $("#name2").text("I-20申请附件");
        $("#name3").text("I-20结果附件");
        $("#name4").text("添加I-20");

    }
    if ($("#nationId").val() == 2) {
        fullOfferName = "Receipt";
        $("#name0").text("Receipt列表");
        $("#name1").text("Receipt信息列表");
        $("#name2").text("Receipt申请附件");
        $("#name3").text("Receipt结果附件");
        $("#name4").text("添加Receipt");
    }
    if ($("#nationId").val() == 3) {
        fullOfferName = "CAS";
        $("#name0").text("CAS列表");
        $("#name1").text("CAS信息列表");
        $("#name2").text("CAS申请附件");
        $("#name3").text("CAS结果附件");
        $("#name4").text("添加CAS");
    }
    if ($("#nationId").val() == 5) {
        fullOfferName = "Full Offer";
        $("#name0").text("Full Offer列表");
        $("#name1").text("Full Offer信息列表");
        $("#name2").text("Full Offer申请附件");
        $("#name3").text("Full Offer结果附件");
        $("#name4").text("添加Full Offer");
    }
})

//弹出首次审批
function toConfirm(id){
    parent.layer.confirm('是否已确认？', {
        icon: 7,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/coe/confirm",
                type: "POST",
                data: {
                    id: id,
                },
                success: function (data) {
                    if (data) {
                        layer.confirm( '操作成功！', {
                            icon: 1,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                $("#query").click();
                                parent.layer.closeAll();
                            }
                        });
                    } else {
                        layer.confirm( '操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                $("#query").click();
                                parent.layer.closeAll();
                            }
                        });
                    }
                }
            });
        }
    });
}