class thorns {
  boolean leftEnd= true;
  PVector pos;
  float mySize=50;
  boolean changePos;

  thorns() {
    pos = new PVector();
    setPosition(random(20, 748), random(20, 748));
  }

  void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  void draw() {
    fill(0, 100, 0);
    rectMode(CENTER);
    image(troll,pos.x, pos.y);
  }

  boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize && neon.pos.x <= pos.x + mySize) {
      if (neon.pos.y >= pos.y - mySize && neon.pos.y <= pos.y + mySize) {
        return true;
      }
    }
    return false;
  }

  boolean isChange() {
    if (changePos) {

      changePos=false;
      return true;
    }      
    return false;
  }

  void update() {
    if (isChange()) {
      float nextX = 0;
    
      if (leftEnd) {
        nextX = 959; 
        setPosition(nextX, random(80, 600));
        leftEnd=false;
      } else { 
        nextX = 5;
        setPosition(nextX, random(80, 600));
        leftEnd=true;
      }
    }
  }
}


