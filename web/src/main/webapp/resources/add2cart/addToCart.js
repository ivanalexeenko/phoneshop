function addToCart(event, button) {
    var fullButtonId = button.id;
    var firstPartButtonId = "add";
    var replaceBy = "";
    var id = fullButtonId.replace(firstPartButtonId, replaceBy);
    event.preventDefault();
    var cartItem = {};
    cartItem["phoneId"] = id;
    cartItem["quantity"] = $("#input" + id).val();
    $.ajax({
        type: "POST",
        data: JSON.stringify(cartItem),
        contentType: "application/json",
        dataType: 'json',
        url: "ajaxCart",
        async: false,
        success: function (res) {
            $('span.error').empty();
            $('span.success').empty();
            if (res.isValidated) {
                $("#messageSuccess" + id).text(res.successMessage, cartItem.phoneId, cartItem.quantity);
            }
            else {
                var message = "";
                $.each(res.errorMessages, function (key, value) {
                    message += ('<p><span>' + value + '</span></p>');
                });
                $("#messageError" + id).html(message);
            }
            $("#cartSize").text(res.cartSize);
            $("#cartPrice").text(res.totalCartPrice);
        }
    })
}