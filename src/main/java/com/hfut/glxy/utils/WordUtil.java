//package com.hfut.glxy.utils;
//
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.converter.WordToHtmlConverter;
//import org.apache.poi.xwpf.converter.core.BasicURIResolver;
//import org.apache.poi.xwpf.converter.core.FileImageExtractor;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.w3c.dom.Document;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import java.io.*;
//
///**
// * Word格式转换的工具类
// * @author chenliangliang
// * @date: 2017/12/4
// */
//public class WordUtil {
//
//
//
//
//    public static boolean wordToHtml(String source, String htmlPath, String htmlName){
//        String[] strs= StringUtils.split(source,".");
//        String suffix=strs[strs.length-1];
//        if (StringUtils.equals(suffix,"doc")){
//            return docToHtml(source,htmlPath,htmlName);
//        }else if (StringUtils.equals(suffix,"docx")){
//            return docxToHtml(source,htmlPath,htmlName);
//        }else {
//            return false;
//        }
//    }
//
//
//
//    public  static boolean docxToHtml(String source, String htmlPath, String htmlName){
//        InputStream in=null ;
//
//        try {
//            in=new FileInputStream(source);
//            docxToHtml(in,htmlPath,htmlName);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }finally {
//            if (in!=null){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        }
//    }
//
//
//    /**
//     * docx转换为html
//     * @param inputStream
//     * @param htmlPath
//     * @param htmlName
//     * @throws Exception
//     */
//    public static void docxToHtml(InputStream inputStream, String htmlPath, String htmlName) throws Exception{
//        String target = htmlPath+htmlName;
//        String imagePath = htmlPath+"image\\";
//        OutputStreamWriter outputStreamWriter = null;
//        OutputStream outputStream = null;
//        try {
//            XWPFDocument document = new XWPFDocument(inputStream);
//            XHTMLOptions options = XHTMLOptions.create();
//            // 存放图片的文件夹
//            options.setExtractor(new FileImageExtractor(new File(imagePath)));
//            // html中图片的路径
//            options.URIResolver(new BasicURIResolver("image"));
//            outputStream = new FileOutputStream(target);
//            outputStreamWriter = new OutputStreamWriter(outputStream, "utf-8");
//            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
//            xhtmlConverter.convert(document, outputStreamWriter, options);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                outputStream.close();
//            }
//            if (outputStreamWriter != null) {
//                outputStreamWriter.close();
//            }
//
//        }
//    }
//
//
//    public  static boolean docToHtml(String source, String htmlPath, String htmlName){
//        InputStream in=null ;
//
//        try {
//            in=new FileInputStream(source);
//            docToHtml(in,htmlPath,htmlName);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }finally {
//            if (in!=null){
//                try {
//                    in.close();
//                } catch (IOException e) {
//                   System.out.println(e.getMessage());
//                }
//            }
//        }
//    }
//
//    /**
//     * doc转换为html
//     * @param in
//     * @param htmlPath
//     * @param htmlName
//     * @throws Exception
//     */
//    public static void docToHtml(InputStream in, String htmlPath, String htmlName) throws Exception {
//        String target = htmlPath+htmlName;
//        String imagePath = htmlPath+"image\\";
//        HWPFDocument wordDocument = new HWPFDocument(in);
//        Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
//        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);
//        // 保存图片，并返回图片的相对路径
//        wordToHtmlConverter.setPicturesManager((content, pictureType, name, width, height) -> {
//            try (FileOutputStream out = new FileOutputStream(imagePath + name)) {
//                out.write(content);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "image/" + name;
//        });
//        wordToHtmlConverter.processDocument(wordDocument);
//        Document htmlDocument = wordToHtmlConverter.getDocument();
//        DOMSource domSource = new DOMSource(htmlDocument);
//        StreamResult streamResult = new StreamResult(new File(target));
//
//        TransformerFactory tf = TransformerFactory.newInstance();
//        Transformer serializer = tf.newTransformer();
//        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//        serializer.setOutputProperty(OutputKeys.METHOD, "html");
//        serializer.transform(domSource, streamResult);
//    }
//
//
//    public static boolean check(String word){
//        String[] strs= StringUtils.split(word,".");
//        String suffix=strs[strs.length-1];
//        return StringUtils.equals(suffix,"doc")||StringUtils.equals(suffix,"docx");
//    }
//}
