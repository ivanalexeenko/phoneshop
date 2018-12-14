$('#show_paginator').bootpag({
    total: totalPages,
    page: currentPage,
    maxVisible: visiblePages
}).on('page', function (event, num) {
    document.getElementById("currentPage").setAttribute("value", num);
    document.numberForm.submit();
});
$('#show_paginator li').addClass('page-item');
$('#show_paginator a').addClass('page-link');
$('#show_paginator ul').addClass('pagination-lg justify-content-end ');
