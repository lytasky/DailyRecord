package com.lytasky.dailyrecord.action

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.EnvironmentCompat
import com.bumptech.glide.Glide
import com.lytasky.dailyrecord.R
import com.lytasky.dailyrecord.base.database.DatabaseHelper
import com.lytasky.dailyrecord.base.database.RecordContent
import com.lytasky.dailyrecord.utils.FileUtils
import com.lytasky.dailyrecord.utils.StringUtils
import com.lytasky.dailyrecord.utils.TimeUtils
import com.lytasky.dailyrecord.utils.ViewUtils
import permissions.dispatcher.*
import java.text.SimpleDateFormat


/**
 *  Created by Liaoxinhui on 2021/3/14 2:22 PM
 */
@RuntimePermissions
class EditRecordActivity : AppCompatActivity() {

    private lateinit var closeSaveAction: ImageView;
    private lateinit var dateInfo: TextView;
    private lateinit var timeInfo: TextView;
    private lateinit var recordContentEt: EditText;
    private lateinit var picArrayLl: LinearLayout;
    private lateinit var picPlaceHolder: RelativeLayout;

    private val CAMERA = 1001;
    private val PICTURE = 1002;

    private val MAX_PIC_NUM = 3;

    private var recordContent = "";
    private var picArray = mutableListOf<String>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)
        initView();
    }

    private fun initView() {
        closeSaveAction = findViewById(R.id.close_save_action);
        closeSaveAction.setOnClickListener {
            if (isRecordEmpty()) {
                Toast.makeText(this, "退出", Toast.LENGTH_SHORT).show()
                finish();
            } else {
                saveToDatabase();
                Toast.makeText(this, "记录成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        dateInfo = findViewById(R.id.date_info)
        dateInfo.text = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("MM月dd日"))
        timeInfo = findViewById(R.id.time_info)
        timeInfo.text = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("HH:mm"))
        recordContentEt = findViewById(R.id.record_content)
        recordContentEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                recordContent = editable.toString();
                adjustCloseSaveIcon();
            }
        })
        picArrayLl = findViewById(R.id.pic_array_ll)
        picPlaceHolder = findViewById(R.id.pic_placeholder)
        picPlaceHolder.setOnClickListener {
            takePhotoWithPermissionCheck();
        }
    }

    private fun isRecordEmpty(): Boolean {
        return TextUtils.isEmpty(recordContent) && picArray.size === 0
    }

    private fun adjustCloseSaveIcon() {
        if (isRecordEmpty()) {
            Glide.with(this).load(R.mipmap.close).into(closeSaveAction)
        } else {
            Glide.with(this).load(R.mipmap.save).into(closeSaveAction)
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun takePhoto() {
        var mediaSelectDialog = layoutInflater.inflate(R.layout.media_select_dialog, null);
        var alertDialog = AlertDialog.Builder(this)
            .setView(mediaSelectDialog)
            .setCancelable(true)
            .show();
        var gallery: LinearLayout = mediaSelectDialog.findViewById(R.id.gallery_ll)
        var camera: LinearLayout = mediaSelectDialog.findViewById(R.id.camera_ll)
        gallery.setOnClickListener {
            alertDialog.dismiss()
            val picture =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(picture, PICTURE)
        }
        camera.setOnClickListener {
            alertDialog.dismiss()
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, CAMERA)
        }
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        ViewUtils.showRationaleDialog(this, R.string.camera_permission_rationale, request)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        Toast.makeText(this, R.string.camera_permission_denied, Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.camera_permission_never_ask_again, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CAMERA && resultCode === Activity.RESULT_OK && attr.data != null) {
            // 拍照
            val bundle: Bundle = data?.extras!!
            var bitmap: Bitmap = bundle.get("data") as Bitmap
            var filePath: String =
                Environment.getExternalStorageDirectory().absolutePath + "/" + System.currentTimeMillis() + "_pic.png"
            FileUtils.saveBitmap2File(bitmap, filePath)
            addPicImageView(filePath)
        } else if (requestCode === PICTURE && resultCode === Activity.RESULT_OK && attr.data != null) {
            //图库
            val uri: Uri = data?.data!! //获取系统返回的照片uri
            val strings = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor = contentResolver.query(uri, strings, null, null, null)!!
            cursor.moveToFirst()
            val index: Int = cursor.getColumnIndex(strings[0])
            addPicImageView(cursor.getString(index))
            cursor.close()
        }
    }

    private fun addPicImageView(path: String) {
        if (TextUtils.isEmpty(path) == null) {
            return;
        }
        var picIV = ViewUtils.addPicImageView(picArrayLl, this)
        Glide.with(this)
            .load(path)
            .into(picIV);
        picArray.add(path);
        if (picArray.size === MAX_PIC_NUM) {
            picPlaceHolder.visibility = View.GONE;
        }
        adjustCloseSaveIcon();
    }

    private fun saveToDatabase() {
        return DatabaseHelper.insertRecord(
            RecordContent(
                recordContent,
                StringUtils.arrayToSegString(picArray, ","),
                ""
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissions[0] == Manifest.permission.CAMERA && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePhoto();
        }
    }

    override fun finish() {
        super.finish()
    }
}