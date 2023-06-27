from sense_emu import SenseHat
import json

sense = SenseHat()

Pomiary={"t":sense.temp,"p":sense.pressure,"h":sense.humidity}
print(json.dumps(Pomiary))