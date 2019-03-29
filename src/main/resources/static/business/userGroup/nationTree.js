
$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var operator = parent.$("#group_operator").val();
    var groupName = parent.$("#group_name").val();
    var createDate = parent.$("#group_time").val();
    var id = parent.$("#id").val();

    //保存操作
    $("#nation_submits").click(function () {

        var tree = $.fn.zTree.getZTreeObj("nationTree");
        var nodes = tree.getNodesByParam("checked", true, null);
        var len = nodes.length;
        var roleIds = new Array();
        for(var i=0; i<len; i++){
            roleIds[i] = nodes[i].id;
        }
        if(roleIds.length >1){
            layer.alert("请选择一个国家组!");
        }else{
            $.ajax({
                url: "/userGroup/saveGroup",
                type: "post",
                data: {
                    roleIdStr : JSON.stringify(roleIds),
                    operator : operator,
                    groupName:groupName,
                    createDate:createDate,
                    id:id,
                },
                traditional: true,
                success: function (data) {
                    layer.alert("小组信息保存成功!",function(){
                        parent.layer.close(index);
                    });
                }
            });
        }
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    //ztree配置
    var setting =
        {
            check:
                {
                    enable: true,
                    chkStyle: "checkbox",
                    chkboxType: { "Y": "p", "N": "s" }
                },
            data:
                {
                    simpleData:
                        {
                            enable: true
                        },
                    keep:
                        {
                            parent: true
                        }
                },
            callback:
                {
                    beforeExpand: zTreeBeforeExpand,
//                        onCheck: zTreeOnCheck,
//                        onExpand: zTreeOnExpand,
                onClick:onClick
            }
        };
    function onClick(event, treeId, treeNode)
    {
        var tree = $.fn.zTree.getZTreeObj("nationTree");
        alert(tree.getCheckedNodes(true));
        /* var departname = treeNode.name;
         var queryParams = $('#expertList').datagrid('options').queryParams;
         queryParams.branch = treeNode.id;
         $('#expertList').datagrid('options').queryParams=queryParams;
         $("#expertList").datagrid("reload");*/
    }
    //展开方法前触发
    function zTreeBeforeExpand(treeId, treeNode) {
        var tree = $.fn.zTree.getZTreeObj("nationTree");
        tree.removeChildNodes(treeNode);
        return true;
    };
    //加载展开方法
    function zTreeOnExpand(event, treeId, treeNode)
    {
        var treeNodeId = treeNode.id;
        $.post('/userGroup/getNationTree',
            { groupName : groupName },
            function(data)
            {
                if (data.success)
                {
                    var dbDate = eval(data.msg);
                    var tree = $.fn.zTree.getZTreeObj("nationTree");
                    tree.addNodes(treeNode, dbDate);
                }
            }
        );
    }

    //首次进入加载
    $(function()
    {
        $.post(
            '/userGroup/getNationTree',
            { groupName : groupName },
            function(data){
                if (data.success) {
                    var dbDate = eval(data.msg);
                    $.fn.zTree.init($("#nationTree"), setting, dbDate);
                }
            }
        );
    });
});

    //被选中或取消选中的回调
    function zTreeOnCheck(event, treeId, treeNode) {
        var tree = $.fn.zTree.getZTreeObj("nationTree");
        var nodes = tree.getNodesByParam("checked", false, treeNode);
        console.log(nodes.length);
    };
