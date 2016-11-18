package net.alfredandersson.indaplusplus.raytracer;

public interface Shape {
  
  public abstract void raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir);
}
