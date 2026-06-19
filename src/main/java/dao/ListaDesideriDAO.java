package dao;

import entities.ElementoCatalogo;
import entities.ListaDesideri;
import entities.Utente;
import jakarta.persistence.*;

import java.util.List;

public class ListaDesideriDAO {

    private final EntityManager em;

    public ListaDesideriDAO(EntityManager em) {
        this.em = em;
    }

    public void aggiungi(Utente utente, ElementoCatalogo elemento) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(new ListaDesideri(utente, elemento));
        transaction.commit();

        System.out.println("Elemento aggiunto alla wishlist!");
    }

    public List<ListaDesideri> findByUtente(Utente utente) {
        TypedQuery<ListaDesideri> query = em.createQuery(
                "SELECT l FROM ListaDesideri l WHERE l.utente = :utente",
                ListaDesideri.class
        );

        query.setParameter("utente", utente);

        return query.getResultList();
    }

    public void rimuovi(Long idWishlist) {
        ListaDesideri elemento = em.find(ListaDesideri.class, idWishlist);

        if (elemento == null) {
            System.out.println("Elemento non trovato.");
            return;
        }

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.remove(elemento);
        transaction.commit();

        System.out.println("Elemento rimosso dalla wishlist!");
    }
}