package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.ui.screens.IGameContext;
import mx.poo.pvzproject.gameProcess.spawn.LevelFactory;
import mx.poo.pvzproject.gameProcess.spawn.SlimeSpawner;
import mx.poo.pvzproject.ui.utils.Constants;

/**
 * Administrador central de los managers lógicos del juego.
 *
 * <p>
 * Esta clase actúa como contenedor y punto de coordinación
 * para todos los sistemas encargados de la lógica del juego.
 * </p>
 *
 * <p>
 * Su objetivo principal es:
 * </p>
 * <ul>
 *     <li>Agrupar los managers en una única estructura.</li>
 *     <li>Reducir el acoplamiento entre componentes.</li>
 *     <li>Evitar dependencias circulares con la pantalla principal.</li>
 * </ul>
 *
 * <p>
 * En lugar de depender directamente de una implementación concreta
 * del gestor de pantalla, recibe una referencia a {@link IGameContext},
 * lo que permite mantener una arquitectura desacoplada.
 * </p>
 *
 * <p>
 * Managers gestionados:
 * </p>
 * <ul>
 *     <li>{@link WaveManager} – Controla las oleadas del nivel.</li>
 *     <li>{@link SlimeSpawner} – Genera enemigos según la oleada.</li>
 *     <li>{@link WaterManager} – Administra el recurso agua.</li>
 *     <li>{@link PlantPlacer} – Gestiona la colocación de plantas.</li>
 *     <li>{@link GameUpdater} – Ejecuta la lógica de actualización por frame.</li>
 * </ul>
 *
 * <p>
 * Funciona como fachada lógica del sistema de juego.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SubBossManager {

    /** Administrador de oleadas del nivel actual. */
    public final WaveManager waveManager;

    /** Sistema de generación de enemigos. */
    public final SlimeSpawner spawner;

    /** Administrador del recurso agua. */
    public final WaterManager waterManager;

    /** Gestor de colocación de plantas. */
    public final PlantPlacer plantPlacer;

    /** Encapsula la lógica de actualización por frame. */
    public final GameUpdater gameUpdater;

    /**
     * Constructor del contenedor de managers.
     *
     * <p>
     * Inicializa todos los sistemas lógicos necesarios
     * para el funcionamiento del juego, utilizando el nivel
     * actual definido en {@link Constants}.
     * </p>
     *
     * @param ctx contexto del juego
     */
    public SubBossManager(IGameContext ctx) {

        this.waveManager = new WaveManager(
            LevelFactory.getLevel(Constants.CURRENT_LEVEL)
        );

        this.spawner = new SlimeSpawner(waveManager);

        this.waterManager = new WaterManager();

        this.plantPlacer = new PlantPlacer(ctx);

        this.gameUpdater = new GameUpdater(
            ctx,
            ctx.getRenderSystem().viewport
        );
    }

    /**
     * Ejecuta la actualización lógica del juego.
     *
     * <p>
     * Delegación directa al {@link GameUpdater},
     * manteniendo este manager como fachada simplificada.
     * </p>
     *
     * @param delta tiempo transcurrido desde el último frame
     */
    public void update(float delta) {
        gameUpdater.update(delta);
    }
}
