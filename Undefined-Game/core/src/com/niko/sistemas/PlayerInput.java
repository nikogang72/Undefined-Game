package com.niko.sistemas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class PlayerInput {
	
	private ModelInstance instance;
	private Vector3 position = new Vector3();
	private float rotation;
	
	/** Inicializamos el Handler de las teclas de movimiento, se el pasa como parametro la instancia
	 * del modelo del jugador
	 * @param instance
	 **/
	public PlayerInput(ModelInstance instance)
	{
		this.instance = instance;
	}
	
	public void movementHandler()
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
		//instance.transform.setTranslation(position); no necesaria
	}
	
	public void rotateHandler()
	{
		rotation = (rotation + Gdx.graphics.getDeltaTime() * 100) % 360;
		instance.transform.setFromEulerAngles(0, 0, rotation).trn(position.x, position.y, position.z);
//		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1))
//		{
//			instance.transform.setFromEulerAngles(rotation, 0, 0).trn(position.x, position.y, position.z);
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2))
//		{
//			instance.transform.setFromEulerAngles(0, rotation, 0).trn(position.x, position.y, position.z);
//		}
//		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3))
//		{
//			instance.transform.setFromEulerAngles(0, 0, rotation).trn(position.x, position.y, position.z);
//		}
	}
	
	public void updateTransformation()
	{
		instance.transform.setFromEulerAngles(0, 0, rotation).trn(position.x, position.y, position.z).scale(scale, scale, scale);
	}
	
	boolean increment;
	float scale = 1;
	
	public void scale()
	{
		if(increment) {
			scale = (scale + Gdx.graphics.getDeltaTime()/5);
			if (scale >= 3f) {
				increment = false;
			}
			else
			{
				scale = (scale - Gdx.graphics.getDeltaTime()/5);
				if (scale <= 0.5f)
					increment = true;
			}
		}
	}
}
