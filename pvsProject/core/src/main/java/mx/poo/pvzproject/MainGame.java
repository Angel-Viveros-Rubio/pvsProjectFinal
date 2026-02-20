package mx.poo.pvzproject;

import com.badlogic.gdx.Game;
import mx.poo.pvzproject.gameProcess.managers.RenderSystem;
import mx.poo.pvzproject.ui.screens.GameScreenManager;
import mx.poo.pvzproject.ui.screens.MenuScreen;
import mx.poo.pvzproject.ui.utils.Assets;

/**
 * Clase principal del juego.
 * Solo coordina pantallas y maneja recursos compartidos.
 */
public class MainGame extends Game {

    public RenderSystem renderSystem;

    @Override
    public void create() {
        renderSystem = new RenderSystem();
        Assets.load();
        setScreen(new MenuScreen(this));
    }

    public void startGame() {
        setScreen(new GameScreenManager(this));
    }

    @Override
    public void dispose() {
        if (renderSystem != null) renderSystem.dispose();
        Assets.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}
