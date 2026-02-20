package mx.poo.pvzproject.gameProcess.entities.soap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Defensa de jabón en el borde izquierdo de cada lane.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class SoapDefense {

    private final float x;
    private final float y;
    private final int   laneIndex;
    private boolean     used = false;

    private static final float TRIGGER_X = Constants.GRID_START_X - 50f;

    public SoapDefense(float x, float y, int laneIndex) {
        this.x         = x;
        this.y         = y;
        this.laneIndex = laneIndex;
    }

    public void tryActivate(IGameContext ctx) {
        if (used) return;
        for (Enemy e : ctx.getEnemies()) {
            if (Math.abs(e.getY() - y) < 55f && e.getX() < TRIGGER_X) {
                activate(ctx);
                break;
            }
        }
    }

    private void activate(IGameContext ctx) {
        used = true;
        ctx.getSoapWaves().add(new SoapWave(Constants.GRID_START_X - 50f, y));
    }

    public void draw(SpriteBatch batch) {
        if (!used) batch.draw(Assets.soap, x + 10f, y - 60f, 80f, 120f);
    }

    public void reset() {
        used = false;
    }
}
