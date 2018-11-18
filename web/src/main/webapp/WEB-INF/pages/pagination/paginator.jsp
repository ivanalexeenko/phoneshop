<html>
<nav aria-label="Page navigation" id="show_paginator">
    <form method="post" name="numberForm">
        <input type="hidden" name="currentPage" id="currentPage" value="${currentPage}"/>
        <ul class="pagination pagination-lg justify-content-end">
            <script>
                $('#show_paginator').bootpag({
                    total: ${totalPages},
                    page: ${currentPage},
                    maxVisible: ${visiblePages}
                }).on('page', function (event, num) {
                    document.getElementById("currentPage").setAttribute("value", num);
                    document.numberForm.submit();
                });
                $('#show_paginator li').addClass('page-item');
                $('#show_paginator a').addClass('page-link');
                $('#show_paginator ul').addClass('pagination-lg justify-content-end ');
            </script>
        </ul>
    </form>
</nav>
</html>