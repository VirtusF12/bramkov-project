const url = `${window.location.origin}`;

let stompClient;
let selectedUserOrGroup = "";
let newMessages = new Map();
let responseMessages;
let userIdTo = '';

function urlify(text) {

  var urlRegex = /(https?:\/\/[^\s]+)/g;

  return text.replace(urlRegex, function(url) {
    return '<a href="' + url + '">' + url + '</a>';
  })
  // return text.replace(urlRegex, '<a href="$1">$1</a>')
}

function httpGet(theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", theUrl, false); // false for synchronous request
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function getCurrentUserId() {

    return httpGet(url + "/auth/rest/api/v1/getCurrentUserId");
}

$("#message-btn-back").click(function() {

    $('#message-container').empty();
    $('#message-footer').hide();
    $('#message-header').hide();
    $([document.documentElement, document.body]).animate({
            scrollTop: 0
    }, 1000);
});

function sendMsgUser(from, text) {

    console.log("userIdTo = " + userIdTo);

    stompClient.send("/app/chat/" + userIdTo, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}

$("#btn-send-msg").click(function() {

    var userId = getCurrentUserId();
    let message = $('input#msg').val();
    let messageTemplateHTML = '';

    if (message != '') {

        message = urlify(message).trim();
        sendMsgUser(userId, message);
        messageTemplateHTML = messageTemplateHTML +
        '<div class="row-message">' +
            '<div class="row-content">' +
                '<div class="contact-message__rigth">' +
                    '<span class="message-span">'+message+'</span>' +
                '</div>' +
            '</div>' +
        '</div>';

        $('#message-container').append(messageTemplateHTML);
        $('input#msg').val("");
        $([document.documentElement, document.body]).animate({
                scrollTop: $("#message-footer__end").offset().top
        }, 100);
        console.log('Отправка сообщения...');

    } else {
        console.log('Ошибка: отправить сообщение не удалось, поскольку сообщение пустое');
    }

});

function getContacts() {

    var userId = getCurrentUserId();

    $.get(url + "/auth/fetchAllUsers/"+userId, function (response) {

        let usersTemplateHTML = "";

        for (let i = 0; i < response.length; i++) {

            let userLocalDateTime = response[i]["last_visit"];
            let visitUserStatus = "";
            let timeVisit = "";

            if (userLocalDateTime === null) {
                visitUserStatus = '<span class="contact-span__status--offline">offline</span>';
                timeVisit = "";
            } else {
                timeVisit = userLocalDateTime.split("T")[1].substring(0,5);
                let dateTimeLastVisit = new Date(Date.parse(userLocalDateTime));
                let dateTime = new Date();
                let diffMinutes = (dateTime.getTime() - dateTimeLastVisit.getTime(userLocalDateTime))/1000/60;
                console.log(`diffMinutes = ${diffMinutes}`);

                if (diffMinutes < 3) {
                    visitUserStatus = '<span class="contact-span__status--online">online</span>';
                } else {
                    visitUserStatus = '<span class="contact-span__status--offline">offline</span>';
                }
            }

            usersTemplateHTML = usersTemplateHTML +
            '<div id="contact-test" class="row" onclick="startChat('+response[i]['user_id']+',\''+response[i]['username']+'\',\'user\',\''+response[i]['path_img']+'\',\''+response[i]['username']+'\')">' +
                  '<div class="col-3" style="width: 20%;">' +
                    '<div class="contact-div__img--photo">' +
                      // '<img class="contact-img__photo" src="https://pbs.twimg.com/profile_images/1377989668189405192/II6ZfJPK_400x400.png" alt="">' +
                        '<img class="contact-img__photo" src="'+response[i]['path_img']+'" alt="">' +
                    '</div>' +
                  '</div>' +
                  '<div class="col-9 contact-div__row9--line">' +
                    '<div class="row" >' +
                      '<div class="col" style="text-align: left !important;  padding-left: 0px !important;">' +
                        '<span class="contact-span__username">'+response[i]['username']+'</span>' +
                      '</div>' +
                      '<div class="col" style = "text-align: right !important;">' +
                        '<span class="contact-span__time">'+timeVisit+'</span>' +
                      '</div>' +
                    '</div>' +
                    '<div class="row">' +
                      visitUserStatus +
                    '</div>' +
                  '</div>' +
            '</div>';
        }

        $('#contacts').html(usersTemplateHTML);
    });
}

function connectToChat(userId) {

    let socket = new SockJS(url + '/ws');
    stompClient = Stomp.over(socket);
    console.table(stompClient);
    console.log("%c stompClient","color: green; font: bold; padding: 50px;");

    stompClient.connect({}, function (frame) {

        stompClient.subscribe("/topic/messages/"+userId, function (response) {

            let data = JSON.parse(response.body);
            console.log("data = " + data);
            console.log("data.message = " + data.message);
            console.log("data.fromLogin = " + data.fromLogin);

            if (userIdTo == data.fromLogin) {

                let messageTemplateHTML = "";
                messageTemplateHTML = messageTemplateHTML +
                '<div class="row-message">' +
                    '<div class="row-content">' +
                        '<div class="contact-message__left">' +
                            '<span class="message-span">'+data.message+'</span>' +
                        '</div>' +
                    '</div>' +
                '</div>';

                $('#message-container').append(messageTemplateHTML);

            } else {

//                newMessages.set(data.fromLogin, data.message);
//                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
                let messageTemplateHTML = "";
                messageTemplateHTML = messageTemplateHTML +
                '<div class="row-message">' +
                    '<div class="row-content">' +
                        '<div class="contact-message__right">' +
                            '<span class="message-span">'+data.message+'</span>' +
                        '</div>' +
                    '</div>' +
                '</div>';
            }
        },{});
    },onError);
}

function onError() {

    console.log("Disconnect from console");
}

function startChat(id, name, type, path_img, usernameContact) {

    console.log("Start chat...");
    console.log("id = " + id);
    console.log("name = " + name);
    console.log("type = " + type);

    $('img.message-img__photo').attr("src", path_img);
    $('span#message-user-fullname').text(usernameContact);
    $('a#contact-info').attr("href", "/auth/user/profile/"+usernameContact);

    userIdTo = id;
    $('#message-container').empty();
    $('#wrapper').scrollTo($('div.content-contact'), 1000);
    $('#message-header').show();
    $('#message-footer').show();
    $([document.documentElement, document.body]).animate({
        scrollTop: $("#message-footer__end").offset().top
    }, 100);

    var authUserId = getCurrentUserId();
    console.log("authUserId = " + authUserId);

    if (type === "user") {

        let endpointListMessage = url + "/auth/listmessage/"+authUserId+"/"+id;
        console.log("endpointListMessage = " + endpointListMessage);

        $.get(endpointListMessage, function (response) {

            let messages = response;
            let messageTemplateHTML = "";
            responseMessages  = response;

            for (let i = 0; i < messages.length; i++) {
                let messageDate = "";
                if (messages[i]['message_from'] == id) {
                    messageDate = new Date(Date.parse(responseMessages[i]['created_datetime'])).toLocaleString();
                    messageTemplateHTML = messageTemplateHTML +
                    '<div class="row-message">' +
                        '<div class="row-content">' +
                            '<div class="contact-message__left">' +
                                '<span class="message-span">'+messages[i]['message_text']+'</span>' +
                            '</div>' +
                        '</div>' +
                    '</div>';
                } else {
                    messageDate = new Date(Date.parse(responseMessages[i]['created_datetime'])).toLocaleString();
                    messageTemplateHTML = messageTemplateHTML +
                    '<div class="row-message">' +
                        '<div class="row-content">' +
                            '<div class="contact-message__rigth">' +
                                '<span class="message-span">'+messages[i]['message_text']+'</span>' +
                            '</div>' +
                        '</div>' +
                    '</div>';
                }
                $('#message-container').append(messageTemplateHTML);
            }
        });
    }
}

function logout(userName) {

    stompClient.disconnect();
    localStorage.removeItem("userId");
    window.location.href = "index_main.html";
    return false;
}

$(document).ready(function() {

    if (getCurrentUserId() === null) {
        window.location.href = "index.html";
        return false;
    }

    $('#message-container').empty();
    $('#message-footer').hide();
    $('#message-header').hide();

    getContacts();
    connectToChat(getCurrentUserId());
});