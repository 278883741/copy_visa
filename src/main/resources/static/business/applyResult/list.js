function toDetail(id,studentNo) {
    location.href='/applyResult/showDetailPage?id=' + id+'&applyId='+$("#applyId").val()+'&studentNo='+studentNo;
}
function toEditURL(id,auditStatus,nationStatus){
    if(auditStatus != null){
        if(auditStatus == "2"){
            layer.alert('当前offer结果已通过审核,无法进行修改');
            return false;
        }
        else{
            var studentNo=$("#studentNo").val();
            var applyId = $("#applyId").val();
            var nationId = $("#nationId").val();
            if(nationId == "58" || nationId == "59" || nationStatus != 0){
                toAddGroup(studentNo,4,applyId,id,nationStatus);
            }else{
                location.href='/applyResult/editPage?id=' + id+'&applyId='+$("#applyId").val()+'&studentNo='+$("#studentNo").val();
            }
        }
    }
}
//跳转到Offer结果的添加页面
function toAddURL(){
    if($("#applyStatus").val() == "10"){
        layer.msg("申请状态为“未提交院校申请”不允许录入offer")
        return;
    }
    else{
        var studentNo=$("#studentNo").val();
        var applyId = $("#applyId").val();
        var nationId = $("#nationId").val();
        var nationStatus = $("#nationStatus").val();
        if(nationId == "58" || nationId == "59" || nationStatus != 0){
            toAddGroup(studentNo,3,applyId,"",nationStatus);
        }else{
            location.href='/applyResult/editPage?applyId='+$("#applyId").val()+'&studentNo='+$("#studentNo").val();
        }
    }
}

function toAddGroup(studentNo,type,id,applyId,nationStatus){
    layer.open({
        title: '选择申请留学国家',
        type: 2,
        area: ['450px', '238px'],
        fixed: false, //不固定
        content: "/nation/add?studentNo="+studentNo+"&type="+type+"&id="+id+"&applyId="+applyId+"&nationStatus="+nationStatus,
    });
}

function returnApplyList(){
    var id=$("#studentNo").val();
    location.href='/apply?studentNo=' + id;
}



function toSchoolConfirm(id) {
    layer.open({
        title: '消息:',
        type: 2,
        area: ['370px', '180px'],
        fixed: false, //不固定
        content: '/applyResult/addSchoolTime?id='+id
    });
}
function toStudentConfirm(id){
    layer.open({
        title: '消息:',
        type: 2,
        area: ['370px', '180px'],
        fixed: false, //不固定
        content: '/applyResult/addStudentTime?id='+id
    });
}

$("#student_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/applyResult/addTime?id=" + $("#id").val() +"&studentTime="+$("#student_date").val(),
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

$("#school_submits").click(function(){
    $.ajax({
        type:'GET',
        url:  "/applyResult/addTime?id=" + $("#id").val() +"&schoolTime="+$("#school_date").val(),
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

// function toSendBack(id){
//     parent.layer.confirm('确定要退回该条记录吗？', {
//         icon: 3,
//         btn: ['确认', '取消'],
//         btn1: function (index, layero) {
//             $.ajax({
//                 url: "applyResult/remove",
//                 type: "post",
//                 data: {applyResultId : id},
//                 success: function (data) {
//                     if (data) {
//                         layer.msg('操作成功', {time: 1000});
//                         oTable1.fnDraw();
//                     } else {
//                         layer.msg('操作失败', {time: 1000});
//                     }
//                 }
//             });
//         }
//     });
// }

//删除
function toDelete(id){
    parent.layer.confirm('确认删除该条结果吗？', {
        icon: 3,
        btn: ['确认', '取消'],
        btn1: function (index, layero) {
            $.ajax({
                url: "applyResult/remove",
                type: "post",
                data: {applyResultId : id},
                success: function (data) {
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
