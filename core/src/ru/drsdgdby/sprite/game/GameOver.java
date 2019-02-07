package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.drsdgdby.base.Sprite;
import ru.drsdgdby.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameover"));
        setHeightProportion(0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(0, 0);
    }
}
