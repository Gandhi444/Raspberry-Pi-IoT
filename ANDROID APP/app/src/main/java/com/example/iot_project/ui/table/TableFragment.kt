package com.example.iot_project.ui.table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iot_pr.SharedViewModel
import com.example.iot_project.R
import com.example.iot_project.databinding.FragmentTableBinding
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries


class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var Boxes:MutableList<CheckBox> = mutableListOf<CheckBox>()
    private val units:List<String> = listOf<String>("C", "%","Ba" ,"°","°","°")
    private val names:List<String> = listOf<String>("Temperature", "Humidity","Pressure" ,"Yaw","Roll","Pitch")
    private var last:MutableList<Float> = mutableListOf<Float>(0.0f,0.0f,0.0f,0.0f,0.0f,0.0f)
    lateinit var table:TableLayout
    lateinit var Joy: GraphView
    private var dataSeries: PointsGraphSeries<DataPoint>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        table = root.findViewById(R.id.MainTable)
        Joy=root.findViewById(R.id.Joy)

        Joy.getViewport().setXAxisBoundsManual(true);
        Joy.getViewport().setYAxisBoundsManual(true);
        Joy.getViewport().setMinX(-8.0);
        Joy.getViewport().setMaxX(8.0);
        Joy.getViewport().setMinY(-8.0);
        Joy.getViewport().setMaxY(8.0);
        model.Joy.observe(requireActivity(), Observer {
            var buf: PointsGraphSeries<DataPoint> = PointsGraphSeries(arrayOf())
            buf.appendData(DataPoint(it[0].toDouble(),it[1].toDouble()),false,1)
            dataSeries=buf
            Joy.removeAllSeries()
            Joy.addSeries(dataSeries)
        })
        Boxes.clear()
        model.temperature.observe(requireActivity(), Observer {
           if (it.isNotEmpty()) last[0]= it.last().toFloat()
        })
        model.preasure.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) last[1]= it.last().toFloat()
        })
        model.humidity.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) last[2]= it.last().toFloat()
        })
        model.yaw.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) last[3]= it.last().toFloat()
        })
        model.roll.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) last[4]= it.last().toFloat()
        })
        model.pitch.observe(requireActivity(), Observer {
            if (it.isNotEmpty()) last[5]= it.last().toFloat()
        })
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxTemperature))
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxHumidity))
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxPressure))
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxYaw))
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxRoll))
        Boxes.add(root.findViewById<CheckBox>(R.id.checkBoxPitch))
        val tbrow0 = TableRow(requireActivity())
        val tv0 = TextView(requireActivity())
        tv0.text = " Pomiar"
        tv0.setPadding(0,0,100,0)
        tbrow0.addView(tv0)
        val tv1 = TextView(requireActivity())
        tv1.text = " Wartość "
        tv1.setPadding(0,0,100,0)
        tbrow0.addView(tv1)
        val tv2 = TextView(requireActivity())
        tv2.text = " Jednostka "
        tv2.setPadding(0,0,20,0)
        tbrow0.addView(tv2)
        table.addView(tbrow0)
        for (box in Boxes)
        {
            box.setOnClickListener{
                var i=0
                table.removeAllViews()
                val tbrow0 = TableRow(requireActivity())
                val tv0 = TextView(requireActivity())
                tv0.text = " Pomiar"
                tv0.setPadding(0,0,100,0)
                tbrow0.addView(tv0)
                val tv1 = TextView(requireActivity())
                tv1.text = " Wartość "
                tv1.setPadding(0,0,100,0)
                tbrow0.addView(tv1)
                val tv2 = TextView(requireActivity())
                tv2.text = " Jednostka "
                tv2.setPadding(0,0,20,0)
                tbrow0.addView(tv2)
                table.addView(tbrow0)
                for (box in Boxes)
                {

                    if(box.isChecked())
                    {
                        var tbrow=TableRow(requireActivity())
                        val tv0 = TextView(requireActivity())
                        tv0.text=names[i]
                        tbrow.addView(tv0)
                        val tv1 = TextView(requireActivity())
                        tv1.text=last[i].toString()
                        tbrow.addView(tv1)
                        val tv2 = TextView(requireActivity())
                        tv2.text=units[i]
                        tbrow.addView(tv2)
                        table.addView(tbrow)
                    }
                    i++
                }
            }
        }

        return root
            }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}