package net.alfredandersson.indaplusplus.raytracer.shapes;

import net.alfredandersson.indaplusplus.raytracer.RaycastResult;
import net.alfredandersson.indaplusplus.raytracer.materials.Material;

public abstract class Shape {
  
  public final Material material;
  
  public Shape(Material material) {
    this.material = material;
  }
  
  public abstract void raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir);
}
