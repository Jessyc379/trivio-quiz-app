document.addEventListener("DOMContentLoaded", () => {
    const quizId = document.getElementById("quiz-id").textContent;
    const takeQuizBtn = document.getElementById("take-quiz-btn");

    takeQuizBtn.addEventListener("click", () => getQuestion(quizId, 1));
    // TODO: disable this button until the quiz ends
});


function getQuestion(quizId, questionNumber) {

    const container = document.getElementById("question-container");
    container.classList.remove("d-none");
    container.classList.add("d-flex", "flex-column", "align-items-center", "gap-3");
    const questionText = document.getElementById("question-text");
    const answersForm = document.getElementById("answers-form");
    const answersContainer = document.getElementById("answers-list");

    const questionUrl = `/api/quizzes/${quizId}/question/${questionNumber}`;

    fetch(questionUrl).then(response => {
        if (response.status === 200) {
            return response.json();
        }
        throw new Error(response);
    }).then(data => {
        console.log(data);
        questionText.textContent = data.questionText;
        const answersList = data.answers;
        console.log(answersList)
        answersList.forEach(answer => createAnswerDiv(answer, answersContainer));

    }).catch(error => {
        console.log(error)
    });
};

function createAnswerDiv(answer, parent) {
    const formRow = document.createElement("div");
    formRow.classList.add("form-check");

    // Create the input element
    const input = document.createElement('input');
    input.type = 'radio';
    input.name = 'answerRadio'; 
    input.id = `answer${answer.answerId}`; 
    input.value = answer.answerText;
    input.classList.add("form-check-input");

    // Create the label element
    const label = document.createElement('label');
    label.htmlFor = `answer${answer.answerId}`;
    label.classList.add("form-check-label");
    label.textContent = answer.answerText;

    // Append elements to formRow
    formRow.appendChild(input);
    formRow.appendChild(label);

    parent.appendChild(formRow);
};

