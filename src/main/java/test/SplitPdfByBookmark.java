package test;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.interactive.action.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author huchen
 * @description
 * @since 2021/12/3 10:15
 */
public class SplitPdfByBookmark {
    public static void main(String[] args) throws IOException {
        File file = new File("E:\\2.pdf");
        //大文件加载问题
        // document = PDDocument.load(file, MemoryUsageSetting.setupTempFileOnly());
        PDDocument pdf = PDDocument.load(file);
        PDDocument doc = new PDDocument();
        for (int i = 1; i < 3; i++) {
            doc.addPage(pdf.getPage(i));
        }
        doc.save("E:\\sample\\34.pdf");


//        PDDocumentCatalog documentCatalog = pdf.getDocumentCatalog();
//        PDDocumentOutline documentOutline = documentCatalog.getDocumentOutline();
//        //printBookmark(documentOutline,"");
//        PDPageTree pages = pdf.getPages();
//        Splitter splitter = new Splitter();
//        //每页都都拆分
//        List<PDDocument> Pages = splitter.split(pdf);
//        splitter.setStartPage(1);
//        splitter.setEndPage(2);
//        ListIterator<PDDocument> pdDocumentListIterator = Pages.listIterator();
//        int i = 1;
//        while(pdDocumentListIterator.hasNext()){
//            PDDocument next = pdDocumentListIterator.next();
//            next.save("E:\\sample\\"+ i++ +".pdf");
//        }
//        System.out.println(pdf.getNumberOfPages());
//        pdf.close();
    }

    public static List<Integer> getBookMarkPages(PDDocument pdDocument) throws IOException{

        //获取文档目录对象
        PDDocumentCatalog catalog = pdDocument.getDocumentCatalog();

        //获取文档大纲对象
        PDDocumentOutline outline = catalog.getDocumentOutline();

        //获取第一个纲要条目
        PDOutlineItem firstChild = outline.getFirstChild();
        if(outline!=null){
            while(firstChild!=null){
                //打印标题一的文本
                System.out.println("Item:"+firstChild.getTitle());

                //获取标题一的二级标题
                PDOutlineItem firstChild1 = firstChild.getFirstChild();
            }
        }


        return null;
    }

    public static void printBookmark(PDOutlineNode bookmark, String indentation) throws IOException
    {

        PDOutlineItem current = bookmark.getFirstChild();
        while( current != null )
        {
            int pages =0;
            if (current.getDestination() instanceof PDPageDestination)
            {
                PDPageDestination pd = (PDPageDestination) current.getDestination();
                pages = (pd.retrievePageNumber() +1);
            }
            if (current.getAction() instanceof PDActionGoTo)
            {
                PDActionGoTo gta = (PDActionGoTo) current.getAction();
                if (gta.getDestination() instanceof PDPageDestination)
                {
                    PDPageDestination pd = (PDPageDestination) gta.getDestination();
                    pages = (pd.retrievePageNumber() +1);
                }
            }
            if (pages ==0)
                System.out.println( indentation + current.getTitle());
            else
                System.out.println( indentation + current.getTitle() +"  "+ pages);
            printBookmark( current, indentation + "    " );  // 递归调用
            current = current.getNextSibling();
        }

    }

}
