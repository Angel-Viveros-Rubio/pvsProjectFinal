package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Renderizador de barras de vida para plantas y enemigos.
 *
 * <p>
 * Esta clase se encarga de representar visualmente el estado
 * de salud de las entidades activas en el juego.
 * </p>
 *
 * <p>
 * Utiliza la información proporcionada por {@link IGameContext}
 * para acceder a las listas de {@link Plant} y {@link Enemy}
 * sin depender directamente de la clase concreta que gestiona
 * la pantalla principal.
 * </p>
 *
 * <p>
 * Para cada entidad:
 * </p>
 * <ul>
 *     <li>Dibuja una barra vacía (fondo).</li>
 *     <li>Dibuja la porción de vida restante.</li>
 *     <li>Muestra un ícono de corazón como indicador visual.</li>
 * </ul>
 *
 * <p>
 * Los recursos gráficos utilizados provienen de {@link Assets}.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class HealthBarRenderer {

    /** Contexto del juego para acceder al estado global. */
    private final IGameContext ctx;

    /**
     * Constructor del renderizador de barras de vida.
     *
     * @param ctx contexto del juego
     */
    protected HealthBarRenderer(IGameContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Renderiza las barras de vida de todas las plantas y enemigos activos.
     *
     * <p>
     * Para las plantas, el porcentaje se calcula con base en el tiempo
     * restante de resistencia.
     * </p>
     *
     * <p>
     * Para los enemigos, el porcentaje se calcula como la relación entre
     * la vida actual y la vida máxima.
     * </p>
     *
     * @param batch SpriteBatch utilizado para dibujar los elementos gráficos
     */
    public void render(SpriteBatch batch) {

        // Renderizado de barras para plantas
        for (Plant p : ctx.getPlants()) {
            float percent = 1f - (p.getResistanceTimer() / p.getMaxResistanceTime());

            float barPlantX = p.getX() - 20f;
            float barPlantY = p.getY() + 40f;

            batch.draw(Assets.emptyBar,  barPlantX,       barPlantY, 50f,           9f);
            batch.draw(Assets.healthFill, barPlantX,       barPlantY, 50f * percent, 9f);
            batch.draw(Assets.heartIcon,  barPlantX - 10f, barPlantY - 5f, 15f, 15f);
        }

        // Renderizado de barras para enemigos
        for (Enemy e : ctx.getEnemies()) {
            float percent = (float) e.getHealth() / e.getMaxHealth();

            float barSlimeX = e.getX() - 60f;
            float barSlimeY = e.getY() + 60f;

            batch.draw(Assets.emptyBar,   barSlimeX,       barSlimeY, 120f,           12f);
            batch.draw(Assets.healthFill, barSlimeX,       barSlimeY, 120f * percent, 12f);
            batch.draw(Assets.heartIcon,  barSlimeX - 10f, barSlimeY - 6f, 25f, 25f);
        }
    }
}
