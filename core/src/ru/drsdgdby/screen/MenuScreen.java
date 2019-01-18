package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    SpriteBatch batch;
    Texture bgd;
    Texture ship;
    Vector2 pos;
    Vector2 mousePos;
    boolean stop;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        bgd = new Texture("backgrnd.jpg");
        ship = new Texture("ship1.png");
        pos = new Vector2(320,0);
         mousePos = new Vector2();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(bgd, 0, 0);
        batch.draw(ship, pos.x, pos.y);
        batch.end();

        if (!stop) {
            mousePos.nor();
            pos.add(mousePos);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        stop = false;
        mousePos.x = Gdx.input.getX();
        mousePos.y = Gdx.graphics.getHeight() - Gdx.input.getY();

        if (mousePos.x < pos.x){
            mousePos.x *= -1;
        }
        if (mousePos.y < pos.y){
            mousePos.y *= -1;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        stop = true;
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        bgd.dispose();
        ship.dispose();
        super.dispose();
    }
}
