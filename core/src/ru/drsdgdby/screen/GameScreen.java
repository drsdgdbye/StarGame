package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.BaseScreen;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;
import ru.drsdgdby.pool.EnemyShipPool;
import ru.drsdgdby.pool.ExplosionPool;
import ru.drsdgdby.sprite.Background;
import ru.drsdgdby.sprite.Star;
import ru.drsdgdby.sprite.game.Explosion;
import ru.drsdgdby.sprite.game.MainShip;
import ru.drsdgdby.utils.EnemyShipEmitter;

//TODO реализовать кнопку назад и паузу
//TODO добавить астероиды
public class GameScreen extends BaseScreen {
    private Texture bgd;
    private Background background;
    private Music music;
    private TextureAtlas gameAtlas;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipPool enemypool;
    private EnemyShipEmitter enemies;

    @Override
    public void show() {
        super.show();
        bgd = new Texture("textures/brd.jpg");
        background = new Background(new TextureRegion(bgd));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gametrack_1.mp3"));
        music.setLooping(true);
        music.setVolume(0.8f);
        music.play();

        gameAtlas = new TextureAtlas("textures/gameAtlas1.tpack");

        star = new Star[32];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(gameAtlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(gameAtlas);
        enemypool = new EnemyShipPool(bulletPool);
        mainShip = new MainShip(gameAtlas, bulletPool);
        enemies = new EnemyShipEmitter(gameAtlas, enemypool, worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }


    public void update(float delta) {
        for (Star s : star) {
            s.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemypool.updateActiveSprites(delta);
        enemies.generate(delta);
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemypool.freeAllDestroyedActiveSprites();
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star s : star) {
            s.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemypool.drawActiveSprites(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star s : star) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        enemypool.resize(worldBounds);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        Explosion explosion = explosionPool.obtain();
        explosion.set(0.15f, touch);
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
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
        enemypool.dispose();
        super.dispose();
    }

}
