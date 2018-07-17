<%@include file="header.jsp"%>
<style>
    #generate_btn{
        padding: 5px;
        margin-left : 50px;
        background-color:#f7a4a3;
        color:#fff;
        border-radius: 5px;
    }
    
    #generate_btn:hover{
        background:transparent;
        border: 1px solid #f7a4a3;
        color:#f7a4a3;
    }
</style>
<div class="col-md-10">
    <div style="text-align: center;margin: 20px;"><a href="#">Add User </a> / <a href="#">Upload User</a></h5></div>

    <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-5 form">
            <form class="login100-form validate-form" action="TutorCreation.java">
       
                <div class="wrap-input100 validate-input m-b-23" data-validate = "Tutor Name is reauired">
                    <span class="label-input100">Tutor's name</span>
                    <input class="input100" type="text" name="tutorName" placeholder="Type username">
                    <span class="focus-input100" data-symbol="&#xf207;"></span>
                </div>
                <br/>
                
                <div class="wrap-input100 validate-input" data-validate="Phone Number is required">
                    <span class="label-input100">Phone number</span>
                    <input class="input100" type="text" name="phoneNo" placeholder="Type phone number">
                    <span class="focus-input100" data-symbol="&#xf2be;"></span>
                </div>
                <br/>

               
                <div class="wrap-input100 validate-input" data-validate="Email is required">
                    <span class="label-input100">Email</span>
                    <input class="input100" type="email" name="emailAdd" placeholder="Type email">
                    <span class="focus-input100" data-symbol="&#xf15a;"></span>
                </div>
                <br/>

                
                <div class="wrap-input100 validate-input" data-validate="Age is required">
                    <span class="label-input100">Age</span>
                    <input class="input100" type="age" name="age" placeholder="Type age">

                </div>
                <br/>
                
                <div>
                    <span class="label-input100">Gender</span>
                    <select id="gender" name="gender" class="cd-select">
                        <option value="-1" selected>Select Gender</option>
                        <option value="1" class="icon-female">Female</option>
                        <option value="2" class="icon-male">Male</option>
                    </select> 
                </div>
                <br/>
                      
                <div class="wrap-input100 validate-input" data-validate="Password is required"> 
                    <span class="label-input100">Generate Password  <input id="generate_btn" type="button" value="Generate" onClick="generatePassword(16);"</span>  
                    <input class="input100" type="text" name="password" id="password" placeholder="Type your password">
                    <span class="focus-input100" data-symbol="&#xf190;"></span>
                </div>
                <br/>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" type="submit">
                            Create Tutor
                        </button>
                    </div>
                </div>	
            </form>
        </div>
    </div>


</div>
</div>
</div>

<%@include file="footer.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/styling/js/jquery.dropdown.js"></script>
<script>
    var Password = {
 
  _pattern : /[a-zA-Z0-9_\-\+\.]/,
  
  
  _getRandomByte : function()
  {
    if(window.crypto && window.crypto.getRandomValues) 
    {
      var result = new Uint8Array(1);
      window.crypto.getRandomValues(result);
      return result[0];
    }
    else if(window.msCrypto && window.msCrypto.getRandomValues) 
    {
      var result = new Uint8Array(1);
      window.msCrypto.getRandomValues(result);
      return result[0];
    }
    else
    {
      return Math.floor(Math.random() * 256);
    }
  },
  
  generate : function(length)
  {
    return Array.apply(null, {'length': length})
      .map(function()
      {
        var result;
        while(true) 
        {
          result = String.fromCharCode(this._getRandomByte());
          if(this._pattern.test(result))
          {
            return result;
          }
        }        
      }, this)
      .join('');  
  }    
    };
    
    function generatePassword(len){
        var pwd = Password.generate(len);
        $("#password").val(pwd);
    }
    
    $(function () {

        $('#gender').dropdown({
        });

    });
</script>
