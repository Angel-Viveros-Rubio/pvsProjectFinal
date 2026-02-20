package mx.poo.pvzproject.ui.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import mx.poo.pvzproject.gameProcess.entities.plants.Champi;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapDefense;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapWave;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Renderiza la pantalla principal del juego.
 * Depende de IGameContext en lugar de GameScreenManager directamente.
 */
public class GameRenderer {

    private final IGameContext ctx;
    private final SpriteBatch  batch;

    public GameRenderer(IGameContext ctx, SpriteBatch batch) {
        this.ctx   = ctx;
        this.batch = batch;
    }

    public void render() {
        batch.begin();

        // Fondo
        if      (Constants.CURRENT_LEVEL <= 3) batch.draw(Assets.backgroundDay,       0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        else if (Constants.CURRENT_LEVEL <= 6) batch.draw(Assets.backgroundJungle, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        else                                    batch.draw(Assets.backgroundNight,  0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        // Defensas jabón
        for (SoapDefense soap : ctx.getSoapDefenses()) soap.draw(batch);

        // Plantas (sin Champi primero)
        for (Plant p : ctx.getPlants()) {
            if (!(p instanceof Champi)) p.draw(batch);
        }

        // Proyectiles
        for (Projectile p : ctx.getProjectiles()) p.draw(batch);

        // Enemigos
        for (Enemy e : ctx.getEnemies()) e.draw(batch);

        // Champi al final (encima)
        for (Plant p : ctx.getPlants()) {
            if (p instanceof Champi) p.draw(batch);
        }

        // Olas jabón
        for (SoapWave wave : ctx.getSoapWaves()) wave.draw(batch);

        // Barra de selección y tarjetas
        batch.draw(Assets.selectionBarBackground, Constants.BAR_X, Constants.BAR_Y, Constants.BAR_WIDTH, Constants.BAR_HEIGHT);
        ctx.getRenderers().cardRenderer.render(batch);

        // Texto de agua
        batch.setColor(Color.WHITE);
        String waterText = String.valueOf((int) ctx.getManagers().waterManager.getCurrentWater());
        ctx.getRenderSystem().layout.setText(ctx.getRenderSystem().font, waterText);
        float waterTextX = Constants.CARD_X1 - ctx.getRenderSystem().layout.width - 70f;
        float waterTextY = Constants.CARDS_Y  + Constants.CARD_HEIGHT / 18f + ctx.getRenderSystem().layout.height / 2f;
        ctx.getRenderSystem().font.draw(batch, waterText, waterTextX, waterTextY);

        ctx.getRenderers().waterBarRenderer.render(batch);

        batch.end();

        // Barras de vida (batch separado)
        batch.begin();
        ctx.getRenderers().healthBarRenderer.render(batch);
        batch.end();
    }
}
