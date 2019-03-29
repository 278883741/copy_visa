var schoolIdSet = new Set([]);
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
//            "leftColumns": 2, //固定左边第一列
//        },

        "bFilter": false,//是否使用搜索
        "bProcessing": true,
        "bDeferRender": true,//
        "sPaginationType": "bs_normal",//分页样式
        "bRetrieve": true,//是否调用后台
        "bServerSide": true,//是否以后台分页
        "sAjaxSource": "/invoice/add/data",//请求路径
        "fnServerData": function (sSource, aoData, fnCallback) {
            aoData.push(
                {"name": "schoolIdStr", "value": $("#schoolIdStrOld").val()},
                {"name": "tempKey", "value": $("#tempKey").val()},
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
                    for(var i of schoolIdSet){
                        if(i == data.schoolId){
                            return '<input type="checkbox" onclick="check(this)" checked="checked" class="checkchild" name="checkchild" value="'+data.schoolId+'"/> ';
                        }
                    }
                    return '<input type="checkbox" onclick="check(this)" class="checkchild" name="checkchild" value="'+data.schoolId+'"/> ';
                }
            },
            {"sClass": "center", "bSortable": false, "mData": "studentNo"},
            {"sClass": "center", "bSortable": false, "mData": "spelling"},
            {"sClass": "center", "bSortable": false, "mData": "birthday"},
            {"sClass": "center", "bSortable": false, "mData": "schoolNo"},
            {"sClass": "center", "bSortable": false, "mData": "nationName"},
            {"sClass": "center", "bSortable": false, "mData":
                function (data) {
                    if (data.collegeType == "" || data.collegeType == null) {
                        return "";
                    } else {
                        if (data.collegeType == "1") {
                            return "主课";
                        } else if (data.collegeType == "2") {
                            return "语言";
                        } else if (data.collegeType == "3") {
                            return "预备";
                        } else {
                            return "";
                        }
                    }
                }
            },
            {"sClass": "center", "bSortable": false, "mData": function(data){
                if(data.commissionBelong == 1){ return "AEA" }
                if(data.commissionBelong == 2){ return "ECIE" }
                if(data.commissionBelong == 3){ return "BAEC" }
                return "";
            }},
            {"sClass": "center", "bSortable": false, "mData": "courseName"},
            {"sClass": "center", "bSortable": false, "mData": function(data){
                var majorName = data.majorName != null ? data.majorName : '';
                if (majorName.length > 18) {
                    return '<a href="javascript:void(0);" id="major'+data.schoolId+'" onclick="toTipContent(\'major' + data.schoolId + '\',\'' +  majorName.replace(/\r\n/mg,'  ') + '\')" >'+majorName.substring(0,17)+"..."+'</a>';
                }else{
                    return majorName;
                }
            }},
            {"sClass": "center", "bSortable": false, "mData": "schoolTuition"},
            {"sClass": "center", "bSortable": false, "mData": "studyWeek"},
            {"sClass": "center", "bSortable": false, "mData": "startDate"},
            {"sClass": "center", "bSortable": false, "mData": "schoolRate"},
            {"sClass": "center", "bSortable": false, "mData": "currency"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceMoney"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceGst"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceSum"},
            {"sClass": "center", "bSortable": false, "mData": "invoiceRemark"}
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
            schoolIdSet.add(this.value);
        });
    } else {
        $(that).removeAttr('checked')
        $('.checkchild').each(function () {
            this.checked = false;
            schoolIdSet.delete(this.value);
        });
    }
}

function check(label){
    if(label.checked){
        schoolIdSet.add(label.value);
    }else{
        schoolIdSet.delete(label.value);
    }
}

//批量编辑
$('#save').on('click',function(){
    var invoiceNo = $("#invoiceNo").val();
    if(invoiceNo == null || invoiceNo == ""){
        layer.alert('请输入InvoiceNo!', {icon: 0});
        return;
    }
    var rate = $("#schoolRate").val();
    if(rate != "" && rate > 1){
        layer.alert('请输入正确的佣金比例！', {icon: 0});
        return;
    }
    var gstRate = $("#gstRate").val();
    if(gstRate != "" && gstRate > 1){
        layer.alert('请输入正确的GST比例！', {icon: 0});
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

   var schoolIdStr = "";
    for(var i of schoolIdSet){
        schoolIdStr += i;
        schoolIdStr += ',';
    }
    if(schoolIdStr == ""){
        layer.alert("请勾选至少一条记录！",{
            icon: 0
        })
        return;
    }

    $("#schoolIdStr").val(schoolIdStr);

    addSave(invoiceNo);

//    $.ajax({
//        url: "/invoice/checkSchool",
//        type: "post",
//        data: {
//            schoolIdStr: number,
//            invoiceNo: invoiceNo
//        },
//        success: function (data) {
//            if (data.result) {
//                addSave(invoiceNo)
//            } else {
//                layer.confirm('所选佣金信息已有InvoiceNO，是否继续添加？', {
//                    btn: ['确定','取消'] //按钮
//                }, function(){
//                    addSave(invoiceNo);
//                }, function(){
//                    layer.close();
//                });
//            }
//        }
//    });

});

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

function addSave(invoiceNo){
    $.ajax({
        url: "/invoice/save",
        type: "post",
        data: $("#edit").serialize(),
        success: function (data) {
            if (data.result) {
                layer.confirm("操作成功！", {
                    icon: 1,
                    btn: ['确定']
                },function(){
                    window.location.href = "/invoice/edit?invoiceNo="+invoiceNo;
                });
            } else {
                layer.alert(data.errorMsg, {
                    icon: 2,
                });
            }
        }
    });
}

$('#cancel').on('click',function(){
    window.location.href="/commissionManage/list";
});

function toTipContent(id,content){
    layer.tips(content, $("#"+id), {
        tips: [1, '#3595CC'],
        time: 3000
    });
}