using Projekt_IoT.Nowy_folder;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Policy;
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
    /// Interaction logic for MainWindow.xaml
    /// </summary>
   
    public partial class MainWindow : Window
    {
        public static Pixels pixels = new Pixels();
        public static Graph graph = new Graph();
        public static Table table = new Table();
        public static Options options = new Options();
        public MainWindow()
        {
            InitializeComponent();
            Main.Content = graph;
        }

        private void Button_Click_2(object sender, RoutedEventArgs e)
        {
            Main.Content = graph;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Main.Content = pixels;
            
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            Main.Content = table;
        }

        private void Button_Click_3(object sender, RoutedEventArgs e)
        {
            options.OnShow();
            Main.Content = options;

        }
    }
}
