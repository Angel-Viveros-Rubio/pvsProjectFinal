package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.ui.screens.IGameContext;

/**
 * Agrupa todos los renderers del juego.
 * Todos los renderers reciben IGameContext: cero dependencias a GameScreenManager.
 */
public class GameRendererManager {
    public final CardRenderer cardRenderer;
    public final HealthBarRenderer healthBarRenderer;
    public final WaterBarRenderer waterBarRenderer;
    public final GameOverRenderer gameOverRenderer;
    public final GameRenderer gameRenderer;

    public GameRendererManager(IGameContext ctx, SpriteBatch batch, ShapeRenderer shape, Viewport viewport) {
        this.cardRenderer      = new CardRenderer(ctx);
        this.healthBarRenderer = new HealthBarRenderer(ctx);
        this.waterBarRenderer  = new WaterBarRenderer(ctx);
        this.gameOverRenderer  = new GameOverRenderer(batch, shape, viewport);
        this.gameRenderer      = new GameRenderer(ctx, batch);
    }

    public void renderGame() {
        gameRenderer.render();
    }

    public void renderGameOver(boolean victory) {
        gameOverRenderer.render(victory);
    }

    public void handleGameOverInput(IGameContext ctx) {
        gameOverRenderer.handleInput(ctx);
    }

    public void dispose() {
        waterBarRenderer.dispose();
    }
}
