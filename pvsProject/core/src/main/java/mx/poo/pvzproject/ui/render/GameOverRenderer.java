package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Renderiza la pantalla de Game Over / Victoria.
 * handleInput recibe IGameContext para poder llamar
 * advanceToNextLevel / restartAfterGameOver sin acoplarse
 * a GameScreenManager concreto.
 */
public class GameOverRenderer {

    private final SpriteBatch   batch;
    private final ShapeRenderer shapeRenderer;
    private final Viewport      viewport;

    private static final float WORLD_WIDTH    = 1280f;
    private static final float WORLD_HEIGHT   = 720f;
    private static final float OVERLAY_WIDTH  = 800f;
    private static final float OVERLAY_HEIGHT = 500f;
    private static final float OVERLAY_X      = WORLD_WIDTH  / 2f - OVERLAY_WIDTH  / 2f;
    private static final float OVERLAY_Y      = WORLD_HEIGHT / 2f - OVERLAY_HEIGHT / 2f + 50f;
    private static final float BUTTON_WIDTH   = 250f;
    private static final float BUTTON_HEIGHT  = 100f;
    private static final float BUTTON_X       = WORLD_WIDTH  / 2f - BUTTON_WIDTH  / 2f;
    private static final float BUTTON_Y       = WORLD_HEIGHT / 2f - BUTTON_HEIGHT / 2f - 200f;

    private boolean inputHandled = false;

    public GameOverRenderer(SpriteBatch batch, ShapeRenderer shapeRenderer, Viewport viewport) {
        this.batch         = batch;
        this.shapeRenderer = shapeRenderer;
        this.viewport      = viewport;
    }

    public void render(boolean isVictory) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.75f);
        shapeRenderer.rect(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        shapeRenderer.end();

        batch.begin();
        batch.draw(isVictory ? Assets.victoryOverlay : Assets.defeatOverlay, OVERLAY_X, OVERLAY_Y, OVERLAY_WIDTH, OVERLAY_HEIGHT);
        batch.draw(isVictory ? Assets.nextLevelButton : Assets.retryButton,   BUTTON_X,  BUTTON_Y,  BUTTON_WIDTH,  BUTTON_HEIGHT);
        batch.end();
    }

    public boolean handleInput(IGameContext ctx) {
        if (inputHandled) return false;

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touchPos);

            if (touchPos.x >= BUTTON_X && touchPos.x <= BUTTON_X + BUTTON_WIDTH
                && touchPos.y >= BUTTON_Y && touchPos.y <= BUTTON_Y + BUTTON_HEIGHT) {
                inputHandled = true;
                if (ctx.isVictory()) ctx.advanceToNextLevel();
                else                 ctx.restartAfterGameOver();
                return true;
            }
        }
        return false;
    }

    public void reset() {
        inputHandled = false;
    }
}
