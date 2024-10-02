package com.example.movieapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.databinding.FragmentTvBinding
import com.example.movieapp.models.TV
import com.example.movieapp.models.TVResponse
import com.example.movieapp.services.TVApiInterface
import com.example.movieapp.services.TVApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVFragment : Fragment() {
    private val tvList = arrayListOf<TV>()
    private var _binding: FragmentTvBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTvList.layoutManager = LinearLayoutManager(this.context)
        binding.rvTvList.setHasFixedSize(true)
        getTVData { tv: List<TV> ->
            binding.rvTvList.adapter = TVAdapter(tv)
        }
        showRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getTVData(callback: (List<TV>) -> Unit) {
        val apiService = TVApiService.getInstance().create(TVApiInterface::class.java)
        apiService.getTVList().enqueue(object : Callback<TVResponse> {
            override fun onFailure(call: Call<TVResponse>, t: Throwable) {
                // Handle the error
            }

            override fun onResponse(call: Call<TVResponse>, response: Response<TVResponse>) {
                response.body()?.let {
                    callback(it.tv)
                }
            }
        })
    }

    private fun showRecyclerView() {
        binding.rvTvList.layoutManager = LinearLayoutManager(this.context)
        binding.rvTvList.adapter = TVAdapter(tvList)
    }
}
