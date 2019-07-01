/*
 * ODODD.java
 *
 * Created on 23 November 2004, 22:56
 */

/**
 *
 * @author  kurt
 */
 
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ListIterator;
import java.util.Vector;

public class WriteXML{
    private OutputStream XMLFileOut;
    private OutputStream XMLBuffOut;
    private OutputStreamWriter out;
    
    /**
     * Creates a new instance of ODWriteXMLODD
     */
    public WriteXML(String XMLFilepath) {
        try{
            XMLFileOut= new FileOutputStream(XMLFilepath); // add FilePath
            XMLBuffOut= new BufferedOutputStream(XMLFileOut);
            out = new OutputStreamWriter(XMLBuffOut, "UTF-8");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found "+ e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("This VM does not support the UTF-8 character set. "+e.getMessage());
        }
    }
    
    public boolean writeXML(String imgFilename, Vector typeVector, int currentType, int thisCanvasID){
        try {
            out.write("<?xml version=\"1.0\" ");
            out.write("encoding=\"UTF-8\"?>\r\n");
            out.write("<PunctaCounter_Marker_File>\r\n");
            
            // write the image properties
            out.write(" <Image_Properties>\r\n");
            out.write("     <Image_Filename>"+ imgFilename + "</Image_Filename>\r\n");
            out.write(" </Image_Properties>\r\n");
            
            // write the marker data
            out.write(" <Marker_Data>\r\n");
            out.write("     <Current_Type>"+ currentType + "</Current_Type>\r\n");
            ListIterator it = typeVector.listIterator();
            while(it.hasNext()){
                PunctaCntrMarkerVector markerVector = (PunctaCntrMarkerVector)it.next();
                int type = markerVector.getType();
                out.write("     <Marker_Type>\r\n");
                out.write("         <Type>" +type+ "</Type>\r\n");
                ListIterator lit = markerVector.listIterator();
                while(lit.hasNext()){
                    PunctaCntrMarker marker = (PunctaCntrMarker)lit.next();
                    if (marker.canvasID != thisCanvasID && marker.canvasID != 0
                        && thisCanvasID != 0) continue;
                    int x = marker.getX();
                    int y = marker.getY();
                    int z = marker.getZ();
					     int rad = marker.getRad();
				        int uid = marker.getUID();
					     int owner = marker.getOwner();
                    out.write("         <Marker>\r\n");
                    out.write("             <MarkerX>" +x+ "</MarkerX>\r\n");
                    out.write("             <MarkerY>" +y+ "</MarkerY>\r\n");
                    out.write("             <MarkerZ>" +z+ "</MarkerZ>\r\n");
				        out.write("             <MarkerRad>" +rad+ "</MarkerRad>\r\n");
				        out.write("             <MarkerUID>" +uid+ "</MarkerUID>\r\n");
					     out.write("             <MarkerOwner>"+owner+ "</MarkerOwner>\r\n");
					     out.write("             <GroupNumber>"+marker.resultNum+ "</GroupNumber>\r\n");
                    out.write("         </Marker>\r\n");
                }
                out.write("     </Marker_Type>\r\n");
            }
            
            out.write(" </Marker_Data>\r\n");
            out.write("</PunctaCounter_Marker_File>\r\n");
            
            out.flush();  // Don't forget to flush!
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
}
