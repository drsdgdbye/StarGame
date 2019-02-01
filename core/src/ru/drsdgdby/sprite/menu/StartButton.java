package ru.drsdgdby.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.math.Rect;
import ru.drsdgdby.screen.GameScreen;

public class StartButton extends ScealedTouchUpButton {
    private Game game;
    private Music music;
    private float volumeTimer;

    public StartButton(TextureAtlas atlas, Game game, Music music) {
        super(atlas.findRegion("PlayButton"));
        setHeightProportion(0.15f);
        this.game = game;
        this.music = music;
        this.volumeTimer = 1f;
    }


    public void dampingVolume(float delta) {
        volumeTimer += delta;
        if (volumeTimer >= 0.2f && volumeTimer < 0.4f) {
            music.setVolume(0.7f);
        }
        if (volumeTimer >= 0.4f && volumeTimer < 0.6f) {
            music.setVolume(0.5f);
        }
        if (volumeTimer >= 0.6f && volumeTimer < 0.8f) {
            music.setVolume(0.3f);
        }
        if (volumeTimer >= 0.8f && volumeTimer < 1f) {
            music.stop();
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        return super.touchUp(touch, pointer);
    }

    @Override
    protected void action() {
        music.stop();
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(0, worldBounds.getHalfHeight() / 2);
    }
}
