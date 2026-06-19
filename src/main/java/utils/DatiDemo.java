package utils;

import dao.ElementoCatalogoDAO;
import dao.PrestitoDAO;
import dao.UtenteDAO;
import entities.*;

import java.time.LocalDate;

public class DatiDemo {

    public static void caricaDatiDemo(ElementoCatalogoDAO catalogoDAO,
                                      UtenteDAO utenteDAO,
                                      PrestitoDAO prestitoDAO) {

        // LIBRI MARVEL COMICS
        Libro civilWar = new Libro("ISBN001", "Civil War", 2006, 320, "Mark Millar", "Supereroi");
        Libro infinityGauntlet = new Libro("ISBN002", "Infinity Gauntlet", 1991, 256, "Jim Starlin", "Supereroi");
        Libro secretWars = new Libro("ISBN003", "Secret Wars", 1984, 336, "Jim Shooter", "Supereroi");
        Libro houseOfM = new Libro("ISBN004", "House of M", 2005, 224, "Brian Michael Bendis", "Supereroi");
        Libro oldManLogan = new Libro("ISBN005", "Old Man Logan", 2008, 216, "Mark Millar", "Supereroi");

        catalogoDAO.save(civilWar);
        catalogoDAO.save(infinityGauntlet);
        catalogoDAO.save(secretWars);
        catalogoDAO.save(houseOfM);
        catalogoDAO.save(oldManLogan);

        // RIVISTE
        Rivista avengersWeekly = new Rivista("RIV001", "Avengers Weekly", 2020, 80, Periodicita.SETTIMANALE);
        Rivista spiderManMagazine = new Rivista("RIV002", "Spider-Man Magazine", 2018, 90, Periodicita.MENSILE);
        Rivista xMenFiles = new Rivista("RIV003", "X-Men Files", 2019, 100, Periodicita.MENSILE);
        Rivista wakandaReview = new Rivista("RIV004", "Wakanda Review", 2021, 75, Periodicita.MENSILE);
        Rivista shieldReport = new Rivista("RIV005", "S.H.I.E.L.D. Report", 2022, 60, Periodicita.SEMESTRALE);

        catalogoDAO.save(avengersWeekly);
        catalogoDAO.save(spiderManMagazine);
        catalogoDAO.save(xMenFiles);
        catalogoDAO.save(wakandaReview);
        catalogoDAO.save(shieldReport);

        // UTENTI
        Utente martinaAdmin = new Utente(
                "Martina",
                "Aceto",
                LocalDate.of(1998, 7, 13),
                "ADMIN001"
        );

        Utente profGulin = new Utente(
                "Prof",
                "Gulin",
                LocalDate.of(1960, 3, 15),
                "T001"
        );

        utenteDAO.save(martinaAdmin);
        utenteDAO.save(profGulin);

        // PRESTITI DEMO
        Prestito prestitoAttivo = new Prestito(
                profGulin,
                civilWar,
                LocalDate.now()
        );

        Prestito prestitoScaduto = new Prestito(
                profGulin,
                infinityGauntlet,
                LocalDate.now().minusDays(40)
        );

        prestitoDAO.save(prestitoAttivo);
        prestitoDAO.save(prestitoScaduto);

        System.out.println("Dati demo caricati correttamente!");
    }
}