// sdk登录
function webimLogin() {
    webim.login(
        loginInfo, listeners, options,
        function(resp) {
            loginInfo.identifierNick = resp.identifierNick; //设置当前用户昵称
            loginInfo.Headurl = resp.headurl; //设置当前用户头像
            initDemoApp();
        },
        function(err) {
            alert(err.ErrorInfo);
        }
    );
}

//初始化demo
function initDemoApp() {
    //展开聊天界面
    showTalkContent();
    //初始化好友和群信息
    initInfoMap(initInfoMapCallbackOK);
}

// 展示聊天窗口
function showTalkContent(){
	var talkCon = document.getElementsByClassName("talk-content-list-wrap")[0];
	talkCon.classList = ["talk-content-list-wrap active"];
}

function initInfoMap(cbOk) {
    //读取我的好友列表
    initInfoMapByMyFriends(
        //读取我的群组列表
        initInfoMapByMyGroups(
            cbOk
        )
    );
}

function initInfoMapCallbackOK() {
    initRecentContactList(initRecentContactListCallbackOK);
}

//初始化我的最近会话列表框回调函数
function initRecentContactListCallbackOK() {
    onSelSess(selType, selToID);
}

//切换好友或群组聊天对象
function onSelSess(sess_type, to_id) {
    if (selToID != null) {

        //初始化群组成员
        getGroupMemberInfo(to_id);

        //将之前选中用户的样式置为未选中样式
        setSelSessStyleOff(selToID);

        //设置之前会话的已读消息标记
        webim.setAutoRead(selSess, false, false);

        //清空聊天界面
        clearTalkContent();

        selToID = to_id;
        //设置当前选中用户的样式为选中样式
        setSelSessStyleOn(to_id);

        //修改对话框title
        getGroupInfo(selToID, function(resp){
            setTalkListTitle(selToID, resp.GroupInfo[0].Name, resp.GroupInfo[0].MemberNum);
        });

        // 初始化当前会话对象
        selSess = webim.MsgStore.sessByTypeId(sess_type, to_id);
        //设置当前会话的已读消息标记
        webim.setAutoRead(selSess, true, false);

        //临时消息回显
        var tmgmsgtosend = webim.Tool.getCookie("tmpmsg_" + selToID);
        if (tmgmsgtosend) {
            $("#send_msg_text").val(tmgmsgtosend);
        } else {
            $("#send_msg_text").val('');
        }

        bindScrollHistoryEvent.reset();

        //添加 "加载历史记录" 入口
        initTalkContent();

        var sessMap = webim.MsgStore.sessMap(); //获取到之前已经保存的消息
        var sessCS = webim.SESSION_TYPE.GROUP + selToID;
        if (sessMap && sessMap[sessCS]) { //判断之前是否保存过消息
            selType = webim.SESSION_TYPE.GROUP
            bindScrollHistoryEvent.init();

            function compare(property) {
                return function(a, b) {
                    var value1 = a[property];
                    var value2 = b[property];
                    return value1 - value2;
                }
            }
            var sessMapOld = sessMap[sessCS]._impl.msgs.sort(compare('time'));

            for (var i = 0; i < sessMapOld.length; i++) {
                addMsg(sessMapOld[i]); //显示已经保存的消息
            }
        } else {
            if (sess_type == webim.SESSION_TYPE.GROUP) {
                if (selType == webim.SESSION_TYPE.C2C) {
                    selType = webim.SESSION_TYPE.GROUP;
                }
                selSess = null;
                webim.MsgStore.delSessByTypeId(selType, selToID);

                getLastGroupHistoryMsgs(function(msgList) {
                    getHistoryMsgCallback(msgList);
                    bindScrollHistoryEvent.init();
                }, function(err) {
                    alert(err.ErrorInfo);
                });

            } else {
                if (selType == webim.SESSION_TYPE.GROUP) {
                    selType = webim.SESSION_TYPE.C2C;
                }
                //如果是管理员账号，则为全员推送，没有历史消息
                if (selToID == AdminAcount) {
                    var sess = webim.MsgStore.sessByTypeId(selType, selToID);
                    if (sess && sess.msgs() && sess.msgs().length > 0) {
                        getHistoryMsgCallback(sess.msgs());
                    } else {
                        getLastC2CHistoryMsgs(function(msgList) {
                            getHistoryMsgCallback(msgList);
                            bindScrollHistoryEvent.init();
                        }, function(err) {
                            alert(err.ErrorInfo);
                        });
                    }
                    return;
                }

                //拉取漫游消息
                getLastC2CHistoryMsgs(function(msgList) {
                    getHistoryMsgCallback(msgList);
                    //绑定滚动操作
                    bindScrollHistoryEvent.init();
                }, function(err) {
                    alert(err.ErrorInfo);
                });
            }
        }
    }
}

//读取群组基本资料-高级接口
var getGroupInfo = function(group_id, cbOK, cbErr) {
    var options = {
        'GroupIdList': [
            group_id
        ],
        'GroupBaseInfoFilter': [
            'Type',
            'Name',
            'Introduction',
            'Notification',
            'FaceUrl',
            'CreateTime',
            'Owner_Account',
            'LastInfoTime',
            'LastMsgTime',
            'NextMsgSeq',
            'MemberNum',
            'MaxMemberNum',
            'ApplyJoinOption',
            'ShutUpAllMember'
        ],
        'MemberInfoFilter': [
            'Tag_Profile_IM_Nick',
            'Account',
            'Role',
            'JoinTime',
            'LastSendMsgTime',
            'ShutUpUntil'
        ]
    };
    webim.getGroupInfo(
        options,
        function(resp) {
            if (resp.GroupInfo[0].ShutUpAllMember == 'On') {
                alert('该群组已开启全局禁言');
            }
            if (cbOK) {
                cbOK(resp);
            }
        },
        function(err) {
            alert(err.ErrorInfo);
        }
    );
};

// 点击聊天对话框列表按钮
$('.left-side ol li').eq(0).on('click', function(){
    $(this).addClass('active');
    $('.left-side ol li').eq(1).removeClass('active');
    $('.student-list').removeClass('active');
    $('.talk-list').addClass('active');
    $('.no-student').removeClass('active');
    $('.talk-content-list-wrap').addClass('active');
    $('.send-message-wrap').removeClass('active');
    $('#left-title').text('会话');
    // 清空搜索
    $('#student-search').val('');
    $("#no-student").addClass("active");
    $(".send-message").removeClass('active')
    clearSearchResult();
    if(selSess != null){
        var selToID = selSess._impl.id;
        getGroupInfo(selToID, function(resp){
            setTalkListTitle(selToID, resp.GroupInfo[0].Name, resp.GroupInfo[0].MemberNum);
        });
    }else{
        setTalkListTitle();
    }

});


$('.left-side ol li').eq(1).on('click', function(){
    $(this).addClass('active');
    $('.left-side ol li').eq(0).removeClass('active');
    $('.talk-list').removeClass('active');
    $('.student-list').addClass('active');
    $('.no-student').removeClass('active');
	$('.talk-content-list-wrap').removeClass('active');
	$('.send-message-wrap').addClass('active');
	$('#no-select-tips').addClass('active');
	$('#left-title').text('学生');
	$('.group-member').html(`<p id="right-title">学生详情</p>`);

    hideMemberList();
    initStudentGroupInfo();
});

function initStudentGroupInfo(){
	// 初始化学生列表数据
    $.ajax({
        "url" : "/webim/student/search",
        "data" : {
            memberId : identifier
        },
        "type" : "post",
        "success" : function(data){
            if(data != ''){
                studentListAll = data;
            }
        },
        "error" : function(err){
        }
    });
}





// 点击回车发送消息
$('#text').keydown(function(event){
  if(event.keyCode ==13){
    if($('#text').val() != ''){
        $("#sendBtn").click();
    }
  }
});

// 点击回车发送消息
$('#text').keyup(function(event){
    if(event.keyCode ==13){
        $('#text').val('');
    }
});

// 点击发送消息按钮
$('#sendBtn').on('click', function(){
  if (!selToID) {
      alert("你还没有选中好友或者群组，暂不能聊天");
      $("#send_msg_text").val('');
      return;
  }
  //获取消息内容
  var msgContent = $('#text').val();
  var msgLen = webim.Tool.getStrBytes(msgContent);

  if (msgContent.length < 1) {
      alert("发送的消息不能为空!");
      $("#send_msg_text").val('');
      return;
  }
  var maxLen, errInfo;
  if (selType == webim.SESSION_TYPE.C2C) {
      maxLen = webim.MSG_MAX_LENGTH.C2C;
      errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
  } else {
      maxLen = webim.MSG_MAX_LENGTH.GROUP;
      errInfo = "消息长度超出限制(最多" + Math.round(maxLen / 3) + "汉字)";
  }
  if (msgLen > maxLen) {
      alert(errInfo);
      return;
  }
  //发消息处理
  handleMsgSend(msgContent);

  //清空消息
  $('#text').val("");
});

// 点击铃铛
var iBellBtn = false;
$('#bellBtn').on('click', function(){
  if(iBellBtn){
    $(this).addClass('icon-jingyin');
    $(this).removeClass('icon-bell');
  }else{
    $(this).removeClass('icon-jingyin');
    $(this).addClass('icon-bell');
  }

  iBellBtn = !iBellBtn;
});


// 清空会话框
function clearSessionList(){
    $(".talk-list").html();
}

// 修改对话框title
function setTalkListTitle(to_id, titleName, memberCount){
    // 格式化群组名称
    titleName = groupNameFormat(to_id, titleName);
    $(".talk-content-title-wrap").html("");
    var titleDiv = document.createElement("div");
    titleDiv.id = "talk-title-"+to_id;
    titleDiv.classList = ["group-member"];
    var titleP = document.createElement("p");
    if(to_id != null){
        titleP.innerHTML = titleName + `（<span id="member-total">`+ memberCount +`</span>）<i class="iconfont icon-arrow"></i>`;
    }else{
        titleP.innerHTML = `消息内容`;
    }
    titleDiv.appendChild(titleP);
    $(".talk-content-title-wrap").append(titleDiv);

    // 展开群成员的点击
    var iBtn = true;
    var timer0 = null;
    var timer1 = null;
    $('.group-member').on('click', function(){
      clearTimeout(timer0)
      clearTimeout(timer1)

      if(iBtn){
        $(this).addClass('active');
        $('.member-list').addClass('active');
        timer0 = setTimeout(function(){
          $('.member-list').css('opacity', 1)
        },10);
      }else{
        $(this).removeClass('active');
        $('.member-list').css('opacity', 0)
        timer1 = setTimeout(function(){
          $('.member-list').removeClass('active');
        },510);
      }

      iBtn = !iBtn;

      var id = this.id;
      getGroupMemberInfo(id.replace('talk-title-', ''));
    });
}

//读取群组成员
var getGroupMemberInfo = function(group_id) {
    var options = {
        'GroupId': group_id,
        'Offset': 0, //必须从0开始
        'Limit': totalCount,
        'MemberInfoFilter': [
            'Account',
            'Role',
            'JoinTime',
            'LastSendMsgTime',
            'ShutUpUntil'
        ]
    };
    webim.getGroupMemberInfo(
        options,
        function(resp) {
            if (resp.MemberNum <= 0) {
                alert('该群组目前没有成员');
                return;
            }
            var data = [];
            for (var i in resp.MemberList) {
                var account = resp.MemberList[i].Member_Account;
                var role = webim.Tool.groupRoleEn2Ch(resp.MemberList[i].Role);
                var join_time = webim.Tool.formatTimeStamp(resp.MemberList[i].JoinTime);
                var shut_up_until = webim.Tool.formatTimeStamp(resp.MemberList[i].ShutUpUntil);
                if (shut_up_until == 0) {
                    shut_up_until = '-';
                }
                data.push(account);
            }

            // 获取群成员信息
            getMemberDetailInfo(data, group_id);
        },
        function(err) {
            alert(err.ErrorInfo);
        }
    );
};

// 读取成员详细信息
var getMemberDetailInfo = function(accountList, groupId) {
    var tag_list = [
            "Tag_Profile_IM_Nick",//昵称
            "Tag_Profile_IM_Gender",//性别
            "Tag_Profile_IM_AllowType",//加好友方式
            "Tag_Profile_IM_Image"//头像
        ];
    var options = {
        'To_Account': accountList,
        'TagList': tag_list
    };
    webim.getProfilePortrait(
        options,
        function (resp) {
            var data = [];
            if (resp.UserProfileItem && resp.UserProfileItem.length > 0) {
                for (var i in resp.UserProfileItem) {
                    var to_account = resp.UserProfileItem[i].To_Account;
                    var nick = null, gender = null, allowType = null,imageUrl=null;
                    for (var j in resp.UserProfileItem[i].ProfileItem) {
                        switch (resp.UserProfileItem[i].ProfileItem[j].Tag) {
                            case 'Tag_Profile_IM_Nick':
                                nick = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                            case 'Tag_Profile_IM_Gender':
                                switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                    case 'Gender_Type_Male':
                                        gender = '男';
                                        break;
                                    case 'Gender_Type_Female':
                                        gender = '女';
                                        break;
                                    case 'Gender_Type_Unknown':
                                        gender = '未知';
                                        break;
                                }
                                break;
                            case 'Tag_Profile_IM_AllowType':
                                switch (resp.UserProfileItem[i].ProfileItem[j].Value) {
                                    case 'AllowType_Type_AllowAny':
                                        allowType = '允许任何人';
                                        break;
                                    case 'AllowType_Type_NeedConfirm':
                                        allowType = '需要确认';
                                        break;
                                    case 'AllowType_Type_DenyAny':
                                        allowType = '拒绝任何人';
                                        break;
                                    default:
                                        allowType = '需要确认';
                                        break;
                                }
                                break;
                            case 'Tag_Profile_IM_Image':
                                imageUrl = resp.UserProfileItem[i].ProfileItem[j].Value;
                                break;
                        }
                    }
                    data.push({
                        'To_Account': to_account,
                        'Nick': webim.Tool.formatText2Html(nick),
                        'Gender': gender,
                        'AllowType': allowType,
                        'Image': imageUrl
                    });
                }
                tempGroupMember[groupId] = data;
                showGroupMember(data);
            }
        },
        function (err) {
            alert(err.ErrorInfo);
        }
    );
};

// 隐藏成员列表
function hideMemberList(){
    $('.group-member').removeClass('active');
    $('.member-list').css('opacity', 0)
    timer1 = setTimeout(function(){
      $('.member-list').removeClass('active');
    },510);
}

// 生成会话框
function addSession(session_type, to_id, name, face_url, unread_msg_count){
	var talks = document.getElementsByClassName("talk-list")[0];
	var session = document.createElement('li');
	session.id = "sessionLi_"+to_id;
	session.innerHTML =
        `<div class="people-name-block">` +
//          `<span>李</span>` +
//          `<span>张</span>` +
//          `<span>王</span>` +
//          `<span>赵</span>` +
            `<img src="/im/imgs/test-avatar.jpeg" alt="">` +
        `</div>` +
        `<div class="group-name">` +
          `<h6>`+name+`</h6>` +
          `<p>张：@文签老师 我的那啥整好了吗？</p>` +
        `</div>` +
        `<div class="tips">` +
          `<p>20:31</p>` +
          `<span class="active"></span>` +
        `</div>` ;
				
	talks.appendChild(session);
	
	// 点击会话框
	$('.talk-list li').on('click', function(){
		showTalkContent();
	});
}

// 添加消息
function addMessage(type, msgContent, fromAccount, fromAccountNick, headUrl, sendTime){
    sendTime = sendTimeFormat(sendTime);

	var msgDiv = document.getElementsByClassName("show-talk-con")[0];
	var msgs = document.getElementsByClassName("message-list")[0];
	var msg = document.createElement('li');
	if(headUrl == null || headUrl.search('http') == -1){
	    headUrl = '/im/imgs/default.png';
	}
	if(type == "OTHER"){
	    // 接收消息 - 左
		msg.classList = 'pub left-show';
		msg.innerHTML = 
            `<img src="${headUrl}" alt="">` +
            `<div class="name-con">` +
                `<span>`+fromAccountNick+`（${sendTime}）</span>` +
                `<p>`+msgContent+`</p>` +
            `</div>`;
	}
	
	if(type == "MYSELF"){			
		// 发送消息 - 右
		msg.classList = 'pub right-show';
		msg.innerHTML = 
            `<div class="name-con">` +
                `<span>（${sendTime}）`+fromAccountNick+`</span>` +
                `<p>`+msgContent+`</p>` +
            `</div>` +
            `<img src="${headUrl}" alt="">`;
	}	
	
	msgs.appendChild(msg);

	// 滚动条置底
	msgDiv.scrollTop = msgDiv.scrollHeight;

}

// 隐藏聊天窗口
function hideTalkContent(){
	var talkCon = document.getElementsByClassName("talk-content-list-wrap")[0];
	talkCon.classList = ["talk-content-list-wrap"];
}

// 清空聊天内容
function clearTalkContent(){
	var talkCon = document.getElementsByClassName("message-list")[0];
	talkCon.innerHTML = "";
}

function initTalkContent(){
	var talkCon = document.getElementsByClassName("message-list")[0];
	talkCon.innerHTML = `<li class="loadBtn">
                             <p onclick="loadHistoryRecord()">点击加载历史记录</p>
                           </li>`;
}

// 展示群成员
function showGroupMember(memberList){
	var memberListLabel = document.getElementById("group-member-list");
	// 清空
	memberListLabel.innerHTML = "";
	memberList.forEach(function(data){
	    var faceUrl = data.Image;
	    if(faceUrl.search('http') == -1){
	        faceUrl = '/im/imgs/default.png';
	    }
		var member = document.createElement("li");
		member.innerHTML =           
			`<img src="${faceUrl}" alt="">` +
			`<p>`+data.Nick+`</p>` ;
		memberListLabel.appendChild(member);
	});
	document.getElementById("member-count").innerHTML = "共"+memberList.length+"人";
}

// 历史消息展示
function showHistoryMsg(){
	var array = [1, 3, 5, 7];
	array.forEach(function(){
		addMsg('OTHER');
	});
}

// 搜索学生
$('#student-search').on('input', function(){
    var array = new Array();
    var value = $("#student-search").val();
    if(value != ''){
        studentListAll.forEach(function(index){
            if((index.name != null && index.name.search(value) > -1) || (index.student_no != null && index.student_no.search(value) > -1)){
                array.push(index);
            }
        })
        if(array.length > 0){
            showSearchResult(array);
        }else{
            showNoSearchResult(value);
        }
    }else{
        clearSearchResult();
        $("#no-student").removeClass("active");
    }
});

// 清空搜索内容
function clearSearchResult(){
    $("#student-list-dl").html("");
}

// 展示搜索结果
function showSearchResult(studentList){
	$("#no-student").removeClass("active");
	$("#student-list-con").addClass("active");
	// 渲染结果
	clearSearchResult();
	studentList.forEach(function(index){
		showOneSearchResult(index);
	});
}

// 展示无结果
function showNoSearchResult(tips){
    $("#no-student > p").html(`未找到'${tips}'`);
	$("#no-student").addClass("active");
	$("#student-list-con").removeClass("active");
}

// 展示一条搜索结果
function showOneSearchResult(index){
	var student = document.createElement("dd");
	var pre_id = "student-list-";
	var pre_name = "student-name-";
	var pre_send_btn = "send-msg-btn-";
	student.id = [pre_id + index.group_id];
	student.name = pre_name + index.name;
	// student.classList = ["active"];
	student.innerHTML = 
                `<div class="con">` +
                `<img src="/im/imgs/default.png" alt="">` +
                `<p>${index.name}（${index.student_no}）</p>` +
                `</div>` ;
	
	$("#student-list-dl").append(student);	
			
	// 点击搜索到的学生
	$('#student-list-dl dd').on('click', function(){
	    $(".send-message > h6").html(this.name.replace(pre_name, ""));
	    $(".send-message > button").attr('class', pre_send_btn + this.id.replace(pre_id, ""));
		$(".send-message").addClass('active');
		$("#no-select-tips").removeClass('active');
	});
}

// 点击发消息按钮
$('#send-msg-btn').on('click', function(){
    console.log(this.className);

    var sessList = new Array()
    $(".talk-list > li").each(function(){
        sessList.push(this.id.replace('sessionLi_', ''))
    })
    console.log(sessList);
    var clickToID = this.className.replace('send-msg-btn-', '');

    if(sessList.indexOf(clickToID) > -1){
        // 切换到对应的会话窗口
        onSelSess(webim.SESSION_TYPE.GROUP, clickToID);
    }else{
        getGroupInfo(clickToID, function(resp){
            addSess(webim.SESSION_TYPE.GROUP, clickToID, resp.GroupInfo[0].Name, resp.GroupInfo[0].FaceUrl, resp.GroupInfo[0].MemberNum);
            onSelSess(webim.SESSION_TYPE.GROUP, clickToID);
        });
    }

    // 切换到会话页 (这里存在一个问题是：切换对话时会更新消息的title， 切换到回话页时也会更新title，切换会话时会更新selSess对象，这时切换页面时取到的selSess才是正确的)
    $('.left-side ol li').eq(0).click();
});

// 点击图片按钮
$('.img-input').on('click', function(){
  $('.upload-img-wrap').css('display', 'block');
  setTimeout(function(){
    $('.upload-img-wrap').css('opacity', '1');
  },5)
});

$('.upload-img-wrap').find('.close').on('click', function(){
  $('.upload-img-wrap').css('opacity', '0');
  setTimeout(function(){
    $('.upload-img-wrap').css('display', 'none');
  },505)
});

// 图片预览功能
$('.upload-img').find('input').on('change', function(){
  var reads = new FileReader();
  var f = $(this).get(0).files[0]
  reads.readAsDataURL(f);
  reads.onload = function(e) {
    console.log(e)
    $('.upload-img').find('img').get(0).src = e.target.result;
  };
});

// 发送文件或图片
function upd_file_btn(){
$('.upload-img-wrap').find('.close').click();
    var uploadFiles = document.getElementById('upd_pic');
    var fileType = uploadFiles.files[0].type;
    if('image/jpeg' == fileType || 'image/png' == fileType){ // 图片
        uploadPic();
    }else{
        uploadFile();
    }
}

//格式化群组名称
function groupNameFormat(to_id, name){
    var group_name = '';
    studentListAll.forEach(function(data){
        if(to_id == data.group_id){
            if(data.name != null)
            group_name += `${data.name}-`;
            if(data.student_no != null && data.student_no != 0)
            group_name += `${data.student_no}-`;
            group_name += `沟通群`;
            return group_name;
        }
    })
    return group_name != '' ? group_name : name;
}

// 加载历史聊天记录
function loadHistoryRecord (){

    getPrePageGroupHistoryMsgs(function(msgList){
        var msgDiv = $(".loadBtn");

        if(msgList.length > 0){
            groupNextMsgSeq[selToID] = msgList[0].seq;
            groupNextMsgTime[selToID] = msgList[0].time;
            //如果是加载前几页的消息，消息体需要prepend，所以先倒排一下
            msgList.reverse();

            for (var j in msgList) { //遍历新消息
                msg = msgList[j];
                var time = sendTimeFormat(msg.time);
                var headUrl = msg.fromAccountHeadurl;
                var fromAccount = msg.fromAccountNick;
                if(headUrl == null || headUrl.search('http') == -1){
                    headUrl = '/im/imgs/default.png';
                }

                if (msg.getSession().id() == selToID && '@TIM#SYSTEM' != fromAccount) { //为当前聊天对象的消息
                    var msgLi = document.createElement('li');
    //                自己发送的
                    if(msg.fromAccount == loginInfo.identifier){
                        msgLi.classList = 'pub right-show';
                        msgLi.innerHTML =
                            `<div class="name-con">` +
                                `<span>（${time}）${msg.fromAccountNick}</span>` +
                                `<p>${msg.elems[0].content.text}</p>` +
                            `</div>` +
                            `<img src="${headUrl}" alt="">`;
                    }else{
                        msgLi.classList = 'pub left-show';
                        msgLi.innerHTML =
                            `<img src="${headUrl}" alt="">` +
                            `<div class="name-con">` +
                                `<span>${msg.fromAccountNick}（${time}）</span>` +
                                `<p>${msg.elems[0].content.text}</p>` +
                            `</div>`;
                    }
                    msgDiv.after(msgLi);
                }
            }
        }else{
            $.ajax({
                "url" : "/webim/group/history",
                "data" : {
                    groupId : selToID,
                    msgTimeStamp : groupNextMsgTime[selToID]
                },
                "type" : "post",
                "success" : function(data){
                    if(data.length > 0){
                        for (var j in data) { //遍历新消息
                            msg = data[j];
                            var time = sendTimeFormat(msg.msgTimeStamp);
                            var nick;
                            var headUrl;
                            var groupMembers = tempGroupMember[selToID];
                            var content = msg.content;
                            for(var i in groupMembers){
                                if(groupMembers[i].To_Account == msg.fromAccount){
                                    headUrl = groupMembers[i].Image;
                                    nick = groupMembers[i].Nick;
                                }
                            }
                            if(headUrl && nick){
                                if(headUrl == null || headUrl.search('http') == -1){
                                    headUrl = '/im/imgs/default.png';
                                }
                                // 图片
                                if(msg.messageType == 2){
                                    content = convertImageToHtml(content, content);
                                }

                                if (msg.groupId == selToID) { //为当前聊天对象的消息
                                    var msgLi = document.createElement('li');
                    //                自己发送的
                                    if(msg.fromAccount == loginInfo.identifier){
                                        msgLi.classList = 'pub right-show';
                                        msgLi.innerHTML =
                                            `<div class="name-con">` +
                                                `<span>（${time}）${nick}</span>` +
                                                `<p>${content}</p>` +
                                            `</div>` +
                                            `<img src="${headUrl}" alt="">`;
                                    }else{
                                        msgLi.classList = 'pub left-show';
                                        msgLi.innerHTML =
                                            `<img src="${headUrl}" alt="">` +
                                            `<div class="name-con">` +
                                                `<span>${nick}（${time}）</span>` +
                                                `<p>${content}</p>` +
                                            `</div>`;
                                    }
                                    msgDiv.after(msgLi);
                                    groupNextMsgTime[selToID] = msg.msgTimeStamp;
                                }
                            }
                        }
                    }else{
                        alert("无历史记录！")
                    }
                },
                "error" : function(err){
                }
            });
        }
    });

}

function sendTimeFormat(sendTime){
    sendTime = sendTime * 1000;
    var date = new Date(sendTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    var d = date.getDate();
    var time = date.toTimeString().substr(0, 8);

    var now = new Date();
    var y_ = now.getFullYear();
    var m_ = now.getMonth() + 1;
    var d_ = now.getDate();

    sendTime = `${y}-${m}-${d} ${time}`;
    nowDate = `${y_}-${m_}-${d_}`;

    return sendTime.replace(nowDate + ' ', '');
}

function convertImageToHtml(imageUrl, bigImageUrl){
    return `<img name="图片" src="${imageUrl}" style="CURSOR: hand" id="" bigimgurl="${bigImageUrl}" onclick="imageClick(this)">`;
}