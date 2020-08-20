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
import com.niko.cfg.cfg;
import com.niko.components.ModelComponent;
import com.niko.sistemas.RenderSystem;

public class GameWorld {
	
	private ModelBatch batch;
	private Environment environment;
	private PerspectiveCamera cam;
	private Engine engine;
	
	public GameWorld()
	{
		initPersCamera();
		initEnvironment();
		initModelBatch();
		engine = new Engine();
		
		ModelBuilder modelBuilder = new ModelBuilder();
		Material boxMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), FloatAttribute.createShininess(16f));
		Model box = modelBuilder.createBox(5, 5, 5, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
	
		Entity entity = new Entity();
		entity.add(new ModelComponent(box,10,10,10));
		engine.addEntity(entity);

		engine.addSystem(new RenderSystem(batch, environment));
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
	
	/**The ModelBatch is one of the object, which require disposing, 
	 * hence we add it to the dispose function **/
	public void dispose()
	{
		batch.dispose();
	}
	
	/** With the camera set we can now fill in the resize function as well **/
	public void resize(int width, int height)
	{
		cam.viewportHeight = height;
		cam.viewportWidth = width;
	}
	
	public void render(float delta)
	{
		batch.begin(cam);
		engine.update(delta);
		batch.end();
	}
	
}
