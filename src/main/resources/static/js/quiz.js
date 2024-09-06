document.addEventListener("DOMContentLoaded", () => {
    quizId = document.getElementById("quiz-id").textContent;
    resultDiv = document.getElementById("result");
    nextBtn = document.getElementById("next-question-btn");


    const takeQuizBtn = document.getElementById("take-quiz-btn");
    const answersForm = document.getElementById("answers-form");
    

    takeQuizBtn.addEventListener("click", getQuestion);
    // TODO: disable this button until the quiz ends

    answersForm.addEventListener('submit', (event) => handleSubmitAnswer(event));
});

let quizId;
let correctAnswer;
let userQuizScore = 0;
let currentQuestionNumber = 0;
let resultDiv;
let nextBtn;


function getQuestion() {

    currentQuestionNumber++;
    
    const container = document.getElementById("question-container");
    container.classList.remove("d-none");
    container.classList.add("d-flex", "flex-column", "align-items-center", "gap-3");
    const questionText = document.getElementById("question-text");
    const answersContainer = document.getElementById("answers-list");
    answersContainer.innerHTML = "";
    resultDiv.classList.add("d-none");
    resultDiv.classList.remove("text-danger");
    resultDiv.classList.remove("text-success");
    nextBtn.classList.add("d-none");

    const questionUrl = `/api/quizzes/${quizId}/question/${currentQuestionNumber}`;

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
        correctAnswer = answersList.filter(answer => answer.correct === true)[0].answerText;
        console.log(`correctAnswer: ${correctAnswer}`);

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

function handleSubmitAnswer(event) {
    event.preventDefault();
    const checkedAnswer = getCheckedAnswerValue();
    console.log(`User selected: ${checkedAnswer}`);
    resultDiv.classList.remove("d-none");
    if (checkedAnswer === correctAnswer) {
        userQuizScore++;
        resultDiv.textContent = "Correct!";
        resultDiv.classList.add("text-success");
    } else {
        resultDiv.textContent = "WRONG :(";
        resultDiv.classList.add("text-danger");
    }
    
    // TODO: check if the quiz has next question
    // if currentQuestionNumber + 1 <= totalQuestionsNumber
    nextBtn.classList.remove("d-none");
    nextBtn.classList.add("btn", "btn-outline-primary");
    nextBtn.addEventListener("click", getQuestion);
};


function getCheckedAnswerValue() {
    const checkedAnswers = document.querySelectorAll(`input[name="answerRadio"]:checked`);
    if (checkedAnswers.length > 0) {
        return checkedAnswers[0].value;
    } else {
        return null;
    }
};
