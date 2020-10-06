package com.marcoperini.sliceat.ui

import android.content.pm.PackageManager
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.hardware.Camera
import android.hardware.Camera.Parameters.FLASH_MODE_OFF
import android.hardware.Camera.Parameters.FLASH_MODE_ON
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.marcoperini.sliceat.R
import org.koin.android.ext.android.inject

class ScanCustomScreen : AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private lateinit var capture: CaptureManager
    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var switchFlashlightButton: ImageButton
    private lateinit var close: ImageButton

    private val cam = Camera.open()
    private val parameters: Camera.Parameters = cam.parameters

    private val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_custom_screen)

        setupView()

        if (!hasFlash()) {
            switchFlashlightButton.visibility = View.GONE
        }

        barcodeScannerView.setTorchListener(this)
        capture = CaptureManager(this, barcodeScannerView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()

        clickListener()

        switchFlashlight()
    }

    private fun setupView() {
        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner)
        switchFlashlightButton = findViewById(R.id.switch_flashlight)
        close = findViewById(R.id.closeButton)

    }

    private fun clickListener() {
        close.setOnClickListener {
            navigator.goToMapsScreen()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * Check if the device's camera has a Flashlight.
     * @return true if there is Flashlight, otherwise false.
     */
    private fun hasFlash(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun switchFlashlight() {
        switchFlashlightButton.setOnClickListener {

            if (parameters.flashMode == FLASH_MODE_OFF) {
                barcodeScannerView.setTorchOn()
                parameters.flashMode = FLASH_MODE_ON
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    switchFlashlightButton.colorFilter = BlendModeColorFilter(ContextCompat.getColor(this, R.color.gray60), BlendMode.SRC_ATOP)
                } else {
                    switchFlashlightButton.setColorFilter(R.color.gray60, PorterDuff.Mode.SRC_ATOP)
                }

            } else if (parameters.flashMode == FLASH_MODE_ON) {
                barcodeScannerView.setTorchOff()
                parameters.flashMode = FLASH_MODE_OFF
                switchFlashlightButton.colorFilter = null
            }
        }
    }

    override fun onTorchOn() {
//        nothing
    }

    override fun onTorchOff() {
//        nothing
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
