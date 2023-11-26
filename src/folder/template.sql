/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  user
 * Created: 25 nov. 2023
 */

    CREATE TABLE personnes (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(50),
    age INTEGER,
    taille DECIMAL(5,2),
    est_actif BOOLEAN,
    date_naissance DATE,
    heure_naissance TIME,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


