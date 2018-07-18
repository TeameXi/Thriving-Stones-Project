//
//  LoginViewController.swift
//  Thriving Stones
//
//  Created by Hui Xin Tang on 16/7/18.
//  Copyright © 2018 Team eXi. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth

class LoginViewController: UIViewController {
    private var headerImage: UIImageView! = UIImageView()
    
    private var usernameField: UITextField! = UITextField()
    
    private var pwdField: UITextField! = UITextField()
    
    private var loginButton: UIButton! = UIButton()
    
    private var resetPwdButton: UIButton! = UIButton()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Set background color for view
        view.backgroundColor = UIColor.init(red: 252/255, green: 252/255, blue: 244/255, alpha: 1)
        
        //Setting up header image
        headerImage.translatesAutoresizingMaskIntoConstraints = false
        
        headerImage.image = #imageLiteral(resourceName: "header")
        
        self.view.addSubview(headerImage)
        
        headerImage.topAnchor.constraint(equalTo: self.view.topAnchor, constant: 220).isActive = true
        headerImage.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        headerImage.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true
        
        //Setting up username field
        usernameField.translatesAutoresizingMaskIntoConstraints = false
        
        usernameField.layer.cornerRadius = 6.0
        usernameField.layer.borderWidth = 1.0
        usernameField.layer.borderColor = UIColor.lightGray.cgColor
        usernameField.font = UIFont.systemFont(ofSize: 18.0)
        usernameField.placeholder = "Email"
        usernameField.textAlignment = .center
        usernameField.returnKeyType = .done
        usernameField.autocapitalizationType = .none
        
        self.view.addSubview(usernameField)
        
        usernameField.topAnchor.constraint(equalTo: headerImage.bottomAnchor, constant: 20).isActive = true
        usernameField.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        usernameField.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true
        usernameField.heightAnchor.constraint(equalToConstant: 45).isActive = true
        
        //Setting up password field
        pwdField.translatesAutoresizingMaskIntoConstraints = false
        
        pwdField.layer.cornerRadius = 6.0
        pwdField.layer.borderWidth = 1.0
        pwdField.layer.borderColor = UIColor.lightGray.cgColor
        pwdField.font = UIFont.systemFont(ofSize: 18.0)
        pwdField.placeholder = "Password"
        pwdField.textAlignment = .center
        pwdField.returnKeyType = .done
        pwdField.autocapitalizationType = .none
        pwdField.isSecureTextEntry = true
        
        self.view.addSubview(pwdField)
        
        pwdField.topAnchor.constraint(equalTo: usernameField.bottomAnchor, constant: 10).isActive = true
        pwdField.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        pwdField.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true
        pwdField.heightAnchor.constraint(equalToConstant: 45).isActive = true
        
        //Setting up login button
        loginButton.translatesAutoresizingMaskIntoConstraints = false
        
        loginButton.setTitle("Login", for: .normal)
        loginButton.titleLabel?.textAlignment = .center
        loginButton.setTitleColor(UIColor.white, for: .normal)
        loginButton.layer.cornerRadius = 6.0
        loginButton.backgroundColor = UIColor.init(red: 161/255, green: 203/255, blue: 186/255, alpha: 1)
        loginButton.addTarget(self, action: #selector(performLogin), for: .touchUpInside)
        
        self.view.addSubview(loginButton)
        
        loginButton.topAnchor.constraint(equalTo: pwdField.bottomAnchor, constant: 20).isActive = true
        loginButton.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 60).isActive = true
        loginButton.widthAnchor.constraint(equalToConstant: 100).isActive = true
        
        //Setting up reset pwd button
        resetPwdButton.translatesAutoresizingMaskIntoConstraints = false
        
        resetPwdButton.setTitle("Reset Password", for: .normal)
        resetPwdButton.titleLabel?.textAlignment = .center
        resetPwdButton.setTitleColor(UIColor.white, for: .normal)
        resetPwdButton.layer.cornerRadius = 6.0
        resetPwdButton.backgroundColor = UIColor.init(red: 161/255, green: 203/255, blue: 186/255, alpha: 1)
        resetPwdButton.addTarget(self, action: #selector(performReset), for: .touchUpInside)
        
        self.view.addSubview(resetPwdButton)
        
        resetPwdButton.topAnchor.constraint(equalTo: pwdField.bottomAnchor, constant: 20).isActive = true
        resetPwdButton.leftAnchor.constraint(equalTo: loginButton.rightAnchor, constant: 10).isActive = true
        resetPwdButton.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -60).isActive = true
    }
    
    @objc func performReset(){
        //Link to website reset pwd page
    }
    
    @objc func performLogin(_sender: UIButton){
        if let email = usernameField.text, email.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines).isEmpty, let pwd = pwdField.text, pwd.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
            let alert = UIAlertController(title: "Invalid Email/Password", message: "Please enter a valid email and password", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default))
            self.present(alert, animated: true, completion: nil)
        }else if let email = usernameField.text, email.trimmingCharacters(in: CharacterSet.whitespacesAndNewlines).isEmpty {
            let alert = UIAlertController(title: "Invalid Email", message: "Please enter a valid email", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default))
            self.present(alert, animated: true, completion: nil)
        }else if let pwd = pwdField.text, pwd.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty {
            let alert = UIAlertController(title: "Invalid Password", message: "Please enter a valid password", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default))
            self.present(alert, animated: true, completion: nil)
        }else {
            if let email = usernameField.text, let pwd = pwdField.text {
                if email != "admin" {
                    Auth.auth().signIn(withEmail: email, password: pwd) { (user, error) in
                        print(user, " YAYYYY ", error)
                        if user == nil {
                            let alert = UIAlertController(title: "Invalid Account", message: "Invalid Email/Password", preferredStyle: UIAlertControllerStyle.alert)
                            alert.addAction(UIAlertAction(title: "Ok", style: .default))
                            self.present(alert, animated: true, completion: nil)
                        }else {
                            self.launchHomePage()
                        }
                    }
                }else{
                    let ref = Database.database().reference()
                    ref.child("users").child("admin").observe(.value) { (snapshot) in
                        print(snapshot, " YAYYYY")
                        let userDict = snapshot.value as? [String: AnyObject] ?? [:]
                        if let emailEntered = userDict["email"] as? String, emailEntered == self.usernameField.text{
                            if let pwdEntered = userDict["pwd"] as? String, pwdEntered == self.pwdField.text {
                                self.launchHomePage()
                            }else {
                                let alert = UIAlertController(title: "Invalid Password", message: "Incorrect Password", preferredStyle: UIAlertControllerStyle.alert)
                                alert.addAction(UIAlertAction(title: "Ok", style: .default))
                                self.present(alert, animated: true, completion: nil)
                            }
                        }else{
                            let alert = UIAlertController(title: "Invalid Account", message: "Account does not exist", preferredStyle: UIAlertControllerStyle.alert)
                            alert.addAction(UIAlertAction(title: "Ok", style: .default))
                        }
                    }
                }
            }
        }
    }
    
    func launchHomePage() {
        let homeController = HomePageViewController()
        self.present(homeController, animated: true) {
            print("SUCCESS")
        }
    }
}
