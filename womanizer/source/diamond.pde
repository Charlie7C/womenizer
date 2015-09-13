class diamond {
  PVector pos;
  float mySize=50;
  boolean clash=false;
  int count=0;
  
  diamond() {
    pos = new PVector();
    setPosition(80, random(40, 700));
  }

  void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  void draw() {
    fill(0, 0, 100);
    rectMode(CENTER);
    image(rose,pos.x,pos.y);
  }

  boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize && neon.pos.x <= pos.x + mySize ) {
      if (neon.pos.y >= pos.y - mySize && neon.pos.y <= pos.y + mySize ) {
        return true;
      }
    }
    return false;
  }

  void update() {
    if (isColliding()) {
      count++;

      float nextX = 80;
      if (pos.x == 80) nextX = 964; 
      setPosition(nextX, random(80, 600));
    }
  }
}

