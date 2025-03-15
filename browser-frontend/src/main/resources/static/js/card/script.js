var baseUrl = window.location.origin;
const swiper = document.querySelector('#swiper');
const like = document.querySelector('#like');
const dislike = document.querySelector('#dislike');
const bellNotify = document.querySelector('#bell-notify');
const countMatch = document.querySelector('#bell-count-match');
const cardElement = document.getElementsByClassName('card');

var cardGlobal = null;

function httpRequest(type, theUrl) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open(type, theUrl, false);
    xmlHttp.send(null);
    return xmlHttp.responseText;
}

function getNextUser() {
    return JSON.parse(httpRequest("GET", `/auth/rest/api/v1/user-next-1`));
}

function likeUser(idLikeUser) {
    
    let isMatchLike = httpRequest("GET", `/auth/rest/api/v1/user/like/${idLikeUser}`);
    console.log(isMatchLike);

    if (isMatchLike === "true") {

        console.log("взаимная симпатия: ДА");
        const cardHeader = document.getElementById("card-header");
        cardHeader.innerHTML += `<div id="match-like">
                                    <p class="match" style="
                                                            margin-top: 25vh;
                                                            font-size: 5vh;
                                                            font-weight: 500;
                                                            width: 100%;
                                    ">It's a</p>
                                    <p class="match">match!</p>
                                </div>`; 
        setTimeout(function(){ $('#match-like').remove(); }, 7000);
        
    } else {
        console.log("взаимная симпатия: НЕТ");
    }

    console.log('request send...');
}

function dislikeUser() {
    console.log('dislike');
}

function showCountMatch() {

    let responseCountMatch = httpRequest("GET", `/auth/rest/api/v1/user/countMatch`);
    console.log(`countMatch = ${countMatch}`);

    if (responseCountMatch === '0') {
        bellNotify.style.display = 'none';
    } else {
        bellNotify.style.display = 'display';
        countMatch.textContent = `${responseCountMatch}+`;
    }
}

function updateCountMatch() {
    $.ajax({
        url: '/auth/rest/api/v1/user/countMatch',     
        method: 'get',          
        dataType: 'html',       
        success: function(data) {

            let countMatchNum = Number(data);

            if (countMatchNum === 0) {
                bellNotify.style.display = 'none';
            } else {
                bellNotify.style.display = 'display';
                if (countMatchNum > 99) {
                    countMatch.textContent = `${countMatchNum}+`;
                    countMatch.style.cssText += "margin-left: 0px !important;";
                } else {
                    countMatch.textContent = `${countMatchNum}`;
                    if (countMatchNum < 10) {
                        countMatch.style.cssText += "margin-left: 1px !important;";
                    } else {
                        countMatch.style.cssText += "margin-left: 0px !important;";
                    }
                }   
            }        
        }
    });
}

function swipeRight() {

    console.log("right");
    cardGlobal.dismissSwipe(1);
}

function swipeLeft() {
    
    console.log("left");
    cardGlobal.dismissSwipe(-1);
}

axios.
    get('/auth/rest/api/v1/user-index').
    then( (result) => {

        updateCountMatch();

        if (result.status === 200) {
            
            var listUser = [];
            let obj = result.data;
            let id = obj['id'];
            let username = obj['username'];
            let fullName = obj['fullName'];
            let phone = obj['phone'];
            let description = obj['description'];
            let dob = obj['dob'];
            let pathImg = `${baseUrl}${obj['pathImg']}`;
            let user = new User(id, username, fullName, phone, description, dob,  pathImg);
            listUser.push(user);
            let loadUser = false;
            let cardCount = 0;

            function appendNewCard() {

                let idLikeUser = 0;

                if (loadUser == true) {
                    let userJson = getNextUser(); 
                    console.log(userJson);
                    idLikeUser = userJson['id'];
                    listUser[0] = new User( userJson['id'], userJson['username'], 
                                            userJson['fullName'], userJson['phone'], userJson['description'], 
                                            userJson['dob'], userJson['pathImg']);
                    console.log('load user...');
                } else {
                    idLikeUser = id;
                }

                let userCard = listUser[0]; 

                var card = new Card({

                    user: userCard,
                    onDismiss: appendNewCard,
                    onLike: () => {
                        like.style.animationPlayState = 'running';
                        like.classList.toggle('trigger');
                        likeUser(idLikeUser);
                        updateCountMatch();
                    },
                    onDislike: () => {
                        dislike.style.animationPlayState = 'running';
                        dislike.classList.toggle('trigger');
                        dislikeUser();
                        updateCountMatch();
                    }
                });

                cardGlobal = card;

                swiper.append(card.element);
                cardCount++;

                const cards = swiper.querySelectorAll('.card:not(.dismissing)');
                cards.forEach( (card, index) => {
                                card.style.setProperty('--i',index);
                });
            }
            
            appendNewCard();
            loadUser = true;

        } else {
            console.log("status is not 200");
        }
    }).
    catch( (error) => {
        console.log("error = " + error);
    }).
    finally( () => {
        console.log("finally");
    });