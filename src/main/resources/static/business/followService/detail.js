$(function () {

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
            console.log($(this).data('ace_input_files'));
            console.log($(this).data('ace_input_method'));

            $("#form_file").append($("#id-input-file-3").parent().children(":first"));
            $("#form_file").hide();

            $("#form_file").ajaxSubmit(function(message) {
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
                $(".insurance-control").hide();
            }
            //隐藏机构
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

        var agencyCode = $("#agencyCode").val();
        $("#agency option[value="+agencyCode+"]").attr("selected", true);
        var feeUnit = $("#feeUnit").val();
        $("#feeUnitSelect option[value="+feeUnit+"]").attr("selected", true);
});
