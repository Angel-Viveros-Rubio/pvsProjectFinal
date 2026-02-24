package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Renderizador de la pantalla de Game Over y Victoria.
 *
 * <p>
 * Esta clase se encarga de mostrar la interfaz visual que aparece
 * cuando la partida finaliza, ya sea por derrota o por victoria.
 * </p>
 *
 * <p>
 * Incluye:
 * </p>
 * <ul>
 *     <li>Un fondo oscuro semitransparente (overlay).</li>
 *     <li>Una imagen central indicando victoria o derrota.</li>
 *     <li>Un botón para avanzar de nivel o reintentar.</li>
 * </ul>
 *
 * <p>
 * El método {@link #handleInput(IGameContext)} recibe un
 * {@link IGameContext} para poder ejecutar acciones como
 * {@code advanceToNextLevel()} o {@code restartAfterGameOver()}
 * sin depender directamente de una implementación concreta.
 * </p>
 *
 * <p>
 * Esto mantiene el desacoplamiento respecto a la clase
 * principal que administra el juego.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class GameOverRenderer {

    /** SpriteBatch utilizado para dibujar texturas. */
    private final SpriteBatch batch;

    /** ShapeRenderer utilizado para dibujar el fondo oscuro. */
    private final ShapeRenderer shapeRenderer;

    /** Viewport necesario para convertir coordenadas táctiles. */
    private final Viewport viewport;

    /** Dimensiones del mundo del juego. */
    private static final float WORLD_WIDTH  = 1280f;
    private static final float WORLD_HEIGHT = 720f;

    /** Dimensiones del overlay central. */
    private static final float OVERLAY_WIDTH  = 800f;
    private static final float OVERLAY_HEIGHT = 500f;

    /** Posición del overlay central. */
    private static final float OVERLAY_X = WORLD_WIDTH  / 2f - OVERLAY_WIDTH  / 2f;
    private static final float OVERLAY_Y = WORLD_HEIGHT / 2f - OVERLAY_HEIGHT / 2f + 50f;

    /** Dimensiones del botón principal. */
    private static final float BUTTON_WIDTH  = 250f;
    private static final float BUTTON_HEIGHT = 100f;

    /** Posición del botón principal. */
    private static final float BUTTON_X = WORLD_WIDTH  / 2f - BUTTON_WIDTH  / 2f;
    private static final float BUTTON_Y = WORLD_HEIGHT / 2f - BUTTON_HEIGHT / 2f - 200f;

    /** Indica si la entrada ya fue procesada. */
    private boolean inputHandled = false;

    /**
     * Constructor del renderizador de Game Over.
     *
     * @param batch         SpriteBatch compartido
     * @param shapeRenderer ShapeRenderer compartido
     * @param viewport      Viewport activo del juego
     */
    public GameOverRenderer(SpriteBatch batch,
                            ShapeRenderer shapeRenderer,
                            Viewport viewport) {
        this.batch = batch;
        this.shapeRenderer = shapeRenderer;
        this.viewport = viewport;
    }

    /**
     * Renderiza la pantalla de fin de partida.
     *
     * @param isVictory {@code true} si el jugador ganó,
     *                  {@code false} si perdió
     */
    public void render(boolean isVictory) {

        // Fondo oscuro semitransparente
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.75f);
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();

        // Overlay y botón
        batch.begin();

        batch.draw(
            isVictory ? Assets.victoryOverlay : Assets.defeatOverlay,
            OVERLAY_X,
            OVERLAY_Y,
            OVERLAY_WIDTH,
            OVERLAY_HEIGHT
        );

        batch.draw(
            isVictory ? Assets.nextLevelButton : Assets.retryButton,
            BUTTON_X,
            BUTTON_Y,
            BUTTON_WIDTH,
            BUTTON_HEIGHT
        );

        batch.end();
    }

    /**
     * Maneja la entrada táctil sobre el botón principal.
     *
     * <p>
     * Si el jugador toca el botón:
     * </p>
     * <ul>
     *     <li>Avanza al siguiente nivel en caso de victoria.</li>
     *     <li>Reinicia la partida en caso de derrota.</li>
     * </ul>
     *
     * @param ctx contexto del juego
     * @return {@code true} si se procesó la entrada correctamente
     */
    protected boolean handleInput(IGameContext ctx) {

        if (inputHandled) return false;

        if (Gdx.input.justTouched()) {

            Vector3 touchPos = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
            );

            viewport.unproject(touchPos);

            if (touchPos.x >= BUTTON_X && touchPos.x <= BUTTON_X + BUTTON_WIDTH
                && touchPos.y >= BUTTON_Y && touchPos.y <= BUTTON_Y + BUTTON_HEIGHT) {

                inputHandled = true;

                if (ctx.isVictory()) {
                    ctx.advanceToNextLevel();
                } else {
                    ctx.restartAfterGameOver();
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Reinicia el estado interno del renderizador,
     * permitiendo que la entrada vuelva a procesarse
     * en una nueva pantalla de Game Over.
     */
    public void reset() {
        inputHandled = false;
    }
}
