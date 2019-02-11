package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.drsdgdby.base.BaseScreen;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.pool.BulletPool;
import ru.drsdgdby.pool.EnemyShipPool;
import ru.drsdgdby.pool.ExplosionPool;
import ru.drsdgdby.sprite.Background;
import ru.drsdgdby.sprite.Star;
import ru.drsdgdby.sprite.game.Bullet;
import ru.drsdgdby.sprite.game.GameOver;
import ru.drsdgdby.sprite.game.PauseButton;
import ru.drsdgdby.sprite.game.StartNewGame;
import ru.drsdgdby.sprite.game.ships.EnemyShip;
import ru.drsdgdby.sprite.game.ships.MainShip;
import ru.drsdgdby.utils.EnemyShipEmitter;
import ru.drsdgdby.utils.Font;

//TODO реализовать кнопку назад и паузу
//TODO добавить астероиды
public class GameScreen extends BaseScreen {

    private static final float MUSIC_VOLUME = 0.8f;
    private State state;
    private StartNewGame startNewGame;
    private Texture bgd;
    private GameOver gameOver;
    private PauseButton pauseButton;
    private TextureAtlas menuAtlas;
    private Background background;
    private Music music;
    private Font font;
    private TextureAtlas gameAtlas;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyShipPool enemyPool;
    private EnemyShipEmitter enemies;
    private int frags = 0;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    @Override
    public void show() {
        super.show();
        bgd = new Texture("textures/brd.jpg");
        background = new Background(new TextureRegion(bgd));

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gametrack_1.mp3"));
        music.setLooping(true);
        music.setVolume(MUSIC_VOLUME);
        music.play();

        menuAtlas = new TextureAtlas("textures/menuButtons1.tpack");
        gameAtlas = new TextureAtlas("textures/gameatlas2.tpack");

        gameOver = new GameOver(gameAtlas);
        startNewGame = new StartNewGame(menuAtlas, this);
        this.font = new Font("fonts/font.fnt", "fonts/font.png");
        this.font.setSize(0.02f);
        star = new Star[32];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(gameAtlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(gameAtlas);
        mainShip = new MainShip(gameAtlas, bulletPool, explosionPool, worldBounds);
        enemyPool = new EnemyShipPool(bulletPool, worldBounds, explosionPool, mainShip);
        enemies = new EnemyShipEmitter(gameAtlas, enemyPool, worldBounds);
        pauseButton = new PauseButton(menuAtlas, this, mainShip);
        startNewGame();
    }

    private void checkCollisions() {
        if (state == State.PLAY) {
            List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
            for (EnemyShip enemy : enemyShipList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
                if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                    mainShip.damage(enemy.getDamage());
                    enemy.destroy();
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
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
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
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
                        if (enemy.isDestroyed()) {
                            frags++;
                        }
                        bullet.destroy();
                    }
                }

            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Star s : star) {
            s.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        switch (state) {
            case PLAY:
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                enemies.generate(delta, frags);
                break;
            case PAUSE:
                break;
            case GAME_OVER:
                break;
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star s : star) {
            s.draw(batch);
        }
        switch (state) {
            case PLAY:
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                pauseButton.draw(batch);
                break;
            case PAUSE:
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                pauseButton.draw(batch);
                break;
            case GAME_OVER:
                gameOver.draw(batch);
                startNewGame.draw(batch);
                break;
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + 0.01f, worldBounds.getTop() - 0.01f);
        font.draw(batch, sbHP.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - 0.01f, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(enemies.getLevel()), worldBounds.getRight() - 0.01f, worldBounds.getTop() - 0.01f, Align.right);

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        gameOver.resize(worldBounds);
        for (Star s : star) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        pauseButton.resize(worldBounds);
    }

    @Override
    public void pause() {
        if (state == State.PLAY) {
            state = State.PAUSE;
            music.setVolume(0.3f);
        } else if (state == State.PAUSE) {
            state = State.PLAY;
            music.setVolume(MUSIC_VOLUME);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAY) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAY) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case PLAY:
                mainShip.touchDown(touch, pointer);
                pauseButton.touchDown(touch, pointer);
                break;
            case PAUSE:
                pauseButton.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case PLAY:
                mainShip.touchUp(touch, pointer);
                pauseButton.touchUp(touch, pointer);
                break;
            case PAUSE:
                pauseButton.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                startNewGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    public void startNewGame() {
        state = State.PLAY;
        mainShip.startNewGame();
        frags = 0;
        enemies.setLevel(1);
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
    }

    @Override
    public void dispose() {
        music.dispose();
        mainShip.dispose();
        bgd.dispose();
        gameAtlas.dispose();
        menuAtlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        font.dispose();
        super.dispose();
    }

    private enum State {PLAY, PAUSE, GAME_OVER}

}
