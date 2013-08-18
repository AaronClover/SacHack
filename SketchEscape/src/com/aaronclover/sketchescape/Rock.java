package com.aaronclover.sketchescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Rock {
	// Assets
	private Texture image;
	// End Assets

	protected Rectangle hitbox;
	final static int SPRITE_WIDTH = 37;
	final static int SPRITE_HEIGHT = 35;
	final static int SCALE = 1;
	protected int y;
	protected boolean released;
	// Debug
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	public int x;

	private float vertVelocity;

	Rock(OrthographicCamera pcamera) {
		x = (int) (pcamera.position.x + 400);
		y = (int)MathUtils.random(150, 400);
		vertVelocity = 6;
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
		vertVelocity -= 0.25;
		hitbox.y += (int)vertVelocity;
		hitbox.x -= 5; 
	}
	
	public void dispose() {
		image.dispose();
		
	}


	public float getTop() {
		return y + SPRITE_HEIGHT;
	}
}