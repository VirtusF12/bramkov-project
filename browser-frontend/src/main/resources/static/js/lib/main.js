const url = Config.url;

$(document).ready(function() {
    $("#btnNext").click(function(event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit_next();
    });
    $("#btnPrev").click(function(event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        fire_ajax_submit_prev();
    });
});

function fire_ajax_submit_next() {
    // $('#cardContent').empty();
    $.get(url + "/randUser", function (response) {
        let randUser = response;
        let titleText = randUser.firstName + ", " + randUser.age + ", " + randUser.city
        let cardTemplateHTML =
               '<div class="card mb-3">' +
               '<img src="https://n1s1.hsmedia.ru/8d/ca/54/8dca542afa5a78f3608291dec6c7748c/690x381_0x0a330c2a_14056847571628246523.jpeg" class="card-img-top" alt="...">' +
                    '<div class="card-body">' +
                        '<h5 class="card-title">'+ titleText +'</h5>' +
                        '<p class="card-text">'+ randUser.description +'</p>' +
                        '<p class="card-text"><small class="text-muted">Last updated 3 mins ago</small></p>' +
                    '</div>' +
               '</div>';
        console.log("cardTemplateHTML = " + cardTemplateHTML);
        $('#cardContent').append(cardTemplateHTML);
    });
}

function fire_ajax_submit_prev() {
    console.log("PointG - prev");
}