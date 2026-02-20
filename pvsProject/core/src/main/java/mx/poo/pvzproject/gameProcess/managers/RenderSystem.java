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
 * Encapsula y gestiona los componentes principales necesarios
 * para dibujar en pantalla:
 * - SpriteBatch para texturas.
 * - ShapeRenderer para formas.
 * - Cámara ortográfica.
 * - Viewport adaptable.
 * - Fuente y layout para texto.
 *
 * Permite mantener el renderizado desacoplado del resto
 * de la lógica del juego y facilita el manejo de cámara
 * y redimensionamiento de ventana.
 */
public class RenderSystem {
    public final SpriteBatch batch;
    public final ShapeRenderer shapeRenderer;
    public final OrthographicCamera camera;
    public final Viewport viewport;
    public final BitmapFont font;
    public final GlyphLayout layout;

    public RenderSystem() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        this.layout = new GlyphLayout();

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        this.viewport.apply();
        this.camera.position.set(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT / 2f, 0);
    }

    public void updateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}
