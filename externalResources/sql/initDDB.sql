CREATE TABLE Compte (
    idCompte number(4) PRIMARY KEY,
    nom varchar(20),
    prenom varchar(20)
);

CREATE TABLE Produit (
    intituleProduit varchar(30) PRIMARY KEY,
    prixAchat number(5,2),
    prixVente number(5,2),
    typeProduit varchar(10) CHECK(typeProduit IN('Boisson', 'Aperitif'))
);

CREATE TABLE Ingredient (
    intituleProduitPere varchar(30) PRIMARY KEY ,
    intituleProduitConsomme varchar(30),
    CONSTRAINT fk_ingredient1 FOREIGN KEY (intituleProduitPere) REFERENCES Produit(intituleProduit) ON DELETE CASCADE,
    CONSTRAINT fk_ingredient2 FOREIGN KEY (intituleProduitConsomme) REFERENCES Produit(intituleProduit) ON DELETE CASCADE
);

CREATE TABLE Stock (
    intituleProduit varchar(30) PRIMARY KEY,
    quantite number(4),
    CONSTRAINT fk_stock FOREIGN KEY (intituleProduit) REFERENCES Produit(intituleProduit) ON DELETE CASCADE
);

CREATE TABLE Stock_info (
    totalAchat number(10,2),
    totalVente number(10,2)
);

CREATE TABLE Compte_achats (
    idCompte number(4),
    dateAchat date,
    intituleProduit varchar(30),
    prixAchatUnite number(4,2),
    quantite number(4),
    CONSTRAINT pk_compte_achats PRIMARY KEY (idCompte, dateAchat, intituleProduit),
    CONSTRAINT fk_compte_achats1 FOREIGN KEY (idCompte) REFERENCES Compte(idCompte),
    CONSTRAINT fk_compte_achats2 FOREIGN KEY (intituleProduit) REFERENCES Produit(intituleProduit) ON DELETE CASCADE
);

CREATE TABLE Compte_info (
    idCompte number(4),
    dateInfo date,
    reste number(10, 2),
    moins number(10, 2),
    plus number(10, 2),
    CONSTRAINT pk_compte_info PRIMARY KEY (idCompte, dateInfo),
    CONSTRAINT fk_compte_info FOREIGN KEY (idCompte) REFERENCES Compte(idCompte) ON DELETE CASCADE
);

CREATE TABLE Stock_achats (
    intituleProduit varchar(30),
    dateAchat date,
    quantite number(4),
    prixUnite number(4,2),
    CONSTRAINT pk_stock_achats PRIMARY KEY (intituleProduit, dateAchat),
    CONSTRAINT fk_stock_achats FOREIGN KEY (intituleProduit) REFERENCES Produit(intituleProduit) ON DELETE CASCADE
);

CREATE SEQUENCE sequence_idCompte;

CREATE FUNCTION recupererDateAvant(p_idCompte number, p_date date) RETURN date
IS
    v_dateAvant date;
    v_delta number;
    v_minDelta number;
    CURSOR curseur_Compte_info IS SELECT dateInfo FROM compte_info WHERE p_idCompte = idCompte;
BEGIN
    v_minDelta := 999999;
    v_dateAvant := NULL;

    FOR dateCourante IN curseur_Compte_info LOOP
        v_delta := (p_date - dateCourante.dateInfo);

        IF (v_delta > 0 AND v_delta < v_minDelta) THEN
            v_minDelta := v_delta;
            v_dateAvant := dateCourante.dateInfo;
        END IF;
    END LOOP ;

    RETURN v_dateAvant;
END;

CREATE FUNCTION recupererDateApres(p_idCompte number, p_date date) RETURN date
IS
    v_dateApres date;
    v_delta number;
    v_minDelta number;
    CURSOR curseur_Compte_info IS SELECT dateInfo FROM compte_info WHERE p_idCompte = idCompte;
BEGIN
    v_minDelta := 999999;
    v_dateApres := NULL;

    FOR dateCourante IN curseur_Compte_info LOOP
        v_delta := dateCourante.dateInfo - p_date;
        IF (v_delta > 0 AND v_delta < v_minDelta) THEN
            v_minDelta := v_delta;
            v_dateApres := dateCourante.dateInfo;
        END IF;
    END LOOP ;

    RETURN v_dateApres;
END;

CREATE PROCEDURE maj_info_compte(p_idCompte number, p_date date)
IS
    v_dateSuivante date;
    v_total number(10, 2);
BEGIN
    IF (p_date IS NOT NULL) THEN
        v_dateSuivante := recupererDateApres(p_idCompte, p_date);
        IF (v_dateSuivante IS NOT NULL) THEN
            SELECT reste + plus - moins INTO v_total FROM Compte_info
            WHERE TO_CHAR(p_date, 'DD/MM/YY') = TO_CHAR(dateInfo, 'DD/MM/YY') AND idCompte = p_idCompte;

            UPDATE Compte_info SET reste = v_total WHERE dateInfo = v_dateSuivante AND idCompte = p_idCompte;
            maj_info_compte(p_idCompte, v_dateSuivante);
        END IF;
    END IF;
END;


--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
--------------------- APPELS EN DEHORS DU SGBD ---------------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>

CREATE OR REPLACE PROCEDURE compte_creer(p_nom varchar, p_prenom varchar)
IS
    v_check number(1);
    v_idCompte number(4);
BEGIN
    SELECT Count(*) INTO v_check FROM Compte
    WHERE p_nom = nom AND p_prenom = prenom;

    IF (v_check > 0) THEN
        RAISE_APPLICATION_ERROR(-20001, p_prenom || ' ' || p_nom || ' possède déjà un compte');
    END IF;

    v_idCompte := sequence_idCompte.nextval;

    INSERT INTO Compte VALUES (v_idCompte, p_nom, p_prenom);
END;

CREATE PROCEDURE produit_inserer(p_intituleProduit varchar, p_prixAchat number, p_prixVente number, p_typeProduit varchar, p_ingredient varchar)
IS
    v_check varchar(30);
    e_produitDejaExistant exception;
    e_typeProduitIncorrect exception;
BEGIN
    SELECT COUNT(*) INTO v_check FROM Produit
    WHERE intituleProduit = p_intituleProduit;

    IF (v_check > 0) THEN
        RAISE e_produitDejaExistant;
    END IF;

    IF p_typeProduit != 'Boisson' AND p_typeProduit != 'Aperitif' THEN
        RAISE e_typeProduitIncorrect;
    END IF;

    INSERT INTO Produit VALUES (p_intituleProduit, p_prixAchat, p_prixVente, p_typeProduit);

    IF (p_ingredient != 'null') THEN
        INSERT INTO Ingredient VALUES (p_intituleProduit, p_ingredient);
    END IF;

END;

CREATE OR REPLACE PROCEDURE produit_modifier(p_intituleProduitAvant varchar, p_intituleProduitApres varchar,
                                  p_prixAchat number, p_prixVente number, p_typeProduit varchar, p_ingredient varchar)
IS
    e_produitInexistant exception;
    e_typeProduitIncorrect exception;
    v_check number(1);
BEGIN
    SELECT COUNT(*) INTO v_check FROM Produit
    WHERE intituleProduit = p_intituleProduitAvant;

    IF (v_check = 0) THEN
        RAISE e_produitInexistant;
    END IF;

    IF p_typeProduit != 'Boisson' AND p_typeProduit != 'Aperitif' THEN
        RAISE e_typeProduitIncorrect;
    END IF;

    UPDATE Produit SET intituleProduit = p_intituleProduitApres, prixAchat = p_prixAchat,
                       prixVente = p_prixVente, typeProduit = p_typeProduit
    WHERE intituleProduit = p_intituleProduitAvant;

    IF (p_ingredient = 'Aucun') THEN
        DELETE FROM Ingredient WHERE intituleProduitPere = p_intituleProduitAvant;
    ELSE
        SELECT count(*) into v_check from Ingredient where intituleProduitPere = p_intituleProduitAvant;
        IF (v_check > 0) THEN
            UPDATE Ingredient SET intituleProduitPere = p_intituleProduitApres, intituleProduitConsomme = p_ingredient
                WHERE intituleProduitPere = p_intituleProduitAvant;
        ELSE
            INSERT INTO Ingredient VALUES (p_intituleProduitApres, p_ingredient);
        END IF;
    END IF;
END;

CREATE PROCEDURE compte_crediter(p_idCompte number, p_valeur number, p_date date)
IS
    v_idCompte2 number(4);
    v_dateAvant date;
    e_compteInexistant exception;
BEGIN
    SELECT NVL(MAX(idCompte), -1) INTO v_idCompte2 FROM Compte_info
    WHERE idCompte = p_idCompte AND TO_CHAR(dateInfo, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY');

    IF (v_idCompte2 = -1) THEN
        INSERT INTO Compte_info VALUES (p_idCompte, p_date, 0, 0, p_valeur);
    ELSE
        UPDATE Compte_info SET plus = plus + p_valeur
        WHERE idCompte = p_idCompte AND TO_CHAR(dateInfo, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY');
    END IF;

    v_dateAvant := recupererDateAvant(p_idCompte, p_date);

    IF (v_dateAvant IS NULL) THEN
        v_dateAvant := p_date;
    END IF;

    maj_info_compte(p_idCompte, v_dateAvant);
EXCEPTION
    when no_data_found THEN RAISE e_compteInexistant;
END;

CREATE PROCEDURE compte_supprimer(p_idCompte number)
IS
BEGIN
    DELETE FROM Compte_info WHERE idCompte = p_idCompte;
    DELETE FROM Compte_achats WHERE idCompte = p_idCompte;
    DELETE FROM Compte WHERE idCompte = p_idCompte;
END;

CREATE PROCEDURE stock_acheter(p_intituleProduit varchar, p_quantite number, p_gratuit number)
IS
    v_prix number(10,2);
    v_check number(1);
    e_produitInexistant exception;
    e_quantiteNegative exception;
BEGIN
    IF (p_quantite < 0) THEN
        RAISE e_quantiteNegative;
    END IF;

    SELECT COUNT(*) INTO v_check FROM Produit WHERE intituleProduit = p_intituleProduit;

    IF (v_check = 0) THEN
        RAISE e_produitInexistant;
    END IF;

    SELECT COUNT(*) INTO v_check FROM Stock WHERE intituleProduit = p_intituleProduit;

    IF(v_check = 0) THEN
        INSERT INTO Stock VALUES (p_intituleProduit, p_quantite);
    ELSE
       UPDATE Stock SET quantite = quantite + p_quantite WHERE p_intituleProduit = intituleProduit;
    END IF;

    IF (p_gratuit = 0) THEN
        v_prix := 0;
    ELSE
        SELECT prixAchat * p_quantite INTO v_prix FROM Produit WHERE intituleProduit = p_intituleProduit;
    END IF;

    UPDATE Stock_info SET totalAchat = totalAchat + v_prix;
END;

CREATE OR REPLACE PROCEDURE enregisterAchat(p_intituleProduit varchar, p_date date, p_quantite number, p_prixUnit number)
IS
    v_check number(4);
BEGIN
    SELECT count(*) INTO v_check FROM Stock_achats
        WHERE intituleProduit = p_intituleProduit AND dateAchat = p_date;

    IF (v_check = 0) THEN
        INSERT INTO Stock_achats VALUES (p_intituleProduit, p_date, p_quantite, p_prixUnit);
    ELSE
        UPDATE Stock_achats SET prixUnite = (quantite*prixUnite + p_prixUnit*p_quantite)/(quantite + p_quantite),
                                quantite = quantite + p_quantite
            WHERE intituleProduit = p_intituleProduit AND dateAchat = p_date;
    END IF;
END;

CREATE FUNCTION stock_vendre(p_intituleProduit varchar, p_quantite number, p_gratuit number) RETURN number
IS
    v_quantiteStock number(4);
    e_produitInexistant exception;
    e_quantiteInsuffisante exception;
    v_prix number(10,2);
    v_ingredient varchar(30);
BEGIN
    SELECT NVL(MAX(quantite), -1) INTO v_quantiteStock FROM Stock
    WHERE intituleProduit = p_intituleProduit;

    IF (v_quantiteStock = -1) THEN
        RAISE_APPLICATION_ERROR(-20001, 'Le produit n''est pas en stock');
    END IF;

    IF (v_quantiteStock < p_quantite) THEN
        RAISE_APPLICATION_ERROR(-20001, 'Il n''y a plus assez de stock : ' || v_quantiteStock || ' ' || p_intituleProduit ||' en stock');
    END IF;

    SELECT NVL(MAX(intituleProduitConsomme), null) INTO v_ingredient FROM Ingredient WHERE intituleProduitPere = p_intituleProduit;

    IF (v_ingredient IS NOT NULL) THEN
        SELECT NVL(MAX(quantite), -1) INTO v_quantiteStock FROM Stock
             WHERE intituleProduit = v_ingredient;

        IF (v_quantiteStock = -1) THEN
            RAISE_APPLICATION_ERROR(-20001, v_ingredient || ' n''est pas en stock');
        END IF;

        IF (v_quantiteStock < p_quantite) THEN
            RAISE_APPLICATION_ERROR(-20001, 'Il n''y a plus assez de stock : ' || v_quantiteStock || ' ' || v_ingredient ||' en stock');
        ELSE
            UPDATE Stock SET quantite = quantite - p_quantite WHERE intituleProduit = v_ingredient;
        END IF;
    END IF;

    IF (p_gratuit = 0) THEN
        v_prix := 0;
    ELSE
        SELECT prixVente * p_quantite INTO v_prix FROM Produit WHERE intituleProduit = p_intituleProduit;
    END IF;

    UPDATE Stock SET quantite = quantite - p_quantite WHERE intituleProduit = p_intituleProduit;
    UPDATE Stock_info SET totalVente = totalVente + v_prix;

    RETURN v_prix;
END;

CREATE OR REPLACE PROCEDURE compte_acheter(p_idCompte number, p_intituleProduit varchar, p_quantite number, p_date date, p_gratuit number)
IS
    v_prix number(10,2);
    v_check number(1);
    v_dateMin date;
BEGIN
    v_prix := stock_vendre(p_intituleProduit, p_quantite, p_gratuit);

    SELECT COUNT(*) INTO v_check FROM Compte_info WHERE idCompte = p_idCompte AND TO_CHAR(dateInfo, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY');
    IF (v_check = 0) THEN
        INSERT INTO Compte_info VALUES (p_idCompte, p_date, 0, v_prix, 0);
    ELSE
        UPDATE Compte_info SET moins = moins + v_prix WHERE idCompte = p_idCompte AND TO_CHAR(dateInfo, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY');
    END IF;

    SELECT COUNT(*) INTO v_check FROM Compte_achats
    WHERE idCompte = p_idCompte AND TO_CHAR(dateAchat, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY') AND intituleProduit = p_intituleProduit;

    IF (v_check = 0) THEN
        INSERT INTO Compte_achats VALUES (p_idCompte, p_date, p_intituleProduit, v_prix/p_quantite, p_quantite);
    ELSE
        UPDATE Compte_achats SET quantite = quantite + p_quantite
            WHERE idCompte = p_idCompte AND intituleProduit = p_intituleProduit AND TO_CHAR(dateAchat, 'DD/MM/YY') = TO_CHAR(p_date, 'DD/MM/YY');
    END IF;

    SELECT MIN(dateInfo) INTO v_dateMin FROM Compte_info WHERE idCompte = p_idCompte;
    maj_info_compte(p_idCompte, v_dateMin);
END;

CREATE PROCEDURE produit_supprimer(p_intituleProduit varchar)
IS
    v_checkIngredient number(2);
BEGIN
    SELECT COUNT(*) INTO v_checkIngredient FROM Ingredient WHERE intituleProduitConsomme = p_intituleProduit;

    IF (v_checkIngredient > 0) THEN
        RAISE_APPLICATION_ERROR(-20001,'Impossible de supprimer, ' ||  p_intituleProduit || ' est dans la recette d''autres produits');
    END IF;

    DELETE FROM Ingredient WHERE intituleProduitPere = p_intituleProduit;
    DELETE FROM Compte_achats WHERE intituleProduit = p_intituleProduit;
    DELETE FROM Stock WHERE intituleProduit = p_intituleProduit;
    DELETE FROM Produit WHERE intituleProduit = p_intituleProduit;
END;