package com.niko.sistemas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class PlayerInput {
	
	private ModelInstance instance;
	private Vector3 position = new Vector3();
	
	/** Inicializamos el Handler de las teclas de movimiento, se el pasa como parametro la instancia
	 * del modelo del jugador
	 * @param instance
	 **/
	public PlayerInput(ModelInstance instance)
	{
		this.instance = instance;
	}
	
	public void handler()
	{
		instance.transform.getTranslation(position);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			position.x += Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			position.z += Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			position.z -= Gdx.graphics.getDeltaTime();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			position.x -= Gdx.graphics.getDeltaTime();
		}
		instance.transform.setTranslation(position);
	}
}
