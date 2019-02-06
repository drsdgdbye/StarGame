package ru.drsdgdby.pool;

import ru.drsdgdby.base.SpritesPool;
import ru.drsdgdby.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
