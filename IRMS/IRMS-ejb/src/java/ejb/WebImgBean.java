/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import entity.webImg;
import exception.ExistException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author WU JINGYUN
 */
@Stateless
@Local
public class WebImgBean {

    @PersistenceContext
    private EntityManager em;
    private webImg webImg;
    webImg wi;

    public void addImg(String imgUrl, String imgName, String description, Boolean status, String link) {
        wi = new webImg();//initiate new user


        System.out.println(" addImg1=========");
        status = false;
        wi.create(imgUrl, imgName, description, status, link);

        System.out.println(" addImg1 create=========");

        em.flush();//
        em.persist(wi);//persist
        System.out.println(" addImg1 created=========");

    }

    public List<webImg> getImgToDisplay() {
        Query query = em.createQuery("SELECT im FROM WebImg im WHERE im.status =true");

        List<webImg> recInfo = null;

        try {
            recInfo = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }

        return recInfo;

    }

    public webImg getImgById(long id) {
        Query query = em.createQuery("SELECT im FROM WebImg im WHERE im.status =true and im.id=?1");
        query.setParameter(1, id);

        for (Object o : query.getResultList()) {
            webImg = (webImg) o;
        }


        return webImg;

    }
public List<webImg> getAllImage() throws ExistException{
        Query q = em.createQuery("SELECT ua FROM  WebImg ua");
        List imgList = new ArrayList<webImg>();
         for (Object o: q.getResultList()) { 
            webImg m = (webImg) o; 
            imgList.add(m); 
        } 
        if(imgList.isEmpty())  throw new ExistException("WebImg database is empty!");
        return imgList;     
    }
    public void setFirstImg(long imgId) {
        webImg = em.find(webImg.class, imgId);


        System.out.println(" firstimage=========");

        webImg.setStatus(true);
        webImg.setImgName("1");
        System.out.println(" firstimage2=========");

        em.flush();//
        em.persist(webImg);//persist
        System.out.println(" firstimage3 created=========");

    }

    public void setSecondImg(long imgId) {
        webImg = em.find(webImg.class, imgId);


        System.out.println(" setSecondImg=========");

        webImg.setStatus(true);
        webImg.setImgName("2");
        System.out.println(" setSecondImg2=========");

        em.flush();//
        em.persist(webImg);//persist
        System.out.println(" setSecondImg3 created=========");

    }

    public void setThirdImg(long imgId) {
        webImg = em.find(webImg.class, imgId);


        System.out.println(" setThirdImg=========");

        webImg.setStatus(true);
        webImg.setImgName("3");
        System.out.println(" setThirdImg2=========");

        em.flush();//
        em.persist(webImg);//persist
        System.out.println(" setThirdImg3 created=========");

    }

    public void setForthImg(long imgId) {
        webImg = em.find(webImg.class, imgId);


        System.out.println(" setForthImg=========");

        webImg.setStatus(true);
        webImg.setImgName("4");
        System.out.println(" setForthImg2=========");

        em.flush();//
        em.persist(webImg);//persist
        System.out.println(" setForthImg3 created=========");

    }

    public void setRestStatus(long firstId, long secondId, long thirdId, long forthId)
            throws ExistException {

        Query query = em.createQuery("SELECT im FROM WebImg im WHERE im.id!=?1 and im.id!=?2 and im.id!=?3 and im.id!=?4");
        query.setParameter(1, firstId);
        query.setParameter(2, secondId);
        query.setParameter(3, thirdId);
        query.setParameter(4, forthId);

        List imgList = new ArrayList<webImg>();
        for (Object o : query.getResultList()) {
            webImg m = (webImg) o;
            imgList.add(m);
        }
        if (imgList.isEmpty()) {
            throw new ExistException("WebImg database is empty!");
        }
        for (int i = 0; i < imgList.size(); i++) {
            webImg = (webImg) imgList.get(i);
            webImg.setStatus(false);
            em.flush();//
            em.persist(webImg);
        }
    }
}
