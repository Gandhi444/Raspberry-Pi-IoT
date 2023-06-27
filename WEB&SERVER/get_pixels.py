from sense_emu import SenseHat
import json

sense = SenseHat()
pixel_list = {"pixels":sense.get_pixels()}
print(json.dumps(pixel_list))