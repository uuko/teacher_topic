package com.example.linteacher.ui.managearticle.editinner

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.CustomTarget
import com.example.linteacher.R
import com.example.linteacher.api.pojo.artical.ArticlePostRequest
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.artical.ArticleUpdateRequest
import com.example.linteacher.api.pojo.artical.DeleteArticleRequest
import com.example.linteacher.databinding.ActivityEditInnerBinding
import com.example.linteacher.ui.addarticle.AddArticleRequest
import com.example.linteacher.ui.addarticle.UrlDrawableResponse
import com.example.linteacher.ui.main.announce.Content
import com.example.linteacher.util.BaseActivity
import com.example.linteacher.util.Config
import com.example.linteacher.util.VerticalImageSpan
import java.io.File
import java.io.FileOutputStream

class EditInnerActivity : BaseActivity() {
    private lateinit var binding: ActivityEditInnerBinding
    private val factory = EditViewModelFactory(EditRepository())
    private val picUrlList: ArrayList<UrlDrawableResponse> = arrayListOf()
    private val viewModel: EditInnerViewModel by viewModels {
        factory
    }
    var articleId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.selectPicture.setOnClickListener {
            imageChooser()
        }
        articleId = (intent.getSerializableExtra("articleId") as String).toInt()
        observeInnerId(articleId)
        viewModel.data.observe(this, object : Observer<List<UrlDrawableResponse>> {
            override fun onChanged(t: List<UrlDrawableResponse>?) {
                if (t != null) {
                    for (response: UrlDrawableResponse in t) {
                        if (response.isDrawable) {
                            picUrlList.add(UrlDrawableResponse(picUrl = response.picUrl))
                            binding.contentText.text.insert(
                                binding.contentText.selectionStart, response.picUrl
                            )

                            response.drawable?.let {
                                binding.contentText.addImage(
                                    response.picUrl,
                                    it,
                                    binding.contentText,
                                    300, 300,

                                    )
                            }
                        } else {
                            binding.contentText.text.insert(
                                binding.contentText.selectionStart, response.picUrl
                            )
                        }
                    }
                }
            }

        })
        binding.submitBtn.setOnClickListener {
            var content = binding.contentText.text.toString()
            Log.d("submitBtn", "onCreate: ${picUrlList.size} content: $content")
            for (picUrl in picUrlList) {
                if (content.contains(picUrl.picUrl)) {
                    content = content.replace(picUrl.picUrl, "<img>${picUrl.picUrl}<img>")
                    Log.d("submitBtn", "replace: $content")
                }
            }
            Log.d("submitBtn", "content: $content")
            var articleImportant = ""
            if (binding.articleImportant.selectedItemPosition != -1) {
                articleImportant =
                    getResponseSpinner(
                        R.array.tch_important_array_en,
                        binding.articleImportant,
                    )
                if (articleImportant == "重要") articleImportant = "U"
                else if (articleImportant == "普通") articleImportant = "O"
            }
            val request =
                ArticleUpdateRequest(
                    articleId = articleId,
                    articleContent = content,
                    articleImportant = articleImportant,
                    articleTag = binding.articleTag.text.toString(),
                    articleTitle = binding.articleTitle.text.toString(),
                )
            viewModel.updateArticle(request)
                .observe(this, {
                    Toast.makeText(this, "updateArticle ok", Toast.LENGTH_SHORT).show()
                    closeActivity()
                })
        }
        binding.deleteBtn.setOnClickListener {
            val request = ArrayList<DeleteArticleRequest>()
            request.add(DeleteArticleRequest(articleId))
            viewModel.deleteArticle(request)
                .observe(this, {
                    Toast.makeText(this, "deleteArticle ok ${it.result}", Toast.LENGTH_SHORT).show()
                    closeActivity()
//                    if (it.result==Config.RESULT_OK){
//                        Toast.makeText(this, "deleteArticle ok", Toast.LENGTH_SHORT).show()
//                        closeActivity()
//                    }
                })
        }
    }

    private fun closeActivity() {
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(Intent.createChooser(i, "Select Picture"))
    }

    private fun observeInnerId(id: Int) {
        viewModel.getArticle(id.toString())
            .observe(this, object : Observer<ArticleResponse> {
                override fun onChanged(item: ArticleResponse?) {
                    with(binding) {
                        articleTitle.setText(item?.articleTitle)
                        var important = ""
                        if (item?.articleImportant == "U") {
                            important = "重要"

                        } else if (item?.articleImportant == "O") {
                            important = "普通"
                        }
                        bindSpinnerAdapter(
                            R.array.tch_important_array_en,
                            binding.articleImportant,
                            important,
                            this@EditInnerActivity
                        )
                        articleTag.setText(item?.articleTag)
                        modifyDate.text = item?.modifyDate
                        item?.articleContent?.let {
                            viewModel.handleContentDrawable(it)
                        }


                    }
                }

            })
    }


    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    showVisible(true, binding.bottomSheet)
                    binding.progressBar.visibility = View.GONE
                    binding.progressText.visibility = View.GONE
                    binding.insertBtn.visibility = View.VISIBLE
                    val file = getFile(this, selectedImageUri)
                    val drawable = Drawable.createFromPath(file.path)
                    binding.imageViewThumb.setImageDrawable(drawable)
                    var request = AddArticleRequest(
                        haveExist = true,
                        haveBanner = false,
                        haveArticleId = false,
                        exist = false,
                    )
                    if (articleId != -1) {
                        request = AddArticleRequest(
                            haveBanner = false,
                            haveExist = true,
                            exist = true,
                            haveArticleId = true,
                            articleId = articleId,
                        )
                    }

                    viewModel.uploadFile(
                        getFile(this, selectedImageUri), request
                    )
                        .observe(this, {
                            if (it.result == Config.RESULT_OK) {
                                binding.progressBar.visibility = View.GONE
                                binding.progressText.visibility = View.GONE
                                binding.insertBtn.visibility = View.VISIBLE
                                val cs = it.list.picUrl
                                articleId = it.list.articleId
                                binding.insertBtn.setOnClickListener {
                                    Glide.with(this)
                                        .asDrawable()
                                        .load(cs)
                                        .into(object : CustomTarget<Drawable>() {
                                            override fun onLoadCleared(placeholder: Drawable?) {

                                            }

                                            override fun onResourceReady(
                                                resource: Drawable,
                                                transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                                            ) {
                                                if (drawable != null) {
                                                    picUrlList.add(UrlDrawableResponse(picUrl = cs))
                                                    Log.d(
                                                        "onResourceReady", "onResourceReady: " +
                                                                "${binding.contentText.selectionStart}"
                                                    )
                                                    binding.contentText.text.insert(
                                                        binding.contentText.selectionStart, cs
                                                    )
                                                    Log.d(
                                                        "onResourceReady",
                                                        "onResourceReady11111: ${binding.contentText.text}"
                                                    )
                                                    binding.contentText.addImage(
                                                        cs,
                                                        drawable,
                                                        binding.contentText,
                                                        drawable.intrinsicWidth,
                                                        drawable.intrinsicHeight
                                                    )

                                                }
                                            }


                                        })
                                }


                            }
                        })
                }
            }
        }

    fun showVisible(visible: Boolean, view: View) {
        if (visible) {
            view.visibility = View.VISIBLE
        } else {

            view.visibility = View.GONE
        }

    }

    fun EditText.addImage(
        atText: String, imgSrc: Drawable, textView: EditText, imgWidth: Int,
        imgHeight: Int
    ) {
        val ssb = SpannableStringBuilder(this.text)
        val start = text.indexOf(atText)
        Log.d("onResourceReady", "addImage: $start end : ${start + atText.length}")
        imgSrc.mutate()
        imgSrc.setBounds(
            0, 0,
            imgWidth,
            imgHeight
        )
        ssb.setSpan(
            VerticalImageSpan(imgSrc),
            start,
            start + atText.length,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
        textView.setText(ssb, TextView.BufferType.SPANNABLE)
        textView.setSelection(start + atText.length);
        Log.d("onResourceReady", ": ${binding.contentText.text}")
        showVisible(false, binding.bottomSheet)

    }

    fun getFile(mContext: Context, documentUri: Uri): File {
        val inputStream = mContext?.contentResolver?.openInputStream(documentUri)
        var file = File("")
        inputStream.use { input ->
            file =
                File(mContext?.cacheDir, System.currentTimeMillis().toString() + ".jpg")
            FileOutputStream(file).use { output ->
                val buffer =
                    ByteArray(4 * 1024) // or other buffer size
                var read: Int = -1
                while (input?.read(buffer).also {
                        if (it != null) {
                            read = it
                        }
                    } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        return file
    }

}