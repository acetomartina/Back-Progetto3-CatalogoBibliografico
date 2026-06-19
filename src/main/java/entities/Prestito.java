package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestiti")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "elemento_id",nullable = false)
    private ElementoCatalogo elementoCatalogo;

    @ManyToOne
    @JoinColumn(name = "utente_id",nullable = false)
    private Utente utente;

    @Column(name = "data_inizio_prestito", nullable = false)
    private LocalDate dataInizioPrestito;

    @Column(name = "data_restituzione_prevista", nullable = false)
    private LocalDate dataRestituzionePrevista;

    @Column(name = "data_restituzione_effettiva")
    private LocalDate dataRestituzioneEffettiva;

    public Prestito() {
    }

    public Prestito(Utente utente,
                    ElementoCatalogo elementoCatalogo,
                    LocalDate dataInizioPrestito) {

        this.utente = utente;
        this.elementoCatalogo = elementoCatalogo;
        this.dataInizioPrestito = dataInizioPrestito;

        // restituzione prevista dopo 30 giorni
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);

        // all'inizio il libro non è ancora stato restituito
        this.dataRestituzioneEffettiva = null;
    }

    public Long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public ElementoCatalogo getElementoCatalogo() {
        return elementoCatalogo;
    }

    public LocalDate getDataInizioPrestito() {
        return dataInizioPrestito;
    }

    public LocalDate getDataRestituzionePrevista() {
        return dataRestituzionePrevista;
    }

    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setElementoCatalogo(ElementoCatalogo elementoCatalogo) {
        this.elementoCatalogo = elementoCatalogo;
    }

    public void setDataInizioPrestito(LocalDate dataInizioPrestito) {
        this.dataInizioPrestito = dataInizioPrestito;
    }

    public void setDataRestituzionePrevista(LocalDate dataRestituzionePrevista) {
        this.dataRestituzionePrevista = dataRestituzionePrevista;
    }

    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) {
        this.dataRestituzioneEffettiva = dataRestituzioneEffettiva;
    }

    @Override
    public String toString() {
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente.getNome() + " " + utente.getCognome() +
                ", elementoCatalogo=" + elementoCatalogo.getTitolo() +
                ", dataInizioPrestito=" + dataInizioPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataRestituzioneEffettiva +
                '}';
    }

}
