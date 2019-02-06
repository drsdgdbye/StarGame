package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.drsdgdby.base.BaseScreen;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;
import ru.drsdgdby.pool.EnemyShipPool;
import ru.drsdgdby.pool.ExplosionPool;
import ru.drsdgdby.sprite.Background;
import ru.drsdgdby.sprite.Star;
import ru.drsdgdby.sprite.game.Bullet;
import ru.drsdgdby.sprite.game.EnemyShip;
import ru.drsdgdby.sprite.game.GameOver;
import ru.drsdgdby.sprite.game.MainShip;
import ru.drsdgdby.utils.EnemyShipEmitter;

//TODO реализовать кнопку назад и паузу
//TODO добавить астероиды
public class GameScreen extends BaseScreen {
    private Texture bgd;
    private GameOver gameOver;
    private Background background;
    private Music music;
    private TextureAtlas gameAtlas;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipPool enemyPool;
    private EnemyShipEmitter enemies;

    @Override
    public void show() {
        super.show();
        bgd = new Texture("textures/brd.jpg");
        background = new Background(new TextureRegion(bgd));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gametrack_1.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
//        music.play();

        gameAtlas = new TextureAtlas("textures/gameatlas.tpack");

        gameOver = new GameOver(gameAtlas);
        star = new Star[32];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(gameAtlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(gameAtlas);
        mainShip = new MainShip(gameAtlas, bulletPool, explosionPool);
        enemyPool = new EnemyShipPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemies = new EnemyShipEmitter(gameAtlas, enemyPool, worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void checkCollisions() {
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                mainShip.damage(enemy.getDamage());
                enemy.destroy();
                return;
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }

        }
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(mainShip.getDamage());
                    bullet.destroy();
                }
            }

        }
    }

    private void update(float delta) {
        for (Star s : star) {
            s.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemies.generate(delta);
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star s : star) {
            s.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
        }
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        gameOver.resize(worldBounds);
        for (Star s : star) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (!mainShip.isDestroyed()) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (!mainShip.isDestroyed()) {
            mainShip.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {
        music.dispose();
        mainShip.dispose();
        bgd.dispose();
        gameAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
    }

}
