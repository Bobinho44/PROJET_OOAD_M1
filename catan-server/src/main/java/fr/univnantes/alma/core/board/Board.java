package fr.univnantes.alma.core.board;

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
     * Builds the board
     */
    void build();

    void buildRoad(Road road, BuildableArea buildableArea);

    void buildColony(Colony colony, BuildableArea buildableArea);

    void buildCity(City city, BuildableArea buildableArea);

    void moveThief(Territory territory);

    Ressource pickRessource(Ressource ressource, int amount);

    Developpement pickDeveloppement();

    Special pickSpecial(Special special);

}