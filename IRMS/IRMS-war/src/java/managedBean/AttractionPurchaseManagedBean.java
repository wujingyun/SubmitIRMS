/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AttractionSessionBeanRemote;
import ejb.CustomerBeanRemote;
import ejb.EmailSessionBean;
import entity.Attraction;
import entity.AttractionPass;
import entity.AttractionTicket;
import entity.Customer;
import exception.ExistException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;


import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import urn.ebay.api.PayPalAPI.PayPalAPIInterfaceServiceService;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutReq;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutRequestType;
import urn.ebay.api.PayPalAPI.SetExpressCheckoutResponseType;
import urn.ebay.apis.CoreComponentTypes.BasicAmountType;
import urn.ebay.apis.eBLBaseComponents.AddressType;
import urn.ebay.apis.eBLBaseComponents.CountryCodeType;
import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.ErrorType;
import urn.ebay.apis.eBLBaseComponents.PaymentActionCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;
/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@ViewScoped
public class AttractionPurchaseManagedBean implements Serializable {

   
    
   // @EJB
  //  AttractionSessionBeanRemote asb;
    @EJB
    AttractionSessionBeanRemote asbr;
    @EJB
    CustomerBeanRemote cbb;
     @EJB
    EmailSessionBean emailSessionBean;
    private Integer quantity;
   
   // private Long customerId = new Long(1);
   
    private double orderTotal ;
    private String url = new String();
     private List<Attraction> attList;
      private String attractionName;
        private List<AttractionPass> ap;
        private Map<String, String> apList = new HashMap<String, String>();
        private Map<String, String> atList = new HashMap<String, String>();
    private List<AttractionTicket> at;
        private AttractionPass selectedTicket;
    private AttractionPass selectedPass;
     private String username;
    private String password;
    private Customer customer;
    private Date doa;
   // private Calendar doaCalender;
   private String selectPassId1;
   private String selectPassId2;
   private String selectPassId3;
   private String quantity1;
   private String quantity2;
   private String quantity3;
    private String selectTicketId1;
   private String selectTicketId2;
   private String selectTicketId3;
   private String quantity4;
   private String quantity5;
   private String quantity6;
  private int q1=0, q2=0, q3=0;
  private int q4=0, q5=0, q6=0;
    /**
     * Creates a new instance of CreateInternalMsgManagedBean
     */
    public AttractionPurchaseManagedBean() {
        
    }

    @PostConstruct
    private void init(){
        this.attList = asbr.getAttractions();
      
    }
    

    public void handleAttractionChange() throws ExistException {
      
        if (attractionName != null && !attractionName.equals("")) {
            ap = asbr.getPass(attractionName); 
            at = asbr.getTicket(attractionName);
            System.out.println("handlechage ap"+ap.size());
              System.out.println("handlechage at"+at.size());
            for (int i=0;i<ap.size();i++){
                apList.put(ap.get(i).getPassName(), ap.get(i).getPassName());
            System.out.println("apList"+apList.get(ap.get(i).getPassName()));
            }
            for (int i=0;i<at.size();i++){
               atList.put(at.get(i).getTicketName(), at.get(i).getTicketName());
            System.out.println("apList"+atList.get(at.get(i).getTicketName()));
            }
        } else {
            System.out.println("empty attraction name");
        }
    }
    public void buyInfo() throws ExistException{
   
    }
    
    public void cancelReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/attractionPassPurchase.xhtml");
    }
    
     public void confirmReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/attractionPassPurchase.xhtml");
    }
     public void cancelTicketReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/attractionTicketPurchase.xhtml");
    }
    
     public void confirmTicketReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/attractionTicketPurchase.xhtml");
    }
     
   
  
    public void handlepassChange() {
            System.out.println("handlepassChange"+quantity1);
              System.out.println("handlepassChange"+quantity2);
              System.out.println("handlepassChange"+quantity3);
        if (quantity1 != null && !quantity1.equals("")) {
            q1=Integer.parseInt(quantity1);
             System.out.println("handlepassChange"+quantity1);
        }}
    public void handlepassChange2() {
            System.out.println("handlepassChange"+quantity4);
              System.out.println("handlepassChange"+quantity5);
              System.out.println("handlepassChange"+quantity6);
        if (quantity4!= null && !quantity4.equals("")) {
            q4=Integer.parseInt(quantity4);
             System.out.println("handlepassChange"+quantity4);
        }}
   
     public void BookPass() throws ExistException{
        RequestContext context = RequestContext.getCurrentInstance();
       try{ 
           HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         //System.out.println("BookPass"+username);
         long customerId = (Long)request.getSession().getAttribute("userId");
       
         //  System.out.println("BookPass"+customer.getUserName());
             System.out.println("BookPass"+quantity1);
              System.out.println("BookPass"+selectPassId1);
               System.out.println("BookPass"+selectPassId2);
             q1=Integer.parseInt(quantity1);
               q2=Integer.parseInt(quantity2);
                 q3=Integer.parseInt(quantity3);
       if (q1!=0){ System.out.println("BookPass"+selectPassId1);asbr.buyPass(customerId, q1,selectPassId1);}
       if (q2!=0){System.out.println("BookPass"+selectPassId2);asbr.buyPass(customerId, q2,selectPassId2);}
       if (q3!=0){System.out.println("BookPass"+selectPassId3);asbr.buyPass(customerId, q3,selectPassId3);}
     //  int totalqy=q1+q2+q3;
      //emailSessionBean.emailAttractionConfirm(cbb.getCustomerById(customerId).getEmail(), "ticket type: Pass"+"Quantity :", selectPassId1);
       this.setExpressCheckout();
       }
        catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attraction Pass purchase Fail",""));
               
                return;
                
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction Pass Successfully purchased",""));
              
       }
     
     
       public void BookTicket() throws ExistException{
        RequestContext context = RequestContext.getCurrentInstance();
       try{ 
           HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
         System.out.println("BookTicket"+username);
         long customerId = (Long)request.getSession().getAttribute("userId");
       
           System.out.println("BookTicket"+customer.getUserName());
             System.out.println("BookTicket"+quantity4);
              System.out.println("BookTicket"+selectTicketId1);
               System.out.println("BookTicket"+selectTicketId2);
             q4=Integer.parseInt(quantity4);
               q5=Integer.parseInt(quantity5);
                 q6=Integer.parseInt(quantity6);
                  System.out.println("BookTicket"+selectTicketId2);
                 //Calendar doaCalender= Calendar.getInstance();
 // doaCalender.setTime(doa);
 //System.out.println("BookTicket----->set Calendar"+doaCalender);
       if (q4!=0){ System.out.println("BookTicket"+selectTicketId1);asbr.purchaseTicket(customerId, q4,selectTicketId1, doa);}
       if (q5!=0){System.out.println("BookTicket"+selectTicketId2);asbr.purchaseTicket(customerId, q5,selectTicketId2,doa);}
       if (q6!=0){System.out.println("BookTicket"+selectTicketId3);asbr.purchaseTicket(customerId, q6,selectTicketId3,doa);}
       this.setExpressCheckoutForTicket(); }
        catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Attraction Ticket purchase Fail",""));
               
                return;
                
            }

        
          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Attraction Ticket Successfully purchased",""));
              
       }
       
       
       

               public SetExpressCheckoutResponseType setExpressCheckoutForTicket() throws IOException {
       Logger logger = Logger.getLogger(this.getClass().toString());

		// ## SetExpressCheckoutReq
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();

		// URL to which the buyer's browser is returned after choosing to pay
		// with PayPal. For digital goods, you must add JavaScript to this page
		// to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the final review page on which
		// the buyer confirms the order and payment or billing agreement.`
		setExpressCheckoutRequestDetails
				.setReturnURL("http://localhost:8080/IRMS-war/attractionTicketPaymentSuccess.xhtml");

		// URL to which the buyer is returned if the buyer does not approve the
		// use of PayPal to pay you. For digital goods, you must add JavaScript
		// to this page to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the original page on which the
		// buyer chose to pay with PayPal or establish a billing agreement.`
		setExpressCheckoutRequestDetails
				.setCancelURL("http://localhost:8080/IRMS-war/attractionTicketPaymentCancel.xhtml");

		// ### Payment Information
		// list of information about the payment
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();

		// information about the first payment
		PaymentDetailsType paymentDetails1 = new PaymentDetailsType();

		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		//
		// If the transaction includes one or more one-time purchases, this
		// field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored.
		// 
		// * `Currency Code` - You must set the currencyID attribute to one of
		// the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		BasicAmountType orderTotal1 = new BasicAmountType(CurrencyCodeType.SGD,
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalAm").toString());
                System.out.println(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalAm").toString());
		paymentDetails1.setOrderTotal(orderTotal1);

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
		// transaction does not include a one-time purchase, this field is
		// ignored. It is one of the following values:
		// 
		// * `Sale` - This is a final sale for which you are requesting payment
		// (default).
		// * `Authorization` - This payment is a basic authorization subject to
		// settlement with PayPal Authorization and Capture.
		// * `Order` - This payment is an order authorization subject to
		// settlement with PayPal Authorization and Capture.
		// `Note:
		// You cannot set this field to Sale in SetExpressCheckout request and
		// then change the value to Authorization or Order in the
		// DoExpressCheckoutPayment request. If you set the field to
		// Authorization or Order in SetExpressCheckout, you may set the field
		// to Sale.`
		paymentDetails1.setPaymentAction(PaymentActionCodeType.ORDER);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		SellerDetailsType sellerDetails1 = new SellerDetailsType();
		sellerDetails1.setPayPalAccountID("yangzhennanedward-facilitator@gmail.com");
		paymentDetails1.setSellerDetails(sellerDetails1);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		paymentDetails1.setPaymentRequestID("PaymentRequest1");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
	AddressType shipToAddress1 = new AddressType();
		shipToAddress1.setStreet1("Ape Way");
		shipToAddress1.setCityName("Austin");
		shipToAddress1.setStateOrProvince("TX");
		shipToAddress1.setCountry(CountryCodeType.SG);
		shipToAddress1.setPostalCode("118424");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		paymentDetails1.setNotifyURL("http://localhost/ipn");

		paymentDetails1.setShipToAddress(shipToAddress1);
            
		// information about the second payment
		//PaymentDetailsType paymentDetails2 = new PaymentDetailsType();
		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		//
		// If the transaction includes one or more one-time purchases, this
		// field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored.
		// 
		// * `Currency Code` - You must set the currencyID attribute to one of
		// the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
		// transaction does not include a one-time purchase, this field is
		// ignored. It is one of the following values:
		// 
		// * `Sale` - This is a final sale for which you are requesting payment
		// (default).
		// * `Authorization` - This payment is a basic authorization subject to
		// settlement with PayPal Authorization and Capture.
		// * `Order` - This payment is an order authorization subject to
		// settlement with PayPal Authorization and Capture.
		// `Note:
		// You cannot set this field to Sale in SetExpressCheckout request and
		// then change the value to Authorization or Order in the
		// DoExpressCheckoutPayment request. If you set the field to
		// Authorization or Order in SetExpressCheckout, you may set the field
		// to Sale.`
		//paymentDetails2.setPaymentAction(PaymentActionCodeType.SALE);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		//SellerDetailsType sellerDetails2 = new SellerDetailsType();
		//sellerDetails2.setPayPalAccountID("yangzhennanedward@gmail.com");
		//paymentDetails2.setSellerDetails(sellerDetails2);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		//paymentDetails2.setPaymentRequestID("PaymentRequest2");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
		//AddressType shipToAddress2 = new AddressType();
		//shipToAddress2.setStreet1("Ape Way");
		///shipToAddress2.setCityName("Austin");
		//shipToAddress2.setStateOrProvince("TX");
		//shipToAddress2.setCountry(CountryCodeType.SG);
		//shipToAddress2.setPostalCode("118424");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		//paymentDetails2.setNotifyURL("http://localhost/ipn");

		   List<PaymentDetailsItemType> paymentDetailsItemTypes = new ArrayList<PaymentDetailsItemType>();
        PaymentDetailsItemType paymentDetailsItemType = new PaymentDetailsItemType();
        paymentDetailsItemType.setDescription("Attraction Ticket");
        paymentDetailsItemType.setAmount(orderTotal1);
          paymentDetailsItemType.setQuantity(1);
           paymentDetailsItemTypes.add(paymentDetailsItemType);
paymentDetails1.setPaymentDetailsItem(paymentDetailsItemTypes);

		paymentDetailsList.add(paymentDetails1);
		

		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(
				setExpressCheckoutRequestDetails);

		setExpressCheckoutReq
				.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
                        System.err.println(System.getProperty("user.dir"));

			service = new PayPalAPIInterfaceServiceService(
					"C:\\Users\\WU JINGYUN\\Documents\\NetBeansProjects\\T01IRMS\\IRMS\\IRMS-war\\web\\WEB-INF\\sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			setExpressCheckoutResponse = service
					.setExpressCheckout(setExpressCheckoutReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (setExpressCheckoutResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {
System.out.println("-----------------------------success");
			// ### Redirecting to PayPal for authorization
			// Once you get the "Success" response, needs to authorise the
			// transaction by making buyer to login into PayPal. For that,
			// need to construct redirect url using EC token from response.
			// For example,
			// `redirectURL="https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+setExpressCheckoutResponse.getToken();`

			// Express Checkout Token
			logger.info("EC Token:" + setExpressCheckoutResponse.getToken());
                        
                        
                        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", setExpressCheckoutResponse.getAck());
						/*
						 * A time stamped token by which you identify to PayPal that you are processing 
						 * this payment with Express Checkout. The token expires after three hours. 
						 * If you set the token in the SetExpressCheckout request, the value of the 
						 * token in the response is identical to the value in the request.
							Character length and limitations: 20 single-byte characters
						 */
						map.put("Token", setExpressCheckoutResponse.getToken());
						map.put("Redirect URL",
								"<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
										+ setExpressCheckoutResponse.getToken()
										+ ">Redirect To PayPal</a>");
						/*  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

         request.getSession().setAttribute("map", map);
	externalContext.redirect("/IRMS-war/irmsShowPurchasePayConfirm.xhtml");*/
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {System.out.println("-----------------------------fail");
			List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
                        
		}url = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("url", url);
        //String testUrl=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
        //System.out.println("testUrl== "+testUrl);
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        return setExpressCheckoutResponse;
		
	
    }
       
       public SetExpressCheckoutResponseType setExpressCheckout() throws IOException {
       Logger logger = Logger.getLogger(this.getClass().toString());

		// ## SetExpressCheckoutReq
		SetExpressCheckoutRequestDetailsType setExpressCheckoutRequestDetails = new SetExpressCheckoutRequestDetailsType();

		// URL to which the buyer's browser is returned after choosing to pay
		// with PayPal. For digital goods, you must add JavaScript to this page
		// to close the in-context experience.
		// `Note:
		// PayPal recommends that thefo value be the final review page on which
		// the buyer confirms the order and payment or billing agreement.`
		setExpressCheckoutRequestDetails
				.setReturnURL("http://localhost:8080/IRMS-war/attractionPaymentSuccess.xhtml");

		// URL to which the buyer is returned if the buyer does not approve the
		// use of PayPal to pay you. For digital goods, you must add JavaScript
		// to this page to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the original page on which the
		// buyer chose to pay with PayPal or establish a billing agreement.`
		setExpressCheckoutRequestDetails
				.setCancelURL("http://localhost:8080/IRMS-war/attractionPaymentCancel.xhtml");

		// ### Payment Information
		// list of information about the payment
		List<PaymentDetailsType> paymentDetailsList = new ArrayList<PaymentDetailsType>();

		// information about the first payment
		PaymentDetailsType paymentDetails1 = new PaymentDetailsType();

		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		//
		// If the transaction includes one or more one-time purchases, this
		// field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored.
		// 
		// * `Currency Code` - You must set the currencyID attribute to one of
		// the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		BasicAmountType orderTotal1 = new BasicAmountType(CurrencyCodeType.SGD,
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalAmount").toString());
                System.out.println(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalAmount").toString());
		paymentDetails1.setOrderTotal(orderTotal1);

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
		// transaction does not include a one-time purchase, this field is
		// ignored. It is one of the following values:
		// 
		// * `Sale` - This is a final sale for which you are requesting payment
		// (default).
		// * `Authorization` - This payment is a basic authorization subject to
		// settlement with PayPal Authorization and Capture.
		// * `Order` - This payment is an order authorization subject to
		// settlement with PayPal Authorization and Capture.
		// `Note:
		// You cannot set this field to Sale in SetExpressCheckout request and
		// then change the value to Authorization or Order in the
		// DoExpressCheckoutPayment request. If you set the field to
		// Authorization or Order in SetExpressCheckout, you may set the field
		// to Sale.`
		paymentDetails1.setPaymentAction(PaymentActionCodeType.ORDER);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		SellerDetailsType sellerDetails1 = new SellerDetailsType();
		sellerDetails1.setPayPalAccountID("yangzhennanedward-facilitator@gmail.com");
		paymentDetails1.setSellerDetails(sellerDetails1);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		paymentDetails1.setPaymentRequestID("PaymentRequest1");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
	AddressType shipToAddress1 = new AddressType();
		shipToAddress1.setStreet1("Ape Way");
		shipToAddress1.setCityName("Austin");
		shipToAddress1.setStateOrProvince("TX");
		shipToAddress1.setCountry(CountryCodeType.SG);
		shipToAddress1.setPostalCode("118424");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		paymentDetails1.setNotifyURL("http://localhost/ipn");

		paymentDetails1.setShipToAddress(shipToAddress1);
            
		// information about the second payment
		//PaymentDetailsType paymentDetails2 = new PaymentDetailsType();
		// Total cost of the transaction to the buyer. If shipping cost and tax
		// charges are known, include them in this value. If not, this value
		// should be the current sub-total of the order.
		//
		// If the transaction includes one or more one-time purchases, this
		// field must be equal to
		// the sum of the purchases. Set this field to 0 if the transaction does
		// not include a one-time purchase such as when you set up a billing
		// agreement for a recurring payment that is not immediately charged.
		// When the field is set to 0, purchase-specific fields are ignored.
		// 
		// * `Currency Code` - You must set the currencyID attribute to one of
		// the
		// 3-character currency codes for any of the supported PayPal
		// currencies.
		// * `Amount`
		

		// How you want to obtain payment. When implementing parallel payments,
		// this field is required and must be set to `Order`. When implementing
		// digital goods, this field is required and must be set to `Sale`. If
		// the
		// transaction does not include a one-time purchase, this field is
		// ignored. It is one of the following values:
		// 
		// * `Sale` - This is a final sale for which you are requesting payment
		// (default).
		// * `Authorization` - This payment is a basic authorization subject to
		// settlement with PayPal Authorization and Capture.
		// * `Order` - This payment is an order authorization subject to
		// settlement with PayPal Authorization and Capture.
		// `Note:
		// You cannot set this field to Sale in SetExpressCheckout request and
		// then change the value to Authorization or Order in the
		// DoExpressCheckoutPayment request. If you set the field to
		// Authorization or Order in SetExpressCheckout, you may set the field
		// to Sale.`
		//paymentDetails2.setPaymentAction(PaymentActionCodeType.SALE);

		// Unique identifier for the merchant. For parallel payments, this field
		// is required and must contain the Payer Id or the email address of the
		// merchant.
		//SellerDetailsType sellerDetails2 = new SellerDetailsType();
		//sellerDetails2.setPayPalAccountID("yangzhennanedward@gmail.com");
		//paymentDetails2.setSellerDetails(sellerDetails2);

		// A unique identifier of the specific payment request, which is
		// required for parallel payments.
		//paymentDetails2.setPaymentRequestID("PaymentRequest2");

		// `Address` to which the order is shipped, which takes mandatory
		// params:
		// 
		// * `Street Name`
		// * `City`
		// * `State`
		// * `Country`
		// * `Postal Code`
		//AddressType shipToAddress2 = new AddressType();
		//shipToAddress2.setStreet1("Ape Way");
		///shipToAddress2.setCityName("Austin");
		//shipToAddress2.setStateOrProvince("TX");
		//shipToAddress2.setCountry(CountryCodeType.SG);
		//shipToAddress2.setPostalCode("118424");

		// Your URL for receiving Instant Payment Notification (IPN) about this
		// transaction. If you do not specify this value in the request, the
		// notification URL from your Merchant Profile is used, if one exists.
		//paymentDetails2.setNotifyURL("http://localhost/ipn");

		   List<PaymentDetailsItemType> paymentDetailsItemTypes = new ArrayList<PaymentDetailsItemType>();
        PaymentDetailsItemType paymentDetailsItemType = new PaymentDetailsItemType();
        paymentDetailsItemType.setDescription("Attraction Pass");
        paymentDetailsItemType.setAmount(orderTotal1);
          paymentDetailsItemType.setQuantity(1);
           paymentDetailsItemTypes.add(paymentDetailsItemType);
paymentDetails1.setPaymentDetailsItem(paymentDetailsItemTypes);

		paymentDetailsList.add(paymentDetails1);
		

		setExpressCheckoutRequestDetails.setPaymentDetails(paymentDetailsList);

		SetExpressCheckoutReq setExpressCheckoutReq = new SetExpressCheckoutReq();
		SetExpressCheckoutRequestType setExpressCheckoutRequest = new SetExpressCheckoutRequestType(
				setExpressCheckoutRequestDetails);

		setExpressCheckoutReq
				.setSetExpressCheckoutRequest(setExpressCheckoutRequest);

		// ## Creating service wrapper object
		// Creating service wrapper object to make API call and loading
		// configuration file for your credentials and endpoint
		PayPalAPIInterfaceServiceService service = null;
		try {
                        System.err.println(System.getProperty("user.dir"));

			service = new PayPalAPIInterfaceServiceService(
					"C:\\Users\\WU JINGYUN\\Documents\\NetBeansProjects\\T01IRMS\\IRMS\\IRMS-war\\web\\WEB-INF\\sdk_config.properties");
		} catch (IOException e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		SetExpressCheckoutResponseType setExpressCheckoutResponse = null;
		try {
			// ## Making API call
			// Invoke the appropriate method corresponding to API in service
			// wrapper object
			setExpressCheckoutResponse = service
					.setExpressCheckout(setExpressCheckoutReq);
		} catch (Exception e) {
			logger.severe("Error Message : " + e.getMessage());
		}
		// ## Accessing response parameters
		// You can access the response parameters using getter methods in
		// response object as shown below
		// ### Success values
		if (setExpressCheckoutResponse.getAck().getValue()
				.equalsIgnoreCase("success")) {
System.out.println("-----------------------------success");
			// ### Redirecting to PayPal for authorization
			// Once you get the "Success" response, needs to authorise the
			// transaction by making buyer to login into PayPal. For that,
			// need to construct redirect url using EC token from response.
			// For example,
			// `redirectURL="https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="+setExpressCheckoutResponse.getToken();`

			// Express Checkout Token
			logger.info("EC Token:" + setExpressCheckoutResponse.getToken());
                        
                        
                        Map<Object, Object> map = new LinkedHashMap<Object, Object>();
						map.put("Ack", setExpressCheckoutResponse.getAck());
						/*
						 * A time stamped token by which you identify to PayPal that you are processing 
						 * this payment with Express Checkout. The token expires after three hours. 
						 * If you set the token in the SetExpressCheckout request, the value of the 
						 * token in the response is identical to the value in the request.
							Character length and limitations: 20 single-byte characters
						 */
						map.put("Token", setExpressCheckoutResponse.getToken());
						map.put("Redirect URL",
								"<a href=https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token="
										+ setExpressCheckoutResponse.getToken()
										+ ">Redirect To PayPal</a>");
						/*  HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

         request.getSession().setAttribute("map", map);
	externalContext.redirect("/IRMS-war/irmsShowPurchasePayConfirm.xhtml");*/
		}
		// ### Error Values
		// Access error values from error list using getter methods
		else {System.out.println("-----------------------------fail");
			List<ErrorType> errorList = setExpressCheckoutResponse.getErrors();
			logger.severe("API Error Message : "
					+ errorList.get(0).getLongMessage());
                        
		}url = "https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=" + setExpressCheckoutResponse.getToken();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("url", url);
        //String testUrl=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
        //System.out.println("testUrl== "+testUrl);
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        return setExpressCheckoutResponse;
		
	
    }

       
       
    public String loginBuyPass(ActionEvent actionEvent) throws IOException, ExistException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;

        System.out.println("username is " + username + " password is " + password);
        String hashPassword = cbb.hashPassword(password);
        //if attmep number lesser than 5, allow login
        //if attemp number larger than 5 and account has locked, only allow login after 5 mins .  

        //attemp number lesser than 5 or it's 5 mins after last attemp, allow login
        if ((cbb.getLoginAttemp(username) <= 2) || (cbb.checkLockOut(username) == true)) {
           // System.out.println("login number============" + cbb.getLoginAttemp(username));
            //have to reset attemp number to 0 and update attemp time(in the case where account is unlocked
            //, otherwise, if login fails again, the account will be locked for another 5 min
            if (cbb.checkLockOut(username)) {
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
            }
            System.out.println("login number============" +username);
            System.out.println("login number============" + hashPassword);
            //auth
            if ((username != null) && (password != null) && (cbb.verifyPassword(username, hashPassword))) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
             
                customer = cbb.findCustomer(username);
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
                long customerId=cbb.findCustomer(username).getId();
                 request.getSession().setAttribute("userId", customerId);
                System.out.println("get role ============");
                request.getSession().setAttribute("role", "customer");
                System.out.println("get role finish============");
                request.getSession().setAttribute("isLogin", true);



                // success,装入session中

                Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

                map.put("user", customer);

                this.customer = customer;

                redirct = "fail";
            //     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAmount", selectedShow.getShowId());
  // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("attraction", attractionName);
 // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quantity", selectedShow.getShowId());
   //String url=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("/IRMS-war/attractionPay.xhtml");
   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAmount", asbr.getAmount(q1,selectPassId1) +asbr.getAmount(q2,selectPassId2) + asbr.getAmount(q3,selectPassId3));

 this.BookPass();
                //FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/attractionPay.xhtml");
              
            } //auth fails
            
             else if (!cbb.checkCustomerExist(username)) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Username doesn't exist");
                customer = null;

                redirct = "fail";

            } else if (cbb.checkCustomerExist(username) && (!cbb.verifyPassword(username, hashPassword))) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Wrong password");
                customer = null;

                redirct = "fail";


            } else {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
                customer = null;

                redirct = "fail";
               
            }
        } //time diff is less than 5 mins, don't allow login 
        else {
            System.out.println("Exceed max login nunmber");
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Exceed Max Login Number! Please Try Login After 5 Mins");
            redirct = "fail";

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return redirct;

    }
    
    
    
    
    
    
    public String loginBuyTicket(ActionEvent actionEvent) throws IOException, ExistException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = null;
        boolean loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirct;

        System.out.println("username is " + username + " password is " + password);
        String hashPassword = cbb.hashPassword(password);
        //if attmep number lesser than 5, allow login
        //if attemp number larger than 5 and account has locked, only allow login after 5 mins .  

        //attemp number lesser than 5 or it's 5 mins after last attemp, allow login
        if ((cbb.getLoginAttemp(username) <= 2) || (cbb.checkLockOut(username) == true)) {
           // System.out.println("login number============" + cbb.getLoginAttemp(username));
            //have to reset attemp number to 0 and update attemp time(in the case where account is unlocked
            //, otherwise, if login fails again, the account will be locked for another 5 min
            if (cbb.checkLockOut(username)) {
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
            }
            System.out.println("login number============" +username);
            System.out.println("login number============" + hashPassword);
            //auth
            if ((username != null) && (password != null) && (cbb.verifyPassword(username, hashPassword))) {
                loggedIn = true;
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
             
                customer = cbb.findCustomer(username);
                cbb.setLoginAttempToZero(username);
                cbb.updateLoginAttempTime(username);
                long customerId=cbb.findCustomer(username).getId();
                 request.getSession().setAttribute("userId", customerId);
                System.out.println("get role ============");
                request.getSession().setAttribute("role", "customer");
                System.out.println("get role finish============");
                request.getSession().setAttribute("isLogin", true);



                // success,装入session中

                Map<String, Object> map = facesContext.getExternalContext().getSessionMap();

                map.put("user", customer);

                this.customer = customer;

                redirct = "fail";
           //      FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAmount", selectedShow.getShowId());
  // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("attraction", attractionName);
 // FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quantity", selectedShow.getShowId());
  String url=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
  //  FacesContext.getCurrentInstance().getExternalContext().redirect(url);
     FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAm", asbr.getTicketAmount(q4,selectTicketId1) +asbr.getTicketAmount(q5,selectTicketId2) + asbr.getTicketAmount(q6,selectTicketId3));
 System.out.println("BookTicket-->getTicketAmount"+asbr.getTicketAmount(q4,selectTicketId1) +asbr.getTicketAmount(q5,selectTicketId2) + asbr.getTicketAmount(q6,selectTicketId3));
 
 System.out.println("BookTicket-->getTicketAmount"+q4+"  "+selectTicketId1+"  "+q5+"  "+selectTicketId2+selectTicketId1);
 
 this.BookTicket();
              
              
            } //auth fails
            
             else if (!cbb.checkCustomerExist(username)) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Username doesn't exist");
                customer = null;

                redirct = "fail";

            } else if (cbb.checkCustomerExist(username) && (!cbb.verifyPassword(username, hashPassword))) {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Wrong password");
                customer = null;

                redirct = "fail";


            } else {
                loggedIn = false;
                cbb.updateLoginAttemp(username);
                cbb.updateLoginAttempTime(username);

                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Invalid Credentials");
                customer = null;

                redirct = "fail";
            }
        } //time diff is less than 5 mins, don't allow login 
        else {
            System.out.println("Exceed max login nunmber");
            loggedIn = false;
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Exceed Max Login Number! Please Try Login After 5 Mins");
            redirct = "fail";

        }

        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.addCallbackParam("loggedIn", loggedIn);
        return redirct;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setSelectPassId1(String selectPassId1) {
        this.selectPassId1 = selectPassId1;
    }

    public Map<String, String> getApList() {
        return apList;
    }

    public void setApList(Map<String, String> apList) {
        this.apList = apList;
    }

    public void setSelectedTicket(AttractionPass selectedTicket) {
        this.selectedTicket = selectedTicket;
    }

    public void setSelectTicketId1(String selectTicketId1) {
        this.selectTicketId1 = selectTicketId1;
    }

    public void setSelectTicketId2(String selectTicketId2) {
        this.selectTicketId2 = selectTicketId2;
    }

    public void setSelectTicketId3(String selectTicketId3) {
        this.selectTicketId3 = selectTicketId3;
    }

    public void setQuantity4(String quantity4) {
        this.quantity4 = quantity4;
    }

    public void setQuantity5(String quantity5) {
        this.quantity5 = quantity5;
    }

    public void setQuantity6(String quantity6) {
        this.quantity6 = quantity6;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }

    public void setQ3(int q3) {
        this.q3 = q3;
    }

    public void setQ4(int q4) {
        this.q4 = q4;
    }

    public void setQ5(int q5) {
        this.q5 = q5;
    }

    public void setQ6(int q6) {
        this.q6 = q6;
    }

    public AttractionPass getSelectedTicket() {
        return selectedTicket;
    }

    public String getSelectTicketId1() {
        return selectTicketId1;
    }

    public String getSelectTicketId2() {
        return selectTicketId2;
    }

    public String getSelectTicketId3() {
        return selectTicketId3;
    }

    public String getQuantity4() {
        return quantity4;
    }

    public String getQuantity5() {
        return quantity5;
    }

    public String getQuantity6() {
        return quantity6;
    }

    public int getQ1() {
        return q1;
    }

    public int getQ2() {
        return q2;
    }

    public int getQ3() {
        return q3;
    }

    public int getQ4() {
        return q4;
    }

    public int getQ5() {
        return q5;
    }

    public int getQ6() {
        return q6;
    }

    public void setSelectPassId2(String selectPassId2) {
        this.selectPassId2 = selectPassId2;
    }

    public void setSelectPassId3(String selectPassId3) {
        this.selectPassId3 = selectPassId3;
    }

    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }

    public void setQuantity2(String quantity2) {
        this.quantity2 = quantity2;
    }

    public void setQuantity3(String quantity3) {
        this.quantity3 = quantity3;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getSelectPassId1() {
        return selectPassId1;
    }

    public String getSelectPassId2() {
        return selectPassId2;
    }

    public String getSelectPassId3() {
        return selectPassId3;
    }

    public String getQuantity1() {
        return quantity1;
    }

    public String getQuantity2() {
        return quantity2;
    }

    public String getQuantity3() {
        return quantity3;
    }

    public AttractionSessionBeanRemote getAsb() {
        return asbr;
    }

    public List<Attraction> getAttList() {
        return attList;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public List<AttractionPass> getAp() {
        return ap;
    }

    public List<AttractionTicket> getAt() {
        return at;
    }

    public AttractionPass getSelectedPass() {
        return selectedPass;
    }

    public void setAttList(List<Attraction> attList) {
        this.attList = attList;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public void setAp(List<AttractionPass> ap) {
        this.ap = ap;
    }

    public void setAt(List<AttractionTicket> at) {
        this.at = at;
    }

    public void setSelectedPass(AttractionPass selectedPass) {
        this.selectedPass = selectedPass;
    }

    public void setAtList(Map<String, String> atList) {
        this.atList = atList;
    }

    public Map<String, String> getAtList() {
        return atList;
    }

    public Date getDoa() {
        return doa;
    }

    public void setDoa(Date doa) {
        this.doa = doa;
    }
}
