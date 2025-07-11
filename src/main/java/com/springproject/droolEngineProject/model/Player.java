package com.springproject.droolEngineProject.model;

public class Player {
    private String name;
    private String position; // GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD
    private String club;
    private Integer baseCompensation; // Base monthly salary in EUR
    private Integer goalsScored;
    private Integer assists;
    private Integer cleanSheets; // For goalkeepers and defenders
    private Integer saves; // For goalkeepers
    private Integer tackles; // For defenders and midfielders
    private Integer passingAccuracy; // Percentage (0-100)
    private Integer minutesPlayed;
    private Integer yellowCards;
    private Integer redCards;
    private Integer matchesPlayed;
    private Integer penaltiesSaved; // For goalkeepers
    private Integer penaltiesScored; // For all positions
    private Integer bonusAmount; // This will be calculated by rules
    private Integer fineAmount; // For disciplinary issues
    private Integer totalCompensation; // Final amount including base + bonus - fines

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Integer getBaseCompensation() {
        return baseCompensation;
    }

    public void setBaseCompensation(Integer baseCompensation) {
        this.baseCompensation = baseCompensation;
    }

    public Integer getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(Integer goalsScored) {
        this.goalsScored = goalsScored;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(Integer cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public Integer getSaves() {
        return saves;
    }

    public void setSaves(Integer saves) {
        this.saves = saves;
    }

    public Integer getTackles() {
        return tackles;
    }

    public void setTackles(Integer tackles) {
        this.tackles = tackles;
    }

    public Integer getPassingAccuracy() {
        return passingAccuracy;
    }

    public void setPassingAccuracy(Integer passingAccuracy) {
        this.passingAccuracy = passingAccuracy;
    }

    public Integer getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(Integer minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Integer getRedCards() {
        return redCards;
    }

    public void setRedCards(Integer redCards) {
        this.redCards = redCards;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(Integer matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public Integer getPenaltiesSaved() {
        return penaltiesSaved;
    }

    public void setPenaltiesSaved(Integer penaltiesSaved) {
        this.penaltiesSaved = penaltiesSaved;
    }

    public Integer getPenaltiesScored() {
        return penaltiesScored;
    }

    public void setPenaltiesScored(Integer penaltiesScored) {
        this.penaltiesScored = penaltiesScored;
    }

    public Integer getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Integer bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public Integer getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Integer fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Integer getTotalCompensation() {
        return totalCompensation;
    }

    public void setTotalCompensation(Integer totalCompensation) {
        this.totalCompensation = totalCompensation;
    }

    // Helper method to calculate final compensation
    public void calculateTotalCompensation() {
        // Initialize if null
        if (this.bonusAmount == null) {
            this.bonusAmount = 0;
        }
        if (this.fineAmount == null) {
            this.fineAmount = 0;
        }
        this.totalCompensation = this.baseCompensation + this.bonusAmount - this.fineAmount;
    }
}
