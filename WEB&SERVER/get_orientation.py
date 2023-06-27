from sense_emu import SenseHat
import json

sense = SenseHat()
orientation=sense.get_orientation_degrees()
dic={"yaw":orientation['yaw'],"roll":orientation['roll'],"pitch":orientation['pitch']}
print(json.dumps(dic))