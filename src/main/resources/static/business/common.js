function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}

function fmtDate(obj){
    var date =  new Date(obj);
    var y = 1900+date.getYear();
    var m = "0"+(date.getMonth()+1);
    var d = "0"+date.getDate();
    return y+"-"+m.substring(m.length-2,m.length)+"-"+d.substring(d.length-2,d.length);
}

function onCheck(e,treeId,treeNode) {
    var treeObj = $.fn.zTree.getZTreeObj("roleTree");
    var nodes = treeObj.getCheckedNodes(true);
    var result_nationId = "";
    var result_nationName = "";
    for (var i = 0; i < nodes.length; i++) {
        result_nationId += nodes[i].id + ",";
        result_nationName += nodes[i].name + ",";
    }
    if (result_nationId.indexOf(",") >= 0) {
        $("#input_nationId").val(result_nationId.substring(0, result_nationId.length - 1));
        $("#input_nationName").val(result_nationName.substring(0, result_nationName.length - 1));
    }
    else{
        $("#input_nationId").val(result_nationId);
        $("#input_nationName").val(result_nationName);
    }
}


// var list_upload = document.getElementsByClassName('jsUpload-All');
// for(i=0; i<list_upload.length; i++){
//     list_upload[i].addEventListener('click',function(){
//         alert(this.value)
//         // if(this.value.('upload-cdn.globeedu.com') == -1){
//         //         this.value = 'http://'+'upload-cdn.globeedu.com'+this.value;
//         // }
//         window.open(this.value);
//     })
// }

/*投资本金仅能输入数字和小数点*/
var precapital;
var list = document.getElementsByClassName('capital');
for(i=0; i<list.length; i++){
    list[i].addEventListener('focus', function() {
        if (this.value == '0.00') {
            this.value = '';
        } else {
            this.value = this.value.replace(/\.00/, '').replace(/(\.\d)0/,'$1');
        }
        precapital = this.value;
    });
    list[i].addEventListener('keyup', function() {

        this.value = this.value.replace(/^[\.]/, '').replace(/[^\d.]/g, '');
        if (this.value.split(".").length - 1 > 1) {
            this.value = precapital;
        }
        precapital = this.value;
    });
    list[i].addEventListener('blur', function() {
        this.value = this.value.replace(/[\.]$/, '');
        this.value = this.value.replace(/(.*)\.([\d]{2})(\d*)/g,'$1.$2');
        this.value = Number(this.value).toFixed(2);
    });
}

//文件预览
var visaFileList = document.getElementsByClassName('_visa_file');
for(i=0; i<visaFileList.length; i++){
    visaFileList[i].addEventListener('click', function() {
        $.post("/getPrivateUrl",{"fileUrl":this.getAttribute("value")},function (data,status) {
            window.open(data);
        });
    });
}

