package com.niko.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject;
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.niko.components.BulletComponent;
import com.niko.components.CharacterComponent;
import com.niko.components.ModelComponent;
import com.niko.components.PlayerComponent;
import com.niko.sistemas.BulletSystem;
import com.niko.sistemas.MotionState;

public class EntityFactory {
	
    private static Model playerModel;
    private static Texture playerTexture;
    private static ModelBuilder modelBuilder;
    //private static Model boxModel;

    
    static {
        modelBuilder = new ModelBuilder();
        playerTexture = new Texture(Gdx.files.internal("badlogic.jpg"));
        Material material = new Material(TextureAttribute.createDiffuse(playerTexture), ColorAttribute.createSpecular(1, 1, 1, 1), FloatAttribute.createShininess(8f));
        playerModel = modelBuilder.createCapsule(2f, 6f, 16, material, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
       // boxModel = modelBuilder.createBox(1f, 1f, 1f, new Material(ColorAttribute.createDiffuse(Color.WHITE),
         //       ColorAttribute.createSpecular(Color.WHITE), FloatAttribute.createShininess(64f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }
    
	public static Entity createStaticEntity(Model model, float x, float y, float z)
	{
		final BoundingBox boundingBox = new BoundingBox();
		model.calculateBoundingBox(boundingBox);
		Vector3 tmpV =  new Vector3();
		btCollisionShape col = new btBoxShape(tmpV.set(boundingBox.getWidth() * 0.5f, boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f ));
		Entity entity = new Entity();
		ModelComponent modelComponent = new ModelComponent(model, x, y, z);
		entity.add(modelComponent);
		BulletComponent bulletComponent = new BulletComponent();
		bulletComponent.bodyInfo = new btRigidBody.btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
		bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
		bulletComponent.body.userData = entity;
		bulletComponent.motionState = new MotionState(modelComponent.instance.transform);
		((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);
		entity.add(bulletComponent);
		
		return entity;
	}
	
	
	
	public static Entity createCharacter(BulletSystem bulletSystem, float x, float y, float z)
	{
		Entity entity = new Entity();
		ModelComponent modelComponent = new ModelComponent(playerModel, x, y, z);
		entity.add(modelComponent);
		CharacterComponent characterComponent = new CharacterComponent();
		characterComponent.ghostObject = new btPairCachingGhostObject();
		characterComponent.ghostObject.setWorldTransform(modelComponent.instance.transform);
		characterComponent.ghostShape = new btCapsuleShape(2f, 2f);
		characterComponent.ghostObject.setCollisionShape(characterComponent.ghostShape);
		characterComponent.ghostObject.setCollisionFlags(btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT);
		characterComponent.characterController = new btKinematicCharacterController(characterComponent.ghostObject, characterComponent.ghostShape, 0.35f, new Vector3(0,1f,0));
		characterComponent.ghostObject.userData = entity;
		entity.add(characterComponent);
		bulletSystem.collisionWorld.addCollisionObject(entity.getComponent(CharacterComponent.class).ghostObject, (short)btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
				(short)(btBroadphaseProxy.CollisionFilterGroups.AllFilter));
		bulletSystem.collisionWorld.addAction(entity.getComponent(CharacterComponent.class).characterController);
		
		return entity;
	}
	
	public static Entity createPlayer(BulletSystem bulletSystem, float x, float y, float z)
	{
		Entity entity = createCharacter(bulletSystem, x, y, z);
		entity.add(new PlayerComponent());
		return entity;
	}
}
