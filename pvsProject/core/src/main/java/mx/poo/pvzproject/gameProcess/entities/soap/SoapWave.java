package mx.poo.pvzproject.gameProcess.entities.soap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Ola de jabón que recorre la lane de izquierda a derecha.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class SoapWave {

    private float         x;
    private final float   y;
    private final float   speed  = 600f;
    private boolean       active = true;

    public SoapWave(float startX, float laneY) {
        this.x = startX;
        this.y = laneY;
    }

    public void update(float delta, IGameContext ctx) {
        if (!active) return;

        x += speed * delta;

        for (Enemy e : ctx.getEnemies()) {
            if (Math.abs(e.getY() - y) < 55f && Math.abs(e.getX() - x) < 100f) {
                e.takeDamage(9999);
            }
        }

        if (x > 1380f) active = false;
    }

    public void draw(SpriteBatch batch) {
        if (active) batch.draw(Assets.soapWaveAnimation, x - 40f, y - 80f, 160f, 160f);
    }

    public boolean isActive() { return active; }
}
