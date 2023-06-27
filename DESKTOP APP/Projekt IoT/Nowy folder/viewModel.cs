using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Timers;
using System.Xml.Linq;
using Newtonsoft.Json;
using OxyPlot;
using OxyPlot.Axes;
using OxyPlot.Series;
using Newtonsoft.Json.Serialization;
using System.Security.Cryptography.X509Certificates;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Media;

namespace Projekt_IoT.Nowy_folder
{
    public class tableRow
    {
        public String Nazwa { get; set; }

        public float Wartość { get; set; }

        public String Jednostka { get; set; }
    }

    public class MainViewModel : INotifyPropertyChanged
    {
        public static string ipAddress = "192.168.1.216";
        public static int array_len = 100;
        public static int Tp = 500;
        #region Data
        static List<float> Temp = new List<float>();
        static List<float> Hum = new List<float>();
        static List<float> Pre = new List<float>();
        static List<float> Yaw = new List<float>();
        static List<float> Roll = new List<float>();
        static List<float> Pitch = new List<float>();
        public static List<tableRow> Table = new List<tableRow>();
        public static int selectedPixelsX, selectedPixelsY;
        public static List<List<String>> pixels = new List<List<String>>();
        public JoyStick Joy = new JoyStick();
        private static Timer RequestTimer;
        private static Server Server;
        public PlotModel DataPlotModel { get; set; }
        public PlotModel DataJoyModel { get; set; }
        public DataGrid DataGrid { get; set; }
        List<String> names = new List<String>() { "Temperatura", "Wilgotność", "Ciśnienie", "Yaw", "Roll", "Pitch" };
        #endregion
        public MainViewModel()
        {
            DataPlotModel = new PlotModel { Title = "Pomiary" };
            DataGrid = new DataGrid();
            DataGrid.ItemsSource = Table;
            DataPlotModel.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Bottom,
                Minimum = 0,
                Maximum = array_len,
                Key = "Horizontal",
                Unit = "sec",
                Title = "Time"
            });
            DataPlotModel.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Left,
                Minimum = -30,
                Maximum = 105,
                Key = "Vertical",
                Unit = "*C",
                Title = "Temperature"
            });

            DataPlotModel.Series.Add(new LineSeries() { Title = "", Color = OxyColor.Parse("#FFFF0000") });

            DataJoyModel = new PlotModel { Title = "Pomiary" };
            DataJoyModel.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Bottom,
                Minimum = -8,
                Maximum = 8,
                Key = "Horizontal",
                Title = "x"
            });
            DataJoyModel.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Left,
                Minimum = -8,
                Maximum = 8,
                Key = "Vertical",
                Title = "y"
            });
            ScatterSeries ScatterSeries = new ScatterSeries { MarkerType = MarkerType.Circle };
            ScatterSeries.Points.Add(new ScatterPoint(Joy.x, Joy.y, 5.0, 0xff0000));
            ScatterSeries.Points.Add(new ScatterPoint(Joy.x + 1, Joy.y + 1, 5.0, 0xff0000));
            DataJoyModel.Series.Add(ScatterSeries);

            Server = new Server();
            StartTimer();
        }


        static String SelectedGraph = "Temp";
        public static void SetSelectedGraph(String graph)
        {
            SelectedGraph = graph;

        }
        public static void SetIp(string ip)
        {
            ipAddress = ip;
        }
        public static void SetArryLen(int len)
        {
            array_len = len;
        }
        public static void SetTp(int tp)
        {
            Tp = tp;
            RequestTimer.Stop();
            RequestTimer.Interval = tp;
            RequestTimer.Start();
        }
        public event PropertyChangedEventHandler PropertyChanged;

        /**
         * @brief Simple function to trigger event handler
         * @params propertyName Name of ViewModel property as string
         */
        protected void OnPropertyChanged(string propertyName)
        {
            PropertyChangedEventHandler handler = PropertyChanged;
            if (handler != null) handler(this, new PropertyChangedEventArgs(propertyName));
        }
        private void StartTimer()
        {
            if (RequestTimer == null)
            {
                RequestTimer = new Timer(Tp);
                RequestTimer.Elapsed += new ElapsedEventHandler(RequestTimerElapsed);
                RequestTimer.Enabled = true;

                //DataPlotModel.ResetAllAxes();
            }
        }
        private async void RequestTimerElapsed(object sender, ElapsedEventArgs e)
        {
            string responseText = await Server.GETwithClient("http://" + ipAddress + "/get_all.php");
            if (responseText == null) { return; }
            var Json = JsonConvert.DeserializeObject<Data>(responseText);
            AddData(Temp, Json.t);
            AddData(Hum, Json.h);
            AddData(Pre, Json.p);
            AddData(Yaw, Json.Yaw);
            AddData(Roll, Json.Roll);
            AddData(Pitch, Json.Pitch);
            pixels = Json.pixels;
            Joy = Json.Joy;
            UpdateGraph();
            UpdateJoyStick();
        }
        private void AddData(List<float> array, float data)
        {
            array.Add(data);
            while (array.Count > array_len)
            {
                array.RemoveAt(0);
            }
        }
        private static LineSeries converListToSeries(List<float> array)
        {
            int i = 0;
            LineSeries lineSeries = new LineSeries { Color = OxyColor.Parse("#FFFF0000") };
            while (i < array.Count)
            {

                lineSeries.Points.Add(new DataPoint(i, array[i]));
                i++;
            }
            return lineSeries;
        }
        public static async void SendPixel(int r, int g, int b)
        {
            String command = "x=" + selectedPixelsX.ToString() + "&y=" + selectedPixelsY.ToString() + "&r=" + r.ToString() + "&g=" + g.ToString() + "&b=" + b.ToString();
            String URL = "http://" + ipAddress + "/set_pixel.php?" + command;
            string responseText = await Server.GETwithClient(URL);
        }
        static public void AddTableRow(String name)
        {
            tableRow row;
            try
            {
                switch (name)
                {
                    case "temp":
                        {
                            row = new tableRow() { Nazwa = "Temperatura", Wartość = Temp.Last(), Jednostka = "C" };
                            Table.Add(row);
                            break;
                        }
                    case "hum":
                        {
                            row = new tableRow() { Nazwa = "Wilgotność", Wartość = Hum.Last(), Jednostka = "Ba" };
                            Table.Add(row);
                            break;
                        }
                    case "pre":
                        {
                            row = new tableRow() { Nazwa = "Ciśnienie", Wartość = Pre.Last(), Jednostka = "mbar" };
                            Table.Add(row);
                            break;
                        }
                    case "Yaw":
                        {
                            row = new tableRow() { Nazwa = "Yaw", Wartość = Yaw.Last(), Jednostka = "*" };
                            Table.Add(row);
                            break;
                        }
                    case "Pitch":
                        {
                            row = new tableRow() { Nazwa = "Pitch", Wartość = Pitch.Last(), Jednostka = "*" };
                            Table.Add(row);
                            break;
                        }
                    case "Roll":
                        {
                            row = new tableRow() { Nazwa = "Roll", Wartość = Roll.Last(), Jednostka = "*" };
                            Table.Add(row);
                            break;
                        }
                }
            }
            catch { }

        }
        static public void RemoveTableRow(String name)
        {
            foreach (tableRow item in Table)
            {
                if (item.Nazwa == name)
                {
                    Table.Remove(item);
                    break;
                }
            }
        }
        private void UpdateGraph()
        {
            DataPlotModel.Axes.Clear();
            DataPlotModel.Axes.Add(new LinearAxis()
            {
                Position = AxisPosition.Bottom,
                Minimum = 0,
                Maximum = array_len-1,
                Key = "Horizontal",
                Unit = "sec",
                Title = "Time"
            });
            switch (SelectedGraph)
            {
                case "Temp":
                    {

                        DataPlotModel.Series[0] = converListToSeries(Temp);
                        DataPlotModel.Series[0].Title = "Temperatura";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = -30,
                            Maximum = 105,
                            Key = "Vertical",
                            Unit = "C",
                            Title = "Temperature"
                        });
                        break;
                    }
                case "Pre":
                    {
                        DataPlotModel.Series[0] = converListToSeries(Pre);
                        DataPlotModel.Series[0].Title = "Ciśnienie";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = 260,
                            Maximum = 1260,
                            Key = "Vertical",
                            Unit = "Ba",
                            Title = "Ciśnienie"
                        });
                        break;
                    }
                case "Hum":
                    {
                        DataPlotModel.Series[0] = converListToSeries(Hum);
                        DataPlotModel.Series[0].Title = "Wilgotność";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = 0,
                            Maximum = 100,
                            Key = "Vertical",
                            Unit = "%",
                            Title = "Wilgotność"
                        });
                        break;
                    }
                case "Yaw":
                    {
                        DataPlotModel.Series[0] = converListToSeries(Yaw);
                        DataPlotModel.Series[0].Title = "Yaw";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = -180,
                            Maximum = 180,
                            Key = "Vertical",
                            Unit = "*",
                            Title = "Yaw"
                        });
                        break;
                    }
                case "Roll":
                    {
                        DataPlotModel.Series[0] = converListToSeries(Roll);
                        DataPlotModel.Series[0].Title = "Roll";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = -180,
                            Maximum = 180,
                            Key = "Vertical",
                            Unit = "*",
                            Title = "Roll"
                        });
                        break;
                    }
                case "Pitch":
                    {
                        DataPlotModel.Series[0] = converListToSeries(Pitch);
                        DataPlotModel.Series[0].Title = "Pitch";
                        DataPlotModel.Axes.Add(new LinearAxis()
                        {
                            Position = AxisPosition.Left,
                            Minimum = -180,
                            Maximum = 180,
                            Key = "Vertical",
                            Unit = "*",
                            Title = "Pitch"
                        });
                        break;
                    }
            }
            DataPlotModel.InvalidatePlot(true);

        }
        private void UpdateJoyStick()
        {
            DataJoyModel.Series.Remove(DataJoyModel.Series[0]);
            ScatterSeries ScatterSeries = new ScatterSeries { MarkerType = MarkerType.Circle };
            ScatterSeries.Points.Add(new ScatterPoint(Joy.x, Joy.y, 5.0, 0xff0000));
            DataJoyModel.Series.Add(ScatterSeries);
            DataJoyModel.InvalidatePlot(true);
        }
    }
}



