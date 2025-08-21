let exercises = {};
let exerciseCounter = 0;

function addExercise() {
    const exerciseNameInput = document.getElementById('newExerciseName');
    const exerciseName = exerciseNameInput.value.trim();

    if (!exerciseName) {
        alert('Please enter an exercise name!');
        exerciseNameInput.focus();
        return;
    }

    const exerciseId = ++exerciseCounter;
    exercises[exerciseId] = {
        name: exerciseName,
        sets: []
    };

    exerciseNameInput.value = '';
    exerciseNameInput.focus();

    renderExercises();
    updateStats();
}

function deleteExercise(exerciseId) {
    if (confirm('Are you sure you want to delete this exercise and all its sets?')) {
        delete exercises[exerciseId];
        renderExercises();
        updateStats();
    }
}

function addSet(exerciseId) {
    if (!exercises[exerciseId]) return;

    const setNumber = exercises[exerciseId].sets.length + 1;
    exercises[exerciseId].sets.push({
        setNumber: setNumber,
        weight: '',
        reps: ''
    });

    renderExercises();
}

function deleteSet(exerciseId, setIndex) {
    if (!exercises[exerciseId]) return;

    exercises[exerciseId].sets.splice(setIndex, 1);

    // Renumber the remaining sets
    exercises[exerciseId].sets.forEach((set, index) => {
        set.setNumber = index + 1;
    });

    renderExercises();
    updateStats();
}

function updateSetValue(exerciseId, setIndex, field, value) {
    if (!exercises[exerciseId] || !exercises[exerciseId].sets[setIndex]) return;

    exercises[exerciseId].sets[setIndex][field] = value;
    updateStats();
}

function renderExercises() {
    const container = document.getElementById('exercisesContainer');

    if (Object.keys(exercises).length === 0) {
        container.innerHTML = `
                    <div class="empty-state">
                        No exercises added yet. Start by adding your first exercise above! 🏋️‍♂️
                    </div>
                `;
        return;
    }

    container.innerHTML = Object.entries(exercises).map(([exerciseId, exercise]) => `
                <div class="exercise-container">
                    <div class="exercise-header">
                        <div class="exercise-title">${exercise.name}</div>
                        <div class="exercise-controls">
                            <button class="add-set-btn" onclick="addSet(${exerciseId})">+ Add Set</button>
                            <button class="delete-exercise-btn" onclick="deleteExercise(${exerciseId})">Delete Exercise</button>
                        </div>
                    </div>
                    <table class="exercise-table">
                        <thead>
                            <tr>
                                <th>Set</th>
                                <th>Weight (lbs/kg)</th>
                                <th>Reps</th>
                                <th>Volume</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            ${exercise.sets.length === 0 ? `
                                <tr>
                                    <td colspan="5" style="text-align: center; color: #a0aec0; padding: 20px;">
                                        Click "Add Set" to start logging sets for this exercise
                                    </td>
                                </tr>
                            ` : exercise.sets.map((set, setIndex) => {
        const weight = parseFloat(set.weight) || 0;
        const reps = parseInt(set.reps) || 0;
        const volume = weight * reps;

        return `
                                    <tr class="fade-in">
                                        <td data-label="Set" style="font-weight: 600;">${set.setNumber}</td>
                                        <td data-label="Weight">
                                            <input type="number"
                                                   class="set-input"
                                                   value="${set.weight}"
                                                   placeholder="0"
                                                   min="0"
                                                   step="0.5"
                                                   oninput="updateSetValue(${exerciseId}, ${setIndex}, 'weight', this.value)">
                                        </td>
                                        <td data-label="Reps">
                                            <input type="number"
                                                   class="set-input"
                                                   value="${set.reps}"
                                                   placeholder="0"
                                                   min="0"
                                                   oninput="updateSetValue(${exerciseId}, ${setIndex}, 'reps', this.value)">
                                        </td>
                                        <td data-label="Volume" style="font-weight: 600; color: #38a169;">
                                            ${volume > 0 ? volume.toFixed(1) : '-'}
                                        </td>
                                        <td  data-label="Action">
                                            <button class="delete-set-btn" onclick="deleteSet(${exerciseId}, ${setIndex})">
                                                Delete
                                            </button>
                                        </td>
                                    </tr>
                                `;
    }).join('')}
                        </tbody>
                    </table>
                </div>
            `).join('');
}

function updateStats() {
    const totalExercises = Object.keys(exercises).length;
    let totalSets = 0;
    let totalVolume = 0;
    let totalWeightEntries = 0;
    let totalWeight = 0;

    Object.values(exercises).forEach(exercise => {
        exercise.sets.forEach(set => {
            totalSets++;
            const weight = parseFloat(set.weight) || 0;
            const reps = parseInt(set.reps) || 0;
            totalVolume += weight * reps;

            if (weight > 0) {
                totalWeight += weight;
                totalWeightEntries++;
            }
        });
    });

    const avgWeight = totalWeightEntries > 0 ? totalWeight / totalWeightEntries : 0;

    document.getElementById('totalExercises').textContent = totalExercises;
    document.getElementById('totalSets').textContent = totalSets;
    document.getElementById('totalVolume').textContent = totalVolume.toFixed(1);
    document.getElementById('avgWeight').textContent = avgWeight.toFixed(1);
}

// Allow pressing Enter to add exercise
document.getElementById('newExerciseName').addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        addExercise();
    }
});

// Initialize
updateStats();