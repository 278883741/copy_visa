$(function () {
    //展示保存和审核的逻辑
    statusApprove();
    // $("#scholarship").change(function () {
    //     if ($("#scholarship").find("option:selected").val() == 1) {
    //         $('#scholarshipText').show();
    //         $('#scholarshipMoney').show();
    //         $('#scholarshipMoneyType').show();
    //     }
    //     else {
    //         $('#scholarshipText').hide();
    //         $('#scholarshipMoney').hide();
    //         $('#scholarshipMoneyType').hide();
    //     }
    // })
    if ($("#scholarship").find("option:selected").val() == 1) {
        $('#scholarshipText').show();
        $('#scholarshipMoney').show();
        $('#scholarshipMoneyType').show();
    } else {
        $('#scholarshipText').hide();
        $('#scholarshipMoney').hide();
        $('#scholarshipMoneyType').hide();
    }
    if ($("#nationId").val() == 1) {
        fullOfferName = "COE";
        $("#name1").text("COE信息：");
        $("#name2").text("COE申请信息：");
        $("#name3").text("COE多文件列表：");
        $("#resultDate").text("COE到达日期：");
    }
    if ($("#nationId").val() == 4 || $("#nationId").val() == 40 || $("#nationId").val() == 41) {
        fullOfferName = "I-20";
        $(".supplementInfo").show();
        $("#name1").text("I-20信息：");
        $("#name2").text("I-20申请信息：");
        $("#name3").text("I-20多文件列表：");

        $("#resultDate").text("I-20到达日期：");

    }
    if ($("#nationId").val() == 2) {
        fullOfferName = "Receipt";
        $("#name1").text("Receipt信息：");
        $("#name2").text("Receipt申请信息：");
        $("#name3").text("Receipt多文件列表：");

        $("#resultDate").text("Receipt到达日期：");
    }
    if ($("#nationId").val() == 3) {
        fullOfferName = "CAS";
        $("#name1").text("CAS信息：");
        $("#name2").text("CAS申请信息：");
        $("#name3").text("CAS多文件列表：");
        $("#resultDate").text("CAS到达日期：");
    }
    if ($("#nationId").val() == 5) {
        fullOfferName = "Full Offer";
        $("#name1").text("Full Offer信息：");
        $("#name2").text("Full Offer申请信息：");
        $("#name3").text("Full Offer多文件列表：");
        $("#resultDate").text("Full Offer到达日期：");
    }


//        $("#scholarship").find("option[value='2']") .attr("selected",true)

    $('#form-field-select-3').addClass('tag-input-style');

    $('.date-picker').datepicker({
        autoclose: true,
        format: "yyyy-mm-dd hh:ii",
        language: "cn",
        todayHighlight: true,
        todayBtn: true,
        startDate: new Date()
    }).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
    var canSubmit = true;
    var uploadResult = true;
    //提交
    $("#submits").click(function () {
        console.log("input_fileName:"+$("#input_fileName").val());
        console.log("input_fileName_apply:"+$("#input_fileName_apply").val());
        console.log("input_fileName_pre:"+$("#input_fileName_pre").val());
        console.log("input_fileName_language:"+$("#input_fileName_language").val());
        console.log($("#edit").serialize())


        if(uploadResult = false) {
             layer.msg('正在上传中,请稍后保存!');
             return;
        }
        if(!canSubmit){
            console.log("重复提交！");
            return;
        }else{
            canSubmit = false;
        }
        $.ajax({
            url: "/CoeApply/save",
            type: "post",
            data: $("#edit").serialize(),
            success: function (data) {
                canSubmit = true;

                if (data) {
                    layer.confirm('操作成功，是否跳转到列表页？', {
                        btn: ['确定', '取消'] //按钮
                    }, function () {
                        location.href="/coeApply?applyId="+$('#applyId').val()+"&studentNo="+$('#studentNo').val();
                    }, function () {
                        parent.layer.close();
                        window.location.reload(true);
                    });
                } else {
                    layer.confirm('操作失败，是否跳转到列表页？', {
                        btn: ['确定', '取消'] //按钮
                    }, function () {
                        location.href="/coeApply?applyId="+$('#applyId').val()+"&studentNo="+$('#studentNo').val();
                    }, function () {
                        parent.layer.close();
                        window.location.reload(true);
                    });
                }
            }
        });
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    //审批信息
    $.ajax({
        url: "/auditResult/list",
        type: "post",
        data: {
            caseId: 4,
            bussinessId: $("#coeApplyId").val()
        },
        dataType: "HTML",
        success: function (data) {
            $(".auditResult_content").html(data);
        }
    });

    //点击取消
    $("#returnPage").click(function () {
        location.href="/coeApply?applyId="+$('#applyId').val()+"&studentNo="+$('#studentNo').val();
    });

    //审批
    $("#approve").click(function () {
        var id = $("#coeApplyId").val();
        var applyId = $("#applyId").val();
        var updateTime = $("#updateTime").val();
        var nationId = $("#nationId").val();
        layer.prompt({
            formType: 2,
            title: '请添加备注',
            btn: ['通过', '拒绝'],
            yes: function (index, layero) {
                $("a.layui-layer-btn0").remove();
                var reason = layero.find('.layui-layer-input').val();
                //加载框
        		var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                $.ajax({
                    url: "/CoeApply/approve",
                    type: "post",
                    data: {
                        coeApplyId: id,
                        type: 2,
                        remark: reason,
                        updateTime: updateTime,
                        applyId: applyId,
                        nationId: nationId,
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        layer.close(index);
//                        if (data) {
//                            layer.msg('操作成功', {time: 1000});
//                            waitRun();
////                            location.href="/CoeApply/detail?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
//                        } else {
//                            layer.msg('操作失败', {time: 1000});
//                        }
                         if (data.result) {
                             layer.msg('操作成功', {time: 1000});
                             waitRun();
                         } else {
                             parent.layer.confirm(data.errorMsg, {
                                 icon: 7,
                                 btn: ['确认']
                             },function () {
                                 location.reload(true);
                             })
                         }
                    }
                })
            },
            btn2: function (index, layero) {
                $("a.layui-layer-btn1").remove();
                var reason = layero.find('.layui-layer-input').val();
                //加载框
                var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                $.ajax({
                    url: "/CoeApply/approve",
                    type: "post",
                    data: {
                        coeApplyId: id,
                        type: 1,
                        remark: reason,
                        updateTime: updateTime,
                        applyId: applyId,
                        nationId: nationId,
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        if (data.result) {
                            layer.msg('操作成功', {time: 1000});
                            waitRun();
                        } else {
                            parent.layer.confirm(data.errorMsg, {
                                icon: 7,
                                btn: ['确认']
                            },function () {
                                location.reload(true);
                            })
                        }
                    }
                })
            }
        });
    });
});

function waitRun(){
    setTimeout(function(){
        location.reload(true);
    }, 800)
}

//展示保存和审核的逻辑
function statusApprove() {
    if (canApprove == false) {
        $("#approve").hide();
    }
    if (canSave == false) {
//        $("#edit").children(":enabled").attr("disabled", true);
        $("input").attr("disabled", "disabled");
        $("textarea").attr("readonly", "readonly");
        $("select").attr("disabled", "disabled");
        $("#submits").hide();
    }
}

// function inputStatus(status) {
//     if (status) {
//         $("input").attr("disabled", "disabled");
//         $("textarea").attr("readonly", "readonly");
//         $("select").attr("disabled", "disabled");
//     }
// }

//删除
function delete_file(id){
    var studentNo = $("#studentNo").val();
    console.log(id);
    parent.layer.confirm('是否执行' + '删除'+ '功能？', {
        icon: 2,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/coeAttachmentInfo/delete",
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
                                window.location.reload();
                                parent.layer.closeAll();
                            }
                        });
                    }
                    if (data.code == 1) {
                        layer.confirm('操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                window.location.reload();
                                parent.layer.closeAll();
                            }
                        });
                    }
                    if (data.code == 2) {
                        layer.confirm('当前登录人不是此条记录的操作人，无法删除！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                window.location.reload();
                                parent.layer.closeAll();
                            }
                        });
                    }
                }
            });
        }
    });
}
