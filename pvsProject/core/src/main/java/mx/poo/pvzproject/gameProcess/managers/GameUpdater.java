package mx.poo.pvzproject.gameProcess.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapDefense;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapWave;
import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.utils.Constants;

import java.util.Iterator;

import static mx.poo.pvzproject.ui.utils.Constants.CARD_HEIGHT;

/**
 * Encapsula la lógica de actualización del juego en cada frame.
 *
 * <p>
 * Esta clase representa el núcleo del ciclo de actualización (update loop)
 * del juego. Se ejecuta una vez por frame y coordina todos los sistemas
 * lógicos necesarios para mantener el estado dinámico de la partida.
 * </p>
 *
 * <p>
 * Responsabilidades principales:
 * </p>
 * <ul>
 *     <li>Actualizar recursos (agua y cooldowns).</li>
 *     <li>Procesar entrada del jugador.</li>
 *     <li>Gestionar colocación de plantas.</li>
 *     <li>Actualizar plantas, enemigos y proyectiles.</li>
 *     <li>Controlar colisiones.</li>
 *     <li>Gestionar defensas y olas de jabón.</li>
 *     <li>Determinar condiciones de victoria y derrota.</li>
 * </ul>
 *
 * <p>
 * Depende exclusivamente de {@link IGameContext}, lo que garantiza
 * bajo acoplamiento y facilita pruebas, mantenimiento y extensibilidad.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class GameUpdater {

    /** Contexto del juego. */
    private final IGameContext ctx;

    /** Viewport activo para convertir coordenadas de entrada. */
    private final Viewport viewport;

    /** Temporizador inicial del nivel para controlar la victoria. */
    private float startLevelTimer = 0f;

    /**
     * Constructor del actualizador del juego.
     *
     * @param ctx       contexto del juego
     * @param viewport  viewport activo
     */
    public GameUpdater(IGameContext ctx, Viewport viewport) {
        this.ctx = ctx;
        this.viewport = viewport;
    }

    /**
     * Ejecuta la actualización lógica del juego para un frame.
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    public void update(float delta) {

        startLevelTimer += delta;

        // =============================
        // Actualización de recursos
        // =============================

        ctx.getManagers().waterManager.update(delta, ctx.getPlants());

        ctx.getCooldowns().setCorn(Math.max(0f, ctx.getCooldowns().getCorn() - delta));
        ctx.getCooldowns().setPapa(Math.max(0f, ctx.getCooldowns().getPapa() - delta));
        ctx.getCooldowns().setWater(Math.max(0f, ctx.getCooldowns().getWater() - delta));
        ctx.getCooldowns().setRedBom(Math.max(0f, ctx.getCooldowns().getRedBom() - delta));
        ctx.getCooldowns().setLilyPad(Math.max(0f, ctx.getCooldowns().getLilyPad() - delta));
        ctx.getCooldowns().setMaceta(Math.max(0f, ctx.getCooldowns().getMaceta() - delta));
        ctx.getCooldowns().setCampanilla(Math.max(0f, ctx.getCooldowns().getCampanilla() - delta));
        ctx.getCooldowns().setChampi(Math.max(0f, ctx.getCooldowns().getChampi() - delta));

        // =============================
        // Defensas de jabón
        // =============================

        for (SoapDefense soap : ctx.getSoapDefenses()) {
            soap.tryActivate(ctx);
        }

        // =============================
        // Entrada: selección de cartas
        // =============================

        if (Gdx.input.justTouched()) {

            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(touchPos);

            if      (touchPos.x >= Constants.CARD_X1 && touchPos.x < Constants.CARD_X1 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(0);
            else if (touchPos.x >= Constants.CARD_X2 && touchPos.x < Constants.CARD_X2 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(1);
            else if (touchPos.x >= Constants.CARD_X3 && touchPos.x < Constants.CARD_X3 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(2);
            else if (touchPos.x >= Constants.CARD_X4 && touchPos.x < Constants.CARD_X4 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(3);
            else if (touchPos.x >= Constants.CARD_X5 && touchPos.x < Constants.CARD_X5 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(4);
            else if (touchPos.x >= Constants.CARD_X6 && touchPos.x < Constants.CARD_X6 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(5);
            else if (touchPos.x >= Constants.CARD_X7 && touchPos.x < Constants.CARD_X7 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(6);
            else if (touchPos.x >= Constants.CARD_X8 && touchPos.x < Constants.CARD_X8 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(7);
            else if (touchPos.x >= Constants.CARD_X9 && touchPos.x < Constants.CARD_X9 + Constants.CARD_WIDTH && touchPos.y >= Constants.CARDS_Y && touchPos.y < Constants.CARDS_Y + CARD_HEIGHT) ctx.setSelectedPlant(8);
        }

        // =============================
        // Colocación de plantas
        // =============================

        ctx.getManagers().plantPlacer.handleInput();

        // =============================
        // Actualización de entidades
        // =============================

        for (Plant p : ctx.getPlants()) {
            p.update(delta);
            p.act(delta, ctx.getProjectiles(), ctx.getEnemies());
        }

        ctx.getManagers().spawner.update(delta, ctx.getEnemies());

        Iterator<SoapWave> waveIter = ctx.getSoapWaves().iterator();
        while (waveIter.hasNext()) {
            SoapWave wave = waveIter.next();
            wave.update(delta, ctx);
            if (!wave.isActive()) waveIter.remove();
        }

        for (Projectile p : ctx.getProjectiles()) p.update(delta);
        for (Enemy e : ctx.getEnemies()) e.update(delta);

        // =============================
        // Colisiones
        // =============================

        Iterator<Projectile> projIter = ctx.getProjectiles().iterator();
        while (projIter.hasNext()) {
            Projectile p = projIter.next();
            Iterator<Enemy> enemyIter = ctx.getEnemies().iterator();
            while (enemyIter.hasNext()) {
                Enemy e = enemyIter.next();
                if (Math.abs(p.getX() - e.getX()) < 50 &&
                    Math.abs(p.getY() - e.getY()) < 50) {

                    e.takeDamage(p.getDamage());
                    projIter.remove();

                    if (e.isDead()) enemyIter.remove();
                    break;
                }
            }
        }

        // =============================
        // Limpieza de entidades
        // =============================

        ctx.getProjectiles().removeIf(Projectile::isOffScreen);
        ctx.getEnemies().removeIf(e -> e.isOffScreen() || e.isDead());
        ctx.getPlants().removeIf(Plant::isDead);

        // =============================
        // Condición de victoria
        // =============================

        if (startLevelTimer > 2.0f &&
            ctx.getManagers().spawner.isLevelComplete(ctx.getEnemies())) {

            ctx.setVictory(true);
            ctx.setDefeat(false);
            ctx.setGameActive(false);
            ctx.markGameOverTriggered();
        }

        // =============================
        // Condición de derrota
        // =============================

        for (Enemy e : ctx.getEnemies()) {
            if (e.getX() < 0) {
                ctx.setDefeat(true);
                ctx.setVictory(false);
                ctx.setGameActive(false);
                break;
            }
        }
    }
}
