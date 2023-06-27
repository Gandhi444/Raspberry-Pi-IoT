import sys
import getopt
from sense_emu import SenseHat
import json
sysarg = sys.argv[1:]

sense = SenseHat()
if len(sysarg) !=5:
    sys.exit(1)
sysarg = [eval(i) for i in sysarg]
for arg in sysarg:
    if not isinstance(arg,int):
        sys.exit(1)
sense.set_pixel(sysarg[0],sysarg[1],[sysarg[2],sysarg[3],sysarg[4]])