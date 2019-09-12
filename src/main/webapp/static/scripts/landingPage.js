$('.collapse').collapse()

$('#passwordChangeModal').on('hidden.bs.modal', function () {

    document.getElementById("landing-new-password-success").hidden = true;
    document.getElementById("landing-new-password-error-message").hidden = true;

    document.getElementById("landing-new-password-current").value = "";
    document.getElementById("landing-new-password-new").value = "";
    document.getElementById("landing-new-password-confirm").value = "";
});

$('#passwordChangeModal').keypress(function(e){
    if (e.which == 13){
        $("#password-change-button").click();
    }
});

$('#request-info-modal').keypress(function(e){
    if (e.which == 13){
        $("#request-update-button").click();
    }
});

$('#request-new-modal').keypress(function(e){
    if (e.which == 13){
        $("#request-new-button").click();
    }
});

const url = "http://localhost:8080/Project1";
let loggedInUser;
const authEnum = ["Employee", "Manager", "General Manager"];
const statEnum = ["Unopened", "Pending", "Resolved"];

build(window.sessionStorage.getItem("user"));

function logout(){

    sessionStorage.clear();
    window.location.href = url;
}

function getUserRequests(callback){

    let xhr = new XMLHttpRequest();
    xhr.open("GET", url + "/api/landing/requests");

    xhr.setRequestHeader("token", window.sessionStorage.getItem("token"));
    xhr.setRequestHeader("User", window.sessionStorage.getItem("user"));

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            let list = xhr.response;
            window.sessionStorage.setItem("requests", list)
            callback(list);
        }
    }

    xhr.send();
}

function build(user){

    loggedInUser = JSON.parse(user.trim());

    getUserRequests(applyRequestTable);

    applyHeaders();
    applyAccountInformation();

    if(loggedInUser.authority > 0){

        document.getElementById("landing-management").hidden = false;
    }
}

function applyHeaders(){

    document.getElementById("landing-welcome-user").textContent = "Welcome: " + loggedInUser.firstname + " " + loggedInUser.lastname;
    document.getElementById("landing-account-user").textContent = "View and Change Details For " + loggedInUser.firstname + " " + loggedInUser.lastname;
    document.getElementById("landing-requests-user").textContent = "View Requests Made by " + loggedInUser.firstname + " " + loggedInUser.lastname;
}

function applyAccountInformation(){

    document.getElementById("account-username").textContent = loggedInUser.username;
    document.getElementById("account-password").textContent = loggedInUser.password;
    document.getElementById("account-firstname").textContent = loggedInUser.firstname;
    document.getElementById("account-lastname").textContent = loggedInUser.lastname;
    document.getElementById("account-email").textContent = loggedInUser.email;
    document.getElementById("account-authority").textContent = authEnum[loggedInUser.authority];
}

function applyRequestTable(list){

    let obj = JSON.parse(list);

    let table = document.getElementById("request-table-body");
    let row;
    let atag;
    let ptag;

    let temp

    for(let e of obj){

        row = table.insertRow();

        row.insertCell(0).appendChild(document.createTextNode(e.requestId));
        row.insertCell(1).appendChild(document.createTextNode(e.applicant));
        row.insertCell(2).appendChild(document.createTextNode(statEnum[e.status]));
        row.insertCell(3).appendChild(document.createTextNode(authEnum[e.ticketLevel]));
        row.insertCell(4).appendChild(document.createTextNode("$" + e.amount));
        row.insertCell(5).appendChild(document.createTextNode(e.submissionDate));

        row.insertCell(6).appendChild(document.createTextNode(e.resolutionDate || ""));
        row.insertCell(7).appendChild(document.createTextNode(e.resolvedBy || ""));
        
        atag = document.createElement('a');
        atag.setAttribute('href', "#");
        atag.setAttribute('class', 'landing-anchor-glyph');
        atag.setAttribute('data-toggle', 'modal');
        atag.setAttribute('data-target', '#request-info-modal');
        atag.setAttribute('onclick', 'onRequestModalExpand(this)');

        row.insertCell(8).appendChild(atag);
    }

    $('.landing-anchor-glyph').prepend("<span class = 'fas fa-caret-square-down'></span>");

}

function applyRequestTableWithParam(list, status){

    let obj = JSON.parse(list);

    let table = document.getElementById("request-table-body");

    let row;

    for(let e of obj){

        if(e.status != status){

            continue;
        }

        row = table.insertRow();

        row.insertCell(0).appendChild(document.createTextNode(e.requestId));
        row.insertCell(1).appendChild(document.createTextNode(e.applicant));
        row.insertCell(2).appendChild(document.createTextNode(statEnum[e.status]));
        row.insertCell(3).appendChild(document.createTextNode(authEnum[e.ticketLevel]));
        row.insertCell(4).appendChild(document.createTextNode("$" + e.amount));
        row.insertCell(5).appendChild(document.createTextNode(e.submissionDate));
        row.insertCell(6).appendChild(document.createTextNode(e.resolutionDate || ""));
        row.insertCell(7).appendChild(document.createTextNode(e.resolvedBy || ""));

        atag = document.createElement('a');
        atag.setAttribute('href', "#2");
        atag.setAttribute('class', 'landing-anchor-glyph');
        atag.setAttribute('data-toggle', 'modal');
        atag.setAttribute('data-target', '#request-info-modal');
        atag.setAttribute('onclick', 'onRequestModalExpand(this)');

        row.insertCell(8).appendChild(atag);
    }

    $('.landing-anchor-glyph').prepend("<span class = 'fas fa-caret-square-down'></span>");

}


function onNavbarEntryClick(e){

    let element = document.getElementsByClassName("active")[0];
    element.classList.remove("active");

    e.classList.add("active");
}

function onNavbarEntryClickGetMyAccount(){

    let element = document.getElementById("landing-side-nav").getElementsByClassName("active")[0];
    element.classList.remove("active");

    document.getElementById("navbar-my-account").classList.add("active");
}

function submitNewPasswordRequest(curPassword, newPassword, callback, errorCallBack){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/changePassword");

    xhr.setRequestHeader("Username", loggedInUser.username);
    xhr.setRequestHeader("Password", curPassword);
    xhr.setRequestHeader("NewPassword", newPassword);

    xhr.setRequestHeader("token", window.sessionStorage.getItem("token"));

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            callback(xhr.getResponseHeader("NewPassword"));
        }

        else if(xhr.readyState === 4 && xhr.status === 403){

            errorCallBack(403);
        }
    }

    xhr.send();
}


function onPasswordChangeClick(){

    let curPassword = document.getElementById("landing-new-password-current").value;
    let newPassword = document.getElementById("landing-new-password-new").value;
    let conPassword = document.getElementById("landing-new-password-confirm").value;

    if(!(curPassword || newPassword || conPassword)){

        updateNewPasswordError("Please fill in all fields.");
    }   

    else if(newPassword != conPassword){

        updateNewPasswordError("New desired passwords must match.");
    }

    else{

        submitNewPasswordRequest(curPassword, newPassword, passwordChangeCallback, passwordChangeErrorCallback);
    }

}

function passwordChangeCallback(newPassword){

    document.getElementById("account-password").textContent = newPassword;
    document.getElementById("landing-new-password-error-message").hidden = true;
    document.getElementById("landing-new-password-success").hidden = false;
}

function passwordChangeErrorCallback(status){

    if(status == 403){

        updateNewPasswordError("Current password does not match.");
    }
}




function updateNewPasswordError(str){

    document.getElementById("landing-new-password-error-message").textContent = str;
    document.getElementById("landing-new-password-error-message").hidden = false;
    document.getElementById("landing-new-password-success").hidden = true;
}

function hideAll(){

    let elements = document.getElementsByClassName("landing-div");

    for(let ele of elements){

        ele.hidden = true;
    }
}

function loadHome(){

    hideAll();
    document.getElementById("landing-home-div").hidden = false;
}

function loadMyAccount(){

    hideAll();
    document.getElementById("landing-my-account-div").hidden = false;
}

function loadMyRequests(){

    hideAll();
    document.getElementById("landing-my-requests-div").hidden = false;
}

function loadManagementEmployees(){

    hideAll();
    document.getElementById("landing-management-employees").hidden = false;
}

function onFilterClick(e){

    document.getElementById("landing-filter-nav").getElementsByClassName("active")[0].classList.remove("active");
    e.classList.add("active");
}

function filterAllRequests(e){

    if(e == document.getElementById("landing-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    destroyTable();

    applyRequestTable(window.sessionStorage.getItem("requests"));
}

function filterUnopenedRequests(e){

    if(e == document.getElementById("landing-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    destroyTable();

    applyRequestTableWithParam(window.sessionStorage.getItem("requests"), 0);
}

function filterPendingRequests(e){

    if(e == document.getElementById("landing-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    destroyTable();

    applyRequestTableWithParam(window.sessionStorage.getItem("requests"), 1);
}

function filterResolvedRequests(e){

    if(e == document.getElementById("landing-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    destroyTable();

    applyRequestTableWithParam(window.sessionStorage.getItem("requests"), 2);
}

function destroyTable(){

    const table = document.getElementById("request-table-body");

    while(table.firstChild){

        table.removeChild(table.firstChild);
    }
}

function onRequestModalExpand(e){

    let row = e.parentNode.parentNode.childNodes;
    let request = searchCurrentRequests(row[0].textContent);

    document.getElementById("request-modal-id").textContent = row[0].textContent;
    document.getElementById("request-modal-applicant").textContent = row[1].textContent;
    document.getElementById("request-modal-status").textContent = row[2].textContent;
    document.getElementById("request-modal-authority").textContent = row[3].textContent;
    document.getElementById("request-modal-amount").textContent = row[4].textContent;
    document.getElementById("request-modal-submission-date").textContent = row[5].textContent;
    document.getElementById("request-modal-resolution-date").textContent = row[6].textContent;
    document.getElementById("request-modal-resolved-by").textContent = row[7].textContent;

    document.getElementById("request-modal-description").value = request.description || "";
    document.getElementById("request-modal-reference").value = request.reference || "";

}

function searchCurrentRequests(id){

    requests = JSON.parse(window.sessionStorage.getItem("requests"));

    for(let r of requests){

        if(r.requestId == id){

            return r;
        }
    }
}

function reassignSessionRequests(request){

    console.log(request)

    requests = JSON.parse(window.sessionStorage.getItem("requests"));

    for(let r of requests){

        if(r.requestId == request.requestId){

            r.description = request.description;
            r.reference = request.reference;
        }
    }

    console.log(requests);
    window.sessionStorage.setItem("requests", JSON.stringify(requests));
}

function onUpdateRequestClick(){

    hideUpdateRequestMessages();

    let description = document.getElementById("request-modal-description").value;
    let reference = document.getElementById("request-modal-reference").value;

    let request = searchCurrentRequests(document.getElementById("request-modal-id").textContent);

    request.description = description;
    request.reference = reference;

    postRequestUpdate(JSON.stringify(request), postRequestSuccess, postRequestError);
}

function postRequestUpdate(request, callback, errorCallback){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/updateRequest");

    xhr.setRequestHeader("request", request);
    xhr.setRequestHeader("token", window.sessionStorage.getItem("token"));

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            callback(JSON.parse(request));
        }

        else if(xhr.readyState === 4){

            errorCallback();
        }
    }

    xhr.send();
}

function postRequestSuccess(request){

    document.getElementById("landing-update-request-success").hidden = false;
    reassignSessionRequests(request);
}

function postRequestError(){

    document.getElementById("landing-update-request-error-message").hidden = false;
}

function hideUpdateRequestMessages(){

    document.getElementById("landing-update-request-error-message").hidden = true;
    document.getElementById("landing-update-request-success").hidden = true;
}

function onNavbarEntryClickGetMyRequests(){

    let element = document.getElementById("landing-side-nav").getElementsByClassName("active")[0];
    element.classList.remove("active");

    document.getElementById("navbar-my-requests").classList.add("active");

    document.getElementById("landing-request-new-amount").value = "";
    document.getElementById("request-new-description").value = "";
    document.getElementById("request-new-reference").value = "";

    document.getElementById("request-new-button").setAttribute("onclick", "onNewRequestClick();")
    document.getElementById("request-new-button").disabled = false;

    hideNewRequestMessages();
}

function onNewRequestClick(){

    hideNewRequestMessages();

    let amount = document.getElementById("landing-request-new-amount").value;
    let description = document.getElementById("request-new-description").value;
    let reference = document.getElementById("request-new-reference").value;

    if(!amount){

        raiseNewRequestError("Please enter a valid amount.");
    }

    let user = sessionStorage.getItem("user");

    postNewRequest(user, amount, description, reference, raiseNewRequestSuccess, raiseNewRequestError)

}

function postNewRequest(user, amount, description, reference, callback, errorCallback){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/newRequest");

    xhr.setRequestHeader("user", user);
    xhr.setRequestHeader("amount", amount);
    xhr.setRequestHeader("description", description);
    xhr.setRequestHeader("reference", reference);

    xhr.onreadystatechange = function() {

        if(xhr.readyState === 4 && xhr.status === 200){

            callback();
        }

        else if(xhr.readyState === 4){

            errorCallback("There was an error adding the request.");
        }
    }

    xhr.send();
}

function hideNewRequestMessages(){

    document.getElementById("landing-request-new-success").hidden = true;
    document.getElementById("landing-request-new-error-message").hidden = true;
}

function raiseNewRequestSuccess(){

    document.getElementById("landing-request-new-success").hidden = false;

    document.getElementById("request-new-button").setAttribute("onclick", "");
    document.getElementById("request-new-button").disabled = true;

    destroyTable();
    getUserRequests(applyRequestTable);

    onFilterClick(document.getElementById("filter-all"));
}

function raiseNewRequestError(str){

    document.getElementById("landing-request-new-error-message").textContent = str;
    document.getElementById("landing-request-new-error-message").hidden = false;
}