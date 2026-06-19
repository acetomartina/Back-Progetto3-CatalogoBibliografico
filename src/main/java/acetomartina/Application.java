package acetomartina;

import dao.ElementoCatalogoDAO;
import dao.PrestitoDAO;
import entities.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("CatalogoBibliografico");

        EntityManager em = emf.createEntityManager();

        ElementoCatalogoDAO catalogoDAO = new ElementoCatalogoDAO(em);
        PrestitoDAO prestitoDAO = new PrestitoDAO(em);

        Libro hp = new Libro(
                "978123456",
                "Harry Potter",
                1997,
                350,
                "J.K. Rowling",
                "Fantasy"
        );


        catalogoDAO.findByAutore("Rowling").forEach(System.out::println);





    }
}
