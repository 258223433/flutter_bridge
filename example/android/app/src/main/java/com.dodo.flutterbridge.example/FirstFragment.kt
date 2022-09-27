package com.dodo.flutterbridge.example

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dodo.flutterbridge.example.databinding.FragmentFirstBinding
import com.dodo.flutterbridge.function.FlutterFunction
import io.flutter.plugin.common.MethodChannel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//            FlutterFunction<Int,Int>("nativeInvoke",Int::class.java).invoke(1, object : MethodChannel.Result {
//                override fun success(result: Any?) {
//                    Toast.makeText(requireContext(),result.toString(),Toast.LENGTH_SHORT).show()
//                }
//
//                override fun error(errorCode: String, errorMessage: String?, errorDetails: Any?) {
//                    Toast.makeText(requireContext(),errorMessage,Toast.LENGTH_SHORT).show()
//                }
//
//                override fun notImplemented() {
//                    Toast.makeText(requireContext(),"notImplemented",Toast.LENGTH_SHORT).show()
//                }
//
//            })
            lifecycleScope.launch {
                FlutterFunction<Int,Int>("nativeInvoke").invokeFlow(1).collect {
                    Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}