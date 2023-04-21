package ru.sejapoe.digitalhotel.ui.qr;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCodeViewModel extends ViewModel {
    public LiveData<Bitmap> generateQrCode(String content) {
        MutableLiveData<Bitmap> bitmapMutableLiveData = new MutableLiveData<>();
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
            bitmapMutableLiveData.postValue(bitmap);
        } catch (WriterException ignored) {

        }
        return bitmapMutableLiveData;
    }
}
