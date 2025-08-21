
function saveSession(){

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');


    let exerciseContainer = document.querySelectorAll(".exercise-container");


    const myMap = new Map();

    exerciseContainer.forEach((exerciseContainer) => {

        let exeName = exerciseContainer.getElementsByClassName("exercise-title")[0].innerText;


        let table = exerciseContainer.getElementsByTagName("table")[0];

        let rows = table.getElementsByTagName("tr");


        let rowData = {
            exerciseName: exeName,
            sets: [],
            weight: [],
            reps: [],
            volume: []
        };

        for (let i = 1; i < rows.length; i++) {
            //inserting data in rowData
            let cells = rows[i].getElementsByTagName("td");
            rowData.sets.push(parseInt(cells[0].innerText) || 0);
            rowData.weight.push(parseFloat(cells[1].querySelector("input").value) || 0);
            rowData.reps.push(parseInt(cells[2].querySelector("input").value) || 0);
            rowData.volume.push(parseFloat(cells[3].innerText) || 0);
        }

        //Putting that exercise with its data
        myMap.set(exeName, [rowData]);
    })

    //getting unique sessionId
    let sessionId = document.getElementById("sessionId").value;

    //Asking for a sessionName
    const name = prompt("Enter the Name of the session")
    if(name && name.trim()!==""){
        document.getElementById("sessionName").value=name;
    }
    else{
        alert("Session name is required.");
    }

    //required to send SaveSessionRequest DTO
    const payload = {
        sessionName: name,
        workouts: Object.fromEntries(myMap) //convert to plain Object
    };

    // Send to Spring Boot
    fetch(`/sessionSave/saveTable?sessionId=${sessionId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken //required for login user
        },
        body: JSON.stringify(payload),//sending payload as saveSessionRequest plain object
        credentials: "include"
    })
        .then(response => response.json())
        .then(result => {
            alert("Table saved successfully!");
            if (result.redirectUrl) {
                //redirect url : coming from backened
                window.location.href = result.redirectUrl;
            }
        })
        .catch(error => console.error("Error:", error));

}