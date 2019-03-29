
function toGroupUpdatePage(id){
    location.href = "/userGroup/updateGroup?id="+id;
}
function toGroupDeletePage(id){
    parent.layer.confirm('是否执行' + '删除'+ '功能？', {
        icon: 2,
        btn: ['确认', '取消']
        , btn1: function (index, layero) {
            $.ajax({
                url: "/userGroup/toUpdate?id="+id,
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

    // layer.alert("确定要删除此项记录吗?",function(){
    //     location.href = "/userGroup/toUpdate?id="+id;
    // })

}
function changeTable(){
    oTable1.fnDraw();
}

function enableData(id) {
    layer.alert("确定要启用此项内容吗?",function(){
        $.post("/userGroup/editEnableStatus",{"id":id,"enableStatus":"1"},function(data, status){
            if(data){
                window.location.reload();
            }
        });
    })
}

function disableData(id) {
    layer.alert("确定要禁用此项内容吗?",function(){
        $.post("/userGroup/editEnableStatus",{"id":id,"enableStatus":"0"},function(data, status){
            if(data){
                window.location.reload();
            }
        });
    })
}
function selectNation(){
    layer.open({
        title: '小组选择',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/userGroup/nationTree'
    });
}

function toAddGroup(id){
        layer.open({
        title: '添加用户组',
        type: 2,
        area: ['450px', '238px'],
        fixed: false, //不固定
        content: "/userGroup/add?id="+id,
    });
}

function toShowGroup(id){
    layer.open({
        title: '查看用户组',
        type: 2,
        area: ['450px', '238px'],
        fixed: false, //不固定
        content: "/userGroup/add?id="+id+"&type=1",
    });
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
