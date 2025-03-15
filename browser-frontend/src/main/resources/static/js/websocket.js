const url = "http://localhost:8080"; // Config.url;
// const url = "http://176.113.82.195";
let stompClient;
let selectedUserOrGroup = "";
let newMessages = new Map();
let responseMessages;

function httpGet(theUrl) {

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function getCurrentUserId() {

    return httpGet(url + "/auth/rest/api/v1/getCurrentUserId");
}

function fetchAll() {

    var userId = getCurrentUserId();

    $.get(url + "/auth/fetchAllUsers/"+userId, function (response) {

        let usersTemplateHTML = "";

        for (let i = 0; i < response.length; i++) {
            usersTemplateHTML = usersTemplateHTML +
            '<li class="active" id="child_message" onclick="formMessageLaunch('+response[i]['user_id']+',\''+response[i]['username']+'\',\'user\')" data-userid="'+response[i]['id']+'" data-type="user">'+
                '<div class="d-flex bd-highlight">'+
                    '<div class="img_cont">'+
                        '<img src="https://u-stena.ru/upload/iblock/ba7/ba785480d6c9e2b34f75eb7a562b0db8.jpg" class="rounded-circle user_img">'+
                        '<span class="online_icon"></span>'+
                    '</div>'+
                    '<div class="user_info" id="userNameAppender_' + response[i]['id'] + '">'+
                        '<span>'+response[i]['username']+'</span>'+
                        '<p>'+response[i]['username']+' is online</p>'+
                    '</div>'+
                '</div>'+
            '</li>';
        }

        $('#usersList').html(usersTemplateHTML);
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

            if (selectedUserOrGroup == data.fromLogin) {

                let messageTemplateHTML = "";
                messageTemplateHTML = messageTemplateHTML +
                '<div id="child_message" class="d-flex justify-content-end mb-4">'+
                    '<div id="child_message" class="msg_container_send">'+data.message+
                    '</div>'+
                '</div>';

                $('#formMessageBody').append(messageTemplateHTML);

            } else {

                newMessages.set(data.fromLogin, data.message);
                $('#userNameAppender_' + data.fromLogin).append('<span id="newMessage_' + data.fromLogin + '" style="color: red">+1</span>');
                let messageTemplateHTML = "";
                messageTemplateHTML = messageTemplateHTML +
                '<div id="child_message" class="d-flex justify-content-end mb-4">'+
                    '<div class="msg_container_send">'+data.message+
                    '</div>'+
                '</div>';

            }
        },{});
    },onError);
}

function onError() {
    console.log("Disconnect from console");
}


function sendMsgUser(from, text) {

    stompClient.send("/app/chat/" + selectedUserOrGroup, {}, JSON.stringify({
        fromLogin: from,
        message: text
    }));
}

function sendMessage(type) {

    let username = $('#userName').attr("data-id");
    let message = $('#message-to-send').val();
    var userId = getCurrentUserId();
    selectedUserOrGroup = username;

    if (message !== "") {

        if (type === "user") {
            sendMsgUser(userId, message.trim());
        }

        let messageTemplateHTML = "";
        messageTemplateHTML = messageTemplateHTML +
        '<div id="child_message" class="d-flex justify-content-end mb-4">' +
            '<div id="child_message" class="msg_container">'+message.trim() +
            '</div>' +
        '</div>';

        $('#formMessageBody').append(messageTemplateHTML);
        document.getElementById("message-to-send").value = "";

    } else {
        console.log("пустое сообщение отправить нельзя");
    }
    
}

function formMessageLaunch(id, name, type) {

    var userId = getCurrentUserId();
    let buttonSend = document.getElementById("buttonSend");
    
    if (buttonSend !== null) {
        buttonSend.parentNode.removeChild(buttonSend);
    }

    let userInfoHeader = $('#formMessageHeader .user_info').find('span');
    userInfoHeader.html(name);
    userInfoHeader.attr("data-id", id);
    let isNew = document.getElementById("newMessage_" + id) !== null;
    
    if (isNew) {
        let element = document.getElementById("newMessage_" + id);
        element.parentNode.removeChild(element);
    }

    let username = $('#userName').attr("data-id");
    selectedUserOrGroup = username;
    let isHistoryMessage = document.getElementById("formMessageBody");
    
    if (isHistoryMessage !== null && isHistoryMessage.hasChildNodes()) {
        isHistoryMessage.innerHTML = "";
    }

    if (type === "user") {

        $.get(url + "/auth/listmessage/"+userId+"/"+id, function (response) {
            
            let messages = response;
            let messageTemplateHTML = "";
            responseMessages  = response;

            for (let i = 0; i < messages.length; i++) {

                let strDate = "";

                if (messages[i]['message_from'] == id) {

                    strDate = new Date(Date.parse(responseMessages[i]['created_datetime'])).toLocaleString();
                    messageTemplateHTML = messageTemplateHTML +
                    '<div id="child_message" class="d-flex justify-content-start mb-4">'+
                        '<div id="child_message" class="msg_container">'+messages[i]['message_text']+
                            '<span class="msg_time">'+strDate+'</span>'+
                        '</div>'+
                    '</div>';

                } else {

                    strDate = new Date(Date.parse(responseMessages[i]['created_datetime'])).toLocaleString();
                    messageTemplateHTML = messageTemplateHTML +
                    '<div id="child_message" class="d-flex justify-content-end mb-4">'+
                        '<div id="child_message" class="msg_container_send">'+messages[i]['message_text']+
                            '<span class="msg_time">'+strDate+'</span>'+
                        '</div>'+
                    '</div>';
                }
            }
            $('#formMessageBody').append(messageTemplateHTML);
        });
    }

    let submitButton =  '<div class="input-group-append" id="buttonSend">'+
                            '<button class="input-group-text send_btn" onclick="sendMessage(\''+type+'\')"><i class="fas fa-location-arrow"></i></button>'+
                        '</div>';
    $('#formSubmit').append(submitButton)
}

function logout(userName) {

    stompClient.disconnect();
    localStorage.removeItem("userId");
    window.location.href = "index_main.html";
    return false;
}


window.onload = function() {

    if (getCurrentUserId() === null) {
        window.location.href = "index.html";
        return false;
    }

    fetchAll();
    connectToChat(getCurrentUserId());
};


// https://code.tutsplus.com/ru/tutorials/build-a-real-time-chat-application-with-modulus-and-spring-boot--cms-22513

//    stompClient.connect({"X-Authorization":"Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVQmppQkZXYmM4NnpBaER0M1QtTUJ6cnl3R3FnYkF5QlFxYjRjN0w3VHpNIn0.eyJleHAiOjE2MzE1ODc4NzksImlhdCI6MTYzMDM3ODI3OSwianRpIjoiODAyZGQyYzAtNjlhYi00Yjk2LTllZjgtODA5YWY3MWJmNmFmIiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay1kZXYuZ2l0c29sdXRpb25zLmlkL2F1dGgvcmVhbG1zL2dpdCIsImF1ZCI6WyJyZWFsbS1tYW5hZ2VtZW50IiwiYWNjb3VudCJdLCJzdWIiOiJhYTkzMzMxMi0wMjhkLTQ3MzQtYTlhNC1hMGYxNmNlZDY5ZTEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJnaXQtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjM5YTRhNzFhLTJmZWYtNDkwMS1hNjdlLTYwYTViNjI0YjllNyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InJlYWxtLW1hbmFnZW1lbnQiOnsicm9sZXMiOlsidmlldy1yZWFsbSIsInZpZXctaWRlbnRpdHktcHJvdmlkZXJzIiwibWFuYWdlLWlkZW50aXR5LXByb3ZpZGVycyIsImltcGVyc29uYXRpb24iLCJyZWFsbS1hZG1pbiIsImNyZWF0ZS1jbGllbnQiLCJtYW5hZ2UtdXNlcnMiLCJxdWVyeS1yZWFsbXMiLCJ2aWV3LWF1dGhvcml6YXRpb24iLCJxdWVyeS1jbGllbnRzIiwicXVlcnktdXNlcnMiLCJtYW5hZ2UtZXZlbnRzIiwibWFuYWdlLXJlYWxtIiwidmlldy1ldmVudHMiLCJ2aWV3LXVzZXJzIiwidmlldy1jbGllbnRzIiwibWFuYWdlLWF1dGhvcml6YXRpb24iLCJtYW5hZ2UtY2xpZW50cyIsInF1ZXJ5LWdyb3VwcyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsInZpZXctYXBwbGljYXRpb25zIiwidmlldy1jb25zZW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJkZWxldGUtYWNjb3VudCIsIm1hbmFnZS1jb25zZW50Iiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJnaXRzY29wZSIsInRlbmFudF9pZCI6WyI1ZDEyNWI2OS05YzcxLTRhYzktODVhNi1lMWQ4NGU3ZDFiNWIiXSwiZ3JvdXBfbmFtZSI6IklEU3RhciIsInVzZXJfbmFtZSI6ImZyZWR5LmZlcm5hbmRvIiwiaW5zdGFuY2VfdXJsIjpbImh0dHBzOi8vZGV2LmdpdHNvbHV0aW9ucy5pZCJdLCJpbnN0YW5jZV9hcGkiOlsiaHR0cHM6Ly9hcGktZGV2LmdpdHNvbHV0aW9ucy5pZCJdLCJlbXBfaWQiOiJlYmEyYjQ2Yi05MjljLTExZWItOTdjZS0wYWRiY2M5ZWFhYTgifQ.SAW95PiA3DZFwjOCeY3-aLzHPKkH9J_ucbZQ6rV9b8QjZ8zbzW_0F2yrYa7GpKjFNmZ7cL1mFm46wepnGwZvqUIb08EDN0wIqgf20XUsnck7Ji8av4HVEgAuLseiOwoHKHSjRGY8Rj-AeOQ3clbmYz_wy0RtlRResmr0_M59X-iYBtIaWxIDnfarqKvAWHz1Sus0y1abPvRyahLTjtAeKYNITmVhYQb66vWomttJiEDvKmCcNpQtJjW2WkJi7SiojxrsjFOo9R_PiPnYV3vMjsZMRfa8n3PXeG1g-cRst6nYZ0YYoarhAS_aLv-cCzEty5-rgEOMPGWPtyYGtKbJbg"}, function (frame) {
