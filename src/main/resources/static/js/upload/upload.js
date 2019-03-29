//判断照片大小以及格式
function checkUp(obj) {
    photoExt = obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀

    if (!getFileSize(obj)) {
        layer.msg("请上传20M以内的文件!请重新选择文件！", {
            icon: 2, time: 5000
        })
        return false;
    }
    if (!IsInArray(photoExt)) {
        layer.msg("请上传正确格式的文件!请重新选择文件！", {
            icon: 2, time: 5000
        })
        return false;
    }
    return true;
}


/**
 * 判断 文件大小
 * @param val
 * @returns {boolean}
 * @constructor
 */
function getFileSize(obj) {
    var fileSize = obj.files[0].size;//文件的大小，单位为字节B
    if (fileSize > 20 * 1024 * 1024) {
        // 文件大于指定大小
        return false;
    } else {
        return true;
    }
}

/**
 * 判断值是否出现在数组
 * @param val
 * @returns {boolean}
 * @constructor
 */
function IsInArray(val) {
    var array = new Array(['.jpg', '.JPG', '.png', '.PNG', '.gif', '.jpeg', '.JPEG','.doc','.docx','.pdf','.eml']);
    var testStr = ',' + array.join(",") + ",";
    return testStr.indexOf("," + val + ",") != -1;
}