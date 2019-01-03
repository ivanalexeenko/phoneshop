function updateCart(event) {
    var phoneIdList = phoneIds;
    var updateForm = document.getElementById("updateForm");
    var hiddenQuantities = document.getElementsByName("hiddenQuantity");
    var hiddenPhoneIds = document.getElementsByName("hiddenPhoneId");
    var inputQuantities = document.getElementsByName("inputQuantity");
    for (var key in phoneIdList) {
        var value = inputQuantities[key].value;
        hiddenQuantities[key].value = value;
        hiddenPhoneIds[key].value = phoneIdList[key];
    }
    updateForm.submit();
}