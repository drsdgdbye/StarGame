package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.Sprite;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;

public class MainShip extends Sprite {
    private final Vector2 v0 = new Vector2(0.5f, 0);
    private Vector2 v = new Vector2();
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("player"));
        this.bulletRegion = atlas.findRegion("laserGreenShot");
        this.bulletPool = bulletPool;
        setHeightProportion(0.15f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom() + 0.02f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        checkBoundsAndChangeRoute();
    }

    private void checkBoundsAndChangeRoute() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight() + 0.01f) setRight(worldBounds.getLeft());
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                isPressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.RIGHT:
                isPressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                isPressedLeft = false;
                if (isPressedRight) {
                    moveRight();
                } else stop();
                break;

            case Input.Keys.RIGHT:
                isPressedRight = false;

                if (isPressedLeft) {
                    moveLeft();
                } else stop();
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x <= 0) {
            moveLeft();
        } else {
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        stop();
        return super.touchUp(touch, pointer);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                new Vector2(0, 0.5f),
                0.03f,
                worldBounds,
                1);
    }
}
