package com.satya.bookmyshowclone.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.satya.bookmyshowclone.constants.AppConstants
import com.satya.bookmyshowclone.databinding.FragmentHomeBinding
import kotlin.math.log


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var city = ""
    private var time = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        city = activity?.intent?.getStringExtra("cityName").toString()
        time = activity?.intent?.getStringExtra("time").toString()

        if(time == "first") {
            storeDataInSharedPreference(city)
        } else {
            Log.e("city", city)
        }



        Log.e("city", "$city" )

        //send data to share preference



        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    private fun storeDataInSharedPreference(city: String?) {

        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(AppConstants.sharedPrefFile, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("city",city)
        editor.apply()

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}