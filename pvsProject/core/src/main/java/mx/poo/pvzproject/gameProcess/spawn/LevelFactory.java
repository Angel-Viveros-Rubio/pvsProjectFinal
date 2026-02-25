package mx.poo.pvzproject.gameProcess.spawn;

import mx.poo.pvzproject.gameProcess.entities.enemies.slimes.NormalSlime;
import mx.poo.pvzproject.gameProcess.entities.enemies.slimes.SlimeColossus;
import mx.poo.pvzproject.gameProcess.entities.enemies.slimes.SlimeRapido;
import mx.poo.pvzproject.gameProcess.entities.enemies.slimes.SlimeTanque;

import java.util.ArrayList;

/**
 * Fábrica encargada de construir la configuración de oleadas
 * para cada nivel del juego.
 *
 * <p>
 * Implementa el patrón Factory para centralizar la definición
 * de la progresión de dificultad del juego.
 * </p>
 *
 * <p>
 * Cada nivel devuelve una lista ordenada de {@link Wave},
 * que posteriormente será utilizada por el {@code WaveManager}
 * para controlar el flujo del nivel.
 * </p>
 *
 * <p>
 * La progresión de dificultad se construye introduciendo
 * gradualmente nuevos tipos de enemigos:
 * </p>
 * <ul>
 *     <li>{@link NormalSlime} – Enemigo base.</li>
 *     <li>{@link SlimeTanque} – Mayor resistencia.</li>
 *     <li>{@link SlimeRapido} – Mayor velocidad.</li>
 *     <li>{@link SlimeColossus} – Mini-jefe de alto impacto.</li>
 * </ul>
 *
 * <p>
 * La configuración de cada oleada define:
 * </p>
 * <ul>
 *     <li>Tipo de enemigo.</li>
 *     <li>Cantidad total.</li>
 *     <li>Intervalo de aparición (spawn delay).</li>
 * </ul>
 *
 * <p>
 * Este diseño permite modificar el balance del juego
 * sin afectar la lógica interna de los managers.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class LevelFactory {

    /**
     * Construye la configuración completa de un nivel.
     *
     * <p>
     * Genera dinámicamente la lista de {@link Wave}
     * correspondiente al número de nivel indicado.
     * </p>
     *
     * @param level número del nivel solicitado
     * @return lista ordenada de oleadas para ese nivel
     */
    public static ArrayList<Wave> getLevel(int level) {

        ArrayList<Wave> waves = new ArrayList<>();

        // ─────────────────────────────────────────────────────────────
        // Cada bloque define progresión de dificultad por nivel.
        // ─────────────────────────────────────────────────────────────

        if (level == 1) {
            waves.add(new Wave()
                .add(NormalSlime.class, 8, 5f));

            waves.add(new Wave()
                .add(NormalSlime.class, 12, 4f));
        }

        else if (level == 2) {
            waves.add(new Wave()
                .add(NormalSlime.class, 15, 3.5f));

            waves.add(new Wave()
                .add(NormalSlime.class, 12, 3f)
                .add(SlimeTanque.class, 2, 10f));
        }

        else if (level == 3) {
            waves.add(new Wave()
                .add(NormalSlime.class, 18, 3f));

            waves.add(new Wave()
                .add(SlimeTanque.class, 4, 7f)
                .add(NormalSlime.class, 15, 2.5f));
        }

        else if (level == 4) {
            waves.add(new Wave()
                .add(NormalSlime.class, 20, 2.5f)
                .add(SlimeTanque.class, 4, 7f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 3, 10f)
                .add(NormalSlime.class, 10, 3f));
        }

        else if (level == 5) {
            waves.add(new Wave()
                .add(NormalSlime.class, 25, 2.2f)
                .add(SlimeTanque.class, 5, 6f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 5, 7f)
                .add(NormalSlime.class, 15, 2.5f));
        }

        else if (level == 6) {
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 2f)
                .add(SlimeTanque.class, 7, 5f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 7, 6f)
                .add(NormalSlime.class, 15, 2f));
        }

        else if (level == 7) {
            waves.add(new Wave()
                .add(NormalSlime.class, 20, 2f)
                .add(SlimeTanque.class, 5, 5f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 6, 5f)
                .add(NormalSlime.class, 15, 2f));

            waves.add(new Wave()
                .add(NormalSlime.class, 20, 1.8f)
                .add(SlimeTanque.class, 8, 4f)
                .add(SlimeRapido.class, 4, 6f));
        }

        else if (level == 8) {
            waves.add(new Wave()
                .add(NormalSlime.class, 25, 1.8f)
                .add(SlimeTanque.class, 6, 4.5f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 8, 4.5f)
                .add(NormalSlime.class, 18, 1.8f));

            waves.add(new Wave()
                .add(NormalSlime.class, 25, 1.6f)
                .add(SlimeTanque.class, 10, 3.5f)
                .add(SlimeRapido.class, 6, 5f));
        }

        else if (level == 9) {
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.7f)
                .add(SlimeTanque.class, 8, 4f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 10, 4f)
                .add(NormalSlime.class, 20, 1.7f));

            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.5f)
                .add(SlimeTanque.class, 12, 3f)
                .add(SlimeRapido.class, 8, 4f));
        }

        else if (level == 10) {
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.8f)
                .add(SlimeTanque.class, 8, 4f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 10, 3.5f)
                .add(NormalSlime.class, 20, 1.8f));

            waves.add(new Wave()
                .add(SlimeColossus.class, 1, 20f)
                .add(NormalSlime.class, 20, 1.8f)
                .add(SlimeTanque.class, 6, 4f));

            waves.add(new Wave()
                .add(SlimeColossus.class, 2, 15f)
                .add(SlimeRapido.class, 8, 4f)
                .add(NormalSlime.class, 15, 2f));
        }

        return waves;
    }
}
