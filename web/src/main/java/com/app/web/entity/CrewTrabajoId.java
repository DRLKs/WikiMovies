package com.app.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CrewTrabajoId implements java.io.Serializable {
    private static final long serialVersionUID = -4857248791238997507L;
    @Column(name = "Crew_id_crew", nullable = false)
    private Integer crewIdCrew;

    @Column(name = "Trabajo_id_trabajo", nullable = false)
    private Integer trabajoIdTrabajo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CrewTrabajoId entity = (CrewTrabajoId) o;
        return Objects.equals(this.crewIdCrew, entity.crewIdCrew) &&
                Objects.equals(this.trabajoIdTrabajo, entity.trabajoIdTrabajo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewIdCrew, trabajoIdTrabajo);
    }

}