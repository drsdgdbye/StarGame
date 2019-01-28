package ru.drsdgdby.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.BaseScreen;
import ru.drsdgdby.math.Rect;
import ru.drsdgdby.sprite.Background;
import ru.drsdgdby.sprite.menu.ExitButton;
import ru.drsdgdby.sprite.menu.StartButton;

public class MenuScreen extends BaseScreen {
    private Game game;
    private Texture bgd;
    private TextureAtlas atlas;
    private Background background;
    private StartButton startButton;
    private ExitButton exitButton;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bgd = new Texture("textures/brd.jpg");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(new TextureRegion(bgd));
        startButton = new StartButton(atlas, game);
        exitButton = new ExitButton(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        draw();
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        startButton.draw(batch);
        exitButton.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        startButton.resize(worldBounds);
        exitButton.resize(worldBounds);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        startButton.touchDown(touch, pointer);
        exitButton.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        startButton.touchUp(touch, pointer);
        exitButton.touchUp(touch, pointer);
        return false;
    }

    @Override
    public void dispose() {
        bgd.dispose();
        atlas.dispose();
        super.dispose();
    }
}
