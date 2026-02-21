package mx.poo.pvzproject.gameProcess.managers; // ajusta si tu package es diferente

import java.util.ArrayList;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.plants.WaterPlant;
/**
 * Gestiona el recurso de agua del jugador.
 *
 * Se encarga de:
 * - Mantener la cantidad actual de agua.
 * - Regenerar agua automáticamente con el tiempo.
 * - Aumentar la velocidad de regeneración según la cantidad
 *   de plantas tipo {@link WaterPlant} activas.
 * - Validar y descontar el costo de agua al colocar plantas.
 */
public class WaterManager {
    private float currentWater = 4f;
    public final float MAX_WATER = 20f;
    private float waterTimer = 0f;
    private final float WATER_REGEN_INTERVAL = 1f;

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

    public float getCurrentWater() {
        return currentWater;
    }

    public boolean canAfford(int cost) {
        return currentWater >= cost;
    }

    public void spendWater(int cost) {
        currentWater -= cost;
    }
}
