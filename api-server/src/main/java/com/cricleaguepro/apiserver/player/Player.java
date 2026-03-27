package com.cricleaguepro.apiserver.player;

import com.cricleaguepro.apiserver.team.Team;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    private String battingStyle;  // "Right Hand Bat", "Left Hand Bat"
    private String bowlingStyle;  // "Right Arm Fast", "Left Arm Spin"
    private String role;          // "Batsman", "Bowler", "All-rounder", "Wicketkeeper"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @JsonBackReference
    private Team team;

    // constructors
    protected Player() {}

    public Player(String fullName, String battingStyle, String bowlingStyle, String role, Team team) {
        this.fullName = fullName;
        this.battingStyle = battingStyle;
        this.bowlingStyle = bowlingStyle;
        this.role = role;
        this.team = team;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public String getBattingStyle() { return battingStyle; }
    public String getBowlingStyle() { return bowlingStyle; }
    public String getRole() { return role; }
    public Team getTeam() { return team; }
}
