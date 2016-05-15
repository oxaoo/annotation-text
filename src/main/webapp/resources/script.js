/* аннотирование */
function annotate() {
    console.log("Call annotated text");
    var text = $('#inputText').val();
    var met = $("input[name=annotationMethod]:checked").val();
    console.log("Text: " + text);
    console.log("Method: " + met);

    $.ajax({
        type: "POST",
        url: 'handler',
        data: {
            text: $('#inputText').val(),
            method: $("input[name=annotationMethod]:checked").val()
        },
        success: resultAnnotate
    });
};

/* результат аннотирования */
function resultAnnotate(response) {
    console.log("resposne: " + response);
    var map = JSON.parse(response);
    var annotation = map.annotation;
    //var status = map.status;

/*    switch (status) {
        case 'STATION_ERROR':
            toastr.error('Проверьте корректность указанных станций');
            return;
        case 'DATE_ERROR':
            toastr.error('Проверьте корректность указанных временных промежутков');
            return;
        case 'ERROR':
            toastr.error('Не удалось выполнить запрос');
            return;
    }*/

    $("#annotatedText").val(annotation);
}