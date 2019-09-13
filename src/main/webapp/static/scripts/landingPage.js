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

$(document).ready(function(){
  $("#management-filter-username").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#management-requests-table-body tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});

$('#management-filter-username').keypress(function(e){
    if (e.which == 13){
        e.preventDefault();
    }
});

const url = "http://localhost:8080/Project1";
let loggedInUser;
const authEnum = ["Employee", "Manager", "General Manager"];
const statEnum = ["Unopened", "Pending", "Resolved"];
const approvalEnum = ["REJECTED", "APPROVED"];

const authEnumReverse = {"Employee" : 0, "Manager" : 1, "General Manager" : 2};

build(window.sessionStorage.getItem("user"));

function getUserRequests(callback){

    let xhr = new XMLHttpRequest();
    xhr.open("GET", url + "/api/landing/request/employee");

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

function submitNewPasswordRequest(curPassword, newPassword, callback, errorCallBack){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/employee/setPassword");

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

function logout(){

    sessionStorage.clear();
    window.location.href = url;
}

function build(user){

    loggedInUser = JSON.parse(user.trim());

    getUserRequests(applyRequestTable);

    applyHeaders();
    applyAccountInformation();

    if(loggedInUser.authority > 0){

        document.getElementById("landing-management").hidden = false;

        getManagementRequests(applyManagementRequestsTable);
        getManagementEmployees(applyEmployeeTable);
        fillNewEmployeeAuthority(loggedInUser.authority);
    }
}

function fillNewEmployeeAuthority(auth){

    let list = document.getElementById("new-employee-modal-authority");
    let node;

    for(let i = 0 ; i < auth ; i++){

        node = document.createElement("option");
        node.textContent = authEnum[i];
        list.appendChild(node);
    }
}

function applyHeaders(){

    document.getElementById("landing-welcome-user").textContent = "Welcome: " + loggedInUser.firstname + " " + loggedInUser.lastname;
    document.getElementById("landing-account-user").textContent = "View and Change Details For " + loggedInUser.firstname + " " + loggedInUser.lastname;
    document.getElementById("landing-requests-user").textContent = "View Requests Made by " + loggedInUser.firstname + " " + loggedInUser.lastname;
    document.getElementById("management-employee-directory-subheader").textContent = "All Employees With Lower Authorization than " + loggedInUser.firstname + " " + loggedInUser.lastname; 
    document.getElementById("management-requests-subheader").textContent = "All Requests Made By Employees WIth Lower Authorization than " + loggedInUser.firstname + " " + loggedInUser.lastname;
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

function loadManagementRequests(){

    hideAll();
    document.getElementById("landing-management-requests").hidden = false;
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
    document.getElementById("request-modal-resolution-description").value = request.resolutionDescription || "";

    if(request.status == 2){

        document.getElementById("request-modal-description").disabled = true;
        document.getElementById("request-modal-reference").disabled = true;
        document.getElementById("request-update-button").disabled = true;
        document.getElementById("request-update-button").setAttribute("onclick", "");
        document.getElementById("request-modal-approved").textContent = approvalEnum[request.approved ? 1 : 0];
    }

    else{

        document.getElementById("request-modal-description").disabled = false;
        document.getElementById("request-modal-reference").disabled = false;
        document.getElementById("request-update-button").disabled = false;
        document.getElementById("request-update-button").setAttribute("onclick", "onUpdateRequestClick();");
        document.getElementById("request-modal-approved").textContent = "";
    }
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

    requests = JSON.parse(window.sessionStorage.getItem("requests"));

    for(let r of requests){

        if(r.requestId == request.requestId){

            r.description = request.description;
            r.reference = request.reference;
        }
    }

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

    xhr.open("POST", url + "/api/landing/request/update");

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

    document.getElementById("request-modal-description").disabled = true;
    document.getElementById("request-modal-reference").disabled = true;
    document.getElementById("request-update-button").disabled = true;
    document.getElementById("request-update-button").setAttribute("onclick", "");

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

    xhr.open("POST", url + "/api/landing/request/new");

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

function getManagementEmployees(callback){

    let xhr = new XMLHttpRequest();

    xhr.open("GET", url + "/api/landing/employee/authority");

    xhr.setRequestHeader("authority", loggedInUser.authority);

    xhr.onreadystatechange = function() {

        if(xhr.readyState === 4 && xhr.status === 200){

            callback(JSON.parse(xhr.response));
        }
    }

    xhr.send();
}

function onNavbarEntryClickGetManagementEmployees(){

    let element = document.getElementById("landing-side-nav").getElementsByClassName("active")[0];
    element.classList.remove("active");

    document.getElementById("navbar-management-employees").classList.add("active");

    document.getElementById("new-employee-modal-firstname").value = "";
    document.getElementById("new-employee-modal-lastname").value = "";
    document.getElementById("new-employee-modal-username").value = "";
    document.getElementById("new-employee-modal-password").value = "";
    document.getElementById("new-employee-modal-email").value = "";
    document.getElementById("new-employee-modal-authority").value = "Employee";

    document.getElementById("new-employee-modal-button").setAttribute("onclick", "onNewEmployeeClick();")
    document.getElementById("new-employee-modal-button").disabled = false;

    hideNewEmployeeMessages();
}

function hideNewEmployeeMessages(){

    document.getElementById("new-employee-modal-error-message").hidden = true;
    document.getElementById("new-employee-modal-success").hidden = true;
}

function onNewEmployeeSuccess(){

    document.getElementById("new-employee-modal-success").hidden = false;
    document.getElementById("new-employee-modal-button").setAttribute("onclick", "")
    document.getElementById("new-employee-modal-button").disabled = true;

    getManagementEmployees(applyEmployeeTable);
}

function onNewEmployeeError(str){

    document.getElementById("new-employee-modal-error-message").textContent = str;
    document.getElementById("new-employee-modal-error-message").hidden = false;
}

function applyEmployeeTable(list){

    destroyEmployeeTable();

    let table = document.getElementById("employee-table-body");

    let row

    for(let e of list){

        row = table.insertRow();
        row.insertCell(0).appendChild(document.createTextNode(e.firstname));
        row.insertCell(1).appendChild(document.createTextNode(e.lastname));
        row.insertCell(2).appendChild(document.createTextNode(e.email));
        row.insertCell(3).appendChild(document.createTextNode(authEnum[e.authority]));
        row.insertCell(4).appendChild(document.createTextNode(e.username));
    }
}

function destroyEmployeeTable(){

    const table = document.getElementById("employee-table-body");

    while(table.firstChild){

        table.removeChild(table.firstChild);
    }
}

function onNewEmployeeClick(){

    hideNewEmployeeMessages();

    let user = {};

    user.username = document.getElementById("new-employee-modal-username").value;
    user.password = document.getElementById("new-employee-modal-password").value;
    user.firstname = document.getElementById("new-employee-modal-firstname").value;
    user.lastname = document.getElementById("new-employee-modal-lastname").value;
    user.email = document.getElementById("new-employee-modal-email").value;
    user.authority = authEnumReverse[document.getElementById("new-employee-modal-authority").value];

    if(!(user.username && user.password && user.firstname && user.lastname && user.email)){

        onNewEmployeeError("Please fill in all fields");
        return;
    }

    postNewEmployee(JSON.stringify(user), onNewEmployeeSuccess, onNewEmployeeError);
}

function postNewEmployee(user, callback, errorCallback){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/employee/new")

    xhr.setRequestHeader("user", user);

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            callback();
        }

        else if(xhr.readyState === 4){

            errorCallback("There was an error creating an employee");
        }
    }

    xhr.send();
}

function getManagementRequests(callback){

    let xhr = new XMLHttpRequest();

    xhr.open("GET", url + "/api/landing/request/authority");

    xhr.setRequestHeader("authority", loggedInUser.authority);

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            window.sessionStorage.setItem("userRequests", xhr.response);
            callback(JSON.parse(xhr.response));
        }
    }

    xhr.send();
}

function applyManagementRequestsTable(list){

    destroyManagementRequestsTable();

    let table = document.getElementById("management-requests-table-body");
    let row;
    let atag;
    let ptag;

    let temp

    for(let e of list){

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
        atag.setAttribute('class', 'management-anchor-glyph');
        atag.setAttribute('data-toggle', 'modal');
        atag.setAttribute('data-target', '#management-requests-modal');
        atag.setAttribute('onclick', 'onManagementRequestsModalExpand(this)');

        row.insertCell(8).appendChild(atag);
    }

    $('.management-anchor-glyph').prepend("<span class = 'fas fa-caret-square-down'></span>");
}

function applyManagementRequestsTableWithParams(list, status, str){

    destroyManagementRequestsTable();

    let table = document.getElementById("management-requests-table-body");
    let row;
    let atag;
    let ptag;

    let temp

    for(let e of list){

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
        atag.setAttribute('href', "#");
        atag.setAttribute('class', 'management-anchor-glyph');
        atag.setAttribute('data-toggle', 'modal');
        atag.setAttribute('data-target', '#management-requests-modal');
        atag.setAttribute('onclick', 'onManagementRequestsModalExpand(this)');

        row.insertCell(8).appendChild(atag);
    }

    $('.management-anchor-glyph').prepend("<span class = 'fas fa-caret-square-down'></span>");
}

function destroyManagementRequestsTable(){

    const table = document.getElementById("management-requests-table-body");

    while(table.firstChild){

        table.removeChild(table.firstChild);
    }
}

function onManagementFilterClick(e){

    document.getElementById("management-filter-nav").getElementsByClassName("active")[0].classList.remove("active");
    e.classList.add("active");
}

function filterManagementAllRequests(e){

    if(e == document.getElementById("management-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    applyManagementRequestsTable(JSON.parse(window.sessionStorage.getItem("userRequests")));
}

function filterManagementUnopenedRequests(e){

    if(e == document.getElementById("management-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    applyManagementRequestsTableWithParams(JSON.parse(window.sessionStorage.getItem("userRequests")), 0);
}

function filterManagementPendingRequests(e){

    if(e == document.getElementById("management-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    applyManagementRequestsTableWithParams(JSON.parse(window.sessionStorage.getItem("userRequests")), 1);
}

function filterManagementResolvedRequests(e){

    if(e == document.getElementById("management-filter-nav").getElementsByClassName("active")[0]){

        return;
    }

    applyManagementRequestsTableWithParams(JSON.parse(window.sessionStorage.getItem("userRequests")), 2);
}

function onManagementRequestsModalExpand(e){

    let row = e.parentNode.parentNode.childNodes;
    let request = searchCurrentEmployeeRequests(row[0].textContent);

    document.getElementById("management-requests-modal-id").textContent = row[0].textContent;
    document.getElementById("management-requests-modal-applicant").textContent = row[1].textContent;
    document.getElementById("management-requests-modal-status").textContent = row[2].textContent;
    document.getElementById("management-requests-modal-authority").textContent = row[3].textContent;
    document.getElementById("management-requests-modal-amount").textContent = row[4].textContent;
    document.getElementById("management-requests-modal-submission-date").textContent = row[5].textContent;
    document.getElementById("management-requests-modal-resolution-date").textContent = row[6].textContent;
    document.getElementById("management-requests-modal-resolved-by").textContent = row[7].textContent;

    document.getElementById("management-requests-modal-description").value = request.description || "";
    document.getElementById("management-requests-modal-reference").value = request.reference || "";
    document.getElementById("management-requests-modal-resolution-description").value = request.resolutionDescription || "";

    if(request.status == 2){

        document.getElementById("management-requests-modal-reject-button").disabled = true;
        document.getElementById("management-requests-modal-approved-button").disabled = true;
        document.getElementById("management-requests-modal-resolution-description").disabled = true;
    }

    else{

        document.getElementById("management-requests-modal-reject-button").disabled = false;
        document.getElementById("management-requests-modal-approved-button").disabled = false;
        document.getElementById("management-requests-modal-resolution-description").disabled = false;
    }

    checkStatus(request);
}

function searchCurrentEmployeeRequests(id){

    requests = JSON.parse(window.sessionStorage.getItem("userRequests"));

    for(let r of requests){

        if(r.requestId == id){

            return r;
        }
    }
}

function reassignSessionEmployeeRequests(request){

    requests = JSON.parse(window.sessionStorage.getItem("userRequests"));

    for(let r of requests){

        if(r.requestId == request.requestId){

            r.status = request.status;
            r.resolutionDescription = request.resolutionDescription;
            r.resolvedBy = request.resolvedBy;
            r.resolutionDate = request.resolutionDate;
        }
    }

    window.sessionStorage.setItem("userRequests", JSON.stringify(requests));
}

function checkStatus(request){

    if(request.status == 0){

        request.status = 1;

        postRequestUpdate(JSON.stringify(request), managementTableUpdateRequest, managementTableUpdateRequestError);
    }
}

function managementTableUpdateRequest(request){

    reassignSessionEmployeeRequests(request)

    let str = document.getElementById("management-filter-nav").getElementsByClassName("active")[0].textContent;

    if(str.includes("All")){

        filterManagementAllRequests();
    }

    else if(str.includes("Unopened")){

        filterManagementUnopenedRequests();
    }

    else if(str.includes("Pending")){

        filterManagementPendingRequests();
    }

    else{

        filterManagementResolvedRequests();
    }

}

function managementTableUpdateRequestError(){


}

function onManagementUpdateRequestClick(approved){

    hideUpdateManagementRequestMessages();

    let resolutionDescription = document.getElementById("management-requests-modal-resolution-description").value;

    let request = searchCurrentEmployeeRequests(document.getElementById("management-requests-modal-id").textContent);

    request.status = 2;
    request.resolutionDescription = resolutionDescription;
    request.approved = approved

    postRequestFinalize(JSON.stringify(request), JSON.stringify(loggedInUser), showManagementRequestSuccess, showManagementRequestError);
}

function showManagementRequestSuccess(request){

    document.getElementById("management-requests-modal-success").hidden = false;
    managementTableUpdateRequest(request);

    document.getElementById("management-requests-modal-reject-button").disabled = true;
    document.getElementById("management-requests-modal-approved-button").disabled = true;
    document.getElementById("management-requests-modal-resolution-description").disabled = true;
}

function showManagementRequestError(){

    document.getElementById("management-requests-modal-error-message").hidden = false;
}

function hideUpdateManagementRequestMessages(){

    document.getElementById("management-requests-modal-success").hidden = true;
    document.getElementById("management-requests-modal-error-message").hidden = true;
}

function postRequestFinalize(request, user, callback, errorCallback){

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url + "/api/landing/request/final");

    xhr.setRequestHeader("request", request);
    xhr.setRequestHeader("user", user);
    xhr.setRequestHeader("token", window.sessionStorage.getItem("token"));

    xhr.onreadystatechange = function(){

        if(xhr.readyState === 4 && xhr.status === 200){

            callback(JSON.parse(xhr.response));
        }

        else if(xhr.readyState === 4){

            errorCallback();
        }
    }

    xhr.send();
}