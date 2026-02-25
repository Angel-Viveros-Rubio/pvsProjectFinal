package mx.poo.pvzproject.gameProcess.entities.soap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Representa la defensa de jabón ubicada al inicio de cada carril.
 *
 * <p>
 * Esta defensa actúa como una salvaguarda final. Cuando un enemigo
 * llega al extremo izquierdo del tablero (la casa), esta defensa se activa,
 * lanzando una {@link SoapWave} que limpia todo el carril.
 * </p>
 *
 * <p>
 * Solo puede usarse una vez por nivel en cada carril.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SoapDefense {

    private final float x;
    private final float y;
    private final int   laneIndex;
    private boolean     used = false;

    private static final float TRIGGER_X = Constants.GRID_START_X - 50f;

    /**
     * Constructor de la defensa de jabón.
     *
     * @param x         posición en X
     * @param y         posición en Y
     * @param laneIndex índice del carril (0-4)
     */
    public SoapDefense(float x, float y, int laneIndex) {
        this.x         = x;
        this.y         = y;
        this.laneIndex = laneIndex;
    }

    /**
     * Intenta activar la defensa si un enemigo cruza el umbral.
     *
     * @param ctx contexto del juego para verificar enemigos
     */
    public void tryActivate(IGameContext ctx) {
        if (used) return;
        for (Enemy e : ctx.getEnemies()) {
            if (Math.abs(e.getY() - y) < 55f && e.getX() < TRIGGER_X) {
                activate(ctx);
                break;
            }
        }
    }

    /**
     * Activa la defensa, generando una ola y marcándola como usada.
     *
     * @param ctx contexto del juego para agregar la ola
     */
    private void activate(IGameContext ctx) {
        used = true;
        ctx.getSoapWaves().add(new SoapWave(Constants.GRID_START_X - 50f, y));
    }

    /**
     * Dibuja la defensa si aún no ha sido utilizada.
     *
     * @param batch SpriteBatch utilizado para dibujar
     */
    public void draw(SpriteBatch batch) {
        if (!used) batch.draw(Assets.soap, x + 10f, y - 60f, 80f, 120f);
    }

    /**
     * Reinicia el estado de la defensa (para reiniciar nivel).
     */
    public void reset() {
        used = false;
    }
}
