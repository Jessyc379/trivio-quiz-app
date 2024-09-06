document.addEventListener("DOMContentLoaded", () => {
    const quizId = document.getElementById("quiz-id").textContent;
    const takeQuizBtn = document.getElementById("take-quiz-btn");

    takeQuizBtn.addEventListener("click", () => getQuestion(quizId, 1));
    // TODO: disable this button until the quiz ends
});


function getQuestion(quizId, questionNumber) {

    const container = document.getElementById("question-container");
    const questionText = document.getElementById("question-text");
    console.log(questionText);

    const questionUrl = `/api/quizzes/${quizId}/question/${questionNumber}`;

    fetch(questionUrl).then(response => {
        if (response.status === 200) {
            return response.json();
        }
        throw new Error(response);
    }).then(data => {
        console.log(data);
        
        questionText.textContent = data.questionText;

    }).catch(error => {
        console.log(error)
    });
};

