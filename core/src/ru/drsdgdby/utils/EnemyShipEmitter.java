package ru.drsdgdby.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.math.Rect;
import ru.drsdgdby.math.Rnd;
import ru.drsdgdby.pool.EnemyShipPool;
import ru.drsdgdby.sprite.game.ships.EnemyShip;

public class EnemyShipEmitter {
    private static final float ENEMY_SMALL_HEIGHT = 0.08f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 1.5f;
    private static final int ENEMY_SMALL_HP = 3;

    private static final float ENEMY_MIDDLE_HEIGHT = 0.12f;
    private static final float ENEMY_MIDDLE_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_MIDDLE_BULLET_VY = -0.4f;
    private static final int ENEMY_MIDDLE_BULLET_DAMAGE = 2;
    private static final float ENEMY_MIDDLE_RELOAD_INTERVAL = 2f;
    private static final int ENEMY_MIDDLE_HP = 6;

    private static final float ENEMY_BIG_HEIGHT = 0.16f;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.05f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 3;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 2.5f;
    private static final int ENEMY_BIG_HP = 12;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMidV = new Vector2(0, -0.15f);
    private Vector2 enemyBigV = new Vector2(0, -0.1f);
    private TextureRegion[] enemySmallRegion;
    private TextureRegion[] enemyMiddleRegion;
    private TextureRegion[] enemyBigRegion;
    private TextureRegion enemy1;
    private TextureRegion enemy2;
    private TextureRegion enemy3;
    private TextureRegion bulletRegion;
    private EnemyShipPool enemyShipPool;
    private float generateTimer;
    private float generateInterval = 4f;
    private Rect worldBounds;
    private int level;

    public EnemyShipEmitter(TextureAtlas atlas, EnemyShipPool enemyShipPool, Rect worldBounds) {
        this.worldBounds = worldBounds;
        this.enemyShipPool = enemyShipPool;
        enemy1 = atlas.findRegion("enemyRed");
        enemySmallRegion = Regions.split(enemy1, 1, 2, 2);
        enemy2 = atlas.findRegion("enemyBlue");
        enemyMiddleRegion = Regions.split(enemy2, 1, 2, 2);
        enemy3 = atlas.findRegion("enemyGreen");
        enemyBigRegion = Regions.split(enemy3, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("laserRed");
    }

    public void generate(float delta, int frags) {
        level = (frags / 10) + 1;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemy = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemy.set(enemySmallRegion,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_BULLET_DAMAGE * level,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP
                );
            } else if (type < 0.8f) {
                enemy.set(enemyMiddleRegion,
                        enemyMidV,
                        bulletRegion,
                        ENEMY_MIDDLE_BULLET_HEIGHT,
                        ENEMY_MIDDLE_BULLET_VY,
                        ENEMY_MIDDLE_BULLET_DAMAGE * level,
                        ENEMY_MIDDLE_RELOAD_INTERVAL,
                        ENEMY_MIDDLE_HEIGHT,
                        ENEMY_MIDDLE_HP
                );
            } else {
                enemy.set(enemyBigRegion,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_BULLET_DAMAGE * level,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP
                );
            }

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(),
                    worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
