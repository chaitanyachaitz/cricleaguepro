package com.cricleaguepro.apiserver.player;

import com.cricleaguepro.apiserver.team.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 10)
    private String role; // "BAT", "AR", "BOWL"

    @Column(name = "is_captain")
    private Boolean isCaptain = false;

    @Column(name = "is_vice_captain")
    private Boolean isViceCaptain = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @JsonBackReference
    private Team team;

    // Default constructor
    protected Player() {}

    public Player(String name, String role, Boolean isCaptain, Boolean isViceCaptain, Team team) {
        this.name = name;
        this.role = role;
        this.isCaptain = isCaptain;
        this.isViceCaptain = isViceCaptain;
        this.team = team;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public Boolean getIsCaptain() { return isCaptain; }
    public Boolean getIsViceCaptain() { return isViceCaptain; }
    public Team getTeam() { return team; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setRole(String role) { this.role = role; }
    public void setIsCaptain(Boolean isCaptain) { this.isCaptain = isCaptain; }
    public void setIsViceCaptain(Boolean isViceCaptain) { this.isViceCaptain = isViceCaptain; }
    public void setTeam(Team team) { this.team = team; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", isCaptain=" + isCaptain +
                ", isViceCaptain=" + isViceCaptain +
                '}';
    }
}
