package com.example.iot_project.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.iot_pr.SharedViewModel
import com.example.iot_project.R
import com.example.iot_project.databinding.FragmentOptionsBinding

class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(requireActivity()).get(OptionsViewModel::class.java)

        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val model = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        var ConfirmButton=root.findViewById<Button>(R.id.Confirm)
        var ip=root.findViewById<EditText>(R.id.IpIn)
        var samples=root.findViewById<EditText>(R.id.SamplesIn)
        var tp=root.findViewById<EditText>(R.id.TpIn)
        model.IP.observe(requireActivity(), Observer {
            // updating data in displayMsg
            ip.setText(it.toString())
        })
        model.max_list_size.observe(requireActivity(), Observer {
            // updating data in displayMsg
            samples.setText(it.toString())
        })
        model.Tp.observe(requireActivity(), Observer {
            tp.setText(it.toString())
        })
        ConfirmButton.setOnClickListener()
        {
            model.Set_Ip(ip.text.toString())
            model.set_max_list_size(samples.text.toString().toInt())
            model.set_Tp(tp.text.toString().toInt())
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}