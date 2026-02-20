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
 * Define de manera progresiva:
 * - Tipos de enemigos que aparecen.
 * - Cantidad de enemigos por ola.
 * - Intervalos de aparición.
 *
 * Cada nivel devuelve una lista ordenada de {@link Wave}
 * que será utilizada por el sistema de oleadas.
 *
 * La dificultad aumenta gradualmente introduciendo:
 * - NormalSlime (enemigo base).
 * - SlimeTanque (más resistencia).
 * - SlimeRapido (mayor velocidad).
 * - SlimeColossus (mini-jefe).
 */
public class LevelFactory {

    public static ArrayList<Wave> getLevel(int level) {
        //System.out.println("Cargando nivel: " + level);
        ArrayList<Wave> waves = new ArrayList<>();

        // ── NIVEL 1 ─────────────────────────────────────────────────────────────
        if (level == 1) {
            // Ola 1: pequeña intro con slimes lentos
            waves.add(new Wave()
                .add(NormalSlime.class, 8, 5f));

            // Ola 2: un poco más, el jugador ya tiene plantas
            waves.add(new Wave()
                .add(NormalSlime.class, 12, 4f));
        }

        // ── NIVEL 2 ─────────────────────────────────────────────────────────────
        else if (level == 2) {
            waves.add(new Wave()
                .add(NormalSlime.class, 15, 3.5f));

            // Presenta el SlimeTanque: solo 2, para que el jugador los conozca
            waves.add(new Wave()
                .add(NormalSlime.class, 12, 3f)
                .add(SlimeTanque.class, 2, 10f));
        }

        // ── NIVEL 3 ─────────────────────────────────────────────────────────────
        else if (level == 3) {
            waves.add(new Wave()
                .add(NormalSlime.class, 18, 3f));

            waves.add(new Wave()
                .add(SlimeTanque.class, 4, 7f)
                .add(NormalSlime.class, 15, 2.5f));
        }

        // ── NIVEL 4 (jungle empieza) ─────────────────────────────────────────────
        else if (level == 4) {
            // Presenta el SlimeRapido: solo 2, despacio
            waves.add(new Wave()
                .add(NormalSlime.class, 20, 2.5f)
                .add(SlimeTanque.class, 4, 7f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 3, 10f)
                .add(NormalSlime.class, 10, 3f));
        }

        // ── NIVEL 5 ─────────────────────────────────────────────────────────────
        else if (level == 5) {
            waves.add(new Wave()
                .add(NormalSlime.class, 25, 2.2f)
                .add(SlimeTanque.class, 5, 6f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 5, 7f)
                .add(NormalSlime.class, 15, 2.5f));
        }

        // ── NIVEL 6 ─────────────────────────────────────────────────────────────
        else if (level == 6) {
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 2f)
                .add(SlimeTanque.class, 7, 5f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 7, 6f)
                .add(NormalSlime.class, 15, 2f));
        }

        // ── NIVEL 7 ─────────────────────────────────────────────────────────────
        else if (level == 7) {
            // Ola 1: presión mixta manejable
            waves.add(new Wave()
                .add(NormalSlime.class, 20, 2f)
                .add(SlimeTanque.class, 5, 5f));

            // Ola 2: añade divisores cuando el jugador ya está listo
            waves.add(new Wave()
                .add(SlimeRapido.class, 6, 5f)
                .add(NormalSlime.class, 15, 2f));

            // Ola 3: final de nivel con presión real
            waves.add(new Wave()
                .add(NormalSlime.class, 20, 1.8f)
                .add(SlimeTanque.class, 8, 4f)
                .add(SlimeRapido.class, 4, 6f));
        }

        // ── NIVEL 8 ─────────────────────────────────────────────────────────────
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

        // ── NIVEL 9 ─────────────────────────────────────────────────────────────
        else if (level == 9) {
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.7f)
                .add(SlimeTanque.class, 8, 4f));

            waves.add(new Wave()
                .add(SlimeRapido.class, 10, 4f)
                .add(NormalSlime.class, 20, 1.7f));

            // Ola final intensa como preludio al nivel 10
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.5f)
                .add(SlimeTanque.class, 12, 3f)
                .add(SlimeRapido.class, 8, 4f));
        }

        else if (level == 10) {
            // Ola 1: presión inicial para que el jugador coloque torres
            waves.add(new Wave()
                .add(NormalSlime.class, 30, 1.8f)
                .add(SlimeTanque.class, 8, 4f));

            // Ola 2: divisores rápidos para poner presión en lanes
            waves.add(new Wave()
                .add(SlimeRapido.class, 10, 3.5f)
                .add(NormalSlime.class, 20, 1.8f));

            // Ola 3: PRIMER Colossus + escolta ligera (épico pero manejable)
            waves.add(new Wave()
                .add(SlimeColossus.class, 1, 20f)   // aparece solo, impacto visual
                .add(NormalSlime.class, 20, 1.8f)
                .add(SlimeTanque.class, 6, 4f));

            // Ola 4 (FINAL): 2 Colossus con escolta reducida
            // Con 600 HP c/u y 3 CornShooters por lane → ~25-30s para eliminarlos
            waves.add(new Wave()
                .add(SlimeColossus.class, 2, 15f)
                .add(SlimeRapido.class, 8, 4f)
                .add(NormalSlime.class, 15, 2f));
        }

        return waves;
    }
}
