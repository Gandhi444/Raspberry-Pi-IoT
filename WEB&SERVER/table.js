var tablerows=[]
var chart
var checkedboxes=["t","p","h","yaw","pitch","roll"]
function tablechanged(checkBox)
{
    var table=document.getElementById("table")
    if(checkBox.checked == true)
    {
        tablerows.push(checkBox.id)
        var row= table.insertRow(-1);
        row.id=checkBox.id+"row";
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.innerHTML = window.parent.names[checkBox.id];
        cell2.innerHTML = window.parent.Measurments[window.parent.Measurments.length-1][checkBox.id]
        cell3.innerHTML = window.parent.units[checkBox.id];
    }else
    {
        tablerows.splice(tablerows.indexOf(checkBox.id),1)
        document.getElementById(checkBox.id+"row").remove();
    }
}
function initTable()
{
    var table=document.getElementById("table")
    var row= table.insertRow(-1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    cell1.innerHTML = "Pomiar";
    cell2.innerHTML = "Wartość";
    cell3.innerHTML = "Jednostka";
    console.log("test")
}

function refreshJoy_table()
{
    for(var i=0 ;i<tablerows.length;i++)
    {
        document.getElementById(tablerows[i]+"row").cells[1].innerHTML=window.parent.Measurments[window.parent.Measurments.length-1][tablerows[i]]
    }
    chart.datasets=    chart.datasets=[{
        label: 'JoyStick',
        data: window.parent.joy,
        borderColor: "#3e95cd",
        fill: false
      }]
      chart.update()
}
function startJoy()
{
      chart=new Chart(document.getElementById("scatter-chart"), {
        type: 'scatter',
        data: {
            datasets: [{
              label: 'JoyStick',
              data: window.parent.joy,
              borderColor: "#3e95cd",
              fill: false
            }],
           },
        options: {
        animation: {
        duration: 0
    },
    responsive: true,
    scales: {
        yAxes: [{
            display: true,
            ticks: {
                suggestedMin: -8,
                suggestedMax :8
            }
        }],
        xAxes: [{
            display: true,
            ticks: {
                suggestedMin: -8,
                suggestedMax :8
            }
        }]}
 }
     });
      timer = setInterval(refreshJoy_table, 0.1);
}