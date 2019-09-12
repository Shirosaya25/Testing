$(document).keypress(function(e){
    if (e.which == 13){
        $("#login-button").click();
    }
});

const baseUrl = "http://localhost:8080/Project1/";

function onLoginClick(){

    username = document.getElementById("username-input").value;
    password = document.getElementById("password-input").value;
    
    if(!username){

        updateErrorMessage("Please enter a username in the respective field.")
    }

    else if(!password){

        updateErrorMessage("Please enter a password in the respective field.");
    }

    else{

        resetErrorMessage();

        sendCredentials(username, password, loginCallback);

    }
}

function sendCredentials(username, password, callback){

    let xhr = new XMLHttpRequest();
    xhr.open("GET", baseUrl + "/api/login");

    xhr.setRequestHeader("username", username);
    xhr.setRequestHeader("password", password);

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            callback(xhr.getResponseHeader("Login"), xhr.getResponseHeader("User"), xhr.getResponseHeader("Token"), xhr.getResponseHeader("URL"));
        }
    }

    xhr.send();
}

function updateErrorMessage(str){

    let ele = document.getElementById("login-error-message");

    ele.textContent = str
    ele.hidden = false;
}

function resetErrorMessage(){

    document.getElementById("login-error-message").hidden = true;
}

function loginCallback(login, user, token, url){

    if(login == "failed"){

        updateErrorMessage("Please enter a valid Username and Password combination.");
    }

    else{

        window.sessionStorage.setItem("user", user);
        window.sessionStorage.setItem("token", token)
        window.location.href = baseUrl + url;
    }
}