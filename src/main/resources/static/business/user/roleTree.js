$(function () {
        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        var userId = parent.$("#id").val();
        var oaid = parent.$("#oaid").val();

        //保存操作
        $("#submits").click(function () {
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            var nodes = tree.getNodesByParam("checked", true, null);
            var len = nodes.length;
            var roleIds = new Array();
            for(var i=0; i<len; i++){
                roleIds[i] = nodes[i].id;
            }
            console.log(roleIds);
            $.ajax({
                url: "/user/saveRole",
                type: "post",
                data: {
                    userId : userId,
                    roleIdStr : JSON.stringify(roleIds),
                    oaid : oaid,
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
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            alert(tree.getCheckedNodes(true));
           /* var departname = treeNode.name;
            var queryParams = $('#expertList').datagrid('options').queryParams;
            queryParams.branch = treeNode.id;
            $('#expertList').datagrid('options').queryParams=queryParams;
            $("#expertList").datagrid("reload");*/
        }
        //展开方法前触发
        function zTreeBeforeExpand(treeId, treeNode) {
            var tree = $.fn.zTree.getZTreeObj("roleTree");
            tree.removeChildNodes(treeNode);
            return true;
        };
        //加载展开方法
        function zTreeOnExpand(event, treeId, treeNode)
        {
            var treeNodeId = treeNode.id;
            $.post('/user/roles',
                {
                   userId : userId,
                },
                function(data)
                {
                    if (data.success)
                    {
                        var dbDate = eval(data.msg);
                        var tree = $.fn.zTree.getZTreeObj("roleTree");
                        tree.addNodes(treeNode, dbDate);
                    }
                }
            );
        }

        //首次进入加载
        $(function()
        {
            $.post(
                '/user/roles',
                { userId : userId },
                function(data){
                    if (data.success) {
                        var dbDate = eval(data.msg);
                        $.fn.zTree.init($("#roleTree"), setting, dbDate);
                    }
                }
            );
        });
    });


