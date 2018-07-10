//
//  SplashViewController.swift
//  Thriving Stones
//
//  Created by Hui Xin Tang on 9/7/18.
//  Copyright © 2018 Team eXi. All rights reserved.
//

import UIKit

class SplashViewController: UIViewController {
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
