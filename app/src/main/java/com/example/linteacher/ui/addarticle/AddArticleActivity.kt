package com.example.linteacher.ui.addarticle

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.CustomTarget
import com.example.linteacher.R
import com.example.linteacher.api.pojo.artical.ArticlePostRequest
import com.example.linteacher.api.pojo.artical.ArticleUpdateRequest
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.databinding.ActivityAddArticleBinding
import com.example.linteacher.util.BaseActivity
import com.example.linteacher.util.Config
import com.example.linteacher.util.VerticalImageSpan
import java.io.File
import java.io.FileOutputStream


class AddArticleActivity : BaseActivity() {
    private val picUrlList: ArrayList<UrlDrawableResponse> = arrayListOf()
    private lateinit var binding: ActivityAddArticleBinding
    private val factory = AddArticleViewModelFactory(AddArticleRepository())
    private val viewModel: AddArticleViewModel by viewModels {
        factory
    }
    private var articleId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.selectPicture.setOnClickListener {
            imageChooser()
        }
        binding.removeBtn.setOnClickListener {
            showVisible(false, binding.bottomSheet)
        }
        bindSpinnerAdapter(
            R.array.tch_important_array_en,
            binding.articleImportant,
            "",
            this
        )
        binding.addBtn.setOnClickListener {
            getBannerList()


        }


    }

    private fun getBannerList() {
        viewModel.getBannerList()
            .observe(this, {
                if (it.totalCount >= 5 || picUrlList.size <= 0) {
                    uploadArticle()
                } else {

                    showCustomDialog(this, picUrlList)
                }
            })

    }

    private fun uploadArticle() {
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
        if (articleId == -1) {

            val request =
                ArticlePostRequest(
                    articleContent = binding.contentText.text.toString(),
                    articleImportant = articleImportant,
                    articleTag = binding.articleTag.text.toString(),
                    articleTitle = binding.articleTitle.text.toString(),
                )
            viewModel.postArticle(request)
                .observe(this, {
                    Toast.makeText(this, "postArticle ok", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })
        } else {
            var content = binding.contentText.text.toString()
            Log.d("onResourceReady", "onCreate: ${picUrlList.size}")
            for (picUrl in picUrlList) {
                if (content.contains(picUrl.picUrl)) {
                    content = content.replace(picUrl.picUrl, "<img>${picUrl.picUrl}<img>")
                    Log.d("onResourceReady", "replace: $content")
                }
            }
            Log.d("onResourceReady", "content: $content")
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
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                })
        }
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        resultLauncher.launch(Intent.createChooser(i, "Select Picture"))
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
                                val picName = it.picName
                                binding.insertBtn.setOnClickListener {

                                    binding.progressBar.visibility = View.VISIBLE
                                    binding.progressText.visibility = View.VISIBLE
                                    binding.insertBtn.visibility = View.GONE
                                    binding.removeBtn.visibility = View.GONE
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
                                                    picUrlList.add(
                                                        UrlDrawableResponse(
                                                            picUrl = cs, picName = picName
                                                        )
                                                    )
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

    private fun showCustomDialog(context: Context, list: ArrayList<UrlDrawableResponse>) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.banner_dialog)
        val imageView = dialog.findViewById<ImageView>(R.id.imageView)
        var position = 0
        val textView = dialog.findViewById(R.id.checkBox2) as TextView
        Glide.with(context)
            .load(GlideUrl(list[position].picUrl))
            .into(imageView)
        textView.text = list[position].picName
        dialog.findViewById<ImageView>(R.id.previousBtn).setOnClickListener {
            position--
            if (position <= 0) position = 0
            else {
                textView.text = list[position].picName
                Glide.with(context)
                    .load(GlideUrl(list[position].picUrl))
                    .into(imageView)
            }
        }
        dialog.findViewById<ImageView>(R.id.nextBtn).setOnClickListener {
            position++
            if (position >= list.size) position = list.size - 1
            else {
                textView.text = list[position].picName
                Glide.with(context)
                    .load(GlideUrl(list[position].picUrl))
                    .into(imageView)
            }
        }
        val submitBtn: Button = dialog.findViewById(R.id.submitBtn) as Button


        submitBtn.setOnClickListener {
            val request = BannerUpdateRequest(
                banner = true,
                picUrl = list[position].picUrl
            )
            viewModel.updateBanner(request)
                .observe(this, {
                    if (it.responseContent == Config.RESULT_OK) {
                        uploadArticle()
                    }
                })
        }
        val dialogButton: Button = dialog.findViewById(R.id.cancelBtn) as Button
        dialogButton.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            uploadArticle()
        })
        dialog.show()
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