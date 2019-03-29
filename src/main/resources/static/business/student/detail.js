
var oTableRemark;

jQuery(function($) {
    oTableRemark = $('#sample-table-remark').dataTable({
        "bFilter" : false,//是否使用搜索
        "aLengthMenu": [20, 50, 100],  //用户可自选每页展示数量 5条或10条
        "bProcessing" : true,
        "bDeferRender" : true,//
        "sPaginationType" : "bs_normal",//分页样式
        "bRetrieve" : true,//是否调用后台
        "bServerSide" : true,//是否以后台分页
        "bAutoWidth": true,
        "sAjaxSource" : "/studentRemark/list/data",//请求路径
        "fnServerData" : function(sSource, aoData, fnCallback) {
            aoData.push(
                {"name":"studentNo","value":$("#studentNo").val()!=""? $("#studentNo").val():null},
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
            {"sClass": "center", "bSortable": false, "mData": "content"},
            {"sClass": "center", "bSortable": false, "mData": "createTime"},
            {"sClass": "center", "bSortable": false, "mData": "operatorName"}
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
})

function addRemark(){
var studentNo = $("#studentNo").val();
    layer.prompt({
      formType: 2,
      title: '备注记录',
//      area: ['540px', '350px'] //自定义文本域宽高
    }, function(value, index, elem){
        $.ajax({
            url : "/studentRemark/add",
            data : {
                content : value,
                studentNo : studentNo,
            },
            type : "post",
            resultType : "json",
            success : function(data){
                layer.close(index);
                oTableRemark.fnDraw();
            }
        });
    });
}