
//编辑金额
function toEditPage(id, costchinese, costEnglish, money) {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['570px', '270px'],
        fixed: false, //不固定
        content: '/cost/editFeePage?id=' + id + '&costChiness=' + costchinese + '&costEnglish=' + costEnglish +
        '&money=' + money

    });

}


//编辑基本费用
function toBasicEditPage(id, tuition, registrationFee, depositAmount) {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['370px', '240px'],
        fixed: false, //不固定
        content: '/cost/basicEditFeePage?id=' + id + '&tuition=' + tuition + '&registrationFee=' + registrationFee +
        '&depositAmount=' + depositAmount

    });

}


//点击编辑基本费用的保存按钮
$("#basicFeeSubmit").click(function () {
    $.ajax({
        type: 'POST',
        data: $("#basicEditFeeForm").serialize(),
        url: "/cost/basicEditFee",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            if (json) {
                layer.confirm('保存成功！', {
                    icon: 1,
                    btn: ['确认'],
                    btn1: function (index, layero) {
                        window.parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                });
            } else {
                layer.confirm('操作失败，费用中英文名称及金额均不能为空！', {});
            }
        }
    });
})


//点击编辑金额的保存按钮
$("#feeSubmit").click(function () {
   /* $("#createTimeformat").val(new Date($("#createTimeformat").val()));
    $("#updateTimeformat").val(new Date($("#updateTimeformat").val()));*/
    $.ajax({
        type: 'POST',
        data: $("#editFeeForm").serialize(),
        url: "/cost/edit",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            if (json) {
                layer.confirm('保存成功！', {
                    icon: 1,
                    btn: ['确认'],
                    btn1: function (index, layero) {
                        window.parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                });
            } else {
                layer.confirm('操作失败，学费，注册费，押金均不能为空！', {});
            }
        }
    });
})

//添加费用
$("#addFee").click(function () {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['570px', '270px'],
        fixed: false, //不固定
        content: '/cost/addFeePage?studentNo=' + $("#studentNo").val()
    });
})
//新增费用
$("#feeSave").click(function () {
    /*$("#createTimeformat").val(new Date($("#createTimeformat").val()));
    $("#updateTimeformat").val(new Date($("#updateTimeformat").val()));*/
    $.ajax({
        type: 'POST',
        url: "/cost/add",
        dataType: 'json',
        data: $("#add").serialize(),
        error: function (json) {

            console.log("D");
        },
        success: function (json) {
            if (json) {
                layer.confirm('保存成功！', {
                    icon: 1,
                    btn: ['确认'],
                    btn1: function (index, layero) {
                        window.parent.location.reload();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    }
                });
            } else {
                layer.confirm('操作失败，费用中英文名称及金额均不能为空！或者费用类型已达上限，请修改现有费用类型', {});
            }
        }
    });
})

//删除费用
function toDelete(id) {
    parent.layer.confirm('是否删除该费用？', {
        icon: 7,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/cost/deleteById",
                type: "POST",

                data: {
                    id: id,
                },
                success: function (data) {
                    if (data) {
                        layer.confirm('操作成功！', {
                            icon: 1,
                            btn: ['确认'],
                            btn1: function (index, layero) {
                                parent.oTable1.fnDraw();
                                parent.layer.closeAll();
                            }
                        });
                    } else {
                        layer.confirm('操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                parent.oTable1.fnDraw();
                                parent.layer.closeAll();
                            }
                        });
                    }
                }
            });
        }
    });
}


//添加费用
function toStudentSettleDetail(studentNo) {
    window.open("/student/settle?studentNo=" + studentNo);
}





