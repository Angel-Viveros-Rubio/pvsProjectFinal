package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Renderiza barras de vida de plantas y enemigos.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class HealthBarRenderer {

    private final IGameContext ctx;

    public HealthBarRenderer(IGameContext ctx) {
        this.ctx = ctx;
    }

    public void render(SpriteBatch batch) {
        // Plantas
        for (Plant p : ctx.getPlants()) {
            float percent   = 1f - (p.getResistanceTimer() / p.getMaxResistanceTime());
            float barPlantX = p.getX() - 20f;
            float barPlantY = p.getY() + 40f;
            batch.draw(Assets.emptyBar,  barPlantX,       barPlantY, 50f,           9f);
            batch.draw(Assets.healthFill, barPlantX,       barPlantY, 50f * percent, 9f);
            batch.draw(Assets.heartIcon,  barPlantX - 10f, barPlantY - 5f, 15f, 15f);
        }

        // Slimes
        for (Enemy e : ctx.getEnemies()) {
            float percent   = (float) e.getHealth() / e.getMaxHealth();
            float barSlimeX = e.getX() - 60f;
            float barSlimeY = e.getY() + 60f;
            batch.draw(Assets.emptyBar,   barSlimeX,       barSlimeY, 120f,           12f);
            batch.draw(Assets.healthFill, barSlimeX,       barSlimeY, 120f * percent, 12f);
            batch.draw(Assets.heartIcon,  barSlimeX - 10f, barSlimeY - 6f, 25f, 25f);
        }
    }
}
