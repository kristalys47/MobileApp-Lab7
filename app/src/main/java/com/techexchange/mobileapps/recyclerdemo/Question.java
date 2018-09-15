package com.techexchange.mobileapps.recyclerdemo;

class Question {

    private final String question, wrongAnswer, correctAnswer;
    private String selectedAnswer;

    public Question(String q, String c, String w) {
        this.question=q;
        this.wrongAnswer=w;
        this.correctAnswer=c;
        this.selectedAnswer=null;
    }

    public String getQuestion() {
        return question;
    }

    public String getWrongAnswer() {
        return wrongAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return "Question: " + this.question + "\nCorrect Answer: " + this.correctAnswer + "\nIncorrectAnswer" + this.wrongAnswer;
    }
}
