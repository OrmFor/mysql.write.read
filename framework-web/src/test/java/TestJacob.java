//import com.jacob.activeX.ActiveXComponent;
//import com.jacob.com.ComThread;
//import com.jacob.com.Dispatch;
//import com.jacob.com.Variant;
//
//import java.io.File;
//
//public class TestJacob {
//
//  /*  private static final int wdFormatPDF = 17;// PDF 格式
//    public static void wordToPDF(){
//
//        ActiveXComponent app = null;
//        Dispatch doc = null;
//        try {
//            app = new ActiveXComponent("Word.Application");
//            app.setProperty("Visible", new Variant(false));
//            Dispatch docs = app.getProperty("Documents").toDispatch();
//
//            //转换前的文件路径
//            String startFile = "C:\\Users\\Thinkpad\\Desktop\\新建文件夹\\简历.doc";
//            //转换后的文件路劲
//            String overFile =  "F:\\data\\简历.pdf";
//
//            doc = Dispatch.call(docs,  "Open" , startFile).toDispatch();
//            File tofile = new File(overFile);
//            if (tofile.exists()) {
//                tofile.delete();
//            }
//            Dispatch.call(doc,"SaveAs", overFile, wdFormatPDF);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        } finally {
//            Dispatch.call(doc,"Close",false);
//            if (app != null)
//                app.invoke("Quit", new Variant[] {});
//        }
//        //结束后关闭进程
//        ComThread.Release();
//    }*/
//
//    private static final int wdFormatPDF = 17; // PDF 格式
//
//    public void wordToPDF(String sfileName, String toFileName) {
//
//        System.out.println("启动 Word...");
//        long start = System.currentTimeMillis();
//        ActiveXComponent app = null;
//        Dispatch doc = null;
//        try {
//            app = new ActiveXComponent("Word.Application");
//            app.setProperty("Visible", new Variant(false));
//            Dispatch docs = app.getProperty("Documents").toDispatch();
//            doc = Dispatch.call(docs, "Open", sfileName).toDispatch();
//            System.out.println("打开文档..." + sfileName);
//            System.out.println("转换文档到 PDF..." + toFileName);
//            File tofile = new File(toFileName);
//            if (tofile.exists()) {
//                tofile.delete();
//            }
//            Dispatch.call(doc, "SaveAs", toFileName, // FileName
//                    wdFormatPDF);
//            long end = System.currentTimeMillis();
//            System.out.println("转换完成..用时：" + (end - start) + "ms.");
//
//        } catch (Exception e) {
//            System.out.println("========Error:文档转换失败：" + e.getMessage());
//        } finally {
//            Dispatch.call(doc, "Close", false);
//            System.out.println("关闭文档");
//            if (app != null)
//                app.invoke("Quit", new Variant[]{});
//        }
//        // 如果没有这句话,winword.exe进程将不会关闭
//        ComThread.Release();
//    }
//
//    public static void main(String[] args) {
//        TestJacob d = new TestJacob();
//        d.wordToPDF("C:\\Users\\Thinkpad\\Desktop\\新建文件夹\\简历.doc", "F:\\data\\简历.pdf");
//    }
//
//
//
// /*  public static void main(String[] args) {
//        wordToPDF();
//    }*/
//}
