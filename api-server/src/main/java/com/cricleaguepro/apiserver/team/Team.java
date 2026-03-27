package com.cricleaguepro.apiserver.team;

import com.cricleaguepro.apiserver.league.League;
import com.cricleaguepro.apiserver.match.Match;
import com.cricleaguepro.apiserver.player.Player;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String shortName;
    private String logoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    @JsonBackReference
    private League league;

    // ADD THIS 👇 Players association
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    @OneToMany(mappedBy = "team1")
    @JsonManagedReference
    private List<Match> homeMatches = new ArrayList<>();

    @OneToMany(mappedBy = "team2")
    @JsonManagedReference
    private List<Match> awayMatches = new ArrayList<>();

    // constructors
    protected Team() {}

    public Team(String name, String shortName, String logoUrl, League league) {
        this.name = name;
        this.shortName = shortName;
        this.logoUrl = logoUrl;
        this.league = league;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getShortName() { return shortName; }
    public String getLogoUrl() { return logoUrl; }
    public League getLeague() { return league; }

    // ADD THESE 👇
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public void setName(String name) { this.name = name; }
    public void setShortName(String shortName) { this.shortName = shortName; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public void setLeague(League league) { this.league = league; }
}
