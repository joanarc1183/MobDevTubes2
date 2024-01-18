package com.example.tubes2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.tubes2.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        val root = binding.root

        val fragmentContainer = requireActivity().findViewById<View>(R.id.fragmentContainer)

        root.setOnClickListener {
            val homeFragment = HomeFragment()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(fragmentContainer.id, homeFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return root
    }
}
