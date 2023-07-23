package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	private Stage stage;


	Texture playerUpTexture;
	Texture playerDownTexture;
	Texture playerLeftTexture;
	Texture playerRightTexture;
	Texture backgroundTexture;
	Texture obstacleTexture;
	Texture dodgeRollTextureRegion;
	Texture enemyTexture;
	// Target width and height for all textures
	private static final int TEXTURE_WIDTH = 32;
	private static final int TEXTURE_HEIGHT = 32;
	private boolean isGameOver;

	ShapeRenderer shapeRenderer;
	BitmapFont font;
	private OrthographicCamera camera;
	private OrthographicCamera healthBarCamera;

	private List<Enemy> enemies;
	private List<Obstacle> obstacles;
	private boolean isDodgeRollActive; // Flag to indicate if dodge roll animation is active
	private float dodgeRollTimer; // Timer to control the dodge roll animation duration

	// Define the dimensions for the elements
	private Player player;

	private void update() {
		// Update game logic here, handle input, move enemies, etc.
		// For example, if you want to move the player based on input:
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			player.moveUp();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.moveDown();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			player.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			player.moveRight();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			player.dodgeRoll();
		}
	}

	private void handleGameOver() {
		isGameOver = true;
	}

	private void drawHealthBar(SpriteBatch batch, Player player) {
		float healthPercentage = player.getHealth() / player.getMaxHealth();
		float barWidth = 100; // Width of the health bar
		float barHeight = 10; // Height of the health bar
		float barX = 20; // X coordinate of the health bar
		float barY = Gdx.graphics.getHeight() - 20; // Y coordinate of the health bar at the top of the screen

		shapeRenderer.setProjectionMatrix(healthBarCamera.combined);
		// Draw the health bar background
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(barX, barY, barWidth, barHeight);

		// Draw the current health level
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(barX, barY, barWidth * healthPercentage, barHeight);
		shapeRenderer.end();
	}

	@Override
	public void create() {
		batch = new SpriteBatch(); // Initialize the SpriteBatch
		playerUpTexture = new Texture("up.png");
		playerDownTexture = new Texture("down.png");
		playerLeftTexture = new Texture("left.png");
		playerRightTexture = new Texture("right.png");
		backgroundTexture = new Texture("background.png");
		obstacleTexture = new Texture("obstacle.png");
		dodgeRollTextureRegion = new Texture("dodgeroll.png");
		enemyTexture = new Texture("enemy.png");
		shapeRenderer = new ShapeRenderer();
		// Initialize enemies and obstacles lists before creating the Player instance
		enemies = new ArrayList<>();
		obstacles = new ArrayList<>();
		isDodgeRollActive = false;
		dodgeRollTimer = 0;

		// Create some enemy and obstacle instances for testing
		enemies.add(new Enemy(300, 200, 1.5f, 10));
		obstacles.add(new Obstacle(300, 160));
		obstacles.add(new Obstacle(300, 240));


		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		healthBarCamera = new OrthographicCamera();
		healthBarCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		healthBarCamera.update();

		player = new Player(100, 100, enemies, obstacles, camera); // Initialize player at position (100, 100) on the game world

		// Create the stage
		stage = new Stage();

		// Add the Player instance to the stage
		stage.addActor(player);

		// Set the stage as the input processor
		Gdx.input.setInputProcessor(stage);

		Gdx.input.setInputProcessor(new InputAdapter() {
			// ...
		});
	}

	@Override
	public void render() {
		// Update the game logic here
		update();

		// Update the stage
		stage.act(Gdx.graphics.getDeltaTime());

		// Clear the screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenUtils.clear(1, 0, 0, 1);

		// Update camera position to follow the player
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		// Draw the background texture
		batch.draw(backgroundTexture, 0, 0);

		for (Enemy enemy : enemies) {
			batch.draw(enemyTexture, enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
			enemy.act(Gdx.graphics.getDeltaTime());
		}
		for (Obstacle obstacle : obstacles) {
			batch.draw(obstacleTexture, obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
		}
		// Render dodge roll animation if active
		if (isDodgeRollActive) {
			// Replace dodgeRollTextureRegion with the TextureRegion or animation representing the dodge roll
			batch.draw(dodgeRollTextureRegion, player.getX(), player.getY(), player.getWidth(), player.getHeight());
		}

		player.draw(batch, camera, 1);

		batch.end();

		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.setColor(Color.RED); // Set the color of the bounding box
		shapeRenderer.rect(player.getX(), player.getY(), player.getWidth(), player.getHeight());
		for (Obstacle obstacle : obstacles) {
			shapeRenderer.rect(obstacle.getX(), obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
		}
		for (Enemy enemy : enemies) {
			shapeRenderer.rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
		}
		shapeRenderer.end();

		// Draw the health bar overlay on top of the game elements using the health bar camera
		drawHealthBar(batch, player);

		// Draw the stage
		stage.draw();

		// ...
	}


	@Override
	public void dispose () {
		batch.dispose();
		playerUpTexture.dispose();
		playerDownTexture.dispose();
		playerLeftTexture.dispose();
		playerRightTexture.dispose();
		backgroundTexture.dispose();
		obstacleTexture.dispose();
		dodgeRollTextureRegion.dispose();
		enemyTexture.dispose();
	}
}
