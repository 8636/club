
function goTop(t) {
    $(t).animate({
        opacity: 0
    }), $("body,html").animate({
        scrollTop: 0
    }, 1e3, function () {
        $(t).animate({
            opacity: 1
        })
    })
}
/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

/**
 * 提交评论
 * @param e
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

/**
 *
 * @param targetId
 * @param type
 * @param content
 */
function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=42e591d46be31af9c854&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}



/**
 * 展开二级评论
 */

//展开二级评论
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    if ($("#two_comment_" + id).attr("status") == "close") {
        //后台获取二级数据
        var url = "/comment/" + id;
        $.get(url, function (data) {
            //构建二级评论列表
            BuildComments(data.data, id);
        })
        $("#two_comment_" + id).addClass("in");
        $("#two_comment_" + id).attr("status", "open");
    } else {
        $("#two_comment_" + id).removeClass("in");
        $("#two_comment_" + id).attr("status", "close");
    }
}

//构建列表
function BuildComments(comments, id) {
    var wrapper = $("#comment2_wrapper_" + id);
    wrapper.empty();
    $.each(comments, function (index, item) {
        var dateOriginal = item.gmtCreate;
        console.info('1111'+dateOriginal);
        var date = moment(dateOriginal).format('YYYY-MM-DD HH:mm:ss')
        console.info(date);
        var comment = $('' +
            '<div class="media" style="margin-top: 5px;">\n' +
            '  <div class="media-left">\n' +
            '    <a href="#">\n' +
            '      <img width="40"  class="media-object img-rounded" src="' + item.user.avatarUrl + '" alt="...">\n' +
            '    </a>\n' +
            '  </div>\n' +
            '  <div class="media-body">\n' +
            '    <h4 class="media-heading" style="font-size: 12px;font-weight: 500">' + item.user.name + '</h4>\n' +
            '    ' + item.content + '\n' +
            '   <span class ="pull-right">' + date +'</span>\n'+
            '  </div>\n' +
            '</div><hr style="color: #303030">\n');
        comment.appendTo(wrapper);
    })
}
/**
 * 显示 问题标签
 */
function showSelectTag() {
    $("#select-tag").show();
}

/**
 * 追加 标签到输入框
 * @param e
 */
function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if (previous.indexOf(value) == -1) {
        if (previous) {
            $("#tag").val(previous + ',' + value);
        } else {
            $("#tag").val(value);
        }
    }
}