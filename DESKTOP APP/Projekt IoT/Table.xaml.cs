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
    /// Interaction logic for Table.xaml
    /// </summary>
    public partial class Table : Page
    {
        public Table()
        {
            InitializeComponent();
            Table1.ItemsSource = MainViewModel.Table;
        }

        private void TempCheckClick(object sender, RoutedEventArgs e)
        {
            if (TempCheck.IsChecked == true) { MainViewModel.AddTableRow("temp"); }
            else MainViewModel.RemoveTableRow("Temperatura");
            Table1.Items.Refresh();
        }

        private void humCheckClick(object sender, RoutedEventArgs e)
        {
            if (humCheck.IsChecked == true) { MainViewModel.AddTableRow("hum"); }
            else MainViewModel.RemoveTableRow("Wilgotność");
            Table1.Items.Refresh();
        }

        private void preCheckClick(object sender, RoutedEventArgs e)
        {
            if (preCheck.IsChecked == true) { MainViewModel.AddTableRow("pre"); }
            else MainViewModel.RemoveTableRow("Ciśnienie");
            Table1.Items.Refresh();
        }

        private void yawCheckClick(object sender, RoutedEventArgs e)
        {
            if (yawCheck.IsChecked == true) { MainViewModel.AddTableRow("Yaw"); }
            else MainViewModel.RemoveTableRow("Yaw");
            Table1.Items.Refresh();
        }

        private void rollCheckClick(object sender, RoutedEventArgs e)
        {
            if (rollCheck.IsChecked == true) { MainViewModel.AddTableRow("Roll"); }
            else MainViewModel.RemoveTableRow("Roll");
            Table1.Items.Refresh();
        }

        private void pitchCheckClick(object sender, RoutedEventArgs e)
        {
            if (pitchCheck.IsChecked == true) { MainViewModel.AddTableRow("Pitch"); }
            else MainViewModel.RemoveTableRow("Pitch");
            Table1.Items.Refresh();
        }
    }
}
