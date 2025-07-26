function askSessionName(form){
    const name = prompt("Enter the Name of the session")
    if(name && name.trim()!==""){
        document.getElementById("sessionName").value=name;
        return true;
    }
    alert("Session name is required.");
    return false;
}



