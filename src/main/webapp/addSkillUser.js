function addSkillUser() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        document.getElementById("demo").innerHTML =
                this.responseText;
    };
    var user = document.getElementById("username").value;
    var skill = document.getElementById("skill").value;
    xhttp.open("POST", "addSkillUser?user=" + user + "&skill=" + skill, true);
    xhttp.send();
}