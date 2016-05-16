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
    //var annotation = map.annotation;
    var method = map.method;

    switch (method) {
        case 'trm':
            makeTrmModel(map);
            //toastr.error('Проверьте корректность указанных станций');
            return;
        case 'srl':
            makeSrlModel(map);
            //toastr.error('Проверьте корректность указанных временных промежутков');
            return;
        default:
            toastr.error('Не произвести аннотирование текста');
            return;
    }

    //$("#annotatedText").val(annotation);
}

function makeTrmModel(map) {
    var stringList = map.strings;

    var trmTab = '<table width="100%" class="table table-hover"><tr><th>№</th><th>Приоритет</th></tr>';
    for (var i = 0; i < stringList.length; i++) {
        trmTab += '<tr><td>' + (i + 1) + '</td><td>' + stringList[i] + '</td></tr>';
    }
    trmTab += '</table>';

    $('#annotateResult').empty();
    $('#annotateResult').append(trmTab);
}

function makeSrlModel(map) {
    var wordList = map.words;
    var predicateList = map.predicates;

    var srlWordTab = '<p>Слова</p><table width="100%" class="table table-hover"><tr><th>№</th><th>Форма</th><th>Лемма</th><th>Роль</th></tr>';
    for (var i = 0; i < wordList.length; i++) {
        srlWordTab += '<tr><td>' + (i + 1) + '</td><td>' + wordList[i] + '</td><td>' + wordList[i] + '</td><td>' + wordList[i] + '</td></tr>';
    }
    srlWordTab += '</table>';

    $('#annotateResult').empty();
    $('#annotateResult').append(srlWordTab);



    var srlPredicateTab = '<br><p>Предикаты</p><table width="100%" class="table table-hover"><tr><th>№</th><th>Форма</th><th>Смысл</th><th>Аргумент</th></tr>';
    for (var i = 0; i < predicateList.length; i++) {
        srlPredicateTab += '<tr><td>' + (i + 1) + '</td><td>' + srlPredicateTab[i] + '</td><td>' + srlPredicateTab[i] + '</td><td>' + srlPredicateTab[i] + '</td></tr>';
    }
    srlPredicateTab += '</table>';

    $('#annotateResult').append(srlWordTab);
}