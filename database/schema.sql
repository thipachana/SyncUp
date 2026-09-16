CREATE TABLE benutzer (
    benutzer_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    passwort VARCHAR(255) NOT NULL,
    rolle VARCHAR(50) NOT NULL
);
CREATE TABLE kalender (
    kalender_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    besitzer_id BIGINT NOT NULL,
    beschreibung VARCHAR(255),
    FOREIGN KEY (besitzer_id) REFERENCES benutzer(benutzer_id)
);
CREATE TABLE termin (
    termin_id BIGSERIAL PRIMARY KEY,
    titel VARCHAR(100) NOT NULL,
    beschreibung VARCHAR(255),
    datum DATE NOT NULL,
    startzeit TIME NOT NULL,
    endzeit TIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    kalender_id BIGINT NOT NULL,
    FOREIGN KEY (kalender_id) REFERENCES kalender(kalender_id)
);
CREATE TABLE terminanfrage (
    terminanfrage_id BIGSERIAL PRIMARY KEY,
    dauer INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL
);
CREATE TABLE terminanfrage_benutzer (
    terminanfrage_id BIGINT NOT NULL,
    benutzer_id BIGINT NOT NULL,
    PRIMARY KEY (terminanfrage_id, benutzer_id),
    FOREIGN KEY (terminanfrage_id) REFERENCES terminanfrage(terminanfrage_id),
    FOREIGN KEY (benutzer_id) REFERENCES benutzer(benutzer_id)
);
CREATE TABLE ressource (
    ressourcen_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    typ VARCHAR(100) NOT NULL,
    kapazitaet INTEGER NOT NULL,
    verfuegbarkeit BOOLEAN NOT NULL
);
CREATE TABLE buchung (
  buchung_id BIGSERIAL PRIMARY KEY,
  termin_id BIGINT NOT NULL,
  ressourcen_id BIGINT NOT NULL,
  zeitraum VARCHAR(100) NOT NULL,
  status VARCHAR(50) NOT NULL,
  FOREIGN KEY (termin_id) REFERENCES termin(termin_id),
  FOREIGN KEY (ressourcen_id) REFERENCES ressource(ressourcen_id)
);