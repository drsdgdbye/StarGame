package ru.drsdgdby.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.drsdgdby.base.SpritesPool;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.sprite.game.EnemyShip;

//TODO поменять звук вражеского выстрела
public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private Sound shootSound;
    private BulletPool bulletPool;

    public EnemyShipPool(BulletPool bulletPool) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laserfire02.ogg"));
        this.bulletPool = bulletPool;

    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool, worldBounds);
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }
}
