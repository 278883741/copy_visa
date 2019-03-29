/**
 * 替换文件路径中的 +
 * @param obj
 * @returns {boolean}
 */
function URLencode(sStr) {
    var a = sStr.replace(/\+/g, '%2B');
    return sStr.replace(/\+/g, '%2B');
}
