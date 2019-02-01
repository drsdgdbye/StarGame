package ru.drsdgdby.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.math.Rect;
import ru.drsdgdby.math.Rnd;
import ru.drsdgdby.pool.EnemyShipPool;
import ru.drsdgdby.sprite.game.EnemyShip;

public class EnemyShipEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private TextureRegion[] enemySmallRegion;
    private TextureRegion enemy1;
    private TextureRegion enemy2;
    private TextureRegion enemy3;
    private TextureRegion enemy4;
    private TextureRegion bulletRegion;
    private EnemyShipPool enemyShipPool;
    private float generateTimer;
    private float generateInterval = 4f;
    private Rect worldBounds;

    public EnemyShipEmitter(TextureAtlas atlas, EnemyShipPool enemyShipPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        enemy1 = atlas.findRegion("enemyRed2");
        enemy2 = atlas.findRegion("enemyBlue1");
        enemy3 = atlas.findRegion("enemyGreen3");
        enemy4 = atlas.findRegion("enemyBlack4");
        this.bulletRegion = atlas.findRegion("laserRed");
    }

    private TextureRegion[] randomCreate() {
        int n = Rnd.nextInt(1, 4);
        switch (n) {
            case 1:
                return enemySmallRegion = Regions.split(enemy1, 1, 1, 1);
            case 2:
                return enemySmallRegion = Regions.split(enemy2, 1, 1, 1);
            case 3:
                return enemySmallRegion = Regions.split(enemy3, 1, 1, 1);
            case 4:
                return enemySmallRegion = Regions.split(enemy4, 1, 1, 1);
            default:
                return enemySmallRegion;
        }
    }

    public void generate(float delta) {
        randomCreate();
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyShipPool.obtain();
            enemy.set(enemySmallRegion,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_BULLET_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
