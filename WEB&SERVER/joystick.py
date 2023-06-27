from sense_emu import SenseHat
import json
sense = SenseHat()
x=y=0
try:
    while True:
        events=sense.stick.get_events()
        if len(events)>0:
            for event in events:
                if event.action=="pressed":
                    dir=event.direction
                    if dir == "up":
                        y+=1
                    if dir == "down":
                        y-=1
                    if dir == "left":
                        x-=1
                    if dir == "right":
                        x+=1
                    x = max(-8, min(8, x))
                    y = max(-8, min(8, y))
                    d={"joy":{"x":x,"y":y}}
                    f = open("joystick.dat", "w")
                    f.write(json.dumps(d))
                    f.close()
except KeyboardInterrupt:
    pass