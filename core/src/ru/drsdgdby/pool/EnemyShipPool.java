package ru.drsdgdby.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.drsdgdby.base.SpritesPool;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.sprite.game.ships.EnemyShip;
import ru.drsdgdby.sprite.game.ships.MainShip;

//TODO поменять звук вражеского выстрела
public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private Rect worldBounds;
    private Sound shootSound;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyShipPool(BulletPool bulletPool, Rect worldBounds, ExplosionPool explosionPool, MainShip mainShip) {
        this.mainShip = mainShip;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laserfire02.ogg"));
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;

    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool, worldBounds, explosionPool, mainShip);
    }

    @Override
    public void dispose() {
        shootSound.dispose();
    }
}
