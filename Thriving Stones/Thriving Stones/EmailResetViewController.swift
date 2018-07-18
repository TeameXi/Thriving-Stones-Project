//
//  EmailResetViewController.swift
//  Thriving Stones
//
//  Created by Hui Xin Tang on 18/7/18.
//  Copyright © 2018 Team eXi. All rights reserved.
//

import UIKit
import FirebaseDatabase
import FirebaseAuth

class EmailResetViewController: UIViewController {
    private var header: UIImageView! = UIImageView()
    
    private var instructions: UILabel! = UILabel()
    
    private var emailInput: UITextField! = UITextField()
    
    private var resetButton: UIButton! = UIButton()
    
    private var backLogin: UIButton! = UIButton()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.navigationController?.isNavigationBarHidden = true
        
        //Set background color for view
        view.backgroundColor = UIColor.init(red: 252/255, green: 252/255, blue: 244/255, alpha: 1)
        
        //Setting up header image
        header.translatesAutoresizingMaskIntoConstraints = false
        
        header.image = #imageLiteral(resourceName: "header")
        
        self.view.addSubview(header)
        
        header.topAnchor.constraint(equalTo: self.view.topAnchor, constant: 180).isActive = true
        header.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
        
        //Setting up instructions
        instructions.translatesAutoresizingMaskIntoConstraints = false
        
        instructions.text = "Please enter registered email address to reset your password"
        instructions.font = UIFont.systemFont(ofSize: 14.0)
        instructions.textAlignment = .center
        instructions.numberOfLines = 0
        instructions.textColor = UIColor.darkGray
        
        self.view.addSubview(instructions)
        
        instructions.topAnchor.constraint(equalTo: header.bottomAnchor, constant: 30).isActive = true
        instructions.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        instructions.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true

        //Setting up email field
        emailInput.translatesAutoresizingMaskIntoConstraints = false
        
        emailInput.layer.cornerRadius = 6.0
        emailInput.layer.borderWidth = 1.0
        emailInput.layer.borderColor = UIColor.lightGray.cgColor
        emailInput.font = UIFont.systemFont(ofSize: 18.0)
        emailInput.placeholder = "Enter Email"
        emailInput.textAlignment = .center
        emailInput.returnKeyType = .done
        emailInput.autocapitalizationType = .none
        
        self.view.addSubview(emailInput)
        
        emailInput.topAnchor.constraint(equalTo: instructions.bottomAnchor, constant: 20).isActive = true
        emailInput.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        emailInput.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true
        emailInput.heightAnchor.constraint(equalToConstant: 45).isActive = true
        
        //Setting up reset pwd button
        resetButton.translatesAutoresizingMaskIntoConstraints = false
        
        resetButton.setTitle("Continue", for: .normal)
        resetButton.titleLabel?.textAlignment = .center
        resetButton.setTitleColor(UIColor.white, for: .normal)
        resetButton.backgroundColor = UIColor.init(red: 161/255, green: 203/255, blue: 186/255, alpha: 1)
        resetButton.layer.cornerRadius = 3.0;
        
        resetButton.layer.borderWidth = 2.0;
        resetButton.layer.borderColor = UIColor.clear.cgColor
        
        resetButton.layer.shadowColor = UIColor.init(red: 77/255, green: 153/255, blue: 77/255, alpha: 1).cgColor
        resetButton.layer.shadowOpacity = 0.8;
        resetButton.layer.shadowRadius = 1.0;
        resetButton.layer.shadowOffset = CGSize.init(width: 3, height: 0)
        resetButton.addTarget(self, action: #selector(continueReset), for: .touchUpInside)
        
        self.view.addSubview(resetButton)
        
        resetButton.topAnchor.constraint(equalTo: emailInput.bottomAnchor, constant: 30).isActive = true
        resetButton.leftAnchor.constraint(equalTo: self.view.leftAnchor, constant: 40).isActive = true
        resetButton.rightAnchor.constraint(equalTo: self.view.rightAnchor, constant: -40).isActive = true
        
        //Setting up back to login
        backLogin.translatesAutoresizingMaskIntoConstraints = false
        
        backLogin.setTitle("<<Back", for: .normal)
        backLogin.titleLabel?.textAlignment = .center
        backLogin.setTitleColor(UIColor.black, for: .normal)
        backLogin.backgroundColor = UIColor.init(red: 252/255, green: 252/255, blue: 244/255, alpha: 1)
        backLogin.titleLabel?.font = UIFont.boldSystemFont(ofSize: 13.0)
        backLogin.addTarget(self, action: #selector(backToLogin), for: .touchUpInside)
        
        self.view.addSubview(backLogin)
        
        backLogin.topAnchor.constraint(equalTo: resetButton.bottomAnchor, constant: 20).isActive = true
        backLogin.centerXAnchor.constraint(equalTo: self.view.centerXAnchor).isActive = true
    }
    
    @objc func backToLogin() {
        let login = LoginViewController()
        self.present(login, animated: true, completion: nil)
    }
    
    @objc func continueReset() {
        var status: Bool = false
        
        if let email = emailInput.text, !email.isEmpty {
            let ref = Database.database().reference()
            
            ref.child("users").observe(.value) { (snapshot) in
                let usersDict = snapshot.value as? [String: AnyObject] ?? [:]
                if email != "admin" {
                    let key = String(email.split(separator: "@", maxSplits: 1, omittingEmptySubsequences: true)[0])
                    if usersDict[key] != nil  {
                        let userInfo = usersDict[key] as? [String: AnyObject] ?? [:]
                        if userInfo["email"] != nil, let emailFound = userInfo["email"] as? String, emailFound == email {
                            status = true
                        }
                    }
                } else {
                    status = true
                }
                
                if status == true{
                    Auth.auth().sendPasswordReset(withEmail: email, completion: { (error) in
                        print(error)
                        if error == nil {
                            let alert = UIAlertController(title: "Password Reset", message: "Password Succesfully Reset!", preferredStyle: UIAlertControllerStyle.alert)
                            alert.addAction(UIAlertAction.init(title: "Back", style: .default, handler: { (action) in
                                let login = LoginViewController()
                                self.present(login, animated: true, completion: nil)
                            }))
                            self.present(alert, animated: true, completion: nil)
                        }else {
                            let alert = UIAlertController(title: "Connection Error", message: "Please try again", preferredStyle: UIAlertControllerStyle.alert)
                            alert.addAction(UIAlertAction(title: "Ok", style: .default))
                            self.present(alert, animated: true, completion: nil)
                        }
                    })
                } else {
                    let alert = UIAlertController(title: "Invalid Email", message: "Please enter a valid email address", preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "Ok", style: .default))
                    self.present(alert, animated: true, completion: nil)
                }
            }
        }
    }
}
