package ru.drsdgdby;

import com.badlogic.gdx.Game;

import ru.drsdgdby.screen.MenuScreen;

public class StarGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen());
    }
}
