

document.addEventListener('DOMContentLoaded', ()=>{

    // Cache CSRF token and header name from meta tags
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    document.querySelectorAll('.delete-form').forEach(function(form) {
        form.addEventListener('submit', function(event) {
            event.stopPropagation();

            var cardElement = form.closest('.card');
            var workoutId = form.querySelector('input[name="id"]').value;

            if (!workoutId) {
                alert('Workout ID not found!');
                return;
            }

            fetch('/prevsession/delete', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    [csrfHeader]: csrfToken  // dynamically add CSRF token header
                },
                body: new URLSearchParams({
                    'id': workoutId
                })
            })
                .then(response => {
                    if (response.ok) {
                        if (cardElement) {
                            cardElement.remove();
                        }
                    } else {
                        alert('Failed to delete the workout session.');
                    }
                })
                .catch(error => {
                    console.error('Delete request failed:', error);
                    alert('Error deleting the workout session.');
                });
        });
    });

    document.querySelectorAll('.card').forEach(card => {
        card.addEventListener('click', function() {
            const cardId = this.getAttribute('data-id');
            showPopup(cardId);
        });
    });




});

async function showPopup(cardId) {

    console.log(cardId);
    const response = await fetch(`/api/getWorkout/${cardId}`);

    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }

    const sessionData = await response.json();
    console.log("Received data:", sessionData);


    // Select popup-box inside the popup container for this cardId
    const popupBox = document.querySelector(`#popup-${cardId} .popup-box`);
    if (!popupBox) {
        console.error(`Popup box not found for cardId: ${cardId}`);
        return;
    }

    let htmlContent = '';
    for (const [exerciseName, sets] of Object.entries(sessionData)) {
        htmlContent += `
                <div class="exercise-header" style="background-color: transparent;      font-size: 24px;
                    font-weight: 600;
                    margin-bottom: 19px;">
                    ${exerciseName.charAt(0).toUpperCase() + exerciseName.slice(1)}
                </div>
                <table class="table table-striped table-dark table-bordered mb-4">
                  <thead>
                    <tr>
                      <th>#</th>
                      <th>Weight (kg)</th>
                      <th>Set</th>
                      <th>Reps</th>
                    </tr>
                  </thead>
                  <tbody>
            `;

        sets.forEach((set, index) => {
            htmlContent += `
                    <tr>
                      <td>${index + 1}</td>
                      <td>${set.weight}</td>
                      <td>${set.set || index + 1}</td>
                      <td>${set.reps}</td>
                    </tr>
                `;
        });

        htmlContent += `</tbody></table>`;
    }

    popupBox.innerHTML = htmlContent;

    // Show popup container
    const popupContainer = document.getElementById(`popup-${cardId}`);
    if (popupContainer) popupContainer.classList.remove('hidden');

    // Add blur
    const mainContent = document.getElementById('main-content');
    if (mainContent) mainContent.classList.add('blurred');

}

function hidePopup(cardId) {
    const popupContainer = document.getElementById(`popup-${cardId}`);
    if (popupContainer) popupContainer.classList.add('hidden');
    const mainContent = document.getElementById('main-content');
    if (mainContent) mainContent.classList.remove('blurred');
}


