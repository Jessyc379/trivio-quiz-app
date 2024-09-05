document.addEventListener("DOMContentLoaded", () => {
    // on load

    const quizId = document.getElementById("quizId").textContent;
    console.log(quizId);

    const takequiz = document.getElementById("takeQuiz");
    takequiz.addEventListener("click", () => getQuestion(quizId, 1));


});


function getQuestion(quizId, questionNumber) {

    const container = document.getElementById("question-container");
    container.innerHTML = "";

    const url = `/quizzes/${quizId}/question/${questionNumber}`;

    fetch(url).then(response => {
        if (response.status === 200) {
            return response.text();
        }
        throw new Error(response);
    }).then(data => {
        container.innerHTML = data;
    }).catch(error => {
        console.log(error)
    });

}