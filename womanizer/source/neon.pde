import ddf.minim.analysis.*;
import ddf.minim.*;
Minim       minim;
AudioPlayer player;
AudioPlayer player1;
Character neon;
PVector gravity = new PVector(0, 0.2);
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
void setup() {
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
  fill(#E30B98);
  textFont(font, 32);
  size(1024, 768);
  rectMode(CENTER);
  neon = new Character();
  neon.setPosition(width*0.5, height*0.5);
  diamond=new diamond();
  heads=new ArrayList<thorns>();
  rings=new ring();
  noStroke();
  initialize();
  
}

void initialize() {
  
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


void draw() {
  background(100);
  fill(#F0B13C);

  currentMode.play();
  
}

void mousePressed() {
  userHasPressedMouse = true;
}
void mouseReleased() {
  userHasPressedMouse = false;
}
void keyPressed() {
//  if (key == 'z') {
//    currentModeIndex = (currentModeIndex + 1) % modes.size();
//    changeMode(modes.get(currentModeIndex));
//  }

  neon.setVelocity(-11 * neon.direction, -4.5);
  rings.setPosition(random(100,500), random(100, 500));
  
}



//----------

