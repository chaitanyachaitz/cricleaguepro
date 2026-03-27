package com.cricleaguepro.apiserver.match;

import com.cricleaguepro.apiserver.team.Team;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)  // Changed from LAZY
    @JoinColumn(name = "team1_id")
    @JsonManagedReference
    private Team team1;

    @ManyToOne(fetch = FetchType.EAGER)  // Changed from LAZY
    @JoinColumn(name = "team2_id")
    @JsonManagedReference
    private Team team2;


    @Column(nullable = false)
    private LocalDateTime scheduledStart;

    private LocalDateTime actualStart;
    private String venue;
    private String status;  // "Scheduled", "Live", "Completed"

    // constructors
    protected Match() {}

    public Match(Team team1, Team team2, LocalDateTime scheduledStart, String venue) {
        this.team1 = team1;
        this.team2 = team2;
        this.scheduledStart = scheduledStart;
        this.venue = venue;
        this.status = "Scheduled";
    }

    // getters/setters
    public Long getId() { return id; }
    public Team getTeam1() { return team1; }
    public Team getTeam2() { return team2; }
    public LocalDateTime getScheduledStart() { return scheduledStart; }
    public String getVenue() { return venue; }
    public String getStatus() { return status; }
}
