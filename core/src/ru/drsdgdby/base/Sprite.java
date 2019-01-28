package ru.drsdgdby.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.math.Rect;
import ru.drsdgdby.utils.Regions;

public class Sprite extends Rect {
    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean isDestroyed;

    public Sprite() {
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException("create sprite with null region");
        }
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("create sprite with null region");
        } else {
            regions = new TextureRegion[1];
            regions[0] = region;
        }
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame], // текущий регион
                getLeft(), getBottom(), // отцентровка
                halfWidth, halfHeight, // смещение
                getWidth(), getHeight(), // ширина и высота
                scale, scale, angle // масштабирование и угол вращения
        );
    }

    public void resize(Rect worlBounds) {

    }

    public void update(float delta) {
    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public void flushDestroy() {
        isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
