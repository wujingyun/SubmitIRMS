/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.FbBanquetReservation;
import entity.FbContract;
import entity.FbOrderList;
import entity.FbQuotation;
import entity.Fbalacarte;
import entity.Fbpackage;
import exception.ExistException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wangxiahao
 */
@Stateless
public class CentralKitchenSessionBean implements CentralKitchenSessionBeanRemote {

    @PersistenceContext()
    EntityManager em;
    private Fbalacarte singleDish;
    private Fbpackage singlePackage;
    private FbOrderList orderList;
    private FbQuotation quotation;
    private FbBanquetReservation reservation;
    private FbContract fbContract;
    private Collection<Fbalacarte> dishList;
    private Collection<Fbpackage> packageList;
    private Collection<FbOrderList> orderListList;
    private Collection<FbQuotation> quotationList;
    private Collection<FbContract> contractList;
     
    //email
    String emailServerName = "smtp.gmail.com";

    @Override
    public void emailInitialPassward(String toEmailAdress, Long orderlistID) {
        orderList = new FbOrderList();
       
        orderList = em.find(FbOrderList.class, orderlistID);
        String order = "Dear Customer\n\n\n"
                + "We have prepared your order list\n"
                + "Packages:\n";
        for (Fbpackage a : orderList.getFbpack()) {
            order += a.getPackageName();
            order += "\n";
            for (Fbalacarte b : a.getSingleDish()) {
                order +=b.getType()+": ";
                order += b.getDishName();
                order += "\n";
            }
            order += "Total Sum for the package: $";

            order += Double.toString(a.getTotalPrice());
            order += "\n";
        }
        order += "\n\nDishes: \n";
        for (Fbalacarte c : orderList.getSingDish()) {
            order +=c.getType()+": ";
            order += c.getDishName();
            order += ":  Price: $";
             order += Double.toString(c.getPrice());
            order += "\n";
           
        }
        order +="\nThe total sum of the order list would be $"+orderList.getPrice()+"\n";
        order +="\nPlease contact our staff to edit the order list";
        order += "\n\nThank you\n\nBest Regards\nCoral Island Resort";
        System.err.println(order);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("resortcoral", "hehehehe");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("resortcoral@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAdress));
            message.setSubject("Order List for Your Event");
            message.setText(order);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void createQuotation(Long orderListID, Long ReservationID,int headCount) {
       quotation = new FbQuotation();
       double price = 0;

       orderList = new FbOrderList();
       reservation = new FbBanquetReservation();
       orderList = em.find(FbOrderList.class, orderListID);
       reservation =em.find(FbBanquetReservation.class, ReservationID);
       price += orderList.getPrice() * headCount;
       price += reservation.getBanquet().getCapacity() * 100;
       
        DecimalFormat df = new DecimalFormat("#.00");
         String l = df.format(price);
         price = Double.parseDouble(l);
         
       quotation.setHeadCount(headCount);
       quotation.setTotalPrice(price);
       quotation.setOrderList(orderList);
       quotation.setReservation(reservation);
       em.persist(quotation);
    }
    
    @Override
    public void editQuotation(Long orderListID,int headCount){
         quotation = new FbQuotation();
         double price;
         quotation = em.find(FbQuotation.class, orderListID);
         price =quotation.getTotalPrice();
         price -= quotation.getOrderList().getPrice() * quotation.getHeadCount();
         quotation.setHeadCount(headCount);
         price += quotation.getOrderList().getPrice() * quotation.getHeadCount();
         quotation.setTotalPrice(price);
         em.flush();
    }
    
    @Override
    public void deleteQuotation(Long orderListID){
         quotation = new FbQuotation();
         quotation = em.find(FbQuotation.class, orderListID);
         em.remove(quotation);
         em.flush();
    }
    
    @Override
    public void createDish(String name, double price, String type, String description) throws ExistException {
        singleDish = new Fbalacarte();
        if (em.find(Fbalacarte.class, name) != null) {
            throw new ExistException("Two dishes can't have the same name");
        }
        singleDish.createAlacarteDish(name, price, type, description);
        em.persist(singleDish);
    }

    @Override
    public void editDish(String name, double price, String type, String description) {
        singleDish = new Fbalacarte();
        System.err.println("session bean" + name);
        singleDish = em.find(Fbalacarte.class, name);
        singleDish.editAlacarteDish(price, type, description);
        em.flush();
    }

    @Override
    public void deleteDish(String name) throws ExistException {
        singleDish = new Fbalacarte();
        singleDish = em.find(Fbalacarte.class, name);
        if (singleDish.isInPackage() == true) {
            throw new ExistException("Dish is in a package!");
        }
        em.remove(singleDish);
        em.flush();
    }

    @Override
    public void createPackage(String packageName, List<String> selectedDishes) {
        double packagePrice = 0;
        singlePackage = new Fbpackage();
        singlePackage.createPackage(packageName);

        //  singlePackage =em.find(Fbpackage.class, packageName);
        for (String dishName : selectedDishes) {

            singleDish = em.find(Fbalacarte.class, dishName);

            System.err.println("singleDish: " + singleDish.getDishName());

            singleDish.setInPackage(true);
            packagePrice += singleDish.getPrice();
            singlePackage.getSingleDish().add(singleDish);
        }
        System.err.println("central kitchen session bean:" + packagePrice);
         DecimalFormat df = new DecimalFormat("#.00");
         String l = df.format(packagePrice);
         packagePrice = Double.parseDouble(l);
        singlePackage.setTotalPrice(packagePrice);
        em.persist(singlePackage);
    }
    // adding dish to package

    @Override
    public void editPackage(String packageName, List selectedDishes) throws ExistException {
        double packagePrice = 0;
        singlePackage = new Fbpackage();
        singlePackage = em.find(Fbpackage.class, packageName);
        packagePrice = singlePackage.getTotalPrice();

        for (Iterator it = selectedDishes.iterator(); it.hasNext();) {
            singleDish = new Fbalacarte();
            String result = (String) it.next();
            singleDish = em.find(Fbalacarte.class, result);
            if (singlePackage.getSingleDish().contains(singleDish)) {
                throw new ExistException("The dish is already in the package!");
            }
            packagePrice += singleDish.getPrice();
            singlePackage.getSingleDish().add(singleDish);
        }
         DecimalFormat df = new DecimalFormat("#.00");
         String l = df.format(packagePrice);
         packagePrice = Double.parseDouble(l);
        singlePackage.setTotalPrice(packagePrice);
        em.flush();
    }

    @Override
    public void deletePackage(String packageName) {
        singlePackage = new Fbpackage();
        singlePackage = em.find(Fbpackage.class, packageName);
        singlePackage.getSingleDish().clear();
        singlePackage.setSingleDish(null);
        em.remove(singlePackage);
        em.flush();
    }

    @Override // remove 
    public void editPackage(Fbpackage selectedPackage) {
        double packagePrice = 0;
        for (Iterator it = selectedPackage.getSingleDish().iterator(); it.hasNext();) {
            singleDish = new Fbalacarte();
            singleDish = (Fbalacarte) it.next();
            packagePrice += singleDish.getPrice();

        }
         DecimalFormat df = new DecimalFormat("#.00");
         String l = df.format(packagePrice);
         packagePrice = Double.parseDouble(l);
         
        selectedPackage.setTotalPrice(packagePrice);
        em.merge(selectedPackage);
    }

    @Override
    public void createOrderList(List<String> selectedPackageName, List<String> selectedDishName) {
        orderList = new FbOrderList();
        double totalPrice = 0;

        for (String dishName : selectedPackageName) {
            System.err.println("sessionbean:createOrderList: " + dishName);
        }
        for (String dishName : selectedDishName) {
            System.err.println("sessionbean:createOrderList: " + dishName);
        }

        for (Iterator it = selectedPackageName.iterator(); it.hasNext();) {
            singlePackage = new Fbpackage();
            String result = (String) it.next();
            System.err.println("sessionbean: package Name : " + result);
            singlePackage = em.find(Fbpackage.class, result);
            System.err.println("sessionbean: package found : " + singlePackage.getPackageName());
            for (Fbalacarte a : singlePackage.getSingleDish()) {
                System.err.println("session dishes in the package: " + a.getDishName());
            }
            totalPrice += singlePackage.getTotalPrice();
            orderList.getFbpack().add(singlePackage);
            System.err.println("sessionbean: package in the orderlist" + orderList.getFbpack().size());
        }

        for (Iterator it = selectedDishName.iterator(); it.hasNext();) {
            singleDish = new Fbalacarte();
            String dish = (String) it.next();
            System.err.println("sessionbean: Dish Name : " + dish);
            singleDish = em.find(Fbalacarte.class, dish);
            totalPrice += singleDish.getPrice();
            orderList.getSingDish().add(singleDish);
        }
         DecimalFormat df = new DecimalFormat("#.00");
         String l = df.format(totalPrice);
        totalPrice = Double.parseDouble(l);
        orderList.setPrice(totalPrice);
        em.persist(orderList);
    }

    @Override
    public void editOrderList(String orderId, List selectedPackageName, List selectedDishName) {
        orderList = new FbOrderList();
        Long l = Long.parseLong(orderId);
        orderList = em.find(FbOrderList.class, l);
        orderList.getFbpack().clear();
        orderList.getSingDish().clear();
        double totalPrice = 0; 
        
        for (Iterator it = selectedPackageName.iterator(); it.hasNext();) {
            singlePackage = new Fbpackage();
            String result = (String) it.next();
            singlePackage = em.find(Fbpackage.class, result);
              totalPrice += singlePackage.getTotalPrice();
            orderList.getFbpack().add(singlePackage);
        }

        for (Iterator it = selectedDishName.iterator(); it.hasNext();) {
            singleDish = new Fbalacarte();
            String result = (String) it.next();
            singleDish = em.find(Fbalacarte.class, result);
            totalPrice += singleDish.getPrice();
            orderList.getSingDish().add(singleDish);
        }
         
         DecimalFormat df = new DecimalFormat("#.00");
         String lp = df.format(totalPrice);
        totalPrice = Double.parseDouble(lp);
        orderList.setPrice(totalPrice);
        
        em.flush();
    }
    
   

    @Override
    public void deleteOrderList(List<String> listOfOrderList) {
        System.err.println("delete orderlist at session bean "+listOfOrderList.size());
      
        for (Iterator it= listOfOrderList.iterator();it.hasNext();) {
            orderList = new FbOrderList();
            String order = (String) it.next();
           Long l = Long.parseLong(order);
            System.err.println("Order Id at session bean" + l);
            orderList = em.find(FbOrderList.class, l);
            orderList.getFbpack().clear();
            orderList.getSingDish().clear();
            em.remove(orderList);
        }
        em.flush();
    }
    
    @Override
    public void createContract(Long quotationID, Date dateSigned){
         quotation = new FbQuotation();
         fbContract = new FbContract();
         
         quotation =em.find(FbQuotation.class, quotationID);
         fbContract.setBookingDate(dateSigned);
         fbContract.setContractStatus("In process");
         fbContract.setQuote(quotation);
         em.persist(fbContract);
    }
    
    @Override
    public void updateContractStatus(Long contractID,String status){
         fbContract = new FbContract();
         fbContract = em.find(FbContract.class,contractID);
         fbContract.setContractStatus(status);
         em.flush();
    }
    
    

    //-------------------getList section---------------------------
    @Override ///get appetizer String ejbql = "SELECT d FROM Fbalacarte d WHERE d.type =?1"; q.setParameter(1, "Appetizers"); 
    public Collection<Fbalacarte> getDishList() {
        dishList = new ArrayList<Fbalacarte>();
        String ejbql = "SELECT d FROM Fbalacarte d GROUP BY d.type";
        Query q = em.createQuery(ejbql);

        for (Object o : q.getResultList()) {
            Fbalacarte fa = (Fbalacarte) o;
            dishList.add(fa);
        }
        return dishList;
    }
    
   
    @Override
    public Collection<FbContract> getContractList() {
        contractList = new ArrayList<FbContract>();
        String ejbql = "SELECT c FROM FbContract c";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbContract fc = (FbContract) o;
            fc.getQuote().getOrderList().getFbpack().size();
             for(Fbpackage a:  fc.getQuote().getOrderList().getFbpack()){
                   Fbpackage p = (Fbpackage) a;
                p.getSingleDish().size();
             }
            fc.getQuote().getOrderList().getSingDish().size();
            contractList.add(fc);
        }
        em.flush();
        return contractList;
    }

    @Override
    public Collection<Fbalacarte> getDishListInPackage(String packageName) throws ExistException {
        dishList = new ArrayList<Fbalacarte>();
        singlePackage = new Fbpackage();
        singlePackage = em.find(Fbpackage.class, packageName);
        if (singlePackage == null) {
            throw new ExistException("The package is not found!");
        }
        for (Fbalacarte a : singlePackage.getSingleDish()) {
            dishList.add(a);
        }
        return dishList;
    }

    @Override
    public Collection<Fbpackage> getPackageList() {
        packageList = new ArrayList<Fbpackage>();
        String ejbql = "SELECT d FROM Fbpackage d";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            Fbpackage fa = (Fbpackage) o;
            fa.getSingleDish().size();
            packageList.add(fa);
        }
        return packageList;
    }
    
    @Override
    public Collection<FbQuotation> getQuotationList(){
        quotationList = new ArrayList<FbQuotation>();
         String ejbql = "SELECT d FROM FbQuotation d";
         Query q = em.createQuery(ejbql);
         for(Object o: q.getResultList()){
             FbQuotation qo = (FbQuotation)o;
             qo.getOrderList().getFbpack().size();
             for(Fbpackage a:  qo.getOrderList().getFbpack()){
                   Fbpackage p = (Fbpackage) a;
                p.getSingleDish().size();
             }
             qo.getOrderList().getSingDish().size();
             quotationList.add(qo);
         }
         return quotationList;
    }

    @Override
    public Collection<FbOrderList> getOrderLists() {
        orderListList = new ArrayList<FbOrderList>();
        System.err.println("getting orderlists");
        String ejbql = "SELECT d FROM FbOrderList d";
        Query q = em.createQuery(ejbql);
        for (Object o : q.getResultList()) {
            FbOrderList fo = (FbOrderList) o;
            fo.getFbpack().size();
            for (Fbpackage a : fo.getFbpack()) {
                Fbpackage p = (Fbpackage) a;
                p.getSingleDish().size();
            }
            fo.getSingDish().size();
            orderListList.add(fo);
        }
        return orderListList;
    }
    //Assisting functions----------------------------
}
