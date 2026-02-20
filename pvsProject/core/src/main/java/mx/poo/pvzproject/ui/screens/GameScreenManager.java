package mx.poo.pvzproject.ui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import mx.poo.pvzproject.MainGame;
import mx.poo.pvzproject.gameProcess.entities.enemies.Enemy;
import mx.poo.pvzproject.gameProcess.entities.plants.Plant;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapDefense;
import mx.poo.pvzproject.gameProcess.entities.soap.SoapWave;
import mx.poo.pvzproject.gameProcess.managers.RenderSystem;
import mx.poo.pvzproject.gameProcess.entities.projectiles.Projectile;
import mx.poo.pvzproject.gameProcess.managers.SubBossManager;
import mx.poo.pvzproject.ui.render.GameRendererManager;
import mx.poo.pvzproject.ui.utils.Assets;
import mx.poo.pvzproject.ui.utils.Constants;
import mx.poo.pvzproject.ui.utils.Cooldowns;

import java.util.ArrayList;

/**
 * Pantalla principal donde se ejecuta la lógica del juego.
 *
 * Esta clase implementa {@link Screen} de LibGDX y la interfaz
 * {@link IGameContext}, actuando como núcleo central del estado
 * del juego.
 *
 * Su responsabilidad principal es:
 * - Gestionar el ciclo de vida de la pantalla (show, render, resize, dispose).
 * - Mantener y exponer el estado global del juego.
 * - Coordinar los subsistemas como:
 *      - {@link SubBossManager} (lógica del juego).
 *      - {@link GameRendererManager} (renderizado).
 * - Administrar la transición entre niveles.
 * - Controlar los estados de victoria, derrota y reinicio.
 *
 * Además, al implementar {@link IGameContext}, permite que los
 * subsistemas trabajen contra una interfaz y no contra esta
 * clase concreta, evitando dependencias circulares y mejorando
 * la modularidad del proyecto.
 */
public class GameScreenManager implements Screen, IGameContext {

    private final MainGame game;
    private boolean transitioning = false;

    public SubBossManager managers;
    public GameRendererManager renderers;
    private boolean gameOverJustTriggered = false;

    private boolean gameActive   = true;
    private boolean victory      = false;
    private boolean defeat       = false;
    private int     selectedPlant = 0;
    private final Cooldowns cd   = new Cooldowns();

    private final ArrayList<Plant>       plants       = new ArrayList<>();
    private final ArrayList<Enemy>       enemies      = new ArrayList<>();
    private final ArrayList<Projectile>  projectiles  = new ArrayList<>();
    private final ArrayList<SoapWave>    soapWaves    = new ArrayList<>();
    private final ArrayList<SoapDefense> soapDefenses = new ArrayList<>();

    public GameScreenManager(MainGame game) {
        this.game = game;
    }
    /**
     * Se ejecuta cuando la pantalla se muestra por primera vez.
     *
     * Inicializa:
     * - Los managers principales del juego.
     * - El sistema de renderizado.
     * - Las defensas Soap en cada línea.
     * - La música correspondiente al nivel actual.
     */
    @Override
    public void show() {
        managers  = new SubBossManager(this);
        renderers = new GameRendererManager(this,
            game.renderSystem.batch,
            game.renderSystem.shapeRenderer,
            game.renderSystem.viewport);

        float soapX = Constants.GRID_START_X - 80f;
        for (int i = 0; i < Constants.LANES; i++) {
            soapDefenses.add(new SoapDefense(soapX, Constants.getPlantY(i), i));
        }
        updateBackgroundMusic();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.renderSystem.updateCamera();

        if (gameActive) managers.update(delta);

        renderers.renderGame();

        if (!gameActive) {
            renderers.renderGameOver(victory);
            if (gameOverJustTriggered) { gameOverJustTriggered = false; return; }
            renderers.handleGameOverInput(this);
        }
    }

    @Override public void resize(int w, int h) { if (game.renderSystem != null) game.renderSystem.resize(w, h); }
    @Override public void pause()  {}
    @Override public void resume() {}
    @Override public void hide()   {}
    @Override public void dispose() { if (renderers != null) renderers.dispose(); }

    // ==================== GESTIÓN DEL JUEGO ====================
    /**
     * Avanza al siguiente nivel del juego.
     *
     * - Incrementa el nivel actual si no se ha alcanzado el máximo.
     * - Actualiza la música de fondo.
     * - Reinicia el estado del juego.
     *
     * Evita múltiples transiciones simultáneas mediante
     * la variable de control "transitioning".
     */
    @Override
    public void advanceToNextLevel() {
        if (transitioning) return;
        transitioning = true;
        if (Constants.CURRENT_LEVEL < Constants.MAX_LEVEL) Constants.CURRENT_LEVEL++;
        updateBackgroundMusic();
        restartAfterGameOver();
    }
    /**
     * Reinicia completamente el estado del juego tras una derrota
     * o al cambiar de nivel.
     *
     * Limpia todas las entidades activas, reinicia cooldowns,
     * managers, defensas y música.
     */
    @Override
    public void restartAfterGameOver() {
        gameActive = true;
        victory    = false;
        defeat     = false;

        plants.clear();
        enemies.clear();
        projectiles.clear();
        soapWaves.clear();

        selectedPlant = 0;
        cd.reset();

        managers = new SubBossManager(this);
        soapDefenses.forEach(SoapDefense::reset);
        renderers.gameOverRenderer.reset();

        Assets.currentBackgroundMusic.setPosition(0f);
        Assets.currentBackgroundMusic.play();
        transitioning = false;
    }
    /**
     * Cambia la música de fondo dependiendo del nivel actual.
     *
     * Niveles:
     * - 1–3  : Música de día.
     * - 4–6  : Música de jungla.
     * - 7–9  : Música nocturna.
     * - 10   : Música de jefe.
     */
    private void updateBackgroundMusic() {
        Assets.currentBackgroundMusic.stop();
        if      (Constants.CURRENT_LEVEL == 10) Assets.currentBackgroundMusic = Assets.bossMusic;
        else if (Constants.CURRENT_LEVEL <=  3) Assets.currentBackgroundMusic = Assets.dayMusic;
        else if (Constants.CURRENT_LEVEL <=  6) Assets.currentBackgroundMusic = Assets.jungleMusic;
        else                                     Assets.currentBackgroundMusic = Assets.nightMusic;
        Assets.currentBackgroundMusic.play();
    }

    // ==================== IGameContext ====================

    /**
     * Métodos que exponen el estado del juego a los subsistemas.
     * Permiten acceder y modificar información sin depender
     * directamente de esta clase concreta.
     */
    @Override public ArrayList<Plant>       getPlants()       { return plants; }
    @Override public ArrayList<Enemy>       getEnemies()      { return enemies; }
    @Override public ArrayList<Projectile>  getProjectiles()  { return projectiles; }
    @Override public ArrayList<SoapWave>    getSoapWaves()    { return soapWaves; }
    @Override public ArrayList<SoapDefense> getSoapDefenses() { return soapDefenses; }

    @Override public boolean   isVictory()        { return victory; }

    @Override public int       getSelectedPlant() { return selectedPlant; }
    @Override public Cooldowns getCooldowns()     { return cd; }

    @Override public void setGameActive(boolean a) { this.gameActive    = a; }
    @Override public void setVictory(boolean v)    { this.victory       = v; }
    @Override public void setDefeat(boolean d)     { this.defeat        = d; }
    @Override public void setSelectedPlant(int p)  { this.selectedPlant = p; }
    @Override public void markGameOverTriggered()  { gameOverJustTriggered = true; }

    @Override public SubBossManager getManagers()     { return managers; }
    @Override public GameRendererManager getRenderers()    { return renderers; }
    @Override public RenderSystem getRenderSystem() { return game.renderSystem; }

    /** Solo para código dentro del paquete ui/ que necesita MainGame directamente. */
    public MainGame getGame() { return game; }
}
