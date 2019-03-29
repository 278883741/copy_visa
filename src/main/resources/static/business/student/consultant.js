var studentNo = $("#studentNo").val();

var oTable1;
jQuery(function($) {
    oTable1 = $('#sample-table-12').dataTable({
        "bFilter" : false,//是否使用搜索
        "bProcessing" : true,
        "bDeferRender" : true,//
        "sPaginationType" : "bs_normal",//分页样式
        "bRetrieve" : true,//是否调用后台
        "bServerSide" : true,//是否以后台分页
        "bAutoWidth": true,
        "sAjaxSource" : "/apply/list/query",//请求路径
        "fnServerData" : function(sSource, aoData, fnCallback) {
            aoData.push(
                {"name":"studentNo","value":$("#studentNo").val()}
            )
            $.ajax({
                "dataType" : 'json',
                "type" : "GET",
                "url" : sSource,
                "data" : aoData,
                "success" : fnCallback
            });
        },
        "aaSorting" : [ [ 0, "desc" ] ],
        "bSort":true,
        "aoColumns" : [
            {"sClass": "center", "bSortable": false, "mData":
                function(data){
                    if (data.applyStatusCode == 10) return "未提交院校申请";
                    if (data.applyStatusCode == 11) return "未补件";
                    if (data.applyStatusCode == 20) return "未收到院校结果";
                    if (data.applyStatusCode == 30) return "未确认录取院校";
                    if (data.applyStatusCode == 40) return "确认录取已完成";
                    if (data.applyStatusCode == 50) return "确认拒绝已完成";
                    return "";
                }
            },
            {"sClass": "center", "bSortable": false, "mData":
                function (data) {
                    if (data.courseType == 1) return "语言";
                    if (data.courseType == 2) return "预备";
                    if (data.courseType == 3) return "主课";
                    return "";
                }},
            {"sClass": "center", "bSortable": false,"mData": "collegeName"},
            {"sClass": "center", "bSortable": false, "mData": "courseName"},
            {"sClass": "center", "bSortable": false, "mData": "majorName"},
            {"sClass": "center", "bSortable": false, "mData": "operatorName"},
            {"sClass": "center", "bSortable": false, "mData":"updateTimeString"}
        ],
        "oLanguage" : {
            "sLengthMenu" : "每页 _MENU_条 ",
            "sZeroRecords" : "对不起，查询不到任何相关数据",
            "sInfo" : "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录 ",
            "sInfoEmpty" : "显示 0 到 0 条，共 0 条记录",
            "sInfoFiltered" : "数据表中共为 _MAX_ 条记录)",
            "sProcessing" : "正在加载中...",
            "sSearch" : "搜索",
            "sUrl" : "", //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "oPaginate" : {
                "sFirst" : "第一页",
                "sPrevious" : " 上一页 ",
                "sNext" : " 下一页 ",
                "sLast" : " 最后一页 "
            }
        }
        //多语言配置

    });


    $("#query").click(function() {
        oTable1.fnDraw();
    })

})
