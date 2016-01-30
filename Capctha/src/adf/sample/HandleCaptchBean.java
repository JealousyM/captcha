package adf.sample;

import java.io.UnsupportedEncodingException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import nl.captcha.Captcha;
import oracle.adf.view.rich.context.AdfFacesContext;


public class HandleCaptchBean {

    public HandleCaptchBean() {
    }

    public void verifyAnswer(ActionEvent actionEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx =  fctx.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
        Captcha captcha = (Captcha) ectx.getSessionMap().get(Captcha.NAME);
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            //bad luck - but ignore
            System.out.println("UTF not supported !");
        }
        String answer = (String) ectx.getRequestMap().get("bestGuess");
        if (answer != null && captcha.isCorrect(answer)){
            message("Hello, Human");
        }
        else{           
            message("Hello Mr. Roboto. Try again.");
            UIComponent panelLabelAndMessage = ((UIComponent) actionEvent.getSource()).getParent().getParent();
            UIComponent panelFormlayout = panelLabelAndMessage.getParent();
            AdfFacesContext.getCurrentInstance().addPartialTarget(panelFormlayout);
        }

    }

    private void message(String s){
        FacesContext fctx = FacesContext.getCurrentInstance();
        fctx.addMessage("it1",new FacesMessage(FacesMessage.SEVERITY_INFO,"Info: "+s,null));
    }
}
