package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.ui.screens.IGameContext;

/**
 * Administrador central de renderizadores del juego.
 *
 * <p>
 * Esta clase agrupa y coordina todos los renderers encargados
 * de dibujar los distintos elementos visuales del juego.
 * </p>
 *
 * <p>
 * Todos los renderers reciben una referencia a {@link IGameContext},
 * garantizando desacoplamiento total respecto a la clase concreta
 * que implementa la lógica principal del juego.
 * </p>
 *
 * <p>
 * Renderers gestionados:
 * </p>
 * <ul>
 *     <li>{@link CardRenderer} – Renderiza las cartas seleccionables.</li>
 *     <li>{@link HealthBarRenderer} – Renderiza barras de vida.</li>
 *     <li>{@link WaterBarRenderer} – Renderiza la barra de agua.</li>
 *     <li>{@link GameOverRenderer} – Renderiza la pantalla de Game Over.</li>
 *     <li>{@link GameRenderer} – Renderiza el escenario y entidades principales.</li>
 * </ul>
 *
 * <p>
 * Esta clase funciona como un punto de acceso único al sistema
 * de renderizado, simplificando la gestión desde la pantalla
 * principal del juego.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class GameRendererManager {

    /** Renderizador de cartas seleccionables. */
    public final CardRenderer cardRenderer;

    /** Renderizador de barras de vida. */
    public final HealthBarRenderer healthBarRenderer;

    /** Renderizador de barra de agua. */
    public final WaterBarRenderer waterBarRenderer;

    /** Renderizador de pantalla Game Over. */
    public final GameOverRenderer gameOverRenderer;

    /** Renderizador principal del juego. */
    public final GameRenderer gameRenderer;

    /**
     * Constructor del administrador de renderers.
     *
     * <p>
     * Inicializa todos los renderizadores necesarios para
     * el funcionamiento visual del juego.
     * </p>
     *
     * @param ctx      contexto del juego
     * @param batch    SpriteBatch compartido
     * @param shape    ShapeRenderer compartido
     * @param viewport Viewport activo del juego
     */
    public GameRendererManager(IGameContext ctx,
                               SpriteBatch batch,
                               ShapeRenderer shape,
                               Viewport viewport) {

        this.cardRenderer      = new CardRenderer(ctx);
        this.healthBarRenderer = new HealthBarRenderer(ctx);
        this.waterBarRenderer  = new WaterBarRenderer(ctx);
        this.gameOverRenderer  = new GameOverRenderer(batch, shape, viewport);
        this.gameRenderer      = new GameRenderer(ctx, batch);
    }

    /**
     * Renderiza el estado normal del juego.
     */
    public void renderGame() {
        gameRenderer.render();
    }

    /**
     * Renderiza la pantalla de Game Over.
     *
     * @param victory indica si el resultado fue victoria o derrota
     */
    public void renderGameOver(boolean victory) {
        gameOverRenderer.render(victory);
    }

    /**
     * Maneja la entrada del usuario en la pantalla de Game Over.
     *
     * @param ctx contexto del juego
     */
    public void handleGameOverInput(IGameContext ctx) {
        gameOverRenderer.handleInput(ctx);
    }

    /**
     * Libera los recursos gráficos necesarios.
     *
     * <p>
     * Actualmente solo requiere liberar la barra de agua,
     * ya que los demás renderers reutilizan recursos globales.
     * </p>
     */
    public void dispose() {
        waterBarRenderer.dispose();
    }
}
