function addToCart(event, button) {
    var fullButtonId = button.id;
    var firstPartButtonId = "add";
    var replaceBy = "";
    var id = fullButtonId.replace(firstPartButtonId, replaceBy);
    event.preventDefault();
    var cartItem = {};
    cartItem["phoneId"] = id;
    cartItem["quantity"] = $("#input" + id).val();
    $("#input" + id).find("p").find("span").text("");
    $.ajax({
        async: false,
        type: "POST",
        data: JSON.stringify(cartItem),
        contentType: "application/json",
        dataType: 'json',
        url: "ajaxCart",
        success: function (res) {
            if (res.isValidated) {
                $("#input" + id).after('<p><span class="success">' + res.successMessage + '</span></p>');
            }
            else {
                $.each(res.errorMessages, function (key, value) {
                    $("#input" + id).after('<p><span class="error">' + value + '</span></p>');
                });
            }
            $("#cartSize").text(res.cartSize);
        }
    })
}



