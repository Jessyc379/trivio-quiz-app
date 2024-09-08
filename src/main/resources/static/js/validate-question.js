document.addEventListener("DOMContentLoaded", () => {

    const editQuestionForm = document.getElementById("edit-question");
    const questionNumber = document.getElementById("questionNumber");
    const questionText = document.getElementById("questionText");

    questionNumber.addEventListener("input", () => {
        editQuestionForm.classList.remove("was-validated");
    });
    questionText.addEventListener("input", () => {
        editQuestionForm.classList.remove("was-validated");
    });

    editQuestionForm.addEventListener("submit", (event) => {
        if (!editQuestionForm.checkValidity()) {
            event.preventDefault();
            editQuestionForm.classList.add("was-validated");
        }
    });
    
});