package dao;

import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PrestitoDAO {

    private final EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestito prestito) {
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        em.persist(prestito);
        transaction.commit();

        System.out.println("Prestito salvato correttamente!");
    }

    public List<Prestito> findPrestitiAttiviByNumeroTessera(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE p.utente.numeroTessera = :numeroTessera " +
                        "AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        );

        query.setParameter("numeroTessera", numeroTessera);

        return query.getResultList();
    }

    public List<Prestito> findPrestitiScaduti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE p.dataRestituzionePrevista < :oggi " +
                        "AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        );

        query.setParameter("oggi", LocalDate.now());

        return query.getResultList();
    }

    public void restituisciPrestito(Long idPrestito) {
        Prestito prestito = em.find(Prestito.class, idPrestito);

        EntityTransaction transaction = em.getTransaction();

        transaction.begin();
        prestito.setDataRestituzioneEffettiva(LocalDate.now());
        transaction.commit();

        System.out.println("Prestito restituito correttamente!");
    }

    public List<Prestito> findUltimiPrestitiByNumeroTessera(String numeroTessera) {

        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE p.utente.numeroTessera = :numeroTessera " +
                        "ORDER BY p.dataInizioPrestito DESC",
                Prestito.class
        );

        query.setParameter("numeroTessera", numeroTessera);

        return query.getResultList();
    }
}