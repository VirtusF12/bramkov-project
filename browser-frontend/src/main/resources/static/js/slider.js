
$(document).ready(function() {

    $("#contact-search").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#contacts #contact-test").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });

	$('div#contact-test').click(function () {

        $('#wrapper').scrollTo($('div.content-contact'), 800);
		return false;
	});

    $('a.panel-back').click(function () {

        $('#wrapper').scrollTo($('div#mask'), 800);
		return false;
	});

});




/*
$(document).ready(function() {

	$('a.panel').click(function () {

		$('a.panel').removeClass('selected');
		$(this).addClass('selected');
		current = $(this);

		$('#wrapper').scrollTo($(this).attr('href'), 800);
        $('#wrapper').scrollTo($('div.content-contact'), 800);


		return false;
	});

	$(window).resize(function () {
		resizePanel();
	});

});

function resizePanel() {

	width = $(window).width();
	height = $(window).height();

	mask_width = width * $('.item').length;

	$('#debug').html(width  + ' ' + height + ' ' + mask_width);

	$('#wrapper, .item').css({width: width, height: height});
	$('#mask').css({width: mask_width, height: height});
	$('#wrapper').scrollTo($('a.selected').attr('href'), 0);

}
*/