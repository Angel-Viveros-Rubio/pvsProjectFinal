package mx.poo.pvzproject.ui.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Clase encargada de cargar, almacenar y liberar
 * todos los recursos del juego (texturas y audio).
 *
 * Se centralizan aquí para evitar cargar recursos
 * múltiples veces y mantener el proyecto organizado.
 */
public class Assets {

    /* ===================== AUDIO ===================== */

    public static Music dayMusic;
    public static Music jungleMusic;
    public static Music nightMusic;
    public static Music bossMusic;
    public static Music currentBackgroundMusic;

    public static Music cornShootSound;
    public static Music petaloShootSound;
    public static Music explosionSound;

    /* ===================== FONDOS ===================== */

    public static Texture backgroundDay;
    public static Texture backgroundJungle;
    public static Texture backgroundNight;

    /* ===================== PROYECTILES ===================== */

    public static Texture popcornProjectile;
    public static Texture cornProjectileTexture;
    public static Texture petaloTexture;

    /* ===================== PLANTAS ===================== */

    public static Texture[] cornShooterFrames = new Texture[11];
    public static Animation<TextureRegion> cornShooterAnimation;

    public static Texture[] papaCompletaFrames = new Texture[16];
    public static Texture[] papaComida1Frames = new Texture[16];
    public static Texture[] papaComida2Frames = new Texture[16];

    public static Texture[] waterPlantFrames = new Texture[8];
    public static Texture[] redBomFrames = new Texture[11];
    public static Texture[] macetaFrames = new Texture[7];
    public static Texture[] campanillaFrames = new Texture[5];

    public static Texture[] champiNeutralFrames = new Texture[16];
    public static Texture[] champiAttackFrames = new Texture[4];
    public static Texture champiDetectedTexture;

    public static Texture lilyPadTexture;

    /* ===================== ENEMIGOS ===================== */

    public static Texture[] slimeNormalFrames = new Texture[4];
    public static Texture[] slimeTanqueFrames = new Texture[4];
    public static Texture[] slimeDivisorFrames = new Texture[4];
    public static Texture[] colossusStaticFrames = new Texture[4];

    /* ===================== TARJETAS ===================== */

    public static Texture cardCornShooter;
    public static Texture cardPapa;
    public static Texture cardWaterPlant;
    public static Texture cardRedBom;
    public static Texture cardMaceta;
    public static Texture cardCampanilla;
    public static Texture cardLilyPad;
    public static Texture cardChampi;
    public static Texture cardShovel;

    /* ===================== UI ===================== */

    public static Texture selectionBarBackground;
    public static Texture emptyBar;
    public static Texture healthFill;
    public static Texture heartIcon;

    public static Texture victoryOverlay;
    public static Texture defeatOverlay;
    public static Texture retryButton;
    public static Texture nextLevelButton;

    public static TextureRegion soap;
    public static TextureRegion soapWaveAnimation;

    public static void load() {

        /* ---------- Música ---------- */

        dayMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Fondo1.wav"));
        jungleMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Fondo2.wav"));
        nightMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Fondo3.wav"));
        bossMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/Fondo4.wav"));

        dayMusic.setVolume(0.2f);
        jungleMusic.setVolume(0.2f);
        nightMusic.setVolume(0.2f);
        bossMusic.setLooping(true);
        bossMusic.setVolume(0.2f);

        currentBackgroundMusic = dayMusic;
        currentBackgroundMusic.play();

        cornShootSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/DisparoMaiz.wav"));
        petaloShootSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/DisparoPetalo.wav"));
        explosionSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Exposion.wav"));

        /* ---------- Fondos ---------- */

        backgroundDay = new Texture(Gdx.files.internal("textures/background/Background_day.png"));
        backgroundJungle = new Texture(Gdx.files.internal("textures/background/JungleBackground.png"));
        backgroundNight = new Texture(Gdx.files.internal("textures/background/Noche_Background.png"));

        /* ---------- Proyectiles ---------- */

        popcornProjectile = new Texture(Gdx.files.internal("projectiles/Popcorn.png"));
        cornProjectileTexture = new Texture(Gdx.files.internal("projectiles/Popcorn.png"));
        petaloTexture = new Texture(Gdx.files.internal("projectiles/Petalo.png"));

        /* ---------- UI dinámica por idioma ---------- */

        String lang = (Constants.CURRENT_LANGUAGE == Language.EN) ? "en" : "es";

        defeatOverlay = new Texture(Gdx.files.internal("ui/GameLose_" + lang + ".png"));
        victoryOverlay = new Texture(Gdx.files.internal("ui/GameVictory_" + lang + ".png"));
        retryButton = new Texture(Gdx.files.internal("ui/retryButton_" + lang + ".png"));
        nextLevelButton = new Texture(Gdx.files.internal("ui/nextButton_" + lang + ".png"));

        /* ---------- UI fija ---------- */

        emptyBar = new Texture(Gdx.files.internal("ui/EmplyBar.png"));
        healthFill = new Texture(Gdx.files.internal("ui/HealthFill.png"));
        heartIcon = new Texture(Gdx.files.internal("ui/Heart.png"));

        selectionBarBackground = new Texture(Gdx.files.internal("textures/seeds/SeleccionDeCartas.png"));
        cardShovel = new Texture(Gdx.files.internal("textures/seeds/Pala.png"));

        soap = new TextureRegion(new Texture("ui/Jabon.png"));
        soapWaveAnimation = new TextureRegion(new Texture("ui/OlaJabon.png"));

        /* ---------- Tarjetas ---------- */

        cardCornShooter = new Texture(Gdx.files.internal("textures/seeds/CornShootherCard.png"));
        cardPapa = new Texture(Gdx.files.internal("textures/seeds/PapaCard.png"));
        cardWaterPlant = new Texture(Gdx.files.internal("textures/seeds/WaterPlantCard.png"));
        cardRedBom = new Texture(Gdx.files.internal("textures/seeds/RedBomCard.png"));
        cardMaceta = new Texture(Gdx.files.internal("textures/seeds/MacetaCard.png"));
        cardCampanilla = new Texture(Gdx.files.internal("textures/seeds/CampanillaCard.png"));
        cardLilyPad = new Texture(Gdx.files.internal("textures/seeds/LilyPadCard.png"));
        cardChampi = new Texture(Gdx.files.internal("textures/seeds/ChampiCard.png"));

        lilyPadTexture = new Texture(Gdx.files.internal("plants/lilyPad/LilyPad.png"));

        /* ---------- Carga por frames ---------- */

        for (int i = 1; i <= 4; i++)
            colossusStaticFrames[i - 1] = new Texture(Gdx.files.internal("enemies/colosalSLime/static/Profe(" + i + ").png"));

        for (int i = 1; i <= 4; i++)
            slimeNormalFrames[i - 1] = new Texture(Gdx.files.internal("enemies/slimeNormal/SlimeNormal(" + i + ").png"));

        for (int i = 1; i <= 4; i++)
            slimeTanqueFrames[i - 1] = new Texture(Gdx.files.internal("enemies/slimeTanque/SlimeTanque (" + i + ").png"));

        for (int i = 1; i <= 4; i++)
            slimeDivisorFrames[i - 1] = new Texture(Gdx.files.internal("enemies/slimeDivisor/SlimeDivisor (" + i + ").png"));

        for (int i = 1; i <= 11; i++)
            cornShooterFrames[i - 1] = new Texture(Gdx.files.internal("plants/cornshooter/popcornplant (" + i + ").png"));

        for (int i = 1; i <= 16; i++)
            papaCompletaFrames[i - 1] = new Texture(Gdx.files.internal("plants/papa/papaCompleta/PapaCompleta (" + i + ").png"));

        for (int i = 1; i <= 16; i++)
            papaComida1Frames[i - 1] = new Texture(Gdx.files.internal("plants/papa/papaComida1/PapaComida1 (" + i + ").png"));

        for (int i = 1; i <= 16; i++)
            papaComida2Frames[i - 1] = new Texture(Gdx.files.internal("plants/papa/papaComida2/PapaComida2 (" + i + ").png"));

        for (int i = 1; i <= 8; i++)
            waterPlantFrames[i - 1] = new Texture(Gdx.files.internal("plants/waterplant/WaterPlant (" + i + ").png"));

        for (int i = 1; i <= 11; i++)
            redBomFrames[i - 1] = new Texture(Gdx.files.internal("plants/redbom/Redbom (" + i + ").png"));

        for (int i = 1; i <= 7; i++)
            macetaFrames[i - 1] = new Texture(Gdx.files.internal("plants/maceta/Maceta(" + i + ").png"));

        for (int i = 1; i <= 5; i++)
            campanillaFrames[i - 1] = new Texture(Gdx.files.internal("plants/campanilla/Campanilla(" + i + ").png"));

        for (int i = 1; i <= 16; i++)
            champiNeutralFrames[i - 1] = new Texture(Gdx.files.internal("plants/champivoltear/framechampi/ChampiNeutral(" + i + ").png"));

        for (int i = 1; i <= 4; i++)
            champiAttackFrames[i - 1] = new Texture(Gdx.files.internal("plants/champivoltear/framechampiattack/ChampiAttack(" + i + ").png"));

        champiDetectedTexture = new Texture(Gdx.files.internal("plants/champivoltear/framchampivoltear/Champi_voltear.png"));
    }

    public static void drawProjectile(SpriteBatch batch, float x, float y) {
        float scale = 0.70f;
        float width = popcornProjectile.getWidth() * scale;
        float height = popcornProjectile.getHeight() * scale;
        batch.draw(popcornProjectile, x - width / 2f, y - height / 2f, width, height);
    }

    public static void dispose() {
        backgroundDay.dispose();
        backgroundJungle.dispose();
        backgroundNight.dispose();

        popcornProjectile.dispose();
        cornProjectileTexture.dispose();
        petaloTexture.dispose();

        for (Texture t : cornShooterFrames) t.dispose();
        for (Texture t : papaCompletaFrames) t.dispose();
        for (Texture t : papaComida1Frames) t.dispose();
        for (Texture t : papaComida2Frames) t.dispose();
        for (Texture t : waterPlantFrames) t.dispose();
        for (Texture t : redBomFrames) t.dispose();
        for (Texture t : macetaFrames) t.dispose();
        for (Texture t : campanillaFrames) t.dispose();
        for (Texture t : champiNeutralFrames) t.dispose();
        for (Texture t : champiAttackFrames) t.dispose();
        for (Texture t : slimeNormalFrames) t.dispose();
        for (Texture t : slimeTanqueFrames) t.dispose();
        for (Texture t : slimeDivisorFrames) t.dispose();
        for (Texture t : colossusStaticFrames) t.dispose();

        champiDetectedTexture.dispose();
        lilyPadTexture.dispose();

        cardCornShooter.dispose();
        cardPapa.dispose();
        cardWaterPlant.dispose();
        cardRedBom.dispose();
        cardMaceta.dispose();
        cardCampanilla.dispose();
        cardLilyPad.dispose();
        cardChampi.dispose();
        cardShovel.dispose();

        selectionBarBackground.dispose();
        emptyBar.dispose();
        healthFill.dispose();
        heartIcon.dispose();

        victoryOverlay.dispose();
        defeatOverlay.dispose();
        retryButton.dispose();
        nextLevelButton.dispose();

        cornShootSound.dispose();
        petaloShootSound.dispose();
        explosionSound.dispose();
        dayMusic.dispose();
        jungleMusic.dispose();
        nightMusic.dispose();
        bossMusic.dispose();
    }
}
