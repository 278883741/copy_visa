
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
            "leftColumns": 4, //固定左边第一列
        },
        "bFilter": false,//是否使用搜索
        "bProcessing": true,
        "bDeferRender": true,//
        "sPaginationType": "bs_normal",//分页样式
        "bRetrieve": true,//是否调用后台
        "bServerSide": true,//是否以后台分页
        "sAjaxSource": "/commission/agentStudent/listData",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "agentId", "value": $("#agentId").val()},
                {"name": "studentNo", "value": $("#studentNo").val()},
                {"name": "studentName", "value": $("#studentName").val()},
                {"name": "spelling", "value": $("#studentName").val()},
                {"name": "accountCurrency", "value": $("#accountCurrency").val()},
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
                    /*for(var i of invoiceIdSet){
                        if(i == data.id){
                            return '<input type="checkbox" onclick="check()" checked class="checkchild" name="checkchild" value="'+data.id+'" data-a="'+data.accountMoney+'" data-a="'+data.thisReturnMoney+'" data-a="'+data.returnMoneyCny+'"/> ';
                        }
                    }*/
                    return '<input type="checkbox" onclick="check()" class="checkchild" name="checkchild" value="'+data.id+'" data-a="'+data.accountMoney+'" data-b="'+data.thisReturnMoney+'" data-c="'+data.returnMoneyCny+'"data-d="'+data.lastCreatTime+'"/> ';
                }
            },
            {"sClass": "center", "bSortable": false, "mData": "studentNo"},
            {"sClass": "center", "bSortable": false, "mData": "studentName"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                return studentDetail = '<a class="blue" href="javascript:void(0);" onclick="toContractDetail(\'' + data.agentId + '\',\'' + data.signDate + '\')"> 查看</a>';
            }},
            {"sClass": "center", "bSortable": false, "mData": "accountMoney"},
            {"sClass": "center", "bSortable": false, "mData": "accountCurrency"},
            {"sClass": "center", "bSortable": false, "mData": "returnDate"},
            {"sClass": "center", "bSortable": false, "mData": "getVisaSum"},
            //累计应返金额
            {"sClass": "center", "bSortable": false, "mData": "returnMoney"},
            //上次标识时间
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.lastCreatTime != '' && data.lastCreatTime != null){
                    return getTimeString_day(new Date(data.lastCreatTime));
                }
                return "";
            }},
            // 上次返佣比例
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.channelReturnRate != '' && data.channelReturnRate != null){
                    return data.channelReturnRate + '%';
                }
                return "";
            }},
            //本次返佣比例
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.percentNum != '' && data.percentNum != null){
                    return data.percentNum + '%';
                }
                return "";
            }},

            {"sClass": "center", "bSortable": false, "mData": "thisReturnMoney"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.exchangeRate != '' && data.exchangeRate != null){
                    return data.exchangeRate ;
                }
                return "";
            }},
            {"sClass": "center", "bSortable": false, "mData": "returnMoneyCny"},
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

    function getTimeString_day(date) {
        var y = date.getFullYear();
        var m = date.getMonth() + 1;
        m = m >= 10 ? m : '0' + m;
        var d = date.getDate();
        d = d >= 10 ? d : '0' + d;
        return y + '-' + m + '-' + d;
    }

    $("#query").click(function () {
        oTable1.fnDraw();
    });

})

function checkAll(that) {
    if (that.checked) {
        $(that).attr('checked','checked')
        $('.checkchild').each(function () {
            this.checked = true;
            getTableContent();

        });
    } else {
        $(that).removeAttr('checked')
        $('.checkchild').each(function () {
            this.checked = false;
            getTableContent();

        });
    }
    console.log(that);

}
function  getTableContent() {
    var invoiceIdSetCheckAll = '';
    var accountMoneyCheckAll=0;
    var thisReturnMoneyCheckAll=0;
    var returnMoneyCnyCheckAll=0;
    var lastCreatTimeSetCheckAll='';
    var trList = oTable1.fnGetNodes();
    for (i = 0; i < trList.length; i++) {
        if (trList[i].firstElementChild.firstElementChild.checked) {
            invoiceIdSetCheckAll= invoiceIdSetCheckAll+Number(oTable1.fnGetData(trList[i]).id)+',';
            accountMoneyCheckAll =accountMoneyCheckAll+ Number(oTable1.fnGetData(trList[i]).accountMoney);
            thisReturnMoneyCheckAll =thisReturnMoneyCheckAll+ Number(oTable1.fnGetData(trList[i]).thisReturnMoney);
            returnMoneyCnyCheckAll =returnMoneyCnyCheckAll+ Number(oTable1.fnGetData(trList[i]).returnMoneyCny);
            lastCreatTimeSetCheckAll= lastCreatTimeSetCheckAll+Number(oTable1.fnGetData(trList[i]).lastCreatTime)+',';
        }
    }
    console.log(lastCreatTimeSetCheckAll+"alllastTime");
    $("#invoiceIdSet").val(invoiceIdSetCheckAll);
    $("#accountMoney").val(accountMoneyCheckAll.toFixed(2));
    $("#thisReturnMoney").val(thisReturnMoneyCheckAll.toFixed(2));
    $("#returnMoneyCny").val(returnMoneyCnyCheckAll.toFixed(2));
    $("#lastCreatTime").val(lastCreatTimeSetCheckAll);
}
function check(){
    var invoiceIdSetCheck = '';
    var accountMoneyCheck=0;
    var thisReturnMoneyCheck=0;
    var returnMoneyCnyCheck=0;
    var lastCreatTimeSetCheck='';
    $(".DTFC_LeftBodyLiner input[name='checkchild']:checked").each(function(){
            invoiceIdSetCheck+=Number($(this).attr('value'))+',';
            accountMoneyCheck+=Number($(this).attr('data-a'));
            thisReturnMoneyCheck+=Number($(this).attr('data-b'));
            returnMoneyCnyCheck+=Number($(this).attr('data-c'));
            lastCreatTimeSetCheck+=Number($(this).attr('data-d'))+',';
    })
    console.log(lastCreatTimeSetCheck+"checkLstTime");
    $("#invoiceIdSet").val(invoiceIdSetCheck);
    $("#accountMoney").val(accountMoneyCheck.toFixed(2));
    $("#thisReturnMoney").val(thisReturnMoneyCheck.toFixed(2));
    $("#returnMoneyCny").val(returnMoneyCnyCheck.toFixed(2));
    $("#lastCreatTime").val(lastCreatTimeSetCheck);
}
/*<![CDATA[*/
//标识
$('#confirm').on('click',function(){

    if($("#invoiceIdSet").val() == ""){
        layer.alert("请勾选至少一条记录！",{
            icon: 0
        })
        return;
    }
    /*补返的操作请选择2018-09-11之后标识的学生，在这之前已经返佣的学生，无需操作返佣给代理！经与产品确认，最终以"2018-09-11 23:59:59"作为是否二次标识的时间节点*/
    var beginIdentificationTime =  new Date(Date.parse("2018-09-11 23:59:59")).getTime();
    console.log("2018-09-11 23:59:59    "+beginIdentificationTime);
    var lastIdentificationTime  =  $("#lastCreatTime").val().split(",");
    for (var i = 0; i < lastIdentificationTime.length; i++) {
        /*此处判断0 是因为全选的时候，如果上次标识时间为空，则此处lastIdentificationTime[i]为0*/
        if(lastIdentificationTime[i]!=null && lastIdentificationTime[i]!="" && lastIdentificationTime[i]!="NaN" && lastIdentificationTime[i]!="undefined" && lastIdentificationTime[i]!="0"){
            if(lastIdentificationTime[i]<beginIdentificationTime){
                layer.alert("所选数据中有2018-09-12之前标识的学生，该类学生无需操作补返给代理，请重新选择！",{
                    icon: 0
                })
                return;
            }
        }

    }

    confirm($("#invoiceIdSet").val());

});
/*]]>*/
function modify(id){
    layer.open({
        type: 2,
        area: ['auto', '520px'], //宽高
        content: '/invoice/modify',
        btn: ['保存', '取消'],
        yes: function(index, layero){
            console.log("DDDD");
        },
        btn2: function(index, layero){
            layer.close();
        },
    })
}

function confirm(invoiceIdStr){
    layer.confirm('操作标识后学生将流转至财务组！', {
        btn: ['确定', '取消'], //按钮
        btn1: function () {
            $.ajax({
                url: "/commission/toAccount/updateChannelReturnStatus",
                type: "post",
                data: {
                    invoiceIds: invoiceIdStr,
                    type: '已标识',
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
$('#cancel').on('click',function(){
    window.location.href="/commissionManage/list";
});

$('#export').on('click',function(){
    window.location.href="/report/commission/agentStudentList/toExcel";
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