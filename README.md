Il database è stato progettato utilizzando DrawSQL e segue una struttura relazionale basata sull'ereditarietà JPA tramite strategia JOINED.

Tabelle principali
- Utenti
La tabella utenti contiene le informazioni degli utenti registrati nella biblioteca.

Relazioni:

Un utente può avere più prestiti.

Un utente può avere più elementi nella wishlist.

- Elementi Catalogo
La tabella elementi_catalogo rappresenta la superclasse astratta del catalogo bibliografico.

Relazioni:

Un elemento può essere presente in molti prestiti.

Un elemento può essere presente in molte wishlist.

Libri
La tabella libri estende elementi_catalogo.
	
id	PK/FK verso elementi_catalogo
autore	Autore del libro
genere	Genere letterario
Relazione:

Eredita tutti gli attributi di elementi_catalogo.

Riviste
La tabella riviste estende elementi_catalogo.

periodicita	Settimanale, Mensile o Semestrale


Eredita tutti gli attributi di elementi_catalogo.

Prestiti
La tabella prestiti gestisce i prestiti degli elementi del catalogo.

Relazioni:

Molti prestiti possono appartenere allo stesso utente.

Molti prestiti possono riferirsi allo stesso elemento catalogo.

Lista Desideri
La tabella lista_desideri rappresenta la wishlist personale degli utenti.

Relazioni:

Un utente può salvare più elementi.

Un elemento può essere presente nelle wishlist di più utenti.

Relazioni implementate
Utente ↔ Prestito
Utente (1) ------ (N) Prestito
Un utente può effettuare più prestiti.

ElementoCatalogo ↔ Prestito
ElementoCatalogo (1) ------ (N) Prestito
Un elemento può essere prestato più volte nel tempo.

Utente ↔ ListaDesideri
Utente (1) ------ (N) ListaDesideri
Ogni utente possiede una propria wishlist.

ElementoCatalogo ↔ ListaDesideri
ElementoCatalogo (1) ------ (N) ListaDesideri
Lo stesso elemento può essere salvato nelle wishlist di utenti diversi.


L'ereditarietà è stata implementata tramite strategia JPA:

@Inheritance(strategy = InheritanceType.JOINED)
in modo da mantenere separati gli attributi specifici di libri e riviste evitando colonne inutilizzate.


