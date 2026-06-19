package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "lista_desideri")
public class ListaDesideri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elemento_id", nullable = false)
    private ElementoCatalogo elementoCatalogo;

    @Column(name = "data_inserimento", nullable = false)
    private LocalDate dataInserimento;

    public ListaDesideri() {
    }

    public ListaDesideri(Utente utente, ElementoCatalogo elementoCatalogo) {
        this.utente = utente;
        this.elementoCatalogo = elementoCatalogo;
        this.dataInserimento = LocalDate.now();
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

    public LocalDate getDataInserimento() {
        return dataInserimento;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public void setElementoCatalogo(ElementoCatalogo elementoCatalogo) {
        this.elementoCatalogo = elementoCatalogo;
    }

    public void setDataInserimento(LocalDate dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    @Override
    public String toString() {
        return "ListaDesideri{" +
                "id=" + id +
                ", utente=" + utente.getNome() + " " + utente.getCognome() +
                ", elementoCatalogo=" + elementoCatalogo.getTitolo() +
                ", dataInserimento=" + dataInserimento +
                '}';
    }
}