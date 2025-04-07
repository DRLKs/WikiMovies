package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "crew_personaje")
public class CrewPersonaje {
    @EmbeddedId
    private CrewPersonajeId id;

    @MapsId("crewIdCrew")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Crew_id_crew", nullable = false)
    private Crew crewIdCrew;

    @MapsId("personajeIdMiembro")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "Personaje_id_miembro", nullable = false)
    private com.app.web.entity.Personaje personajeIdMiembro;

}