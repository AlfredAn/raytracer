package net.alfredandersson.indaplusplus.raytracer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pool;

public final class Pools {
  
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
}
