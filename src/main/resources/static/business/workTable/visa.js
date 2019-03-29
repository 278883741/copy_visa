//跳转到签证申请的详情页面
function toDetail(id,studentNo) {
    window.open("/visaApply/detailPage?id=" + id+"&studentNo="+studentNo);
}

//跳转到获签信息的详情页面
function toRecordDetail(studentNo){
    window.open("/visaRecord/addPage?studentNo="+studentNo);
}

//跳转到签证申请的详情页面
function toStudentSettleDetail(studentNo) {
    window.open("/student/settle?studentNo="+studentNo);
}

//跳转到签证申请的详情页面
function toApplyResult(id,applyId,studentNo) {
    window.open("/applyResult/detailPage?id="+id+"&applyId="+applyId+"&studentNo="+studentNo);
}
