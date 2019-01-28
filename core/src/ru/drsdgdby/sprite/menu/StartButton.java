package ru.drsdgdby.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.math.Rect;
import ru.drsdgdby.screen.GameScreen;

public class StartButton extends ScealedTouchUpButton {
    private Game game;

    public StartButton(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.15f);
        this.game = game;
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
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(0, worldBounds.getHalfHeight() / 2);
    }
}
