package com.example.iot_project.ui.graph

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iot_pr.SharedViewModel
import com.example.iot_project.R
import com.example.iot_project.databinding.FragmentGraphBinding
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class GraphsFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var Boxes:MutableList<RadioButton> = mutableListOf<RadioButton>()
    private val units:List<String> = listOf<String>("C", "%","Ba" ,"°","°","°")
    private val names:List<String> = listOf<String>("Temperature", "Humidity","Pressure" ,"Yaw","Roll","Pitch")
    private var dataSeries:LineGraphSeries<DataPoint>? = null
    lateinit var lineGraph: GraphView
    var update:Boolean =true
    var x=0;
    var max_list_size:Int=200
    lateinit var  gridLabel: GridLabelRenderer
   // var dataSeries:MutableList<Float> = mutableListOf<Float>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       update=true
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
       val root: View = binding.root
       val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
       lineGraph=root.findViewById(R.id.dataGraph)
        gridLabel= lineGraph.gridLabelRenderer
        Boxes.clear()
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxTemperature))
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxHumidity))
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxPressure))
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxYaw))
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxRoll))
        Boxes.add(root.findViewById<RadioButton>(R.id.radioBoxPitch))

       gridLabel.horizontalAxisTitle="samples"
       model.temperature.observe(requireActivity(), Observer {
           observer_fun(0,it)
       })
       model.humidity.observe(requireActivity(), Observer {
           observer_fun(1,it)
       })
       model.preasure.observe(requireActivity(), Observer {
           observer_fun(2,it)
       })
       model.yaw.observe(requireActivity(), Observer {
           observer_fun(3,it)
       })
       model.roll.observe(requireActivity(), Observer {
           observer_fun(4,it)
       })
       model.pitch.observe(requireActivity(), Observer {
           observer_fun(5,it)
       })
       model.max_list_size.observe(requireActivity(), Observer {
           max_list_size=it
       })
        lineGraph.getViewport().setXAxisBoundsManual(true);
        lineGraph.getViewport().setMinX(0.0);
        lineGraph.getViewport().setMaxX(max_list_size.toDouble()-1);

        for( box in Boxes)
        {
            box.setOnClickListener{
                update=true
            }
        }



        return root
    }

    fun convert_to_data_series(list:MutableList<Float>)
    {
        x=0
        var buf: LineGraphSeries<DataPoint> = LineGraphSeries(arrayOf())
        for (point in list)
        {
            buf.appendData(DataPoint(x.toDouble(),point.toDouble()),false,max_list_size)
            x++
        }
        dataSeries=buf
        lineGraph.removeAllSeries()
        lineGraph.addSeries(dataSeries)
    }
    fun observer_fun(nr:Int,it:MutableList<Float>)
    {
        if (Boxes[nr].isChecked)
        {
            gridLabel.verticalAxisTitle=names[nr]+"["+units[nr]+"]"
            gridLabel.setPadding(40)
            convert_to_data_series(it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}