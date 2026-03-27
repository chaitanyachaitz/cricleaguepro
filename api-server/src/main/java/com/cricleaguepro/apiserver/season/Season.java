package com.cricleaguepro.apiserver.season;

import com.cricleaguepro.apiserver.league.League;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seasons")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // "IPL 2026", "Season 1"

    private Integer year;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id")
    private League league;

    // constructors
    protected Season() {}

    public Season(String name, Integer year, LocalDate startDate, LocalDate endDate, League league) {
        this.name = name;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
        this.league = league;
    }

    // getters/setters
    public Long getId() { return id; }
    public String getName() { return name; }
    public Integer getYear() { return year; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public League getLeague() { return league; }
}
