package com.app.web.entity;

import com.app.web.dto.DTO;
import com.app.web.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario implements DTO<UsuarioDTO> {
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


    @OneToMany(mappedBy = "idUsuario")
    private Set<Lista> listas = new LinkedHashSet<>();

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

    /**
     *  ROL 0: Usuario normal
     * 	ROL 1: Usuario premium
     * 	ROL 2: Usuario editor de películas
     * 	ROL 3: Usuario editor de usuarios
     */
    @ColumnDefault("0")
    @Column(name = "rol")
    private Integer rol;

    @Column(name = "avatarUrl", length = 600)
    private String avatarUrl;

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
        } else {
            if( meses > 1 ){
                sb.append(meses).append(" meses ");
            }else if( meses > 0 ){
                sb.append(meses).append(" mes ");
            } else {
                if( dias > 1 ){
                    sb.append(dias).append(" días ");
                }else if( dias > 0 ){
                    sb.append(dias).append(" día ");
                }else {
                    sb.append("Usuario nuevo");
                }
            }
        }

        return sb.toString();
    }

    public boolean sigueA(Usuario usuario){
        return usuario.getSeguidores().contains(this);
    }

    public boolean sigueA(Set<Integer> seguidoresUsuario){
        return seguidoresUsuario.contains(this.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public UsuarioDTO toDTO() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(this.id);
        usuarioDTO.setNombreUsuario(this.nombreUsuario);
        usuarioDTO.setBiografia(this.biografia);
        usuarioDTO.setAvatar(this.avatarUrl);
        usuarioDTO.setTiempoRegistrado(this.getTiempoRegistrado());
        usuarioDTO.setGenero(this.getGenero());
        usuarioDTO.setRol(this.getRol());
        usuarioDTO.setNacimientoFecha(this.getNacimientoFecha());

        HashSet<Integer> seguidoresId = new HashSet();
        for( Usuario usuario : this.getSeguidores() ){
            seguidoresId.add(usuario.getId());
        }
        usuarioDTO.setSeguidoresIds(seguidoresId) ;

        return usuarioDTO;
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