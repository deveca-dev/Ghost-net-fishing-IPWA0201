BEGIN;

ALTER TABLE person
    ADD COLUMN name varchar(120),
    ADD COLUMN telefon varchar(40);

ALTER TABLE person
    ADD CONSTRAINT person_kontakt_gueltig CHECK (
        (name IS NULL AND telefon IS NULL)
        OR (
            name IS NOT NULL
            AND telefon IS NOT NULL
            AND length(btrim(name)) > 0
            AND length(btrim(telefon)) > 0
        )
    );

COMMIT;