package com.blueland.permissioncheck

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.blueland.permissioncheck.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_main) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.btnRequestPermission.setOnClickListener {
            requestPermission()
        }
    }

    /**
     * 권한 승인 요청 결과 확인
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 99) {
            var checkResult = true

            // 모든 퍼미션을 허용했는지 체크
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }

            // 권한 승인 여부 출력
            binding.tvPermissionState.text =
                if (checkResult) {
                    "권한 있음"
                } else {
                    Toast.makeText(this, "모든 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                    "권한 없음"
                }
        }
    }

    override fun onResume() {
        super.onResume()
        // 권한 승인 여부 출력
        binding.tvPermissionState.text = if (isCheckedPermission()) "권한 있음" else "권한 없음"
    }

    /**
     * 권한 승인 요청
     */
    private fun requestPermission() {
        requestPermissions(arrayOf(Manifest.permission.CAMERA), 99)
    }

    /**
     * 권한 승인 여부 확인
     * @return 승인 여부
     */
    private fun isCheckedPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
}