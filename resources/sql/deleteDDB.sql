DROP TABLE Stock_info;
DROP TABLE Compte_achats;
DROP TABLE Compte_info;
DROP TABLE Stock;
DROP TABLE Compte;
DROP TABLE Ingredient;
DROP TABLE Stock_achats;
DROP TABLE Produit;


DROP SEQUENCE sequence_idCompte;

DROP PROCEDURE compte_creer;
DROP PROCEDURE produit_inserer;
DROP PROCEDURE produit_modifier;
DROP PROCEDURE compte_crediter;
DROP PROCEDURE compte_supprimer;
DROP PROCEDURE compte_acheter;
DROP PROCEDURE stock_acheter;
DROP FUNCTION stock_vendre;
DROP PROCEDURE maj_info_compte;
DROP FUNCTION recupererDateAvant;
DROP FUNCTION recupererDateApres;
DROP PROCEDURE clearDatesInutilesCompte;
DROP PROCEDURE compte_init;
DROP PROCEDURE produit_supprimer;