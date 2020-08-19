package com.niko.sistemas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.niko.cfg.cfg;

public class Camara {
	
	private float FoV;
	public PerspectiveCamera cam;
	
	// Sistema de Camara
	public Camara() 
	{
		this.FoV = cfg.FoV;
		this.cam = new PerspectiveCamera(FoV, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.cam.position.set(10f, 10f, 10f);
		this.cam.lookAt(0, 0, 0);
		this.cam.near = 1f;
		this.cam.far = 300f;
		this.cam.update();
	}


}
