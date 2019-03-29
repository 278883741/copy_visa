$(function () {

    if($("#canUpdate").val()){
        layer.alert("获签信息已通过审核，监护信息不可以修改！",{
            icon:0,
        },function(){
            goBack($("#studentNo").val());
        });
    }

    $("#submits_callback").click(function(){
        window.location.href = "/followService/list?studentNo="+$("#studentNo").val();
    })

    if(uploadResult == false){
        layer.msg("正在上传中,请稍后保存!")
    }else{
        $("#submits").click(function () {

            var studentNo = $("#studentNo").val();

            if($("#visitType").val() == "-1"){
                layer.alert('请选择服务类型！', {icon: 0});
                return;
            } else if($("#visitType").val() == 2){ //保险
//            if($("#insuranceType").val() == ""){
//                layer.alert('请选择保险类型！', {icon: 0});
//                return;
//            }
//            if($("#insuranceLength").val() == ""){
//                layer.alert('请输入保险长度！', {icon: 0});
//                return;
//            }
            }
//        if($("#applyWay").val() == "-1"){
//            layer.alert('请选择购买途径！', {icon: 0});
//            return;
//        } else if($("#applyWay").val() == 2){ //合作机构
//            if($("#agency").val() == ""){
//                layer.alert('请选择合作机构！', {icon: 0});
//                return;
//            }
//        }
//        if($("#paymentStatus").val() == "-1"){
//            layer.alert('请选择是否付费！', {icon: 0});
//            return;
//        }
            //加载框
            var loadIndex = layer.load(1, {shade: [0.5, '#393D49']});
            $.ajax({
                url: "/followService/saveData",
                type: "post",
                data: $("#edit").serialize(),
                success: function (data) {
                    layer.close(loadIndex);
                    if (data.result) {
                        layer.confirm('操作成功，是否跳转到列表页？', {
                            btn: ['确定','取消'] //按钮
                        }, function(){
                            goBack(studentNo);
                        }, function(){
                            parent.layer.close();
                        });
                    } else {
                        var msg = data.errorMsg != ""? data.errorMsg: "操作失败，是否跳转到列表页？";
                        layer.confirm(msg, {
                            btn: ['确定','取消'] //按钮
                        }, function(){
                            goBack(studentNo);
                        }, function(){
                            parent.layer.close();
                        });
                    }
                }
            });
        })
    }
    //保存操作


    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });


        $("#offer_upload").click(function(){
            $("#upload_office").attr("href",$("#offerAttachment").text());
        })

        // 监听事件
        $("#visitType").change(function(){
            var visitType = $("#visitType").val();
            // 保险
            if(visitType == 2){
                $(".insurance-control").show();
            }else{
                $("#insuranceType option[value="+$("#insuranceType").val()+"]").removeAttr("selected");
                $("#insuranceType option[value='-1']").attr("selected", true);
                $("#insuranceLength").val("");
                $(".insurance-control").hide();
            }
            //隐藏机构
            $("#agency option[value="+$("#agency").val()+"]").removeAttr("selected");
            $("#agency option[value='-1']").attr("selected", true);
            $(".agency").hide();
            if(visitType == 4 || visitType == 5) {
                $(".4").show(); //住宿
            }else{
                $("."+visitType).show();
            }
        })


        $("#applyWay").change(function(){
            var applyWay = $("#applyWay").val();
            // 学校
            if(applyWay == 2){
                $(".agency-control").show();
            }else{
                $("#agency option[value="+$("#agency").val()+"]").removeAttr("selected");
                $("#agency option[value='-1']").attr("selected", true);
                $(".agency-control").hide();
            }
        })

        //加载事件
        var visitType = $("#visitType").val();
        if(visitType != 2){
            $(".insurance-control").hide();
        }
        var applyWay = $("#applyWay").val();
        if(applyWay == 1){
            $(".agency-control").hide();
        }
        $(".agency").hide();
        $("."+visitType).show();

        //机构回写
        var agencyCode = $("#agencyCode").val();
        $("#agency option[value="+agencyCode+"]").attr("selected", true);
        var feeUnit = $("#feeUnit").val();
        $("#feeUnitSelect option[value="+feeUnit+"]").attr("selected", true);
});

function goBack(studentNo){
    location.href="/followService/list?studentNo="+studentNo;
}
