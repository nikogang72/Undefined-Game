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
import com.niko.components.ModelComponent;
import com.niko.managers.EntityFactory;
import com.niko.sistemas.BulletSystem;
import com.niko.sistemas.RenderSystem;

public class GameWorld {
	
	private ModelBatch batch;
	private Environment environment;
	private PerspectiveCamera cam;
	private Engine engine;
	public BulletSystem bulletSystem;
	public ModelBuilder modelBuilder = new ModelBuilder();
	
	Model wallHorizontal = modelBuilder.createBox(40, 20, 1, 
			new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), 
					FloatAttribute.createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    Model wallVertical = modelBuilder.createBox(1, 20, 40,
            new Material(ColorAttribute.createDiffuse(Color.GREEN), ColorAttribute.createSpecular(Color.WHITE), FloatAttribute
                    .createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    Model groundModel = modelBuilder.createBox(40, 1, 40,
            new Material(ColorAttribute.createDiffuse(Color.YELLOW), ColorAttribute.createSpecular(Color.BLUE), FloatAttribute
                    .createShininess(16f)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

	
	public GameWorld()
	{
		Bullet.init();
		initEnvironment();
		initModelBatch();
		initPersCamera();
		addSystems();
		addEntities();
		
//		engine = new Engine();
//		
//		ModelBuilder modelBuilder = new ModelBuilder();
//		Material boxMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), FloatAttribute.createShininess(16f));
//		Model box = modelBuilder.createBox(5, 5, 5, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//	
//		Entity entity = new Entity();
//		entity.add(new ModelComponent(box,10,10,10));
//		engine.addEntity(entity);
//
//		engine.addSystem(new RenderSystem(batch, environment));
	}
	
	private void addEntities()
	{
		createGround();
	}
	
	private void createGround()
	{
		engine.addEntity(EntityFactory.createStaticEntity(groundModel, 0, 0, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, -20));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, 20));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, 20, 10, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, -20, 10, 0));
	}
	
	private void addSystems()
	{
		engine = new Engine();
		engine.addSystem(new RenderSystem(batch, environment));
		engine.addSystem(bulletSystem = new BulletSystem());
	}
	
	private void initPersCamera()
	{
		cam = new PerspectiveCamera(cfg.FoV, cfg.VIRTUAL_WIDTH, cfg.VIRTUAL_HEIGTH);
		cam.position.set(30f, 40f, 30f);
		cam.lookAt(0f, 0f, 0f);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();
	}
	
	private void initEnvironment()
	{
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
	}
	
	private void initModelBatch()
	{
		batch = new ModelBatch();
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
	
	
	/**The ModelBatch is one of the object, which require disposing, 
	 * hence we add it to the dispose function **/
	public void dispose()
	{
		bulletSystem.dispose();
		bulletSystem = null;
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
