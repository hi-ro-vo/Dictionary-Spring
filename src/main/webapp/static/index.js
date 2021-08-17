$(document).ready(function() {
    console.log($(this).parent().parent().attr("id"));
    $("#remove").click(
        function () {
            $.ajax({
                url:     "/remove/"+$(this).parent().parent().attr("id"),
                type:     "DELETE",
                dataType: "html",
                data: {},
                success: function(response) {
                    console.log('+');
                },
                error: function(response) {
                    console.log('-');
                }
            });
        }
    );
});