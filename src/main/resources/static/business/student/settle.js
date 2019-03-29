$(function () {
    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
    $(this).removeData("bs.modal");
    });

    $("#offer_upload").click(function(){
    $("#upload_office").attr("href",$("#offerAttachment").val());
    })
});

function returnStudentDetail(studentNo){
    if(studentNo.indexOf('TY') == 0){
        location.href = "/channelStudent/detail?studentNo=" + studentNo;
    }
    else{
        location.href = "/student/detail?studentNo=" + studentNo;
    }}

//跳转学生列表页
function returnStudentList(studentNo) {
    location.href="/studentInfo";
}

var auditStatus_apply = $("#input_auditStatus_apply").val();
// if ($("#input_isAuditUser").val() == "false") {
//     $("#button_audit").hide();
// }
if ($("#input_auditStatus_apply").val() == "") {
    $("#button_audit").hide();
}
if ($("#input_auditStatus_apply").val() == "3" || $("#input_auditStatus_apply").val() == "4")
    $("#button_audit").hide();

//审批
// function approve(){
//     var studentNo = $("#studentNo").val();
//     var updateTime = $("#updateTime").val();
// //    $.post("/student/delay/checkStatus",{"date":updateTime,"studentNo":studentNo},function (data,status) {
// //        var data = JSON.parse(data);
// //        if (data.code == 0) {
// //            parent.layer.confirm("待审批内容已修改,请重新审批", {
// //                icon: 7,
// //                btn: ['确定']
// //            }, function () {
// //                window.location.reload();
// //            });
// //        }
// //        else if (data.code == 1) {
//     layer.prompt({
//         formType: 2,
//         title: '请添加备注',
//         btn: ['通过', '拒绝'],
//         yes: function (index, layero) {
//             var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
//             var reason = layero.find('.layui-layer-input').val();
//             $.ajax({
//                 url: "/student/delay/approve",
//                 type: "post",
//                 data: {
//                     type: 2,
//                     remark: reason,
//                     updateTime: updateTime,
//                     studentNo: studentNo
//                 },
//                 success: function (data) {
//                     layer.close(loadIndex);
//                     console.log(data);
//                     data = JSON.parse(data);
//                     layer.close(index);
//                     var code = data.code;
//                     if(data.code == 0){
//                         parent.layer.confirm("待审批内容已修改,请重新审批",{
//                             icon:7,
//                             btn:['确定']
//                         },function () {
//                             location.reload(true);
//                         });
//                     }
//                     if (data.code == 1) {
//                         layer.confirm('操作成功，是否跳转到学生详情页？', {
//                             btn: ['确定', '取消'] //按钮
//                         }, function () {
//                             location.href = "/student/detail?studentNo=" + studentNo;
//                         }, function () {
//                             location.reload(true);
//                         });
//                     }
//                     if (data.code == 2) {
//                         layer.confirm('操作失败，是否跳转到学生详情页？', {
//                             btn: ['确定', '取消'] //按钮
//                         }, function () {
//                             location.href = "/student/detail?studentNo=" + studentNo;
//                         }, function () {
//                             location.reload(true);
//                         });
//                     }
//                 }
//             })
//         },
//         btn2: function (index, layero) {
//             var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
//             var reason = layero.find('.layui-layer-input').val();
//             $.ajax({
//                 url: "/student/delay/approve",
//                 type: "post",
//                 data: {
// //					studentDelayId : id,
//                     type: 1,
//                     remark: reason,
//                     updateTime: updateTime,
//                     studentNo: studentNo,
//                 },
//                 success: function (data) {
//                     layer.close(loadIndex);
//                     console.log(data);
//                     data = JSON.parse(data);
//                     layer.close(index);
//                     var code = data.code;
//                     if(data.code == 0){
//                         console.log(0);
//                         parent.layer.confirm("待审批内容已修改,请重新审批",{
//                             icon:7,
//                             btn:['确定']
//                         },function () {
//                             location.reload(true);
//                         });
//                     }
//                     if (data.code == 1) {
//                         console.log(1);
//                         layer.confirm('操作成功，是否跳转到学生详情页？', {
//                             btn: ['确定', '取消'] //按钮
//                         }, function () {
//                             location.href = "/student/detail?studentNo=" + studentNo;
//                         }, function () {
//                             location.reload(true);
//                         });
//                     }
//                     if (data.code == 2) {
//                         console.log(2);
//                         layer.confirm('操作失败，是否跳转到学生详情页？', {
//                             btn: ['确定', '取消'] //按钮
//                         }, function () {
//                             location.href = "/student/detail?studentNo=" + studentNo;
//                         }, function () {
//                             location.reload(true);
//                         });
//                     }
//                 }
//             })
//         }
//     });
// }