const form = document.getElementById('workout-questions-form');
const steps = document.querySelectorAll('.step');
let currStep = 0;

function showStep(step){
    steps.forEach((s,i) => {
        s.classList.toggle('hidden',i!==step);
    })
}

//for next button
form.querySelectorAll('.next').forEach(btn => btn.addEventListener('click', ()=>{
    if(currStep < steps.length-1){
        currStep++;
        showStep(currStep);
    }
}));

//for previous button
form.querySelectorAll('.prev').forEach(btn => btn.addEventListener('click', ()=>{
    if(currStep > 0){
        currStep--;
        showStep(currStep);
    }
}));

showStep(currStep);

const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(form);
    const data = Object.fromEntries(formData.entries());

    document.querySelector(".spinner-border").style.display = 'inline-block';
    const btn = document.getElementById('workoutBtn');
    btn.disabled = true;
    document.getElementById('btnText').innerText = '';

        fetch('/AskAiPlanAjax', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/html',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(data)
        })
            .then(response => response.text())
            .then(result => {
                // console.log('AI response:', result);
                // todo -display the response in the UI (e.g. in a div)
                const resultDiv = document.getElementById('workout-plan-result');
                const qna = document.getElementById('qna-div');
                if(resultDiv) {
                    // You can insert raw text or sanitized HTML depending on your backend response
                    const box = document.createElement('div');
                    box.innerHTML = result;
                    resultDiv.append(box);
                    qna.style.display = 'none';
                    document.querySelector(".spinner-border").style.display = 'none';
                    document.querySelector(".hidden-btn").style.display = 'block';
                }
            })
            .catch(error => console.error('Error:', error))
            .finally(
                () => {
                    // Hide spinner & re-enable button regardless of success or error
                    document.querySelector(".spinner-border").style.display  = 'none';
                    btn.disabled = false;
                    document.getElementById('btnText').innerText = 'Get Workout Plan';
                });




});


