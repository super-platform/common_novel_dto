package com.platform.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommonUtils {
    public static Map<String, Object> objToMap(Object obj){
        if( !ObjectUtils.isEmpty(obj)){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for(Field field: obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            }catch (Exception e){
                log.error("obj Exception: {}", e.getMessage());
            }
        }
        return map;
    }

    public static byte[] decodeBase64ToByteData(String base64EncodedData){
        if(StringUtils.isNotBlank(base64EncodedData)){
            return Base64.getDecoder().decode(base64EncodedData);
        }

        return null;
    }

    public static String encodeByteToBase64(byte[] data){
        if(data != null && data.length > 0){
            return new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
        }

        return null;
    }

    public static byte[] convertDocToTiff(byte[] data) throws Exception{
        if(ObjectUtils.isEmpty(data)){
            return null;
        }
        PDDocument pdDocument;
        try {
            pdDocument = PDDocument.load(data);
        } catch (IOException e){
            log.error("Convert doc to tiff failed: {}", e.getMessage());
            return null;
        }
        return savePDFAsTiffDoc(pdDocument);
    }
    private static byte[] savePDFAsTiffDoc(PDDocument pdf) throws Exception{
//        List<Image> images = new ArrayList<>();
//        PDFRenderer pdfRenderer = new PDFRenderer(pdf);
//        BufferedImage[] bufferedImages = new BufferedImage[pdf.getNumberOfPages()];
//        int pageCounter = 0;
//        for (PDPage pdPage : pdf.getPages()){
//            try {
//                bufferedImages[pageCounter] = pdfRenderer.renderImageWithDPI(pageCounter,200f, ImageType.GRAY);
//                Image image = bufferedImages[pageCounter];
//                images.add(image);
//
//                pageCounter++;
//            }catch (Exception e){
//                log.error("PDF render error with exception: {}", e.getMessage());
//                throw e;
//            }
//        }
//        TIFFEncodeParam param = new TIFFEncodeParam();
//        param.setExtraImages(images.listIterator(1));
//
//        //set dpi for image 200 for X and Y resolution
//        TIFFField[] extras = new TIFFField[3];
//        extras[0] = new TIFFField(TAG_X_RESOLUTION, TIFFField.TIFF_RATIONAL,1,new long[][]{{(long) 200, (long) 1}, {(long) 0, (long) 0}});
//        extras[0] = new TIFFField(TAG_Y_RESOLUTION, TIFFField.TIFF_RATIONAL,1,new long[][]{{(long) 200, (long) 1}, {(long) 0, (long) 0}});
//        extras[0] = new TIFFField(TAG_RESOLUTION_UNIT, TIFFField.TIFF_SHORT,1, new char[]{2});
//        param.setExtraFields(extras);
//
//        param.setCompression(NONE);
//
//        ByteArrayOutputStream output = null;
//        try {
//            output = new ByteArrayOutputStream();
//            ImageEncoder encoder2 = new ImageEncoderImpl() {
//                @Override
//                public void encode(RenderedImage im) throws IOException {
//
//                }
//            }
//        }
        return null;
    }
}
