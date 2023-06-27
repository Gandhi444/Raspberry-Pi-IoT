var max_len=10;
var x=0;
var timer;
var Measurments=new Array();
var Pixels=new Array();
var SelectedLedX;
var SelectedLedY;
var SelectedGraph="t";
var labels=[];
var GraphData=[];
var domain;
var names={"t":"Temperatura","p":"Cisnienie","h":"Wilgotność","yaw":"Yaw","pitch":"Pitch","roll":"Roll"}
var units={"t":"C","p":"Ba","h":"%","yaw":"°","pitch":"°","roll":"°"}
var joy=[{"x":0,"y":0}];
var Tp=500
var restartTimer=false
function startTimer(){
    timer = setInterval(Get_JSON, Tp);
  }
  function stopTimer(){
    clearInterval(timer);
  }
  function Get_JSON()
  {
    var url = '/get_all.php';
    $.get(url,function(data,status){
      const obj = JSON.parse(data);
      Pixels=obj.pixels;
      delete obj.pixels;
      joy[0]=obj.joy;
      delete obj.joy;
      Measurments.push(obj);
      GraphData.push(obj[SelectedGraph]);
    if(labels.length<max_len)
    {
      labels.push(GraphData.length);
    }
    while(GraphData.length>max_len)
    {
      GraphData.shift()
    }
    while(labels.length>max_len)
    {
      labels.pop()
    }
    while(Measurments.length>max_len)
    {
      Measurments.shift()
    }
    var frame = document.getElementById("frame");
    var src=frame.src.split('/');
    switch(src[src.length-1])
    {
      case "led.htm":
        {
          for (var i = 0; i < 8; i++)
          {
            for (var j = 1; j < 9; j++)
            {
            var name="led"+((i*8)+j);
            var led=frame.contentDocument.getElementById(name);
            var r,g,b;
            r=Pixels[(i*8)+j-1][0];
            g=Pixels[(i*8)+j-1][1];
            b=Pixels[(i*8)+j-1][2];
            led.style.background="rgb("+r.toString()+","+g.toString()+","+b.toString()+")";
            }
          }
        }
        case "graph.htm":
          {
            //refreshGraph();
          }
    }
    if(restartTimer==true)
    {
      clearInterval(window.parent.timer)
      startTimer()
      restartTimer=false;
    }
    })
  }
  function Pixel_on_click(pixel)
  {
    var nr=parseInt(pixel.id.replace("led",''));
    SelectedLedX=(nr-1)%8
    SelectedLedY=((nr-1)-SelectedLedX)/8
  }
  function SetPixel()
  { 
    var r = document.getElementById("red-value").value;
    var g = document.getElementById("green-value").value;
    var b = document.getElementById("blue-value").value;
    var url="/set_pixel.php?x="+SelectedLedX+"&y="+SelectedLedY+"&r="+r+"&g="+g+"&b="+b;
    console.log(url)
    $.get(url,function(data,status){
    })
  }
  function ChangeFrame(btn)
  {
    
    var id=btn.id
    var frame = document.getElementById("frame");
    switch (id)
    {
      case "Graph_nav":
        {
          src="./graph.htm"
          console.log(btn);
          break;
        }
      case "Pixels_nav":
      {
            src="./led.htm"
            break;
      }
      case "Table_nav":
        {
              src="./table.htm"
              break;
        }
      case "Options_nav":
          {
                src="./options.htm"
                break;
          }
    }
    frame.src=src;
  }
  function SetOptions()
  {
    window.parent.Tp=document.getElementById("Tp").value;
    window.parent.max_len=document.getElementById("Max_size").value;
    window.parent.clearInterval(window.parent.timer)
    window.parent.startTimer()
  }
function refreshGraph()
{
    var frame = document.getElementById("frame");
    new Chart(frame.contentDocument.getElementById("line-chart"), {
        type: 'line',
        data: {
          labels: window.parent.labels,
          datasets: [{ 
              data: window.parent.GraphData,
              label: window.parent.names[window.parent.SelectedGraph],
              borderColor: "#3e95cd",
              fill: false
            }
          ]
        },
        options: {
        animation: {
        duration: 0
    },
    responsive: true
}
      });
}