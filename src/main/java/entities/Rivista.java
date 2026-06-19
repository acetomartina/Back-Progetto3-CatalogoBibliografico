package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "riviste")
public class Rivista extends ElementoCatalogo{
    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Rivista() {
    }

    public Rivista(String codiceISBN, String titolo,
                   int annoPubblicazione, int numeroPagine,
                   Periodicita periodicita) {

        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "id=" + id +
                ", codiceISBN='" + codiceISBN + '\'' +
                ", titolo='" + titolo + '\'' +
                ", periodicita=" + periodicita +
                '}';
    }
}
