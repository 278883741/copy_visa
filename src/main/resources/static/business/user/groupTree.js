
$(function () {
    var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
    var id = parent.$("#id").val();
    var operator = parent.$("#operator").val();

    //保存操作
    $("#group_submits").click(function () {
        var tree = $.fn.zTree.getZTreeObj("groupTree");
        var nodes = tree.getNodesByParam("checked", true, null);
        var len = nodes.length;
        var roleIds = new Array();
        for(var i=0; i<len; i++){
            roleIds[i] = nodes[i].id;
        }
        console.log(roleIds);
        $.ajax({
            url: "/user/saveGroup",
            type: "post",
            data: {
                userId : id,
                roleIdStr : JSON.stringify(roleIds),
                operator : operator,
            },
            traditional: true,
            success: function (data) {
                parent.layer.close(index);
            }
        });
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
        var tree = $.fn.zTree.getZTreeObj("groupTree");
        alert(tree.getCheckedNodes(true));
        /* var departname = treeNode.name;
         var queryParams = $('#expertList').datagrid('options').queryParams;
         queryParams.branch = treeNode.id;
         $('#expertList').datagrid('options').queryParams=queryParams;
         $("#expertList").datagrid("reload");*/
    }
    //展开方法前触发
    function zTreeBeforeExpand(treeId, treeNode) {
        var tree = $.fn.zTree.getZTreeObj("groupTree");
        tree.removeChildNodes(treeNode);
        return true;
    };
    //加载展开方法
    function zTreeOnExpand(event, treeId, treeNode)
    {
        var treeNodeId = treeNode.id;
        $.post('/user/group',
            { userId : id },
            function(data)
            {
                if (data.success)
                {
                    var dbDate = eval(data.msg);
                    var tree = $.fn.zTree.getZTreeObj("groupTree");
                    tree.addNodes(treeNode, dbDate);
                }
            }
        );
    }

    //首次进入加载
    $(function()
    {
        $.post(
            '/user/group',
            { userId : id },
            function(data){
                if (data.success) {
                    var dbDate = eval(data.msg);
                    $.fn.zTree.init($("#groupTree"), setting, dbDate);
                }
            }
        );
    });
});

    //被选中或取消选中的回调
    function zTreeOnCheck(event, treeId, treeNode) {
        var tree = $.fn.zTree.getZTreeObj("roleTree");
        var nodes = tree.getNodesByParam("checked", false, treeNode);
        console.log(nodes.length);
    };
