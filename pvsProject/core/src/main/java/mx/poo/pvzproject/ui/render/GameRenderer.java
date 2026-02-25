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
 * Renderizador principal del juego.
 *
 * <p>
 * Esta clase se encarga de dibujar todos los elementos visuales
 * correspondientes a la partida en curso.
 * </p>
 *
 * <p>
 * Depende exclusivamente de {@link IGameContext} para obtener
 * el estado actual del juego, evitando acoplamiento directo
 * con la clase concreta que administra la pantalla principal.
 * </p>
 *
 * <p>
 * Elementos renderizados (en orden de capa):
 * </p>
 * <ol>
 *     <li>Fondo según el nivel actual.</li>
 *     <li>Defensas de jabón.</li>
 *     <li>Plantas (excepto {@link Champi}).</li>
 *     <li>Proyectiles.</li>
 *     <li>Enemigos.</li>
 *     <li>{@link Champi} (capa superior).</li>
 *     <li>Olas de jabón.</li>
 *     <li>Barra de selección y cartas.</li>
 *     <li>Texto y barra de agua.</li>
 *     <li>Barras de vida (renderizado adicional).</li>
 * </ol>
 *
 * <p>
 * El orden de renderizado es crítico para mantener la
 * jerarquía visual correcta entre entidades.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class GameRenderer {

    /** Contexto del juego. */
    private final IGameContext ctx;

    /** SpriteBatch compartido para renderizado. */
    private final SpriteBatch batch;

    /**
     * Constructor del renderizador principal.
     *
     * @param ctx   contexto del juego
     * @param batch SpriteBatch compartido
     */
    public GameRenderer(IGameContext ctx, SpriteBatch batch) {
        this.ctx   = ctx;
        this.batch = batch;
    }

    /**
     * Renderiza todos los elementos visuales del juego
     * respetando el orden de capas definido.
     */
    public void render() {

        batch.begin();

        // Fondo según nivel
        if (Constants.CURRENT_LEVEL <= 3) {
            batch.draw(Assets.backgroundDay, 0, 0,
                Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        } else if (Constants.CURRENT_LEVEL <= 6) {
            batch.draw(Assets.backgroundJungle, 0, 0,
                Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        } else {
            batch.draw(Assets.backgroundNight, 0, 0,
                Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        }

        // Defensas de jabón
        for (SoapDefense soap : ctx.getSoapDefenses()) {
            soap.draw(batch);
        }

        // Plantas (excepto Champi)
        for (Plant p : ctx.getPlants()) {
            if (!(p instanceof Champi)) {
                p.draw(batch);
            }
        }

        // Proyectiles
        for (Projectile p : ctx.getProjectiles()) {
            p.draw(batch);
        }

        // Enemigos
        for (Enemy e : ctx.getEnemies()) {
            e.draw(batch);
        }

        // Champi en capa superior
        for (Plant p : ctx.getPlants()) {
            if (p instanceof Champi) {
                p.draw(batch);
            }
        }

        // Olas de jabón
        for (SoapWave wave : ctx.getSoapWaves()) {
            wave.draw(batch);
        }

        // Barra de selección
        batch.draw(Assets.selectionBarBackground,
            Constants.BAR_X,
            Constants.BAR_Y,
            Constants.BAR_WIDTH,
            Constants.BAR_HEIGHT);

        ctx.getRenderers().cardRenderer.render(batch);

        // Texto de agua
        batch.setColor(Color.WHITE);

        String waterText = String.valueOf(
            (int) ctx.getManagers().waterManager.getCurrentWater()
        );

        ctx.getRenderSystem().layout.setText(
            ctx.getRenderSystem().font,
            waterText
        );

        float waterTextX = Constants.CARD_X1
            - ctx.getRenderSystem().layout.width
            - 70f;

        float waterTextY = Constants.CARDS_Y
            + Constants.CARD_HEIGHT / 18f
            + ctx.getRenderSystem().layout.height / 2f;

        ctx.getRenderSystem().font.draw(
            batch,
            waterText,
            waterTextX,
            waterTextY
        );

        ctx.getRenderers().waterBarRenderer.render(batch);

        batch.end();

        // Barras de vida (segundo batch)
        batch.begin();
        ctx.getRenderers().healthBarRenderer.render(batch);
        batch.end();
    }
}
