package com.niko.sistemas;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.niko.components.CharacterComponent;
import com.niko.components.ModelComponent;
import com.niko.components.PlayerComponent;
import com.niko.undefgame.GameWorld;

public class PlayerSystem extends EntitySystem implements EntityListener {

	private Entity player;
	private PlayerComponent playerComponent;
	private CharacterComponent characterComponent;
	private ModelComponent modelComponent;
	private final Vector3 tmp = new Vector3();
	private final Camera camera;
    private GameWorld gameWorld;
	
	public PlayerSystem(GameWorld gameWorld, Camera camera) 
	{
		this.camera = camera;
		this.gameWorld = gameWorld;
	}
	
	@Override
	public void addedToEngine(Engine engine)
	{
		engine.addEntityListener(Family.all(PlayerComponent.class).get(), this);
	}
	
	@Override
	public void entityAdded(Entity entity) 
	{
		player = entity;
		playerComponent = entity.getComponent(PlayerComponent.class);
		characterComponent = entity.getComponent(CharacterComponent.class);
		modelComponent = entity.getComponent(ModelComponent.class);
	}
	
	@Override
	public void update(float delta)
	{
		if (player == null) return;
		updateMovement(delta);
	}
	
	private void updateMovement(float delta)
	{
		float deltaX = -Gdx.input.getDeltaX() * 0.5f;
		float deltaY = -Gdx.input.getDeltaY() * 0.5f;
		tmp.set(0, 0, 0);
		camera.rotate(camera.up, deltaX);
		tmp.set(camera.direction).crs(camera.up).nor();
		camera.direction.rotate(tmp, deltaY);
		//Move
		characterComponent.characterDirection.set(-1, 0, 0).rot(modelComponent.instance.transform);
		characterComponent.walkDirection.set(0, 0, 0);
		
		if (Gdx.input.isKeyPressed(Input.Keys.W)) characterComponent.walkDirection.add(camera.direction);
		if (Gdx.input.isKeyPressed(Input.Keys.S)) characterComponent.walkDirection.sub(camera.direction);
		tmp.set(0,0,0);
		if (Gdx.input.isKeyPressed(Input.Keys.A)) tmp.set(camera.direction).crs(camera.up).nor().scl(-1);
		if (Gdx.input.isKeyPressed(Input.Keys.D)) tmp.set(camera.direction).crs(camera.up).nor().scl(1);
		
		characterComponent.walkDirection.add(tmp);
		characterComponent.walkDirection.scl(10f * delta);
		characterComponent.characterController.setWalkDirection(characterComponent.walkDirection);
		
		Matrix4 ghost = new Matrix4();
		Vector3 translation = new Vector3();
		
		characterComponent.ghostObject.getWorldTransform(ghost);
		// TODO export this
		ghost.getTranslation(translation);
		modelComponent.instance.transform.set(translation.x, translation.y, translation.z, 
				camera.direction.x, camera.direction.y, camera.direction.z, 0);
		camera.position.set(translation.x, translation.y, translation.z);
		camera.update(true);
	}

	@Override
	public void entityRemoved(Entity entity) {
	}

}
