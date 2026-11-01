package de.ghostnet.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String benutzername;

    @Column(length = 120)
    private String name;

    @Column(length = 40)
    private String telefon;

    protected Person() {
        // Für JPA erforderlich.
    }

    public Person(String benutzername) {
        this.benutzername = benutzername;
    }

    public void kontaktdatenSetzen(String name, String telefon) {
        if (name == null || name.isBlank()
                || name.trim().length() > 120) {
            throw new IllegalArgumentException(
                    "Bitte einen Namen mit höchstens 120 Zeichen eingeben."
            );
        }

        if (telefon == null || telefon.isBlank()
                || telefon.trim().length() > 40) {
            throw new IllegalArgumentException(
                    "Bitte eine Telefonnummer mit höchstens 40 Zeichen eingeben."
            );
        }

        this.name = name.trim();
        this.telefon = telefon.trim();
    }

    public void kontaktdatenPruefen() {
        if (name == null || name.isBlank()
                || telefon == null || telefon.isBlank()) {
            throw new IllegalArgumentException(
                    "Bitte zuerst Namen und Telefonnummer ergänzen."
            );
        }
    }

    public Long getId() {
        return id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public String getName() {
        return name;
    }

    public String getTelefon() {
        return telefon;
    }
}