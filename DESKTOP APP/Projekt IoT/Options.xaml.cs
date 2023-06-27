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
    /// Interaction logic for Options.xaml
    /// </summary>
    public partial class Options : Page
    {
        public Options()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            MainViewModel.SetIp(IpIn.Text);
            MainViewModel.SetTp(int.Parse(TpIn.Text));
            MainViewModel.SetArryLen(int.Parse(SamplesIn.Text));
        }
        public void OnShow()
        {
            IpIn.Text = MainViewModel.ipAddress;
            TpIn.Text = MainViewModel.Tp.ToString();
            SamplesIn.Text = MainViewModel.array_len.ToString();
        }
    }
}
