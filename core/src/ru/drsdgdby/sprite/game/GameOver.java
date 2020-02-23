package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.drsdgdby.base.Sprite;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameover"));
        setHeightProportion(0.2f);
        setTop(0.3f);
    }

}
