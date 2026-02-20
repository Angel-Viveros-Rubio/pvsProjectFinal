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
 * Pantalla principal del juego.
 *
 * Esta clase implementa la interfaz {@link Screen} de LibGDX y
 * representa el menú inicial donde el jugador puede:
 * - Iniciar el modo Aventura.
 * - Cambiar el idioma del juego (Español / Inglés).
 *
 * Se encarga de:
 * - Cargar y dibujar el fondo y los botones.
 * - Detectar toques del usuario.
 * - Cambiar el idioma actual.
 * - Iniciar la partida cuando se presiona el botón correspondiente.
 *
 * Utiliza el sistema de cámara y viewport del juego para adaptar
 * correctamente los elementos a la pantalla.
 */
public class MenuScreen implements Screen {

    private final MainGame game;

    private SpriteBatch batch;
    private ShapeRenderer shape;

    private Texture background;
    private Texture btnAventuraEn;
    private Texture btnAventuraEs;
    private Texture btnLenguajeEn;
    private Texture btnLenguajeEs;

    private static final float BTN_AVE_X  = 620f;
    private static final float BTN_AVE_Y  = 450f;
    private static final float BTN_AVE_W  = 555f;
    private static final float BTN_AVE_H  = 55f;

    private static final float BTN_LANG_X = 950f;
    private static final float BTN_LANG_Y = 30f;
    private static final float BTN_LANG_W = 165f;
    private static final float BTN_LANG_H = 140f;

    private Rectangle rectAventura;
    private Rectangle rectLenguaje;

    public MenuScreen(MainGame game) {
        this.game = game;
    }
    /**
     * Se ejecuta una vez cuando esta pantalla se muestra.
     * Aquí se inicializan los recursos gráficos (texturas,
     * batch, shapeRenderer) y las áreas de los botones.
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
     * Se encarga de:
     * - Limpiar la pantalla.
     * - Detectar interacción táctil.
     * - Cambiar el idioma si se presiona el botón correspondiente.
     * - Iniciar el juego si se presiona el botón de aventura.
     * - Dibujar el fondo y los botones.
     *
     * @param delta Tiempo transcurrido desde el último frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.renderSystem.camera.update();
        batch.setProjectionMatrix(game.renderSystem.camera.combined);
        shape.setProjectionMatrix(game.renderSystem.camera.combined);

        // Touch
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.renderSystem.viewport.unproject(touch);

            if (rectAventura.contains(touch.x, touch.y)) {
                dispose();
                game.startGame();
                return;
            }
            if (rectLenguaje.contains(touch.x, touch.y)) {
                Constants.CURRENT_LANGUAGE = (Constants.CURRENT_LANGUAGE == Language.ES)
                    ? Language.EN : Language.ES;
            }
        }

        // Fondo
        batch.begin();
        batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        batch.end();

        // Botones encima con alpha
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.begin();
        Texture btnAve  = (Constants.CURRENT_LANGUAGE == Language.ES) ? btnAventuraEs : btnAventuraEn;
        batch.draw(btnAve,  BTN_AVE_X,  BTN_AVE_Y,  BTN_AVE_W,  BTN_AVE_H);
        Texture btnLang = (Constants.CURRENT_LANGUAGE == Language.ES) ? btnLenguajeEn : btnLenguajeEs;
        batch.draw(btnLang, BTN_LANG_X, BTN_LANG_Y, BTN_LANG_W, BTN_LANG_H);
        batch.end();
    }

    @Override
    public void resize(int w, int h) {
        game.renderSystem.viewport.update(w, h, true);
    }

    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}

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
