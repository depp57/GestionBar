INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (1, 'Hautière', 'Alain');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (2, 'Rouhling', 'Amicale');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (3, 'Philippe', 'Anne-L');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (4, 'Wagner', 'Bernard');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (5, 'Feicht', 'Fabrice');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (6, '1', 'Inconnue');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (7, 'Sp', 'Inter');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (8, 'Hamm', 'Kévin');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (9, 'Majon', 'Ltt');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (10, 'Vintier', 'Maxime');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (11, 'Schuster', 'Patrick');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (12, 'Europapark', 'Sortie');
INSERT INTO SYSTEM.COMPTE (IDCOMPTE, NOM, PRENOM) VALUES (13, 'Schmitt', 'Yannick');

INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Becker pils', 0.49, 0.60, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Bitburger', 0.00, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Bouteille de crément', 7.00, 7.50, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Bouteille de rosé', 4.00, 5.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Bouteille de rouge', 3.91, 4.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Bouteille de vin blanc', 5.32, 6.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Cacahuètes', 0.50, 0.50, 'Aperitif');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Chips', 0.50, 0.50, 'Aperitif');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Coca cola', 0.55, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Curly', 0.50, 0.50, 'Aperitif');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Desperados', 1.09, 1.50, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Eau plate', 0.56, 0.50, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Eau pétillante', 0.00, 0.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Fanta', 0.55, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Fuze-tea', 0.69, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Picon', 10.50, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Verre de crément', 1.40, 1.50, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Verre de rosé', 0.80, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Verre de rouge', 0.78, 0.80, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Verre de vin blanc', 1.00, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Passoa orange', 0.00, 1.00, 'Boisson');
INSERT INTO SYSTEM.PRODUIT (INTITULEPRODUIT, PRIXACHAT, PRIXVENTE, TYPEPRODUIT) VALUES ('Ricard', 0.00, 1.00, 'Boisson');


INSERT INTO SYSTEM.INGREDIENT (INTITULEPRODUITPERE, INTITULEPRODUITCONSOMME) VALUES ('Picon', 'Becker pils');


INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Becker pils', 94);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Bitburger', 50);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Bouteille de crément', 9);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Bouteille de rosé', 9);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Bouteille de rouge', 9);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Bouteille de vin blanc', 4);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Chips', 16);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Coca cola', 96);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Desperados', 63);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Eau pétillante', 90);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Eau plate', 91);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Fanta', 49);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Fuze-tea', 31);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Picon', 30);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Verre de crément', 5);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Verre de rosé', 5);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Verre de rouge', 5);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Passoa orange', 16);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Verre de vin blanc', 3);
INSERT INTO SYSTEM.STOCK (INTITULEPRODUIT, QUANTITE) VALUES ('Ricard', 5);


INSERT INTO SYSTEM.STOCK_INFO (TOTALACHAT, TOTALVENTE) VALUES (281.35, 149.80);


INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (1, TO_DATE('2019-09-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0.00, 0.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-07-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0.00, 6.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-07-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -6.00, 10.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-07-16 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -16.00, 5.20, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -21.20, 5.10, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-07-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -26.30, 0.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-08-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -26.30, 7.30, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-08-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -33.60, 16.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-08-03 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -49.60, 3.60, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-08-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -53.20, 1.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -54.20, 12.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -66.20, 13.30, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -79.50, 7.50, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -87.00, 19.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-13 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -106.00, 2.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -108.00, 8.60, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -116.60, 1.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -117.60, 4.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -121.60, 8.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-10-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -129.60, 2.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-11-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -131.60, 4.20, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (2, TO_DATE('2019-11-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -135.80, 10.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (3, TO_DATE('2019-07-08 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 0.00, 1.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (3, TO_DATE('2019-07-29 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -1.00, 2.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (3, TO_DATE('2019-08-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -3.00, 1.00, 0.00);
INSERT INTO SYSTEM.COMPTE_INFO (IDCOMPTE, DATEINFO, RESTE, MOINS, PLUS) VALUES (3, TO_DATE('2020-11-08 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), -4.00, 0.00, 4.00);


INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (1, TO_DATE('2019-09-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Coca cola', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 4);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-11 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 6);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Coca cola', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-16 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-16 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-16 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-16 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-07-20 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 5);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 10);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 5);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-03 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-03 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-05 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 9);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-08-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fanta', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 5);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Coca cola', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Picon', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de rosé', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de rouge', 0.80, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-09-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de vin blanc', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 4);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau pétillante', 0.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de vin blanc', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 5);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Coca cola', 1.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de vin blanc', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fanta', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-12 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 6);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-13 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-14 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bouteille de vin blanc', 6.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 4);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-24 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 8);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-10-28 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-11-06 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Becker pils', 0.60, 7);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-11-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Bitburger', 1.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-11-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Desperados', 1.50, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-11-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Eau plate', 0.50, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (2, TO_DATE('2019-11-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 3);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (3, TO_DATE('2019-07-08 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 1);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (3, TO_DATE('2019-07-29 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Verre de vin blanc', 1.00, 2);
INSERT INTO SYSTEM.COMPTE_ACHATS (IDCOMPTE, DATEACHAT, INTITULEPRODUIT, PRIXACHATUNITE, QUANTITE) VALUES (3, TO_DATE('2019-08-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), 'Fuze-tea', 1.00, 1);