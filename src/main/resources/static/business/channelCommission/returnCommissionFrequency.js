var schoolIdSet = new Set([]);
var oTable1;

jQuery(function ($) {
    oTable1 = $('#sample-table-2').dataTable({
        "aLengthMenu": [10],  //用户可自选每页展示数量 5条或10条
        "aLengthMenu": true,
        "sScrollY": "450px",
        "scrollX": true,
        "scrollCollapse": true,
        "bProcessing": true,//显示搜索样式
        "bDeferRender": true,//延迟加载表格
        "fixedColumns": { //固定列的配置项
            "leftColumns": 5, //固定左边第一列
        },
        "bFilter": false,//是否使用搜索
        "bProcessing": true,
        "bDeferRender": true,//
        "sPaginationType": "bs_normal",//分页样式
        "bRetrieve": true,//是否调用后台
        "bServerSide": true,//是否以后台分页
        "sAjaxSource": "/commission/returnCommissionFrequency/listData",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "studentNo", "value": $("#studentNo").val()}
            )
            $.ajax({
                "dataType": 'json',
                "type": "POST",
                "url": sSource,
                "data": aoData,
                "success": fnCallback
            });
        },
        "aaSorting": [[0, "desc"]],
        "bSort": true,
        "aoColumns": [
            {"sClass": "center", "bVisible": false, "mData": "id"},//此列不显示
            //序号
            {"sClass": "center", "bVisible": false, "mData": "channelCommId"},

            {"sClass": "center", "bSortable": false, "mData": "studentNo"},
            {"sClass": "center", "bSortable": false, "mData": "studentName"},
            //代理名称
            {"sClass": "center", "bSortable": false, "mData": "agentName"},
            //账户金额
            {"sClass": "center", "bSortable": false, "mData": "accountMoney"},
            //账户币种
            {"sClass": "center", "bSortable": false, "mData": "accountCurrency"},
            //渠道返佣比例
            {"sClass": "center", "bSortable": false, "mData": "channelReturnRate"},
            //应返金额 （账户金额 * 渠道返佣比例）
            {"sClass": "center", "bSortable": false, "mData": "returnMoney"},
            //汇率
            {"sClass": "center", "bSortable": false, "mData": "exchangeRate"},
            //应返人民币 （应返金额 * 汇率）
            {"sClass": "center", "bSortable": false, "mData": "returnMoneyCny"},
            //付款日期
            {"sClass": "center", "bSortable": false, "mData": "paymentDate"},
            //备注
            {"sClass": "center", "bSortable": false, "mData": "resultComment"},
        ],
        "oLanguage": {
            "sLengthMenu": "每页 _MENU_条 ",
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录 ",
            "sInfoEmpty": "显示 0 到 0 条，共 0 条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录)",
            "sProcessing": "正在加载中...",
            "sSearch": "搜索",
            "sUrl": "", //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
            "oPaginate": {
                "sFirst": "第一页",
                "sPrevious": " 上一页 ",
                "sNext": " 下一页 ",
                "sLast": " 最后一页 "
            }
        },
        //多语言配置
    });

    $("#query").click(function () {
        oTable1.fnDraw();
    });

})

