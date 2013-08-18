package com.aaronclover.sketchescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Rock {
	// Assets
	private Texture image;
	// End Assets

	protected Rectangle hitbox;
	final static int SPRITE_WIDTH = 37;
	final static int SPRITE_HEIGHT = 35;
	final static int SCALE = 1;
	protected float height;
	protected boolean released;
	// Debug
	ShapeRenderer shapeRenderer = new ShapeRenderer();

	Rock(OrthographicCamera pcamera, float x, float y) {
		height = y;
		create(x, y);
	}

	public void create(float x, float y) {
		image = new Texture(Gdx.files.internal("data/rock.png"));
		hitbox = new Rectangle(x, y, SPRITE_WIDTH, SPRITE_HEIGHT);
	}

	public void draw(SpriteBatch batch) {

		batch.draw(image, hitbox.x, hitbox.y, hitbox.width*SCALE, hitbox.height*SCALE);
	}
	
	public void update() {
		
	}
	
	public void dispose() {
		image.dispose();
		
	}


	public float getTop() {
		return height + SPRITE_HEIGHT;
	}
}