package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

/** Win screen of the application. Displayed when the player wins. NO PLAY AGAIN IMPLEMENTED AS OF NOW */
public class WinScreen implements Screen {
    final Main game;
    private Music winSound;
    private Stage stage;
    private Skin skin;

    public WinScreen(Main game) {
        this.game = game;
        this.winSound = game.WinSound;
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        createUI();
    }

    private void createUI() {

        Table table = new Table();
        table.setFillParent(true);


        Label congratsLabel = new Label("Congratulations!", new Label.LabelStyle(game.font46, Color.BLACK));



        Label winLabel = new Label("You Win!", new Label.LabelStyle(game.font46, Color.BLACK));



        table.add(congratsLabel).center();
        table.row();
        table.add(winLabel).center();

        // Add the table to the stage
        stage.addActor(table);
    }

    @Override
    public void show() {
        winSound.play();
        winSound.setLooping(false);
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(Color.WHITE);


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        winSound.dispose();
        stage.dispose();
    }
}
