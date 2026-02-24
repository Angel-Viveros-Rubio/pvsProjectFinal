package mx.poo.pvzproject.gameProcess.managers; // ajusta si tu package es diferente

import java.util.ArrayList;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.plants.WaterPlant;

/**
 * Gestiona el recurso de agua del jugador.
 *
 * <p>
 * Esta clase administra el sistema económico principal del juego
 * basado en el recurso "agua".
 * </p>
 *
 * <p>
 * Responsabilidades:
 * </p>
 * <ul>
 *     <li>Mantener la cantidad actual de agua disponible.</li>
 *     <li>Regenerar agua automáticamente con el tiempo.</li>
 *     <li>Aumentar la velocidad de regeneración según la cantidad
 *     de {@link WaterPlant} activas.</li>
 *     <li>Validar y descontar el costo al colocar plantas.</li>
 * </ul>
 *
 * <p>
 * La regeneración ocurre en intervalos fijos definidos por
 * {@code WATER_REGEN_INTERVAL}. Cada planta de tipo
 * {@link WaterPlant} incrementa la velocidad de regeneración
 * en un 30%.
 * </p>
 *
 * <p>
 * Este sistema mantiene encapsulada la lógica económica
 * del juego, permitiendo modificaciones futuras sin afectar
 * otros sistemas.
 * </p>
 *
 * @author SmallJunior
 * @version 1.0
 */
public class WaterManager {

    /** Cantidad actual de agua disponible. */
    private float currentWater = 4f;

    /** Cantidad máxima de agua permitida. */
    public final float MAX_WATER = 20f;

    /** Temporizador interno para controlar la regeneración. */
    private float waterTimer = 0f;

    /** Intervalo base (en segundos) para regenerar agua. */
    private final float WATER_REGEN_INTERVAL = 1f;

    /**
     * Actualiza el sistema de regeneración de agua.
     *
     * <p>
     * La velocidad de regeneración aumenta según la cantidad
     * de plantas {@link WaterPlant} activas en el tablero.
     * </p>
     *
     * @param delta  tiempo transcurrido desde el último frame
     * @param plants lista actual de plantas en el juego
     */
    public void update(float delta, ArrayList<Plant> plants) {

        int waterPlantCount = 0;

        for (Plant p : plants) {
            if (p instanceof WaterPlant) {
                waterPlantCount++;
            }
        }

        float waterRegenMultiplier = 1f + 0.3f * waterPlantCount;

        waterTimer += delta * waterRegenMultiplier;

        if (waterTimer >= WATER_REGEN_INTERVAL) {
            waterTimer -= WATER_REGEN_INTERVAL;
            currentWater += 0.5f;
            currentWater = Math.min(currentWater, MAX_WATER);
        }
    }

    /**
     * Obtiene la cantidad actual de agua.
     *
     * @return agua disponible
     */
    public float getCurrentWater() {
        return currentWater;
    }

    /**
     * Verifica si el jugador puede pagar el costo especificado.
     *
     * @param cost costo de la planta
     * @return true si hay suficiente agua disponible
     */
    public boolean canAfford(int cost) {
        return currentWater >= cost;
    }

    /**
     * Descuenta agua al colocar una planta.
     *
     * @param cost costo de la planta
     */
    public void spendWater(int cost) {
        currentWater -= cost;
    }
}
