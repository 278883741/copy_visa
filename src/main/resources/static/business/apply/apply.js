//添加申请
function addPage(){
    var studentNo=$("#studentNo").val();
    var nationStatus = $("#nationStatus").val();
    if($("#nationId").val() == 58 || $("#nationId").val() == 59 || nationStatus != 0){
        toAddGroup(studentNo,1,"",nationStatus);
    }else{
        location.href='/apply/addPage?studentNo='+studentNo+'&nationId=';
    }
}

function toAddGroup(studentNo,type,id,nationStatus){
    layer.open({
        title: '选择申请留学国家',
        type: 2,
        area: ['450px', '238px'],
        fixed: false, //不固定
        content: "/nation/add?studentNo="+studentNo+"&type="+type+"&id="+id+"&nationStatus="+nationStatus,
    });
}


//查询费用
function queryFee(){
    var studentNo=$("#studentNo").val();
    location.href='/cost/page?studentNo='+studentNo;
}

//下载pdf
/*function queryFee(){
    var studentNo=$("#studentNo").val();
    location.href='/cost/page?studentNo='+studentNo;
}*/
//跳转补件列表页
function supplementPage(id) {
    var studentNo = $("#studentNo").val();
    location.href = '/supplementInfo?applyId=' + id + '&studentNo='+studentNo;
}

//跳转回访列表页
function visitPage(id) {
    var studentNo = $("#studentNo").val();
    location.href = '/visit?businessId=' + id + "&studentNo=" + studentNo + "&visitCase=2";
}

//跳转offer结果列表页
function resultPage(id) {
    var studentNo = $("#studentNo").val();
    location.href = '/applyResult?applyId=' + id + '&studentNo=' + studentNo;
}

//跳转编辑页
function toEditURL(id,studentNo,nationStatus) {
    if($("#nationId").val() == 58 || $("#nationId").val() == 59 || nationStatus != 0){
        toAddGroup(studentNo,2,id,nationStatus);
    }else{
        location.href = '/apply/editPage?id=' + id +"&studentNo=" + studentNo;
    }

}

//跳转申请信息编辑页
function toEditOthers(id,studentNo) {
    location.href = '/apply/editOthers?id=' + id +"&studentNo=" + studentNo;
}

//跳转申请信息详情页
function toDetailURL(id) {
    location.href = '/apply/detailPage?id=' + id;
}

//跳转到coe申请页
function coeApply(id) {
    var studentNo = $("#studentNo").val();
    location.href = '/coeApply?applyId=' + id + '&studentNo=' + studentNo;
}

//提示是否最终接受Offer
function confirmCollege(id) {
    layer.confirm('是否设置为学生最终录取院校？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        //加载框
        var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
        $.ajax({
            url: "/apply/acceptOffer",
            type: "post",
            data: {"applyId": id},
            success: function (data) {
                layer.close(loadIndex);
                location.href = "/apply?studentNo=" + $("#studentNo").val();
            }
        });
    }, function () {
        parent.layer.close();
    });
}

function assign(id) {
    layer.open({
        title: '选择外联顾问',
        type: 2,
        area: ['500px', '350px'],
        fixed: false, //不固定
        btn: ['确定'],
        yes: function (index) {
            //获得选中的顾问
            var body = layer.getChildFrame('body', index);
            var oaid = body.contents().find("#connector").val();
            var name = body.contents().find("#connector").find("option:selected").text();
            if (oaid != "") {
                //加载框
                var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
                $.ajax({
                    url: "/apply/assign",
                    type: "post",
                    data: {
                        applyId: id,
                        oaid: oaid,
                        connector: name
                    },
                    success: function (data) {
                        layer.close(loadIndex);
                        layer.close(index);
                        if (data) {
                            location.reload(true);
                        } else {
                            layer.msg('操作失败', {time: 1000});
                        }
                    },
                    error: function (data) {
                        layer.close(loadIndex);
                    }
                })
            } else {
                layer.alert('请选择外联顾问！', {
                    icon: 0
                });
            }
        },
        content: '/planCollege/connector'
    });
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

function getTimeString_day(date) {
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m >= 10 ? m : '0' + m;
    var d = date.getDate();
    d = d >= 10 ? d : '0' + d;
    return y + '-' + m + '-' + d;
}