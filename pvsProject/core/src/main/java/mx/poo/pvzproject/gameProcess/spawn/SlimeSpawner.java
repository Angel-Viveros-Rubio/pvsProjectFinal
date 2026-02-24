package mx.poo.pvzproject.gameProcess.spawn;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.managers.WaveManager;

import java.util.ArrayList;

/**
 * Encapsula la lógica de generación de enemigos tipo slime.
 *
 * <p>
 * Esta clase actúa como intermediario entre el sistema principal
 * del juego y el {@link WaveManager}, delegando en él la gestión
 * completa de las oleadas.
 * </p>
 *
 * <p>
 * Su propósito es desacoplar el sistema de actualización general
 * del juego respecto a la implementación concreta del manejo
 * de oleadas.
 * </p>
 *
 * <p>
 * Responsabilidades:
 * </p>
 * <ul>
 *     <li>Actualizar las oleadas activas.</li>
 *     <li>Delegar la generación progresiva de enemigos.</li>
 *     <li>Consultar si el nivel ha sido completado.</li>
 * </ul>
 *
 * <p>
 * Funciona como una capa de abstracción ligera, manteniendo
 * una arquitectura modular y extensible.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class SlimeSpawner {

    /** Administrador de oleadas asociado al nivel actual. */
    private WaveManager waveManager;

    /**
     * Constructor del generador de enemigos.
     *
     * @param waveManager gestor de oleadas que controla
     *                    la lógica de aparición de enemigos
     */
    public SlimeSpawner(WaveManager waveManager) {
        this.waveManager = waveManager;
    }

    /**
     * Actualiza el estado de las oleadas activas.
     *
     * <p>
     * Delegación directa al {@link WaveManager}.
     * </p>
     *
     * @param delta    tiempo transcurrido desde el último frame
     * @param enemies  lista actual de enemigos activos
     */
    public void update(float delta, ArrayList<Enemy> enemies) {
        waveManager.update(delta, enemies);
    }

    /**
     * Indica si el nivel ha sido completado.
     *
     * @param enemies lista actual de enemigos activos
     * @return true si no quedan oleadas ni enemigos
     */
    public boolean isLevelComplete(ArrayList<Enemy> enemies) {
        return waveManager.isLevelComplete(enemies);
    }
}
