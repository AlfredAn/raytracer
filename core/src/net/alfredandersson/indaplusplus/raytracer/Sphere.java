package net.alfredandersson.indaplusplus.raytracer;

public final class Sphere implements Shape {
  
  public final float x, y, z, radius, invradius;
  
  public Sphere(float x, float y, float z, float radius) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.radius = radius;
    this.invradius = 1.0f / radius;
  }
  
  @Override
  public void raycast(RaycastResult result, float xStart, float yStart, float zStart, float xDir, float yDir, float zDir) {
    float dx = x - xStart;
    float dy = y - yStart;
    float dz = z - zStart;
    
    // quadratic formula
    float a = xDir * xDir + yDir * yDir + zDir * zDir;
    float b = -2 * (xDir * dx + yDir * dy + zDir * dz);
    float c = dx * dx + dy * dy + dz * dz - radius * radius;
    
    float insideSqrt = b * b - 4 * a * c;
    
    if (insideSqrt < 0) {
      result.setNoCollision();
      return;
    }
    
    float t = (-b - (float)Math.sqrt(insideSqrt)) * 0.5f / a;
    //float t2 = (-b + (float)Math.sqrt(insideSqrt)) * 0.5f / a; // this will only be useful if the camera is inside the sphere --- ignore it for performance
    
    if (t < 0) {
      result.setNoCollision();
      return;
    }
    
    float hitX = xStart + xDir * t;
    float hitY = yStart + yDir * t;
    float hitZ = zStart + zDir * t;
    
    float normX = (hitX - x) * invradius;
    float normY = (hitY - y) * invradius;
    float normZ = (hitZ - z) * invradius;
    
    result.set(this, t, hitX, hitY, hitZ, normX, normY, normZ);
  }
}
