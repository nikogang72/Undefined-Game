package com.niko.undefgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class UndefinedCore extends Game {
	
//	public Camara camara;
//	public PlayerInput playerInput;
//	
//	public Model model;
//	public ModelInstance instance;
//	public ModelBatch modelBatch;
//	public Environment environment;
	public Screen screen;
	
	
	@Override
	public void create () {
		new Assets();
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
//		playerInput.rotateHandler();
//		playerInput.movementHandler();
//		playerInput.scale();
//		playerInput.updateTransformation();
//		
//		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		screen.render(Gdx.graphics.getDeltaTime());
		
	}
	
	public void setScreen(Screen screen)
	{
		if (this.screen != null)
		{
			this.screen.hide();
			this.screen.dispose();
		}
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
	
	@Override
	public void dispose () {
		Assets.dispose();
	}
}
