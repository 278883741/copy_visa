var invoiceIdSet = new Set([]);
var bankAccountStr = $.parseJSON( $("#bankAccountStr").val() );
var accountCurrencyStr = $.parseJSON( $("#accountCurrencyStr").val() );
var commissionBalanceStr = $.parseJSON( $("#commissionBalanceStr").val() );
var oTable1;
jQuery(function ($) {
    oTable1 = $('#sample-table-2').dataTable({
        "aLengthMenu": [50, 100, 200, 500],  //用户可自选每页展示数量
        "sScrollY": "450px",
        "scrollX": true,
        "scrollCollapse": true,
        "bProcessing": true,//显示搜索样式
        "bDeferRender": true,//延迟加载表格
//        "fixedColumns": { //固定列的配置项
//            "leftColumns": 6, //固定左边第一列
//        },
        "fnInitComplete": function (oSettings, json) {
            sum();
        },

        "bFilter": false,//是否使用搜索
        "bProcessing": true,
        "bDeferRender": true,//
        "sPaginationType": "bs_normal",//分页样式
        "bRetrieve": true,//是否调用后台
        "bServerSide": true,//是否以后台分页
        "sAjaxSource": "/invoice/edit/data",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "invoiceIds", "value": $("#invoiceIdStrOld").val()},
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
            {"sClass": "center", "bSortable": false, "mData": "spelling"},
            {"sClass": "center", "bSortable": false, "mData": "birthday"},
            {"sClass": "center", "bSortable": false, "mData": "nationName"},
            {"sClass": "center", "bSortable": false, "mData": "schoolNo"},
            {"sClass": "center", "bSortable": false, "mData": "currency"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceMoney"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceGst"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceSum"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var value = data.returnDate == null ? "" : data.returnDate;
                return "<input style='width:120px' type='text' onclick='WdatePicker({dateFmt:\"yyyy-MM-dd\"})' readonly='readonly' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"returnDate1\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var bankAccount = data.bankAccount;
                var result = "";
                result += "<select name='nationName' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"bankAccount2\")'>";
                result += "<option value=''>无</option>";
                for(var i=0,l=bankAccountStr.length;i<l;i++){
                    if(data.bankAccount == bankAccountStr[i].accountName){
                        result += "<option selected='selected' value='"+bankAccountStr[i].accountName+"'>"+bankAccountStr[i].accountName+"</option>";
                    }else{
                        result += "<option value='"+bankAccountStr[i].accountName+"'>"+bankAccountStr[i].accountName+"</option>";
                    }
                 }
                result += "</select>";
                return result;
            }},
//            {"sClass": "center", "bSortable": false, "mData": function(data){
//                var returnCurrency = data.returnCurrency;
//                var result = "";
//                result += "<select name='nationName' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"returnCurrency2\")'>";
//                result += "<option value=''>无</option>";
//                for(var i=0,l=accountCurrencyStr.length;i<l;i++){
//                    if(data.returnCurrency == accountCurrencyStr[i].enName){
//                        result += "<option selected='selected' value='"+accountCurrencyStr[i].enName+"'>"+accountCurrencyStr[i].enName+"</option>";
//                    }else{
//                        result += "<option value='"+accountCurrencyStr[i].enName+"'>"+accountCurrencyStr[i].enName+"</option>";
//                    }
//                 }
//                result += "</select>";
//                return result;
//            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var value = data.actualAmount == null ? "" : data.actualAmount;
                return "<input style='width:120px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' invoiceMoney='"+data.invoiceMoney+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"actualAmount\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": "accountGst"},
            {"sClass": "center", "bSortable": false, "mData": "accountSum"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var value = data.bankFee == null ? "" : data.bankFee;
                return "<input style='width:120px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' invoiceMoney='"+data.invoiceMoney+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"bankFee\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": "accountMoney"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var accountCurrency = data.accountCurrency;
                var result = "";
                result += "<select name='nationName' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"accountCurrency2\")'>";
                result += "<option value=''>无</option>";
                for(var i=0,l=accountCurrencyStr.length;i<l;i++){
                    if(data.accountCurrency == accountCurrencyStr[i].enName){
                        result += "<option selected='selected' value='"+accountCurrencyStr[i].enName+"'>"+accountCurrencyStr[i].enName+"</option>";
                    }else{
                        result += "<option value='"+accountCurrencyStr[i].enName+"'>"+accountCurrencyStr[i].enName+"</option>";
                    }
                 }
                result += "</select>";
                return result;
            }},
            {"sClass": "center", "bSortable": false, "mData": "balance"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                // var value = data.balanceType == null ? "" : data.balanceType;
                // return "<input style='width:120px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"balanceType\")' value='"+value+"'/>";

                var balanceType = data.balanceType;
                var result = "";
                result += "<select name='nationName' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"balanceType\")'>";
                result += "<option value=''>无</option>";
                for(var i=0,l=commissionBalanceStr.length;i<l;i++){
                    if(data.balanceType == commissionBalanceStr[i].balance){
                        result += "<option selected='selected' value='"+commissionBalanceStr[i].balance+"'>"+commissionBalanceStr[i].balance+"</option>";
                    }else{
                        result += "<option value='"+commissionBalanceStr[i].balance+"'>"+commissionBalanceStr[i].balance+"</option>";
                    }
                }
                result += "</select>";
                return result;
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.status == '1') return "未开始";
                if(data.status == '2') return "进行中";
                if(data.status == '3') return "已结束";
                if(data.status == '4') return "已取消";
                return "";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var value = data.invoiceRemark == null ? "" : data.invoiceRemark;
                return "<input style='width:120px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"schoolRemark\")' value='"+value+"'/>";
            }}
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

    $('table th input:checkbox').on('click' , function(){
        var that = this;
        $(this).closest('table').find('tr > td:first-child input:checkbox')
        .each(function(){
            this.checked = that.checked;
            $(this).closest('tr').toggleClass('selected');
        });
    });

})

function sum(){
    $.ajax({
        url: "/invoice/list/sum",
        type: "post",
        success: function (data) {
            $("#invoiceSum").text(data.invoiceMoney);
            $("#invoiceGstSum").text(data.invoiceGst);
            $("#invoiceSumSum").text(data.invoiceSum);
            $("#accountMoneySum").text(data.accountMoney);
            $("#accountGstSum").text(data.accountGst);
            $("#accountSumSum").text(data.accountSum);
            $("#bankFeeSum").text(data.bankFee == null ? "" : data.bankFee);
            $("#actualAmountSum").text(data.actualAmount);
            $("#balanceSum").text(data.balance == null ? "": data.balance);
        }
    });
}

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

//批量编辑
$('#save').on('click',function(){

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

    var invoiceIdStr = "";
    for(var i of invoiceIdSet){
        invoiceIdStr += i;
        invoiceIdStr += ',';
    }
    if(invoiceIdStr == ""){
        layer.alert("请勾选至少一条记录！",{
            icon: 0
        })
        return;
    }

    $("#invoiceIdStr").val(invoiceIdStr);

    $.ajax({
        url: "/invoice/save",
        type: "post",
        data: $("#edit").serialize(),
        success: function (data) {
            layer.closeAll();
            if (data.result) {
                layer.msg("操作成功!", {time: 1000});
                oTable1.fnDraw();
            } else {
                layer.alert(data.errorMsg);
            }
        }
    });
});

function remove(id){
    layer.confirm('是否删除佣金信息？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            url: "/invoice/remove?id="+id,
            type: "get",
            success: function (data) {
                parent.layer.closeAll();
                if (data.result) {
                    oTable1.fnDraw();
                    sum();
                } else {
                    layer.alert(data.errorMsg);
                }
            }
        });
    }, function(){
        layer.close();
    });

}

function modify(id){
    layer.open({
        type: 2,
        area: ['auto', '450px'], //宽高
        content: '/invoice/acknowledgeEdit?invoiceId='+id,
        btn: ['保存', '取消'],
        yes: function(index, layero){
            var body = layer.getChildFrame('body', index);
            $.ajax({
                url: "/invoice/save",
                type: "post",
                data: body.contents().find("#modify").serialize(),
                success: function (data) {
                    if (data.result) {
                        layer.closeAll();
                        oTable1.fnDraw();
                    } else {
                        layer.alert(data.errorMsg);
                    }
                }
            });
        },
        btn2: function(index, layero){
            layer.close();
        },
    })
}

function rowEdit(label, type){
    $(".rowEdit").val(null);
    $("#schoolId").val(label.getAttribute('schoolId'));
    $("#studentId").val(label.getAttribute('studentId'));
    $("#invoiceId").val(label.getAttribute('invoiceId'));
    if(type == 'bankFee' || type == 'actualAmount'){
        $("#invoiceMoney").val(label.getAttribute('invoiceMoney'));
    }
    $("#"+type).val(label.value);
    ajaxSubmit();
}

function ajaxSubmit(){
    $.ajax({
        url: "/invoice/save",
        type: "post",
        data: $("#modify").serialize(),
        success: function (data) {
            layer.closeAll();
            if (data.result) {
                oTable1.fnDraw();
                sum();
            } else {
                layer.alert(data.errorMsg);
                oTable1.fnDraw();
            }
        }
    });
}

$('#cancel').on('click',function(){
    window.location.href="/invoice/list";
});
