//
//  SplashViewController.swift
//  Thriving Stones
//
//  Created by Hui Xin Tang on 9/7/18.
//  Copyright © 2018 Team eXi. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth

class SplashViewController: UIViewController {
    let usernameTextField: UITextField = {
        let u = UITextField()
        u.placeholder = "username"
        u.textColor = .black
        u.autocapitalizationType = .none
        return u
    }()
    let passwordTextField: UITextField = {
        let p = UITextField()
        p.placeholder = "password"
        p.textColor = .black
        p.autocapitalizationType = .none
        return p
    }()
    let loginButton : UIButton = {
        let l = UIButton(type: .system)
        l.setTitle("Log in", for: .normal)
        l.setTitleColor(.white, for: .normal)
        l.backgroundColor = .red
        l.addTarget(self, action: #selector(loginAction), for: .touchUpInside)
        return l
    }()
    
    private var splashImage: UIImageView! = UIImageView()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Setting background color of view
        view.backgroundColor = UIColor.init(red: 252/255, green: 252/255, blue: 244/255, alpha: 1)
        
        splashImage.translatesAutoresizingMaskIntoConstraints = false
        
        //Setting the animation properties of the logo
        splashImage.animationImages = [#imageLiteral(resourceName: "splash1"),#imageLiteral(resourceName: "splash2"),#imageLiteral(resourceName: "splash3"),#imageLiteral(resourceName: "splash4"),#imageLiteral(resourceName: "splash5"),#imageLiteral(resourceName: "splash6")]
        splashImage.animationDuration = 2
        splashImage.animationRepeatCount = 1
        splashImage.startAnimating()
        
        view.addSubview(splashImage)
        
        //Setting constraints for displayed logo
        splashImage.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        splashImage.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
    
        setupTextFieldComponent()
    }
    fileprivate func setupTextFieldComponent(){
        setupUsernameField()
        setupPasswordField()
        setupLoginButton()
    }
    fileprivate func setupUsernameField(){
        view.addSubview(usernameTextField)
        
        usernameTextField.translatesAutoresizingMaskIntoConstraints = false
        usernameTextField.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 16).isActive = true
        usernameTextField.leftAnchor.constraint(equalTo: view.leftAnchor, constant: 24).isActive = true
        usernameTextField.rightAnchor.constraint(equalTo: view.rightAnchor, constant: -24).isActive = true
        usernameTextField.heightAnchor.constraint(equalToConstant: 30).isActive = true
    }
    fileprivate func setupPasswordField(){
        view.addSubview(passwordTextField)
        
        passwordTextField.translatesAutoresizingMaskIntoConstraints = false
        passwordTextField.topAnchor.constraint(equalTo: usernameTextField.bottomAnchor, constant: 8).isActive = true
        passwordTextField.leftAnchor.constraint(equalTo: usernameTextField.leftAnchor, constant: 0).isActive = true
        passwordTextField.rightAnchor.constraint(equalTo: usernameTextField.rightAnchor, constant: 0).isActive = true
        passwordTextField.heightAnchor.constraint(equalToConstant: 30).isActive = true
    }
    fileprivate func setupLoginButton(){
        view.addSubview(loginButton)
        
        loginButton.translatesAutoresizingMaskIntoConstraints = false
        loginButton.topAnchor.constraint(equalTo: passwordTextField.bottomAnchor, constant: 8).isActive = true
        loginButton.leftAnchor.constraint(equalTo: passwordTextField.leftAnchor, constant: 0).isActive = true
        loginButton.rightAnchor.constraint(equalTo: passwordTextField.rightAnchor, constant: 0).isActive = true
        loginButton.heightAnchor.constraint(equalToConstant: 30).isActive = true
    }
    @IBAction func loginAction(_sender: AnyObject){
         if self.usernameTextField.text == "" || self.passwordTextField.text == "" {
            let alertController = UIAlertController(title: "Error", message: "Please enter an email and password.", preferredStyle: .alert)
            
            let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
            alertController.addAction(defaultAction)
            
            self.present(alertController, animated: true, completion: nil)
         }else{
            let ref = Database.database().reference().child("users")
            ref.observeSingleEvent(of: .value, with: { snapshot in
                
                if !snapshot.exists() {
                    let alertController = UIAlertController(title: "Error", message: "Connection error.", preferredStyle: .alert)
                    
                    let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
                    alertController.addAction(defaultAction)
                    
                    self.present(alertController, animated: true, completion: nil)
                    return
                }
                
                for item in snapshot.children {
                    let child = item as! DataSnapshot
                    let dict = child.value as! [String:String]
                    print(dict)
                    if dict["username"] == self.usernameTextField.text{
                        if dict["pwd"] == self.passwordTextField.text{
                            let homepageController = HomepageController()
                            self.navigationController?.pushViewController(homepageController, animated: true)
                        }else{
                            let alertController = UIAlertController(title: "Error", message: "Invalid password.", preferredStyle: .alert)
                            
                            let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
                            alertController.addAction(defaultAction)
                            
                            self.present(alertController, animated: true, completion: nil)
                        }
                    }else{
                        let alertController = UIAlertController(title: "Error", message: "Username does not exist.", preferredStyle: .alert)
                        
                        let defaultAction = UIAlertAction(title: "OK", style: .cancel, handler: nil)
                        alertController.addAction(defaultAction)
                        
                        self.present(alertController, animated: true, completion: nil)
                    }
                }
            })
        }
        
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
