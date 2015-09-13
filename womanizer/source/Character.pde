class Character {
  PVector pos;
  PVector vel;
  PVector acc;
  float mySize = 50;
    
  float v=5;
  int direction = 1;
  

  boolean wasLeft = false;
  boolean wasRight = true;

  Character() {
    pos = new PVector();
    vel = new PVector();
    acc = new PVector();

    setPosition(0, 0);
    setVelocity(0, 0);
    setAcceleration(0, 0);
  }

  void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;

  }

  void setVelocity(float vx, float vy) {    
    vel.set(vx, vy);
  }


  void changeDirection() {
    direction *= -1;
    setVelocity(vel.x * -1, vel.y );  
    for(thorns t:heads)
    { t.changePos=true; 
  }
  }
  void setAcceleration(float ax, float ay) {
    acc.x = ax;
    acc.y = ay;
  }

  void update() {

    vel.add(acc);
//    if (!keyPressed) { // don't slow the character down unless the player is no longer pressing the key
//      vel.mult(friction);
//    }
    vel.add(gravity);   
    pos.add(vel);

    //dont go off the right edge of the screen
    if (pos.x +mySize/2 > width ) {
     
      changeDirection();
//      println(vel);
      setPosition(width - (mySize / 2.0 - 2), pos.y);
//      setVelocity(vel.x, vel.y);
    }

    //dont go off the left edge of the screen
    if ( pos.x < mySize /2.0 ) {
      setPosition(mySize/2.0 + 2.0, pos.y);
      changeDirection();

    }

    //    //dont go off the top of the screen
    if ( pos.y < mySize/2.0 ) {
      setPosition(pos.x, mySize/2.0);
    }
    //
    //    //dont go off the bottom of the screen
    if ( pos.y > height - mySize /2.0 ) {
      setPosition(pos.x, height - mySize/2.0);
    }
    
    setAcceleration(0,0);
  }

  void draw() {
    pushStyle();
    pushMatrix();
    noStroke();
    fill(200, 200, 10);
    translate(pos.x, pos.y);
    rectMode(CENTER);
    
    if(keyPressed){
    image(maninlove,0,0);
   
  
    }
    else{
    image(man,0,0);
    }
    popMatrix();
    popStyle();
  }
  boolean isColliding(){
  if(pos.y<26||pos.y>702){
    return true;
  }
  return false;
  
  }
}

