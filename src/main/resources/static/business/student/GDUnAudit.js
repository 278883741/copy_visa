function toAudit(studentNo,id) {
    var type = $("#type").find("option:selected").val();
    if(type == 1){
        window.open("/visaApply/detailPage?id=" +id+"&studentNo="+studentNo);
    }
    if(type == 2){
        window.open("/visaApply/detailPage?id=" +id+"&studentNo="+studentNo);
    }
    if(type == 3){
        window.open("/student/settle?studentNo="+studentNo);
    }
    if(type == 0){
        location.reload(true);
    }
}