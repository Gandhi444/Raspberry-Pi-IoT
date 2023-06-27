using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_IoT.Nowy_folder
{
    public class JoyStick
    {
        public int x; public int y;
    }
    public class Data
    {
        public float t { get; set; }
        public float h { get; set; }
        public float p { get; set; }
        public float Yaw { get; set; }
        public float Roll { get; set; }
        public float Pitch { get; set; }
        public List<List<String>> pixels { get; set; }
        public JoyStick Joy { get; set; }

    }
}
