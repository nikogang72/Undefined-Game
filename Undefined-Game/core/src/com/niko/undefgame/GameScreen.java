package com.niko.undefgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.niko.cfg.cfg;
import com.niko.ui.GameUI;

public class GameScreen implements Screen {

	private UndefinedCore game;
	private GameWorld gameWorld;
	private GameUI gameUI;
	
	public GameScreen(UndefinedCore game) {
		this.game = game;
		gameUI = new GameUI(game);
		gameWorld = new GameWorld(gameUI);
		cfg.Paused = false;
		Gdx.input.setInputProcessor(gameUI.stage);
		Gdx.input.setCursorCatched(true);
	}
	
	@Override
	public void render(float delta) {
		gameUI.update(delta);
		gameWorld.render(delta);
		gameUI.render();
	}
	@Override
	public void resize(int width, int height) {
		gameUI.resize(width, height);
		gameWorld.resize(width, height);
	}
	@Override
	public void dispose() {
		gameWorld.dispose();
		gameUI.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
