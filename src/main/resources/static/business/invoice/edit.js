var invoiceIdSet = new Set([]);
var countryInfoListStr = $.parseJSON( $("#countryInfoListStr").val() );
var currencyInfoStr = $.parseJSON( $("#currencyInfoStr").val() );
//for(var i=0,l=json.length;i<l;i++){
//　　　　console.log(json[i].id + "--" + json[i].countryName);
//}
var oTable1;
jQuery(function ($) {
    oTable1 = $('#sample-table-2').dataTable({
        "aLengthMenu": [50, 100, 200, 500],  //用户可自选每页展示数量

        "scrollX": true,
        "scrollCollapse": true,
        "scrollY": '450px',
        "scroller": {
            "loadingIndicator": true
        },
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
                {"name": "invoiceNo", "value": $("#invoiceNo").val()}
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
                    if(data.id == null){ return ""}// 区分是否为总额行
                    for(var i of invoiceIdSet){
                        if(i == data.id){
                            return '<input type="checkbox" onclick="check(this)" checked="checked" class="checkchild" name="checkchild" value="'+data.id+'"/> ';
                        }
                    }
                    return '<input type="checkbox" onclick="check(this)" class="checkchild" name="checkchild" value="'+data.id+'"/> ';
                }
            },
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var remove = '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="remove(' + data.id + ')" >删除</a>&nbsp;&nbsp;';
                var modify = '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="modify(' + data.id + ')" >编辑</a>&nbsp;&nbsp;';
                if(data.id != null){
                    return remove;
                }
                return "";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var no = data.studentNo == null ? "": data.studentNo;
                var studentNo = '<a class="blue" href="javascript:void(0);" onclick="toCommissionEdit(\'' + data.studentId + '\')">' + no + '</a>';
                return studentNo;
            }},
            {"sClass": "center", "bSortable": false, "mData": "spelling"},
            {"sClass": "center", "bSortable": false, "mData": "birthday"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return ""}
                var value = data.schoolNo == null ? "" : data.schoolNo;
                return "<input style='width:75px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"schoolNo\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, sWidth:"10%", "mData": "courseName"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var majorName = data.majorName != null ? data.majorName : '';
                if (majorName.length > 18) {
                    return '<a href="javascript:void(0);" id="major'+data.id+'" onclick="toTipContent(\'major' + data.id + '\',\'' +  majorName.replace(/\r\n/mg,'  ') + '\')" >'+majorName.substring(0,17)+"..."+'</a>';
                }else{
                    return majorName;
                }
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.returnDate != null) { return data.tuition}
                var value = data.tuition;
                return "<input style='width:75px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"tuition2\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return ""}
                var value = data.startDate == null ? "" : data.startDate;
                return "<input style='width:120px' type='text' onclick='WdatePicker({dateFmt:\"yyyy-MM-dd\"})' readonly='readonly' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"startDate\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return ""}
                var value = data.rate == null ? "" : data.rate;
                return "<input style='width:75px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"rate\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return ""}
                var currency = data.currency;
                var result = "";
                result += "<select invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"currency2\")'>";
                result += "<option value=''>无</option>";
                for(var i=0,l=currencyInfoStr.length;i<l;i++){
                    if(data.currency == currencyInfoStr[i].enName){
                        result += "<option selected='selected' value='"+currencyInfoStr[i].enName+"'>"+currencyInfoStr[i].enName+"</option>";
                    }else{
                        result += "<option value='"+currencyInfoStr[i].enName+"'>"+currencyInfoStr[i].enName+"</option>";
                    }
                 }
                result += "</select>";
                return result;
            }},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return data.invoiceMoney}
                var value = data.invoiceMoney == null ? "" : data.invoiceMoney;
                return "<input style='width:75px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"invoiceMoney\")' value='"+value+"'/>";
            }},
            {"sClass": "center", "bSortable": false, "mData": "invoiceGst"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceSum"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.id == null){return ""}
                var value = data.invoiceRemark == null ? "" : data.invoiceRemark;
                return "<input style='width:75px' type='text' invoiceId='"+data.id+"' schoolId='"+data.schoolId+"' studentId='"+data.studentId+"' onchange='rowEdit(this, \"schoolRemark\")' value='"+value+"'/>";
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

function toCommissionEdit(studentId){
    window.location.href="/commissionManage/edit?studentId="+studentId;
}

//批量编辑
$('#save').on('click',function(){

    var rate = $("#schoolRate").val();
    if(rate != "" && rate > 1){
        layer.alert('请输入正确的佣金比例！', {icon: 0});
        return;
    }

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
                sum();
            } else {
                layer.alert(data.errorMsg);
            }
        }
    });
});

//认账
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

        window.location.href = "/invoice/acknowledge?invoiceIdStr="+invoiceIdStr;
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
                    layer.msg("操作成功!", {time: 1000});
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
        area: ['720px', '350px'], //宽高
        content: '/invoice/modify?invoiceId='+id,
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
                        layer.msg("操作成功!", {time: 1000});
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

$('#cancel').on('click',function(){
    window.location.href="/invoice/list";
});

function rowEdit(label, type){
    $(".rowEdit").val(null);
    $("#schoolId").val(label.getAttribute('schoolId'));
    $("#studentId").val(label.getAttribute('studentId'));
    $("#invoiceId").val(label.getAttribute('invoiceId'));
    if(type == 'nationId2'){
        var countryName = $("#CountryList"+label.getAttribute('schoolId')+" option[value="+label.value+"]").text();
        $("#nationName2").val(countryName);
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
//                oTable1.fnDraw();
                layer.msg("修改成功", {time: 800});
                sum();
            } else {
                layer.alert(data.errorMsg);
                oTable1.fnDraw();
            }
        }
    });
}

function changeCountry(){
    var nationId = $("#nationId").val();
    if(nationId != null && nationId != ''){
        var nationName = $("#nationId option[value="+nationId+"]").text();
        $("#nationName").val(nationName);
    }
}

function toTipContent(id,content){
    layer.tips(content, $("#"+id), {
        tips: [1, '#3595CC'],
        time: 3000
    });
}
