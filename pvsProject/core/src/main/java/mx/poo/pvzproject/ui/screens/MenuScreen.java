package mx.poo.pvzproject.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import mx.poo.pvzproject.MainGame;
import mx.poo.pvzproject.ui.utils.Constants;
import mx.poo.pvzproject.ui.utils.Language;

/**
 * Pantalla principal (menú inicial) del juego.
 *
 * <p>
 * Implementa la interfaz {@link Screen} de LibGDX y representa
 * la primera interfaz con la que interactúa el jugador.
 * </p>
 *
 * <p>
 * Desde esta pantalla el usuario puede:
 * </p>
 * <ul>
 *     <li>Iniciar el modo Aventura.</li>
 *     <li>Cambiar el idioma del juego (Español / Inglés).</li>
 * </ul>
 *
 * <p>
 * La clase se encarga de:
 * </p>
 * <ul>
 *     <li>Cargar los recursos gráficos del menú.</li>
 *     <li>Renderizar fondo y botones.</li>
 *     <li>Detectar interacción táctil.</li>
 *     <li>Actualizar el idioma global.</li>
 *     <li>Iniciar la transición hacia la pantalla de juego.</li>
 * </ul>
 *
 * <p>
 * Utiliza el sistema de cámara y viewport proporcionado por
 * {@link MainGame} para adaptar correctamente los elementos
 * a diferentes resoluciones.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class MenuScreen implements Screen {

    /** Referencia al controlador principal del juego. */
    private final MainGame game;

    /** SpriteBatch para renderizado de texturas. */
    private SpriteBatch batch;

    /** ShapeRenderer para posibles depuraciones o formas. */
    private ShapeRenderer shape;

    /** Textura de fondo del menú principal. */
    private Texture background;

    /** Botón Aventura en inglés. */
    private Texture btnAventuraEn;

    /** Botón Aventura en español. */
    private Texture btnAventuraEs;

    /** Botón de cambio de idioma en inglés. */
    private Texture btnLenguajeEn;

    /** Botón de cambio de idioma en español. */
    private Texture btnLenguajeEs;

    /** Coordenadas y dimensiones del botón Aventura. */
    private static final float BTN_AVE_X  = 620f;
    private static final float BTN_AVE_Y  = 450f;
    private static final float BTN_AVE_W  = 555f;
    private static final float BTN_AVE_H  = 55f;

    /** Coordenadas y dimensiones del botón de idioma. */
    private static final float BTN_LANG_X = 950f;
    private static final float BTN_LANG_Y = 30f;
    private static final float BTN_LANG_W = 165f;
    private static final float BTN_LANG_H = 140f;

    /** Área interactiva del botón Aventura. */
    private Rectangle rectAventura;

    /** Área interactiva del botón de idioma. */
    private Rectangle rectLenguaje;

    /**
     * Constructor de la pantalla de menú.
     *
     * @param game referencia al juego principal
     */
    public MenuScreen(MainGame game) {
        this.game = game;
    }

    /**
     * Se ejecuta una vez cuando la pantalla se muestra.
     *
     * <p>
     * Inicializa los recursos gráficos, crea el SpriteBatch,
     * el ShapeRenderer y define las áreas de interacción
     * de los botones.
     * </p>
     */
    @Override
    public void show() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();

        background    = new Texture(Gdx.files.internal("textures/Menu/Menuprincipal.png"));
        btnAventuraEn = new Texture(Gdx.files.internal("textures/Menu/BotonAventura1_en.png"));
        btnAventuraEs = new Texture(Gdx.files.internal("textures/Menu/BotonAventura1_es.png"));
        btnLenguajeEn = new Texture(Gdx.files.internal("textures/Menu/BotonLenguaje1_en.png"));
        btnLenguajeEs = new Texture(Gdx.files.internal("textures/Menu/BotonLenguaje1_es.png"));

        rectAventura = new Rectangle(BTN_AVE_X, BTN_AVE_Y, BTN_AVE_W, BTN_AVE_H);
        rectLenguaje = new Rectangle(BTN_LANG_X, BTN_LANG_Y, BTN_LANG_W, BTN_LANG_H);
    }

    /**
     * Método principal de renderizado.
     *
     * <p>
     * Se encarga de:
     * </p>
     * <ul>
     *     <li>Limpiar la pantalla.</li>
     *     <li>Actualizar la cámara.</li>
     *     <li>Detectar interacción del usuario.</li>
     *     <li>Cambiar el idioma si corresponde.</li>
     *     <li>Iniciar el juego si se presiona Aventura.</li>
     *     <li>Dibujar fondo y botones.</li>
     * </ul>
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.renderSystem.camera.update();
        batch.setProjectionMatrix(game.renderSystem.camera.combined);
        shape.setProjectionMatrix(game.renderSystem.camera.combined);

        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.renderSystem.viewport.unproject(touch);

            if (rectAventura.contains(touch.x, touch.y)) {
                dispose();
                game.startGame();
                return;
            }

            if (rectLenguaje.contains(touch.x, touch.y)) {
                Constants.CURRENT_LANGUAGE =
                    (Constants.CURRENT_LANGUAGE == Language.ES)
                        ? Language.EN
                        : Language.ES;
            }
        }

        batch.begin();
        batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.begin();
        Texture btnAve  = (Constants.CURRENT_LANGUAGE == Language.ES) ? btnAventuraEs : btnAventuraEn;
        batch.draw(btnAve, BTN_AVE_X, BTN_AVE_Y, BTN_AVE_W, BTN_AVE_H);

        Texture btnLang = (Constants.CURRENT_LANGUAGE == Language.ES) ? btnLenguajeEn : btnLenguajeEs;
        batch.draw(btnLang, BTN_LANG_X, BTN_LANG_Y, BTN_LANG_W, BTN_LANG_H);
        batch.end();
    }

    /**
     * Ajusta el viewport cuando cambia el tamaño de la ventana.
     *
     * @param w nuevo ancho
     * @param h nuevo alto
     */
    @Override
    public void resize(int w, int h) {
        game.renderSystem.viewport.update(w, h, true);
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

    /**
     * Libera los recursos gráficos utilizados por esta pantalla.
     * Debe ejecutarse antes de cambiar a otra pantalla
     * para evitar fugas de memoria.
     */
    @Override
    public void dispose() {
        if (batch != null)         batch.dispose();
        if (shape != null)         shape.dispose();
        if (background != null)    background.dispose();
        if (btnAventuraEn != null) btnAventuraEn.dispose();
        if (btnAventuraEs != null) btnAventuraEs.dispose();
        if (btnLenguajeEn != null) btnLenguajeEn.dispose();
        if (btnLenguajeEs != null) btnLenguajeEs.dispose();
    }
}
