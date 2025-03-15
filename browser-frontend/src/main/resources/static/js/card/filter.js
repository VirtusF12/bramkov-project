
var rangeOne = document.querySelector('input[name="rangeOne"]');
var rangeTwo = document.querySelector('input[name="rangeTwo"]');
var outputOne = document.querySelector('.outputOne');
var outputTwo = document.querySelector('.outputTwo');
var inclRange = document.querySelector('.incl-range');
/* кнопки формы фильтра */
var formFilter = document.getElementById('filter');
var btnFormFilter = document.getElementById('btn-filter');
var btnFormFilterExit = document.getElementById('filter-img-exit');
var btnFilter = document.getElementById('img-card-filter');

updateView = function () {

    if (this.getAttribute('name') === 'rangeOne') {
outputOne.innerHTML = this.value;
        outputOne.style.left = this.value / this.getAttribute('max') * 100 + '%';
    } else {
        outputTwo.style.left = this.value / this.getAttribute('max') * 100 + '%';
        outputTwo.innerHTML = this.value
    }

    if (parseInt(rangeOne.value) > parseInt(rangeTwo.value)) {
        inclRange.style.width = (rangeOne.value - rangeTwo.value) / this.getAttribute('max') * 100 + '%';
        inclRange.style.left = rangeTwo.value / this.getAttribute('max') * 100 + '%';
    } else {
        inclRange.style.width = (rangeTwo.value - rangeOne.value) / this.getAttribute('max') * 100 + '%';
        inclRange.style.left = rangeOne.value / this.getAttribute('max') * 100 + '%';
    }
};

document.addEventListener('DOMContentLoaded', function () {

formFilter.hidden = true;

updateView.call(rangeOne);
updateView.call(rangeTwo);
$('input[type="range"]').on('mouseup', function() {
this.blur();
}).on('mousedown input', function () {
updateView.call(this);
});

btnFormFilter.addEventListener('click', function (e) {
formFilter.hidden = true;
}); 

btnFilter.addEventListener('click', function (e) {
formFilter.hidden = false;
});

btnFormFilterExit.addEventListener('click', function() {
formFilter.hidden = true;
});

});