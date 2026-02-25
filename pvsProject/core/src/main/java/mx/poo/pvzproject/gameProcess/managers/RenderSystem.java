package mx.poo.pvzproject.gameProcess.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Sistema centralizado de renderizado del juego.
 *
 * <p>
 * Esta clase encapsula todos los componentes necesarios para
 * realizar el renderizado gráfico en pantalla, manteniendo
 * el desacoplamiento entre la lógica del juego y la capa visual.
 * </p>
 *
 * <p>
 * Componentes gestionados:
 * </p>
 * <ul>
 *     <li>{@link SpriteBatch} para renderizar texturas.</li>
 *     <li>{@link ShapeRenderer} para dibujar formas primitivas.</li>
 *     <li>{@link OrthographicCamera} para controlar la vista del mundo.</li>
 *     <li>{@link Viewport} adaptable al tamaño de ventana.</li>
 *     <li>{@link BitmapFont} y {@link GlyphLayout} para renderizado de texto.</li>
 * </ul>
 *
 * <p>
 * Utiliza un {@link FitViewport} para mantener la proporción
 * del mundo definida en {@link Constants}, independientemente
 * del tamaño de la ventana.
 * </p>
 *
 * <p>
 * Este sistema permite:
 * </p>
 * <ul>
 *     <li>Centralizar la configuración gráfica.</li>
 *     <li>Facilitar el manejo de cámara.</li>
 *     <li>Gestionar correctamente el redimensionamiento.</li>
 *     <li>Evitar duplicación de recursos gráficos.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class RenderSystem {

    /** SpriteBatch utilizado para dibujar texturas. */
    public final SpriteBatch batch;

    /** ShapeRenderer utilizado para dibujar formas. */
    public final ShapeRenderer shapeRenderer;

    /** Cámara ortográfica del juego. */
    public final OrthographicCamera camera;

    /** Viewport adaptable que mantiene proporción del mundo. */
    public final Viewport viewport;

    /** Fuente utilizada para renderizar texto. */
    public final BitmapFont font;

    /** Layout auxiliar para medir texto. */
    public final GlyphLayout layout;

    /**
     * Constructor del sistema de renderizado.
     *
     * <p>
     * Inicializa todos los componentes gráficos necesarios,
     * configura el viewport y posiciona la cámara en el centro
     * del mundo.
     * </p>
     */
    public RenderSystem() {

        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();

        this.camera = new OrthographicCamera();

        this.viewport = new FitViewport(
            Constants.WORLD_WIDTH,
            Constants.WORLD_HEIGHT,
            camera
        );

        this.viewport.apply();

        this.camera.position.set(
            Constants.WORLD_WIDTH / 2f,
            Constants.WORLD_HEIGHT / 2f,
            0
        );
    }

    /**
     * Actualiza la cámara y sincroniza las matrices
     * de proyección del SpriteBatch y ShapeRenderer.
     *
     * <p>
     * Debe llamarse antes de cualquier operación de renderizado
     * para asegurar que todos los elementos se dibujen con la
     * transformación correcta.
     * </p>
     */
    public void updateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    /**
     * Ajusta el viewport cuando cambia el tamaño de la ventana.
     *
     * @param width  nuevo ancho de la ventana
     * @param height nuevo alto de la ventana
     */
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    /**
     * Libera los recursos gráficos asociados.
     *
     * <p>
     * Debe llamarse al cerrar el juego para evitar fugas de memoria.
     * </p>
     */
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
