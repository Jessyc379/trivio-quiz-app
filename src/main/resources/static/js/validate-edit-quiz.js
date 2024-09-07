document.addEventListener("DOMContentLoaded", () => {

    const editQuizForm = document.getElementById("edit-quiz");
    const quizTitle = document.getElementById("title");

    quizTitle.addEventListener("input", () => {
        editQuizForm.classList.remove("was-validated");
    });

    editQuizForm.addEventListener("submit", (event) => {
        if (!editQuizForm.checkValidity()) {
            event.preventDefault();
            editQuizForm.classList.add("was-validated");
        }
    });
    
});