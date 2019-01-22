package ru.drsdgdby.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.drsdgdby.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Texture bgd;
    private Texture ship;
    private Vector2 pos, touchPos, dst, buf;

    @Override
    public void show() {
//        Gdx.input.setInputProcessor(this);
        super.show();
        bgd = new Texture("brd.jpg");
        ship = new Texture("frog.png");
        pos = new Vector2(-0.05f, -0.05f);
        touchPos = new Vector2();
        dst = new Vector2();
        buf = new Vector2();

    }

    /*float width = Gdx.graphics.getWidth();
    float height = Gdx.graphics.getHeight();*/
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(bgd, -0.5f, -0.5f, 1f, 1f);
        batch.draw(ship, pos.x, pos.y, 0.1f, 0.1f);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            pos.x -= 0.5f * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            pos.x += 0.5f * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            pos.y += 0.5f * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            pos.y -= 0.5f * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isTouched()) {
            buf.set(touchPos);
            if (buf.sub(pos).len() > 0.01f) {
                pos.add(dst);
            }
        }

        if (pos.x >= 0.5f) {
            pos.set(-0.49f, pos.y);
        }
        if (pos.y >= 0.5f) {
            pos.set(pos.x, -0.49f);
        }
        if (pos.x <= -0.5f) {
            pos.set(0.5f, pos.y);
        }
        if (pos.y <= -0.5f) {
            pos.set(pos.x, 0.5f); // сеттится только в 2 из 4 сторон
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touchPos.set(touch);
        dst.set(touch.sub(pos).setLength(0.01f));
        return false;
    }

    @Override
    public void dispose() {
        bgd.dispose();
        ship.dispose();
        super.dispose();
    }
}
