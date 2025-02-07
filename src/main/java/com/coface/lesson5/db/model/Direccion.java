package com.coface.lesson5.db.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "direcciones"
)
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direcciones_id_seq")
    @SequenceGenerator(name = "direcciones_id_seq", sequenceName = "direcciones_id_seq")
    private Long id;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "codigo_postal", nullable = false)
    private String codigoPostal;

    public Direccion() {
    }

    public Direccion(Long id, String direccion, String codigoPostal) {
        this.id = id;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }

    public Direccion(String direccion, String codigoPostal) {
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", codigoPostal='" + codigoPostal + '\'' +
                '}';
    }
}
