function askSessionName(form){
    const name = prompt("Enter the Name of the session")
    if(name && name.trim()!==""){
        document.getElementById("sessionName").value=name;
        return true;
    }
    alert("Session name is required.");
    return false;
}

document.addEventListener("DOMContentLoaded", function () {

    fetch("/exercises/names")
        .then(res=>res.json())
    .then(data=> {
        let options = document.getElementById("exerciseOptions");
        if(options){
            data.forEach(element => {
                let op = document.createElement("option");
                op.value = element;
                options.appendChild(op);
            })
        }
        else{
            console.warn("exerciseOptions not found in DOM");
        }
    })

})



