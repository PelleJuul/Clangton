

void makeGui() {
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
