$(document).ready(function () {
    $('button[id^="add"].addition').click(function (event) {
        addToCart(event,this);
    });
    $('button.ordering').click(function () {
        handleClick(this);
    });
});
