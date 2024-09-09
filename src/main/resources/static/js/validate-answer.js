document.addEventListener("DOMContentLoaded", () => {

  const editAnswerForm = document.getElementById("edit-answer");
  const answerText = document.getElementById("answerText");

  answerText.addEventListener("input", () => {
      editQuizForm.classList.remove("was-validated");
  });

  editAnswerForm.addEventListener("submit", (event) => {
      if (!editAnswerForm.checkValidity()) {
          event.preventDefault();
          editAnswerForm.classList.add("was-validated");
      }
  });
  
});