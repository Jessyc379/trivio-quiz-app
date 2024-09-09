let quizId;
let total;
let takeQuizBtn;
let correctAnswer;
let userQuizScore = 0;
let currentQuestionNumber = 0;
let resultDiv;
let nextBtn;
let submitBtn;



document.addEventListener("DOMContentLoaded", () => {
    quizId = document.getElementById("quiz-id").textContent;
    total = +document.getElementById("total").textContent;

    takeQuizBtn = document.getElementById("take-quiz-btn");
    nextBtn = document.getElementById("next-question-btn");
    submitBtn = document.getElementById("submit-btn");

    resultDiv = document.getElementById("result");

    const answersForm = document.getElementById("answers-form");

    takeQuizBtn.addEventListener("click", getQuestion);
    nextBtn.addEventListener("click", getQuestion);
    answersForm.addEventListener('submit', (event) => handleSubmitAnswer(event));

});


function getQuestion() {

    if (currentQuestionNumber == 0) {
        takeQuizBtn.disabled = true;
        const questionContainer = document.getElementById("question-container");
        questionContainer.classList.remove("d-none");
        questionContainer.classList.add("d-flex", "flex-column", "align-items-center", "gap-3");
    }

    currentQuestionNumber++;

    const questionText = document.getElementById("question-text");
    const answersContainer = document.getElementById("answers-list");
    answersContainer.innerHTML = "";

    resultDiv.classList.add("d-none");
    resultDiv.classList.remove("text-danger");
    resultDiv.classList.remove("text-success");

    nextBtn.classList.add("d-none");
    submitBtn.classList.remove("d-none");

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
        correctAnswer = answersList.filter(answer => answer.isCorrect)[0].answerText;
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
    submitBtn.classList.add("d-none");
    disableAnswers();
    if (checkedAnswer === correctAnswer) {
        userQuizScore++;
        resultDiv.textContent = "Correct!";
        resultDiv.classList.add("text-success");
    } else {
        resultDiv.textContent = "Wrong";
        resultDiv.classList.add("text-danger");
    }

    if (currentQuestionNumber + 1 <= total) {
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
    const questionContainer = document.getElementById("question-container");
    questionContainer.classList.add("d-none");

    const resultsDiv = document.getElementById("results-container");
    resultsDiv.classList.remove("d-none");
    resultsDiv.classList.add("d-flex", "flex-column", "align-items-center");

    const resultsBtn = document.getElementById("show-results-btn");

    resultsBtn.addEventListener("click", displayResults);
};

function displayResults() {
    const resultsBtn = document.getElementById("show-results-btn");
    resultsBtn.classList.add("d-none");

    const results = document.getElementById("quiz-results");
    const percent = Math.round((userQuizScore / total * 10000) / 100);

    const scoreSpan = document.getElementById("score");
    scoreSpan.textContent = userQuizScore;

    const percentSpan = document.getElementById("percent");
    percentSpan.textContent = `${percent}%`;

    if (percent >= 80) {
        percentSpan.classList.add("text-success")
    }
    else if (percent >= 60) {
        percentSpan.classList.add("text-warning")
    }
    else {
        percentSpan.classList.add("text-danger")
    }
    results.classList.remove("d-none");
    results.classList.add("d-flex", "flex-column", "align-items-center");
}


