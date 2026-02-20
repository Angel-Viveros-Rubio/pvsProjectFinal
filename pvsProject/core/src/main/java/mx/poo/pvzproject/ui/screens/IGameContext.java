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
 * Interfaz que define el contrato de acceso al estado del juego.
 *
 * Su propósito es permitir que los diferentes subsistemas
 * (renderizadores, actualizadores, sistema Soap, managers, etc.)
 * puedan consultar y modificar el estado del juego sin depender
 * directamente de la clase principal que lo controla
 *
 * Esto ayuda a:
 * - Evitar dependencias circulares.
 * - Mejorar la modularidad del código.
 * - Facilitar mantenimiento y escalabilidad.
 *
 * Cualquier clase que implemente esta interfaz actúa como
 * contexto central del juego.
 */
public interface IGameContext {

    // ── Entidades ────────────────────────────────────────────
    ArrayList<Plant>       getPlants();
    ArrayList<Enemy>       getEnemies();
    ArrayList<Projectile>  getProjectiles();
    ArrayList<SoapWave>    getSoapWaves();
    ArrayList<SoapDefense> getSoapDefenses();

    // ── Estado del juego ─────────────────────────────────────

    boolean isVictory();
    int     getSelectedPlant();
    Cooldowns getCooldowns();

    void setGameActive(boolean active);
    void setVictory(boolean victory);
    void setDefeat(boolean defeat);
    void setSelectedPlant(int plant);
    void markGameOverTriggered();

    // ── Subsistemas ──────────────────────────────────────────
    SubBossManager getManagers();
    GameRendererManager getRenderers();   // necesario en GameRenderer para cardRenderer, etc.
    RenderSystem       getRenderSystem();

    // ── Navegación ───────────────────────────────────────────
    void advanceToNextLevel();
    void restartAfterGameOver();
}
