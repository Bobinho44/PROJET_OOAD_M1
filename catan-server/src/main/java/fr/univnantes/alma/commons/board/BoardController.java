package fr.univnantes.alma.commons.board;

import fr.univnantes.alma.commons.construction.ConstructionController;
import fr.univnantes.alma.commons.territory.TerritoryController;
import fr.univnantes.alma.core.construction.ConstructionManager;
import fr.univnantes.alma.core.territory.TerritoryManager;

public class BoardController {

    private TerritoryManager territoryManager;
    private ConstructionManager constructionManager;

    public BoardController() {
        territoryManager = new TerritoryController();
        constructionManager = new ConstructionController((territoryManager.getTerritories()));
    }
}
