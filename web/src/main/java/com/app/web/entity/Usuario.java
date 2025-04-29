package com.app.web.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre_usuario", length = 100)
    private String nombreUsuario;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "contrasena_hash")
    private String contrasenaHash;

    @Column(name = "rol")
    private Integer rol;

    @Column(name = "avatarUrl", length = 200)
    private String avatarUrl;

    @OneToMany(mappedBy = "idUsuario")
    private Set<Lista> listas = new LinkedHashSet<>();


    @ManyToMany
    @JoinTable(name = "favoritos",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_pelicula"))
    private Set<Pelicula> peliculasFavoritas = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario1"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario2"))
    private Set<Usuario> seguidores = new LinkedHashSet<>();

    @Column(name = "biografia", length = 500)
    private String biografia;

    @Column(name = "genero")
    private Integer genero;

    @Column(name = "nacimientoFecha")
    private LocalDate nacimientoFecha;

    @Column(name = "creacionCuentaFecha", nullable = false)
    private LocalDate creacionCuentaFecha;

    public String getTiempoRegistrado(){

        Period periodoPasado = Period.between(getCreacionCuentaFecha(), LocalDate.now());

        StringBuilder sb = new StringBuilder();
        int anyos = periodoPasado.getYears();
        int meses = periodoPasado.getMonths();
        int dias = periodoPasado.getDays();

        if( anyos > 1 ){
            sb.append(anyos).append(" años ");
        }else if( anyos > 0 ){
            sb.append( anyos ).append(" año ");
        }

        if( meses > 1 ){
            sb.append(meses).append(" meses ");
        }else if( meses > 0 ){
            sb.append(meses).append(" mes ");
        }

        if( dias > 1 ){
            sb.append(dias).append(" dias ");
        }else if( dias > 0 ){
            sb.append(dias).append(" dia ");
        }else if( dias <= 0 && meses <= 0 && anyos <= 0  ){
            sb.append("Usuario nuevo");
        }


        return sb.toString();
    }


    /*
    @ManyToMany
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario1"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario1")
    )
    private Set<Usuario> seguidores = new LinkedHashSet<>();

    // Usuarios a los que este usuario sigue
    @ManyToMany
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name = "idUsuario2"),
            inverseJoinColumns = @JoinColumn(name = "idUsuario2")
    )
    private Set<Usuario> seguidos = new LinkedHashSet<>();

    * */

}