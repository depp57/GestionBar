--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
---------------- TEST DE LA BASE DE DONNEE GESTION BAR -------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>
--------------------------------------------------------------------->>

BEGIN
    compte_creer('Thommet', 'Sacha');
    produit_inserer('Bière', 0.50, 0.82, 'Aperitif', null);
    --compte_supprimer('Thommet', 'Sacha');
    --compte_creer('Thommet', 'Sacha');
    --compte_crediter('Thommet', 'Sacha', 10, current_date);
END;

select * from Produit;
select * from Compte;
select * from Compte_achats;
select * from Compte_info;
select * from stock;
select * from Stock_info;

BEGIN
    INSERT INTO Compte_info VALUES(2, TO_DATE('02/10/2019'), 5, 0, 0);
    compte_crediter('Thommet', 'Sacha', 10, TO_DATE('03/10/2019'));
    compte_crediter('Thommet', 'Sacha', 10, TO_DATE('04/10/2019'));
end;

BEGIN
    produit_modifier('Bière', 'Bière2', 10, 15, 'Boisson');
end;

BEGIN
    clearDatesInutilesCompte('Thommet', 'Sacha');
end;

BEGIN
    compte_supprimer('Thommet', 'Sacha');
    compte_creer('Thommet', 'Sacha');
end;

begin
    stock_acheter('Bière2', 5, false);
end;

begin
    stock_acheter('Bière2', 5, false);
    compte_acheter('Thommet', 'Sacha', 'Bière2', 2, current_date);
end;

BEGIN
    compte_creer('Gauthier', 'Mayer');
    produit_inserer('Eau', 0.75, 0.95, 'Boisson');
    stock_acheter('Eau', 100, false);
    compte_acheter('Gauthier', 'Mayer', 'Eau', 5, TO_DATE('01/10/2019'));
    compte_acheter('Gauthier', 'Mayer', 'Eau', 5, TO_DATE('02/10/2019'));
    compte_crediter('Gauthier', 'Mayer', 100, TO_DATE('02/10/2019'));
    compte_acheter('Gauthier', 'Mayer', 'Eau', 5, TO_DATE('03/10/2019'));
    compte_acheter('Gauthier', 'Mayer', 'Eau', 5, TO_DATE('04/10/2019'));
END;

CREATE OR REPLACE PROCEDURE test
    IS
    x date;
BEGIN
    FOR i in 1..1000 loop
            x := RECUPERERDATEAPRES(2, TO_DATE('17/10/2019'));
        end loop;
    dbms_output.put_line(x);
END;

CREATE OR REPLACE PROCEDURE test2
IS
    x date;
BEGIN
    FOR i in 1..1000 loop
        x := RECUPERERDATEAVANT(2, TO_DATE('17/10/2019'));
    end loop;
    dbms_output.put_line(x);
END;

BEGIN
    test();
end;

BEGIN
    test2();
end;

DROP PROCEDURE test;
DROP PROCEDURE test2;

select * from compte_info WHERE 2 = idCompte;

SELECT TO_CHAR(dateInfo, 'dd/MM/yyyy'), reste, moins, plus  FROM COMPTE_INFO
                     WHERE idCompte = ? AND EXTRACT(year FROM dateInfo) = ? ORDER BY dateInfo;
