<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <script src="js/script.js"></script>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
</head>
<body>
<div class="file">
    <h1>Pixelizator</h1>
    <br/>
        <input class="custom-file-upload" type="file" id="upload" onchange="uploadFile()" accept="image/png, image/bmp, image/jpeg, image/jpg"><br><br>
            <input class="range" type="range" id="range" value="1" min="1" max="100" onchange= "number.value = this.value" disabled><br><br>
            <input type="number" id="number" value="1" min="1" max="100" onchange= "this.value >=1 && this.value <= 100 ? range.value = this.value : null" disabled><br><br>
            <input class="testbutton" type="button" id="pixelate" onclick="pixelate()" value="pixelate" disabled> <br><br>
            <img class="preview" id="img" src="#" alt="Image preview"><br><br>
            <p id="name"></p>
            <p id="size"></p>


        <button class="testbutton" id="png"onclick="download('png')" disabled>png</button>
        <button class="testbutton" id="bmp" onclick="download('bmp')" disabled>bmp</button>
        <button class="testbutton" id="jpeg" onclick="download('jpeg')" disabled>jpeg</button>
        <button class="testbutton" id="jpg" onclick="download('jpg')" disabled>jpg</button>
    </div>
</body>
</html>