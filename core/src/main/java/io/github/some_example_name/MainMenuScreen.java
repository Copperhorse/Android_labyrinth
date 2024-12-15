package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

//----------------------------------------------------------------
//THE STARTING SCREEN OF THE GAME. CONTAINS THE NAME OF THE GAME AS WELL AS THE PLAY BUTTON.
//NOTHING ELSE.
//----------------------------------------------------------------

public class MainMenuScreen implements Screen {
    private final Main game;
    private final Stage stage;
    private Skin skin;
    public MainMenuScreen(Main game) {
        this.game = game;

        // Initialize the Stage and set the Input Processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load a Skin or create a custom Button Style
        skin = new Skin(Gdx.files.internal("ui/button.json")); // Replace with your skin file if necessary

        // Create the title label style
        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = game.font46; // Use the font from the main game class
        titleStyle.fontColor = Color.BLACK;

        // Create the Title Label
        Label titleLabel = new Label("Labyrinth", titleStyle);
         // Scale the title text to make it larger

        // Create the Play Button
        TextButton playButton = new TextButton("Play", skin);
        playButton.setSize(150, 50);

        // Add a Click Listener to the Play Button
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                game.setScreen(new FirstScreen(game.horiz,game.vert,game)); // Transition to FirstScreen
                dispose(); // Dispose of the current screen resources
            }
        });

        // Create a Table and arrange the UI elements
        Table table = new Table();
        table.setFillParent(true); // Makes the table fill the entire stage
        table.center(); // Center-align all content in the table

        // Add the Title and Play Button to the Table
        table.add(titleLabel).center().expandX().padBottom(50); // Add the title and pad below it
        table.row(); // Move to the next row in the table
        table.add(playButton).center(); // Add the button in the next row

        // Add the Table to the Stage
        stage.addActor(table);
    }

    @Override
    public void show() {
        // Any setup required when this screen is shown
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(Color.WHITE);

        // Update and draw the stage
        stage.act(delta);
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
        stage.dispose();
        skin.dispose();
    }
}
