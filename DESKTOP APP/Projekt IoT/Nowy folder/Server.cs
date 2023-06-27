using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Projekt_IoT.Nowy_folder
{
    public class Server
    {
        public async Task<string> GETwithClient(String Url)
        {
            string responseText = null;
            try
            {
                using (HttpClient client = new HttpClient())
                {
                    responseText = await client.GetStringAsync(Url);
                }
            }
            catch (Exception e)
            {
                Debug.WriteLine("NETWORK ERROR");
                Debug.WriteLine(e);
            }

            return responseText;
        }

    }
}
