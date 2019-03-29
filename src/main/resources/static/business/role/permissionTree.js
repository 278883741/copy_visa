$(function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        var roleId = parent.$("#id").val();
        var operator = parent.$("#operator").val();

        //保存操作
        $("#submits").click(function () {
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            var nodes = tree.getNodesByParam("checked", true, null);
            var len = nodes.length;
            var permissionIds = new Array();
            for(var i=0; i<len; i++){
                permissionIds[i] = nodes[i].id;
            }
            console.log(permissionIds);
            $.ajax({
                url: "/role/savePermission",
                type: "post",
                data: {
                    roleId : roleId,
                    permissionIdStr : JSON.stringify(permissionIds),
                    operator : operator,
                },
                traditional : true,
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
                    chkboxType: { "Y": "ps", "N": "ps" }
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
                    onExpand: zTreeOnExpand,
                    onClick:onClick
                }
        };
        //点击节点触发
        function onClick(event, treeId, treeNode) {
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            tree.checkNode(treeNode, !treeNode.checked, true, true);
        }
        //展开方法前触发
        function zTreeBeforeExpand(treeId, treeNode) {
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            tree.removeChildNodes(treeNode);
            return true;
        };
        //加载展开方法
        function zTreeOnExpand(event, treeId, treeNode, type)
        {
            var treeNodeId = treeNode.id;
            var level = parseInt(treeNode.level) + 1 ;
            $.post('/role/functions',
                {
                    parentId : treeNodeId,
                    roleId : roleId,
                    level : level,
                },
                function(data)
                {
                    if (data.success)
                    {
                        var dbDate = eval(data.msg);
                        var tree = $.fn.zTree.getZTreeObj("roleTree");
                        tree.addNodes(treeNode, dbDate);
                        //第一次加载时传递
                        if(type == "AUTO"){
                            //如果节点被选中
                            var nodes = treeNode.children;
                            for(i=0; i<nodes.length; i++){
                                if(nodes[i].checked){
                                    zTreeOnExpand(null, null, nodes[i], "AUTO");
                                }
                            }
                        }
                    }
                }
            );
        }

        //首次进入加载level为1的
        $(function(){
            $.post(
                '/role/functions',
                { roleId : roleId },
                function(data){
                    if (data.success) {
                        var dbDate = eval(data.msg);
                        $.fn.zTree.init($("#roleTree"), setting, dbDate);
                    }
                    //如果节点被选中
                    var tree = $.fn.zTree.getZTreeObj("roleTree");
                    var nodes = tree.getNodes();
                    for(i=0; i<nodes.length; i++){
                        if(nodes[i].checked){
                            zTreeOnExpand(null, null, nodes[i], "AUTO");
                        }
                    }
                }
            );
        });

    });
