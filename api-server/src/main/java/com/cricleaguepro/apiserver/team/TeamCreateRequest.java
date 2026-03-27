package com.cricleaguepro.apiserver.team;

public class TeamCreateRequest {
    private String name;
    private String shortName;
    private String logoUrl;
    private Long leagueId;

    // Default constructor
    public TeamCreateRequest() {}

    public TeamCreateRequest(String name, String shortName, String logoUrl, Long leagueId) {
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.leagueId = leagueId;
    }

    // Getters
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public String getLogoUrl() { return logoUrl; }
    public Long getLeagueId() { return leagueId; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setLeagueId(Long leagueId) { this.leagueId = leagueId; }
}
