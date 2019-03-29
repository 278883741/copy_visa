var invoiceIdSet = new Set([]);
var oTable1;

jQuery(function ($) {
    oTable1 = $('#sample-table-2').dataTable({
        "aLengthMenu": [50, 100, 200, 500],  //用户可自选每页展示数量
        // "aLengthMenu": true,
        //  "sScrollY": "450px",
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
        "sAjaxSource": "/commission/toAccount/listData",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "agentName", "value": $("#agentName").val()},
                {"name": "studentNo", "value": $("#studentNo").val()},
                {"name": "studentName", "value": $("#nameOrSpelling").val()},
                {"name": "spelling", "value": $("#nameOrSpelling").val()},
                {"name": "accountCurrency", "value": $("#accountCurrency").val()},
                {"name": "channelReturnStatus", "value": $("#channelReturnStatus").val()},
                {"name": "returnDateCondition", "value": $("#returnDate").val()}
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
            {"sClass": "center", "bSortable": false, "mData":
                function(data,row){
                    for(var i of invoiceIdSet){
                        if(i == data.id){
                            return '<input type="checkbox" onclick="check(this)" checked="checked" class="checkchild" name="checkchild" value="'+data.id+'"/> ';
                        }
                    }
                    return '<input type="checkbox" onclick="check(this)" class="checkchild" name="checkchild" value="'+data.id+'"/> ';
                }
            },
            {"sClass": "center", "bSortable": false, "mData": "studentNo"},
            {"sClass": "center", "bSortable": false, "mData": "studentName"},
            {"sClass": "center", "bSortable": false, "mData": "spelling"},
            {"sClass": "center", "bSortable": false, "mData": "birthday"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var agentNameRes = "";
                var agentName = data.agentName == null ? "" : data.agentName;
                if(data.toAgentStudentListFlag){
                    agentNameRes = '<a class="blue" href="javascript:void(0);" onclick="toAgentStudentList('+data.agentId+ ', \'' + agentName + '\')">' + data.agentName + '</a>';
                }else{
                    agentNameRes = agentName;
                }
                return agentNameRes;
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                return studentDetail = '<a class="blue" href="javascript:void(0);" onclick="toContractDetail(\'' + data.agentId + '\',\'' + data.signDate + '\')"> 查看</a>';
            }},
            {"sClass": "center", "bSortable": false, "mData": "accountMoney"},
            {"sClass": "center", "bSortable": false, "mData": "accountCurrency"},
            {"sClass": "center", "bSortable": false, "mData": "returnDate"},
            {"sClass": "center", "bSortable": false, "mData": "visaDate"},
            {"sClass": "center", "bSortable": false, "mData": "schoolName"},
            {"sClass": "center", "bSortable": false, "mData": "majorName"},
            {"sClass": "center", "bSortable": false, "mData": "courseName"},
            {"sClass": "center", "bSortable": false, "mData": "startDate"},
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

function checkAll(that) {
    if (that.checked) {
        $(that).attr('checked','checked')
        $('.checkchild').each(function () {
            this.checked = true;
            invoiceIdSet.add(this.value);
        });
    } else {
        $(that).removeAttr('checked')
        $('.checkchild').each(function () {
            this.checked = false;
            invoiceIdSet.delete(this.value);
        });
    }
}

function check(label){
    if(label.checked){
        invoiceIdSet.add(label.value);
    }else{
        invoiceIdSet.delete(label.value);
    }
}

//确认到账
$('#acknowledge').on('click',function(){
    var number = '';
    var arr = '';
    $('input:checkbox[name=checkchild]:checked').each(function(k){
        if(k == 0){
            number = $(this).val();
        }else{
            number += ','+$(this).val();
        }
        if(k == 0){
           arr = 1 ;
        } else if(k > 0){
           arr=number.split(",").length;
        }else{
            arr = 0 ;
        }
    })

   var invoiceIds = "";
    for(var i of invoiceIdSet){
        invoiceIds += i;
        invoiceIds += ',';
    }
    if(invoiceIds == ""){
        layer.alert("请勾选至少一条记录！",{
            icon: 0
        })
        return;
    }

    toConfirm(invoiceIds);
});

//function modify(id){
//    layer.open({
//        type: 2,
//        area: ['auto', '520px'], //宽高
//        content: '/invoice/modify',
//        btn: ['保存', '取消'],
//        yes: function(index, layero){
//            console.log("DDDD");
//        },
//        btn2: function(index, layero){
//            layer.close();
//        },
//    })
//}

function toConfirm(invoiceIds){
    layer.confirm('确认到账后学生将流转到同业组！', {
        btn: ['确定', '取消'], //按钮
        btn1: function () {
            $.ajax({
                url: "/commission/toAccount/updateChannelReturnStatus",
                type: "post",
                data: {
                    invoiceIds: invoiceIds,
                    type: '已确认',
                },
                success: function (data) {
                    if (data.result) {
                        layer.confirm("操作成功！", {
                            icon: 1,
                            btn: ['确定']
                        },function(){
                            layer.closeAll();
                            oTable1.fnDraw();
                        });
                    } else {
                        layer.alert(data.errorMsg, {
                            icon: 2,
                        });
                    }
                }
            });
        },
        btn2: function () {
            layer.closeAll();
        }
    });

}

//$('#cancel').on('click',function(){
//    window.location.href="/commissionManage/list";
//});

function toAgentStudentList(agentId, agentName) {
    window.location.href="/commission/agentStudent/list?agentId="+agentId+"&agentName="+agentName;
}

function toContractDetail(url) {
    window.open(url);
}

$('#export').on('click',function(){
    window.location.href="/report/commission/toAccountList/toExcel";
});

//查询合同信息
function toContractDetail(agentId,signDate) {
    $.ajax({
        url: "/commission/selectContractNameByAgentIdAndSignDate",
        data: {
            agentId: agentId,
            signDate: signDate,
        },
        type: "post",
        success: function (data) {
            console.log(data)
            if (data.result) {
                layer.open({
                    type: 1,
                    skin: 'layui-layer-rim', //加上边框
                    area: ['350px', '100px'], //宽高
                    content: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="blue"  href="javascript:void(0);" onclick="toContractUrl(\'' + data.data.contractPic + '\')">'+data.data.contractName+'</a>'
                });
            } else {
                layer.alert(data.errorMsg, {
                    icon: 2,
                }, function () {
                    layer.closeAll();
                    oTable1.fnDraw();
                });
            }
        }
    });
}

//展示合同附件
function toContractUrl(url) {
    window.open(url);
}