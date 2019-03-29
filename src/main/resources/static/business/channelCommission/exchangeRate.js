var schoolIdSet = new Set([]);
var oTable1;
jQuery(function ($) {
    oTable1 = $('#sample-table-2').dataTable({
        "aLengthMenu": false,
        "sScrollY": "700px",
        //"scrollX": true,
        "scrollCollapse": true,
        "bProcessing": true,//显示搜索样式
        "bDeferRender": true,//延迟加载表格
        /*"fixedColumns": { //固定列的配置项
            "leftColumns": 2, //固定左边第一列
        },*/
        "bFilter": false,//是否使用搜索
        "bProcessing": true,
        "bDeferRender": true,//
        "sPaginationType": "bs_normal",//分页样式
        "bRetrieve": true,//是否调用后台
        "bServerSide": true,//是否以后台分页
        "sAjaxSource": "/commission/exchangeRate/listData",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
//                {"name": "schoolIdStr", "value": $("#schoolIdStrOld").val()},
//                {"name": "tempKey", "value": $("#tempKey").val()},
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
            {"sClass": "center", "bSortable": false, "mData": "cnName"},
            {"sClass": "center", "bSortable": false, "mData": "enName"},
            {
                "sClass": "center", "bSortable": false, "mData": function (data) {
                    var visitOperation ;
                    if(data.exchangeRate==null){
                        return visitOperation = '<input style="width:130px" type="text"  class="'+data.id+'" onchange="rowEdit(this, \'exchangeRate\')" value=""/>';
                    }else{
                        return visitOperation = '<input style="width:130px" type="text"  class="'+data.id+'" onchange="rowEdit(this, \'exchangeRate\')" value="'+data.exchangeRate+'"/>';
                    }

            }
            },
            {"sClass": "center", "bSortable": false, "mData": "operatorName"},
            {"sClass": "center", "bSortable": false, "mData": "updateTime"},

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

function rowEdit(label, type){

    $(".rowEdit").val(null);
    $("#id").val(label.getAttribute('class'));
    $("#"+type).val(label.value);
    ajaxSubmit();
}

function ajaxSubmit(){
    var exchangeRate = $("#exchangeRate").val();
    var g = /^\d+(?:\.\d{1,4})?$/;
    if(g.test(exchangeRate)==false){
        layer.alert('请输入正确的汇率格式!', {icon: 0});
        return;
    }

    $.ajax({
        url: "/commission/updateCurrencyInfoByExchangeRate",
        type: "post",
        data: $("#modify").serialize(),
        success: function (data) {
            location.reload(true)
        }
    });
}