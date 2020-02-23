package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.ScealedTouchUpButton;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.screen.GameScreen;
import ru.drsdgdby.sprite.game.ships.MainShip;

public class PauseButton extends ScealedTouchUpButton {
    private MainShip mainShip;
    private GameScreen gameScreen;

    public PauseButton(TextureAtlas atlas, GameScreen gameScreen, MainShip mainShip) {
        super(atlas.findRegion("pause"));
        this.mainShip = mainShip;
        this.gameScreen = gameScreen;
        setHeightProportion(0.1f);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            mainShip.stop();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    protected void action() {
        gameScreen.pause();
    }

    @Override
    public void resize(Rect worldBounds) {
        setTop(worldBounds.getTop() - 0.02f);
        setRight(worldBounds.getRight() - 0.02f);
    }
}
