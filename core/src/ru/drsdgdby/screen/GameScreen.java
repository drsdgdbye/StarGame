package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.BaseScreen;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;
import ru.drsdgdby.sprite.Background;
import ru.drsdgdby.sprite.Star;
import ru.drsdgdby.sprite.game.MainShip;

public class GameScreen extends BaseScreen {
    private Texture bgd;
    private Background background;
    private TextureAtlas gameAtlas;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;
    private Sound shootSound;

    @Override
    public void show() {
        super.show();
        bgd = new Texture("textures/brd.jpg");
        background = new Background(new TextureRegion(bgd));
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gametrack_1.mp3"));
        music.play();
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laserfire02.ogg"));
        gameAtlas = new TextureAtlas("textures/gamescreenatlas.pack");
        star = new Star[32];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(gameAtlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(gameAtlas, bulletPool, shootSound);
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
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
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
        shootSound.dispose();
        bgd.dispose();
        gameAtlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

}
