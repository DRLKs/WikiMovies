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
public class CrewPersonajeId implements java.io.Serializable {
    private static final long serialVersionUID = -6790937427614691078L;
    @Column(name = "Crew_id_crew", nullable = false)
    private Integer crewIdCrew;

    @Column(name = "Personaje_id_miembro", nullable = false)
    private Integer personajeIdMiembro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CrewPersonajeId entity = (CrewPersonajeId) o;
        return Objects.equals(this.crewIdCrew, entity.crewIdCrew) &&
                Objects.equals(this.personajeIdMiembro, entity.personajeIdMiembro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewIdCrew, personajeIdMiembro);
    }

}