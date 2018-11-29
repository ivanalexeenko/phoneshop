window.onload = function (window) {
    var elements = document.getElementsByClassName("rotate");
    var numberArray = data;
    var number = 0;
    Array.from(elements).forEach(function (entry) {
        if (numberArray[number] === 0) {
            entry.classList.remove("asc", "desc", "neutral");
        }
        else if (numberArray[number] === 1) {
            entry.classList.remove("asc", "desc", "neutral");
            entry.classList.add("asc");
        }
        else {
            entry.classList.remove("asc", "desc", "neutral");
            entry.classList.add("desc");
        }
        number++;
    });

};

function handleClick(button) {
    var buttons = document.getElementsByClassName("ordering");
    var number = 0;
    var array = data;
    for (var key in buttons) {
        if (buttons[key].id === button.id) {
            number = key;
        }
    }
    var ascend;
    var buttonValue = array[number];

    for (var key1 in array) {
        array[key1] = 0;
    }

    if (buttonValue === 0) {
        buttonValue++;
        ascend = "true";
    }
    else if (buttonValue === 1) {
        buttonValue++;
        ascend = "false";
    }
    else {
        buttonValue = 0;
        ascend = "true";
    }
    array[number] = buttonValue;
    document.getElementById("orderName").setAttribute("value", button.id);
    document.getElementById("orderAscend").setAttribute("value", ascend);
    document.getElementById("orderData").value = array.join(",");
    document.orderByForm.submit();
}






