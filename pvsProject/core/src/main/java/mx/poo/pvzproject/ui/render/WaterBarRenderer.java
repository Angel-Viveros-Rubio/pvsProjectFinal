package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;

/**
 * Renderizador de la barra de agua del juego.
 *
 * <p>
 * Esta clase se encarga de mostrar visualmente la cantidad
 * actual de agua disponible para el jugador.
 * </p>
 *
 * <p>
 * La barra se representa mediante 10 frames (imágenes)
 * que simulan el nivel de llenado/vacío del recurso.
 * </p>
 *
 * <p>
 * Depende únicamente de {@link IGameContext} para obtener
 * el estado del agua a través del manager correspondiente,
 * evitando dependencia directa con la clase concreta
 * que gestiona el juego.
 * </p>
 *
 * <p>
 * El frame mostrado se calcula en función del valor actual
 * de agua respecto al máximo permitido.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class WaterBarRenderer {

    /** Contexto del juego para acceder al estado global. */
    private final IGameContext ctx;

    /** Arreglo de texturas que representan los distintos niveles de agua. */
    private final Texture[] frames = new Texture[10];

    /** Posición horizontal de la barra. */
    private static final float X = 0f;

    /** Posición vertical de la barra. */
    private static final float Y = 20f;

    /** Ancho de la barra renderizada. */
    private static final float WIDTH = 450f;

    /** Alto de la barra renderizada. */
    private static final float HEIGHT = 90f;

    /** Valor máximo de agua permitido. */
    private static final float MAX_WATER = 20f;

    /**
     * Constructor del renderizador de barra de agua.
     *
     * <p>
     * Carga los 10 frames gráficos que representan
     * los distintos niveles del recurso.
     * </p>
     *
     * @param ctx contexto del juego
     */
    protected WaterBarRenderer(IGameContext ctx) {
        this.ctx = ctx;

        for (int i = 1; i <= 10; i++) {
            frames[i - 1] = new Texture(
                Gdx.files.internal("ui/waterBar/WaterBar(" + i + ").png")
            );
        }
    }

    /**
     * Renderiza la barra de agua en pantalla.
     *
     * <p>
     * Calcula el índice del frame correspondiente según
     * la cantidad actual de agua disponible.
     * </p>
     *
     * @param batch SpriteBatch utilizado para dibujar la textura
     */
    public void render(SpriteBatch batch) {
        float current = ctx.getManagers()
            .waterManager
            .getCurrentWater();

        int frameIndex = (int) ((MAX_WATER - current) / (MAX_WATER / 10f));
        frameIndex = Math.max(0, Math.min(9, frameIndex));

        batch.draw(frames[frameIndex], X, Y, WIDTH, HEIGHT);
    }

    /**
     * Libera las texturas utilizadas por la barra de agua.
     * Debe llamarse al cerrar la pantalla o finalizar el juego
     * para evitar fugas de memoria.
     */
    protected void dispose() {
        for (Texture frame : frames) {
            frame.dispose();
        }
    }
}
