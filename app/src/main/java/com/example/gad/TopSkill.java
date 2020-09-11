package com.example.gad;

public class TopSkill {
    // Name of the TopSkillLearner from https://gadsapi.herokuapp.com
    public String learnerName;

    // Score obtained by the TopSkillLearner on study from https://gadsapi.herokuapp.com
    public String score;

    // Country of the TopSkilLearner from https://gadsapi.herokuapp.com
    public String learnerCountry;

    // Url of the TopSkillLearner Badge from https://gadsapi.herokuapp.com
    public String badgeImageUrl;

    /*
     * Create a new TopLearner object.
     *
     * @param learnerName is the name of the TopSkillLearner from https://gadsapi.herokuapp.com
     * @param score is the score obtained by the TopSkillLearner on study from https://gadsapi.herokuapp.com
     * @param learnerCountry is the country of the TopSkillLearner from https://gadsapi.herokuapp.com
     * @param learnerImageUrl is the TopSkillLearner Badge Image Url  from https://gadsapi.herokuapp.com
     * */
    public TopSkill(String learnerName, String score, String learnerCountry, String badgeImageUrl) {
        this.learnerName = learnerName;
        this.score = score;
        this.learnerCountry = learnerCountry;
        this.badgeImageUrl = badgeImageUrl;
    }
}
