package com.hfut.glxy.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenliangliang
 * @date 2017/12/30
 */
public class OfficeUtil {

    private static final List<String> OFFICE_TYPE = Arrays.asList("doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt");

    public static boolean convertible(String fileType) {
        return OFFICE_TYPE.contains(fileType);
    }



    public static void cutPDFImage(String file,int page,String out)throws IOException{

        File in=new File(file);
        if (!in.exists()){
            throw new  FileNotFoundException(file+"文件不存在");
        }
        cutPDFImage(in,page,out);
    }


    public static void cutPDFImage(File pdf,int page,String out) throws IOException{
        PDDocument document = PDDocument.load(pdf);
        int pages = document.getNumberOfPages();
        if (page<0){
            page=0;
        }

        if (page>=pages){
            page=pages-1;
        }
        PDFRenderer renderer = new PDFRenderer(document);
        //renderer.
        BufferedImage image = renderer.renderImage(page);

        ImageIO.write(image,"png",new File(out));

        document.close();
    }



}
