using Projekt_IoT.Nowy_folder;
using System;
using System.Collections.Generic;

using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;
using System.Timers;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Reflection;
using static System.Net.Mime.MediaTypeNames;
using System.Diagnostics;
using System.Windows.Threading;

namespace Projekt_IoT
{
    /// <summary>
    /// Interaction logic for Pixels.xaml
    /// </summary>
    public partial class Pixels : Page
    {
        private Timer RequestTimer;
        public Pixels()
        {

            InitializeComponent();
            for (int i = 0; i < 8; i++)
            {
                ButtonMatrixGrid.ColumnDefinitions.Add(new ColumnDefinition());
                ButtonMatrixGrid.ColumnDefinitions[i].Width = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < 8; i++)
            {
                ButtonMatrixGrid.RowDefinitions.Add(new RowDefinition());
                ButtonMatrixGrid.RowDefinitions[i].Height = new GridLength(1, GridUnitType.Star);
            }

            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    // <Button
                    Button led = new Button()
                    {
                        // Name = "LEDij"
                        Name = "LED" + i.ToString() + j.ToString(),
                        // CommandParameter = "LEDij"
                        CommandParameter = "LED" + i.ToString() + j.ToString(),
                        // Style="{StaticResource LedButtonStyle}"
                        // Bacground="{StaticResource ... }"
                        Background = new SolidColorBrush(Color.FromArgb(255, 230, 230, 230)),
                        // BorderThicness="2"
                        BorderThickness = new Thickness(2),
                    };
                    led.Click += new RoutedEventHandler(SelectedPixel);
                    // Command="{Binding CommonButtonCommand}" 
                    led.SetBinding(Button.CommandProperty, new Binding("CommonButtonCommand"));
                    // Grid.Column="i" 
                    Grid.SetColumn(led, i);
                    // Grid.Row="j"
                    Grid.SetRow(led, j);
                    // />

                    ButtonMatrixGrid.Children.Add(led);
                    ButtonMatrixGrid.RegisterName(led.Name, led);
                }
            }
            Podgląd.Fill = new SolidColorBrush(Color.FromArgb(255, Convert.ToByte(R.Value), Convert.ToByte(G.Value), Convert.ToByte(B.Value)));
            RequestTimer = new Timer(500);
            RequestTimer.Elapsed += new ElapsedEventHandler(SetPixels);
            RequestTimer.Enabled = true;
        }
        private void SelectedPixel(object sender, RoutedEventArgs e)
        {
            Button btn= sender as Button;
            String name=btn.Name.ToString();
            MainViewModel.selectedPixelsY = name[name.Length-1]-'0';
            MainViewModel.selectedPixelsX = name[name.Length-2] - '0';
            name = name;
        }
        

        private void Ustaw_Click(object sender, RoutedEventArgs e)
        {
            MainViewModel.SendPixel(Convert.ToInt32(R.Value), Convert.ToInt32(G.Value), Convert.ToInt32(B.Value));
        }

        private void SliderChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            Podgląd.Fill = new SolidColorBrush(Color.FromArgb(255, Convert.ToByte(R.Value), Convert.ToByte(G.Value), Convert.ToByte(B.Value)));
        }
        private  async void SetPixels(object sender, ElapsedEventArgs e)
        {
            if (MainWindow.pixels.IsVisible)
            {
                List<List<String>> pixels = MainViewModel.pixels;
                if (pixels.Count > 0)
                {
                    for (int i = 0; i < 8; i++)
                    {
                        for (int j = 0; j < 8; j++)
                        {
                            byte r, g, b;
                            r = Convert.ToByte(pixels[i + j * 8][0]);
                            g = Convert.ToByte(pixels[i + j * 8][1]);
                            b = Convert.ToByte(pixels[i + j * 8][2]);
                            Color color = Color.FromArgb(255, r, g, b);
                            String name = "LED" + i.ToString() + j.ToString();
                            Dispatcher.Invoke(DispatcherPriority.Normal, () => { 
                               Button btn =ButtonMatrixGrid.FindName(name) as Button;
                                btn.Background = new SolidColorBrush(color);
                            });
                        }
                    }
                }
            }

        }
    }
}
