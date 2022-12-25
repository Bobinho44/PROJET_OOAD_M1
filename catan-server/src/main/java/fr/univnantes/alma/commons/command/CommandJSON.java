package fr.univnantes.alma.commons.command;

import fr.univnantes.alma.commons.game.GameJSON;
import org.springframework.lang.NonNull;

import java.util.List;

public class CommandJSON {

    private final GameJSON game;
    private final String name;
    private final List<Object> parameters;
    private final boolean needActive;

    public CommandJSON(@NonNull GameJSON game, @NonNull String name, @NonNull List<Object> parameters, boolean needActive) {
        this.game = game;
        this.name = name;
        this.parameters = parameters;
        this.needActive = needActive;
    }

    public GameJSON getGame() {
        return game;
    }

    public String getCommandName() {
        return name;
    }

    public List<Object> getCommandParameters() {
        return parameters;
    }

    public boolean isNeedActive() {
        return needActive;
    }

}