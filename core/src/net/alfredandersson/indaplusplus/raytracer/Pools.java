package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

public final class Pools {
  
  public final RaycastPool raycast = new RaycastPool();
  public final ColorPool color = new ColorPool();
  public final VectorPool vec3 = new VectorPool();
  
  public void free(RaycastResult raycastResult) {
    raycast.free(raycastResult);
  }
  
  public void free(Color col) {
    color.free(col);
  }
  
  public void free(Vector3 vec) {
    vec3.free(vec);
  }
  
  public static final class RaycastPool extends Pool<RaycastResult> {
    
    public RaycastPool() {
      super();
    }
    
    public RaycastPool(int initialCapacity) {
      super(initialCapacity);
    }
    
    public RaycastPool(int initialCapacity, int max) {
      super(initialCapacity, max);
    }
    
    @Override
    protected RaycastResult newObject() {
      return new RaycastResult();
    }
  }
  
  public static final class ColorPool extends Pool<Color> {
    
    public ColorPool() {
      super();
    }
    
    public ColorPool(int initialCapacity) {
      super(initialCapacity);
    }
    
    public ColorPool(int initialCapacity, int max) {
      super(initialCapacity, max);
    }
    
    @Override
    protected Color newObject() {
      return new Color();
    }
  }
  
  public static final class VectorPool extends Pool<Vector3> {
    
    public VectorPool() {
      super();
    }
    
    public VectorPool(int initialCapacity) {
      super(initialCapacity);
    }
    
    public VectorPool(int initialCapacity, int max) {
      super(initialCapacity, max);
    }
    
    @Override
    protected Vector3 newObject() {
      return new Vector3();
    }
  }
}
