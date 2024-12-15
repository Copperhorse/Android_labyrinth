package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

//----------------------------------------------------------------
// YOU COULD SAY THAT IT IS DRIVER FILE OF THE GAME.
// CONTAINS ALL OF THE INITIALIZED VARIABLES WE USED.
// THE VARIABLES ARE THEN USED IN OTHER OTHER CLASSES USING GAME.WHATEVER INSTANCE/ATTRIBUTE YOU WANT.
// THE WIN LEVELSOUND AS WELL AS THE BACKGROUNDSONG WERE THE OBTAINED FROM ENVANTO
// back_ground_song2 is : https://pixabay.com/music/video-games-3-cream-soda-cute-bgm-271159/

//----------------------------------------------------------------

public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public BitmapFont font46;
    public FitViewport viewport;
    public Music WinSound;
    public Music backgroundMusic;
    public int horiz;
    public int vert;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont(Gdx.files.internal("com/badlogic/gdx/utils/cinzel.fnt"));
        font46 = new BitmapFont(Gdx.files.internal("com/badlogic/gdx/utils/Cinzel_46.fnt"));
        viewport = new FitViewport(5, 5);
        WinSound = Gdx.audio.newMusic(Gdx.files.internal("com/badlogic/gdx/utils/winLevelSound2.ogg")); // Replace with your win sound file if necessary
        horiz = 5;
        vert = 5;
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("com/badlogic/gdx/utils/back_ground_song2.ogg"));
        backgroundMusic.play();
        backgroundMusic.setLooping(true);
        // font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this)); // Start with the MainMenuScreen
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        backgroundMusic.dispose();
    }

    private void setFontScale(float xScale, float yScale) {
        font.getData().setScale(xScale, yScale);
    }
}
