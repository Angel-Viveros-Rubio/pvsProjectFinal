package mx.poo.pvzproject.gameProcess.entities.soap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Representa una ola de jabón que recorre una línea del tablero.
 *
 * <p>
 * Esta entidad se genera cuando se activa una {@link SoapDefense}.
 * Se desplaza horizontalmente de izquierda a derecha, eliminando
 * instantáneamente a cualquier enemigo que toque.
 * </p>
 *
 * <p>
 * Es una mecánica de defensa de último recurso ("limpiadora de carril").
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SoapWave {

    private float         x;
    private final float   y;
    private final float   speed  = 600f;
    private boolean       active = true;

    /**
     * Constructor de la ola de jabón.
     *
     * @param startX posición inicial en X
     * @param laneY  posición en Y (carril)
     */
    public SoapWave(float startX, float laneY) {
        this.x = startX;
        this.y = laneY;
    }

    /**
     * Actualiza la posición de la ola y verifica colisiones con enemigos.
     *
     * @param delta tiempo transcurrido desde el último frame
     * @param ctx   contexto del juego para acceder a la lista de enemigos
     */
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

    /**
     * Dibuja la ola de jabón en pantalla.
     *
     * @param batch SpriteBatch utilizado para dibujar
     */
    public void draw(SpriteBatch batch) {
        if (active) batch.draw(Assets.soapWaveAnimation, x - 40f, y - 80f, 160f, 160f);
    }

    /**
     * Indica si la ola sigue activa dentro de la pantalla.
     *
     * @return true si la ola está activa
     */
    public boolean isActive() { return active; }
}
