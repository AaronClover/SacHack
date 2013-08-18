package com.aaronclover.sketchescape;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Hammer {
	private Runner runner;
	private ArrayList<Obstacle> obstacles;
	private Texture hammer;
	protected Rectangle hammerHitbox;
	final int SPRITE_WIDTH = 50;
	final int SPRITE_HEIGHT = 58;
	int swingRotation = 0;
	private int swingRotationInc;
	
	Hammer(Runner runner, ArrayList<Obstacle> obstacles) {
		this.runner = runner;
		this.obstacles = obstacles;
		create();
	}
	
	public void create() {
		hammer = new Texture(Gdx.files.internal("data/runner/hammer.png"));
		hammerHitbox = new Rectangle (runner.hitbox.x + runner.SPRITE_WIDTH, runner.hitbox.y, SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	public void draw(SpriteBatch batch) {
		TextureRegion ham = new TextureRegion(hammer);
		batch.draw(ham, hammerHitbox.x+12, hammerHitbox.y+16, 0 , SPRITE_WIDTH/2, SPRITE_WIDTH, SPRITE_HEIGHT, 1, 1, swingRotation, true);
	}
	
	public void update() {
		hammerHitbox.x = runner.hitbox.x + SPRITE_WIDTH;
		hammerHitbox.y = runner.hitbox.y;
		
		
		if (swingRotation >= 90) {
			swingRotationInc = -8;
		}
		else if (swingRotation <= 0) {
			swingRotationInc = 8;
		}
		swingRotation+= swingRotationInc;
		
		for (int i = 0; i < obstacles.size(); i++) {
			if (hammerHitbox.overlaps(obstacles.get(i).hitbox)) {
				obstacles.remove(i);
			}
		}
	}
	public void drawHitbox() {
		//shapeRenderer.rect(hammerHitbox.x*SCREENSCALEX - camera.position.x+400 , hitbox.y*SCREENSCALEY, hammerHitbox.width, hammerHitbox.height);
	}
}
