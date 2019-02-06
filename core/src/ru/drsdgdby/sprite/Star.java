package ru.drsdgdby.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.Sprite;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.math.Rnd;

public class Star extends Sprite {
    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f, 0.01f));
        if (this.getHeight() <= 0.007f) {
            v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.2f, -0.1f));
        } else {
            v.set(0, Rnd.nextFloat(-0.5f, -0.3f));
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getHeight(), worldBounds.getBottom());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    private void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
