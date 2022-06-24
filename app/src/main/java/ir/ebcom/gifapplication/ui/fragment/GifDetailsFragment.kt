package ir.ebcom.gifapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import ir.ebcom.gifapplication.data.localmodel.GifModel
import ir.ebcom.gifapplication.databinding.FragmentGifDetailsBinding
import ir.ebcom.gifapplication.ui.base.BaseFragment

@AndroidEntryPoint
class GifDetailsFragment: BaseFragment() {

    lateinit var glide:RequestManager
    private var _binding: FragmentGifDetailsBinding? = null
    private val binding get() =_binding!!
    private val args: GifDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGifDetailsBinding.inflate(inflater,container,false)
        glide = Glide.with(this)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        showGifDetails(args.gifDetails)
    }

    private fun setupListeners() {
        binding.backIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun showGifDetails(gifModel: GifModel) {
        binding.gifDetailsView.setGifDetails(gifModel)
        binding.gifHeaderTv.text = gifModel.title
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}