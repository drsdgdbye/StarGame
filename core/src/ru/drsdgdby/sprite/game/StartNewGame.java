package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.ScealedTouchUpButton;
import ru.drsdgdby.screen.GameScreen;

public class StartNewGame extends ScealedTouchUpButton {
    private GameScreen gameScreen;

    public StartNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("restart"));
        this.gameScreen = gameScreen;
        setHeightProportion(0.1f);
        setTop(0.1f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    protected void action() {
        gameScreen.startNewGame();
    }
}
