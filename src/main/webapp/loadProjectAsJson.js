loadXMLDoc();
function loadXMLDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
            document.getElementById("demo").innerHTML =
                    this.responseText;
    };
    xhttp.open("GET", "project", true);
    xhttp.send();
}