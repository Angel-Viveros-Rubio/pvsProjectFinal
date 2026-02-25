package mx.poo.pvzproject.gameProcess.managers;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.spawn.Wave;

import java.util.ArrayList;

/**
 * Gestiona las oleadas (waves) de enemigos dentro de un nivel.
 *
 * <p>
 * Esta clase controla el flujo progresivo del nivel mediante
 * una secuencia ordenada de {@link Wave}.
 * </p>
 *
 * <p>
 * Responsabilidades principales:
 * </p>
 * <ul>
 *     <li>Mantener la lista de oleadas del nivel.</li>
 *     <li>Actualizar la oleada activa.</li>
 *     <li>Avanzar automáticamente a la siguiente oleada
 *     cuando la actual finaliza.</li>
 *     <li>Determinar cuándo el nivel ha sido completado.</li>
 * </ul>
 *
 * <p>
 * El nivel se considera finalizado cuando:
 * </p>
 * <ul>
 *     <li>No quedan oleadas pendientes.</li>
 *     <li>No existen enemigos activos en pantalla.</li>
 * </ul>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class WaveManager {

    /** Lista de oleadas que componen el nivel. */
    private ArrayList<Wave> waves;

    /** Índice de la oleada actual. */
    private int currentWaveIndex = 0;

    /** Referencia a la oleada actualmente activa. */
    private Wave currentWave;

    /**
     * Constructor del administrador de oleadas.
     *
     * @param waves lista de oleadas que conforman el nivel
     */
    public WaveManager(ArrayList<Wave> waves) {
        this.waves = waves;

        if (!waves.isEmpty()) {
            currentWave = waves.get(0);
        }
    }

    /**
     * Actualiza la oleada actual y controla el avance
     * entre oleadas.
     *
     * <p>
     * Si la oleada activa termina y no quedan enemigos
     * en pantalla, se avanza automáticamente a la siguiente.
     * </p>
     *
     * @param delta    tiempo transcurrido desde el último frame
     * @param enemies  lista actual de enemigos activos
     */
    public void update(float delta, ArrayList<Enemy> enemies) {

        if (currentWave == null) return;

        currentWave.update(delta, enemies);

        if (currentWave != null && currentWave.isComplete() && enemies.isEmpty()) {

            currentWaveIndex++;

            if (currentWaveIndex < waves.size()) {
                currentWave = waves.get(currentWaveIndex);
            } else {
                currentWave = null; // Nivel terminado
            }
        }
    }

    /**
     * Determina si el nivel ha sido completado.
     *
     * @param enemies lista actual de enemigos activos
     * @return true si no quedan oleadas ni enemigos
     */
    public boolean isLevelComplete(ArrayList<Enemy> enemies) {
        return currentWave == null && enemies.isEmpty();
    }

    /**
     * Obtiene el número de la oleada actual (base 1).
     *
     * @return número de oleada actual
     */
    public int getCurrentWave() {
        return currentWaveIndex + 1;
    }

    /**
     * Obtiene el total de oleadas del nivel.
     *
     * @return cantidad total de oleadas
     */
    public int getTotalWaves() {
        return waves.size();
    }
}
