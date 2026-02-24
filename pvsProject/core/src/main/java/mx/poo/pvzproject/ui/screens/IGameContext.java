package mx.poo.pvzproject.ui.screens;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.managers.SubBossManager;
import mx.poo.pvzproject.gameProcess.managers.RenderSystem;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapDefense;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapWave;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.ui.render.GameRendererManager;
import mx.poo.pvzproject.ui.utils.Cooldowns;

import java.util.ArrayList;

/**
 * Interfaz que define el contrato de acceso y modificación
 * del estado central del juego.
 *
 * <p>
 * Su propósito es desacoplar los distintos subsistemas del juego
 * (renderizado, lógica, managers, sistema Soap, etc.) de la clase
 * principal que controla la ejecución.
 * </p>
 *
 * <p>
 * Esto permite:
 * </p>
 * <ul>
 *     <li>Evitar dependencias circulares.</li>
 *     <li>Mejorar la modularidad del sistema.</li>
 *     <li>Facilitar el mantenimiento y la escalabilidad.</li>
 *     <li>Permitir pruebas y extensiones más limpias.</li>
 * </ul>
 *
 * <p>
 * Cualquier clase que implemente esta interfaz actúa como
 * contexto central del juego y expone únicamente lo necesario
 * para interactuar con el estado global.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public interface IGameContext {

    // ── Entidades ────────────────────────────────────────────

    /**
     * Devuelve la lista de plantas activas en el juego.
     *
     * @return lista de {@link Plant}
     */
    ArrayList<Plant> getPlants();

    /**
     * Devuelve la lista de enemigos activos.
     *
     * @return lista de {@link Enemy}
     */
    ArrayList<Enemy> getEnemies();

    /**
     * Devuelve la lista de proyectiles activos.
     *
     * @return lista de {@link Projectile}
     */
    ArrayList<Projectile> getProjectiles();

    /**
     * Devuelve las oleadas activas del sistema Soap.
     *
     * @return lista de {@link SoapWave}
     */
    ArrayList<SoapWave> getSoapWaves();

    /**
     * Devuelve las defensas activas del sistema Soap.
     *
     * @return lista de {@link SoapDefense}
     */
    ArrayList<SoapDefense> getSoapDefenses();


    // ── Estado del juego ─────────────────────────────────────

    /**
     * Indica si el jugador ha alcanzado la victoria.
     *
     * @return {@code true} si el jugador ganó
     */
    boolean isVictory();

    /**
     * Devuelve el identificador de la planta seleccionada.
     *
     * @return índice o ID de la planta seleccionada
     */
    int getSelectedPlant();

    /**
     * Devuelve el sistema de enfriamientos (cooldowns).
     *
     * @return instancia de {@link Cooldowns}
     */
    Cooldowns getCooldowns();

    /**
     * Activa o desactiva la ejecución del juego.
     *
     * @param active {@code true} para activar la lógica del juego
     */
    void setGameActive(boolean active);

    /**
     * Establece el estado de victoria.
     *
     * @param victory {@code true} si el jugador ganó
     */
    void setVictory(boolean victory);

    /**
     * Establece el estado de derrota.
     *
     * @param defeat {@code true} si el jugador perdió
     */
    void setDefeat(boolean defeat);

    /**
     * Define la planta actualmente seleccionada.
     *
     * @param plant índice o ID de la planta
     */
    void setSelectedPlant(int plant);

    /**
     * Marca que el evento de Game Over ya fue activado,
     * evitando múltiples ejecuciones del mismo.
     */
    void markGameOverTriggered();


    // ── Subsistemas ──────────────────────────────────────────

    /**
     * Devuelve el administrador principal de managers secundarios.
     *
     * @return instancia de {@link SubBossManager}
     */
    SubBossManager getManagers();

    /**
     * Devuelve el administrador de renderizado.
     *
     * <p>
     * Es utilizado principalmente por el sistema de render
     * para acceder a renderizadores específicos como el
     * cardRenderer.
     * </p>
     *
     * @return instancia de {@link GameRendererManager}
     */
    GameRendererManager getRenderers();

    /**
     * Devuelve el sistema de render global (cámara, viewport, etc.).
     *
     * @return instancia de {@link RenderSystem}
     */
    RenderSystem getRenderSystem();


    // ── Navegación ───────────────────────────────────────────

    /**
     * Avanza al siguiente nivel del juego.
     */
    void advanceToNextLevel();

    /**
     * Reinicia el nivel actual después de un Game Over.
     */
    void restartAfterGameOver();
}
