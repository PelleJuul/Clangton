import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ClangtonEdit extends PApplet {



Cell[] cells = new Cell[225];
int s = 15;
float cellSize = 32;

boolean setStart = false;
boolean setEnd = true;

int mouseOver = 0;
int selected = 0;

boolean input = false;
boolean output = false;

ControlP5 cp5;

public void setup() {
  size((int)cellSize*(s+1) + 150, (int)cellSize*(s+1));
  
  cp5 = new ControlP5(this);
  
  makeGui();
  
  for(int i = 0; i < cells.length; i++) {
    cells[i] = new Cell(); 
  }
}

public void draw() {
  background(100);
  for(int i = 0; i < cells.length; i++) {
    Cell thisCell = cells[i];
    if(thisCell.state) fill(0);
    else               fill(255);
    
    rect(cellSize * (i % s), cellSize * (i / s), cellSize, cellSize);
  }
  
  if(mouseX < cellSize*s && mouseY < cellSize*s) {
    int x = (int)mouseX / (int)cellSize;
    int y = (int)mouseY / (int)cellSize;
    int pos = x + (y * s);
    mouseOver = pos;
    cp5.get(Textlabel.class, "mouseOver").setText(""+pos);
  }
}

public void mouseClicked() {
  if(mouseX < cellSize*s && mouseY < cellSize*s) {
    int x = (int)mouseX / (int)cellSize;
    int y = (int)mouseY / (int)cellSize;
    int pos = x + (y * s);
    if(mouseButton == LEFT)
      updateCell(pos);
    else if(mouseButton == RIGHT) {
      cells[pos].state = !cells[pos].state;
      updateCell(pos); 
    }
  }
}

public void updateCell(int pos) {
  selected = pos;
  Cell tc = cells[selected];
  cp5.get(Textlabel.class, "selected").setText(""+selected);
  cp5.get(Textfield.class, "til-delceller").setText(tc.del);
  cp5.get(Textfield.class, "til-teleceller").setText(tc.tele);
  cp5.get(Toggle.class, "input").setState(tc.input);
  cp5.get(Toggle.class, "output").setState(tc.output);
  
}

public void controlEvent(ControlEvent theEvent) {
  if(theEvent.isAssignableFrom(Textfield.class)) {
    println(theEvent.getStringValue());
    if(theEvent.getName() == "til-delceller")
      cells[selected].del = theEvent.getStringValue();
    else if (theEvent.getName() == "til-teleceller")
      cells[selected].tele = theEvent.getStringValue();
  }
  else if(theEvent.isAssignableFrom(Toggle.class)) {
    if(theEvent.getName() == "input")
      cells[selected].input = input;
    else if (theEvent.getName() == "output")
      cells[selected].output = output;
  }
}

public void bang() {
  selectOutput("Select a file to write to:", "fileSelected");
}

public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    String size = ""+s;
    String start = cp5.get(Textfield.class, "start").getText();
    String fin = cp5.get(Textfield.class, "slut").getText();
    String plane = "";
    String fradel = "";
    String tildel = "";
    String fratele = "";
    String tiltele = "";
    String ind = "";
    String ud = "";
    
    for(int i = 0; i < cells.length; i++) {
      Cell c = cells[i];
     
       if (c.state) plane = plane + "1,"; else plane = plane + "0,";
       
       if (c.del.length() >= 1)
       {
         for(int j = 0; j < c.del.length(); j++) {
           fradel = fradel + i + ","; 
         }
         tildel = tildel + c.del + ",";
       }
       
       if (c.tele.length() >= 1) 
       {
         for(int k = 0; k < c.tele.length(); k++) {
           fratele = fratele + i + ","; 
         }
         tiltele = tiltele + c.tele + ",";
       }
   
       if (c.input) ind = ind + i + ",";
       if (c.output) ud = ud + i + ",";     
    }
    
    plane = removeLast(plane);
    fradel = removeLast(fradel);
    tildel = removeLast(tildel);
    fratele = removeLast(fratele);
    tiltele = removeLast(tiltele);
    ind = removeLast(ind);
    ud = removeLast(ud);
    
    String program = size + "/\n" + start + "/\n" + 
      fin + "/\n" + plane + "/\n" + fradel + "/\n" +
      tildel + "/\n" + fratele + "/\n" + tiltele + "/\n" +
      ind + "/\n" + ud;
      
    println(program);
    
    PrintWriter o = createWriter(selection.getAbsolutePath());
    o.print(program);
    o.flush();
    o.close();
  }
}

public String removeLast(String str) {
  if(str != "")
    return str.substring(0, str.length() -1);
  return str;
}
public class Cell {
  public boolean state = false;
  public String del = "";
  public String tele = "";
  public boolean output = false;
  public boolean input = false;
  
  Cell() {
  }
  
}


public void makeGui() {
  cp5.addTextfield("til-delceller")
     .setPosition(cellSize*(s+1),cellSize*1)
     .setSize(100,20)
     .setFont(createFont("helvetica", 13))
     .setFocus(true)
     .setColor(color(255))
     .setAutoClear(false)
     ;
     
   cp5.addTextfield("til-teleceller")
   .setPosition(cellSize*(s+1),cellSize*2)
   .setSize(100,20)
   .setFont(createFont("helvetica", 13))
   .setFocus(false)
   .setColor(color(255))
   .setAutoClear(false)
   ;
   
      cp5.addTextfield("start")
   .setPosition(cellSize*(s+1),cellSize*7)
   .setSize(100,20)
   .setFont(createFont("helvetica", 13))
   .setFocus(false)
   .setColor(color(255))
   .setAutoClear(false)
   ;
   
      cp5.addTextfield("slut")
   .setPosition(cellSize*(s+1),cellSize*8)
   .setSize(100,20)
   .setFont(createFont("helvetica", 13))
   .setFocus(false)
   .setColor(color(255))
   .setAutoClear(false)
   ;
   
   cp5.addToggle("input")
     .setPosition(cellSize*(s+1),cellSize*3)
     .setSize(50,20)
     ;
     
   cp5.addToggle("output")
     .setPosition(cellSize*(s+1),cellSize*4)
     .setSize(50,20)
     ;
     
   cp5.addTextlabel("mouseOver")
                  .setText("0")
                  .setPosition(0,cellSize *(s))
                  .setColorValue(0xffffffff)
                  .setFont(createFont("helvetica", 13))
                  ;
                    
    cp5.addTextlabel("selected")
    .setText("0")
    .setPosition(cellSize*(s+1),5)
    .setColorValue(0xffffffff)
    .setFont(createFont("helvetica", 18))
    ;
    
      cp5.addBang("bang")
     .setPosition(cellSize*(s+1), cellSize *(s-1))
     .setSize(100, 20)
     .setTriggerEvent(Bang.RELEASE)
     .setLabel("Save file")
     ;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ClangtonEdit" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
