var channCommmIdSet = new Set([]);
var a = [];
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
        "sAjaxSource": "/commission/financeReturnCommission/listData",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "agentName", "value": $("#agentName").val()},
                {"name": "studentName", "value": $("#name").val()},
                {"name": "studentNo", "value": $("#studentNo").val()},
                {"name": "accountCurrency", "value": $("#accountCurrency").val()},
                {"name": "channelReturnStatus", "value": $("#channelReturnStatus").val()},
                {"name": "paymentDateStart", "value": $("#paymentDateStart").val()},
                {"name": "paymentDateEnd", "value": $("#paymentDateEnd").val()}
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
            {
                "sClass": "center", "bSortable": false, "mData":
                function (data, row) {
                    return '<input type="checkbox" onclick="check()" class="checkchild" name="checkchild" value="'+data.channelCommId+'" data-d="'+data.channelReturnStatus+'"   data-a="'+data.accountMoney+'" data-b="'+data.thisReturnMoney+'" data-c="'+data.returnMoneyCny+'"/> ';
                }
            },
            {
                "sClass": "center", "bSortable": false, "mData": function (data) {
                var studentDetail = "";
                if (data.studentNo == null) {
                    return studentDetail = "";
                }
                return studentDetail = '<a class="blue" href="javascript:void(0);" onclick="toReturnCommissionFrequency(\'' + data.studentNo + '\')">' + data.studentNo + '</a>';
            }
            },
            {"sClass": "center", "bSortable": false, "mData": "studentName"},
            {"sClass": "center", "bSortable": false, "mData": "spelling"},
            {"sClass": "center", "bSortable": false, "mData": "birthday"},
            //查看费用
            {
                "sClass": "center", "bSortable": false, "mData": function (data) {
                var fee = '<a href="javascript:void(0);" onclick="toFee(\'' + data.studentNo + '\')">查看</a>';
                return fee;
            }
            },
            //代理名称
            {"sClass": "center", "bSortable": false, "mData": "agentName"},
            //合同名称
            /*{
                "sClass": "center", "bSortable": false, "mData": function (data) {
                var studentDetail = "";
                if (data.contractName == null || data.contractName == '') {
                    return studentDetail = "";
                }
                return studentDetail = '<a class="blue" href="javascript:void(0);" onclick="toContractDetail(\'' + data.contractUrl + '\')">' + data.contractName + '</a>';
            }*/
            {
                "sClass": "center", "bSortable": false, "mData": function (data) {
                var studentDetail = "";
                return studentDetail = '<a class="blue" href="javascript:void(0);" onclick="toContractDetail(\'' + data.agentId + '\',\'' + data.signDate + '\')"> 查看</a>';
            }
            },
            //账户金额
            {"sClass": "center", "bSortable": false, "mData": "accountMoney"},
            //账户币种
            {"sClass": "center", "bSortable": false, "mData": "accountCurrency"},
            //获签人数
            {"sClass": "center", "bSortable": false, "mData": "getVisaSum"},
            //渠道返佣比例
            {"sClass": "center", "bSortable": false, "mData": function (data) {
                if(data.channelReturnRate!='' && data.channelReturnRate!=null){
                    return data.channelReturnRate+'%';
                }
                return "";
                }
            },
            //本次应返金额（账户金额*本次返佣百分比-累计金额）
            {"sClass": "center", "bSortable": false, "mData": "thisReturnMoney"},
            //汇率
            {"sClass": "center", "bSortable": false, "mData": "exchangeRate"},
            //应返人民币 （应返金额 * 汇率）
            {"sClass": "center", "bSortable": false, "mData": "returnMoneyCny"},
            //付款状态
            {"sClass": "center", "bSortable": false, "mData": "channelReturnStatus"},
            //付款日期
            {"sClass": "center", "bSortable": false, "mData": "paymentDate"},
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
        channCommmIdSet.clear();

    });

    /*$("#reset").click(function () {
        oTable1.fnDraw();
        channCommmIdSet.clear();

    });*/

    //页面响应回车事件
    $(document).keyup(function (e) {//捕获文档对象的按键弹起事件
        if (e.keyCode == 13) {//按键信息对象以参数的形式传递进来了
            $("#query").click();
        }
    });

    $("#reset").click(function () {
        $("#accountCurrency").empty();
        $("#channelReturnStatus").empty();
        $("#agentName").val("");
        $("#name").val("");
        $("#studentNo").val("");
        $("#paymentDateStart").val("");
        $("#paymentDateEnd").val("");
        location.href = '/commission/financeReturnCommission/list';
    })



})

function changeTable() {
    oTable1.fnDraw()
}


$('#cancel').on('click', function () {
    window.location.href = "/commissionManage/list";
});

function toAgentStudentList(agentName) {
    window.location.href = "/commission/agentStudent/list";
}

function toFee(studentNo) {
    window.open('http://192.168.0.2/finance/see.aspx?StudentID=' + studentNo);
}

function toReturnCommissionFrequency(studentNo) {
    window.open('/commission/returnCommissionFrequency/list?studentNo=' + studentNo);
}

$('#export').on('click', function () {
    window.location.href = "/commission/toExcel";
});

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
    var channelCommIdSetCheckAll = '';
    var channelReturnStatusCkeckAll='';
    var accountMoneyCheckAll=0;
    var thisReturnMoneyCheckAll=0;
    var returnMoneyCnyCheckAll=0;
    var trList = oTable1.fnGetNodes();
    for (i = 0; i < trList.length; i++) {
        if (trList[i].firstElementChild.firstElementChild.checked) {
            channelCommIdSetCheckAll= channelCommIdSetCheckAll+Number(oTable1.fnGetData(trList[i]).channelCommId)+',';
            channelReturnStatusCkeckAll= channelReturnStatusCkeckAll+oTable1.fnGetData(trList[i]).channelReturnStatus+',';
            accountMoneyCheckAll =accountMoneyCheckAll+ Number(oTable1.fnGetData(trList[i]).accountMoney);
            thisReturnMoneyCheckAll =thisReturnMoneyCheckAll+ Number(oTable1.fnGetData(trList[i]).thisReturnMoney);
            returnMoneyCnyCheckAll =returnMoneyCnyCheckAll+ Number(oTable1.fnGetData(trList[i]).returnMoneyCny);
        }
    }
    console.log(channelCommIdSetCheckAll)
    $("#channelCommIdSet").val(channelCommIdSetCheckAll);
    $("#channelReturnStatusSet").val(channelReturnStatusCkeckAll);
    $("#accountMoney").val(accountMoneyCheckAll.toFixed(2));
    $("#thisReturnMoney").val(thisReturnMoneyCheckAll.toFixed(2));
    $("#returnMoneyCny").val(returnMoneyCnyCheckAll.toFixed(2));
}
function check(){
    var channelCommIdSetCheck = '';
    var channelReturnStatusCkeck='';
    var accountMoneyCheck=0;
    var thisReturnMoneyCheck=0;
    var returnMoneyCnyCheck=0;
    $(".DTFC_LeftBodyLiner input[name='checkchild']:checked").each(function(){
        channelCommIdSetCheck+=Number($(this).attr('value'))+',';
        channelReturnStatusCkeck+=$(this).attr('data-d')+',';
        accountMoneyCheck+=Number($(this).attr('data-a'));
        thisReturnMoneyCheck+=Number($(this).attr('data-b'));
        returnMoneyCnyCheck+=Number($(this).attr('data-c'));
    })
    console.log(channelCommIdSetCheck)
    $("#channelCommIdSet").val(channelCommIdSetCheck);
    $("#channelReturnStatusSet").val(channelReturnStatusCkeck);
    $("#accountMoney").val(accountMoneyCheck.toFixed(2));
    $("#thisReturnMoney").val(thisReturnMoneyCheck.toFixed(2));
    $("#returnMoneyCny").val(returnMoneyCnyCheck.toFixed(2));
}


//付款
$('#acknowledge').on('click', function () {

    if ($("#channelCommIdSet").val() == "") {
        layer.alert("请勾选至少一条记录！", {
            icon: 0
        })
        return;
    }
    if ($("#channelReturnStatusSet").val().indexOf("已付款") != -1) {
        layer.alert("所选中数据包含已付款学生！", {
            icon: 0
        })
        return;
    }
    addRemark($("#channelCommIdSet").val());
});


function addRemark(ids) {
    layer.prompt({
        formType: 2,
        title: '提示：学生所在的代理机构是否已经返佣？',
        value: ' ',
//      area: ['540px', '350px'] //自定义文本域宽高
    },function (value, index, elem) {
        $.ajax({
            url: "/commission/financeReturnCommission/updateChannelReturnStatus",
            data: {
                resultComment: value,
                channCommIds: ids,
            },
            type: "post",
            resultType: "json",
            success: function (data) {
                console.log(data)
                if (data.result) {
                    layer.confirm("操作成功！", {
                        icon: 1,
                        btn: ['确定']
                    }, function () {
                        layer.closeAll();
                        oTable1.fnDraw();
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
    );
}
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






