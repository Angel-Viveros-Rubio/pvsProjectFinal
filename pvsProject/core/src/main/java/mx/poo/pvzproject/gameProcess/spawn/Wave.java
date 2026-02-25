package mx.poo.pvzproject.gameProcess.spawn;

import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.ui.utils.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Representa una oleada de enemigos dentro de un nivel.
 *
 * <p>
 * Una oleada puede contener múltiples grupos de enemigos,
 * cada uno con su propia configuración de:
 * </p>
 * <ul>
 *     <li>Tipo de enemigo.</li>
 *     <li>Cantidad total a generar.</li>
 *     <li>Intervalo de aparición (spawn delay).</li>
 * </ul>
 *
 * <p>
 * La clase gestiona automáticamente:
 * </p>
 * <ul>
 *     <li>Temporizadores individuales por tipo de enemigo.</li>
 *     <li>Creación progresiva de instancias.</li>
 *     <li>Control de finalización de la oleada.</li>
 * </ul>
 *
 * <p>
 * La creación de enemigos se realiza mediante reflexión,
 * invocando un constructor con parámetros (x, y).
 * </p>
 *
 * <p>
 * Forma parte del sistema de progresión controlado por
 * el {@code WaveManager}.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class Wave {

    /**
     * Clase interna que almacena la configuración de spawn
     * para un tipo específico de enemigo.
     */
    private static class SpawnInfo {

        /** Tipo de enemigo a generar. */
        Class<? extends Enemy> type;

        /** Cantidad total a generar. */
        int count;

        /** Intervalo entre spawns. */
        float interval;

        /** Cantidad ya generada. */
        int spawned = 0;

        /** Temporizador acumulado. */
        float timer = 0f;

        /**
         * Constructor de la configuración de spawn.
         *
         * @param type     clase del enemigo
         * @param count    cantidad total a generar
         * @param interval intervalo entre apariciones
         */
        SpawnInfo(Class<? extends Enemy> type, int count, float interval) {
            this.type = type;
            this.count = count;
            this.interval = interval;
        }
    }

    /** Lista de configuraciones de spawn para la oleada. */
    private final ArrayList<SpawnInfo> spawns = new ArrayList<>();

    /** Generador de números aleatorios para seleccionar carriles. */
    private final Random random = new Random();

    /**
     * Agrega un grupo de enemigos a la oleada.
     *
     * @param enemy    clase del enemigo
     * @param count    cantidad total a generar
     * @param interval intervalo entre apariciones
     * @return instancia actual de la oleada (permite encadenamiento)
     */
    public Wave add(Class<? extends Enemy> enemy, int count, float interval) {
        spawns.add(new SpawnInfo(enemy, count, interval));
        return this;
    }

    /**
     * Actualiza la oleada y genera enemigos progresivamente.
     *
     * <p>
     * Cada grupo mantiene su propio temporizador.
     * Cuando el temporizador alcanza el intervalo definido,
     * se genera una nueva instancia del enemigo correspondiente.
     * </p>
     *
     * @param delta    tiempo transcurrido desde el último frame
     * @param enemies  lista global de enemigos activos
     */
    public void update(float delta, ArrayList<Enemy> enemies) {

        for (SpawnInfo s : spawns) {

            if (s.spawned >= s.count) continue;

            s.timer += delta;

            if (s.timer >= s.interval) {

                s.timer -= s.interval;

                int lane = random.nextInt(Constants.LANES);
                float x = 1280f + 100f;
                float y = Constants.getPlantY(lane);

                try {
                    enemies.add(
                        s.type
                            .getConstructor(float.class, float.class)
                            .newInstance(x, y)
                    );

                    s.spawned++;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Indica si la oleada ya terminó de generar todos
     * los enemigos configurados.
     *
     * @return true si todos los grupos alcanzaron su cantidad total
     */
    public boolean isComplete() {

        for (SpawnInfo s : spawns) {
            if (s.spawned < s.count) {
                return false;
            }
        }

        return true;
    }
}
