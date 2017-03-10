package ru.tickerparser.util;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract;

import java.nio.charset.StandardCharsets;

public class Tesseract {

    private final tesseract.TessBaseAPI api;

    public Tesseract(String lang) {
        api = new tesseract.TessBaseAPI();
        final String tessdataFolder = System.getProperty("user.dir");
        if (api.Init(tessdataFolder, lang) != 0) {
            System.err.println("Could not initialize tesseract.");
        }
    }

    public void release() {
        api.deallocate();
    }

    public void setCharWhitelist(String whitelist) {
        api.SetVariable("tessedit_char_whitelist", whitelist);
    }

    public String ocr(lept.PIX image) {
        api.SetImage(image);

        final BytePointer outText = api.GetUTF8Text();
        final byte[] outTextStringBytes = outText.getStringBytes();
        final String ocrText = new String(outTextStringBytes, StandardCharsets.UTF_8);

        outText.deallocate();

        return ocrText;
    }

    public lept.PIX ocrWords(lept.PIX image) {
        api.SetImage(image);

        lept.PIXA pixa = null;
        lept.BOXA boxa = api.GetWords(pixa);
        //lept.pixSetTextline(image, null, "TEST", 20, 100, 100, null, (int[])null);
        return lept.pixDrawBoxa(image, boxa, 1, 0);

    }
}

