function selectRole() {
    layer.open({
        title: '角色选择',
        type: 2,
        area: ['700px', '450px'],
        fixed: false, //不固定
        content: '/user/roleTree'
    });
}
    function selectGroupRole(){
        layer.open({
            title: '小组选择',
            type: 2,
            area: ['700px', '450px'],
            fixed: false, //不固定
            content: '/user/groupTree'
        });
}