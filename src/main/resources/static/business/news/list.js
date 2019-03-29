
function toEditURL(id){
    location.href='/news/editPage?id=' + id;
}
//跳转到Offer结果的添加页面
function toAddURL(){
    location.href='/news/editPage';
}

//公告详情
function toDetail(id){
    layer.open({
        title: '公告详情',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/news/detailPage?id=' + id,
        btn: ['确定']
    });
}

//启用
function enableData(id) {
    layer.alert("确定要启用此项内容吗?",function() {
        $.post("/news/save", {"id": id, "enableStatus": "1"}, function (data, status) {
            if (data) {
                window.location.reload();
            }
        });
    })
}
//禁用
function disableData(id) {
    layer.alert("确定要禁用此项内容吗?",function() {
        $.post("/news/save", {"id": id, "enableStatus": "0"}, function (data, status) {
            if (data) {
                window.location.reload();
            }
        });
    })
}