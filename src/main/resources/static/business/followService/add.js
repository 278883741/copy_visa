var uploadResult = true;
$(function () {
    //保存操作
        if(uploadResult = false) {
            layer.msg('正在上传中,请稍后保存!');
        } else{
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
                if($("#applyWay").val() == "-1"){
                    layer.alert('请选择购买途径！', {icon: 0});
                    return;
                } else if($("#applyWay").val() == 2){ //合作机构
        //            if($("#agency").val() == ""){
        //                layer.alert('请选择合作机构！', {icon: 0});
        //                return;
        //            }
                }
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
                                window.location.replace(window.location.href)
                            });
                        } else {
                            layer.confirm('操作失败，是否跳转到列表页？', {
                              btn: ['确定','取消'] //按钮
                            }, function(){
                                goBack(studentNo);
                            }, function(){
                                window.location.replace(window.location.href)
                            });
                        }
                    }
                });
            })
        }

    $("#submits_callback").click(function(){
        window.location.href = "/followService/list?studentNo="+$("#studentNo").val();
    })

    //监听modal关闭事件，清除整个页面的缓存数据
    $("#myModal").on("hidden.bs.modal", function () {
        $(this).removeData("bs.modal");
    });

    $('#id-input-file-3').ace_file_input({
            style:'well',
            btn_choose:'Drop files here or click to choose',
            btn_change:null,
            no_icon:'icon-cloud-upload',
            droppable:true,
            thumbnail:'small',//large | fit
            before_change:function(files, dropped) {
                return true;
            },
            before_remove : function() {
                $("#input_fileName").val("");
                return true;
            },
            preview_error : function(filename, error_code) {
                //name of the file that failed
                //error_code values
                //1 = 'FILE_LOAD_FAILED',
                //2 = 'IMAGE_LOAD_FAILED',
                //3 = 'THUMBNAIL_FAILED'
                //alert(error_code);
            }
        }).on('change', function(){
        //获取文件大小
        if (!checkUp(this)) {
            return false;
        }
            console.log($(this).data('ace_input_files'));
            console.log($(this).data('ace_input_method'));
            uploadResult = false;
            layer.msg('正在上传中...', {
                icon: 16
                ,shade: 0.01
            });

            $("#form_file").append($("#id-input-file-3").parent().children(":first"));
            $("#form_file").hide();

            $("#form_file").ajaxSubmit(function(message) {
                uploadResult = true;
                console.log(message);
                var fileName = message.obj;
                var code = message.code;
                if(code == "1"){
                    layer.msg("请上传正确的文件格式!")
                }else if(message.success){
                    $("#input_fileName").val(fileName);
                    layer.msg("上传成功!");
                }
                // if (message) {
                //     $(".alert-success").text("上传成功").show();
                //     $('#alert').attr('class', 'alert alert-success').show();
                //     setTimeout("$('#alert').hide()", 2000);
                //     $("#myModal").modal("hide");
                // } else {
                //     $('#alertText').text('上传失败');
                //     $('#alert').attr('class', 'alert alert-danger').show();
                //     setTimeout("$('#alert').hide()", 2000);
                //     $("#myModal").modal("hide");
                // }
            });
        });

    // 监听事件
    $("#visitType").change(function(){
        var visitType = $("#visitType").val();
        // 保险
        if(visitType == 2){
            $(".insurance-control").show();
        }else{
            //清空信息
            $("#insuranceLength").val("");
            $("#insuranceType option[value="+$("#insuranceType").val()+"]").removeAttr("selected");
            $("#insuranceType option[value='-1']").attr("selected", true);
            $(".insurance-control").hide();
        }

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
        // 合作机构
        if(applyWay == 2){
            $(".agency-control").show();
        }else{
            $("#agency option[value="+$("#agency").val()+"]").removeAttr("selected");
            $("#agency option[value='-1']").attr("selected", true);
            $(".agency-control").hide();
        }
    })

    //加载事件
    $(".insurance-control").hide();
    $(".agency-control").hide();
    $(".agency").hide();
});

function goBack(studentNo){
    location.href="/followService/list?studentNo="+studentNo;
}
