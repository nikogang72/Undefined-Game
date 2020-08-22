package com.niko.undefgame;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.niko.cfg.cfg;
import com.niko.components.CharacterComponent;
import com.niko.managers.EntityFactory;
import com.niko.sistemas.BulletSystem;
import com.niko.sistemas.EnemySystem;
import com.niko.sistemas.PlayerSystem;
import com.niko.sistemas.RenderSystem;
import com.niko.sistemas.StatusSystem;

public class GameWorld {
	
	private ModelBatch batch;
	private Environment environment;
	private PerspectiveCamera cam;
	private Engine engine;
	private Entity character;
	public BulletSystem bulletSystem;
	public ModelBuilder modelBuilder = new ModelBuilder();
	
	public Model wallHorizontal = modelBuilder.createBox(40, 20, 1, 
			new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), 
					FloatAttribute.createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    public Model wallVertical = modelBuilder.createBox(1, 20, 40,
            new Material(ColorAttribute.createDiffuse(Color.GREEN), ColorAttribute.createSpecular(Color.WHITE), FloatAttribute
                    .createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    public Model groundModel = modelBuilder.createBox(40, 1, 40,
            new Material(ColorAttribute.createDiffuse(Color.YELLOW), ColorAttribute.createSpecular(Color.BLUE), FloatAttribute
                    .createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

	
	public GameWorld()
	{
		Bullet.init();
		System.out.println("Log: Bullet started");
		initEnvironment();
		initModelBatch();
		initPersCamera();
		addSystems();
		addEntities();
	}
	
	private void initPersCamera()
	{
		cam = new PerspectiveCamera(cfg.FoV, cfg.VIRTUAL_WIDTH, cfg.VIRTUAL_HEIGTH);
		System.out.println("Log: Camera created");
	}
	
	private void initEnvironment()
	{
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
		System.out.println("Log: Enviroment created");
	}
	
	private void initModelBatch()
	{
		batch = new ModelBatch();
		System.out.println("Log: Model Batch created");
	}
	
	private void addSystems()
	{
		engine = new Engine();
		engine.addSystem(new RenderSystem(batch, environment));
		System.out.println("Log: RenderSystem added");
		engine.addSystem(bulletSystem = new BulletSystem());
		System.out.println("Log: Physics System added");
		engine.addSystem(new PlayerSystem(this, cam));
		System.out.println("Log: Player System added");
		engine.addSystem(new EnemySystem(this));
		System.out.println("Log: Enemy System added");
		engine.addSystem(new StatusSystem(this));
		System.out.println("Log: Status System added");
	}
	
	private void addEntities()
	{
		createGround();
		System.out.println("Log: Map Created");
		createPlayer(5, 3, 5);
		System.out.println("Log: Player Created");
	}
	
	private void createGround()
	{
		engine.addEntity(EntityFactory.createStaticEntity(groundModel, 0, 0, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, -20));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, 20));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, 20, 10, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, -20, 10, 0));
	}
	
	private void createPlayer(float x, float y, float z)
	{
		character = EntityFactory.createPlayer(bulletSystem, x, y, z);
		engine.addEntity(character);
	}

	public void render(float delta)
	{
		renderWorld(delta);
	}
	
	protected void renderWorld(float delta)
	{
		batch.begin(cam);
		engine.update(delta);
		batch.end();
	}
	
	public void remove(Entity entity)
	{
		engine.removeEntity(entity);
		bulletSystem.removeBody(entity);
	}
	
	/**The ModelBatch is one of the object, which require disposing, 
	 * hence we add it to the dispose function **/
	public void dispose()
	{
		bulletSystem.collisionWorld.removeAction(character.getComponent(CharacterComponent.class).characterController);
		bulletSystem.collisionWorld.removeCollisionObject(character.getComponent(CharacterComponent.class).ghostObject);
		bulletSystem.dispose();
		bulletSystem = null;
		
		character.getComponent(CharacterComponent.class).characterController.dispose();
		character.getComponent(CharacterComponent.class).ghostObject.dispose();
		character.getComponent(CharacterComponent.class).ghostShape.dispose();
		
		wallHorizontal.dispose();
		wallVertical.dispose();
		groundModel.dispose();
		
		batch.dispose();
		batch = null;
	}
	
	/** With the camera set we can now fill in the resize function as well **/
	public void resize(int width, int height)
	{
		cam.viewportHeight = height;
		cam.viewportWidth = width;
	}
	
	
}
