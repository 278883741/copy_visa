$(function(){
    //触发click方法
    $('#defaultImage').on('click',function(){
        $('#fileInput').click();
    });
    //触发click方法
    $('#imageUploadLink').on('click',function(){
        $('#fileInput').click();
    });

    if(typeof uploadArray !='undefined' && uploadArray.length > 0)
    {
        $(uploadArray).each(function (index,obj)
        {
            var imageId = obj.imageId;
            var fileId = obj.fileId;
            var linkId = obj.linkId;

            if(imageId && fileId && linkId)
            {
                //触发click方法
                $('#'+imageId).on('click',function(){
                    $('#'+fileId).click();
                });
                //触发click方法
                $('#'+linkId).on('click',function(){
                    $('#'+fileId).click();
                });
            }
        })
    }
});

function uploadImage(inputId,imageId,fileId,bustype)
{
    if(typeof imageId == 'undefined' || $.trim(imageId).length == 0)
    {
        imageId = "defaultImage";
    }

    if(typeof fileId == 'undefined' || $.trim(fileId).length == 0)
    {
        fileId = "fileInput";
    }

    if(typeof bustype == 'undefined' || $.trim(bustype).length == 0)
    {
        bustype = 'common';
    }
    else
    {
        var length = bustype.length - 1;
        bustype = bustype.indexOf("/") == length?bustype:bustype+"/";
    }

    console.log('imageId='+imageId+',fileId='+fileId);

    $.ajaxFileUpload({
        url:"/upload",//需要链接到服务器地址
        secureuri:false,
        fileElementId:fileId,//文件选择框的id属性
        dataType: 'json',   //json
        data:{'bustype':bustype},
        success: function (data)
        {
            if(data.success)
            {
                $("#"+imageId).attr("src",data.obj);
                $('#'+imageId).css("display","block");
                $('#'+inputId).val(data.obj);
                $('#'+inputId).css("display","block");
            }
            else
            {
                $("#"+inputId).val('');
                $('#'+imageId).css("display","none");
                alertTipTop('上传失败,请重试');
            }
        },
        error:function(XMLHttpRequest, textStatus, errorThrown){
            alert(textStatus);
            alert('上传失败！');
        }
    });
};