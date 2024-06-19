package com.exae.memorialapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.exae.memorialapp.adapter.AttentionMemorialAdapter
import com.exae.memorialapp.base.BaseFragment
import com.exae.memorialapp.base.handleResponse
import com.exae.memorialapp.bean.AttentionListModel
import com.exae.memorialapp.databinding.FragmentIndexBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import com.orhanobut.logger.Logger
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint //1
class ToDoListFragment : BaseFragment<FragmentIndexBinding>(), OnItemClickListener {

    //    private var _binding: FragmentIndexBinding? = null
//    private val binding get() = _binding!!
    private val viewModel: MemorialModel by viewModels()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?,
//    ): View? {
//        _binding = FragmentIndexBinding.inflate(inflater, container, false)
//        return binding.root
//    }

    @Inject
    lateinit var listAdapter: AttentionMemorialAdapter

    val bannerList = mutableListOf<String>()

    val bannerListss = mutableListOf<AttentionListModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        bannerList.add("https://ss0.baidu.com/94o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/a6efce1b9d16fdfabf36882ab08f8c5495ee7b9f.jpg")
//        bannerList.add("https://ss3.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/0824ab18972bd40797d8db1179899e510fb3093a.jpg")
//        bannerList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2647888545,3751969263&fm=224&gp=0.jpg")
        viewModel.bannerRequest()
        requestNetData()
        listAdapter.setOnItemClickListener(this)
        binding.apply {
//            smartRefreshLayout.setRefreshHeader(BezierRadarHeader(context))
            mListView.layoutManager = GridLayoutManager(context, 2)
            mListView.adapter = listAdapter
//            smartRefreshLayout.setOnRefreshListener {
//                requestNetData()
//                smartRefreshLayout.finishRefresh(true)
//            }
            emptyView.setOnClickListener {
                requestNetData()
            }
        }

        viewModel.bannerResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources, {
                it.data?.forEach { item ->
                    bannerList.clear()
                    bannerList.add(item.picUrl ?: "")
                }
                binding.banner.adapter.setDatas(bannerList)
            },
                {

                }
            )
        })

        viewModel.attentionListResponse.observe(viewLifecycleOwner, Observer { resources ->
            handleResponse(resources) {
                if (!it.data.isNullOrEmpty()) {
                    listAdapter.data.clear()
                    listAdapter.data.addAll(it.data)
                    listAdapter.notifyDataSetChanged()
                    listAdapter.setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInBottom)
                    binding.emptyView.visibility = View.GONE
                    binding.mListView.visibility = View.VISIBLE
                } else {
                    binding.emptyView.visibility = View.VISIBLE
                    binding.mListView.visibility = View.GONE
//                    bannerListss.add(AttentionListModel(2,"0","name0",""))
//                    bannerListss.add(AttentionListModel(1,"1","name1",""))
//                    bannerListss.add(AttentionListModel(3,"2","name2",""))
//                    listAdapter.data.clear()
//                    listAdapter.data.addAll(bannerListss)
//                    listAdapter.notifyDataSetChanged()
                }
            }
        })

        with(binding) {
            floatCreate.setOnClickListener {
                ARouter.getInstance().build("/app/create/hall").navigation(requireContext())
            }
            banner.setAdapter(object : BannerImageAdapter<String>(bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: String?,
                    position: Int,
                    size: Int
                ) {
                    if (holder != null) {
                        Glide.with(holder.itemView)
                            .load(bannerList[position])
                            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                            .into(holder.imageView)
                    }
                }
            })
                .addBannerLifecycleObserver(requireActivity())
                .indicator = CircleIndicator(requireContext())
        }

    }

    private fun requestNetData() {
        viewModel.attentionListRequest()
    }

    override fun onResume() {
        super.onResume()
        requestNetData()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentIndexBinding {
        return FragmentIndexBinding.inflate(inflater, container, false)
    }

    override fun onStart() {
        super.onStart()
        binding.banner.start()
        Logger.i("onStart")
    }

    override fun onStop() {
        super.onStop()
        binding.banner.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding.banner.destroy()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val item = adapter.data[position] as AttentionListModel
        ARouter.getInstance().build("/app/home")
            .withInt("memorialNo", item.memorialNo)
            .withString("memorialName", item.name)
            .withString("memorialType", item.type)
            .navigation(requireContext())
    }
}