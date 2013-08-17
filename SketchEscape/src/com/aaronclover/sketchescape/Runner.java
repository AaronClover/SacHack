package com.aaronclover.sketchescape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.SoundLoader.SoundParameter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.aaronclover.sketchescape.MuteHandler;

public class Runner{
	private Texture currentSprite;
	private Texture runningSprite[];
	private Texture jumpingSprite[];
	private Texture duckingSprite[];
	private Sound jump, land;
	private Texture deadSprite[];
	protected int animationIndex;
	private float lastFrameTime;
	protected Rectangle hitbox;
	private float RESW;
	private float RESH;
	final int JUMP_WIDTH = 24;
	final int DUCK_HEIGHT = 43;
	final int SPRITE_WIDTH = 58;
	final int SPRITE_HEIGHT = 60;
	final int SCALE = 1; // Only affects hit box at the moment
	final int WIDTH = SPRITE_WIDTH * SCALE;
	final int HEIGHT = SPRITE_HEIGHT * SCALE;
	private OrthographicCamera camera;
	float floorHeight;
	private int posX = -300;
	protected float speedY;
	private final float FLOAT_RATE = 0.2f;
	private final float GRAVITY = -0.4f;
	private final float MAX_VELOCITY = 4.9f;
	private final float JUMP_HEIGHT = 30f;
	private boolean jumpReleased;
	long beginningOfRoll;
	private final float SCREENSCALEX = MyScreen.SCREENSCALEX;
	private final float SCREENSCALEY = MyScreen.SCREENSCALEY;

	// Debug
	ShapeRenderer shapeRenderer;

	// End Debug

	enum State {
		jumping, running, dead, ducking
	};

	State state;
	

	// Debug

	Runner(OrthographicCamera pcamera, float pheight) {
		RESW = pcamera.viewportHeight;
		RESH = pcamera.viewportHeight;
		camera = pcamera;
		floorHeight = pheight;
		create();
	}

	public void create() {
		speedY = 0;
		Texture walkingInit[] = {
				new Texture(Gdx.files.internal("data/runner/running/1.png")),
				new Texture(Gdx.files.internal("data/runner/running/2.png")),
				new Texture(Gdx.files.internal("data/runner/running/3.png")),
				new Texture(Gdx.files.internal("data/runner/running/4.png")),
				new Texture(Gdx.files.internal("data/runner/running/5.png")),
				new Texture(Gdx.files.internal("data/runner/running/6.png")) };
		runningSprite = walkingInit;
		Texture jumpingInit[] = {
				new Texture(Gdx.files.internal("data/runner/jumping/1.png")),
				new Texture(Gdx.files.internal("data/runner/jumping/2.png")),
				new Texture(Gdx.files.internal("data/runner/jumping/3.png")) };
		jumpingSprite = jumpingInit;
		Texture duckingInit[] = {
				new Texture(Gdx.files.internal("data/runner/rolling/1.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/2.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/3.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/4.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/5.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/6.png")),
				new Texture(Gdx.files.internal("data/runner/rolling/7.png")) };
		duckingSprite = duckingInit;
		Texture deadInit[] = {
				new Texture(Gdx.files.internal("data/runner/dead/1.png")),
				new Texture(Gdx.files.internal("data/runner/dead/2.png")),
				new Texture(Gdx.files.internal("data/runner/dead/3.png")),
				new Texture(Gdx.files.internal("data/runner/dead/4.png")),
				new Texture(Gdx.files.internal("data/runner/dead/5.png")),
				new Texture(Gdx.files.internal("data/runner/dead/6.png")) };
		deadSprite = deadInit;
		
		jump = Gdx.audio.newSound(Gdx.files.internal("data/runner/jump.wav"));
		land = Gdx.audio.newSound(Gdx.files.internal("data/runner/land.wav"));

		hitbox = new Rectangle(camera.position.x + posX, floorHeight,
				SPRITE_WIDTH, SPRITE_HEIGHT);
		animationIndex = 0;
		lastFrameTime = TimeUtils.nanoTime();
		state = State.running;

		// Debug
		shapeRenderer = new ShapeRenderer();
		currentSprite = runningSprite[0];
	}

	public void draw(SpriteBatch batch) {

		// Changes sprite set to the runner's state
				switch (state) {
				case running:
					if (animationIndex > runningSprite.length - 1) {
						animationIndex = 0;
					}
					currentSprite = runningSprite[animationIndex];
					break;

				case jumping:
					if (animationIndex > jumpingSprite.length - 1) {
						animationIndex = 0;
					}
					currentSprite = jumpingSprite[animationIndex];
					break;

				case ducking:
					if (animationIndex > duckingSprite.length - 1) {
						animationIndex = 0;
					}
					currentSprite = duckingSprite[animationIndex];
					break;
				case dead:
					//if (animationIndex > deadSprite.length - 1) {
					//	animationIndex = 0;
					//}
					currentSprite = deadSprite[animationIndex];
					break;
				default:
					System.out.println("Animation switch broken");
					break;
				}
				
				System.out.println(state);

				// Handles changing sprites for animation
				if (TimeUtils.nanoTime() - lastFrameTime >= 80000000) {
					if (state == State.jumping) {
						if (animationIndex < jumpingSprite.length - 1) {
							animationIndex++;
						}
					} else if (state == State.dead) {
						if (animationIndex < deadSprite.length - 1) {
							animationIndex++;
							System.out.println(deadSprite.length - 1);
						}
					} else {
						animationIndex++;
					}

					lastFrameTime = TimeUtils.nanoTime();
				}
		batch.draw(currentSprite, hitbox.x, hitbox.y);

	}
	
	public void drawHitbox() {
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(hitbox.x*SCREENSCALEX, hitbox.y*SCREENSCALEY, hitbox.width, hitbox.height);
		shapeRenderer.end();
	}


	public void dispose() {
		currentSprite.dispose();
		for (int i = 0; i < runningSprite.length; i++) {
			runningSprite[i].dispose();
		}
		for (int i = 0; i < jumpingSprite.length; i++) {
			jumpingSprite[i].dispose();
		}
	}

	public void update() {
		
		
		
		hitbox.y += speedY;
		if (state == State.jumping) {
			if (speedY <= MAX_VELOCITY)
				speedY += GRAVITY;
			else
				speedY = MAX_VELOCITY;
		}
		hitbox.x = camera.position.x + posX;

	
		hitbox.x = camera.position.x + posX;

		if (state == State.ducking) {
			if (TimeUtils.nanoTime() - beginningOfRoll > 500000000) {
				state = State.running;
				hitbox.setHeight(SPRITE_HEIGHT);
				hitbox.setWidth(SPRITE_WIDTH);
			}
		}

		
	}
	
	public void land(float f) {
		if (state == State.jumping) {
			speedY = 0;
			hitbox.setWidth(SPRITE_WIDTH);
			if (MuteHandler.isMuted() == false) {
				land.play();
			}
			state = State.running;
		}
		hitbox.y = f;
	}
	
	public void fall() {
		state = State.jumping;
		hitbox.setWidth(JUMP_WIDTH);
	}

	public void jump() {
		if (state != State.jumping && jumpReleased == true) {
			state = State.jumping;
			animationIndex = 0;
			hitbox.setHeight(SPRITE_HEIGHT);
			hitbox.setWidth(JUMP_WIDTH);
			jumpReleased = false;
			speedY += JUMP_HEIGHT;
			hitbox.y ++;
			if (MuteHandler.isMuted() == false) {
				jump.play();
			}
		}

		
		if (state == State.jumping && speedY < 0) {
			speedY += FLOAT_RATE;
			}

	}

	public void jumpRelease() {
		jumpReleased = true;

	}

	public void duck() {
		if (state != State.jumping) {
			beginningOfRoll = TimeUtils.nanoTime();
			state = State.ducking;
			hitbox.setHeight(DUCK_HEIGHT);
			hitbox.setWidth(SPRITE_WIDTH);
		}

	}

	public void duckRelease() {
	}
}