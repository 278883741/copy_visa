//更新会话框最新消息
function updateSessMsg(to_id, sessName, nick, msg){
    var nameDiv = document.getElementById("nameDiv_" + to_id);
    nameDiv.innerHTML = `<h6>`+sessName+`</h6><p>`+nick+`：`+msg+`</p>`;
}

//更新最近会话的未读消息数

function updateSessDiv(sess_type, to_id, name, unread_msg_count) {
    var badgeDiv = document.getElementById("UnreadTipsDiv_" + to_id);
    if (badgeDiv && unread_msg_count > 0) {
           badgeDiv.classList = ['active'];
    } else if (badgeDiv == null) { //没有找到对应的聊天id
        var headUrl;
        if (sess_type == webim.SESSION_TYPE.C2C) {
            headUrl = friendHeadUrl;
        } else {
            headUrl = groupHeadUrl;
        }
        // 新增会话
        addSess(sess_type, to_id, name, headUrl, unread_msg_count, 'sesslist');
        // 标记未读
        updateSessDiv(sess_type, to_id, name, unread_msg_count);
    }
}

//新增一条最近会话
function addSess(sess_type, to_id, group_name, face_url, unread_msg_count, from_nick, addPositonType) {

    var sess = recentSessMap[sess_type + "_" + to_id];

    var sessDivId = "sessionLi_" + to_id;
    var sessDiv = document.getElementById(sessDivId);
    if (sessDiv) { //先判断是否存在会话DIV，已经存在，则不需要增加
        return;
    }
    var sessList = document.getElementsByClassName("talk-list")[0];
    sessDiv = document.createElement("li");
    sessDiv.id = sessDivId;

    //添加单击用户头像事件
    sessDiv.onclick = function() {
        if (sessDiv.className == "sessinfo-sel") return;
        onSelSess(sess_type, to_id);
    };

    // 格式化群组名称
    group_name = groupNameFormat(to_id, group_name);
    if (group_name.length > maxNameLen) { //名称过长，截取一部分
        group_name = group_name.substr(0, maxNameLen) + "...";
    }

    //会话头像
    var studentShortName = "";
    var imgDiv = document.createElement("div");
    imgDiv.className = "people-name-block";
    studentListAll.forEach(function(index){
        if(index.group_id == to_id){
            studentShortName = index.shortName != null ? index.shortName : '';
        }
    })
    imgDiv.innerHTML = `<div class="people-name-block one"><span>${studentShortName}</span></div>`
//    imgDiv.innerHTML = `<img src="${face_url}" alt="">`;



    var nameDiv = document.createElement("div");
    nameDiv.id = "nameDiv_" + to_id;
    nameDiv.className = "group-name";

    if(from_nick == ''){
        var tag_list = [
                "Tag_Profile_IM_Nick"//昵称
            ];
            var options = {
                'To_Account': [sess.C2cAccount],
                'TagList': tag_list
            };
            webim.getProfilePortrait(
                options,
                function (resp) {
                    if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                        for (var i in resp.UserProfileItem) {
                            var to_account = resp.UserProfileItem[i].To_Account;
                            var nick = null;
                            for (var j in resp.UserProfileItem[i].ProfileItem) {
                                switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                                    case 'Tag_Profile_IM_Nick':
                                        nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                        break;
                                }
                            }
                        }
                        nameDiv.innerHTML = `<h6>`+group_name+`</h6><p>`+webim.Tool.formatText2Html(nick)+`：`+sess.MsgShow+`</p>`;
                    }
                },
                function (err) {
                    alert(err.ErrorInfo);
                }
            );
    }else{
        if(sess){
            nameDiv.innerHTML = `<h6>`+group_name+`</h6><p>`+from_nick+`：`+sess.MsgShow+`</p>`;
        }else{
            nameDiv.innerHTML = `<h6>`+group_name+`</h6><p></p>`;
        }

    }

    var badgeDiv = document.createElement("div");
    badgeDiv.className = "tips";
    badgeDiv.innerHTML = `<p></p>`;
    var badgeSpan = document.createElement("span");
    badgeSpan.id = "UnreadTipsDiv_" + to_id;
    badgeDiv.appendChild(badgeSpan);

    sessDiv.appendChild(imgDiv);
    sessDiv.appendChild(nameDiv);
    sessDiv.appendChild(badgeDiv);
    if (!addPositonType || addPositonType == 'TAIL') {
        sessList.appendChild(sessDiv); //默认插入尾部
    } else if (addPositonType == 'HEAD') {
        sessList.insertBefore(sessDiv); //插入头部
    } else {
        console.log(webim.Log.error('未知addPositonType' + addPositonType));
    }
}

//删除会话

function delChat(sess_type, to_id) {

    var data = {
        'To_Account': to_id,
        'chatType': sess_type
    }
    webim.deleteChat(
        data,
        function(resp) {
//            $("#sessDiv_" + to_id).remove();
            console.log(resp);
        }
    );
}


//删除会话

function deleteSessDiv(sess_type, to_id) {
    var sessDiv = document.getElementById("sessionLi_" + to_id);
    sessDiv && sessDiv.parentNode.removeChild(sessDiv);
    console.log('deleteSessDiv:'+sess_type+to_id);

//    if(selToID && selToID == to_id){
//        $("#group-member-list").html();
//        setTalkListTitle();
//        clearTalkContent();
//        selToID = null;
//        webim.MsgStore.delSessByTypeId(webim.SESSION_TYPE.GROUP, to_id)
//        delChat(sess_type == 'C2C' ? 1 : 2 , to_id);
//    }

//    layer.confirm("群组成员发生变化，点击【确认】！", {
//        icon: 1,
//        btn: ['确定']
//    },function(){
//        window.location.reload();
//    });
}


//更新最近会话的名字

function updateSessNameDiv(sess_type, to_id, newName) {

    var nameDivId = "nameDiv_" + to_id;
    var nameDiv = document.getElementById(nameDivId);
    if (nameDiv) {
        if (newName.length > maxNameLen) { //帐号或昵称过长，截取一部分
            newName = newName.substr(0, maxNameLen) + "...";
        }
        nameDiv.innerHTML = webim.Tool.formatText2Html(newName);
    }
}

//更新最近会话的头像

function updateSessImageDiv(sess_type, to_id, newImageUrl) {
    if (!newImageUrl) {
        return;
    }
    var faceImageId = "faceImg_" + to_id;
    var faceImage = document.getElementById(faceImageId);
    if (faceImage) {
        faceImage.innerHTML = webim.Tool.formatText2Html(newImageUrl);
    }
}

//设置对话框样式  参数：选中对话框Id
function setSelSessStyleOn(newSelToID) {
    var selSessDiv = document.getElementById("sessionLi_" + newSelToID);
    if (selSessDiv) {
        selSessDiv.classList = ["active"]; //设置当前选中用户的样式为选中样式
    } else {
        webim.Log.warn("不存在selSessDiv: selSessDivId=" + "sessionLi_" + newSelToID);
    }

    // 隐藏未读提示
    var selBadgeDiv = document.getElementById("UnreadTipsDiv_" + newSelToID);
    if (selBadgeDiv) {
        selBadgeDiv.className = "";
        selBadgeDiv.style = "";
    } else {
        webim.Log.warn("不存在selBadgeDiv: selBadgeDivId=" + "UnreadTipsDiv_" + selToID);
    }
}

// 设置会话框为未选中状态
function setSelSessStyleOff(preSelToId) {
    var preSessDiv = document.getElementById("sessionLi_" + preSelToId);
    if (preSessDiv) {
        preSessDiv.classList = [""]; //将之前选中用户的样式置为未选中样式
    } else {
        webim.Log.warn("不存在preSessDiv: selSessDivId=" + "sessDiv_" + preSelToId);
    }

    // 隐藏成员列表
    hideMemberList();
}