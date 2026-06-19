package dao;

import entities.Utente;
import jakarta.persistence.*;

public class UtenteDAO {

    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(utente);
        transaction.commit();

        System.out.println("Utente salvato correttamente!");
    }

    public Utente findById(Long id) {
        return em.find(Utente.class, id);
    }

    public Utente findByNumeroTessera(String numeroTessera) {
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.numeroTessera = :numeroTessera",
                Utente.class
        );

        query.setParameter("numeroTessera", numeroTessera);

        return query.getSingleResult();
    }
}