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
 * Lógica de actualización del juego por frame.
 * Depende de IGameContext (no de GameScreenManager directamente).
 */
public class GameUpdater {

    private final IGameContext ctx;
    private final Viewport     viewport;
    private float startLevelTimer = 0f;
    /**
     * Encapsula la lógica de actualización del juego en cada frame.
     *
     * Esta clase se encarga de:
     * - Actualizar recursos (agua y cooldowns).
     * - Procesar entrada del jugador.
     * - Actualizar plantas, enemigos y proyectiles.
     * - Gestionar colisiones.
     * - Controlar condiciones de victoria y derrota.
     *
     * Depende únicamente de {@link IGameContext}, evitando acoplamiento
     * directo con la clase principal que implementa el estado del juego.
     */
    public GameUpdater(IGameContext ctx, Viewport viewport) {
        this.ctx      = ctx;
        this.viewport = viewport;
    }

    public void update(float delta) {
        startLevelTimer += delta;

        // Agua y cooldowns
        ctx.getManagers().waterManager.update(delta, ctx.getPlants());

        ctx.getCooldowns().corn       = Math.max(0f, ctx.getCooldowns().corn       - delta);
        ctx.getCooldowns().papa       = Math.max(0f, ctx.getCooldowns().papa       - delta);
        ctx.getCooldowns().water      = Math.max(0f, ctx.getCooldowns().water      - delta);
        ctx.getCooldowns().redBom     = Math.max(0f, ctx.getCooldowns().redBom     - delta);
        ctx.getCooldowns().lilyPad    = Math.max(0f, ctx.getCooldowns().lilyPad    - delta);
        ctx.getCooldowns().maceta     = Math.max(0f, ctx.getCooldowns().maceta     - delta);
        ctx.getCooldowns().campanilla = Math.max(0f, ctx.getCooldowns().campanilla - delta);
        ctx.getCooldowns().champi     = Math.max(0f, ctx.getCooldowns().champi     - delta);

        // Soap defenses
        for (SoapDefense soap : ctx.getSoapDefenses()) {
            soap.tryActivate(ctx);
        }

        // Input selección de cartas
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

        // Colocación de plantas
        ctx.getManagers().plantPlacer.handleInput();

        // Update plantas
        for (Plant p : ctx.getPlants()) {
            p.update(delta);
            p.act(delta, ctx.getProjectiles(), ctx.getEnemies());
        }

        // Spawner
        ctx.getManagers().spawner.update(delta, ctx.getEnemies());

        // SoapWaves
        Iterator<SoapWave> waveIter = ctx.getSoapWaves().iterator();
        while (waveIter.hasNext()) {
            SoapWave wave = waveIter.next();
            wave.update(delta, ctx);
            if (!wave.isActive()) waveIter.remove();
        }

        // Contacto enemigo-planta
        for (Enemy e : ctx.getEnemies()) {
            Plant target = null;
            float closestDistance = Float.MAX_VALUE;
            for (Plant p : ctx.getPlants()) {
                if (Math.abs(e.getY() - p.getY()) < 80 && p.getX() > e.getX()) {
                    float distance = p.getX() - e.getX();
                    if (distance < closestDistance) {
                        closestDistance = distance;
                        target = p;
                    }
                }
            }
            if (target != null && closestDistance <= 100) {
                target.increaseResistanceTimer(delta);
                e.setEating(true);
            } else {
                e.setEating(false);
            }
        }

        for (Projectile p : ctx.getProjectiles()) p.update(delta);
        for (Enemy e     : ctx.getEnemies())      e.update(delta);

        // Colisiones proyectil-enemigo
        Iterator<Projectile> projIter = ctx.getProjectiles().iterator();
        while (projIter.hasNext()) {
            Projectile p = projIter.next();
            Iterator<Enemy> enemyIter = ctx.getEnemies().iterator();
            while (enemyIter.hasNext()) {
                Enemy e = enemyIter.next();
                if (Math.abs(p.getX() - e.getX()) < 50 && Math.abs(p.getY() - e.getY()) < 50) {
                    e.takeDamage(p.getDamage());
                    projIter.remove();
                    if (e.isDead()) enemyIter.remove();
                    break;
                }
            }
        }

        // Limpieza
        ctx.getProjectiles().removeIf(Projectile::isOffScreen);
        ctx.getEnemies().removeIf(e -> e.isOffScreen() || e.isDead());
        ctx.getPlants().removeIf(Plant::isDead);

        // Victoria
        if (startLevelTimer > 2.0f && ctx.getManagers().spawner.isLevelComplete(ctx.getEnemies())) {
            ctx.setVictory(true);
            ctx.setDefeat(false);
            ctx.setGameActive(false);
            ctx.markGameOverTriggered();
        }

        // Derrota
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
