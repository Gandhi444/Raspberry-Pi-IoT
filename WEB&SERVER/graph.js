var chart;
function GraphChanged(Radio) {
    window.parent.SelectedGraph = Radio.id
    var data=window.parent.Measurments;
    window.parent.labels=[];
    window.parent.GraphData=[];
    for (var i=0;i<data.length;i++)
    {
        window.parent.GraphData.push(data[i][window.parent.SelectedGraph]);
        window.parent.labels.push(i);
    }
    refreshGraph();
}
function refreshGraph()
{
  chart.datasets=[{ 
    data: window.parent.GraphData,
    label: window.parent.names[window.parent.SelectedGraph],
    borderColor: "#3e95cd",
    fill: false
  }
]
chart.update();
}
function startGraph(){
  chart= new Chart(document.getElementById("line-chart"), {
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
      timer = setInterval(refreshGraph, 0.1);
    }
