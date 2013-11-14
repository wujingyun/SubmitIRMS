/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedBean;

import ejb.AdminUserBeanRemote;
import ejb.ShowBeanRemote;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;


import ejb.CustomerBeanRemote;
import entity.Customer;
import exception.ExistException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
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
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsItemType;
import urn.ebay.apis.eBLBaseComponents.PaymentDetailsType;
import urn.ebay.apis.eBLBaseComponents.SellerDetailsType;
import urn.ebay.apis.eBLBaseComponents.SetExpressCheckoutRequestDetailsType;
/**
 *
 * @author WU JINGYUN
 */
@ManagedBean
@SessionScoped
public class ShowPurchaseConfirmManagedBean implements Serializable {

    @EJB
    ShowBeanRemote showBeanRemote;
    @EJB
    AdminUserBeanRemote aub;
    @EJB
    CustomerBeanRemote cbb;
    private Customer customer;
    private String username;
    private String password;
    private String showTitle;
 private String[] array ;
    private String showId;
private Long showIdlong;
        private String seat;
         private List<Long> ticketSeatList;
         private double totalAmount;
      
    private Integer quantity;
 
    private Double orderTotal = new Double(0);
    private String url = new String();
    /**
     * Creates a new instance of CreateInternalMsgManagedBean
     */
    public ShowPurchaseConfirmManagedBean() {
    }

    @PostConstruct
    public void init() throws ExistException {
        System.out.println("init");
         FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
       
         showId = paramMap.get("showId");
         System.out.println("init"+showId);
        showIdlong = Long.parseLong(showId);
         showTitle=showBeanRemote.getShowNameById(showIdlong);
         System.out.println("init"+showTitle);
          seat = paramMap.get("seat");
          System.out.println("init"+seat);
          
                  array = seat.split("\\,",-1); 
  System.out.println("init"+array.length);
      
        ticketSeatList=new ArrayList();
        for (int i=0;i<array.length-1;i++){
            ticketSeatList.add(Long.valueOf(array[i])); 
            System.out.println("init"+array[i]);
        }
        totalAmount=showBeanRemote.getTotalAmount(ticketSeatList);
        
    }
public void cancelReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/irmsShowDisplay.xhtml");
    }
    
     public void confirmReservation(ActionEvent event) throws IOException {
    
    
    FacesContext.getCurrentInstance().getExternalContext().redirect("/IRMS-war/irmsShowDisplay.xhtml");
    }
   
     public String login(ActionEvent actionEvent) throws IOException, ExistException {
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
   FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAmount",totalAmount);
 System.out.println("totalAmount============"+totalAmount);
 this.BookTicket();
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
    
     
     
     
     
     
    public boolean BookTicket() throws ExistException, IOException{
         RequestContext context = RequestContext.getCurrentInstance();
        boolean loggedIn = false;
 //String[] splitArray = seat.split(",");
  array = seat.split("\\,",-1); 
  System.out.println("bookTicket"+array.length);
  //try{ 
      HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        long uid = (Long) request.getSession().getAttribute("userId");
         System.out.println("bookTicket  userid "+uid);
        ticketSeatList=new ArrayList();
        for (int i=0;i<array.length-1;i++){
            ticketSeatList.add(Long.valueOf(array[i])); 
            System.out.println("bookTicket"+array[i]);
    //    }
        
         
         showBeanRemote.makeBooking(uid, ticketSeatList);
         this.setExpressCheckout();
         return true;
    }
   //catch (Exception e) {
             //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ticket Reservation Fail",""));
             //   context.addCallbackParam("loggedIn", false);
             //   return;
                
           // }

        
        //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ticket Reserved Successfully",""));
        //  context.addCallbackParam("loggedIn", true);      
        return false;
    
    }

     public SetExpressCheckoutResponseType setExpressCheckout() throws IOException {
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
				.setReturnURL("http://localhost:8080/IRMS-war/showPaymentSuccess.xhtml");

		// URL to which the buyer is returned if the buyer does not approve the
		// use of PayPal to pay you. For digital goods, you must add JavaScript
		// to this page to close the in-context experience.
		// `Note:
		// PayPal recommends that the value be the original page on which the
		// buyer chose to pay with PayPal or establish a billing agreement.`
		setExpressCheckoutRequestDetails
				.setCancelURL("http://localhost:8080/IRMS-war/showPaymentCancel.xhtml");

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
        paymentDetailsItemType.setDescription("Show Ticket");
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

       
    public String getRedirectUrl(ActionEvent event) throws IOException {
        //System.out.println("BEFORE: url "+this.url);
        this.url= (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("url");
        //System.out.println("AFTER: url "+this.url);
        FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        return url;
    }
    
    
    public AdminUserBeanRemote getAub() {
        return aub;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public String getShowId() {
        return showId;
    }

    public String getSeat() {
        return seat;
    }

    public void setAub(AdminUserBeanRemote aub) {
        this.aub = aub;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public void setShowIdlong(Long showIdlong) {
        this.showIdlong = showIdlong;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getShowIdlong() {
        return showIdlong;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
 
    

}
