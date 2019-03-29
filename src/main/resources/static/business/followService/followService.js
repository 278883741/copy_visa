//跳转编辑页
function toEditPage(id){
    location.href = '/followService/editPage?id=' + id;
}

//跳转编辑页
function toEditURL(id){
    location.href="/studentCopyInfo/edit?id="+id+"&studentNo="+$("#studentNo").val();
}

function toDeletePage(id){
    parent.layer.confirm('确认删除该条结果吗？', {
        icon: 3,
        btn: ['确认', '取消'],
        btn1: function (index, layero) {
            $.ajax({
                url: "/followService/deletePage",
                type: "post",
                data: {id : id,studentNo:$("#studentNo").val()},
                success: function (data) {
                    console.log(data)
                    if (data) {
                        layer.msg('操作成功', {time: 1000});
                        oTable1.fnDraw();
                    } else {
                        layer.msg('操作失败', {time: 1000});
                    }
                }
            });
        }
    });
}

function toDetailPage(id) {
    location.href = '/followService/detailPage?id=' + id;
}

function toMessage(id,applyId){
    location.href = '/followService/applicationMessage?id=' + id + "&type="+1+"&applyId="+applyId+"&studentNo="+$("#studentNo").val();
}

function toUpdatePage(id,applyId){
    location.href = '/followService/applicationMessage?id=' + id + "&type="+2+"&applyId="+applyId+"&studentNo="+$("#studentNo").val();
}

function toResultListPage(id){
    location.href = '/followService/resultList?applyId='+id+"&studentNo="+$("#studentNo").val();
}

//跳转申请页
function toReturnUrl(){
    location.href="/followService/list?studentNo="+$("#studentNo").val();
}

function toAddMessageUrl(){
    location.href="/followService/addApplicationMessage";
}

$("#toAddMessage_id").click(function(){
    location.href = "/followService/addApplicationMessage?applyId="+applyId+"&studentNo="+$("#studentNo").val();
})
//删除
function toDeleteURL(id){
    var studentNo = $("#studentNo").val();
    parent.layer.confirm('是否执行' + '删除'+ '功能？', {
        icon: 2,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/studentCopyInfo/delete?id="+ id +"&studentNo="+studentNo,
                type: "get",
                success: function (data) {
                    if (data) {
                        layer.confirm( '操作成功！', {
                            icon: 1,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                changeTable();
                                parent.layer.closeAll();
                            }
                        });
                    } else {
                        layer.confirm( '操作失败，请联系管理员！', {
                            icon: 2,
                            btn: ['确认']
                            , btn1: function (index, layero) {
                                changeTable();
                                parent.layer.closeAll();
                            }
                        });
                    }
                }
            });
        }
    });
}

function toStudentConfirm_follow(id) {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['370px', '180px'],
        fixed: false, //不固定
        content: '/followService/studentTime?id='+id
    });
}

function toSchoolConfirm_follow(id) {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['370px', '180px'],
        fixed: false, //不固定
        content: '/followService/schoolTime?id='+id
    });
}

$("#school_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/followService/addTime?id=" + $("#id").val() +"&schoolTime="+$("#school_date").val(),
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
})

$("#student_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/followService/addTime?id=" + $("#id").val() +"&studentTime="+$("#student_date").val(),
        dataType: 'json',
        error: function () {
            console.log("D");
        },
        success: function (json) {
            window.parent.location.reload();
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }
    });
})
function changeTable(){
    oTable1.fnDraw();
}
function getTimeString_day(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m >= 10 ? m : '0' + m;
    var d = date.getDate();
    d = d >= 10 ? d : '0' + d;
    return y + '-' + m + '-' + d;
}


function getTimeString(date){
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m >= 10 ? m :'0'+m;
    var d = date.getDate();
    d = d >= 10 ? d :'0'+d;
    var h = date.getHours();
    h = h >= 10 ? h :'0'+h;
    var minute = date.getMinutes();
    minute = minute >= 10 ? minute :'0'+minute;
    var second = date.getSeconds();
    second = second >= 10 ? second :'0'+second;
    return  y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
}

//跳转到回访记录列表
function toVisitURL() {
    var studentNo=$("#studentNo").val();
    location.href='/visit?studentNo=' + studentNo+'&visitType=3';

}
$("#application_result").change(function(){
    changeStatus();
})
function changeStatus(){
    //录取
    if($('#application_result').children('option:selected').val()==1){
        $(".reply_reason").hide();
        $(".offer_info").show();
        $(".studetn_info").show();
        $(".school_info").show();
        //拒绝
    }else if($('#application_result').children('option:selected').val()==2){
        $(".reply_reason").show();
        $(".offer_info").hide();
        $(".studetn_info").show();
        $(".school_info").hide();
    }else if($('#application_result').children('option:selected').val()==0){
        $(".reply_reason").hide();
        $(".offer_info").show();
        $(".studetn_info").show();
        $(".school_info").show();
    }
}
changeStatus();

function success(){
    layer.confirm('操作成功，是否跳转到列表页？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
    }, function(){
        parent.layer.close();
    });
}

function fail(){
    layer.confirm('操作失败，是否跳转到列表页？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
    }, function(){
        parent.layer.close();
    });
}

function postData(type) {
    boolResult = false;
    if(type == '1'){
        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
    }else if(type == '2'){
        if($("#application_result").children("option:selected").val() == 0){
            layer.alert("请选择申请结果",{icon: 0});
            boolResult = true;
            return;
        }
        if($("#application_result_reply_way").children("option:selected").val() == 0){
            layer.alert("请选择申请结果到达方式",{icon: 0});
            boolResult = true;
            return;
        }
        var resultTime = ""
        if($("#application_result").children("option:selected").val() == "1"){
            resultTime = $("#application_result_date").val();
        }else{
            resultTime = $("#application_result_date_refuse").val();
        }
        console.log("reply_reason_id:"+$("#reply_reason_id").val());
        var dataModel={
            "id":$("#id").val(),
            "applyId":$("#applyId").val(),//申请id,关联后续申请id
            "resultType":$("#application_result").children("option:selected").val(),//申请结果
            "replyWay":$("#application_result_reply_way").children("option:selected").val(),//申请结果方式
            "resultDate":resultTime,//回复日期
            "offerAttachment":$("#input_fileName").val(),//offer附件地址
            "replyDeadline":$("#application_result_dateline").val(),//截止日期
            "studentReplyDate":$("#application_student_reply_date").val(),//学生确认日期
            "argueDate":$("#application_argue_date").val(),//学生实际回复日期
            "schoolReplyDate":$("#application_school_reply_date"),//学校确认日期
            "resultComment":$("#textarea_comment").val(),//备注
            "reply_reason":$("#reply_reason_id").val(),//拒绝原因
            "studentNo":$("#studentNo").val()//学生学号
        }
        dataModel["type"] = type;
        console.log(dataModel);
        $.post("/followService/save", {"data": JSON.stringify(dataModel)},function(data,status) {

            if (data.result) {
                boolResult = true;
                if($("#auditStatus").val() == "4"){
                    layer.confirm('操作成功，是否跳转到详情页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href = "/followService/applicationMessage?type=1&applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val()+"&id="+$("#id").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }else{
                    layer.confirm('操作成功，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }
            } else {
                boolResult = true;
                if(data.errorCode == "2"){
                    layer.confirm(data.errorMsg, {
                        btn: ['确定'] //按钮
                    }, function(){
                        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }else{
                    layer.confirm('操作失败，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href = "/followService/resultList?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }

            }
        });
    }
}
