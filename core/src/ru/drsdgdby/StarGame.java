package ru.drsdgdby;

import com.badlogic.gdx.Game;

import ru.drsdgdby.screen.MenuScreen;

public class StarGame extends Game {
    private Game game;

    public StarGame() {
        this.game = this;
    }
    @Override
    public void create() {
        setScreen(new MenuScreen(game));
    }
}
