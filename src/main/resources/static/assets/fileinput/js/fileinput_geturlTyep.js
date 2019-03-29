function getUrlTypeJsonConfig(url) {

    var x = null;

    if (url != null && url != "") {
        var path = url.substr(url.lastIndexOf("."));
        if (path.indexOf("?") == -1) {
            var type = path.substr(1, path.length);
        } else {
            var type = path.substr(1, path.indexOf("?") - 1);
        }
        console.log(type)
        if (type.match(/(xls|xlsx)$/i)) {
            x = {
                caption: "xls",
                type: "xls",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(ppt|pptx)$/i)) {
            x = {
                caption: "ppt",
                type: "ppt",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(mp3|wav)$/i)) {
            x = {
                caption: "mp3",
                type: "mp3",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(avi|mpg|mkv|mov|mp4|3gp|webm|wmv)$/i)) {
            x = {
                caption: "mov",
                type: "mov",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(txt|ini|csv|java|php|js|css)$/i)) {
            x = {
                caption: "txt",
                type: "txt",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(pdf)$/i)) {
            x = {
                caption: "pdf",
                type: "pdf",
                url: "/ueditor/delete",
                key: url
            };
        } else if (type.match(/(doc|docx)$/i)) {
            x = {
                caption: "doc",
                type: "doc",
                url: "/ueditor/delete",
                key: url,

            };
        } else if (type.match(/(eml)$/i)) {
            x = {
                caption: "eml",
                type: "eml",
                url: "/ueditor/delete",
                key: url
            };
        } else {
            x = {
                url: "/ueditor/delete",
                key: url
            };
        }
        return x;
    }

}

function getUrl(url) {
    if (url != null && url != "") {
        return url;
    }
    return null;
}

/**
 *
 * @param fileId 按钮
 * @param id input框
 */
function createFile(fileId, id) {
    //offerFile
    var offerFileUrls = [];
    var offerFileImages = [];
    if (id.val() != null && id.val() != "") {
        offerFileImages = id.val().split(",");
        var initialPreviewdetail = [];
        if (offerFileImages.length > 0) {
            for (var j = 0; j < offerFileImages.length; j++) {
                var url = offerFileImages[j];
                var u = getUrl(url);
                var x = getUrlTypeJsonConfig(url);
                if (u != null) {
                    offerFileUrls.push(u);
                }
                console.log(x);
                console.log(u);
                initialPreviewdetail.push(x);
            }
        }
    }
    $(fileId).fileinput({
        uploadAsync: false,
        language: 'zh',
        uploadUrl: '/uploadFiles',
        allowedFileExtensions: ['jpg', 'png', 'gif', 'jpeg', 'pdf', 'doc', 'docx', 'eml'],
        autoReplace: true,
        initialPreviewAsData: true,
        initialPreview: offerFileUrls,
        showRemove: false,
        initialPreviewConfig: initialPreviewdetail,
        overwriteInitial: false,
        dropZoneEnabled: false,
        maxFileCount: 10,
        maxFileSize: 2 * 1024,
        previewSettings: {
            image: {width: "150px", height: "180px",},
        }
    }).on("filebatchuploadsuccess", function (event, data) {
        var obj = data.response;
        console.log(data)
        if (obj.objNew.length) {//获取上传的图片信息
            for (var i = 0; i < obj.objNew.length; i++) {
                var object = {};
                object = obj.objNew[i];//获取图片链接
                offerFileImages.push(object);
            }
            id.val(JSON.stringify(offerFileImages));

        }
    }).on('filepredelete', function (event, data) {
        if (!confirm("确定删除原文件？删除后保存，即可生效")) {
            return false;
        }
        if (data != null) {
            id.val(id.val().replace(data, ""));
        }

    });
}







