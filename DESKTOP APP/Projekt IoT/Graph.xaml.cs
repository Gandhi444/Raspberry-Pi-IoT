using Projekt_IoT.Nowy_folder;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Projekt_IoT
{
    /// <summary>
    /// Interaction logic for Graph.xaml
    /// </summary>
    public partial class Graph : Page
    {
        public Graph()
        {
            InitializeComponent();
        }
        private void RadioButton_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Temp");
        }

        private void PreRadio_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Pre");
        }

        private void HumRadio_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Hum");
        }

        private void PitchRadio_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Pitch");
        }

        private void YawRadio_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Yaw");
        }

        private void RollRadio_Checked(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetSelectedGraph("Roll");
        }

    }
}
