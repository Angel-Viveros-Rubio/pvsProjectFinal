package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.ui.screens.IGameContext;

/**
 * Renderiza la barra de agua.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class WaterBarRenderer {

    private final IGameContext ctx;
    private final Texture[]    frames = new Texture[10];

    private static final float X          = 0f;
    private static final float Y          = 20f;
    private static final float WIDTH      = 450f;
    private static final float HEIGHT     = 90f;
    private static final float MAX_WATER  = 20f;

    public WaterBarRenderer(IGameContext ctx) {
        this.ctx = ctx;
        for (int i = 1; i <= 10; i++) {
            frames[i - 1] = new Texture(Gdx.files.internal("ui/waterBar/WaterBar(" + i + ").png"));
        }
    }

    public void render(SpriteBatch batch) {
        float current    = ctx.getManagers().waterManager.getCurrentWater();
        int   frameIndex = (int) ((MAX_WATER - current) / (MAX_WATER / 10f));
        frameIndex = Math.max(0, Math.min(9, frameIndex));
        batch.draw(frames[frameIndex], X, Y, WIDTH, HEIGHT);
    }

    public void dispose() {
        for (Texture frame : frames) frame.dispose();
    }
}
