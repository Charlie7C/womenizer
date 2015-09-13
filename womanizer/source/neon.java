import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.analysis.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class neon extends PApplet {



Minim       minim;
AudioPlayer player;
AudioPlayer player1;
Character neon;
PVector gravity = new PVector(0, 0.2f);
boolean isOver=false;
boolean rightEnd=false;PFont mono;
boolean userHasPressedMouse = false;
PImage frontpage;
PImage rose;
PImage man;
PImage deadman;
PImage troll;
PImage endpage;
//PImage<>last;
PImage background;
PImage ring;
PImage maninlove;
PShape womanizer;
PFont font;
Mode currentMode;
int currentModeIndex = 0;
StartScreen level0;
Level level1;
diamond diamond;
ring rings;
ArrayList<thorns> heads;
int thornsCount =3;
ArrayList<Mode> modes;
public void setup() {
  minim = new Minim(this);

  player = minim.loadFile("spka_stkhlm.mp3");
   player1 = minim.loadFile("luvyou.wav");
   player.play();
   player.loop();
   if(keyPressed){
       player1.play();
   }
  font = loadFont("LucidaSans-TypewriterOblique-30.vlw");
  frontpage=loadImage("womanizer.jpg");
  rose=loadImage("rose.png");
  man=loadImage("man.png");
  deadman=loadImage("deadman.png");
  troll=loadImage("bride.png");
  endpage=loadImage("womanizerend1.jpg");
  background=loadImage("backgroundgame.jpg");
  ring=loadImage("ring.png");
  maninlove=loadImage("maninlove.png");
  womanizer=loadShape("8.svg");
  mono = loadFont("Arial-Black-48.vlw");
  textFont(mono);
  text("word", 12, 20);
  textSize(30);
  fill(0xffE30B98);
  textFont(font, 32);
  size(1024, 768);
  rectMode(CENTER);
  neon = new Character();
  neon.setPosition(width*0.5f, height*0.5f);
  diamond=new diamond();
  heads=new ArrayList<thorns>();
  rings=new ring();
  noStroke();
  initialize();
  
}

public void initialize() {
  
  modes = new ArrayList();
  heads = new ArrayList();
  level0 = new StartScreen();
  level1 = new Level(1);
  modes.add(level0);
  modes.add(level1);
  modes.add(new DeathScreen());
  for (int j =0; j < thornsCount; ++j) {
    heads.add(new thorns()); 
    heads.get(j).setPosition(25, random(0, 700));
  }
  changeMode(modes.get(currentModeIndex)); //takes us to our start screen
}


public void draw() {
  background(100);
  fill(0xffF0B13C);

  currentMode.play();
  
}

public void mousePressed() {
  userHasPressedMouse = true;
}
public void mouseReleased() {
  userHasPressedMouse = false;
}
public void keyPressed() {
//  if (key == 'z') {
//    currentModeIndex = (currentModeIndex + 1) % modes.size();
//    changeMode(modes.get(currentModeIndex));
//  }

  neon.setVelocity(-11 * neon.direction, -4.5f);
  rings.setPosition(random(100,500), random(100, 500));
  
}



//----------

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

  public void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;

  }

  public void setVelocity(float vx, float vy) {    
    vel.set(vx, vy);
  }


  public void changeDirection() {
    direction *= -1;
    setVelocity(vel.x * -1, vel.y );  
    for(thorns t:heads)
    { t.changePos=true; 
  }
  }
  public void setAcceleration(float ax, float ay) {
    acc.x = ax;
    acc.y = ay;
  }

  public void update() {

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
      setPosition(width - (mySize / 2.0f - 2), pos.y);
//      setVelocity(vel.x, vel.y);
    }

    //dont go off the left edge of the screen
    if ( pos.x < mySize /2.0f ) {
      setPosition(mySize/2.0f + 2.0f, pos.y);
      changeDirection();

    }

    //    //dont go off the top of the screen
    if ( pos.y < mySize/2.0f ) {
      setPosition(pos.x, mySize/2.0f);
    }
    //
    //    //dont go off the bottom of the screen
    if ( pos.y > height - mySize /2.0f ) {
      setPosition(pos.x, height - mySize/2.0f);
    }
    
    setAcceleration(0,0);
  }

  public void draw() {
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
  public boolean isColliding(){
  if(pos.y<26||pos.y>702){
    return true;
  }
  return false;
  
  }
}

class Mode {
  String name;
  //  diamond diamond;
  //  ArrayList<thorns> heads;
  //  int thornsCount =5;

  Mode(String name) {
    this.name = name;
  }

  public void onStart() {
    println(" THIS IS THE DEFAULT ON START");
  }

  public void onEnd() {
    println(" THIS IS THE DEFAULT ON END");
  }

  public void play() {
    println("something's going wrong!");
  }
}

// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=

class StartScreen extends Mode {

  StartScreen() {
    super("start screen"); // this calls Mode("start screen");
  }

  public void play() {
   loadPixels();
 
  
  // We must also call loadPixels() on the PImage since we are going to read its pixels.  img.loadPixels(); 
  for (int x = 0; x < frontpage.width; x++ ) {
    for (int y = 0; y < frontpage.height; y++ ) {
      
      // Calculate the 1D pixel location
      int loc = x + y*frontpage.width;
      
      // Get the R,G,B values from image
      float r = red (frontpage.pixels[loc]);
      float g = green (frontpage.pixels[loc]);
      float b = blue (frontpage.pixels[loc]);
      
      // Calculate an amount to change brightness
      // based on proximity to the mouse
      float distance = dist(x,y,random(0,width),random(0,height));
      
      // The closer the pixel is to the mouse, the lower the value of "distance" 
      // We want closer pixels to be brighter, however, so we invert the value with the formula: adjustBrightness = (50-distance)/50 
      // Pixels with a distance of 50 (or greater) have a brightness of 0.0 (or negative which is equivalent to 0 here)
      // Pixels with a distance of 0 have a brightness of 1.0.
      float adjustBrightness = (50-distance)/50;
      r *= adjustBrightness;
      g *= adjustBrightness;
      b *= adjustBrightness;
      
      // Constrain RGB to between 0-255
      r = constrain(r,0,255);
      g = constrain(g,0,255);
      b = constrain(b,0,255);
      
      // Make a new color and set pixel in the window
      int c = color(r,g,b);
      pixels[loc] = c;
    }
  }
  
  updatePixels(); 
    pushStyle();
    fill(150, 200, 0);
    rect(512, 364, 1024, 768);
    image(frontpage, 0, 0);
    popStyle();
     if (userHasPressedMouse) {
      if(mouseX<512){
      println("222");
      changeMode(modes.get(1));
      
      }
      if(mouseX>512){
      println("223");
shape(womanizer,0,0,1024,768);
      }
    } 
//    if (keyPressed) {
//      currentModeIndex = (currentModeIndex + 1) % modes.size();
//      changeMode(modes.get(1));
//    }
  }

  public void onStart() {
    println("yo Charlie...this is start screen on Start()"); 
    //   println("Now....watch this little trick.....");
    //   super.onStart();
  }
}

// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=

class Level extends Mode {

  int transitionLength = 2000; //the delay time before transition to death screen
  int transitionStart = 0; //keeps track of the transition
  boolean transitioning = false; //this tells us whether we are playing the level or animating at the end


  Level(int level) {
    super("level " + level);
  }

  public void onStart() {
    neon.setPosition(514, 364);
    
    for (thorns t : heads) {
      t.draw();
      t.update();
      t.setPosition(25, random(20, 748));
      diamond.count=0;

    }
    background(0);
  }

  public void play() {
    image(background,0,0);
    text("ONLY " + diamond.count+" LOVERS LEFT HURRY UP", 280, 60);
    if (!transitioning) {
      diamond.update();
      diamond.draw();
      rings.update();
      rings.draw();
      neon.update();
      neon.draw(); 
      if (neon.isColliding()) {
        beginTransition();
      }
      for (thorns t : heads) {
        t.draw();
        t.update();
        if (t.isColliding()) {
          beginTransition();
        }
        if(rings.isColliding()){
          beginTransition();
        }
      }
    } else { //transition code goes here
      if(!transitionOccuring()) endTransition();
      else onTransition();
    }
  }
  
  public boolean transitionOccuring(){
   return millis() - transitionStart < transitionLength; 
  }

  public void beginTransition() {
    transitioning = true;
    transitionStart = millis();
  }
  //Draw method for transition
  public void onTransition(){
    diamond.draw();
    neon.draw();
    for (thorns t : heads) {
        t.draw();
    }
    //now add whatever you want!
image(deadman,neon.pos.x,neon.pos.y);
  }
  
  public void endTransition(){
    transitioning = false;
    toDeathScreen();
  }

  public void toDeathScreen() {
    currentModeIndex = (currentModeIndex + 1) % modes.size();
    changeMode(modes.get(2));
  }
}

// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=


class DeathScreen extends Mode {
  DeathScreen() {
    super("You Died -- Click to continue");
  }

  public void play() {
    image(endpage, 0, 0);
    //       if(keyPressed){
    //      currentModeIndex = (currentModeIndex + 1) % modes.size();
    //      changeMode(modes.get(1));
    //    }
    if (userHasPressedMouse) {
      changeMode(level0);
    } 
    ////if(neon.pos.y==743)||(neon.pos.y==26)||(isColliding()){
    //changeMode(level2);
  }
}

//}


// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=
// -=-=-=-=-=-=-=-=-=-=

int modeCursor = 0;

public void changeMode(Mode thisIsMyNextMode) {
  if (currentMode != null) {
    currentMode.onEnd();
  }

  currentMode = thisIsMyNextMode;
  currentMode.onStart();
}

class diamond {
  PVector pos;
  float mySize=50;
  boolean clash=false;
  int count=0;
  
  diamond() {
    pos = new PVector();
    setPosition(80, random(40, 700));
  }

  public void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  public void draw() {
    fill(0, 0, 100);
    rectMode(CENTER);
    image(rose,pos.x,pos.y);
  }

  public boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize && neon.pos.x <= pos.x + mySize ) {
      if (neon.pos.y >= pos.y - mySize && neon.pos.y <= pos.y + mySize ) {
        return true;
      }
    }
    return false;
  }

  public void update() {
    if (isColliding()) {
      count++;

      float nextX = 80;
      if (pos.x == 80) nextX = 964; 
      setPosition(nextX, random(80, 600));
    }
  }
}

class ring {
  PVector pos;
  float mySize=50;

 
   boolean changePos;
  ring() {
    pos = new PVector();
    setPosition(random(0,width), random(0, height));
  }

  public void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  public void draw() {
    fill(0, 0, 100);
    rectMode(CENTER);
    image(ring,pos.x,pos.y);
  }

  public boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize/2 && neon.pos.x <= pos.x + mySize/2 ) {
      if (neon.pos.y >= pos.y - mySize/2 && neon.pos.y <= pos.y + mySize/2 ) {
        return true;
      }
    }
    return false;
  }

  public void update() {

    
   if (isColliding()) {
     
     float nextX = 80;
      if (pos.x == 80) nextX = 964; 
      setPosition(nextX, random(80, 600));
   }
   }
}

class thorns {
  boolean leftEnd= true;
  PVector pos;
  float mySize=50;
  boolean changePos;

  thorns() {
    pos = new PVector();
    setPosition(random(20, 748), random(20, 748));
  }

  public void setPosition(float x, float y) {
    pos.x = x;
    pos.y = y;
  }

  public void draw() {
    fill(0, 100, 0);
    rectMode(CENTER);
    image(troll,pos.x, pos.y);
  }

  public boolean isColliding() {
    if (neon.pos.x >= pos.x - mySize && neon.pos.x <= pos.x + mySize) {
      if (neon.pos.y >= pos.y - mySize && neon.pos.y <= pos.y + mySize) {
        return true;
      }
    }
    return false;
  }

  public boolean isChange() {
    if (changePos) {

      changePos=false;
      return true;
    }      
    return false;
  }

  public void update() {
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


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "neon" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
