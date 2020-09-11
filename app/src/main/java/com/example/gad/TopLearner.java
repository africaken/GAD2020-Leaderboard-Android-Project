package com.example.gad;

public class TopLearner {
    // Name of the TopLearner from https://gadsapi.herokuapp.com
    public String learnerName;

    // Hours spent by the TopLearner on study from https://gadsapi.herokuapp.com
    public String learningHours;

    // Country of the TopLearner from https://gadsapi.herokuapp.com
    public String learnerCountry;

    // Url of the TopLearner Badge from https://gadsapi.herokuapp.com
    public String badgeImageUrl;

    /*
     * Create a new TopLearner object.
     *
     * @param learnerName is the name of the TopLearner from https://gadsapi.herokuapp.com
     * @param learningHours is the hours spent by of the TopLearner on study from https://gadsapi.herokuapp.com
     * @param learnerCountry is the country of the TopLearner from https://gadsapi.herokuapp.com
     * @param badgeImageUrl is the TopLearner Badge Image Url from https://gadsapi.herokuapp.com
     * */
    public TopLearner(String learnerName, String learningHours, String learnerCountry, String badgeImageUrl) {
        this.learnerName = learnerName;
        this.learningHours = learningHours;
        this.learnerCountry = learnerCountry;
        this.badgeImageUrl = badgeImageUrl;
    }
}
