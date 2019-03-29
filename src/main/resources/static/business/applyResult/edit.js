
$(function () {

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    $("#returnPage_edit").click(function(){
        window.location.href="/applyResult?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
    });

    $('#resultType').change(function(){
        changeStatus();
    })
    function changeStatus(){
        //拒绝
        if($('#resultType').children('option:selected').val()==2){
            $(".replyDeadlineTag").html("回复offer截止日期:");
            $(".studentConfirmDateTag").html("学生确认offer回复日期:");
            $(".createTimeTag").html("实际操作接受offer日期:");
            $(".schoolReplyDateTag").html("学校确认offer回复日期:");
            $(".reject").show();
            $(".offerInfo").children('input:not(":disabled")').val("");
            $(".offerInfo").hide();
            $(".acceptOffer").children('input:not(":disabled")').val(""); //清空接受信息
            $("#offerType option[value="+$("#offerType").val()+"]").removeAttr("selected");
            $("#offerType option[value='0']").attr("selected", true);
            $(".acceptOffer").hide();
            $("#upload_offer").show();
            //录取
        }else if($('#resultType').children('option:selected').val()==1){
            $(".replyDeadlineTag").html("回复offer截止日期:");
            $(".studentConfirmDateTag").html("学生确认offer回复日期:");
            $(".createTimeTag").html("实际操作接受offer日期:");
            $(".schoolReplyDateTag").html("学校确认offer回复日期:");
            $(".reject").children('input').val("");
            $(".reject").hide();
            $(".offerInfo").show();
            $(".acceptOffer").show();
        }else if($('#resultType').children('option:selected').val()==4){
            $(".replyDeadlineTag").html("回复waiting list截止日期:");
            $(".studentConfirmDateTag").html("学生确认waiting list回复日期:");
            $(".createTimeTag").html("实际操作接受日期waiting list日期:");
            $(".schoolReplyDateTag").html("学校确认waiting list回复日期:");
            $(".reject").children('input').val("");
            $(".reject").hide();
            $(".offerInfo").show();
            $(".acceptOffer").children('input:not(":disabled")').val(""); //清空接受信息
            $("#offerType option[value="+$("#offerType").val()+"]").removeAttr("selected");
            $("#offerType option[value='0']").attr("selected", true);
            $(".acceptOffer").hide();
        }else if($('#resultType').children('option:selected').val()==5){
            $(".replyDeadlineTag").html("回复预录取截止日期:");
            $(".studentConfirmDateTag").html("学生确认预录取回复日期:");
            $(".createTimeTag").html("实际操作接受日期预录取日期:");
            $(".schoolReplyDateTag").html("学校确认预录取回复日期:");
            $(".reject").children('input').val("");
            $(".reject").hide();
            $(".offerInfo").show();
            $(".acceptOffer").children('input:not(":disabled")').val(""); //清空接受信息
            $("#offerType option[value='0']").removeAttr("selected");
            $("#offerType option[value='0']").attr("selected", true);
            $(".acceptOffer").hide();
            //满位
        }else if($('#resultType').children('option:selected').val()==3){
            $(".replyDeadlineTag").html("回复offer截止日期:");
            $(".studentConfirmDateTag").html("学生确认offer回复日期:");
            $(".createTimeTag").html("实际操作接受offer日期:");
            $(".schoolReplyDateTag").html("学校确认offer回复日期:");
            $(".reject").children('input').val(""); //清空拒绝信息
            $(".reject").hide();
            $(".offerInfo").children('input:not(":disabled")').val("");
            $(".offerInfo").hide();
            $(".acceptOffer").children('input:not(":disabled")').val(""); //清空接受信息
            $("#offerType option[value="+$("#offerType").val()+"]").removeAttr("selected");
            $("#offerType option[value='0']").attr("selected", true);
            $(".acceptOffer").hide();
            $("#upload_offer").show();
        }else  if($('#resultType').children('option:selected').val()==6) {
            $(".replyDeadlineTag").html("回复offer截止日期:");
            $(".studentConfirmDateTag").html("学生确认offer回复日期:");
            $(".createTimeTag").html("实际操作接受offer日期:");
            $(".schoolReplyDateTag").html("学校确认offer回复日期:");
            $(".reject").hide();
            $(".offerInfo").children('input:not(":disabled")').val("");
            $(".offerInfo").hide();
            $(".acceptOffer").children('input:not(":disabled")').val(""); //清空接受信息
            $("#offerType option[value=" + $("#offerType").val() + "]").removeAttr("selected");
            $("#offerType option[value='0']").attr("selected", true);
            $(".acceptOffer").hide();
            $("#upload_offer").show();
        }
    }
    changeStatus();

});
function saveDateResult(){
    if($('#resultType').children('option:selected').val()==0){
        layer.alert('请选择申请结果！', {icon: 0});
        $("#submits").removeAttr("disabled");
        $("#returnPage_edit").removeAttr("disabled");
        return;
    }
    if($('#resultDate').val()==""){
        layer.alert('请填写结果到达日期！', {icon: 0});
        $("#submits").removeAttr("disabled");
        $("#returnPage_edit").removeAttr("disabled");
        return;
    }
    if($('#replyWay').children('option:selected').val()==0){
        layer.alert('请选择回复方式！', {icon: 0});
        $("#submits").removeAttr("disabled");
        $("#returnPage_edit").removeAttr("disabled");
        return;
    }
    var applyWay=$('#applyWay').children('option:selected').val();
    if(applyWay==""){
        layer.alert('请选择申请方式！', {icon: 0});
        return;
    }
    if ($("#collegeName").val() == "" || $("#collegeName").val() == '-1' || $("#collegeName").val() == null) {
        layer.alert('请选择院校！', {icon: 0});
        return;
    }
    if ($("#educationSection").val() == "" || $("#educationSection").val() == '-1' || $("#educationSection").val() == null) {
        layer.alert('请选择目标学历！', {icon: 0});
        return;
    }
    if(isClickAgency == true){
        if ($("#agency_no").val() == "" || $("#agency_no").val() == '-999' || $("#agency_no").val() == null) {
            layer.alert('请选择合作机构！', {icon: 0});
            return;
        }
    }
    if ($("#majorName").val() == "" || $("#majorName").val() == '-1' || $("#majorName").val() == null) {
        layer.alert('请选择专业！', {icon: 0});
        return;
    }
    if($("#courseName").val() == "" || $("#courseName").val() == '-1' || $("#courseName").val() == null){
        layer.alert('请选择学位！', {icon: 0});
        return;
    }
    //录取结果为拒绝
    if($('#resultType').children('option:selected').val()==2 && $('#replyReason').val()==""){
        layer.alert('请选择拒绝原因！', {icon: 0});
        $("#submits").removeAttr("disabled");
        $("#returnPage_edit").removeAttr("disabled");
        return;
    }
    $.ajax({
        url: "/applyResult/save",
        type: "post",
        data: $("#edit").serialize(),
        success: function (data) {
            $("#submits").removeAttr("disabled");
            $("#returnPage_edit").removeAttr("disabled");
            if (data.result) {
                if($("#auditStatus").val() == 4){
                    layer.confirm('操作成功，是否跳转到详情页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href='/applyResult/showDetailPage?id='+$("#id").val()+'&applyId='+$("#applyId").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }else{
                    layer.confirm('操作成功，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href="/applyResult?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }
            } else {
                if(data.errorCode =='2'){
                    layer.confirm(data.errorMsg, {
                        btn: ['确定'] //按钮
                    }, function(){
                        location.href="/applyResult?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }else{
                    layer.confirm('操作失败，是否跳转到列表页？', {
                        btn: ['确定','取消'] //按钮
                    }, function(){
                        location.href="/applyResult?applyId="+$("#applyId").val()+"&studentNo="+$("#studentNo").val();
                    }, function(){
                        window.location.replace(window.location.href)
                    });
                }

            }
        }
    });
}


