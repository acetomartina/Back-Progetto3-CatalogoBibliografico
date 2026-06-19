package dao;

import entities.ElementoCatalogo;
import entities.Libro;
import jakarta.persistence.*;

import java.util.List;

public class ElementoCatalogoDAO {

    private final EntityManager em;


    public ElementoCatalogoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(ElementoCatalogo elemento){
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(elemento);
        transaction.commit();

        System.out.println("Elemento salvato correttamente!");
    }

    public ElementoCatalogo findByISBN(String codiceISBN) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.codiceISBN = :codiceISBN",
                ElementoCatalogo.class
        );

        query.setParameter("codiceISBN", codiceISBN);

        return query.getSingleResult();
    }

    public List<ElementoCatalogo> findByAnnoPubblicazione(int anno){
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno",
                ElementoCatalogo.class
        );

        query.setParameter("anno", anno);

        return query.getResultList();
    }

    public List<ElementoCatalogo> findByTitolo(String titolo){
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)",
                ElementoCatalogo.class
        );

        query.setParameter("titolo", "%" + titolo + "%");

        return query.getResultList();
    }

    public List<Libro> findByAutore(String autore) {

        TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE LOWER(l.autore) LIKE LOWER(:autore)",
                Libro.class
        );

        query.setParameter("autore", "%" + autore + "%");

        return query.getResultList();
    }

    public void deleteByISBN(String codiceISBN){
        ElementoCatalogo elemento = findByISBN(codiceISBN);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.remove(elemento);
        transaction.commit();

        System.out.println("Elemento rimosso correttamente!");
    }

    public List<ElementoCatalogo> findAll() {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e",
                ElementoCatalogo.class
        );

        return query.getResultList();
    }

}
