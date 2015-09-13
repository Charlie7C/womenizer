class ring {
  PVector pos;
  float mySize=50;

 
   boolean changePos;
  ring() {
    pos = new PVector();
    setPosition(random(0,width), random(0, height));
  }

  void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  void draw() {
    fill(0, 0, 100);
    rectMode(CENTER);
    image(ring,pos.x,pos.y);
  }

  boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize/2 && neon.pos.x <= pos.x + mySize/2 ) {
      if (neon.pos.y >= pos.y - mySize/2 && neon.pos.y <= pos.y + mySize/2 ) {
        return true;
      }
    }
    return false;
  }

  void update() {

    
   if (isColliding()) {
     
     float nextX = 80;
      if (pos.x == 80) nextX = 964; 
      setPosition(nextX, random(80, 600));
   }
   }
}

