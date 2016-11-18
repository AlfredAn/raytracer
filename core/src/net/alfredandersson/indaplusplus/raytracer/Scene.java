package net.alfredandersson.indaplusplus.raytracer;

import net.alfredandersson.indaplusplus.raytracer.shapes.Shape;
import java.util.ArrayList;
import java.util.List;

public final class Scene {
  
  private final List<Shape> shapes = new ArrayList<>();
  
  public Scene() {}
  
  public void add(Shape shape) {
    shapes.add(shape);
  }
  
  public void raycast(RaycastResult result, RaycastResult temp, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir) {
    float closest = Float.POSITIVE_INFINITY;
    
    for (int i = 0; i < shapes.size(); i++) {
      shapes.get(i).raycast(temp, xStart, yStart, zStart, xDir, yDir, zDir);
      
      if (temp.hitDist < closest) {
        closest = temp.hitDist;
        result.set(temp);
      }
    }
    
    if (closest == Float.POSITIVE_INFINITY) {
      result.setNoCollision();
    }
  }
}
