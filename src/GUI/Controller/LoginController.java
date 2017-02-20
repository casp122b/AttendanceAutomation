/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;


/**
 * FXML Controller class
 *
 * @author Jens, Patrick, Casper
 */
public class LoginController implements Initializable {


   
      @FXML
    private TextField user;
       @FXML
    private PasswordField password;
    @FXML
    private Label incorrect;
    @FXML
    private Button loginBtn;
    
    
    public MainViewController mainView = new MainViewController(); 
    

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    

    @FXML
    private void loginUser(ActionEvent event) throws IOException {
      String username = user.getText();
      
        String pass = password.getText();
        
        if(username.equals("Peter@easv.dk")&& pass.equals("Wombat")){
               
        

System.out.println("det virker");



                    }
        else{
// mainView.teacherButtons();
            incorrect.setText("Incorrect Username or Password");
        }
    }
    
}
