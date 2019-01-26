package ru.drsdgdby.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.drsdgdby.base.Sprite;
import ru.drsdgdby.math.Rect;

public class Background extends Sprite {
    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worlBounds) {
        pos.set(worlBounds.pos);
        setHeightProportion(worlBounds.getHeight());
    }
}
