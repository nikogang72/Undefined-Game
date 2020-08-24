package com.niko.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.niko.cfg.cfg;
import com.niko.undefgame.UndefinedCore;

public class GameUI {

	private UndefinedCore game;
	public Stage stage;
	public HealthWidget healthWidget;
	private PauseWidget pauseWidget;
	private CrosshairWidget crosshairWidget;
	public GameOverWIdget gameOverWidget;
	
	public GameUI(UndefinedCore game) 
	{
		this.game = game;
		stage = new Stage(new FitViewport(cfg.VIRTUAL_WIDTH, cfg.VIRTUAL_HEIGHT));
		setWidgets();
		configureWidgets();
	}

	public void setWidgets()
	{
		healthWidget = new HealthWidget();
		pauseWidget = new PauseWidget(game, stage);
		crosshairWidget = new CrosshairWidget();
		gameOverWidget = new GameOverWidget(game, stage);
	}
	
	public void configureWidgets()
	{
		healthWidget.setSize(140, 25);
		healthWidget.setPosition((cfg.VIRTUAL_WIDTH - (cfg.VIRTUAL_WIDTH * 0.1f) - healthWidget.getWidth() / 2),(cfg.VIRTUAL_HEIGHT - (cfg.VIRTUAL_HEIGHT * 0.1f)));
		pauseWidget.setSize(64, 64);
		pauseWidget.setPosition(cfg.VIRTUAL_WIDTH - pauseWidget.getWidth(), cfg.VIRTUAL_HEIGHT - pauseWidget.getHeigt());
		gameOverWidget.setSize(280, 100);
		gameOverWidget.setPosition((cfg.VIRTUAL_WIDTH / 2) - 280 / 2, cfg.VIRTUAL_HEIGHT / 2);
		crosshairWidget.setPosition(cfg.VIRTUAL_WIDTH / 2 - 16, cfg.VIRTUAL_HEIGHT / 2 - 16);
		crosshairWidget.setSize(32, 32);
		
		stage.addActor(healthWidget);
		stage.addActor(crosshairWidget);
		stage.setKeyboardFocus(pauseWidget);
	}
	
	public void update(float delta)
	{
		stage.act(delta);
	}
	
	public void render()
	{
		stage.draw();
	}
	
	public void resize(int width, int height)
	{
		stage.getViewport().update(width, height);
	}
	
	public void dispose()
	{
		stage.dispose();
	}
}
