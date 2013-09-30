/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;
import ejb.WebImgBean;
import entity.webImg;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.faces.bean.ManagedBean;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class fileUploadManagedBean implements Serializable {

    /**
     * Creates a new instance of fileUploadManagedBean
     */
    public fileUploadManagedBean() {
    }

    public void handleFileUpload(FileUploadEvent event) {
        boolean uploaded=false;
        try {System.out.println("try file updateing===============");
            //File targetFolder = new File("../../../web/resources/img");
            File targetFolder = new File("C:\\Users\\WU JINGYUN\\Documents\\NetBeansProjects\\T01IRMS\\IRMS\\IRMS-war\\web\\resources\\img\\web"); 
            System.out.println("file updateing===============");
            InputStream inputStream = event.getFile().getInputstream();
            OutputStream out = new FileOutputStream(new File(targetFolder,
                    event.getFile().getFileName()));
             System.out.println("file update===============");
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
                System.out.println("file updated===============");
                
            }
            inputStream.close();
            out.flush();
            out.close();
            uploaded=true;
            System.out.println("file updated 2===============");
           
        } catch (IOException e) {
            e.printStackTrace();
        }
if(uploaded){
 FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
}else {FacesMessage msg = new FacesMessage("Uploaded Failed", event.getFile().getFileName() + " cannot be uploaded");
        FacesContext.getCurrentInstance().addMessage(null, msg);}
        }
}
