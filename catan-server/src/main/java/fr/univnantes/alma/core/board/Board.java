package fr.univnantes.alma.core.board;

import fr.univnantes.alma.Model.BuildableArea;

public interface Board {

    /**
     * Ressource
     * Développement
     *      Chevalier (
     *      Progrès (3)
     *      PV (5)
     * spéciales
     *      route (1)
     *      chevalier (1)
     */

    /**
     * TODO change
     */
    void initilization();

    void buildRoad(Road road, BuildableArea buildableArea);

    void buildColony(Colony colony, BuildableArea buildableArea);

    void buildCity(City city, BuildableArea buildableArea);

    void moveThief(Territory territory);

    Ressource pickRessource(Ressource ressource, int amount);

    Developpement pickDeveloppement();

    Special pickSpecial(Special special);

}