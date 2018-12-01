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
                $.i18n.properties({
                    name: 'appMessages',
                    path: 'resources/i18n/',
                    mode: 'both',
                    callback: function () {
                        $("#messageSuccess" + id).text($.i18n.prop(res.successMessage, cartItem.phoneId, cartItem.quantity));
                    }
                });
            }
            else {
                $.i18n.properties({
                    name: 'appMessages',
                    path: 'resources/i18n/',
                    mode: 'both',
                    callback: function () {
                        var message = "";
                        $.each(res.errorMessages, function (key, value) {
                            message += ('<p><span>' + $.i18n.prop(value) + '</span></p>');
                        });
                        $("#messageError" + id).html(message);
                    }
                });
            }
            $("#cartSize").text(res.cartSize);
        }
    })
}