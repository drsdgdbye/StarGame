package ru.drsdgdby.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.Sprite;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;

public class Ship extends Sprite {
    protected Sound shootSound;
    protected BulletPool bulletPool;
    protected Vector2 v = new Vector2();
    protected Rect worldBounds;
    protected TextureRegion bulletRegion;
    protected float reloadInterval;
    protected float reloadTimer;
    protected Vector2 bulletV;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region) {
        super(region);
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    public void shoot() {
        shootSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                bulletV,
                bulletHeight,
                worldBounds,
                damage);
    }

    public void dispose() {
        shootSound.dispose();
    }
}
