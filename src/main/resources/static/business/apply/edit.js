

$("#alert").css("visibility","hidden");
function tips(){
    if($("#alert").css("visibility")=="hidden"){
        $("#alert").css("visibility","visible");
    }else{
        $("#alert").css("visibility","hidden");
    }
}

$("#returnPage").click(function(){
    location.href="/apply?studentNo="+$("#studentNo").val();
});
$(function(){
    var studentNo = $("#studentNo").val();
    var currentUser = $("#currentUser").val();
    var planCollegeId = $("#planCollegeId").val();
    var urlDetail =null;
    if(planCollegeId !=null){
         urlDetail="/selectStudentPlanCollegeDetails?planCollegeId="+planCollegeId;
    }

    $("#planDetail").attr("href",urlDetail);
    var urlScheme = '/planCollege/planList?studentNo=' + studentNo;
    $("#planScheme").attr('href',urlScheme);
});


/*

    function postData() {
            var dataModel= {
                "id": $("#id").val(),
                "applyType": $("#applyType").children("option:selected").val(),//申请类型
                "applyStatusName": $("#applyStatusName").val(),//申请状态
                "collegeName": $("#collegeName").val(),//申请院校
                "majorName": $("#majorName").val(),//申请专业
                "schoolLength": $("#schoolLength").val(),//学制
                "courseStartTime": $("#courseStartTime").val(),//开课日期
                "applyWay": $("#applyWay").children("option:selected").val(),//申请方式
                "commitDate": $("#commitDate").val(),//申请提交日期
                "expressCompanyCode": $("#expressCompanyCode").val(),//快递公司
                "expressNo": $("#expressNo").val(),//快递单号
                "collegeCopy":$("#showCopy").text(),
                "collegeMaterial":$("#input_fileName_1").val(),
            }
            console.log(dataModel);
            $.post("/apply/save", {"data": JSON.stringify(dataModel)},function(data,status) {
                if (data) {
                    if (data) {
                        $(".alert-success").text("操作成功").show();
                        $('#alert').attr('class',
                            'alert alert-success').show();
                        setTimeout("$('#alert').hide()", 2000);
                        $("#myModal").modal("hide");
                        location.href = "/apply?studentNo="+$("#studentNo").val();
                    } else {
                        $('#alertText').text('操作失败');
                        $('#alert').attr('class',
                            'alert alert-danger').show();
                        setTimeout("$('#alert').hide()", 2000);
                        $("#myModal").modal("hide");
                    }
                }
            });

    }

    $("#submits").click(function () {
        postData();
    })
*/


function add(){
    var studentNo = $("#studentNo").val();
    boolResult = false;

    if ($("#collegeName").val() == "" || $("#collegeName").val() == '-1' || $("#collegeName").val() == null) {
        layer.alert('请选择院校！', {icon: 0});
        return;
    }
    if ($("#applyType").val() == "" || $("#applyType").val() == '-1' || $("#applyType").val() == null) {
        layer.alert('请选择申请类型！', {icon: 0});
        return;
    }
    if ($("#courseType").val() == "" || $("#courseType").val() == '-1' || $("#courseType").val() == null) {
        layer.alert('请选择专业类型！', {icon: 0});
        return;
    }
    var applyWay=$('#applyWay').children('option:selected').val();
    if(applyWay==""){
        layer.alert('请选择申请方式！', {icon: 0});
        return;
    }
    if ($("#educationSection").val() == "" || $("#educationSection").val() == '-1' || $("#educationSection").val() == null) {
        layer.alert('请选择目标学历！', {icon: 0});
        return;
    }

    if ($("#agency_no").val() == "" || $("#agency_no").val() == '-999' || $("#agency_no").val() == null) {
        layer.alert('请选择合作机构！', {icon: 0});
        return;
    }

    if ($("#majorName").val() == "" || $("#majorName").val() == '-1' || $("#majorName").val() == null) {
        layer.alert('请选择专业！', {icon: 0});
        return;
    }
    if($("#courseName").val() == "" || $("#courseName").val() == '-1' || $("#courseName").val() == null){
        layer.alert('请选择学位！', {icon: 0});
        return;
    }
    var collegeCopy = $("#collegeCopy").children('option:selected').val();
    if(collegeCopy == ""){
        /*layer.alert('请选择文书内容！', {icon: 0});
        return;*/
    }

    $.ajax({
        url: "/apply/add",
        type: "post",
        data:$("#edit").serialize(),
        success: function (data) {
            if (data) {
                boolResult = true;
                layer.confirm('操作成功，是否跳转到列表页？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    location.href="/apply?studentNo="+$("#studentNo").val();
                }, function(){
                    window.location.replace(window.location.href)
                });
            } else {
                boolResult = true;
                layer.confirm('操作失败，是否跳转到列表页？', {
                    btn: ['确定','取消'] //按钮
                }, function(){
                    location.href="/apply?studentNo="+$("#studentNo").val();
                }, function(){
                    window.location.replace(window.location.href)
                });
            }
        }
    });
}


            function saveDate(){
                if ($("#collegeName").val() == "" || $("#collegeName").val() == '-1' || $("#collegeName").val() == null) {
                    layer.alert('请选择院校！', {icon: 0});
                    return;
                }
                if ($("#applyType").val() == "" || $("#applyType").val() == '-1' || $("#applyType").val() == null) {
                    layer.alert('请选择申请类型！', {icon: 0});
                    return;
                }
                if ($("#courseType").val() == "" || $("#courseType").val() == '-1' || $("#courseType").val() == null) {
                    layer.alert('请选择专业类型！', {icon: 0});
                    return;
                }
                var applyWay=$('#applyWay').children('option:selected').val();
                if(applyWay==""){
                    layer.alert('请选择申请方式！', {icon: 0});
                    return;
                }
                if ($("#educationSection").val() == "" || $("#educationSection").val() == '-1' || $("#educationSection").val() == null) {
                    layer.alert('请选择目标学历！', {icon: 0});
                    return;
                }
                if(isClickAgency == true){
                    if ($("#agency_no").val() == "" || $("#agency_no").val() == '-999' || $("#agency_no").val() == null) {
                        layer.alert('请选择合作机构！', {icon: 0});
                        return;
                    }
                }
                if ($("#majorName").val() == "" || $("#majorName").val() == '-1' || $("#majorName").val() == null) {
                    layer.alert('请选择专业！', {icon: 0});
                    return;
                }
                if($("#courseName").val() == "" || $("#courseName").val() == '-1' || $("#courseName").val() == null){
                    layer.alert('请选择学位！', {icon: 0});
                    return;
                }
                var collegeCopy = $("#collegeCopy").children('option:selected').val();
                if(collegeCopy == ""){
                    /*layer.alert('请选择文书内容！', {icon: 0});
                    return;*/
                }

                boolResult = false;
                $.ajax({
                    url: "/apply/save",
                    type: "post",
                    data:$("#edit").serialize(),
                    success: function (data) {
                        if (data) {
                            boolResult = true;
                            layer.confirm('操作成功，是否跳转到列表页？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                location.href="/apply?studentNo="+$("#studentNo").val();
                            }, function(){
                                window.location.replace(window.location.href)
                            });
                        } else {
                            boolResult = true;
                            layer.confirm('操作失败，是否跳转到列表页？', {
                                btn: ['确定','取消'] //按钮
                            }, function(){
                                location.href="/apply?studentNo="+$("#studentNo").val();
                            }, function(){
                                window.location.replace(window.location.href)
                            });
                        }
                    }
                });
        }
//     })
//
//
//     }
//
// }


$("#expressCompanyCode").change(function(){
    // alert($("#expressCompanyCode").find("option:selected").text());
    $("#companyName").val($("#expressCompanyCode").find("option:selected").text());
    $("#companyCode").val($("#expressCompanyCode").find("option:selected").val());
});

$("#applyWay").change(function () {
    if($("#applyWay").find("option:selected").val()!=1){
        $(".send").hide();
    }else{
        $(".send").show();
    }

})
if($("#applyWay").find("option:selected").val()!=1){
    $(".send").hide();
}else{
    $(".send").show();
}

$("#companyName").val($("#expressCompanyCode").find("option:selected").text());
$("#companyCode").val($("#expressCompanyCode").find("option:selected").val());