
//监听新消息事件
var msgList = [];
var dateStart = null;
var dateEnd = null;
//newMsgList 为新消息数组，结构为[Msg]
function onMsgNotify(newMsgList) {
    //console.warn(newMsgList);
    var sess, newMsg;
    //获取所有聊天会话
    var sessMap = webim.MsgStore.sessMap();

    for (var j in newMsgList) { //遍历新消息
        newMsg = newMsgList[j];

//        if('@TIM#SYSTEM' == newMsg.fromAccount){
//            return;
//        }

        // 播放提示音
        if(iBellBtn && newMsg.fromAccount != loginInfo.identifier){
            $("#msg-tips-voice")[0].play();
        }

        if (!selToID) { //没有聊天对象
            selToID = newMsg.getSession().id();
            selType = newMsg.getSession().type();
            selSess = newMsg.getSession();
            var headUrl;
            if (selType == webim.SESSION_TYPE.C2C) {
                headUrl = friendHeadUrl;
            } else {
                headUrl = groupHeadUrl;
            }
            addSess(selType, selToID, newMsg.getSession().name(), headUrl, 0, 'sesslist'); //新增一个对象
            setSelSessStyleOn(selToID);
        }
        if (newMsg.getSession().id() == selToID) { //为当前聊天对象的消息
            //在聊天窗体中新增一条消息
            //console.warn(newMsg);
            addMsg(newMsg);
        }
        msgList.push(newMsg.elems[0].content.text);

        var sessionLi = document.getElementById("sessionLi_" + newMsg.sess._impl.id);
        if(sessionLi){
            var msgContent = '';
            if(newMsg.elems[0].type == 'TIMImageElem'){
                msgContent = '[图片]';
            }else{
                msgContent = newMsg.elems[0].content.text;
            }
            updateSessMsg(newMsg.sess._impl.id, newMsg.sess._impl.name, newMsg.fromAccountNick, msgContent);
        }
    }
    //消息已读上报，以及设置会话自动已读标记
//     webim.setAutoRead(selSess, true, true);

    for (var i in sessMap) {
        sess = sessMap[i];
        if (selToID != sess.id()) { //更新其他聊天对象的未读消息数
            if (!dateStart) {
                dateStart = new Date();
            }
            updateSessDiv(sess.type(), sess.id(), sess.name(), sess.unread());
            console.debug(sess.id(), sess.unread());
            dateEnd = new Date();
        }
    }
}

//监听直播聊天室新消息事件
//newMsgList 为新消息数组，结构为[Msg]
//监听大群新消息（普通，点赞，提示，红包）
function onBigGroupMsgNotify(newMsgList) {
    var newMsg;
    for (var i = newMsgList.length - 1; i >= 0; i--) { //遍历消息，按照时间从后往前
        newMsg = newMsgList[i];
        webim.Log.warn('receive a new group(AVChatRoom) msg: ' + newMsg.getFromAccountNick());
        //显示收到的消息
        addMsg(newMsg);
    }
}


//消息已读通知
function onMsgReadedNotify(notify) {
    var sessMap = webim.MsgStore.sessMap()[webim.SESSION_TYPE.C2C + notify.From_Account];
    if (sessMap) {
        var msgs = _.clone(sessMap.msgs());
        var rm_msgs = _.remove(msgs, function (m) {
            return m.time <= notify.LastReadTime
        });
        var unread = sessMap.unread() - rm_msgs.length;
        unread = unread > 0 ? unread : 0;
        //更新sess的未读数
        sessMap.unread(unread);
        // console.debug('更新C2C未读数:',notify.From_Account,unread);
        //更新页面的未读数角标

        if (unread > 0) {
            $("#badgeDiv_" + notify.From_Account).text(unread).show();
        } else {
            $("#badgeDiv_" + notify.From_Account).val("").hide();
        }
    }
}