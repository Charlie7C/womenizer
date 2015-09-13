class Mode {
  String name;
  //  diamond diamond;
  //  ArrayList<thorns> heads;
  //  int thornsCount =5;

  Mode(String name) {
    this.name = name;
  }

  void onStart() {
    println(" THIS IS THE DEFAULT ON START");
  }

  void onEnd() {
    println(" THIS IS THE DEFAULT ON END");
  }

  void play() {
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

  void play() {
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
      color c = color(r,g,b);
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

  void onStart() {
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

  void onStart() {
    neon.setPosition(514, 364);
    
    for (thorns t : heads) {
      t.draw();
      t.update();
      t.setPosition(25, random(20, 748));
      diamond.count=0;

    }
    background(0);
  }

  void play() {
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
  
  boolean transitionOccuring(){
   return millis() - transitionStart < transitionLength; 
  }

  void beginTransition() {
    transitioning = true;
    transitionStart = millis();
  }
  //Draw method for transition
  void onTransition(){
    diamond.draw();
    neon.draw();
    for (thorns t : heads) {
        t.draw();
    }
    //now add whatever you want!
image(deadman,neon.pos.x,neon.pos.y);
  }
  
  void endTransition(){
    transitioning = false;
    toDeathScreen();
  }

  void toDeathScreen() {
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

  void play() {
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

void changeMode(Mode thisIsMyNextMode) {
  if (currentMode != null) {
    currentMode.onEnd();
  }

  currentMode = thisIsMyNextMode;
  currentMode.onStart();
}

