package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "crew_trabajo")
public class CrewTrabajo {
    @EmbeddedId
    private CrewTrabajoId id;

    @MapsId("crewIdCrew")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Crew_id_crew", nullable = false)
    private Crew crewIdCrew;

    @MapsId("trabajoIdTrabajo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Trabajo_id_trabajo", nullable = false)
    private com.app.web.entity.Trabajo trabajoIdTrabajo;

}