package com.example.iot_project.ui.pixels

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.iot_pr.SharedViewModel
import com.example.iot_project.R
import com.example.iot_project.databinding.FragmentPixelsBinding
import org.json.JSONArray

class PixelsFragment : Fragment() {

    private var _binding: FragmentPixelsBinding? = null
    lateinit var PixelArray:JSONArray
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var handler= Handler()
    lateinit var queue: RequestQueue
    var ip:String="192.168.1.130"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pixelsViewModel =
            ViewModelProvider(this).get(PixelsViewModel::class.java)

        _binding = FragmentPixelsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        var PixelTable=root.findViewById<TableLayout>(R.id.Pixels)
        var pixels:MutableList<View> = mutableListOf()
        var backgrounds:MutableList<View> = mutableListOf()
        queue = Volley.newRequestQueue(requireActivity())
        for (i in 0..PixelTable.childCount-1)
        {
            var row:ViewGroup=PixelTable.getChildAt(i) as ViewGroup
            for (j in 0 ..row.childCount-1)
            {
                var background:ViewGroup=row.getChildAt(j) as ViewGroup
                backgrounds.add(row.getChildAt(j))
                for (k in 0 .. background.childCount-1)
                {
                    pixels.add(background.getChildAt(k))
                }
            }
        }
        model.pixels.observe(requireActivity(), Observer {
            if (it.length() != 0)
            {
            PixelArray=it
            var i=0
            for (pixel in pixels)
            {
                var r=PixelArray.getJSONArray(i)[0]
                var g=PixelArray.getJSONArray(i)[1]
                var b=PixelArray.getJSONArray(i)[2]
                pixel.setBackgroundColor(Color.argb(255,r as Int,g as Int,b as Int))
                i++
            }
            }
        })
        model.IP.observe(requireActivity(), Observer {
            ip= it
        })


        var r=root.findViewById<EditText>(R.id.r)
        var g=root.findViewById<EditText>(R.id.g)
        var b=root.findViewById<EditText>(R.id.b)
        var x=root.findViewById<EditText>(R.id.x)
        var y=root.findViewById<EditText>(R.id.y)
        var Send_btn=root.findViewById<Button>(R.id.Send)
        Send_btn.setOnClickListener()
        {
            var url="http://"+ip+"/set_pixel.php?x="+x.text+"&y="+y.text+"&r="+r.text+"&g="+g.text+"&b="+b.text
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response -> {  }}
                ,{err-> Log.d("err",err.toString())})
            queue.add(stringRequest)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}