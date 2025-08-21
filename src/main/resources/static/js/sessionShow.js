let cards = document.querySelectorAll('.col');
let popupDiv = document.getElementById('popup_overlay_div');
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
cards.forEach(card => {
    card.addEventListener('click', event => {
        let sessionId = card.getAttribute("data-id");

        //fetching data

        fetch(`/sessionSave/showSession/${sessionId}`, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                [csrfHeader]: csrfToken //required for login user
            }
        }).then(response => response.json())
            .then(result => {

    //             let rows = "";
    //
    //             result.workouts.forEach(workout => {
    //                 // Add workout name as a "section header"
    //                 rows += `
    //     <tr class="table-primary">
    //         <td colspan="4"><strong>${workout.exerciseName}</strong></td>
    //     </tr>
    // `;
    //
    //                 rows+=`
    //                     <thead>
    //                         <tr>
    //                             <th>Set</th>
    //                             <th>Weight</th>
    //                             <th>Reps</th>
    //                             <th>Volume</th>
    //                         </tr>
    //                     </thead>
    //                 `
    //
    //                 // Add each set for this workout
    //                 workout.sets.forEach((setNum, index) => {
    //                     rows += `<tbody>
    //             <tr>
    //                 <td>${setNum}</td>
    //                 <td>${workout.weight[index]}</td>
    //                 <td>${workout.reps[index]}</td>
    //                 <td>${workout.volume[index]}</td>
    //             </tr>
    //         </tbody>
    //     `;
    //                 });
    //             });
    //
    //
    //             // Build popup HTML
    //                 const popupHTML = `
    //         <div class="popup-overlay">
    //             <div class="popup-box">
    //                 <h3>${result.sessionName}</h3>
    //                 <p><strong>Start:</strong> ${result.startTime}</p>
    //                 <p><strong>Duration:</strong> ${result.duration}</p>
    //
    //
    //                 <table class="exercise-table table table-bordered table-dark">
    //
    //
    //                         ${rows}
    //
    //                 </table>
    //
    //                 <button type="button" class="btn btn-danger mt-3 close-popup">Close</button>
    //             </div>
    //         </div>
    //     `;
    //
    //                 popupDiv.innerHTML = popupHTML;


                let tables = "";

                result.workouts.forEach(workout => {
                    let rows = "";

                    workout.sets.forEach((setNum, index) => {
                        rows += `
            <tr>
                <td>${setNum}</td>
                <td>${workout.weight[index]}</td>
                <td>${workout.reps[index]}</td>
                <td>${workout.volume[index]}</td>
            </tr>
        `;
                    });

                    tables += `
        <h5 class="mt-3">${workout.exerciseName}</h5>
        <table class="exercise-table table table-bordered table-dark">
            <thead>
                <tr>
                    <th>Set</th>
                    <th>Weight</th>
                    <th>Reps</th>
                    <th>Volume</th>
                </tr>
            </thead>
            <tbody>
                ${rows}
            </tbody>
        </table>
    `;
                });

// Build popup HTML
                const popupHTML = `
    <div class="popup-overlay">
        <div class="popup-box">
            <h3>${result.sessionName}</h3>
            <p><strong>Start:</strong> ${result.startTime}</p>
            <p><strong>Duration:</strong> ${result.duration}</p>

            ${tables}

            <button type="button" class="btn btn-danger mt-3 close-popup">Close</button>
        </div>
    </div>
`;

                popupDiv.innerHTML = popupHTML;


            })
            .catch(error => console.error("Error:", error));

    });
});

// ✅ Close button works for all popups
popupDiv.addEventListener("click", (e) => {
    if (e.target.classList.contains("close-popup") || e.target.classList.contains("popup-overlay")) {
        popupDiv.innerHTML = ""; // Clear popup
    }
});
