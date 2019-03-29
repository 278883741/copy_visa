//跳转到回访记录列表
function toVisitURL(studentNo) {
    window.open('/visit?studentNo=' + studentNo+'&visitCase=1');
}
//跳转到材料清单列表
function toMaterialList(studentNo) {
    window.open('/material?studentNo=' + studentNo);
}
function toFee(studentNo){
    window.open('http://192.168.0.2/finance/see.aspx?StudentID='+studentNo);
}
// 跳转CC拨打页
function toPhone(studentNo, studentName){
    $.ajax({
        type : "post",
        url : "/student/getCallCenterUrl/new",
        data : {
            studentNo : studentNo,
            studentName : studentName
        },
        success : function(data){
            if(data != ''){
                window.open(data);
            }else{
                layer.alert("呼叫中心注册失败，请联系管理员！");
            }
        }
    });
}

//跳转到后续列表页
function toServiceURL(studentNo,status){
    //1、检查该学生是否有该服务
    $.ajax({
        type:'GET',
        url:  "/student/service?studentNo=" + studentNo +"&serviceId=20",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (data) {
            if(data==0) {
                layer.alert('该学生没有这项业务');
            }
            else{
                if(status>=90){
                    window.open('/followService/list?studentNo=' + studentNo);
                }
                else{
                    layer.confirm( '当前服务进程不在此项服务中！是否继续前往？', {
                        icon: 1,
                        btn: ['确认', '取消']
                        , btn1: function (index, layero) {
                            window.open('/followService/list?studentNo=' + studentNo);
                            parent.layer.closeAll();
                        }
                        , btn2: function (index, layero) {
                            parent.layer.closeAll();
                        }
                    });
                }
            }
        }
    });
}

//跳转到签证申请列表页
function toVisaURL(studentNo,status){
    //1、检查该学生是否有该服务
    $.ajax({
        type:'GET',
        url:  "/student/service?studentNo=" + studentNo +"&serviceId=19",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (data) {
            if(data==0) {
                layer.alert('该学生没有这项业务');
            }
            else{
                if(status>=60){
                    window.open('/visaApply?studentNo=' + studentNo);
                }
                else{
                    layer.confirm( '当前服务进程不在此项服务中！是否继续前往？', {
                        icon: 1,
                        btn: ['确认', '取消']
                        , btn1: function (index, layero) {
                            window.open('/visaApply?studentNo=' + studentNo);
                            parent.layer.closeAll();
                        }
                        , btn2: function (index, layero) {
                            parent.layer.closeAll();
                        }
                    });
                }
            }
        }
    });
}

//跳转到文书列表页
function toCopyURL(studentNo,status){
    //1、检查该学生是否有该服务
    $.ajax({
        type:'GET',
        url:  "/student/service?studentNo=" + studentNo +"&serviceId=17",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (data) {
            if(data==0) {
                layer.alert('该学生没有这项业务');
            }
            else{
                if(status>=10){
                    window.open('/copyInfo?studentNo=' + studentNo);
                }
                else{
                    layer.confirm( '当前服务进程不在此项服务中！是否继续前往？', {
                        icon: 1,
                        btn: ['确认', '取消']
                        , btn1: function (index, layero) {
                            window.open('/copyInfo?studentNo=' + studentNo);
                            parent.layer.closeAll();
                        }
                        , btn2: function (index, layero) {
                            parent.layer.closeAll();
                        }
                    });
                }
            }
         }
    });
}
//弹出首次审批
function toFirstConfirm(id){
    parent.layer.confirm('是否通过审核？', {
        icon: 7,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/student/Action_auditFirst",
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
        // var businessId =data.id;
        // var caseId =2;
        // index = layer.open({
        //     type: 2,
        //     title:"审批",
        //     area: ['50%', '60%'],
        //     content: "/auditApply/addPage?businessId="+businessId+"&caseId="+caseId
        // });

    // layer.alert(function(){
    //     $.ajax({
    //         type:'GET',
    //         url:  "/followService/addTime?id=" + id +"&studentTime="+timeStamp,
    //
    //         dataType: 'json',
    //         error: function () {
    //             console.log("D");
    //         },
    //         success: function (json) {
    //             location.href = '/studentInfo/list';
    //         }
    //     });
    // });
}

//弹出最后审批
function toLastConfirm(id){
    parent.layer.confirm('是否通过审核？', {
        icon: 7,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/student/Action_auditLast",
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
    // var businessId =data.id;
    // var caseId =9;
    // index = layer.open({
    //     type: 2,
    //     title:"审批",
    //     area: ['50%', '60%'],
    //     content: "/auditApply/addPage?businessId="+businessId+"&caseId="+caseId
    // });

    // layer.alert(function(){
    //     $.ajax({
    //         type:'GET',
    //         url:  "/followService/addTime?id=" + id +"&studentTime="+timeStamp,
    //
    //         dataType: 'json',
    //         error: function () {
    //             console.log("D");
    //         },
    //         success: function (json) {
    //             location.href = '/studentInfo/list';
    //         }
    //     });
    // });
}

//检查权限和学生服务
function check(studentNo,serviceId){
    //1、检查该学生是否有该服务
    $.ajax({
        type:'GET',
        url:  "/student/service?studentNo=" + studentNo +"&serviceId="+serviceId,
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (data) {
            if(data==0) {
                layer.alert('该学生没有这项业务');
            }
        }
    });
    return true;
    //2、该用户是否有操作该服务的权限
}
//跳转院校申请列表页
function toApplyCollegeList(studentNo,status) {
    //1、检查该学生是否有该服务
    $.ajax({
        type:'GET',
        url:  "/student/service?studentNo=" + studentNo +"&serviceId=18",
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (data) {
            if(data==0) {
                if($("#checkPermission").val() == "true"){
                    layer.alert('该学生没有这项业务');
                }else{
                layer.open({
                    title: '此学生没有该项业务,是否要添加?',
                    type: 2,
                    area: ['370px', '180px'],
                    fixed: false, //不固定
                    content: '/student/addCollege?studentNo='+studentNo
                });
                }
            }
            else{
                if(status>=20){
                    window.open('/apply?studentNo=' + studentNo);
                }
                else{
                    layer.confirm( '当前服务进程不在此项服务中！是否继续前往？', {
                        icon: 1,
                        btn: ['确认', '取消']
                        , btn1: function (index, layero) {
                            window.open('/apply?studentNo=' + studentNo);
                            parent.layer.closeAll();
                        }
                        , btn2: function (index, layero) {
                            parent.layer.closeAll();
                        }
                    });
                }
            }
        }
    });

}

$("#studentCollege_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/student/addService?studentNo=" + $("#studentNo").val() +"&addMessage="+$("#addMessage").val(),
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            window.open('/apply?studentNo=' + $("#studentNo").val());
            parent.layer.closeAll();
        }
    });
})

$("#studentCollege_callback").click(function(){
    //window.parent.location.reload();
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
})


//跳转学生详情页
function toStudentDetail(studentNo) {
    window.open('/student/detail?studentNo=' + studentNo);
}
//渠道学生详情
function toChannelStudentDetail(studentNo) {
   window.open('/channelStudent/detail?studentNo=' + studentNo);
}

//跳转定校列表
function toPlanCollegeList(studentNo) {
    window.open('/planCollege/list?studentNo=' + studentNo);
}

//高级查询
function toSearchPage(){
    var branch = parent.$("#selectBranch").val();
    layer.open({
        title: '高级查询',
        type: 2,
        area: ['1000px', '358px'],
        fixed: false, //不固定
        content: "/search"
    });
}


function query(branch,nation,dateStart,dateEnd,copy,visaOperator,copier,copyOperator,copyMaker,visaSendDate_Start,visaSendDate_End,visaOperatorDate_Start,visaOperatorDate_End,salesConsultant,birthdayStart,birthdayEnd,agencyName,registerStatus) {
    $("#selectBranch").val(branch);
    $("#selectNation").val(nation);
    $("#dateStart").val(dateStart);
    $("#dateEnd").val(dateEnd);

    $("#copy").val(copy);
    $("#visaOperator").val(visaOperator);
    $("#copier").val(copier);

    $("#copyOperator").val(copyOperator);
    $("#copyMaker").val(copyMaker);

    $("#visaSendDate_Start").val(visaSendDate_Start);
    $("#visaSendDate_End").val(visaSendDate_End);
    $("#visaOperatorDate_Start").val(visaOperatorDate_Start);
    $("#visaOperatorDate_End").val(visaOperatorDate_End);

    $("#salesConsultant").val(salesConsultant);

    $("#birthdayStart").val(birthdayStart);
    $("#birthdayEnd").val(birthdayEnd);
    $("#agencyName").val(agencyName);

    $("#registerStatus").val(registerStatus);

    layer.closeAll();
    parent.oTable1.fnDraw();
}
function search() {
    var branch = $("#selectBranchSearch").find("option:selected").val();
    var nation = $("#selectNationSearch").find("option:selected").val();
    var dateStart = $("#dateStartSearch").val();
    var dateEnd = $("#dateEndSearch").val();

    var copy = $("#copySearch").val();
    var visaOperator = $("#visaOperatorSearch").val();
    var copier = $("#copierSearch").val();

    var copyOperator = $("#copyOperatorSearch").val();
    var copyMaker = $("#copyMakerSearch").val();

    var visaSendDate_Start = $("#visaSendDate_Start_child").val();
    var visaSendDate_End = $("#visaSendDate_End_child").val();
    var visaOperatorDate_Start = $("#visaOperatorDate_Start_child").val();
    var visaOperatorDate_End = $("#visaOperatorDate_End_child").val();

    var salesConsultant = $("#salesConsultantSearch").val();

    var birthdayStart = $("#birthdayStartSearch").val();
    var birthdayEnd = $("#birthdayEndSearch").val();
    var agencyName = $("#agencyNameSearch").val();

    var registerStatus = $("#registerStatusSearch").val();

    parent.query(branch,nation,dateStart,dateEnd,copy,visaOperator,copier,copyOperator,copyMaker,visaSendDate_Start,visaSendDate_End,visaOperatorDate_Start,visaOperatorDate_End,salesConsultant,birthdayStart,birthdayEnd,agencyName,registerStatus);

}

function repeat() {
    var branch = "";
    var nation = "";
    var dateStart = "";
    var dateEnd = "";

    var copy = "";
    var visaOperator = "";
    var copier = "";

    var copyOperator = "";
    var copyMaker = "";

    $("#selectBranchSearch").val(branch);
    $("#selectNationSearch").val(nation);
    $("#dateStartSearch").val(dateStart);
    $("#dateEndSearch").val(dateEnd);

    $("#copySearch").val(copy);
    $("#visaOperatorSearch").val(visaOperator);
    $("#copierSearch").val(copier);

    $("#copyOperatorSearch").val(copyOperator);
    $("#copyMakerSearch").val(copyMaker);

    // parent.query(branch,nation,dateStart,dateEnd,copy,visaOperator,copier,copyOperator,copyMaker);

}
// 转案记录 - 老系统； type：1-文案； 2-文书；3-签证
function transferRecord_old(studentNo, type){
    var typeName = "";
    if(type == 1) typeName = "文案员";
    if(type == 2) typeName = "文书员";
    if(type == 3) typeName = "业务员";
    layer.open({
        title: '转案记录-'+typeName,
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/transfer/record_old?studentNo='+studentNo+"&operatorType="+type
    });
}

// 转案记录 - 新系统； type：1-高签； 0-制作文案
function transferRecord_new(studentNo, type){
    var typeName = "";
    if(type == 0) typeName = "制作文案";
    if(type == 1) typeName = "文签顾问";
    layer.open({
        title: '转案记录-'+typeName,
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/transferNew/record_new?studentNo='+studentNo+"&operatorType="+type
    });
}

//两次寄出绩效审核信息
function sendAuditInfo(studentNo,caseId){
    layer.open({
        title: '审核信息',
        type: 2,
        area: ['700px', '228px'],
        fixed: false, //不固定
        content: "/doubleSendAuditInfo?studentNo="+studentNo+"&caseId="+caseId
    });
}


