let quizId;
let totalNumberOfQuestions;

let correctAnswer;
let userQuizScore = 0;
let currentQuestionNumber = 0;

let takeQuizBtn;
let submitBtn;
let nextBtn;

let questionResultDiv;
let quizResultsContainer; 


document.addEventListener("DOMContentLoaded", () => {
    quizId = document.getElementById("quiz-id").textContent;
    totalNumberOfQuestions = +document.getElementById("total").textContent;

    takeQuizBtn = document.getElementById("take-quiz-btn");
    nextBtn = document.getElementById("next-question-btn");
    submitBtn = document.getElementById("submit-btn");

    questionResultDiv = document.getElementById("result");
    quizResultsContainer = document.getElementById("results-container");

    const answersForm = document.getElementById("answers-form");

    takeQuizBtn.addEventListener("click", getQuestion);
    nextBtn.addEventListener("click", getQuestion);
    }
    answersForm.addEventListener('submit', (event) => handleSubmitAnswer(event));
});


function getQuestion() {

    if (currentQuestionNumber == 0) {
        takeQuizBtn.disabled = true;
        const questionContainer = document.getElementById("question-container");
        questionContainer.classList.remove("d-none");
        quizResultsContainer.classList.add("d-none");
        const quizResults = document.getElementById("quiz-results");
        quizResults.classList.add("d-none");
    }

    currentQuestionNumber++;

    const questionNumber = document.getElementById("question-number");
    questionNumber.textContent = currentQuestionNumber;
    const questionText = document.getElementById("question-text");
    questionText.textContent = "";
    const answersContainer = document.getElementById("answers-list");
    answersContainer.innerHTML = "";

    questionResultDiv.classList.add("d-none");
    questionResultDiv.classList.remove("text-danger");
    questionResultDiv.classList.remove("text-success");

    nextBtn.classList.add("d-none");
    submitBtn.classList.remove("d-none");

    const questionUrl = `/api/quizzes/${quizId}/question/${currentQuestionNumber}`;

    fetch(questionUrl).then(response => {
        if (response.status === 200) {
            return response.json();
        }
        throw new Error(response);
    }).then(data => {
        questionText.textContent = data.questionText;
        const answersList = data.answers;
        answersList.forEach(answer => createAnswerDiv(answer, answersContainer));
        correctAnswer = answersList.filter(answer => answer.isCorrect)[0].answerText;
    }).catch(error => {
        console.log(error);
    });
};

function createAnswerDiv(answer, parent) {
    const formRow = document.createElement("div");
    formRow.classList.add("form-check");

    const input = document.createElement('input');
    input.type = 'radio';
    input.name = 'answerRadio';
    input.id = `answer${answer.answerId}`;
    input.value = answer.answerText;
    input.classList.add("form-check-input");

    const label = document.createElement('label');
    label.htmlFor = `answer${answer.answerId}`;
    label.classList.add("form-check-label");
    label.textContent = answer.answerText;

    formRow.appendChild(input);
    formRow.appendChild(label);

    parent.appendChild(formRow);
};

function handleSubmitAnswer(event) {
    event.preventDefault();
    const checkedAnswer = getCheckedAnswerValue();

    questionResultDiv.classList.remove("d-none");
    submitBtn.classList.add("d-none");
    disableAnswers();
    if (checkedAnswer === correctAnswer) {
        userQuizScore++;
        questionResultDiv.textContent = "Correct!";
        questionResultDiv.classList.add("text-success");
    } else {
        questionResultDiv.textContent = "Wrong";
        questionResultDiv.classList.add("text-danger");
    }

    if (currentQuestionNumber + 1 <= totalNumberOfQuestions) {
        nextBtn.classList.remove("d-none");
        nextBtn.classList.add("btn", "btn-outline-primary");
    } else {
        showResults();
    }
};

function getCheckedAnswerValue() {
    const checkedAnswers = document.querySelectorAll(`input[name="answerRadio"]:checked`);
    if (checkedAnswers.length > 0) {
        return checkedAnswers[0].value;
    } else {
        return null;
    }
};

function disableAnswers() {
    const answersRadioGroup = document.querySelectorAll('input[name="answerRadio"]');
    answersRadioGroup.forEach(radio => {
        radio.disabled = true;
    });
};

function showResults() {
    quizResultsContainer.classList.remove("d-none");

    const resultsBtn = document.getElementById("show-results-btn");
    resultsBtn.classList.remove("d-none");

    resultsBtn.addEventListener("click", displayResults);
};

function displayResults() {
    const questionContainer = document.getElementById("question-container");
    questionContainer.classList.add("d-none");

    const resultsBtn = document.getElementById("show-results-btn");
    resultsBtn.classList.add("d-none");

    const quizResults = document.getElementById("quiz-results");
    const percent = Math.round((userQuizScore /  totalNumberOfQuestions * 10000) / 100);

    const scoreSpan = document.getElementById("score");
    scoreSpan.textContent = userQuizScore;

    const percentSpan = document.getElementById("percent");
    percentSpan.textContent = `${percent}%`;

    if (percent >= 80) {
        percentSpan.classList.add("text-success");
    }
    else if (percent >= 60) {
        percentSpan.classList.add("text-warning");
    }
    else {
        percentSpan.classList.add("text-danger");
    }
    quizResults.classList.remove("d-none");

    resetTakeQuizPage();
};

function resetTakeQuizPage() {
    takeQuizBtn.disabled = false;
    takeQuizBtn.textContent = "Retake Quiz";
    currentQuestionNumber = 0;
    userQuizScore = 0;
};
