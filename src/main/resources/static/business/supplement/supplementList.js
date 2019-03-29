function toAddURL(applyId,studentNo) {
    location.href='/supplementInfo/add?applyId=' + applyId+'&studentNo='+studentNo;
}
function toDetail(id,applyId,studentNo) {
    location.href='/supplementInfo/detail?id=' + id+'&applyId=' + applyId+'&studentNo='+studentNo;
}
function toEditURL(id,studentNo){
    //var applyId = $("#applyId").val();
    location.href='/supplementInfo/edit?id=' + id+'&studentNo='+studentNo;
}
function collegeList(studentNo){
    location.href="/apply?studentNo="+studentNo;
}

//跳转学生列表页
function returnStudentList(studentNo) {
    location.href="/studentInfo";
}