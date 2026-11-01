package de.ghostnet.application;

import java.util.List;
import java.util.Optional;

import de.ghostnet.domain.Person;
import de.ghostnet.persistence.PersonRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@RequestScoped
public class PersonService {

    @Inject
    private PersonRepository personRepository;

    @Inject
    private EntityManager entityManager;

    public List<Person> alleLaden() {
        return personRepository.alleLaden();
    }

    public Optional<Person> anhandIdLaden(Long id) {
        return personRepository.anhandIdLaden(id);
    }

    public Person kontaktdatenAktualisieren(
            Long personId,
            String name,
            String telefon) {

        if (personId == null) {
            throw new IllegalArgumentException(
                    "Bitte zuerst eine Person auswählen.");
        }

        EntityTransaction transaktion = entityManager.getTransaction();

        try {
            transaktion.begin();

            Person person = personRepository.anhandIdLaden(personId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Die ausgewählte Person wurde nicht gefunden."));

            person.kontaktdatenSetzen(name, telefon);

            transaktion.commit();
            return person;

        } catch (RuntimeException fehler) {
            if (transaktion.isActive()) {
                transaktion.rollback();
            }

            throw fehler;
        }
    }

    public Person anlegen(
            String benutzername,
            String kontaktname,
            String telefon) {

        if (benutzername == null || benutzername.isBlank()) {
            throw new IllegalArgumentException(
                    "Bitte einen Benutzernamen eingeben.");
        }

        String name = benutzername.trim();

        if (name.length() > 50) {
            throw new IllegalArgumentException(
                    "Der Benutzername darf höchstens 50 Zeichen lang sein.");
        }

        Person person = new Person(name);
        person.kontaktdatenSetzen(kontaktname, telefon);

        EntityTransaction transaktion = entityManager.getTransaction();

        try {
            transaktion.begin();
            personRepository.speichern(person);
            transaktion.commit();

            return person;
        } catch (RuntimeException fehler) {
            if (transaktion.isActive()) {
                transaktion.rollback();
            }

            throw fehler;
        }
    }
}