package com.exae.memorialapp.home.artical

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.exae.memorialapp.R
import com.exae.memorialapp.base.PosBaseActivity
import com.exae.memorialapp.databinding.ActivityAlbumPublishBinding
import com.exae.memorialapp.databinding.ActivityWorshipRecordBinding
import com.exae.memorialapp.viewmodel.MemorialModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@Route(path = "/app/album/publish")
class AlbumPublishActivity : PosBaseActivity<ActivityAlbumPublishBinding>() {
    private var memorialNo = -1
    private var content = ""
    private var articleId = -1
    private var type = -1
    private var showMessage = ""
    private val viewModel: MemorialModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_album_publish)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setToolTitle("上传图片")
        setBackState(true)

        memorialNo = intent.getIntExtra("memorialNo", -1)
//        content = intent.getStringExtra("content") ?: ""
        type = intent.getIntExtra("type", -1)
//        articleId = intent.getIntExtra("articleId", -1)

        initView()
    }

    private fun initView() {

    }

    override fun getViewBinding(): ActivityAlbumPublishBinding {
        return ActivityAlbumPublishBinding.inflate(layoutInflater)
    }
}