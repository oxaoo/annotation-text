<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Алгоритмы аннотирования текста</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.2/toastr.min.js"></script>


    <link rel="stylesheet" type="text/css" href="resources/style.css">
    <script src="resources/script.js"></script>
</head>
<body>
<div class="container chat-wrapper">
    <form>
        <h2 align="center" class="alert alert-success">Алгоритмы аннотирования текста</h2>

        <p>Выберите метод аннотирования</p>
        <label class="radio-inline">
            <input type="radio" name="annotationMethod" id="trm" value="trm" checked>
            TRM & TLTF
        </label>

        <label class="radio-inline">
            <input type="radio" name="annotationMethod" id="srl" value="srl">
            SRL
        </label>

        <label class="radio-inline">
            <input type="radio" name="annotationMethod" id="txl" value="txl">
            TXL
        </label>

        <label class="radio-inline">
            <input type="radio" name="annotationMethod" id="space" value="space">
            Space
        </label>

        <button class="btn btn-success btn-primary btn-lg" type="button" onclick="annotate()"> Аннотировать
            <span class="glyphicon glyphicon-tags"></span>
        </button>

        <fieldset class="form-group">
            <label for="inputText">Введите текст для аннотирования:</label>
            <textarea class="form-control" id="inputText" rows="10"></textarea>
        </fieldset>
    </form>


    <div id="annotateResult" class="row"></div>
    <%--<div>
        <fieldset class="form-group">
            <label for="annotatedText">Результат аннотирования:</label>
            <textarea class="form-control" id="annotatedText" rows="10"></textarea>
        </fieldset>
    </div>--%>
</div>
</body>
</html>