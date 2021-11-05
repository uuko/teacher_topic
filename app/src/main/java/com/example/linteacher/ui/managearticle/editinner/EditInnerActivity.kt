package com.example.linteacher.ui.managearticle.editinner

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.target.CustomTarget
import com.example.linteacher.R
import com.example.linteacher.api.pojo.artical.ArticleResponse
import com.example.linteacher.api.pojo.artical.ArticleUpdateRequest
import com.example.linteacher.api.pojo.artical.DeleteArticleRequest
import com.example.linteacher.api.pojo.banner.BannerUpdateRequest
import com.example.linteacher.databinding.ActivityEditInnerBinding
import com.example.linteacher.ui.addarticle.AddArticleRequest
import com.example.linteacher.ui.addarticle.UrlDrawableResponse
import com.example.linteacher.util.BaseActivity
import com.example.linteacher.util.Config
import com.example.linteacher.util.VerticalImageSpan
import com.example.linteacher.util.screenRectDp
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

        binding.submitBtn.setOnClickListener {
            getBannerList()

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

    private fun getBannerList() {
        viewModel.getBannerList()
            .observe(this, {
                var list = ArrayList<UrlDrawableResponse>()
//                val content = binding.contentText.html
                list = getPicList(binding.contentText.html)
//                for (picUrl in picUrlList) {
//
//                    if (content.contains(picUrl.picUrl)) {
//                        list.add(picUrl)
//                    }
//                }
                if (it.totalCount >= 5 || list.size <= 0) {
                    uploadArticle()
                } else {
                    showPreviewCheckDialog(list)

                }
            })
    }

    private fun showPreviewCheckDialog(list: ArrayList<UrlDrawableResponse>) {
        AlertDialog.Builder(this)
            .setMessage("是否需要更新Banner?")
            .setTitle("title")
            .setPositiveButton("確定",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    showCustomDialog(this, list)
                })
            .setNegativeButton(
                "更新文章",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    uploadArticle()
                }
            )
            .setCancelable(false)
            .create()
            .show()

    }

    fun showCustomDialog(context: Context, list: ArrayList<UrlDrawableResponse>) {
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

    private fun uploadArticle() {
        val content = binding.contentText.html
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
                        modifyDate.text = pareDate(item!!.modifyDate)
                        item?.articleContent?.let {
//                            viewModel.handleContentDrawable(it)
                            binding.contentText.html = it
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
                                val picName = it.picName
                                binding.insertBtn.setOnClickListener {
                                    picUrlList.add(
                                        UrlDrawableResponse(
                                            picUrl = cs,
                                            picName = picName
                                        )
                                    )
                                    binding.contentText.insertImage(
                                        cs, picName, screenRectDp.width().toInt()
                                    )
                                    binding.bottomSheet.visibility = View.GONE

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